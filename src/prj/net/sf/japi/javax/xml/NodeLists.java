package net.sf.japi.javax.xml;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Utility methods for {@link NodeList}.
 *
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @version 1.0
 * @since 1.0
 */
public class NodeLists {

    private static final IterableNodeList<?> EMPTY_NODE_LIST = new ArrayNodeList<>();

    private NodeLists() {
    }

    /**
     * Returns an iterable for the specified NodeList.
     *
     * @param nodeList
     *         NodeList for which to return an iterable NodeList.
     * @return An Iterable version of the NodeList.
     */
    @SuppressWarnings({"unchecked", "CastConflictsWithInstanceof"})
    public static <N extends Node> IterableNodeList<N> iterable(final NodeList nodeList) {
        if (nodeList instanceof IterableNodeList) {
            return (IterableNodeList<N>) nodeList;
        }
        return new IterableNodeListDecorator<>(nodeList);
    }

    /**
     * Returns an empty NodeList.
     *
     * @return An empty NodeList.
     */
    public static IterableNodeList<?> emptyNodeList() {
        return EMPTY_NODE_LIST;
    }
}
