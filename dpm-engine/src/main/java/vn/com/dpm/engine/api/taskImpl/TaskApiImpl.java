package vn.com.dpm.engine.api.taskImpl;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.TaskService;
import org.flowable.engine.impl.util.TaskHelper;
import org.flowable.task.service.delegate.DelegateTask;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.com.dpm.common.constants.SystemConstants;
import vn.com.dpm.engine.api.TaskApi;
import vn.com.dpm.engine.api.callback.AssigneeEventCallback;
import vn.com.dpm.engine.cache.TaskInfoCache;
import vn.com.dpm.engine.cache.VariableCache;
import vn.com.dpm.engine.vo.TaskOwnerInfoVO;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TaskApiImpl implements TaskApi {

    private TaskService taskService;
    private VariableCache variableCache;
    private TaskInfoCache taskInfoCache;

    @Override
    public void completeTask(String taskId, Map<String, Object> vars) {
        // Flush buffer.
        variableCache.flushBuffer();

        // Complete task.
        taskService.complete(taskId, vars);

        // Clear variable cache.
        variableCache.clearCache(taskId);
    }

    @Override
    public Map<String, Object> getVariables(String taskId) {
        return variableCache.getVariables(taskId);
    }

    @Override
    public void saveVariables(String taskId, Map<String, Object> vars) {
        variableCache.saveVariables(taskId, vars);
    }

    @Override
    public List<TaskOwnerInfoVO> getTaskOwnerInfo(String taskId) {
        return taskService.getIdentityLinksForTask(taskId).stream()
                .filter(info -> "assignee".equalsIgnoreCase(info.getType()))
                .map(info -> {
                    var resp = new TaskOwnerInfoVO();
                    resp.setTaskId(info.getTaskId());
                    resp.setType(info.getType());
                    resp.setUsername(info.getUserId());
                    return resp;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void setAssignee(String taskId, String toUser) {
        taskService.setAssignee(taskId, toUser);

        // Update cache
        taskInfoCache.changeAssignee(taskId, toUser);
    }

    @Override
    public void setAssignee(String assignee,
                            DelegateTask delegateTask,
                            AssigneeEventCallback callback) {
        var task = (TaskEntity) delegateTask;
        TaskHelper.changeTaskAssignee(task, assignee);
        // Update cache
        taskInfoCache.changeAssignee(delegateTask.getId(), assignee);
        // Fire event
        callback.fire(task, assignee);
    }

    @Override
    public void complete(String taskId,
                         Map<String, Object> variables,
                         Map<String, Object> transientVariables) {
        // Flush buffer.
        variableCache.flushBuffer();

        // Complete task.
        taskService.complete(taskId, variables, transientVariables);

        // Clear variable cache.
        variableCache.clearCache(taskId);
    }

    @Override
    public void claim(String taskId, String userId) {
        var start = System.currentTimeMillis();
        taskService.claim(taskId, userId);
        log.info("Wait claim engine task {}: {} ms", taskId, System.currentTimeMillis() - start);

        // Update cache
        start = System.currentTimeMillis();
        taskInfoCache.changeAssignee(taskId, userId);
        log.info("Wait set assignee cache task {}: {} ms", taskId,System.currentTimeMillis() - start);
    }

    @Override
    public void unClaim(String taskId) {
        taskService.unclaim(taskId);

        // Update cache
        taskInfoCache.changeAssignee(taskId, SystemConstants.ASSIGNEE_UNASSIGNED);
    }

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @Autowired
    public void setVariableCache(VariableCache variableCache) {
        this.variableCache = variableCache;
    }

    @Autowired
    public void setTaskInfoCache(TaskInfoCache taskInfoCache) {
        this.taskInfoCache = taskInfoCache;
    }
}
