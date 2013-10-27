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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import net.sf.japi.xml.SimpleNamespaceContext;
import org.junit.Test;
import org.junit.Assert;

/** UnitTest for {@link SimpleNamespaceContext}.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
public class SimpleNamespaceContextTest {

    /** Tests that {@link SimpleNamespaceContext#getNamespaceURI(String)} returns correct defaults.
     * @see javax.xml.namespace.NamespaceContext#getNamespaceURI(String)
     */
    @Test public void testDefaultGetNamespaceURI() {
        final SimpleNamespaceContext testling = new SimpleNamespaceContext();
        Assert.assertEquals("The empty prefix \"\" must be initially mapped to the default namespace \"\".", "", testling.getNamespaceURI(""));
        Assert.assertEquals("The prefix \"xml\" must be mapped to the namespace \"http://www.w3.org/XML/1998/namespace\".", "http://www.w3.org/XML/1998/namespace", testling.getNamespaceURI("xml"));
        Assert.assertEquals("The prefix \"xmlns\" must be mapped to the namespace \"http://www.w3.org/2000/xmlns/\".", "http://www.w3.org/2000/xmlns/", testling.getNamespaceURI("xmlns"));
    }

    /** Tests that {@link SimpleNamespaceContext#getNamespaceURI(String)} with null throws IllegalArgumentException.
     * @see javax.xml.namespace.NamespaceContext#getNamespaceURI(String)
     * @throws IllegalArgumentException (expected).
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDefaultGetNamespaceURINull() {
        final SimpleNamespaceContext testling = new SimpleNamespaceContext();
        testling.getNamespaceURI(null);
    }

    /** Tests that {@link net.sf.japi.xml.SimpleNamespaceContext#getPrefix(String)} returns correct defaults.
     * @see javax.xml.namespace.NamespaceContext#getPrefix(String)
     */
    @Test public void testDefaultGetPrefix() {
        final SimpleNamespaceContext testling = new SimpleNamespaceContext();
        Assert.assertEquals("The default namespace \"\" must be initially mapped to the empty prefix \"\".", "", testling.getPrefix(""));
        Assert.assertEquals("The namespace \"http://www.w3.org/XML/1998/namespace\" must be mapped to the prefix \"xml\".", "xml", testling.getPrefix("http://www.w3.org/XML/1998/namespace"));
        Assert.assertEquals("The namespace \"http://www.w3.org/2000/xmlns/\" must be mapped to the prefix \"xmlns\".", "xmlns", testling.getPrefix("http://www.w3.org/2000/xmlns/"));
    }

    /** Tests that {@link SimpleNamespaceContext#getPrefix(String)} with null throws IllegalArgumentException.
     * @see javax.xml.namespace.NamespaceContext#getPrefix(String)
     * @throws IllegalArgumentException (expected).
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDefaultGetPrefixNull() {
        final SimpleNamespaceContext testling = new SimpleNamespaceContext();
        testling.getPrefix(null);
    }

    /** Tests that legal remappings of the default namespaces are accepted. */
    @Test public void testLegalRemap() {
        final SimpleNamespaceContext testling = new SimpleNamespaceContext();
        testling.map("xml", "http://www.w3.org/XML/1998/namespace");
        testling.map("xmlns", "http://www.w3.org/2000/xmlns/");
    }

    /** Tests that illegally remapping the prefix "xml" is not possible.
     * @throws IllegalArgumentException (expected).
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRemapXmlPrefixImpossible() {
        final SimpleNamespaceContext testling = new SimpleNamespaceContext();
        testling.map("xml", "");
    }

    /** Tests that illegally remapping the prefix "xmlns" is not possible.
     * @throws IllegalArgumentException (expected).
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRemapXmlnsPrefixImpossible() {
        final SimpleNamespaceContext testling = new SimpleNamespaceContext();
        testling.map("xmlns", "");
    }

    /** Tests that illegally remapping the namespace of "xml" is not possible.
     * @throws IllegalArgumentException (expected).
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRemapXmlNamespaceImpossible() {
        final SimpleNamespaceContext testling = new SimpleNamespaceContext();
        testling.map("", "http://www.w3.org/XML/1998/namespace");
    }

    /** Tests that illegally remapping the namespace of "xmlns" is not possible.
     * @throws IllegalArgumentException (expected).
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRemapXmlnsNamespaceImpossible() {
        final SimpleNamespaceContext testling = new SimpleNamespaceContext();
        testling.map("", "http://www.w3.org/2000/xmlns/");
    }

    /** Tests that illegally mapping the prefix "xml" is not possible.
     * @throws IllegalArgumentException (expected).
     */
    @Test(expected = IllegalArgumentException.class)
    public void testMapXmlPrefixImpossible() {
        new SimpleNamespaceContext("xml", "");
    }

    /** Tests that illegally mapping the prefix "xmlns" is not possible.
     * @throws IllegalArgumentException (expected).
     */
    @Test(expected = IllegalArgumentException.class)
    public void testMapXmlnsPrefixImpossible() {
        new SimpleNamespaceContext("xmlns", "");
    }

    /** Tests that illegally mapping the namespace of "xml" is not possible.
     * @throws IllegalArgumentException (expected).
     */
    @Test(expected = IllegalArgumentException.class)
    public void testMapXmlNamespaceImpossible() {
        new SimpleNamespaceContext("", "http://www.w3.org/XML/1998/namespace");
    }

    /** Tests that illegally mapping the namespace of "xmlns" is not possible.
     * @throws IllegalArgumentException (expected).
     */
    @Test(expected = IllegalArgumentException.class)
    public void testMapXmlnsNamespaceImpossible() {
        new SimpleNamespaceContext("", "http://www.w3.org/2000/xmlns/");
    }


    /** Tests that mapping a new prefix works. */
    @Test
    public void testSimpleMap() {
        final SimpleNamespaceContext testling = new SimpleNamespaceContext();

        testling.map("", "http://www.w3.org/1999/xhtml");
        testling.map("html", "http://www.w3.org/1999/xhtml");

        final Set<String> expectedPrefixes = Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("", "html")));

        Assert.assertEquals("http://www.w3.org/1999/xhtml", testling.getNamespaceURI(""));
        Assert.assertEquals("http://www.w3.org/1999/xhtml", testling.getNamespaceURI("html"));

        Assert.assertTrue(expectedPrefixes.contains(testling.getPrefix("http://www.w3.org/1999/xhtml")));

        Assert.assertEquals(expectedPrefixes, collect(new HashSet<String>(), testling.getPrefixes("http://www.w3.org/1999/xhtml")));
    }

    /** Collects items from an Iterator into a Collection.
     * @param c Collection to which the items shall be collected.
     * @param iterator Iterator from which to collect items.
     * @return <var>c</var> after the items from <var>iterator</var> have been added to it.
     */
    private static <T, C extends Collection<T>> C collect(final C c, final Iterator<T> iterator) {
        while (iterator.hasNext()) {
            c.add(iterator.next());
        }
        return c;
    }

}
