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

package test.net.sf.japi.io.args;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.NoSuchElementException;
import net.sf.japi.io.args.TokenReader;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link TokenReader}.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
public class TokenReaderTest {

    /** Tests that a TokenReader on an empty file has no tokens. */
    @Test(expected = NoSuchElementException.class)
    public void testTokenReaderEmpty() {
        final TokenReader reader = new TokenReader(createStream(""));
        Assert.assertFalse("On an empty file, hasNext() must return false.", reader.hasNext());
        reader.next();
    }

    /** Tests that a TokenReader on a file with whitespace only has no tokens. */
    @Test(expected = NoSuchElementException.class)
    public void testTokenReaderWhitespace() {
        final TokenReader reader = new TokenReader(createStream("   \n    "));
        Assert.assertFalse("On whitespace only, hasNext() must return false.", reader.hasNext());
        reader.next();
    }

    /** Tests that a TokenReader on a file with a single token returns that token. */
    @Test(expected = NoSuchElementException.class)
    public void testTokenSimple() {
        final TokenReader reader = new TokenReader(createStream("foo"));
        Assert.assertTrue("Before reading the last token, hasNext() must return true.", reader.hasNext());
        final String token = reader.next();
        Assert.assertEquals("Token must be retrievable", "foo", token);
        Assert.assertFalse("After reading the last token, hasNext() must return false.", reader.hasNext());
        reader.next();
    }

    /** Tests that a TokenReader on a file with preceeding whitespace returns the token without whitespace. */
    @Test(expected = NoSuchElementException.class)
    public void testTokenWithPreceedingWhitespace() {
        final TokenReader reader = new TokenReader(createStream("   foo"));
        Assert.assertTrue("Before reading the last token, hasNext() must return true.", reader.hasNext());
        final String token = reader.next();
        Assert.assertEquals("Token must be retrievable", "foo", token);
        Assert.assertFalse("After reading the last token, hasNext() must return false.", reader.hasNext());
        reader.next();
    }

    /** Tests that a TokenReader on a file with trailing whitespace returns the token without whitespace. */
    @Test(expected = NoSuchElementException.class)
    public void testTokenWithTrailingWhitespace() {
        final TokenReader reader = new TokenReader(createStream("foo   "));
        Assert.assertTrue("Before reading the last token, hasNext() must return true.", reader.hasNext());
        final String token = reader.next();
        Assert.assertEquals("Token must be retrievable", "foo", token);
        Assert.assertFalse("After reading the last token, hasNext() must return false.", reader.hasNext());
        reader.next();
    }

    /** Tests that a TokenReader on a file with surrounding whitespace returns the token without whitespace. */
    @Test(expected = NoSuchElementException.class)
    public void testTokenWithSurroundingWhitespace() {
        final TokenReader reader = new TokenReader(createStream("   foo   "));
        Assert.assertTrue("Before reading the last token, hasNext() must return true.", reader.hasNext());
        final String token = reader.next();
        Assert.assertEquals("Token must be retrievable", "foo", token);
        Assert.assertFalse("After reading the last token, hasNext() must return false.", reader.hasNext());
        reader.next();
    }

    /** Tests that a TokenReader on a file with preceeding whitespace returns the token without whitespace. */
    @Test(expected = NoSuchElementException.class)
    public void testTokensWithPreceedingWhitespace() {
        final TokenReader reader = new TokenReader(createStream("   foo  bar"));
        Assert.assertTrue("Before reading the last token, hasNext() must return true.", reader.hasNext());
        Assert.assertEquals("Token must be retrievable", "foo", reader.next());
        Assert.assertTrue("Before reading the last token, hasNext() must return true.", reader.hasNext());
        Assert.assertEquals("Token must be retrievable", "bar", reader.next());
        Assert.assertFalse("After reading the last token, hasNext() must return false.", reader.hasNext());
        reader.next();
    }

    /** Tests that a TokenReader on a file with trailing whitespace returns the token without whitespace. */
    @Test(expected = NoSuchElementException.class)
    public void testTokensWithTrailingWhitespace() {
        final TokenReader reader = new TokenReader(createStream("foo  bar   "));
        Assert.assertTrue("Before reading the last token, hasNext() must return true.", reader.hasNext());
        Assert.assertEquals("Token must be retrievable", "foo", reader.next());
        Assert.assertTrue("Before reading the last token, hasNext() must return true.", reader.hasNext());
        Assert.assertEquals("Token must be retrievable", "bar", reader.next());
        Assert.assertFalse("After reading the last token, hasNext() must return false.", reader.hasNext());
        reader.next();
    }

