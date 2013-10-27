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

import junit.framework.Assert;
import net.sf.japi.io.args.converter.InputStreamConverter;
import org.junit.Test;

import java.io.InputStream;

/**
 * Unit test for {@link InputStreamConverter}.
 *
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>.
 */
public class InputStreamConverterTest {

    /** Tests that {@link InputStreamConverter#convert(String)} works with "-".
     * @throws Exception (unexpected).
     */
    @Test
    public void testStdin() throws Exception {
        final InputStreamConverter testling = new InputStreamConverter();
        Assert.assertEquals("Conversion of \"-\" must return System.in.", System.in, testling.convert("-"));
    }

    /** Tests that {@link InputStreamConverter#convert(String)} works with a URL.
     * @throws Exception (unexpected).
     */
    @Test
    public void testUrl() throws Exception {
        final InputStreamConverter testling = new InputStreamConverter();
        // TODO:2009-02-21:christianhujer:Choose a better resource.
        final InputStream in = testling.convert("http://www.w3.org/");
        Assert.assertNotNull("Expecting InputStream.", in);
        // TODO:2009-02-21:christianhujer:Verify resource contents.
    }

    /** Tests that {@link InputStreamConverter#convert(String)} works with a filename.
     * @throws Exception (unexpected).
     */
    @Test
    public void testFile() throws Exception {
        final InputStreamConverter testling = new InputStreamConverter();
        // TODO:2009-02-21:christianhujer:Choose a better file.
        final InputStream in = testling.convert(".gitignore");
        Assert.assertNotNull("Expecting InputStream.", in);
        // TODO:2009-02-21:christianhujer:Verify file contents.
    }
}
