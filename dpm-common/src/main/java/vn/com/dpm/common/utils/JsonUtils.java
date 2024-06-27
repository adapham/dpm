package vn.com.dpm.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.StreamSupport;

@Slf4j
public class JsonUtils {

    private static final String NOT_FOUND_MSG = "{} not found";

    private JsonUtils() {
    }

    /**
     * Get int val from json node.
     *
     * @param jsonNode     json node.
     * @param property     property.
     * @param defaultValue def.
     * @return int.
     */
    public static int getInt(JsonNode jsonNode,
                             String property,
                             int defaultValue) {
        try {
            return jsonNode.get(property).asInt();
        } catch (Exception e) {
            log.debug(NOT_FOUND_MSG, property);
        }
        return defaultValue;
    }

    /**
     * /**
     * Get int val from json node.
     *
     * @param jsonNode     json node.
     * @param property     property.
     * @param defaultValue def.
     * @return int.
     */
    public static double getDouble(JsonNode jsonNode,
                                   String property,
                                   double defaultValue) {
        try {
            return jsonNode.get(property).asDouble();
        } catch (Exception e) {
            log.debug(NOT_FOUND_MSG, property);
        }
        return defaultValue;
    }

    /**
     * /**
     * Get int val from json node.
     *
     * @param jsonNode json node.
     * @param property property.
     * @return int.
     */
    public static double getDouble(JsonNode jsonNode,
                                   String property) {
        try {
            return jsonNode.get(property).asDouble();
        } catch (Exception e) {
            log.debug(NOT_FOUND_MSG, property);
        }
        return -1D;
    }

    /**
     * Get long val from json node.
     *
     * @param jsonNode     json node.
     * @param property     property.
     * @param defaultValue def.
     * @return long.
     */
    public static long getLong(JsonNode jsonNode,
                               String property,
                               int defaultValue) {
        try {
            return jsonNode.get(property).asLong();
        } catch (Exception e) {
            log.debug(NOT_FOUND_MSG, property);
        }
        return defaultValue;
    }

    /**
     * Get int val from json node.
     *
     * @param jsonNode json node.
     * @param property property.
     * @return int.
     */
    public static int getInt(JsonNode jsonNode, String property) {
        return getInt(jsonNode, property, -1);
    }

    /**
     * Get long val from json node.
     *
     * @param jsonNode json node.
     * @param property property.
     * @return long.
     */
    public static long getLong(JsonNode jsonNode, String property) {
        return getLong(jsonNode, property, -1);
    }


    /**
     * Get text from json node.
     *
     * @param jsonNode     json node.
     * @param property     property.
     * @param defaultValue def.
     * @return String.
     */
    public static String getText(JsonNode jsonNode,
                                 String property,
                                 String defaultValue) {
        try {
            return jsonNode.get(property).asText();
        } catch (Exception e) {
            log.debug(NOT_FOUND_MSG, property);
        }
        return defaultValue;
    }

    /**
     * Get text from json node.
     *
     * @param jsonNode json node.
     * @param property property.
     * @return String.
     */
    public static String getText(JsonNode jsonNode, String property) {
        return getText(jsonNode, property, "");
    }

    /**
     * Get text from json node.
     *
     * @param jsonNode json node.
     * @param path     path of json: /foo/bar
     * @return String.
     */
    public static String getTextWithPath(JsonNode jsonNode, String path) {
        return getTextWithPath(jsonNode, path, "");
    }

    /**
     * Get text from json node.
     *
     * @param jsonNode     json node.
     * @param path         path.
     * @param defaultValue def.
     * @return String.
     */
    public static String getTextWithPath(JsonNode jsonNode,
                                         String path,
                                         String defaultValue) {
        try {
            return jsonNode.at(path).asText();
        } catch (Exception e) {
            log.debug(NOT_FOUND_MSG, path);
        }
        return defaultValue;
    }

    /**
     * Get obj from json node.
     *
     * @param jsonNode json node.
     * @param property property.
     * @return String.
     */
    public static Optional<JsonNode> getByPropertyOptional(JsonNode jsonNode, String property) {
        try {
            return Optional.of(jsonNode.get(property));
        } catch (Exception e) {
            log.debug(NOT_FOUND_MSG, property);
        }
        return Optional.empty();
    }

    /**
     * Get obj from json node.
     *
     * @param jsonNode json node.
     * @param property property.
     * @return String.
     */
    public static JsonNode getByProperty(JsonNode jsonNode, String property) {
        try {
            return jsonNode.get(property);
        } catch (Exception e) {
            log.debug(NOT_FOUND_MSG, property);
        }
        return null;
    }

