package de.riedquat.java.util;

import java.util.Comparator;

public class InsertionSort implements Sort {
    public static Sort getInstance() {
        return new InsertionSort();
    }

    @Override
    public <T extends Comparable<? super T>> void sort(final T[] array, final int startIndex, final int length) {
        for (int searchIndex = startIndex + 1; searchIndex < length; searchIndex++) {
            insertSortStep(array, startIndex, searchIndex);
        }
    }

    private <T extends Comparable<? super T>> void insertSortStep(final T[] array, final int startIndex, final int searchIndex) {
        final T key = array[searchIndex];
        int insertIndex;
        for (insertIndex = searchIndex - 1; insertIndex >= startIndex && array[insertIndex].compareTo(key) > 0; insertIndex--) {
            array[insertIndex + 1] = array[insertIndex];
        }
        array[insertIndex + 1] = key;
    }

    @Override
    public <T> void sort(final T[] array, final int startIndex, final int length, final Comparator<? super T> comparator) {
        for (int insertIndex, searchIndex = startIndex + 1; searchIndex < length; searchIndex++) {
            final T key = array[searchIndex];
            for (insertIndex = searchIndex - 1; insertIndex >= startIndex && comparator.compare(array[insertIndex], key) > 0; insertIndex--) {
                array[insertIndex + 1] = array[insertIndex];
            }
            array[insertIndex + 1] = key;
        }
    }
}
