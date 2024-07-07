package vn.com.dpm.engine.cache;

import java.util.Map;


public interface VariableCache {

    /**
     * Save changed variable.
     *
     * @param taskId taskId.
     * @param vars   variables need to check change and save.
     */
    void saveVariables(String taskId, Map<String, Object> vars);

    /**
     * Save single variable without check change.
     * Save immediately to db.
     *
     * @param taskId  taskId.
     * @param varName variable name.
     * @param value   variable value.
     */
    void saveVariable(String taskId, String varName, Object value);

    /**
     * Get variable.
     *
     * @param taskId taskId.
     * @return variables as map.
     */
    Map<String, Object> getVariables(String taskId);

    /**
     * Clear local cache, redis cache by taskId.
     *
     * @param taskId taskId.
     */
    void clearCache(String taskId);

    /**
     * Flush buffer to db.
     */
    void flushBuffer();
}
