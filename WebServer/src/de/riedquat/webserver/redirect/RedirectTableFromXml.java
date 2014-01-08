package de.riedquat.webserver.redirect;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RedirectTableFromXml {
    public static Map<String, String> read(final String uri) throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
        final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        final XPathFactory xPathFactory = XPathFactory.newInstance();
        final XPath xPath = xPathFactory.newXPath();
        final Document doc = documentBuilder.parse(uri);
        final NodeList nodeList = (NodeList) xPath.evaluate("//redirect", doc, XPathConstants.NODESET);
        final Map<String, String> redirectTable = new HashMap<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            final Element redirect = (Element) nodeList.item(i);
            redirectTable.put(redirect.getAttribute("from"), redirect.getAttribute("to"));
        }
        return redirectTable;
    }
}
