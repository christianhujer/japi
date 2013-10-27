/*
 * Copyright (C) 2009  Christian Hujer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.sf.japi.progs.jeduca.jtest.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import net.sf.japi.progs.jeduca.jtest.MCAnswerText;
import net.sf.japi.progs.jeduca.jtest.MCQuestionText;
import net.sf.japi.progs.jeduca.jtest.OpenQuestionText;
import net.sf.japi.progs.jeduca.jtest.QuestionCollection;
import net.sf.japi.progs.jeduca.jtest.QuestionText;
import net.sf.japi.progs.jeduca.swing.io.Exporter;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import static org.w3c.dom.Node.ELEMENT_NODE;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/** Interface for reading and writing JTestV1 files.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 * @todo use schema
 */
public class JTestV1 extends AbstractJTestImport<QuestionCollection> implements Exporter<QuestionCollection> {

    /** The DocumentBuilderFactory. */
    private final DocumentBuilderFactory dbf;

    /** The DocumentBuilder. */
    private final DocumentBuilder db;

    /** The DOMImplementation in use. */
    private final DOMImplementation domImpl;

    /** The XPathFactory. */
    private final XPathFactory xpf;

    /** The XPath. */
    private final XPath xp;

    /** Create a new JTest interface.
     * @throws Error in case of ParserConfigurationExceptions
     */
    public JTestV1() {
        try {
            dbf = DocumentBuilderFactory.newInstance();
            try {
                dbf.setCoalescing(true);
                dbf.setExpandEntityReferences(true);
                dbf.setIgnoringComments(true);
                dbf.setIgnoringElementContentWhitespace(true);
                dbf.setNamespaceAware(true);
                dbf.setValidating(true);
                dbf.setXIncludeAware(true);
            } catch (final NoSuchMethodError ignore) {
                /* ignore, this just means there is no XInclude support. */
            } catch (final UnsupportedOperationException ignore) { /* ignore */ }
            db = dbf.newDocumentBuilder();
            domImpl = db.getDOMImplementation();
            xpf = XPathFactory.newInstance();
            xp = xpf.newXPath();
        } catch (final ParserConfigurationException e) {
            throw new Error(e);
        }
    }

    ///** Get all questions from a .jtest file.
    // * @param f .jtest-File
    // * @return all Questions from f
    // * @throws IOException in case of I/O-problems
    // * @throws SAXException in case of XML parsing errors
    // */
    //public QuestionCollection load(final File f) throws IOException, SAXException {
    //    return load(db.parse(f));
    //}

    /** Get all questions from a .jtest file.
     * @param uri .jtest-URI
     * @return all questions from uri
     * @throws IOException in case of I/O-problems
     * @throws SAXException in case of XML parsing errors
     */
    public QuestionCollection load(final String uri) throws IOException, SAXException {
        return load(db.parse(uri));
    }

    ///** Get all questions from a .jtest stream.
    // * @param in .jtest-Stream
    // * @return all questions from in
    // * @throws IOException in case of I/O-problems
    // * @throws SAXException in case of XML parsing errors
    // */
    //public QuestionCollection load(final InputStream in) throws IOException, SAXException {
    //    return load(db.parse(in));
    //}

    /** Get all questions from a .jtest document.
     * @param doc .jtest-Document
     * @return all questions from doc
     * @throws IOException in case of I/O-problems
     * @throws SAXException in case of XML parsing errors
     */
    public QuestionCollection load(final Document doc) throws IOException, SAXException {
        final List<QuestionText> questions = new ArrayList<QuestionText>();
        final NodeList questionEls = doc.getElementsByTagName("QuestionText");
        final int questionElsSize = questionEls.getLength();
        for (int i = 0; i < questionElsSize; i++) {
            final Element questionEl = (Element) questionEls.item(i);
            final String qtype = questionEl.getAttribute("qtype");
            if ("regex".equals(qtype)) {
                final List<String> expressions = new ArrayList<String>();
                final NodeList textEls = questionEl.getChildNodes();
                final int textElsSize = textEls.getLength();
                String questionText = null;
                for (int j = 0; j < textElsSize; j++) {
                    final Node n = textEls.item(j);
                    if (n.getNodeType() == ELEMENT_NODE) {
                        final String tagName = n.getNodeName();
                        if ("text".equals(tagName)) {
                            questionText = n.getFirstChild().getNodeValue();
                        } else if ("regex".equals(tagName)) {
                            expressions.add(n.getFirstChild().getNodeValue());
                        }
                    }
                }
                final OpenQuestionText question = new OpenQuestionText(questionText, expressions);
                question.setType(questionEl.getAttribute("type"));
                question.setHTML(true);
                questions.add(question);
            } else {
                final List<MCAnswerText> answers = new ArrayList<MCAnswerText>();
                final NodeList textEls = questionEl.getChildNodes();
                final int textElsSize = textEls.getLength();
                String questionText = null;
                for (int j = 0; j < textElsSize; j++) {
                    final Node n = textEls.item(j);
                    if (n.getNodeType() == ELEMENT_NODE) {
                        final String tagName = n.getNodeName();
                        if ("text".equals(tagName)) {
                            questionText = n.getFirstChild().getNodeValue();
                        } else if ("true".equals(tagName)) {
                            answers.add(new MCAnswerText(n.getFirstChild().getNodeValue(), true, true));
                        } else if ("false".equals(tagName)) {
                            answers.add(new MCAnswerText(n.getFirstChild().getNodeValue(), false, true));
                        }
                    }
                }
                final QuestionText question = new MCQuestionText(questionText, answers);
                question.setType(questionEl.getAttribute("type"));
                question.setHTML(true);
                questions.add(question);
            }
        }
        final QuestionCollection col = new QuestionCollection(questions);
        setInfo(col, doc);
        return col;
    }

