package vn.com.dpm.engine.api.callback;

import org.flowable.task.service.impl.persistence.entity.TaskEntity;


@FunctionalInterface
public interface AssigneeEventCallback {

    void fire(TaskEntity task, String userAssigned);
}
