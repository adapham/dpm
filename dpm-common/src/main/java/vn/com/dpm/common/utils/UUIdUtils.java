package vn.com.dpm.common.utils;

import java.util.UUID;

public final class UUIdUtils {

    private UUIdUtils() {
    }

    public static String generateSimpleUUID() {
        return UUID.randomUUID().toString();
    }
}
