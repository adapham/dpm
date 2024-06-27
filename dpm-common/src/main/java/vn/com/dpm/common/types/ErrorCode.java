package vn.com.dpm.common.types;


import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public enum ErrorCode {
    DPM_NOT_FOUND_01("DPM-01", HttpStatus.NOT_FOUND, "Not found!"),
    DPM_INVALID_CONFIG("DPM-02", HttpStatus.NOT_FOUND, "Not found!");


    private String message;
    private final String error;
    private final HttpStatus httpStatus;

    ErrorCode(String error, HttpStatus status) {
        this.error = error;
        this.httpStatus = status;
    }

    ErrorCode(String error,
              HttpStatus status,
              String message) {
        this.error = error;
        this.httpStatus = status;
        this.message = message;
    }
}
