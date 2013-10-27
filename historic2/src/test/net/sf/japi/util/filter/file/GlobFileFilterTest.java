/*
 * Copyright (C) 2009  Andreas Kirschbaum.
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

package test.net.sf.japi.util.filter.file;

import java.util.regex.Pattern;
import net.sf.japi.util.filter.file.GlobFileFilter;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

/** Test for {@link GlobFileFilter}.
 * @author Andreas Kirschbaum
 */
public class GlobFileFilterTest {

    /** Test case for {@link GlobFileFilter#createPatternForGlob(String)}.
     * @noinspection JUnitTestMethodWithNoAssertions
     */
    @Test
    public void testConcatByte() {
        checkCreatePatternForGlob("abcd", "abcd", true);
        checkCreatePatternForGlob("abcd", "abc", false);
        checkCreatePatternForGlob("abcd", "bcd", false);
        checkCreatePatternForGlob("a", "abc", false);
        checkCreatePatternForGlob("b", "abc", false);
        checkCreatePatternForGlob("c", "abc", false);
        checkCreatePatternForGlob("^a", "aba", false);
        checkCreatePatternForGlob("$a", "aba", false);
        checkCreatePatternForGlob("a^", "aba", false);
        checkCreatePatternForGlob("a$", "aba", false);
        checkCreatePatternForGlob("^aba", "^aba", true);
        checkCreatePatternForGlob("$aba", "$aba", true);
        checkCreatePatternForGlob("aba^", "aba^", true);
        checkCreatePatternForGlob("aba$", "aba$", true);
        checkCreatePatternForGlob("abc*def", "abcef", false);
        checkCreatePatternForGlob("abc*def", "abcdef", true);
        checkCreatePatternForGlob("abc*def", "abcxdef", true);
        checkCreatePatternForGlob("abc*def", "abcagagadef", true);
        checkCreatePatternForGlob("abc?def", "abcdef", false);
        checkCreatePatternForGlob("abc?def", "abcxdef", true);
        checkCreatePatternForGlob("abc?def", "abcxxdef", false);
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 256; i++) {
            final char ch = (char) i;
            if (ch != '*' && ch != '?') {
                final String s = Character.toString(ch);
                checkCreatePatternForGlob(s, s, true);
                sb.append(ch);
            }
        }
        final String s = sb.toString();
        checkCreatePatternForGlob(s, s, true);
        checkCreatePatternForGlob("\\Q*\\E*\\E\\Q", "\\Q\\Eabc\\E\\Q\\E\\Q", true);
    }

    /**
     * Calls {@link GlobFileFilter#createPatternForGlob(String)}, matches the given name against it and check for the expected result.
     * @param glob the pattern to check
     * @param name the name to match against the pattern
     * @param expectedResult the expected result
     */
    private static void checkCreatePatternForGlob(@NotNull final String glob, @NotNull final CharSequence name, final boolean expectedResult) {
        final String pattern = GlobFileFilter.createPatternForGlob(glob);
        Assert.assertEquals("glob=" + glob + ", pattern=" + pattern, expectedResult, Pattern.compile(pattern).matcher(name).matches());
    }

}
