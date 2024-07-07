package vn.com.dpm.engine.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.flowable.common.engine.impl.db.SuspensionState;

@Getter
@Setter
@Builder
@ToString
public class TaskInfoVO {

    private String id;

    private String name;

    private String dueDate;

    private String assignee;

    private String taskDefinitionKey;

    private String procInstId;

    private long procInstNumber;

    private String procDefId;

    private String owner;

    private String processName;

    private String description;

    private String sentBy;

    private String sentByTime;

    private int priority;

    private int suspensionState;

    public boolean isSuspended() {
        return suspensionState == SuspensionState.SUSPENDED.getStateCode();
    }

    public boolean isActive() {
        return suspensionState == SuspensionState.ACTIVE.getStateCode();
    }
}
