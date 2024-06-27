package vn.com.dpm.common.exception;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.RandomStringUtils;
import vn.com.dpm.common.types.ErrorCode;

public class ErrorCodeException extends RuntimeException {
    private final String id;
    private final String message;
    private final ErrorCode errorCode;
    private transient JsonNode description;

    public ErrorCodeException(ErrorCode errorCode) {
        this.id = RandomStringUtils.randomAlphabetic(5);
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }

    public ErrorCodeException(ErrorCode errorCode,
                              String message) {
        this.message = message;
        this.errorCode = errorCode;
        this.id = RandomStringUtils.randomAlphabetic(5);
    }

    public ErrorCodeException(Throwable cause,
                              ErrorCode errorCode,
                              String message,
                              JsonNode description) {
        super(cause);
        this.message = message;
        this.errorCode = errorCode;
        this.description = description;
        this.id = RandomStringUtils.randomAlphabetic(5);
    }

    public ErrorCodeException(ErrorCode errorCode,
                              String message,
                              JsonNode description) {
        this.message = message;
        this.errorCode = errorCode;
        this.description = description;
        this.id = RandomStringUtils.randomAlphabetic(5);
    }
}
