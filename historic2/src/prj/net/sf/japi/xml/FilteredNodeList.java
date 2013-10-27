/*
 * Copyright (C) 2009  Christian Hujer.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package net.sf.japi.xml;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/** FilteredNodeList is a NodeList implementation that allows quick iteration over an elements children.
 * Currently there are two ways to use FilteredNodeList: iterating over all child elements with a certain name (children, not descendants), or iterating over all children of a specific node type.
 * @param <T> Node type to operate on.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class FilteredNodeList<T extends Node> implements NodeList, Iterable<T> {

    /** Nodes. */
    @NotNull private final T[] nodes;

    /** Create a FilteredNodeList.
     * @param el Element to iterate on
     * @param nodeType nodetype to find
     */
    public FilteredNodeList(@NotNull final Element el, final short nodeType) {
        final List<Node> tmpNodes = new ArrayList<Node>();
        final NodeList childNodes = el.getChildNodes();
        final int count = childNodes.getLength();
        for (int i = 0; i < count; i++) {
            final Node node = childNodes.item(i);
            if (node.getNodeType() == nodeType) {
                tmpNodes.add(node);
            }
        }
        //noinspection unchecked
        nodes = (T[]) tmpNodes.toArray(new Node[tmpNodes.size()]);
    }

    /** Create a FilteredNodeList.
     * @param el Element to iterate on
     * @param childName name of child elements to iterate over
     */
    public FilteredNodeList(@NotNull final Element el, @NotNull final String childName) {
        final List<Node> tmpNodes = new ArrayList<Node>();
        final NodeList childNodes = el.getChildNodes();
        final int count = childNodes.getLength();
        for (int i = 0; i < count; i++) {
            final Node node = childNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals(childName)) {
                tmpNodes.add(node);
            }
        }
        //noinspection unchecked
        nodes = (T[]) tmpNodes.toArray(new Node[tmpNodes.size()]);
    }

    /** {@inheritDoc} */
    @Nullable public T item(final int index) {
        try {
            return nodes[index];
        } catch (final ArrayIndexOutOfBoundsException ignore) {
            return null;
        }
    }

    /** {@inheritDoc} */
    public int getLength() {
        return nodes.length;
    }

    /** {@inheritDoc} */
    @NotNull public Iterator<T> iterator() {
        return new NodeListIterator<T>(this);
    }

} // class FilteredNodeList