    /**
     * Get obj from json node.
     *
     * @param jsonNode json node.
     * @param property property.
     * @return json node.
     */
    public static JsonNode getByPropertyAsArrayNode(JsonNode jsonNode, String property) {
        try {
            return jsonNode.get(property);
        } catch (Exception e) {
            log.debug(NOT_FOUND_MSG, property);
        }
        return JsonNodeFactory.instance.arrayNode();
    }

    /**
     * Get by index from json node.
     *
     * @param jsonNode json node.
     * @param index    index.
     * @return String.
     */
    public static JsonNode getByIndex(JsonNode jsonNode, int index) {
        try {
            return jsonNode.get(index);
        } catch (Exception e) {
            log.error("Not found property with index {}", index);
        }
        return null;
    }

    /**
     * Get boolean.
     *
     * @param jsonNode json node.
     * @param property property.
     * @return boolean.
     */
    public static boolean getBoolean(JsonNode jsonNode, String property) {
        try {
            return jsonNode.get(property).asBoolean();
        } catch (Exception e) {
            log.debug(NOT_FOUND_MSG, property);
        }
        return false;
    }

    /**
     * Set map value to object node.
     *
     * @param node node.
     * @param map  map.
     */
    public static void setValueToObjectNode(ObjectNode node,
                                            Map<String, Object> map) {
        map.forEach((key, value) -> setValueToObjectNode(node, key, value));
    }

    /**
     * Set map value to object node.
     *
     * @param node       node.
     * @param properties properties.
     */
    public static void setValueToObjectNode(ObjectNode node, Properties properties) {
        properties.forEach((key, value) -> setValueToObjectNode(node, String.valueOf(key), value));
    }

    /**
     * Set value to node.
     *
     * @param node  node.
     * @param key   key.
     * @param value value.
     */
    public static void setValueToObjectNode(ObjectNode node,
                                            String key,
                                            Object value) {
        if (value instanceof Integer) {
            node.put(key, (int) value);
        } else if (value instanceof String) {
            node.put(key, (String) value);
        } else if (value instanceof JsonNode) {
            node.set(key, (JsonNode) value);
        }
    }

    /**
     * Set value to array node.
     *
     * @param node  node.
     * @param value value.
     */
    public static void setValueToArrayNode(ArrayNode node, Object value) {
        if (value instanceof Integer) {
            node.add((int) value);
        } else if (value instanceof String) {
            node.add((String) value);
        } else if (value instanceof Float) {
            node.add((float) value);
        } else if (value instanceof ArrayNode) {
            node.addAll((ArrayNode) value);
        } else if (value instanceof ObjectNode) {
            node.add((JsonNode) value);
        }
    }

    /**
     * Put value to node if has content.
     *
     * @param node      node.
     * @param fieldName key.
     * @param value     value.
     */
    public static void putIfHasValue(ObjectNode node,
                                     String fieldName,
                                     String value) {
        if (!StringUtils.isEmpty(value)) node.put(fieldName, value);
    }

    /**
     * Put value to node if value not empty or null.
     *
     * @param node      node.
     * @param fieldName key.
     * @param value     value.
     */
    public static void putIfHasValue(ObjectNode node,
                                     String fieldName,
                                     JsonNode value) {
        if (Objects.nonNull(value) && !value.isEmpty()) node.set(fieldName, value);
    }

    /**
     * Get as text.
     *
     * @param node     node.
     * @param property property.
     * @return node.
     */
    public static String toText(JsonNode node, String property) {
        try {
            return node.get(property).toString();
        } catch (Exception e) {
            return StringUtils.EMPTY;
        }
    }

