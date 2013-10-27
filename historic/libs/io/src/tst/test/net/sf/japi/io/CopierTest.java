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

package test.net.sf.japi.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import net.sf.japi.io.Copier;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link Copier}.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
public class CopierTest {

    /** Tests that Copier copies from one stream to another. */
    @Test
    public void testCopy() {
        final byte[] input = { 0x00, 0x01, 0x7F, 0x03, 0x40 };
        final byte[] verification = input.clone();
        Assert.assertNotSame("Expecting verification to be a new array.", input, verification);
        final ByteArrayInputStream in = new ByteArrayInputStream(input);
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final Copier testling = new Copier(in, out);
        testling.run();
        final byte[] output = out.toByteArray();
        Assert.assertTrue("Expecting input to be unchanged.", Arrays.equals(verification, input));
        Assert.assertTrue("Expecting output to be like input.", Arrays.equals(verification, output));
        Assert.assertNotSame("Expecting output to be a new array.", input, output);
        Assert.assertNotSame("Expecting output to be a new array.", verification, output);
    }
}
