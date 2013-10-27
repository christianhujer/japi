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

import net.sf.japi.io.args.TerminalException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link TerminalException}.
 *
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
public class TerminalExceptionTest {

    /**
     * Tests that {@link TerminalException#TerminalException()} works.
     * @throws Exception (unexpected)
     */
    @SuppressWarnings({"ThrowableInstanceNeverThrown"})
    @Test
    public void testTerminalException() throws Exception {
        final TerminalException exception = new TerminalException();
        Assert.assertEquals("The default return code for TerminalException must be 0 (no error, e.g. for --help).", 0, exception.getReturnCode());
    }

    /**
     * Tests that {@link TerminalException#getReturnCode()} works.
     * @throws Exception (unexpected)
     */
    @SuppressWarnings({"ThrowableInstanceNeverThrown"})
    @Test
    public void testGetReturnCode() throws Exception {
        final TerminalException exception = new TerminalException(1);
        Assert.assertEquals("A specified return code must be stored.", 1, exception.getReturnCode());
    }

} // class TerminalExceptionTest