    /** Tests that a TokenReader on a file with surrounding whitespace returns the token without whitespace. */
    @Test(expected = NoSuchElementException.class)
    public void testTokensWithSurroundingWhitespace() {
        final TokenReader reader = new TokenReader(createStream("   foo  bar   "));
        Assert.assertTrue("Before reading the last token, hasNext() must return true.", reader.hasNext());
        Assert.assertEquals("Token must be retrievable", "foo", reader.next());
        Assert.assertTrue("Before reading the last token, hasNext() must return true.", reader.hasNext());
        Assert.assertEquals("Token must be retrievable", "bar", reader.next());
        Assert.assertFalse("After reading the last token, hasNext() must return false.", reader.hasNext());
        reader.next();
    }

    /** Tests that a TokenReader on a file with String tokens returns them. */
    @Test(expected = NoSuchElementException.class)
    public void testTokensComplex() {
        final TokenReader reader = new TokenReader(createStream("   foo\nbar\nbuzz token\n\"  Multiline\n\\\"String  \"  anotherFoo  a\"n\"a"));
        Assert.assertTrue("Before reading the last token, hasNext() must return true.", reader.hasNext());
        Assert.assertEquals("Expecting token", "foo", reader.next());
        Assert.assertTrue("Before reading the last token, hasNext() must return true.", reader.hasNext());
        Assert.assertEquals("Expecting token", "bar", reader.next());
        Assert.assertTrue("Before reading the last token, hasNext() must return true.", reader.hasNext());
        Assert.assertEquals("Expecting token", "buzz", reader.next());
        Assert.assertTrue("Before reading the last token, hasNext() must return true.", reader.hasNext());
        Assert.assertEquals("Expecting token", "token", reader.next());
        Assert.assertTrue("Before reading the last token, hasNext() must return true.", reader.hasNext());
        Assert.assertEquals("Expecting token", "  Multiline\n\"String  ", reader.next());
        Assert.assertTrue("Before reading the last token, hasNext() must return true.", reader.hasNext());
        Assert.assertEquals("Expecting token", "anotherFoo", reader.next());
        Assert.assertTrue("Before reading the last token, hasNext() must return true.", reader.hasNext());
        Assert.assertEquals("Expecting token", "ana", reader.next());
        Assert.assertFalse("After reading the last token, hasNext() must return false.", reader.hasNext());
        reader.next();
    }

    /** Tests that the TokenReader treats escaped quotes correctly.
     * @see <a href="http://sourceforge.net/tracker/index.php?func=detail&aid=1791713&group_id=149894&atid=776737">[ 1791713 ] quotation marks removed from @arg files</a>
     * @see <a href="http://sourceforge.net/tracker/index.php?func=detail&aid=1791715&group_id=149894&atid=776737">[ 1791715 ] \" silently changed to \\ in argfiles</a>
     */
    @Test
    public void testTokenEscapedQuotesRandom() {
        final TokenReader reader = new TokenReader(createStream(" \\\" \\\" \\\" a\\\"a \"\\\"\" a\"\\\"\"a "));
        Assert.assertTrue("Before reading the last token, hasNext() must return true.", reader.hasNext());
        Assert.assertEquals("Expecting token", "\"", reader.next());
        Assert.assertTrue("Before reading the last token, hasNext() must return true.", reader.hasNext());
        Assert.assertEquals("Expecting token", "\"", reader.next());
        Assert.assertTrue("Before reading the last token, hasNext() must return true.", reader.hasNext());
        Assert.assertEquals("Expecting token", "\"", reader.next());
        Assert.assertTrue("Before reading the last token, hasNext() must return true.", reader.hasNext());
        Assert.assertEquals("Expecting token", "a\"a", reader.next());
        Assert.assertTrue("Before reading the last token, hasNext() must return true.", reader.hasNext());
        Assert.assertEquals("Expecting token", "\"", reader.next());
        Assert.assertTrue("Before reading the last token, hasNext() must return true.", reader.hasNext());
        Assert.assertEquals("Expecting token", "a\"a", reader.next());
        Assert.assertFalse("After reading the last token, hasNext() must return false.", reader.hasNext());
    }

