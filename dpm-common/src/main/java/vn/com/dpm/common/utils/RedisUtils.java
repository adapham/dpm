//package vn.com.dpm.common.utils;
//
//import org.springframework.data.redis.core.HashOperations;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.lang.NonNull;
//
//import java.time.Duration;
//import java.util.*;
//import java.util.concurrent.TimeUnit;
//import java.util.stream.Collectors;
//
//public class RedisUtils {
//
//    private final String prefix;
//    private final RedisTemplate<String, Object> redisTemplate;
//
//    /**
//     * Should define as a bean.
//     *
//     * @param redisTemplate redis template.
//     */
//    public RedisUtils(RedisTemplate<String, Object> redisTemplate) {
//        this.prefix = "";
//        this.redisTemplate = redisTemplate;
//    }
//
//    /**
//     * Should define as a bean.
//     *
//     * @param redisTemplate redis template.
//     */
//    public RedisUtils(String prefix,
//                      RedisTemplate<String, Object> redisTemplate) {
//        this.redisTemplate = redisTemplate;
//        this.prefix = prefix;
//    }
//
//    /**
//     * Get key with prefix.
//     *
//     * @param key key.
//     */
//    private String getKey(String key) {
//        return this.prefix + key;
//    }
//
//    /**
//     * Set cache expire.
//     *
//     * @param key     key.
//     * @param timeout timeout.
//     */
//    public void setExpire(@NonNull String key,
//                          @NonNull Duration timeout) {
//        if (timeout.getSeconds() > 0) this.redisTemplate.expire(getKey(key), timeout.getSeconds(), TimeUnit.SECONDS);
//    }
//
//    /**
//     * Get expire by key.
//     *
//     * @param key key.
//     * @return long.
//     */
//    public Long getExpire(@NonNull String key) {
//        return this.redisTemplate.getExpire(getKey(key), TimeUnit.SECONDS);
//    }
//
//    /**
//     * Check if key exist.
//     *
//     * @param key key.
//     * @return key.
//     */
//    public Boolean hasKey(@NonNull String key) {
//        return this.redisTemplate.hasKey(getKey(key));
//    }
//
//    /**
//     * Delete many keys.
//     *
//     * @param keys keys.
//     * @return boolean.
//     */
//    public Boolean delete(@NonNull String... keys) {
//        var mappedKeys = Arrays.stream(keys)
//                .map(this::getKey)
//                .collect(Collectors.toList());
//        return keys.length == Optional.ofNullable(this.redisTemplate.delete(mappedKeys)).orElse(-1L);
//
//    }
//
//    public Boolean delete(@NonNull String key) {
//        return this.redisTemplate.delete(getKey(key));
//    }
//
//    public Boolean deleteRawKey(@NonNull String key) {
//        return this.redisTemplate.delete(key);
//    }
//
//    /**
//     * Get value.
//     *
//     * @param key key.
//     * @return Object.
//     */
//    public Object getValue(@NonNull String key) {
//        return this.redisTemplate.opsForValue().get(getKey(key));
//    }
//
//    /**
//     * Set value.
//     *
//     * @param key   key.
//     * @param value value.
//     */
//    public void setValue(@NonNull String key,
//                         @NonNull Object value) {
//        this.redisTemplate.opsForValue().set(getKey(key), value);
//    }
//
//    /**
//     * Set value with expire time.
//     *
//     * @param key     key.
//     * @param value   value.
//     * @param timeout expire time.
//     */
//    public void setValue(@NonNull String key,
//                         @NonNull Object value,
//                         @NonNull Duration timeout) {
//        this.redisTemplate.opsForValue().set(getKey(key), value, timeout);
//    }
//
//    /**
//     * Get hash.
//     *
//     * @param key     key.
//     * @param hashKey item.
//     * @return object.
//     */
//    public Object getHash(@NonNull String key,
//                          @NonNull String hashKey) {
//        return this.redisTemplate.opsForHash().get(getKey(key), hashKey);
//    }
//
//    /**
//     * Get hash.
//     *
//     * @param key key.
//     * @return Map.
//     */
//    public Map<Object, Object> getHash(@NonNull String key) {
//        return this.redisTemplate.opsForHash().entries(getKey(key));
//    }
//
//    /**
//     * Get hash.
//     *
//     * @param key key.
//     * @return Map.k
//     */
//    public <T> Map<T, Object> getHashAsMap(@NonNull String key) {
//        HashOperations<String, T, Object> hashOperations = this.redisTemplate.opsForHash();
//        return hashOperations.entries(getKey(key));
//    }
//
//    /**
//     * Put hash.
//     *
//     * @param key key.
//     * @param map map.
//     */
//    public <T> void putHashMap(@NonNull String key,
//                               @NonNull Map<T, Object> map) {
//        HashOperations<String, T, Object> hashOperations = this.redisTemplate.opsForHash();
//        hashOperations.putAll(getKey(key), map);
//    }
//
//    /**
//     * Put hash.
//     *
//     * @param key key.
//     * @param map map.
//     */
//    public void putHash(@NonNull String key,
//                        @NonNull Map<Object, Object> map) {
//        this.redisTemplate.opsForHash().putAll(getKey(key), map);
//    }
//
//    /**
//     * Put hash.
//     *
//     * @param key     key.
//     * @param map     map.
//     * @param timeout expire.
//     */
//    public void putHash(@NonNull String key,
//                        @NonNull Map<String, Object> map,
//                        @NonNull Duration timeout) {
//        this.redisTemplate.opsForHash().putAll(getKey(key), map);
//        this.setExpire(key, timeout);
//    }
//
//    /**
//     * Put hash.
//     *
//     * @param key     key.
//     * @param hashKey item.
//     * @param value   value.
//     */
//    public void putHash(@NonNull String key,
//                        @NonNull String hashKey,
//                        @NonNull Object value) {
//        this.redisTemplate.opsForHash().put(getKey(key), hashKey, value);
//    }
//
//    /**
//     * Put hash.
//     *
//     * @param key     key.
//     * @param hashKey hashKey.
//     * @param value   value.
//     * @param timeout expire.
//     */
//    public void putHash(@NonNull String key,
//                        @NonNull String hashKey,
//                        @NonNull Object value,
//                        @NonNull Duration timeout) {
//        this.redisTemplate.opsForHash().put(getKey(key), hashKey, value);
//        this.setExpire(key, timeout);
//    }
//
//    /**
//     * Delete hash.
//     *
//     * @param key      key.
//     * @param hashKeys hash keys.
//     */
//    public void deleteHash(@NonNull String key,
//                           @NonNull Object... hashKeys) {
//        this.redisTemplate.opsForHash().delete(getKey(key), hashKeys);
//    }
//
//    /**
//     * Check if has hash key.
//     *
//     * @param key     key.
//     * @param hashKey hash key.
//     * @return true if exist.
//     */
//    public Boolean hasKeyHash(@NonNull String key,
//                              @NonNull String hashKey) {
//        return this.redisTemplate.opsForHash().hasKey(getKey(key), hashKey);
//    }
//
//    /**
//     * Get set.
//     *
//     * @param key key.
//     * @return set object.
//     */
//    public Set<Object> getSet(@NonNull String key) {
//        return this.redisTemplate.opsForSet().members(getKey(key));
//    }
//
//    /**
//     * Has key set.
//     *
//     * @param key   key.
//     * @param value value.
//     * @return true if exist.
//     */
//    public Boolean hasKeySet(@NonNull String key,
//                             @NonNull Object value) {
//        return this.redisTemplate.opsForSet().isMember(getKey(key), value);
//    }
//
//    /**
//     * Add set.
//     *
//     * @param key    key.
//     * @param values values.
//     * @return number inserted.
//     */
//    public Long addSet(@NonNull String key,
//                       @NonNull Object... values) {
//        return this.redisTemplate.opsForSet().add(getKey(key), values);
//    }
//
//    /**
//     * Add set with expire time.
//     *
//     * @param key     key.
//     * @param timeout timeout.
//     * @param values  values.
//     * @return number inserted.
//     */
//    public Long addSet(@NonNull String key,
//                       @NonNull Duration timeout,
//                       @NonNull Object... values) {
//        Long num = this.redisTemplate.opsForSet().add(getKey(key), values);
//        this.setExpire(getKey(key), timeout);
//        return num;
//    }
//
//    /**
//     * Get set size.
//     *
//     * @param key key.
//     * @return long.
//     */
//    public Long getSetSize(@NonNull String key) {
//        return this.redisTemplate.opsForSet().size(getKey(key));
//    }
//
//    /**
//     * Remove set.
//     *
//     * @param key    key.
//     * @param values values.
//     * @return long.
//     */
//    public Long removeSet(@NonNull String key,
//                          @NonNull Object... values) {
//        return this.redisTemplate.opsForSet().remove(getKey(key), values);
//    }
//
//    /**
//     * Get list.
//     *
//     * @param key   key.
//     * @param start start.
//     * @param end   end.
//     * @return list object.
//     */
//    public List<Object> getList(@NonNull String key,
//                                @NonNull Long start,
//                                @NonNull Long end) {
//        return this.redisTemplate.opsForList().range(getKey(key), start, end);
//    }
//
//    /**
//     * Get list size.
//     *
//     * @param key key.
//     * @return long.
//     */
//    public Long getListSize(@NonNull String key) {
//        return this.redisTemplate.opsForList().size(getKey(key));
//    }
//
//    /**
//     * Get list index.
//     *
//     * @param key   key.
//     * @param index index.
//     * @return object.
//     */
//    public Object getListIndex(@NonNull String key,
//                               @NonNull Long index) {
//        return this.redisTemplate.opsForList().index(getKey(key), index);
//    }
//
//    /**
//     * Put list.
//     *
//     * @param key   key.
//     * @param value value.
//     * @return long.
//     */
//    public Long pushList(@NonNull String key,
//                         @NonNull Object value) {
//        return this.redisTemplate.opsForList().rightPush(getKey(key), value);
//    }
//
//    /**
//     * Put list.
//     *
//     * @param key     key.
//     * @param value   value.
//     * @param timeout expire.
//     * @return long.
//     */
//    public Long pushList(@NonNull String key,
//                         @NonNull Object value,
//                         @NonNull Duration timeout) {
//        Long num = this.redisTemplate.opsForList().rightPush(getKey(key), value);
//        this.setExpire(key, timeout);
//        return num;
//    }
//
//    /**
//     * Put list.
//     *
//     * @param key   key.
//     * @param value value.
//     * @return long.
//     */
//    public Long pushList(@NonNull String key,
//                         @NonNull List<Object> value) {
//        return this.redisTemplate.opsForList().rightPushAll(getKey(key), value);
//    }
//
//    /**
//     * Put list.
//     *
//     * @param key     key.
//     * @param value   value.
//     * @param timeout expire.
//     * @return long.
//     */
//    public Long pushList(@NonNull String key,
//                         @NonNull List<Object> value,
//                         @NonNull Duration timeout) {
//        Long num = this.redisTemplate.opsForList().rightPushAll(getKey(key), value);
//        this.setExpire(key, timeout);
//        return num;
//    }
//
//    /**
//     * Update list index.
//     *
//     * @param key   key.
//     * @param index index.
//     * @param value value.
//     */
//    public void updateListIndex(@NonNull String key,
//                                @NonNull Long index,
//                                @NonNull Object value) {
//        this.redisTemplate.opsForList().set(getKey(key), index, value);
//    }
//
//    /**
//     * Remove list.
//     *
//     * @param key   key.
//     * @param count count.
//     * @param value value.
//     * @return long.
//     */
//    public Long removeList(@NonNull String key,
//                           @NonNull Long count,
//                           @NonNull Object value) {
//        return this.redisTemplate.opsForList().remove(getKey(key), count, value);
//    }
//
//    /**
//     * Get keys by pattern.
//     *
//     * @param pattern query.
//     * @return Set<String>.
//     */
//    public Set<String> getKeys(@NonNull String pattern) {
//        return redisTemplate.keys(pattern);
//    }
//
//    /**
//     * Increment by value.
//     *
//     * @param key       key.
//     * @param increment value to increment.
//     * @return next value.
//     */
//    public long incrBy(@NonNull String key, int increment) {
//        var value = redisTemplate.boundValueOps(getKey(key)).increment(increment);
//        return value != null ? value : 0L;
//    }
//}