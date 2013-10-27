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

import java.util.Iterator;
import java.util.NoSuchElementException;
import javax.xml.xpath.XPath;
import static javax.xml.xpath.XPathConstants.NODESET;
import javax.xml.xpath.XPathExpressionException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import static org.w3c.dom.Node.ELEMENT_NODE;
import org.w3c.dom.NodeList;

/** A combined iterable / iterator implementation for iterating over NodeLists.
 * @param <T> Node type to operate on.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class NodeListIterator<T extends Node> implements Iterator<T> {

    /** The NodeList to iterate over. */
    @NotNull private final NodeList nodeList;

    /** Index of next element. */
    private int index;

    /** Convenience helper method for getting the first child of an element that is an element with a specific name.
     * @param el element to get child of
     * @param childName name of child element to get
     * @return child element or <code>null</code> if no such child element
     */
    @Nullable public static Element getFirstChild(@NotNull final Element el, @NotNull final String childName) {
        final NodeList childNodes = el.getChildNodes();
        final int count = childNodes.getLength();
        for (int i = 0; i < count; i++) {
            final Node node = childNodes.item(i);
            if (node.getNodeType() == ELEMENT_NODE && node.getNodeName().equals(childName)) {
                return (Element) node;
            }
        }
        return null;
    }

    /** Create a NodeListIterator.
     * @param nodeList NodeList to iterate over
     */
    public NodeListIterator(@NotNull final NodeList nodeList) {
        this.nodeList = nodeList;
    }

    /** Create a NodeListIterator.
     * @param item Element to evaluate against
     * @param nodeType node type of children
     */
    public NodeListIterator(@NotNull final Element item, final short nodeType) {
        this(new FilteredNodeList<T>(item, nodeType));
    }

    /** Create a NodeListIterator.
     * @param item Element to evaluate against
     * @param childName name of child element
     */
    public NodeListIterator(@NotNull final Element item, @NotNull final String childName) {
        this(new FilteredNodeList<T>(item, childName));
    }

    /** Create a NodeListIterator.
     * @param xpath XPath to evaluate against
     * @param item Object to evaluate against
     * @param expression XPath expression to evaluate
     * @throws XPathExpressionException in case of xpath errors
     */
    public NodeListIterator(@NotNull final XPath xpath, @NotNull final Element item, @NotNull final String expression) throws XPathExpressionException {
        this((NodeList) xpath.evaluate(expression, item, NODESET));
    }

    /** {@inheritDoc} */
    public boolean hasNext() {
        return index < nodeList.getLength();
    }

    /** {@inheritDoc} */
    @NotNull public T next() throws NoSuchElementException {
        //noinspection unchecked
        final T item = (T) nodeList.item(index++);
        if (item == null) {
            throw new NoSuchElementException();
        }
        return item;
    }

    /** {@inheritDoc} */
    public void remove() {
        throw new UnsupportedOperationException();
    }

    /** Get the number of elements this iterator would iterate over all.
     * @return number of elements
     */
    public int size() {
        return nodeList.getLength();
    }

} // class NodeListIterator
