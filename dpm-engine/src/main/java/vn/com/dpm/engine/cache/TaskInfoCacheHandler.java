package vn.com.dpm.engine.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Component
public class TaskInfoCacheHandler implements TaskInfoCache {

    private final Cache<String, String> localCache;

    public TaskInfoCacheHandler() {
        localCache = CacheBuilder.newBuilder()
                .expireAfterWrite(5, TimeUnit.SECONDS)
                .build();
    }

    @Override
    public String getTempAssignee(String taskId) {
        return localCache.getIfPresent(taskId);
    }

    @Override
    public void changeAssignee(String taskId, String assignee) {
        localCache.put(taskId, assignee);
    }
}
