package net.sf.japi.javax.xml;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

class IterableNodeListDecorator<T extends Node> implements IterableNodeList<T> {
    private final NodeList nodeList;

    IterableNodeListDecorator(final NodeList nodeList) {
        this.nodeList = nodeList;
    }
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int index;
            @Override
            public boolean hasNext() {
                return index < nodeList.getLength();
            }

            @SuppressWarnings("unchecked")
            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (T) nodeList.item(index++);
            }
        };
    }

    @Nullable
    @Override
    public Node item(final int index) {
        return nodeList.item(index);
    }

    @Override
    public int getLength() {
        return nodeList.getLength();
    }
}
