package vn.com.dpm.common.variables.serializer;

import com.fasterxml.jackson.databind.JsonNode;


public interface VariableSerializer<V> {

    /**
     * Class of value.
     *
     * @return class.
     */
    Class<V> typeOf();

    /**
     * Type as string.
     */
    default String type() {
        return typeOf().getName();
    }

    /**
     * Serialize from value to Json node.
     */
    JsonNode serialize(Object value);

    /**
     * Convert from object to V.
     */
    V deserialize(Object value);
}