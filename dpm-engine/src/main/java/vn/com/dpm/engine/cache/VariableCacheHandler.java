package vn.com.dpm.engine.cache;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.TaskService;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import vn.com.dpm.common.constants.CacheConstants;
import vn.com.dpm.common.locks.Striped;
import vn.com.dpm.common.utils.RedisUtils;
import vn.com.dpm.common.variables.ObjectVariableSerializable;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * Using local-cache to decrease workload to database, redis cache.
 * Using redis for scaling.
 *
 */
@Slf4j
@Component
public class VariableCacheHandler implements VariableCache, DisposableBean {

    private TaskService taskService;
    private RedisUtils redisUtils;
    private final ScheduledExecutorService executor;
    private final Cache<String, Map<String, Object>> localVarCache;
    // Using this for write-behind strategy.
    private final Map<String, Map<String, Object>> bufferChangedVars;
    private ObjectMapper mapper;
    private final Striped<Lock> striped = Striped.lazy(6);
//    private InternalNotifyHelper internalNotifyHelper;

    public VariableCacheHandler() {
        // Temporary cache variable for 10 seconds.
        this.localVarCache = CacheBuilder.newBuilder()
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .build();

        // Init buffer
        this.bufferChangedVars = new ConcurrentHashMap<>();
        this.executor = Executors.newSingleThreadScheduledExecutor();
        this.executor.scheduleWithFixedDelay(this::flushBuffer, 1, 2, TimeUnit.SECONDS);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void saveVariables(String taskId, Map<String, Object> vars) {
        var lock = striped.get(taskId);
        try {
            lock.lock();
            var varTaskKey = genTaskVarCacheKey(taskId);
            var variables = localVarCache.getIfPresent(varTaskKey);
            if (CollectionUtils.isEmpty(variables)) {
                variables = (Map<String, Object>) redisUtils.getValue(varTaskKey);
            }
            if (CollectionUtils.isEmpty(variables)) {
                saveToBuffer(taskId, vars);
            } else {
                compareAndSet(taskId, variables, vars);
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void saveVariable(String taskId,
                             String varName,
                             Object value) {
        taskService.setVariable(taskId, varName, value);
    }

    private void compareAndSet(String taskId,
                               Map<String, Object> variables,
                               Map<String, Object> vars) {
        var varTaskKey = genTaskVarCacheKey(taskId);
        var changedVars = new HashMap<String, Object>(10);
        for (var varEntry : vars.entrySet()) {
            var varName = varEntry.getKey();
            var varValue = varEntry.getValue();
            if (variables.containsKey(varName)) {
                if (varValue == null) {
                    variables.remove(varName);
                    changedVars.put(varName, null);
                } else if (!varEqual(variables.get(varName), varValue)) {
                    variables.put(varName, varValue);
                    changedVars.put(varName, varValue);
                }
            } else {
                variables.put(varName, varValue);
                changedVars.put(varName, varValue);
            }
        }

        if (!changedVars.isEmpty()) {
            // Update cache
            redisUtils.setValue(varTaskKey, variables, Duration.ofDays(1));
            // Save change variable to buffer
            saveToBuffer(taskId, changedVars);
        }
    }

    private boolean varEqual(Object oldValue, Object newValue) {
        //check oldValue and new is same null. prevent null pointer exception.
        if (oldValue == null) {
            return newValue == null;
        }
        if (newValue instanceof Map<?, ?> && oldValue instanceof Map<?, ?>) {
            return newValue.equals(oldValue);
        }
        if (ObjectVariableSerializable.class.isAssignableFrom(oldValue.getClass())) {
            var convertedOldValue = mapper.convertValue(oldValue, Map.class);
            return newValue.equals(convertedOldValue);
        }
        if (oldValue instanceof JsonNode) {
            var convertedNewValue = mapper.convertValue(newValue, JsonNode.class);
            return oldValue.equals(convertedNewValue);
        }

        return newValue.equals(oldValue);
    }

    @Override
    public Map<String, Object> getVariables(String taskId) {
        var variables = taskService.getVariables(taskId);
        var buffers = bufferChangedVars.get(taskId);

        if (buffers != null) {
            variables.putAll(buffers);
        }

        var varTaskKey = genTaskVarCacheKey(taskId);

        localVarCache.invalidate(varTaskKey);
        localVarCache.put(varTaskKey, variables);
        return variables;
    }

    @Override
    public void clearCache(String taskId) {
        var varTaskKey = genTaskVarCacheKey(taskId);

        localVarCache.invalidate(varTaskKey);
        redisUtils.delete(varTaskKey);
    }

    @Override
    public synchronized void flushBuffer() {
        if (bufferChangedVars.isEmpty()) return;
        var iterator = bufferChangedVars.entrySet().iterator();
        while (iterator.hasNext()) {
            var entry = iterator.next();
            var lock = striped.get(entry.getKey());
            try {
                lock.lock();
                trySaveVariables(entry.getKey(), entry.getValue());
                iterator.remove();
            } finally {
                lock.unlock();
            }
        }
    }

    /**
     * try to save variables of task, notify error if raise any error.
     *
     * @param taskId    task id.
     * @param variables variables to save.
     */
    private void trySaveVariables(String taskId, Map<String, Object> variables) {
        try {
            taskService.setVariables(taskId, variables);
        } catch (Exception e) {
            log.error("[VariableCache] can't save variable to taskId: {}, variables: {}, error: {}",
                    taskId, variables, e.getMessage(), e);
        }
    }

    private void saveToBuffer(String taskId, Map<String, Object> changedVars) {
        var currentChangedVars = bufferChangedVars.get(taskId);
        if (currentChangedVars == null) {
            bufferChangedVars.put(taskId, changedVars);
        } else {
            currentChangedVars.putAll(changedVars);
        }
    }

    private String genTaskVarCacheKey(String taskId) {
        return String.format("%s::%s", CacheConstants.Name.VARIABLES, taskId);
    }

    @Override
    public void destroy() {
        executor.shutdown();
    }

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @Autowired
    public void setRedisTemplate(@Qualifier("jdkRedisTemplate") RedisTemplate<String, Object> redisTemplate) {
        this.redisUtils = new RedisUtils(CacheConstants.DPM_PREFIX, redisTemplate);
    }

    @Autowired
    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

}
