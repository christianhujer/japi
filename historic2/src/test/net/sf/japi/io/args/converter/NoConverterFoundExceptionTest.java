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

import net.sf.japi.io.args.converter.NoConverterFoundException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link NoConverterFoundException}.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
public class NoConverterFoundExceptionTest {

    /**
     * Tests that the target type is stored correctly.
     * @throws Exception (unexpected)
     */
    @SuppressWarnings({"ThrowableInstanceNeverThrown"})
    @Test
    public void testGetTargetType() throws Exception {
        final NoConverterFoundException exception = new NoConverterFoundException(Object.class);
        Assert.assertSame(exception.getTargetType(), Object.class);
    }

    /**
     * Tests that the target type is stored correctly.
     * @throws Exception (unexpected)
     */
    @Test
    public void testNoConverterFoundException() throws Exception {
        testGetTargetType();
    }

} // class NoConverterFoundExceptionTest
