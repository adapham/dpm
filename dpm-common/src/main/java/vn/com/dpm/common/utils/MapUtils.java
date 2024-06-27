package vn.com.dpm.common.utils;

import java.util.Map;
import java.util.Objects;

/**
 * @author an.cantuong
 */
public final class MapUtils {

    private MapUtils() {
    }

    /**
     * Put value to map if value not empty or null.
     *
     * @param fields    map.
     * @param fieldName key.
     * @param value     value.
     */
    public static void putIfHasValue(Map<String, Object> fields,
                                     String fieldName,
                                     Object value) {
        if (!ObjectUtils.isNullOrEmpty(value)) {
            fields.put(fieldName, value);
        }
    }

    /**
     * Get value as string.
     *
     * @param fields    fields.
     * @param fieldName fieldName.
     * @return resp as string.
     */
    public static String getValueAsStr(Map<String, Object> fields, String fieldName) {
        var result = fields.get(fieldName);
        return Objects.isNull(result) ? null : String.valueOf(result);
    }

    /**
     * Get value as string with default value..
     *
     * @param fields       fields.
     * @param fieldName    fieldName.
     * @param defaultValue defaultValue.
     * @return resp as string.
     */
    public static String getValueAsStrOrDefault(Map<String, Object> fields,
                                                String fieldName,
                                                String defaultValue) {
        var result = fields.get(fieldName);
        return Objects.isNull(result) ? defaultValue : String.valueOf(result);
    }
}
