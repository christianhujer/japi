package net.sf.japi.javax.xml;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import static net.sf.japi.javax.xml.NodeLists.emptyNodeList;
import static net.sf.japi.javax.xml.NodeLists.iterable;
import static org.junit.Assert.assertEquals;

public class NodeListsTest {

    private static Element[] createAndAppendElements(final Node parentElement, final String... elementNames) {
        final Element[] elements = createElements(parentElement.getOwnerDocument(), elementNames);
        appendChildren(parentElement, elements);
        return elements;
    }

    private static void appendChildren(final Node parentElement, final Element... elements) {
        for (final Element element : elements) {
            parentElement.appendChild(element);
        }
    }

    private static Element[] createElements(final Document ownerDocument, final String... elementNames) {
        final Element[] elements = new Element[elementNames.length];
        for (int i = 0; i < elementNames.length; i++) {
            elements[i] = ownerDocument.createElement(elementNames[i]);
        }
        return elements;
    }

    public static List<Node> asList(final NodeList nodeList) {
        final List<Node> nodes = new ArrayList<>();
        for (final Node node : NodeLists.iterable(nodeList)) {
            nodes.add(node);
        }
        return nodes;
    }

    @Test
    public void iterableForEmptyNodeList_hasNoNodes() {
        final NodeList emptyNodeList = emptyNodeList();
        int nodeCount = 0;
        for (final Node ignored : iterable(emptyNodeList)) {
            nodeCount++;
        }
        assertEquals(0, nodeCount);
    }

    @Test
    public void iterableForSampleNodeList_hasExpectedNodes() throws ParserConfigurationException {
        final Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        final Element documentElement = document.createElement("doc");
        document.appendChild(documentElement);
        final String[] elementNames = {"foo", "bar", "buzz"};
        final Node[] elementArray = createAndAppendElements(documentElement, elementNames);
        final NodeList nodeList = documentElement.getChildNodes();
        ArrayNodeListTest.assertArrayInNodeList(elementArray, NodeLists.iterable(nodeList));
        assertEquals(Arrays.asList(elementArray), asList(nodeList));
    }
}
