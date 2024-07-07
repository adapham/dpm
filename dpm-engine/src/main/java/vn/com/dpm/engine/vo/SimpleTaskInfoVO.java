package vn.com.dpm.engine.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SimpleTaskInfoVO {
    private String taskId;

    private String taskName;

    private String taskKey;

    private String formId;
}