    /** Tests that <samp>\n</samp> returns <samp>n</samp>.
     * @see <a href="http://sourceforge.net/tracker/index.php?func=detail&aid=1791713&group_id=149894&atid=776737">[ 1791713 ] quotation marks removed from @arg files</a>
     * @see <a href="http://sourceforge.net/tracker/index.php?func=detail&aid=1791715&group_id=149894&atid=776737">[ 1791715 ] \" silently changed to \\ in argfiles</a>
     */
    @Test
    public void testTokenReaderNormalEscape() {
        final TokenReader reader = new TokenReader(createStream("\\n"));
        Assert.assertTrue("Before reading the last token, hasNext() must return true.", reader.hasNext());
        Assert.assertEquals("Expecting token", "n", reader.next());
        Assert.assertFalse("After reading the last token, hasNext() must return false.", reader.hasNext());
    }

    /** Tests that <samp>\"</samp> returns <samp>"</samp>.
     * @see <a href="http://sourceforge.net/tracker/index.php?func=detail&aid=1791713&group_id=149894&atid=776737">[ 1791713 ] quotation marks removed from @arg files</a>
     * @see <a href="http://sourceforge.net/tracker/index.php?func=detail&aid=1791715&group_id=149894&atid=776737">[ 1791715 ] \" silently changed to \\ in argfiles</a>
     */
    @Test
    public void testTokenReaderNormalQuote() {
        final TokenReader reader = new TokenReader(createStream("\\\""));
        Assert.assertTrue("Before reading the last token, hasNext() must return true.", reader.hasNext());
        Assert.assertEquals("Expecting token", "\"", reader.next());
        Assert.assertFalse("After reading the last token, hasNext() must return false.", reader.hasNext());
    }

    /** Tests that <samp>\'</samp> returns <samp>'</samp>.
     * @see <a href="http://sourceforge.net/tracker/index.php?func=detail&aid=1791713&group_id=149894&atid=776737">[ 1791713 ] quotation marks removed from @arg files</a>
     * @see <a href="http://sourceforge.net/tracker/index.php?func=detail&aid=1791715&group_id=149894&atid=776737">[ 1791715 ] \" silently changed to \\ in argfiles</a>
     */
    @Test
    public void testTokenReaderNormalApos() {
        final TokenReader reader = new TokenReader(createStream("\\'"));
        Assert.assertTrue("Before reading the last token, hasNext() must return true.", reader.hasNext());
        Assert.assertEquals("Expecting token", "'", reader.next());
        Assert.assertFalse("After reading the last token, hasNext() must return false.", reader.hasNext());
    }

    /** Tests that <samp>"\n"</samp> returns <samp>\n</samp>.
     * @see <a href="http://sourceforge.net/tracker/index.php?func=detail&aid=1791713&group_id=149894&atid=776737">[ 1791713 ] quotation marks removed from @arg files</a>
     * @see <a href="http://sourceforge.net/tracker/index.php?func=detail&aid=1791715&group_id=149894&atid=776737">[ 1791715 ] \" silently changed to \\ in argfiles</a>
     */
    @Test
    public void testTokenReaderQuotesEscape() {
        final TokenReader reader = new TokenReader(createStream("\"\\n\""));
        Assert.assertTrue("Before reading the last token, hasNext() must return true.", reader.hasNext());
        Assert.assertEquals("Expecting token", "\\n", reader.next());
        Assert.assertFalse("After reading the last token, hasNext() must return false.", reader.hasNext());
    }

    /** Tests that <samp>"\""</samp> returns <samp>"</samp>.
     * @see <a href="http://sourceforge.net/tracker/index.php?func=detail&aid=1791713&group_id=149894&atid=776737">[ 1791713 ] quotation marks removed from @arg files</a>
     * @see <a href="http://sourceforge.net/tracker/index.php?func=detail&aid=1791715&group_id=149894&atid=776737">[ 1791715 ] \" silently changed to \\ in argfiles</a>
     */
    @Test
    public void testTokenReaderQuotesQuote() {
        final TokenReader reader = new TokenReader(createStream("\"\\\"\""));
        Assert.assertTrue("Before reading the last token, hasNext() must return true.", reader.hasNext());
        Assert.assertEquals("Expecting token", "\"", reader.next());
        Assert.assertFalse("After reading the last token, hasNext() must return false.", reader.hasNext());
    }

