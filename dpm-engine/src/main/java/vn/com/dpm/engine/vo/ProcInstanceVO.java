package vn.com.dpm.engine.vo;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ProcInstanceVO {
    private String id;

    private String processDefId;

    private String processDefKey;

    private long procInstNumber;

    private String name;

    private List<SimpleTaskInfoVO> tasks;
}
