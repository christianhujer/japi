package net.sf.japi.java.util;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.fail;

public class ArraysTest {

    private static final Object[] EMPTY_ARRAY = new Object[0];

    @Test
    public void emptyIterator_runsLikeEmptyArray() {
        final Object[] data = EMPTY_ARRAY;
        assertIteratorIsLikeArray(data);
    }

    @Test
    public void someStringsIterator_runsLikeArray() {
        final String[] data = {"foo", "bar", "buzz"};
        assertIteratorIsLikeArray(data);
    }

    @Test(expected = NoSuchElementException.class)
    public void iteratorThrowsNoSuchElementException() {
        final Iterator<Object> iterator = Arrays.iterator(EMPTY_ARRAY);
        iterator.next();
    }

    @SafeVarargs
    private final <T> void assertIteratorIsLikeArray(final T... data) {
        final Iterator<T> iterator = Arrays.iterator(data);
        for (final T object : data) {
            Assert.assertTrue(iterator.hasNext());
            Assert.assertSame(object, iterator.next());
        }
        Assert.assertFalse(iterator.hasNext());
        try {
            iterator.next();
            fail();
        } catch (final NoSuchElementException ignore) {
        }
    }

}
