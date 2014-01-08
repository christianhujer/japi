package de.riedquat.java.util;

import org.junit.Test;

import static de.riedquat.java.util.Arrays.createRandomByteArray;
import static de.riedquat.java.util.Arrays.isSorted;
import static org.junit.Assert.*;

public class ArraysTest {

    private static final String[] emptyArray = {};
    private static final String[] oneElementArray = {"foo"};
    private static final String[] sortedTwoElementArray = {"bar", "foo"};
    private static final String[] unsortedTwoElementArray = {"foo", "bar"};

    private static double average(final byte... randomData) {
        double sum = 0.0;
        for (final byte b : randomData) {
            sum += 0.5 + b;
        }
        return sum / randomData.length;
    }

    @Test
    public void testEmptyArray() {
        assertTrue(isSorted(emptyArray));
    }

    @Test
    public void testOneElementArray() {
        assertTrue(isSorted(oneElementArray));
    }

    @Test
    public void sortedArray_returnsTrue() {
        assertTrue(isSorted(sortedTwoElementArray));
    }

    @Test
    public void unsortedArray_returnsFalse() {
        assertFalse(isSorted(unsortedTwoElementArray));
    }

    @Test
    public void randomData() {
        final byte[] randomData = createRandomByteArray(4096);
        final double average = average(randomData);
        assertEquals(0.0, average, 3.0);
    }
}
