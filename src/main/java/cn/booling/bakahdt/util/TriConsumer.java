package cn.booling.bakahdt.util;

import org.jetbrains.annotations.NotNull;

/**
 * @author youyihj
 */
@FunctionalInterface
public interface TriConsumer<T, U, V> {
    void accept(T t, U u, V v);

    static <T, U, V> ChainingCaller<T, U, V> createChainingCaller(@NotNull TriConsumer<T, U, V> triConsumer) {
        return new ChainingCaller<>(triConsumer);
    }

    class ChainingCaller<T, U, V> {
        private final TriConsumer<T, U, V> triConsumer;

        private ChainingCaller(TriConsumer<T, U, V> triConsumer) {
            this.triConsumer = triConsumer;
        }

        public ChainingCaller<T, U, V> accept(T t, U u, V v) {
            triConsumer.accept(t, u, v);
            return this;
        }
    }
}
