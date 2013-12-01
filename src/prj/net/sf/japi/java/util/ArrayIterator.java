package net.sf.japi.java.util;

import java.util.Iterator;

class ArrayIterator<N> implements Iterator<N> {
    private final N[] nodes;
    private int index;

    @SuppressWarnings("AssignmentToCollectionOrArrayFieldFromParameter")
    @SafeVarargs
    ArrayIterator(final N... nodes) {
        this.nodes = nodes;
    }

    @Override
    public boolean hasNext() {
        return index < nodes.length;
    }

    @Override
    public N next() {
        return nodes[index++];
    }
}
