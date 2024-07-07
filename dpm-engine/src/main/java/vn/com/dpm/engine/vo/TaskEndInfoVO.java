package vn.com.dpm.engine.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskEndInfoVO {
    private String id;

    private String key;

    private String name;

    private String assignee;

    private String createdTime;

    private String endTime;

    private String dueDate;

    private String type;

    private String description;

    private String procDefId;

    private List<String> candidateGroups;
}