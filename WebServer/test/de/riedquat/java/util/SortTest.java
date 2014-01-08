package de.riedquat.java.util;

import org.junit.Test;

import static de.riedquat.java.util.Arrays.isSorted;
import static org.junit.Assert.assertTrue;

public abstract class SortTest {

    protected abstract Sort getSort();

    @Test
    public void sortEmptyArray() {
        final String[] emptyArray = new String[0];
        getSort().sort(emptyArray);
        assertTrue(isSorted(emptyArray));
    }

    @Test
    public void sortOneElementArray() {
        final String[] oneElementArray = new String[]{"foo"};
        getSort().sort(oneElementArray);
        assertTrue(isSorted(oneElementArray));
    }

    @Test
    public void sortTwoElementArray() {
        final String[] twoElementArray = new String[]{"foo", "bar"};
        getSort().sort(twoElementArray);
        assertTrue(isSorted(twoElementArray));
    }

    @Test
    public void sortBigArray() {
        final String[] twoElementArray = new String[]{"foo", "bar", "buzz", "qux", "qux", "alpha"};
        getSort().sort(twoElementArray);
        assertTrue(isSorted(twoElementArray));
    }
}
