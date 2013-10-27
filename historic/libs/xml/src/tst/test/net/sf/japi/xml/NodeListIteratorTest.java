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

package test.net.sf.japi.xml;

import java.io.IOException;
import java.io.StringReader;
import java.util.NoSuchElementException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import net.sf.japi.xml.NodeListIterator;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * TODO
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
public class NodeListIteratorTest {

    /** The DocumentBuilder for creating DOM trees for testing. */
    private static DocumentBuilder db;

    /** Creates the DocumentBuilder that is used for creating DOM trees for testing.
     * @throws ParserConfigurationException (unexpected)
     */
    @BeforeClass
    public static void createDocumentBuilder() throws ParserConfigurationException {
        final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        db = dbf.newDocumentBuilder();
    }

    /** */
    @SuppressWarnings({"AssignmentToNull"})
    @AfterClass
    public static void deleteDocumentBuilder() {
        db = null;
    }

    /** Tests that {@link NodeListIterator#getFirstChild(Element, String)} works. */
    @Test
    public void testGetFirstChild() {
        final Document doc = parse("<foo><bar/><target>hit</target><target>wrong</target></foo>");
        final Element child = NodeListIterator.getFirstChild(doc.getDocumentElement(), "target");
        Assert.assertNotNull("Expecting child to be found.", child);
        assert child != null; // TODO:2009-02-15:christianhujer:Remove as soon as IDEA knows Assert.assertNotNull()
        final String text = child.getFirstChild().getNodeValue();
        Assert.assertEquals("Expecting correct child to be found.", "hit", text);
    }

    /** Tests that {@link NodeListIterator#getFirstChild(Element, String)} works. */
    @Test
    public void testGetFirstChildNull() {
        final Document doc = parse("<foo><bar/><target>hit</target><target>wrong</target></foo>");
        final Element child = NodeListIterator.getFirstChild(doc.getDocumentElement(), "doesnotexist");
        Assert.assertNull("Expecting child 'doesnotexist' to not be found.", child);
    }

    /** Tests that {@link NodeListIterator#NodeListIterator(NodeList)} works. */
    @Test
    public void testNodeListIteratorForNodeList() {
        final Document doc = parse("<foo><bar/><target>hit</target><target>wrong</target></foo>");
        final NodeList nodeList = doc.getDocumentElement().getChildNodes();
        final NodeListIterator testling = new NodeListIterator(nodeList);
        for (int i = 0; i < nodeList.getLength(); i++) {
            final Node expected = nodeList.item(i);
            Assert.assertTrue("Expecting NodeListIterator to return the same number of elements as the NodeList contains.", testling.hasNext());
            final Node actual = testling.next();
            Assert.assertSame("Expecting NodeListIterator to return the same elements as manual NodeList iteration.", expected, actual);
        }
        Assert.assertFalse("Expecting NodeListIterator to not return more elements than the NodeList has.", testling.hasNext());
    }

    /** Tests that {@link NodeListIterator#NodeListIterator(NodeList)} also works for empty NodeLists. */
    @Test(expected = NoSuchElementException.class)
    public void testNodeListIteratorForEmptyNodeList() {
        final Document doc = parse("<foo/>");
        final NodeList nodeList = doc.getDocumentElement().getChildNodes();
        final NodeListIterator testling = new NodeListIterator(nodeList);
        Assert.assertFalse("Expecting NodeListIterator to not return elements for an empty NodeList.", testling.hasNext());
        testling.next();
    }

    /** Tests that {@link NodeListIterator#NodeListIterator(Element, short)} works. */
    @Test
    public void testNodeListIteratorForElementAndNodeType() {
        final Document doc = parse("<foo>text<element/>text<element/>text</foo>");
        final NodeList nodeList = doc.getDocumentElement().getElementsByTagName("element");
        final NodeListIterator testling = new NodeListIterator(doc.getDocumentElement(), Node.ELEMENT_NODE);
        for (int i = 0; i < nodeList.getLength(); i++) {
            final Node expected = nodeList.item(i);
            Assert.assertTrue("Expecting NodeListIterator to return the same number of elements as the NodeList contains.", testling.hasNext());
            final Node actual = testling.next();
            Assert.assertSame("Expecting NodeListIterator to return the same elements as manual NodeList iteration.", expected, actual);
        }
        Assert.assertFalse("Expecting NodeListIterator to not return more elements than the NodeList has.", testling.hasNext());
    }

    /** Tests that {@link NodeListIterator#NodeListIterator(Element, short)} also works for empty NodeLists. */
    @Test(expected = NoSuchElementException.class)
    public void testNodeListIteratorForElementAndNodeTypeEmpty() {
        final Document doc = parse("<foo>text</foo>");
        final NodeListIterator testling = new NodeListIterator(doc.getDocumentElement(), Node.ELEMENT_NODE);
        Assert.assertFalse("Expecting NodeListIterator to not return elements for an empty NodeList.", testling.hasNext());
        testling.next();
    }

    /** Tests that {@link NodeListIterator#NodeListIterator(Element, String)} works. */
    @Test(expected = NoSuchElementException.class)
    public void testNodeListIteratorForElementAndChildName() {
        final Document doc = parse("<foo>text<bar/>text<target v='t'><target v='f'/></target>text<target v='t'/>text<bar><target v='f'/></bar>text</foo>");
        final NodeListIterator<Element> testling = new NodeListIterator<Element>(doc.getDocumentElement(), "target");

        Assert.assertTrue(testling.hasNext());
        final Element child1 = testling.next();
        Assert.assertNotNull(child1);
        Assert.assertEquals("target", child1.getNodeName());
        Assert.assertEquals("t", child1.getAttribute("v"));

        Assert.assertTrue(testling.hasNext());
        final Element child2 = testling.next();
        Assert.assertNotNull(child2);
        Assert.assertEquals("target", child2.getNodeName());
        Assert.assertEquals("t", child2.getAttribute("v"));

        Assert.assertFalse(testling.hasNext());
        testling.next();
    }

    /** Test case for {@link NodeListIterator#remove()}. */
    @Test(expected = UnsupportedOperationException.class)
    public void testRemove() {
        final Document doc = parse("<foo>text<bar/>text<target v='t'><target v='f'/></target>text<target v='t'/>text<bar><target v='f'/></bar>text</foo>");
        final NodeListIterator testling = new NodeListIterator(doc.getDocumentElement(), "target");
        testling.remove();
    }

    /** Helper method that creates an XML document from a String.
     * @param document String that contains an XML document.
     * @return DOM Document from parsing that String.
     */
    private Document parse(final String document) {
        try {
            return db.parse(new InputSource(new StringReader(document)));
        } catch (final SAXException e) {
            throw new AssertionError(e);
        } catch (final IOException e) {
            throw new AssertionError(e);
        }
    }
}
