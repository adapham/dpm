package vn.com.dpm.engine.api;

import org.apache.commons.codec.binary.Hex;
import org.flowable.task.service.delegate.DelegateTask;
import vn.com.dpm.engine.api.callback.AssigneeEventCallback;
import vn.com.dpm.engine.vo.TaskOwnerInfoVO;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public interface TaskApi {
    /**
     * Complete a task, routing to next task.
     *
     * @param taskId task id.
     * @param vars   vars.
     */
    void completeTask(String taskId, Map<String, Object> vars);

    /**
     * Get all variable include task vars, global vars.
     *
     * @param taskId task id.
     */
    Map<String, Object> getVariables(String taskId);

    /**
     * Save vars.
     * If var not exist, will create new with global scope.
     *
     * @param taskId taskId.
     * @param vars   var-name, value map.
     */
    void saveVariables(String taskId, Map<String, Object> vars);

    /**
     * Get IdentityLinkInfo of taskId
     *
     * @param taskId taskId
     * @return list of identityLinkInfo
     */
    List<TaskOwnerInfoVO> getTaskOwnerInfo(String taskId);

    /**
     * Set assignee to a task.
     */
    void setAssignee(String taskId, String toUser);

    /**
     * Set assignee to a task.
     */
    void setAssignee(String toUser, DelegateTask delegateTask, AssigneeEventCallback callback);

    /**
     * Complete a task.
     */
    void complete(String taskId, Map<String, Object> variables, Map<String, Object> transientVariables);

    /**
     * Make a claim decision.
     */
    void claim(String taskId, String userId);

    /**
     * Make a unClaim decision.
     */
    void unClaim(String taskId);

    /**
     * Generate task id from task definition key, process definition id.
     */
    static String generateServiceTaskId(String taskKey, String procId) {
        var taskIdHex = Hex.encodeHexString(taskKey.getBytes(StandardCharsets.UTF_8));
        return String.format("%s-%s", procId, taskIdHex);
    }
}