    /**
     * Write obj to Json.
     *
     * @param mapper mapper.
     * @param obj    obj.
     * @return string.
     */
    public static String toJsonStr(ObjectMapper mapper, Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.error("Error write object to json str", e);
            return StringUtils.EMPTY;
        }
    }

    /**
     * Write obj to JsonNode.
     *
     * @param mapper mapper.
     * @param obj    obj.
     * @return JsonNode.
     */
    public static JsonNode toJsonNode(ObjectMapper mapper, Object obj) {
        try {
            return mapper.convertValue(obj, JsonNode.class);
        } catch (Exception e) {
            log.error("Error convert object to jsonNode", e);
            return mapper.createObjectNode();
        }
    }

    /**
     * Write obj to JsonNode.
     *
     * @param mapper mapper.
     * @param obj    obj.
     * @return JsonNode.
     */
    public static <X extends Throwable> JsonNode toJsonNode(ObjectMapper mapper,
                                                            String obj,
                                                            Supplier<? extends X> exceptionSupplier) throws X {
        try {
            return mapper.readTree(obj);
        } catch (Exception e) {
            throw exceptionSupplier.get();
        }
    }

    public static JsonNode toJsonOrElseRawValue(ObjectMapper mapper, String json) {
        try {
            return mapper.readTree(json);
        } catch (JsonProcessingException e) {
            return JsonNodeFactory.instance.textNode(json); // return raw value
        }
    }

    public static boolean arrayContainsString(ArrayNode strArrayNode, String value) {
        return StreamSupport.stream(Objects.requireNonNull(strArrayNode).spliterator(), false)
                .anyMatch(node -> node.asText().equals(value));
    }

    /**
     * @return true if the two specified collections have any String elements in common.
     */
    public static boolean joint(ArrayNode strArrayNode, Collection<String> value) {
        for (var node : strArrayNode) {
            if (value.contains(node.asText())) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return true if the two specified collections have no String elements in common.
     */
    public static boolean disjoint(ArrayNode strArrayNode, Collection<String> value) {
        return !joint(strArrayNode, value);
    }

    /**
     * parse string to object.
     *
     * @param mapper mapper.
     * @return T.
     */
    public static <T> T readValue(ObjectMapper mapper,
                                  Object obj,
                                  Class<T> clazz) {
        try {
            return mapper.readValue(String.valueOf(obj), clazz);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * parse string to object.
     *
     * @param mapper mapper.
     * @return T.
     */
    public static <T> T readValueReference(ObjectMapper mapper,
                                           Object obj,
                                           TypeReference<T> valueTypeRef) {
        try {
            return mapper.readValue(String.valueOf(obj), valueTypeRef);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * parse string to object.
     *
     * @param mapper mapper.
     */
    public static Object readValueAsObject(ObjectMapper mapper, Object input) {
        try {
            return mapper.readValue(String.valueOf(input), new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * parse string to object or else raw value as String.
     *
     * @param mapper mapper.
     */
    public static Object readValueAsObjectOrElseRaw(ObjectMapper mapper, String raw) {
        try {
            return mapper.readValue(raw, new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return raw;
    }

    /**
     * Convert object to TypeReference.
     *
     * @param mapper mapper.
     * @param obj    object.
     * @param <T>    TypeReference.
     * @return T.
     */
    public static <T> T convertValue(ObjectMapper mapper, Object obj) {
        return mapper.convertValue(obj, new TypeReference<>() {
        });
    }

    /**
     * Convert value to object.
     */
    public static <T> T convertValue(ObjectMapper mapper,
                                     Object obj,
                                     Class<T> clazz) {
        return mapper.convertValue(obj, clazz);
    }

    /**
     * Check json node is null or empty.
     *
     * @param node json node.
     */
    public static boolean isNullOrEmpty(JsonNode node) {
        return isNull(node) || node.isEmpty();
    }

    /**
     * Check json node is not empty.
     *
     * @param node json node.
     */
    public static boolean notEmpty(JsonNode node) {
        return !isNullOrEmpty(node);
    }

    /**
     * Check json node is null or is {@link com.fasterxml.jackson.databind.node.NullNode}.
     *
     * @param node json node.
     */
    public static boolean isNull(JsonNode node) {
        return node == null || node.isNull();
    }

    /**
     * Check json node is not null or is not {@link com.fasterxml.jackson.databind.node.NullNode}.
     *
     * @param node json node.
     */
    public static boolean nonNull(JsonNode node) {
        return node != null && !node.isNull();
    }

    /**
     * check jsonNode is arrayNode and it has only one record.
     *
     * @param arrayNode arrayNode
     * @return true if arrayNode has only one.
     */
    public static boolean hasOnlyOne(JsonNode arrayNode) {
        return nonNull(arrayNode) && arrayNode.isArray() && arrayNode.size() == 1;
    }

    /**
     * collect stream to arrayNode
     *
     * @return ArrayNodeCollector
     */
    public static ArrayNodeCollector collectToArrayNode() {
        return new ArrayNodeCollector();
    }

    public static class ArrayNodeCollector implements Collector<JsonNode, ArrayNode, ArrayNode> {

        @Override
        public Supplier<ArrayNode> supplier() {
            return JsonNodeFactory.instance::arrayNode;
        }

        @Override
        public BiConsumer<ArrayNode, JsonNode> accumulator() {
            return ArrayNode::add;
        }

        @Override
        public BinaryOperator<ArrayNode> combiner() {
            return ArrayNode::addAll;
        }

        @Override
        public Function<ArrayNode, ArrayNode> finisher() {
            return accumulator -> accumulator;
        }

        @Override
        public Set<Characteristics> characteristics() {
            return EnumSet.of(Characteristics.UNORDERED);
        }
    }
}