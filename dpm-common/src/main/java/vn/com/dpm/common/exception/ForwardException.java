package vn.com.dpm.common.exception;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class ForwardException extends Exception {

    private final String id;
    private final String error;
    private final String message;
    private final HttpStatus statusCode;
    private transient JsonNode description;


    public ForwardException(Throwable cause,
                            String error,
                            String message,
                            HttpStatus statusCode,
                            JsonNode description) {
        super(cause);
        this.error = error;
        this.message = message;
        this.statusCode = statusCode;
        this.description = description;
        this.id = RandomStringUtils.randomAlphabetic(5);
    }

    public ForwardException(String error,
                            String message,
                            HttpStatus statusCode,
                            JsonNode description) {
        this.error = error;
        this.message = message;
        this.statusCode = statusCode;
        this.description = description;
        this.id = RandomStringUtils.randomAlphabetic(5);
    }

    public ForwardException(String error,
                            String message,
                            HttpStatus statusCode) {
        this.error = error;
        this.message = message;
        this.statusCode = statusCode;
        this.id = RandomStringUtils.randomAlphabetic(5);
    }
}
