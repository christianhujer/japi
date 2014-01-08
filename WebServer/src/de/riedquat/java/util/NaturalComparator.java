package de.riedquat.java.util;

import java.util.Comparator;

public class NaturalComparator<T extends Comparable<? super T>> implements Comparator<T> {

    private static final Comparator<? extends Comparable<?>> instance = new NaturalComparator<>();

    @Override
    public int compare(final T o1, final T o2) {
        return o1.compareTo(o2);
    }

    public static <N extends Comparable<? super N>> Comparator<N> getInstance() {
        return (Comparator<N>) instance;
    }
}
