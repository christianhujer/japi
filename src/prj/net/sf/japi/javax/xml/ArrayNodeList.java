package net.sf.japi.javax.xml;

import java.util.Iterator;
import net.sf.japi.java.util.Arrays;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/** A user-defined {@link NodeList} backed by an array.
 * @author <a href="mailto:chrer@riedquat.de">Christian Hujer</a>
 * @since 1.0
 * @version 1.0
 */
public class ArrayNodeList<N extends Node> implements IterableNodeList<N> {
    private final N[] nodes;

    /** Creates a NodeList backed by the specified array.
     * The array is not copied, changes to the array will reflect in the NodeList.
     * @param nodes Array with nodes for this NodeList.
     */
    @SafeVarargs
    @SuppressWarnings("AssignmentToCollectionOrArrayFieldFromParameter")
    public ArrayNodeList(final N... nodes) {
        this.nodes = nodes;
    }

    @Override
    @Nullable
    public Node item(final int index) {
        if (index < 0 || index >= nodes.length) {
            return null;
        }
        return nodes[index];
    }

    @Override
    public int getLength() {
        return nodes.length;
    }

    @Override
    public Iterator<N> iterator() {
        return Arrays.iterator(nodes);
    }
}
