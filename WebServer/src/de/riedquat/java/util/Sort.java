package de.riedquat.java.util;

import java.util.Comparator;

public interface Sort {
    default <T extends Comparable<? super T>> void sort(final T[] array) {
        sort(array, 0, array.length);
    }

    default <T extends Comparable<? super T>> void sort(final T[] array, final int startIndex, final int length) {
        sort(array, 0, array.length, NaturalComparator.getInstance());
    }

    default <T> void sort(final T[] array, final Comparator<? super T> comparator) {
        sort(array, 0, array.length, comparator);
    }

    <T> void sort(T[] array, int startIndex, int length, Comparator<? super T> comparator);
}
