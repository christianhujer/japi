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

import java.util.List;
import net.sf.japi.io.args.CommandWithHelp;
import net.sf.japi.io.args.Command;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

/**
 * TODO:2009-02-21:christianhujer:Documentation.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
public class CommandWithHelpTest {

    /** Tests that {@link CommandWithHelp#isExiting()} returns <code>false</code>. */
    @Test
    public void testExiting() {
        final Command testling = new CommandWithHelp() {
            public int run(@NotNull final List<String> args) {
                return 0;
            }
        };
        Assert.assertFalse("Expecting a command to not be exiting by default.", testling.isExiting());
    }

    /** Tests that {@link CommandWithHelp#isCheckRequiredOptions()} returns <code>true</code>. */
    @Test
    public void testCheckRequiredOptions() {
        final Command testling = new CommandWithHelp() {
            public int run(@NotNull final List<String> args) {
                return 0;
            }
        };
        Assert.assertTrue("Expecting a command to be checking for required optinos by default.", testling.isCheckRequiredOptions());
    }
}
