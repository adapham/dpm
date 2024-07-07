package vn.com.dpm.common.locks;

import com.google.common.base.MoreObjects;
import com.google.common.collect.MapMaker;
import com.google.common.math.IntMath;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;


public abstract class Striped<L> {

    //2^31 > max value of integer.
    private static final int MAX_OF_STRIPS = 30;

    private Striped() {
    }

    public abstract L get(Object key);

    public abstract int size();

    public abstract L getAt(int index);


    /**
     * Using reentrant lock.
     *
     * @param stripes number of stripes.
     */
    public static Striped<Lock> simple(int stripes) {
        return new SimpleStriped<>(stripes, ReentrantLock::new);
    }

    /**
     * Using reentrant lock.
     *
     * @param stripes number of stripes.
     */
    public static Striped<Lock> lazy(int stripes) {
        return new LazyStriped<>(stripes, ReentrantLock::new);
    }

    /**
     * Using reentrant readwrite lock.
     *
     * @param stripes number of stripes.
     */
    public static Striped<ReadWriteLock> readWriteLock(int stripes) {
        return new LazyStriped<>(stripes, ReentrantReadWriteLock::new);
    }

    /**
     * Using reentrant readwrite lock.
     *
     * @param stripes number of stripes.
     */
    public static Striped<ReadWriteLock> lazyReadWriteLock(int stripes) {
        return new LazyStriped<>(stripes, ReentrantReadWriteLock::new);
    }

    /**
     * Using semaphores.
     */
    public static Striped<Semaphore> semaphore(int stripes, int permits) {
        return new SimpleStriped<>(stripes, () -> new Semaphore(permits, false));
    }

    /**
     * Using semaphores.
     */
    public static Striped<Semaphore> lazySemaphore(int stripes, int permits) {
        return new LazyStriped<>(stripes, () -> new Semaphore(permits, false));
    }

    private static class SimpleStriped<L> extends PowerOfTwoStriped<L> {

        private final Object[] arr;

        private SimpleStriped(int stripes, Supplier<L> supplier) {
            super(stripes);

            this.arr = new Object[numberOfLocks];
            for (var i = 0; i < numberOfLocks; i++) {
                arr[i] = supplier.get();
            }
        }

        @Override
        public int size() {
            return arr.length;
        }

        @Override
        @SuppressWarnings("unchecked")
        public L getAt(int index) {
            return (L) arr[index];
        }
    }

    private static class LazyStriped<L> extends PowerOfTwoStriped<L> {
        protected final Map<Integer, L> locks;
        protected Supplier<L> supplier;

        private LazyStriped(int stripes, Supplier<L> supplier) {
            super(stripes);
            this.supplier = supplier;
            // Equal with ConcurrentHashMap because of it separate into many segments.
            // Weak value wil be clean if not longer used.
            this.locks = new MapMaker().weakValues().makeMap();
        }

        @Override
        public int size() {
            return locks.size();
        }

        @Override
        public L getAt(int index) {
            // For concurrent get.
            var existing = locks.get(index);
            if (existing != null) return existing;
            var created = supplier.get();

            existing = locks.putIfAbsent(index, created);
            return MoreObjects.firstNonNull(existing, created);
        }
    }

    /**
     * Number of lock eq 2^k.
     *
     * @param <L>
     */
    private abstract static class PowerOfTwoStriped<L> extends Striped<L> {

        protected int numberOfLocks;

        private PowerOfTwoStriped(int stripes) {
            if (stripes > MAX_OF_STRIPS) {
                throw new IllegalArgumentException("Number of stripes must be less than " + stripes);
            }

            if (stripes < 0) {
                throw new IllegalArgumentException("Number of stripes must be positive");
            }

            this.numberOfLocks = IntMath.pow(2, stripes);
        }

        @Override
        public final L get(Object key) {
            var index = (Objects.hashCode(key) & 0x7fffffff) % numberOfLocks;
            return getAt(index);
        }
    }
}
