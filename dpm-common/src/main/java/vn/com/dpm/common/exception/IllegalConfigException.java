package vn.com.dpm.common.exception;


import vn.com.dpm.common.types.ErrorCode;

/**
 * @author an.cantuong
 */
public class IllegalConfigException extends ErrorCodeException {

    public IllegalConfigException(String message) {
        super(ErrorCode.DPM_INVALID_CONFIG, message);
    }

    public IllegalConfigException(Throwable cause, String message) {
        super(cause, ErrorCode.DPM_INVALID_CONFIG, message, null);
    }
}