    /** Tests that <samp>"'"</samp> returns <samp>'</samp>.
     * @see <a href="http://sourceforge.net/tracker/index.php?func=detail&aid=1791713&group_id=149894&atid=776737">[ 1791713 ] quotation marks removed from @arg files</a>
     * @see <a href="http://sourceforge.net/tracker/index.php?func=detail&aid=1791715&group_id=149894&atid=776737">[ 1791715 ] \" silently changed to \\ in argfiles</a>
     */
    @Test
    public void testTokenReaderQuotesApos1() {
        final TokenReader reader = new TokenReader(createStream("\"'\""));
        Assert.assertTrue("Before reading the last token, hasNext() must return true.", reader.hasNext());
        Assert.assertEquals("Expecting token", "'", reader.next());
        Assert.assertFalse("After reading the last token, hasNext() must return false.", reader.hasNext());
    }

    /** Tests that <samp>"\'"</samp> returns <samp>\'</samp>.
     * @see <a href="http://sourceforge.net/tracker/index.php?func=detail&aid=1791713&group_id=149894&atid=776737">[ 1791713 ] quotation marks removed from @arg files</a>
     * @see <a href="http://sourceforge.net/tracker/index.php?func=detail&aid=1791715&group_id=149894&atid=776737">[ 1791715 ] \" silently changed to \\ in argfiles</a>
     */
    @Test
    public void testTokenReaderQuotesApos2() {
        final TokenReader reader = new TokenReader(createStream("\"\\'\""));
        Assert.assertTrue("Before reading the last token, hasNext() must return true.", reader.hasNext());
        Assert.assertEquals("Expecting token", "\\'", reader.next());
        Assert.assertFalse("After reading the last token, hasNext() must return false.", reader.hasNext());
    }

    /** Tests that <samp>'\n'</samp> returns <samp>\n</samp>.
     * @see <a href="http://sourceforge.net/tracker/index.php?func=detail&aid=1791713&group_id=149894&atid=776737">[ 1791713 ] quotation marks removed from @arg files</a>
     * @see <a href="http://sourceforge.net/tracker/index.php?func=detail&aid=1791715&group_id=149894&atid=776737">[ 1791715 ] \" silently changed to \\ in argfiles</a>
     */
    @Test
    public void testTokenReaderAposEscape1() {
        final TokenReader reader = new TokenReader(createStream("'\\n'"));
        Assert.assertTrue("Before reading the last token, hasNext() must return true.", reader.hasNext());
        Assert.assertEquals("Expecting token", "\\n", reader.next());
        Assert.assertFalse("After reading the last token, hasNext() must return false.", reader.hasNext());
    }

    /** Tests that <samp>'\\'</samp> returns <samp>\\</samp>.
     * @see <a href="http://sourceforge.net/tracker/index.php?func=detail&aid=1791713&group_id=149894&atid=776737">[ 1791713 ] quotation marks removed from @arg files</a>
     * @see <a href="http://sourceforge.net/tracker/index.php?func=detail&aid=1791715&group_id=149894&atid=776737">[ 1791715 ] \" silently changed to \\ in argfiles</a>
     */
    @Test
    public void testTokenReaderAposEscape2() {
        final TokenReader reader = new TokenReader(createStream("'\\\\'"));
        Assert.assertTrue("Before reading the last token, hasNext() must return true.", reader.hasNext());
        Assert.assertEquals("Expecting token", "\\\\", reader.next());
        Assert.assertFalse("After reading the last token, hasNext() must return false.", reader.hasNext());
    }

    /** Tests that <samp>'"'</samp> returns <samp>"</samp>.
     * @see <a href="http://sourceforge.net/tracker/index.php?func=detail&aid=1791713&group_id=149894&atid=776737">[ 1791713 ] quotation marks removed from @arg files</a>
     * @see <a href="http://sourceforge.net/tracker/index.php?func=detail&aid=1791715&group_id=149894&atid=776737">[ 1791715 ] \" silently changed to \\ in argfiles</a>
     */
    @Test
    public void testTokenReaderAposQuote() {
        final TokenReader reader = new TokenReader(createStream("'\"'"));
        Assert.assertTrue("Before reading the last token, hasNext() must return true.", reader.hasNext());
        Assert.assertEquals("Expecting token", "\"", reader.next());
        Assert.assertFalse("After reading the last token, hasNext() must return false.", reader.hasNext());
    }

    /** Creates an InputStream for reading from a String.
     * @param s String to read from.
     * @return InputStream created from s.
     */
    @NotNull private static InputStream createStream(@NotNull final String s) {
        return new ByteArrayInputStream(s.getBytes());
    }

} // class TokenReaderTest
