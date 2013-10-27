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

package test.net.sf.japi.tools.replacer;

import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Pattern;

import net.sf.japi.tools.replacer.Substitution;

/** Tests that {@link Substitution}.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
@SuppressWarnings({"InstanceMethodNamingConvention"})
public class SubstitutionTest {

    /** Tests that {@link Substitution#Substitution(String[])} works. */
    @Test
    public void testSimpleSubstitution() {
        final Substitution testling = new Substitution("foo", "bar");
        Assert.assertEquals("Pattern String must be stored.", "foo", testling.getPatternString());
        Assert.assertEquals("Replacement String must be stored.", "bar", testling.getReplacement());
        Assert.assertEquals("If flags are not specified, they must be 0.", 0, testling.getFlags());
        final String testText = "my foo my foo fuzz";
        final String replaced = testling.replace(testText);
        Assert.assertEquals("Substitution(\"foo\", \"bar\") must replace all occurrences of foo by bar.", "my bar my foo fuzz", replaced);
    }

    /** Tests that {@link Substitution#parseRegex(CharSequence)} works for simple substitutions. */
    @Test
    public void testParseSimple() {
        final Substitution testling = new Substitution("s/foo/bar/g");
        Assert.assertEquals("Pattern string must be the regex.", "foo", testling.getPatternString());
        Assert.assertEquals("Replacement string must be the substitution.", "bar", testling.getReplacement());
        Assert.assertEquals("No flags specified - flags need to be empty.", 0, testling.getFlags());
        final String testText = "my foo my foo fuzz";
        final String replaced = testling.replace(testText);
        Assert.assertEquals("s/foo/bar/g must replace all occurrences of foo by bar.", "my bar my bar fuzz", replaced);
    }

    /** Tests that {@link Substitution#parseRegex(CharSequence)} works when the substitution contains an escaped slash. */
    @Test
    public void testParseRegexEscapedSlash() {
        final String[] parsed = Substitution.parseRegex("s/f\\/oo/bar/");
        Assert.assertEquals("Expecting length of parsed regex array to be 3.", 3, parsed.length);
        Assert.assertEquals("First element must be regex.", "f\\/oo", parsed[0]);
        Assert.assertEquals("Second element must be substitution.", "bar"   , parsed[1]);
        Assert.assertEquals("Third element must be flags.", ""      , parsed[2]);
    }

    /** Tests that {@link Substitution#parseRegex(CharSequence)} throws an exception with a malformed substitution specification. */
    @Test(expected = IllegalArgumentException.class)
    public void testParseRegexBogus() {
        Substitution.parseRegex("s/f\\/oo/bar");
    }

    /** Tests that {@link Substitution#parseFlags(String)} works with null flags. */
    @Test
    public void testParseFlagNull() {
        final int flags = Substitution.parseFlags(null);
        Assert.assertEquals("Expecting null flags to be zero.", 0, flags);
    }

    /** Tests that {@link Substitution#parseFlags(String)} works with empty flags. */
    @Test
    public void testParseFlagEmpty() {
        final int flags = Substitution.parseFlags("");
        Assert.assertEquals("Expecting null flags to be zero.", 0, flags);
    }

    /** Tests that {@link Substitution#parseFlags(String)} works with "d" flag. */
    @Test
    public void testParseFlagD() {
        final int flags = Substitution.parseFlags("d");
        Assert.assertEquals("Expecting d flag to be UNIX LINES.", Pattern.UNIX_LINES, flags);
    }

    /** Tests that {@link Substitution#parseFlags(String)} works with "i" flag. */
    @Test
    public void testParseFlagI() {
        final int flags = Substitution.parseFlags("i");
        Assert.assertEquals("Expecting i flag to be CASE INSENSITIVE.", Pattern.CASE_INSENSITIVE, flags);
    }

    /** Tests that {@link Substitution#parseFlags(String)} works with "x" flag. */
    @Test
    public void testParseFlagX() {
        final int flags = Substitution.parseFlags("x");
        Assert.assertEquals("Expecting x flag to be COMMENTS.", Pattern.COMMENTS, flags);
    }

    /** Tests that {@link Substitution#parseFlags(String)} works with "m" flag. */
    @Test
    public void testParseFlagM() {
        final int flags = Substitution.parseFlags("m");
        Assert.assertEquals("Expecting m flag to be MULTI_LINE.", Pattern.MULTILINE, flags);
    }

    /** Tests that {@link Substitution#parseFlags(String)} works with "l" flag. */
    @Test
    public void testParseFlagL() {
        final int flags = Substitution.parseFlags("l");
        Assert.assertEquals("Expecting l flag to be LITERAL.", Pattern.LITERAL, flags);
    }

    /** Tests that {@link Substitution#parseFlags(String)} works with "s" flag. */
    @Test
    public void testParseFlagS() {
        final int flags = Substitution.parseFlags("s");
        Assert.assertEquals("Expecting s flag to be DOTALL.", Pattern.DOTALL, flags);
    }

    /** Tests that {@link Substitution#parseFlags(String)} works with "u" flag. */
    @Test
    public void testParseFlagU() {
        final int flags = Substitution.parseFlags("u");
        Assert.assertEquals("Expecting u flag to be UNICODE CASE.", Pattern.UNICODE_CASE, flags);
    }

    /** Tests that {@link Substitution#parseFlags(String)} works with "c" flag. */
    @Test
    public void testParseFlagC() {
        final int flags = Substitution.parseFlags("c");
        Assert.assertEquals("Expecting c flag to be CANON EQ.", Pattern.CANON_EQ, flags);
    }

    /** Tests that {@link Substitution#parseFlags(String)} works with multiple flags. */
    @Test
    public void testParseFlags() {
        final int flags = Substitution.parseFlags("idmsux");
        Assert.assertEquals("Expecting idmsux flags to be CASE_INSENSITIVE + UNIX_LINES + MULTILINE + DOTALL + UNICODE_CASE + COMMENTS.", Pattern.CASE_INSENSITIVE | Pattern.UNIX_LINES | Pattern.MULTILINE | Pattern.DOTALL | Pattern.UNICODE_CASE | Pattern.COMMENTS, flags);
    }

    /** Tests that {@link Substitution#parseFlags(String)} throws an exception with bogus flags. */
    @Test(expected = IllegalArgumentException.class)
    public void testParseFlagsBogus() {
        Substitution.parseFlags("qwe");
    }

    /** Tests that {@link Substitution#Substitution(CharSequence)} interprets the d flag. */
    @Test
    public void testConstructFlagD() {
        final Substitution subst = new Substitution("s/foo/bar/d");
        Assert.assertEquals("Expecting d flag to activate UNIX_LINES,", Pattern.UNIX_LINES, subst.getFlags());
        Assert.assertFalse("Expecting d flag to not set file mode.", subst.isFile());
        Assert.assertFalse("Expecting d flag to not set global mode.", subst.isGlobal());
    }

    /** Tests that {@link Substitution#Substitution(CharSequence)} interprets the i flag. */
    @Test
    public void testConstructFlagI() {
        final Substitution subst = new Substitution("s/foo/bar/i");
        Assert.assertEquals("Expecting d flag to activate CASE_INSENSITIVE,", Pattern.CASE_INSENSITIVE, subst.getFlags());
    }

    /** Tests that {@link Substitution#Substitution(CharSequence)} interprets the x flag. */
    @Test
    public void testConstructFlagX() {
        final Substitution subst = new Substitution("s/foo/bar/x");
        Assert.assertEquals("Expecting d flag to activate COMMENTS,", Pattern.COMMENTS, subst.getFlags());
    }

    /** Tests that substitution works on multilines. */
    @Test
    public void testInsertAtEachStartOfLine() {
        final Substitution subst = new Substitution("s/^/_/");
        final String input = "foo\nbar\nbuzz\n";
        final String expected = "_foo\n_bar\n_buzz\n";
        final String actual = subst.replace(input);
        Assert.assertEquals("Expecting multiline substitution for ^ to work,", expected, actual);
    }

    /** Tests that substitution works on multilines. */
    @Test
    public void testInsertAtEachEndOfLine() {
        final Substitution subst = new Substitution("s/$/_/");
        final String input = "foo\nbar\nbuzz\n";
        final String expected = "foo_\nbar_\nbuzz_\n";
        final String actual = subst.replace(input);
        Assert.assertEquals("Expecting multiline substitution for ^ to work,", expected, actual);
    }

    /** Tests that replacing the tab character works. */
    @Test
    public void testReplaceTab() {
        final Substitution subst = new Substitution("s/\\t/_/");
        final String input = "foo\tbar";
        final String expected = "foo_bar";
        final String actual = subst.replace(input);
        Assert.assertEquals("Expecting \\t to match tab,", expected, actual);
    }

    /** Tests that replacing the newline character works. */
    @Test
    public void testReplaceNewline() {
        final Substitution subst = new Substitution("s/\\n/_/");
        final String input = "foo\nbar";
        final String expected = "foo_bar";
        final String actual = subst.replace(input);
        Assert.assertEquals("Expecting \\n to match newline,", expected, actual);
    }

    /** Tests that replacing the carriage return character works. */
    @Test
    public void testReplaceCarriageReturn() {
        final Substitution subst = new Substitution("s/\\r/_/");
        final String input = "foo\rbar";
        final String expected = "foo_bar";
        final String actual = subst.replace(input);
        Assert.assertEquals("Expecting \\r to match carriage return,", expected, actual);
    }

    /** Tests that replacing the form feed character works. */
    @Test
    public void testReplaceFormFeed() {
        final Substitution subst = new Substitution("s/\\f/_/");
        final String input = "foo\fbar";
        final String expected = "foo_bar";
        final String actual = subst.replace(input);
        Assert.assertEquals("Expecting \\f to match form feed,", expected, actual);
    }

    /** Tests that replacing the alert character works. */
    @Test
    public void testReplaceAlert() {
        final Substitution subst = new Substitution("s/\\a/_/");
        final String input = "foo\u0007bar";
        final String expected = "foo_bar";
        final String actual = subst.replace(input);
        Assert.assertEquals("Expecting \\a to match alert,", expected, actual);
    }

    /** Tests that replacing the escape character works. */
    @Test
    public void testReplaceEscape() {
        final Substitution subst = new Substitution("s/\\e/_/");
        final String input = "foo\u001bbar";
        final String expected = "foo_bar";
        final String actual = subst.replace(input);
        Assert.assertEquals("Expecting \\e to match escape,", expected, actual);
    }

    /** More sophisticated sample test case, remove trailing whitespace. */
    @Test
    public void testRemoveTrailingWhitespace() {
        final Substitution subst = new Substitution("s/[ \\t]+$//");
        final String input = "foo   \nbar\t\nbuzz \n";
        final String expected = "foo\nbar\nbuzz\n";
        final String actual = subst.replace(input);
        Assert.assertEquals("Expecting remove trailing whitespace to work,", expected, actual);
    }

    /** More sophisticated sample test case, change from Allman to 1TBS. */
    @Test
    public void testReplaceAllmanTo1TBS() {
        final Substitution subst = new Substitution("s/$\\s+\\{/ {/mfg");
        final String input = "class X\n{\n    void foo()\n    {\n        foo();\n    }\n}\n";
        final String expected = "class X {\n    void foo() {\n        foo();\n    }\n}\n";
        final String actual = subst.replace(input);
        Assert.assertEquals("Expecting AllmanTo1TBS to work,", expected, actual);
    }

    /** More sophisticated sample test case, change from Allman to 1TBS including EOL comments. */
    @Test
    public void testReplaceAllmanTo1TBSWithEOLComments() {
        final Substitution subst = new Substitution("s/\\s*?( ?\\/\\/.*)$\\s+\\{/ {$1/mfg");
        final String input = "class X //foo\n{\n    void foo() //bar\n    {\n        foo();\n    }\n}\n";
        final String expected = "class X { //foo\n    void foo() { //bar\n        foo();\n    }\n}\n";
        final String actual = subst.replace(input);
        Assert.assertEquals("Expecting AllmanTo1TBS to work,", expected, actual);
    }

    /** More sophisticated sample test case, change from Allman to 1TBS including comments. */
    @Test
    public void testReplaceAllmanTo1TBSWithComments() {
        final Substitution subst = new Substitution("s/\\s*?( ?(?:\\/\\/.*|\\/\\*.*\\*\\/))$\\s+\\{/ {$1/mfg");
        final String input = "class X /*foo*/\n{\n    void foo() //bar\n    {\n        foo();\n    }\n}\n";
        final String expected = "class X { /*foo*/\n    void foo() { //bar\n        foo();\n    }\n}\n";
        final String actual = subst.replace(input);
        Assert.assertEquals("Expecting AllmanTo1TBS to work,", expected, actual);
    }

    /** Tests that replacing by the tab character works. */
    @Test
    public void testReplaceByTab() {
        final Substitution subst = new Substitution("s/_/\\t/");
        final String input = "foo_bar";
        final String expected = "foo\tbar";
        final String actual = subst.replace(input);
        Assert.assertEquals("Expecting \\t to replace to tab,", expected, actual);
    }

    /** Tests that replacing by the newline character works. */
    @Test
    public void testReplaceByNewline() {
        final Substitution subst = new Substitution("s/_/\\n/");
        final String input = "foo_bar";
        final String expected = "foo\nbar";
        final String actual = subst.replace(input);
        Assert.assertEquals("Expecting \\t to replace to newline,", expected, actual);
    }

    /** Tests that replacing by the carriage return character works. */
    @Test
    public void testReplaceByCarriageReturn() {
        final Substitution subst = new Substitution("s/_/\\r/");
        final String input = "foo_bar";
        final String expected = "foo\rbar";
        final String actual = subst.replace(input);
        Assert.assertEquals("Expecting \\r to replace to carriage return,", expected, actual);
    }

    /** Tests that replacing by the form feed character works. */
    @Test
    public void testReplaceByFormFeed() {
        final Substitution subst = new Substitution("s/_/\\f/");
        final String input = "foo_bar";
        final String expected = "foo\fbar";
        final String actual = subst.replace(input);
        Assert.assertEquals("Expecting \\f to replace to form feed,", expected, actual);
    }

    /** Tests that replacing by the alert character works. */
    @Test
    public void testReplaceByAlert() {
        final Substitution subst = new Substitution("s/_/\\a/");
        final String input = "foo_bar";
        final String expected = "foo\u0007bar";
        final String actual = subst.replace(input);
        Assert.assertEquals("Expecting \\a to replace to alert,", expected, actual);
    }

    /** Tests that replacing by the escape character works. */
    @Test
    public void testReplaceByEscape() {
        final Substitution subst = new Substitution("s/_/\\e/");
        final String input = "foo_bar";
        final String expected = "foo\u001bbar";
        final String actual = subst.replace(input);
        Assert.assertEquals("Expecting \\e to replace to escape,", expected, actual);
    }

    /** Tests that replacing by the dollar character works. */
    @Test
    public void testReplaceByDollar() {
        final Substitution subst = new Substitution("s/_/\\$/");
        final String input = "foo_bar";
        final String expected = "foo$bar";
        final String actual = subst.replace(input);
        Assert.assertEquals("Expecting \\$ to replace to $,", expected, actual);
    }

    /** Tests that replacing by the backslash character works. */
    @Test
    public void testReplaceByBackslash() {
        final Substitution subst = new Substitution("s/_/\\\\/");
        final String input = "foo_bar";
        final String expected = "foo\\bar";
        final String actual = subst.replace(input);
        Assert.assertEquals("Expecting \\\\ to replace to \\,", expected, actual);
    }

    /** Tests that an unsupported escape sequence is reported. */
    @Test(expected = IllegalArgumentException.class)
    public void testReplacewithUnsupportedEscapeThrowsException() {
        final Substitution subst = new Substitution("s/_/\\l/");
        final String input = "foo_bar";
        subst.replace(input);
    }

    /** Tests that replacing by references works. */
    @Test
    public void testReplaceWithReference() {
        final Substitution subst = new Substitution("s/(foo)/$1$1/");
        final String input = "bar foo bar";
        final String expected = "bar foofoo bar";
        final String actual = subst.replace(input);
        Assert.assertEquals("Expecting $1 to replace to first group,", expected, actual);
    }
}
