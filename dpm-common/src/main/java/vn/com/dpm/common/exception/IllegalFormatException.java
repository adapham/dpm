package vn.com.dpm.common.exception;

/**
 * @author an.cantuong
 */
public class IllegalFormatException extends RuntimeException {

    public IllegalFormatException(String message) {
        super(message);
    }

    public IllegalFormatException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
