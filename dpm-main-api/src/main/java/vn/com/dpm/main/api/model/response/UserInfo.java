package vn.com.dpm.main.api.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserInfo {
    private String name;
    private String email;
    private String phone;
    private String role;

}
