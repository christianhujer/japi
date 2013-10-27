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

import java.io.File;
import java.io.FileFilter;
import net.sf.japi.util.filter.file.EndingFileFilter;
import org.junit.Assert;
import org.junit.Test;

/** Test for {@link EndingFileFilter}.
 * @author Andreas Kirschbaum
 * @since 0.1
 */
public class EndingFileFilterTest {

    /** Test case for {@link EndingFileFilter#accept(File)}. */
    @Test
    public void testAccept() {
        final File file1 = new File("test.abc");
        checkFileFilter(new boolean[] { true, false, true, false, }, file1, ".abc", ".def", ".ghi");
        checkFileFilter(new boolean[] { false, true, false, true, }, file1, ".def", ".ghi");

        final File dir1 = new File("test.abc") {
            /** The serial version UID. */
            private static final long serialVersionUID = 1;

            /** {@inheritDoc} */
            @Override
            public boolean isDirectory() {
                return true;
            }
        };
        checkFileFilter(new boolean[] { false, false, true, true, }, dir1, ".abc", ".def", ".ghi");
        checkFileFilter(new boolean[] { false, false, true, true, }, dir1, ".def", ".ghi");
    }

    /** Checks that {@link EndingFileFilter#accept(File)} returns the expected result.
     * @param expectedResults the expected results from accept(File)
     * @param file the file to pass to accept(File)
     * @param endings parameter for EndingFileFilter constructor
     */
    private static void checkFileFilter(final boolean[] expectedResults, final File file, final String... endings) {
        Assert.assertEquals(4, expectedResults.length);
        checkFileFilter(false, false, expectedResults[0], file, endings);
        checkFileFilter(false, true, expectedResults[1], file, endings);
        checkFileFilter(true, false, expectedResults[2], file, endings);
        checkFileFilter(true, true, expectedResults[3], file, endings);
    }

    /** Checks that {@link EndingFileFilter#accept(File)} returns the expected result.
     * @param acceptDirectories parameter for EndingFileFilter constructor
     * @param negate parameter for EndingFileFilter constructor
     * @param expectedResult the expected result of accept(File)
     * @param file the file to pass to accept(File)
     * @param endings parameter for EndingFileFilter constructor
     */
    private static void checkFileFilter(final boolean acceptDirectories, final boolean negate, final boolean expectedResult, final File file, final String... endings) {
        final FileFilter fileFilter = new EndingFileFilter(acceptDirectories, negate, "description", endings);
        Assert.assertEquals(expectedResult, fileFilter.accept(file));
    }

} // class EndingFileFilterTest
