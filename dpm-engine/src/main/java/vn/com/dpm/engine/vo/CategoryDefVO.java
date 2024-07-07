package vn.com.dpm.engine.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDefVO {

    private String category;

    private List<ProcessDefVO> lstProcess;
}
