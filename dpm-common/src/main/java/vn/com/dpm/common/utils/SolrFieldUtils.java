//package vn.com.dpm.common.utils;
//
//import org.apache.solr.common.SolrDocument;
//
//public final class SolrFieldUtils {
//
//    public static final String KEY_VALUE_FORMAT = "%s:%s";
//    public static final String KEY_VALUE_FORMAT_STRICT = "%s:\"%s\"";
//    public static final String SELECT_WILD_CAST = "*";
//
//    private SolrFieldUtils() {
//    }
//
//    /**
//     * Get field value as string.
//     */
//    public static String getFieldValueAsStr(SolrDocument doc, String fieldName) {
//        return String.valueOf(doc.getFieldValue(fieldName));
//    }
//
//    /**
//     * Get field value as string.
//     */
//    public static String getFieldValueAsStr(SolrDocument doc, String fieldName, String defaultValue) {
//        var value = doc.getFieldValue(fieldName);
//        return value == null ? defaultValue : String.valueOf(value);
//    }
//
//    /**
//     * Get field value and parse to long.
//     */
//    public static Long getFieldValueAsLong(SolrDocument doc, String fieldName) {
//        return ParserUtils.parseLong(doc.getFieldValue(fieldName)).orElse(0L);
//    }
//
//    /**
//     * Set field value with normal format.
//     *
//     * @param fieldName field name.
//     * @param value     value.
//     */
//    public static String setFieldValue(String fieldName, Object value) {
//        return String.format(KEY_VALUE_FORMAT, fieldName, value);
//    }
//
//    /**
//     * Auto wrap field value with quote ".
//     *
//     * @param fieldName field name.
//     * @param value     value.
//     */
//    public static String setFieldValueWithQuote(String fieldName, Object value) {
//        return String.format(KEY_VALUE_FORMAT_STRICT, fieldName, value);
//    }
//
//    /**
//     * Get field value and parse to int.
//     */
//    public static int getFieldValueAsInt(SolrDocument doc, String fieldName, int defaultValue) {
//        return ParserUtils.parseInt(getFieldValueAsStr(doc, fieldName)).orElse(defaultValue);
//    }
//}
