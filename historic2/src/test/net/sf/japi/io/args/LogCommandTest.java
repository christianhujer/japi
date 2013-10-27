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

import net.sf.japi.io.args.LogCommand;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link LogCommand}.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
public class LogCommandTest {

    /** Tests that a {@link LogCommand} has Logging.
     * @throws Exception (unexpected)
     */
    @Test
    public void testLogLevel() throws Exception {
        final LogCommand testling = new DummyLogCommand();
        Assert.assertNotNull("Expecting testling to have a log.", testling.getLog());
        //testling.setLevel(new LogLevelConverter().convert("FINEST")); // FIXME starts logging everything, including AWT, X11, URL etc.
    }
}
