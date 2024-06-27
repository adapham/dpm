package vn.com.dpm.common.utils;


import vn.com.dpm.common.exception.NotFoundException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


public final class CollectionUtil {

    private CollectionUtil() {
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * Combine array arguments to array object.
     *
     * @param args can be collection, array, or single object.
     */
    public static Object[] combine(Object... args) {
        var combined = new ArrayList<>();
        for (var obj : args) {
            if (obj instanceof Collection<?>) {
                combined.addAll((Collection<?>) obj);
            } else if (obj.getClass().isArray()) {
                combined.addAll(Collections.singletonList(obj));
            } else {
                combined.add(obj);
            }
        }
        return combined.toArray();
    }

    /**
     * Check given collection is null or empty.
     * If true throw exception NotFoundException.
     */
    public static <T extends Collection<?>> T requireNonNullAndEmpty(T collection, String message) {
        if (isEmpty(collection)) {
            throw new NotFoundException(message);
        }
        return collection;
    }

    public static <T> String join(Collection<T> collection,
                                  Function<? super T, String> keyExtractor,
                                  String delim) {
        return join(collection.spliterator(), keyExtractor, delim);
    }

    /**
     * Join collection with keyExtractor to String with delimiter.
     *
     * @param spliterator  input.
     * @param keyExtractor mapper function to String.
     * @param delim        delimiter.
     * @return joined String.
     */
    public static <T> String join(Spliterator<T> spliterator,
                                  Function<? super T, String> keyExtractor,
                                  String delim) {
        return StreamSupport.stream(spliterator, false)
                .map(keyExtractor)
                .collect(Collectors.joining(delim));
    }

    public static <T, K, V> Map<K, V> toMap(Collection<T> collection,
                                            Function<? super T, ? extends K> keyMapper,
                                            Function<? super T, ? extends V> valueMapper) {
        return collection.stream()
                .collect(Collectors.toMap(keyMapper, valueMapper));
    }

    public static <T, K> Map<K, List<T>> groupingBy(Collection<T> collection,
                                                    Function<? super T, ? extends K> keyMapper) {
        return groupingBy(collection.spliterator(), keyMapper);
    }

    public static <T, K> Map<K, List<T>> groupingBy(Spliterator<T> spliterator,
                                                    Function<? super T, ? extends K> keyMapper) {
        return StreamSupport.stream(spliterator, false)
                .collect(Collectors.groupingBy(keyMapper));
    }

    public static <T, K> Map<K, List<T>> groupingBy(Iterator<T> iterator,
                                                    Function<? super T, ? extends K> keyMapper) {
        return streamOf(iterator)
                .collect(Collectors.groupingBy(keyMapper));
    }

    public static <T, K> Map<K, List<T>> groupingBy(Iterable<T> iterable,
                                                    Function<? super T, ? extends K> keyMapper) {
        return streamOf(iterable)
                .collect(Collectors.groupingBy(keyMapper));
    }

    /**
     * Return true if c1, c2 has common element(s).
     */
    public static boolean containAny(Collection<?> c1, Collection<?> c2) {
        return !Collections.disjoint(c1, c2);
    }

    public static <T, R> List<R> toList(Collection<T> collection, Function<T, R> func) {
        return toList(collection.spliterator(), func);
    }

    /**
     * collect to list with filter from collection.
     */
    public static <T> List<T> toFilterList(Collection<T> collection, Predicate<T> predicate) {
        return toList(collection.spliterator(), predicate);
    }

    /**
     * collect to list with filter from collection.
     */
    public static <T> List<T> toFilterList(Spliterator<T> spliterator, Predicate<T> predicate) {
        return toList(spliterator, predicate);
    }

    /**
     * collect to list with distinct by <code> keyExtractor</code>  from collection.
     */
    public static <T> List<T> toDistinctList(Collection<T> collection, Function<? super T, ?> keyExtractor) {
        return toList(collection.spliterator(), distinctByKey(keyExtractor));
    }

    /**
     * collect to list with distinct by <code> keyExtractor</code>  from spliterator.
     */
    public static <T> List<T> toDistinctList(Spliterator<T> spliterator, Function<? super T, ?> keyExtractor) {
        return toList(spliterator, distinctByKey(keyExtractor));
    }

    /**
     * collect to list with filter from spliterator.
     */
    public static <T, R> List<R> toList(Spliterator<T> spliterator, Function<T, R> func) {
        return toList(spliterator, f -> true, func);
    }

    /**
     * collect to list with filter from collection.
     */
    public static <T, R> List<R> toList(Collection<T> collection,
                                        Predicate<T> predicate,
                                        Function<T, R> func) {
        return toList(collection.spliterator(), predicate, func);
    }

    /**
     * collect to list with filter and mapper from spliterator.
     *
     * @param spliterator spliterator.
     * @param predicate   filter.
     * @param func        mapper func.
     */
    public static <T, R> List<R> toList(Spliterator<T> spliterator,
                                        Predicate<T> predicate,
                                        Function<T, R> func) {
        return spliteratorToList(func).apply(spliterator, predicate);
    }

    /**
     * collect to list with filter from collection.
     */
    public static <T> List<T> toList(Spliterator<T> spliterator,
                                     Predicate<T> predicate) {
        return StreamSupport.stream(spliterator, false)
                .filter(predicate)
                .collect(Collectors.toList());
    }

    public static <T> boolean noneMatch(Spliterator<T> spliterator,
                                        Predicate<T> predicate) {
        return StreamSupport.stream(spliterator, false)
                .noneMatch(predicate);
    }

    public static <T> boolean noneMatch(Collection<T> collection,
                                        Predicate<T> predicate) {
        return noneMatch(collection.spliterator(), predicate);
    }

    public static <T> boolean anyMatch(Spliterator<T> spliterator,
                                       Predicate<T> predicate) {
        return StreamSupport.stream(spliterator, false)
                .anyMatch(predicate);
    }

    public static <T> boolean anyMatch(Collection<T> collection,
                                       Predicate<T> predicate) {
        return anyMatch(collection.spliterator(), predicate);
    }

    public static <T> boolean allMatch(Spliterator<T> spliterator,
                                       Predicate<T> predicate) {
        return StreamSupport.stream(spliterator, false)
                .allMatch(predicate);
    }

    public static <T> boolean allMatch(Collection<T> collection,
                                       Predicate<T> predicate) {
        return allMatch(collection.spliterator(), predicate);
    }

    public static <T> Optional<T> findFirst(Spliterator<T> spliterator,
                                            Predicate<T> predicate) {
        return StreamSupport.stream(spliterator, false)
                .filter(predicate)
                .findFirst();
    }

    public static <T> Optional<T> findFirst(Spliterator<T> spliterator) {
        return StreamSupport.stream(spliterator, false)
                .findFirst();
    }

    public static <T> Optional<T> findFirst(Collection<T> collection,
                                            Predicate<T> predicate) {
        return findFirst(collection.spliterator(), predicate);
    }

    public static <T> Optional<T> findFirst(Collection<T> collection) {
        return findFirst(collection.spliterator());
    }

    /**
     * get first entry of map.
     */

    public static <K, V> Optional<Map.Entry<K, V>> findFirst(Map<K, V> map) {
        return map.entrySet().stream().findFirst();
    }

    /**
     * get first entry of map.
     */

    public static <K, V> Optional<Map.Entry<K, V>> findFirst(Map<K, V> map, Predicate<Map.Entry<K, V>> predicate) {
        return map.entrySet().stream()
                .filter(predicate)
                .findFirst();
    }

    /**
     * get first value of map
     */
    public static <V> Optional<V> firstValues(Map<?, V> map) {
        return map.values().stream().findFirst();
    }

    /**
     * get first value of map
     */
    public static <V> Optional<V> firstValues(Map<?, V> map, Predicate<V> predicate) {
        return map.values().stream()
                .filter(predicate)
                .findFirst();
    }

    /**
     * get first key of map
     */
    public static <K> Optional<K> firstKeys(Map<K, ?> map) {
        return map.keySet().stream().findFirst();
    }


    /**
     * get first key of map
     */
    public static <K> Optional<K> firstKeys(Map<K, ?> map, Predicate<K> predicate) {
        return map.keySet().stream()
                .filter(predicate)
                .findFirst();
    }

    /**
     * count by condition.
     */
    public static <T> long count(Collection<T> collection, Predicate<T> predicate) {
        return count(collection.spliterator(), predicate);
    }

    public static <T> long count(Spliterator<T> spliterator, Predicate<T> predicate) {
        return StreamSupport.stream(spliterator, false)
                .filter(predicate)
                .count();
    }

    public static <T, R> long countOn(Collection<T> collection, Function<T, R> mapperFunc) {
        return countOn(collection.spliterator(), mapperFunc);
    }


    public static <T, R> long countOn(Spliterator<T> spliterator, Function<T, R> mapperFunc) {
        return StreamSupport.stream(spliterator, false)
                .map(mapperFunc)
                .count();
    }

    public static <T> long countDistinctOn(Collection<T> collection, Predicate<T> predicate) {
        return countDistinctOn(collection.spliterator(), predicate);
    }

    public static <T> long countDistinctOn(Spliterator<T> spliterator, Predicate<T> predicate) {
        return StreamSupport.stream(spliterator, false)
                .filter(predicate)
                .distinct()
                .count();
    }

    public static <T, R> long countDistinctWith(Collection<T> collection, Function<T, R> mapperFunc) {
        return countDistinctWith(collection.spliterator(), mapperFunc);
    }

    public static <T, R> long countDistinctWith(Spliterator<T> spliterator, Function<T, R> mapperFunc) {
        return StreamSupport.stream(spliterator, false)
                .map(mapperFunc)
                .distinct()
                .count();
    }

    /**
     * collect to list with filter from collection.
     */
    private static <T, R> BiFunction<Spliterator<T>, Predicate<T>, List<R>> spliteratorToList(Function<T, R> func) {
        return (spliterator, predicate) -> StreamSupport.stream(spliterator, false)
                .filter(predicate)
                .map(func)
                .collect(Collectors.toList());
    }

    public static <T> Stream<T> streamOf(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }

    public static <T> Stream<T> streamOf(Iterator<T> iterator) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false);
    }

    public static <T> Stream<T> parallelStreamOf(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), true);
    }

    /**
     * @param keyExtractor function to get property of object by key.
     * @param <T>          type of object.
     * @return function check distinct value from keyExtractor.
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = new HashSet<>();
        return t -> seen.add(keyExtractor.apply(t));
    }

    @SafeVarargs
    public static <T> List<T> toDistinctList(Collection<T> collection, Function<? super T, ?>... keyExtractors) {
        return toList(collection.spliterator(), distinctByKeys(keyExtractors));
    }

    @SafeVarargs
    public static <T> Predicate<T> distinctByKeys(Function<? super T, ?>... keyExtractors) {
        Map<List<?>, Boolean> seen = new ConcurrentHashMap<>();

        return t -> {
            List<?> keys = Arrays.stream(keyExtractors)
                    .map(ke -> ke.apply(t))
                    .collect(Collectors.toList());

            return seen.putIfAbsent(keys, Boolean.TRUE) == null;
        };
    }
}
