package de.riedquat.webserver.redirect;

import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.Map;

public class XmlRedirectTableTest {

    @Test
    public void readRedirectTableFromXml() throws SAXException, ParserConfigurationException, XPathExpressionException, IOException {
        final Map<String, String> redirectMap = RedirectTableFromXml.read("redirectTable.xml");
        assertMapped(redirectMap, "foo", "bar");
    }

    private static void assertMapped(final Map<String, String> redirectMap, final String key, final String value) {
        Assert.assertTrue(redirectMap.containsKey(key));
        Assert.assertEquals(value, redirectMap.get(key));
    }
}
