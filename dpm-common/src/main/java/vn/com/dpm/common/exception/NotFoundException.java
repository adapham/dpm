package vn.com.dpm.common.exception;

import vn.com.dpm.common.types.ErrorCode;

public class NotFoundException extends ErrorCodeException {

    public NotFoundException(String message) {
        super(ErrorCode.DPM_NOT_FOUND_01, message);
    }
}
