package vn.com.dpm.common.utils;

import io.netty.channel.ConnectTimeoutException;
import io.netty.handler.timeout.TimeoutException;
import org.springframework.web.client.ResourceAccessException;


public class ExceptionUtils {

    private ExceptionUtils() {
    }


    /**
     * Check if exception is an uncaught exception.
     *
     * @param e exception.
     */
    public static boolean isServerUncaught(Exception e) {
        return e instanceof TimeoutException
                || e instanceof ConnectTimeoutException
                || e instanceof ResourceAccessException;
    }
}
