package vn.com.dpm.common.variables;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import vn.com.dpm.common.utils.ObjectUtils;
import vn.com.dpm.common.utils.ParserUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author an.cantuong
 */
public class VariableMap<V> extends HashMap<String, V> {

    public VariableMap() {
    }

    public VariableMap(int initialCapacity) {
        super(initialCapacity);
    }

    public VariableMap(Map<? extends String, ? extends V> m) {
        super(m);
    }

    public VariableMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public static VariableMap<Object> empty() {
        return new VariableMap<>(5);
    }

    public static VariableMap<Object> of(Map<String, Object> map) {
        return new VariableMap<>(map);
    }

    /**
     * Put variable.
     */
    public void put(UserDefinedVariable variable, V value) {
        this.put(variable.varName(), value);
    }

    /**
     * Put variable.
     */
    public void putIfHasValue(UserDefinedVariable variable, V value) {
        this.putIfHasValue(variable.varName(), value);
    }

    /**
     * Put variable.
     */
    public void putIfHasValue(String key, V value) {
        if (!ObjectUtils.isNullOrEmpty(value)) {
            this.put(key, value);
        }
    }

    public void putIf(String key, Predicate<String> predicate, V value) {
        if (predicate.test(key)) {
            this.put(key, value);
        }
    }

    /**
     * Get variable.
     */
    public V get(UserDefinedVariable variable) {
        return this.get(variable.varName());
    }

    /**
     * Get variable or return default value.
     */
    public V get(UserDefinedVariable variable, V defaultValue) {
        return getOrDefault(variable.varName(), defaultValue);
    }

    /**
     * Get variable or return default value.
     */
    public V get(String variable, V defaultValue) {
        return getOrDefault(variable, defaultValue);
    }

    public V getOrDefault(String variable, V defaultValue) {
        var value = this.get(variable);
        return Objects.isNull(value) ? defaultValue : value;
    }

    /**
     * Get variable as text.
     */
    public String getAsStr(UserDefinedVariable variable, V defaultValue) {
        return String.valueOf(getOrDefault(variable.varName(), defaultValue));
    }

    /**
     * Get var as text.
     */
    public String getAsStr(UserDefinedVariable variable) {
        return getAsStr(variable.varName());
    }

    /**
     * Get var as text.
     */
    public String getAsStr(Object key) {
        var value = get(key);
        return Objects.isNull(value) ? null : String.valueOf(value);
    }

    /**
     * get variable as entity using class cast.
     */
    public <T> T getAsEntity(UserDefinedVariable variable, Class<T> clazz) {
        return clazz.cast(get(variable));
    }

    /**
     * Get var as boolean.
     */
    public Boolean getAsBool(UserDefinedVariable variable) {
        return Boolean.valueOf(getAsStr(variable));
    }

    /**
     * Get var value as enum.
     * this method using valueOf method.
     *
     * @param variable variable name.
     */
    public <T extends Enum<T>> T getVariableAsEnum(UserDefinedVariable variable,
                                                   Class<T> enumClazz) {
        try {
            var enumStr = getAsStr(variable);
            return Enum.valueOf(enumClazz, enumStr);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Get var value as enum.
     *
     * @param variable variable name.
     * @param resolver function convert string to enum.
     */
    public <T extends Enum<T>> T getVariableAsEnum(UserDefinedVariable variable,
                                                   Function<String, T> resolver) {
        var enumStr = getAsStr(variable);
        return resolver.apply(enumStr);
    }

    public <T> T convertValue(UserDefinedVariable variable) {
        var mapper = new ObjectMapper();
        return convertValue(variable, mapper);
    }

    public Optional<BigDecimal> getAsBigDecimal(String varName) {
        return ParserUtils.parseBigDecimal(getAsStr(varName));
    }

    public Optional<BigDecimal> getAsBigDecimal(UserDefinedVariable variable) {
        return getAsBigDecimal(variable.varName());
    }

    public <X extends Throwable> BigDecimal getAsBigDecimal(UserDefinedVariable variable,
                                                            Supplier<X> errorSupplier) throws X {
        return getAsBigDecimal(variable.varName())
                .orElseThrow(errorSupplier);
    }

    /**
     * convert value to object which is generic type.
     * <p>
     * <strong>This method may convert to wrong bound type parameter because of type erasing at runtime.</strong>
     *
     * @param <T> Bound Type
     */
    //FIXME: This method may convert to wrong bound type parameter because of type erasing at runtime.
    public <T> T convertValue(UserDefinedVariable variable, ObjectMapper mapper) {
        return mapper.convertValue(get(variable), new TypeReference<>() {
        });
    }

    public <T> T convertValue(UserDefinedVariable variable, Class<T> clazz) {
        var mapper = new ObjectMapper();
        return convertValue(mapper, variable, clazz);
    }

    public <T> T convertValue(ObjectMapper mapper,
                              UserDefinedVariable variable,
                              Class<T> clazz) {
        return mapper.convertValue(get(variable), clazz);
    }

    public <T> T convertValue(ObjectMapper mapper,
                              String variable,
                              Class<T> clazz) {
        return mapper.convertValue(get(variable), clazz);
    }

    /**
     * Check contain key.
     */
    public boolean containsKey(UserDefinedVariable variable) {
        return this.containsKey(variable.varName());
    }

    /**
     * Set as null variable.
     */
    public void clear(UserDefinedVariable variable) {
        put(variable, null);
    }

    /**
     * Set as null variables.
     */
    public void clear(UserDefinedVariable... variables) {
        for (var varName : variables) {
            clear(varName);
        }
    }

    /**
     * Set as null variable.
     */
    public void clear(String variable) {
        put(variable, null);
    }

    /**
     * Set as null variables.
     */
    public void clear(String... variables) {
        for (var varName : variables) {
            clear(varName);
        }
    }

    /**
     * Set as null list variables.
     */
    public void clear(Collection<UserDefinedVariable> variables) {
        for (var varName : variables) {
            clear(varName);
        }
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
