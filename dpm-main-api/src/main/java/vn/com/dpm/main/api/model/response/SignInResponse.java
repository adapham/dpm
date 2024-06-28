package vn.com.dpm.main.api.model.response;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class SignInResponse implements Serializable {
    private String accessToken;
    private String refreshToken;
}