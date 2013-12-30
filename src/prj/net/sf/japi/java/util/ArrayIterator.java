package net.sf.japi.java.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

class ArrayIterator<N> implements Iterator<N> {
    private final N[] elements;
    private int index;

    @SuppressWarnings("AssignmentToCollectionOrArrayFieldFromParameter")
    @SafeVarargs
    ArrayIterator(final N... elements) {
        this.elements = elements;
    }

    @Override
    public boolean hasNext() {
        return index < elements.length;
    }

    @Override
    public N next() {
        try {
            return elements[index++];
        } catch (final ArrayIndexOutOfBoundsException e) {
            throw new NoSuchElementException();
        }
    }
}
