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
import net.sf.japi.io.args.RequiredOptionsMissingException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link RequiredOptionsMissingException}.
 *
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
public class RequiredOptionsMissingExceptionTest {

    /**
     * Tests that {@link RequiredOptionsMissingException#RequiredOptionsMissingException(String[])} works.
     * @throws Exception (unexpected)
     */
    @SuppressWarnings({"ThrowableInstanceNeverThrown"})
    @Test
    public void testRequiredOptionsMissingException() throws Exception {
        final String[] options = { "foo", "bar" };
        final RequiredOptionsMissingException exception = new RequiredOptionsMissingException(options.clone());
        Assert.assertTrue(Arrays.equals(exception.getMissingOptions(), options));
    }

    /**
     * Tests that {@link RequiredOptionsMissingException#getMissingOptions()} works.
     * @throws Exception (unexpected)
     */
    @Test
    public void testGetMissingOptions() throws Exception {
        testRequiredOptionsMissingException();
    }

    /**
     * Tests that {@link RequiredOptionsMissingException#RequiredOptionsMissingException(String[])} throws an IllegalArgumentException in case no missing options were given.
     * @throws IllegalArgumentException (expected).
     */
    @SuppressWarnings({"ThrowableInstanceNeverThrown"})
    @Test(expected = IllegalArgumentException.class)
    public void testNoMissingOptions() throws Exception {
        new RequiredOptionsMissingException();
    }

} // class RequiredOptionsMissingExceptionTest
