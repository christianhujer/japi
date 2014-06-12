package de.riedquat.webserver.xmlServer;

import de.riedquat.http.HttpOutputStream;
import de.riedquat.http.rest.RestMethod;
import de.riedquat.webserver.WebServerFactory;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.xml.sax.SAXException;

import static de.riedquat.http.Http11Header.CONTENT_LANGUAGE;
import static de.riedquat.http.Http11Header.CONTENT_LENGTH;
import static de.riedquat.http.Http11Header.CONTENT_TYPE;

public class XmlServer {
    private final DocumentBuilder validatingDocumentBuilder;
    private final DocumentBuilder nonvalidatingDocumentBuilder;

    public XmlServer() throws ParserConfigurationException {
        validatingDocumentBuilder = createValidatingDocumentBuilder();
        nonvalidatingDocumentBuilder = createNonvalidatingDocumentBuilder();
    }

    public static void main(final String... args) throws ParserConfigurationException, IOException {
        final WebServerFactory webServerFactory = new WebServerFactory();
        final XmlServer xmlServer = new XmlServer();
        webServerFactory.addRest(xmlServer);
        webServerFactory.createHttpServer(8001);
    }

    private DocumentBuilder createValidatingDocumentBuilder() throws ParserConfigurationException {
        final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setCoalescing(true);
        dbf.setExpandEntityReferences(true);
        dbf.setIgnoringComments(true);
        dbf.setIgnoringElementContentWhitespace(true);
        dbf.setNamespaceAware(true);
        dbf.setValidating(true);
        dbf.setXIncludeAware(true);
        return dbf.newDocumentBuilder();
    }

    private DocumentBuilder createNonvalidatingDocumentBuilder() throws ParserConfigurationException {
        final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setCoalescing(true);
        dbf.setExpandEntityReferences(false);
        dbf.setIgnoringComments(true);
        dbf.setIgnoringElementContentWhitespace(true);
        dbf.setNamespaceAware(false);
        dbf.setValidating(false);
        dbf.setXIncludeAware(false);
        // TODO Obviously, the DTD gets resolved and loaded despite of above settings (validating=false, expendEntityReferences=false, XIncludeAware=false).
        // So, I will need a catalogue for DTDs in order to process.
        // That could be a good idea in general anyway, to speed up processing, because the loaded stuff could be cached.
        final DocumentBuilder db = dbf.newDocumentBuilder();
        db.setEntityResolver((publicId, systemId) -> {
            System.err.format("Requested Entity pubId: %s, sysId: %s%n", publicId, systemId);
            return null;
        });
        return db;
    }

    // TODO There should be a possibility to declare the content type, content language and things like that. It should be a repeatable annotation.
    @RestMethod("/")
    public void directoryListing(final HttpOutputStream out) throws IOException, SAXException {
        out.setHeader(CONTENT_TYPE, "application/xhtml+xml");
        out.setHeader(CONTENT_LANGUAGE, "en_US");
        //out.setStatus(Http11StatusCode.OK);
        // TODO it should not be required to "manually" buffer the output for HTTP/1.1. Instead, the class HttpOutputStream should take care of that.
        // In case the REST method returned without a flush, the output should be fully buffered and the caller should use the help of HttpOutputStream for setting the Content-Length header and sending the data.
        // In case the REST method flushes itself, the output method automatically should be chunked transfer encoding.
        final ByteArrayOutputStream bout = new ByteArrayOutputStream();
        final Document doc = nonvalidatingDocumentBuilder.parse(getClass().getClassLoader().getResourceAsStream(getClass().getPackage().getName().replace('.', '/').concat("/dir.xhtml")));
        final DOMImplementationLS domImplementationLS = (DOMImplementationLS) doc.getImplementation();
        final LSOutput lsOutput = domImplementationLS.createLSOutput();
        lsOutput.setByteStream(bout);
        lsOutput.setEncoding("UTF-8");
        domImplementationLS.createLSSerializer().write(doc, lsOutput);
        final byte[] data = bout.toByteArray();
        out.setHeader(CONTENT_LENGTH, String.valueOf(data.length));
        out.write(data);
        out.flush();
    }
}
