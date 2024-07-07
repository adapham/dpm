package vn.com.dpm.engine.cache;


public interface TaskInfoCache {

    /**
     * Get temp assignee.
     *
     * @param taskId task id.
     */
    String getTempAssignee(String taskId);

    /**
     * Change assignee will trigger update cache.
     *
     * @param taskId   task id.
     * @param assignee assignee.
     */
    void changeAssignee(String taskId, String assignee);
}