    /** Read the information from a KEduca document and write it to a QuestionCollection.
     * @param col QuestionCollection to write information to
     * @param doc KEduca document to read from
     */
    private void setInfo(final QuestionCollection col, final Document doc) {
        try {
            col.setTitle(xp.evaluate("document/info/title", doc));
            col.setCategory(xp.evaluate("document/info/category", doc));
            col.setType(xp.evaluate("document/info/type", doc));
            col.setLevel(xp.evaluate("document/info/level", doc));
            col.setLanguage(xp.evaluate("document/info/language", doc));
            col.setAuthorName(xp.evaluate("document/info/author/name", doc));
            col.setAuthorEMail(xp.evaluate("document/info/author/email", doc));
            col.setAuthorWWW(xp.evaluate("document/info/author/www", doc));
        } catch (final XPathExpressionException e) {
            throw new Error(e);
        }
    }

    /** Export a QuestionCollection as KEduca file.
     * @param col QuestionCollection to create document from
     * @param f File to save
     * @throws IOException in case of I/O-problems
     */
    public void save(final QuestionCollection col, final File f) throws IOException {
        if (!(domImpl instanceof DOMImplementationLS)) {
            throw new IOException("DOM Implementation with LS-Feature required, upgrade your Java XML Library");
        }
        final DOMImplementationLS ls = (DOMImplementationLS) domImpl;
        ls.createLSSerializer().writeToURI(export(col), f.toURI().toString());
    }

    /** Create a KEduca XML Document from a QuestionCollection.
     * @param col QuestionCollection to create document from
     * @return XML Document (KEduca) for <var>col</var>
     */
    public Document export(final QuestionCollection col) {
        final DocumentType keduca = domImpl.createDocumentType("educa", null, null);
        final Document doc = domImpl.createDocument(null, "Document", keduca);
        //System.err.println("<!DOCTYPE " + doc.getDoctype().getName() + ">");
        final Element docEl = doc.getDocumentElement();

        final Element infoEl = doc.createElement("info");
        docEl.appendChild(infoEl);
        infoEl.appendChild(create(doc, "title", col.getTitle()));
        infoEl.appendChild(create(doc, "category", col.getCategory()));
        infoEl.appendChild(create(doc, "type", col.getType()));
        infoEl.appendChild(create(doc, "level", col.getLevel()));
        infoEl.appendChild(create(doc, "language", col.getLanguage()));

        final Element authorEl = doc.createElement("author");
        infoEl.appendChild(authorEl);
        infoEl.appendChild(create(doc, "name", col.getAuthorName()));
        infoEl.appendChild(create(doc, "email", col.getAuthorEMail()));
        infoEl.appendChild(create(doc, "www", col.getAuthorWWW()));

        final Element dataEl = doc.createElement("data");
        docEl.appendChild(dataEl);
        for (int i = 0; i < col.getSize(); i++) {
            final QuestionText qt = col.getQuestion(i);
            if (qt instanceof MCQuestionText) { // skip answers other than Multiple Choice
                dataEl.appendChild(createQuestionEl(doc, (MCQuestionText) qt));
            }
        }

        return doc;
    }

    /** Create a Question element for a given MCQuestionText.
     * @param doc document to create element for
     * @param question MCQuestionText to create Question for
     * @return element for question
     */
    private Element createQuestionEl(final Document doc, final MCQuestionText question) {
        final Element questionEl = doc.createElement("question");
        if (question.getType() != null) {
            questionEl.setAttribute("type", question.getType());
        }
        questionEl.appendChild(create(doc, "text", question.getText()));
        for (int i = 0; i < question.getSize(); i++) {
            final MCAnswerText answer = question.getAnswer(i);
            questionEl.appendChild(create(doc, answer.isCorrect() ? "true" : "false", answer.getText()));
        }
        return questionEl;
    }

    /** Create a simple element with a given qname and a given text as child.
     * @param doc document to create element for
     * @param name element name
     * @param text element text content
     * @return element with name <var>name</var> containing <var>text</var>
     */
    private Element create(final Document doc, final String name, final String text) {
        final Element el = doc.createElement(name);
        el.appendChild(doc.createTextNode(text));
        return el;
    }

    /** {@inheritDoc} */
    public String getName() {
        return "XML JTest Version 1";
    }

    /** {@inheritDoc} */
    public Class<QuestionCollection> getType() {
        return QuestionCollection.class;
    }

    /** {@inheritDoc} */
    @Override
    protected boolean canLoadImpl(final String uri) {
        try {
            final ErrorCapture capture = new ErrorCapture();
            db.setErrorHandler(capture);
            db.parse(uri);
            return capture.hadErrors();
        } catch (final Exception ignore) { /* ignore */ }
        return false;
    }

    /** Class to capture XML parsing errors when trying to load a document.
     * @author $Author: chris $
     * @version $Id: JTestV1.java,v 1.5 2006/01/31 23:11:54 chris Exp $
     */
    private static class ErrorCapture implements ErrorHandler {

        /** Whether there were errors. */
        private boolean errors;

        /** Create a new ErrorCapture. */
        ErrorCapture() { }

        /** {@inheritDoc} */
        public void warning(final SAXParseException exception) throws SAXException {
        }

        /** {@inheritDoc} */
        public void error(final SAXParseException exception) throws SAXException {
            errors = true;
        }

        /** {@inheritDoc} */
        public void fatalError(final SAXParseException exception) throws SAXException {
            errors = true;
        }

        /** Get whether there were errors.
         * @return errors
         */
        public boolean hadErrors() {
            return errors;
        }

    } // class ErrorCapture

} // class JTestV1
