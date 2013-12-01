package net.sf.japi.javax.xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import static org.junit.Assert.assertEquals;

public class ArrayNodeListTest {

    public static final Node[] EMPTY_NODES_ARRAY = new Node[0];
    private static Document document;

    @BeforeClass
    public static void initDocument() throws ParserConfigurationException {
        final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        final DocumentBuilder db = dbf.newDocumentBuilder();
        document = db.newDocument();
    }

    static void assertArrayInNodeList(final Node[] nodes, final NodeList nodeList) {
        assertEquals(nodes.length, nodeList.getLength());
        assertEquals(nodes.length, nodeList.getLength());
        int nodeIndex = -1;
        while (nodeIndex < 0) {
            Assert.assertNull(nodeList.item(nodeIndex++));
        }
        while (nodeIndex < nodes.length) {
            Assert.assertSame(nodes[nodeIndex], nodeList.item(nodeIndex++));
        }
        while (nodeIndex < nodes.length + 2) {
            Assert.assertNull(nodeList.item(nodeIndex++));
        }
    }

    private static Node[] createSampleNodes() {
        final Node[] nodes = new Node[3];
        nodes[0] = document.createElement("foo");
        nodes[1] = document.createElement("bar");
        nodes[2] = document.createElement("buzz");
        return nodes;
    }

    @Test
    public void userCreatedEmptyArrayNodeList_isEmpty() {
        final NodeList nodeList = new ArrayNodeList<>(EMPTY_NODES_ARRAY);
        assertArrayInNodeList(EMPTY_NODES_ARRAY, nodeList);
    }

    @Test
    public void emptyArrayNodeList_isEmpty() {
        final NodeList nodeList = NodeLists.emptyNodeList();
        assertArrayInNodeList(EMPTY_NODES_ARRAY, nodeList);
    }

    @Test
    public void simpleArrayNodeList_returnsElements() {
        final Node[] nodes = createSampleNodes();
        final NodeList nodeList = new ArrayNodeList<>(nodes);
        assertArrayInNodeList(nodes, nodeList);
    }

    @Test
    public void simpleArrayNodeList_returnsIsIterable() {
        final Node[] nodes = createSampleNodes();
        final Iterable<Node> nodeList = new ArrayNodeList<>(nodes);
        int index = 0;
        for (final Node node : nodeList) {
            Assert.assertSame(nodes[index++], node);
        }
    }
}
