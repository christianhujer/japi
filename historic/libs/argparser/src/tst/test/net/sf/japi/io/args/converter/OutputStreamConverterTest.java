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

package test.net.sf.japi.io.args.converter;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import junit.framework.Assert;
import net.sf.japi.io.args.converter.OutputStreamConverter;
import org.junit.After;
import org.junit.Test;

/**
 * Unit test for {@link OutputStreamConverter}.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
public class OutputStreamConverterTest {

    /** Remove the test file created by some test cases.
     * @throws IOException (unexpected).
     */
    @After
    public void removeTestFile() throws IOException {
        new File("foo.txt").delete();
    }

    /** Tests that {@link OutputStreamConverter#convert(String)} works with "-".
     * @throws Exception (unexpected).
     */
    @Test
    public void testStdin() throws Exception {
        final OutputStreamConverter testling = new OutputStreamConverter();
        Assert.assertEquals("Conversion of \"-\" must return System.out.", System.out, testling.convert("-"));
    }

    /** Tests that {@link OutputStreamConverter#convert(String)} works with a filename.
     * @throws Exception (unexpected).
     */
    @Test
    public void testFile() throws Exception {
        final OutputStreamConverter testling = new OutputStreamConverter();
        // TODO:2009-02-21:christianhujer:Choose a better file.
        final OutputStream in = testling.convert("foo.txt");
        Assert.assertNotNull("Expecting OutputStream.", in);
        // TODO:2009-02-21:christianhujer:Write to file then verify file contents.
    }

}
