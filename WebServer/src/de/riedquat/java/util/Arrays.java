package de.riedquat.java.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Arrays {

    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

    @SafeVarargs
    public static <T> Set<T> asUnmodifiableSet(final T... args) {
        return Collections.unmodifiableSet(new HashSet<>(java.util.Arrays.asList(args)));
    }

    public static <T extends Comparable<T>> boolean isSorted(final T[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i].compareTo(array[i - 1]) < 0) {
                return false;
            }
        }
        return true;
    }

    public static byte[] createRandomByteArray(final int length) {
        final byte[] randomData = new byte[length];
        final Random random = new Random();
        random.nextBytes(randomData);
        return randomData;
    }
}
