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

import java.util.Arrays;
import net.sf.japi.io.args.UnknownOptionException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link UnknownOptionException}.
 *
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
public class UnknownOptionExceptionTest {

    /**
     * Tests that {@link UnknownOptionException#UnknownOptionException(String[])} works.
     * @throws Exception (unexpected)
     */
    @SuppressWarnings({"ThrowableInstanceNeverThrown"})
    @Test
    public void testUnknownOptionException() throws Exception {
        final String[] options = { "foo", "bar" };
        final UnknownOptionException exception = new UnknownOptionException(options.clone());
        Assert.assertTrue("Options which have been unknown must be stored with the exceptions.", Arrays.equals(options, exception.getUnknownOptions()));
    }

    /**
     * Tests that {@link UnknownOptionException#getUnknownOptions()} works.
     * @throws Exception (unexpected)
     */
    @Test
    public void testGetUnknownOptions() throws Exception {
        testUnknownOptionException();
    }

} // class UnknownOptionExceptionTest
