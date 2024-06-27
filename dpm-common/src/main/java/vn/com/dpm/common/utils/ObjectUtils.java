package vn.com.dpm.common.utils;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;


@Slf4j
public final class ObjectUtils {

    private ObjectUtils() {
    }

    /**
     * Check null or empty.
     *
     * @param obj obj.
     * @param <T> T.
     * @return true if null or empty.
     */
    public static <T> boolean isNullOrEmpty(T obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof Map) {
            return ((Map<?, ?>) obj).isEmpty();
        }
        if (obj instanceof Collection) {
            return ((Collection<?>) obj).isEmpty();
        }
        if (obj instanceof String) {
            return StringUtils.isEmpty(String.valueOf(obj));
        }
        if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        }
        if (obj instanceof Optional) {
            return ((Optional<?>) obj).isEmpty();
        }
        return false;
    }

    public static <T> void setIfHasValue(T value, Consumer<T> setConsumer) {
        if (!isNullOrEmpty(value)) {
            setConsumer.accept(value);
        }
    }

    public static <T extends JsonNode> void setIfHasValue(T value, Consumer<T> setConsumer) {
        if (JsonUtils.isNull(value)) {
            setConsumer.accept(value);
        }
    }

    public static boolean equalsNonNull(Object a, Object b) {
        if (a == null || b == null)
            return false;
        return a.equals(b);
    }
}
