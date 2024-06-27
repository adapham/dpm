package vn.com.dpm.common.utils;

import java.util.*;

public final class HeaderUtils {

    private HeaderUtils() {
    }

    /**
     * Build header key with random string.
     */
    public static Map<String, List<String>> buildHeaderWithIdempotencyKey(String xMaker,
                                                                          String xChecker,
                                                                          String xBusinessDate,
                                                                          String via) {

        Map<String, List<String>> headers = new HashMap<>(8);
        if (StringUtils.hasLength(xMaker)) {
            headers.put("x-maker", Collections.singletonList(xMaker));
        }
        if (StringUtils.hasLength(xChecker)) {
            headers.put("x-checker", Collections.singletonList(xChecker));
        }
        if (StringUtils.hasLength(xBusinessDate)) {
            headers.put("x-businessdate", Collections.singletonList(xBusinessDate));
        }
        String requestKey = String.format("bpm_%s", UUID.randomUUID());
        headers.put("x-idempotencykey", Collections.singletonList(requestKey));
        headers.put("x-via", Collections.singletonList(via));
        return headers;
    }
}
