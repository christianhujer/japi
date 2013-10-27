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

import java.util.ArrayList;
import java.util.ResourceBundle;
import net.sf.japi.io.args.BasicCommand;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link BasicCommand}.
 *
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
public class BasicCommandTest {

    /**
     * Tests that {@link BasicCommand#BasicCommand()} works.
     * @throws Exception (unexpected)
     */
    @Test
    public void testBasicCommand() throws Exception {
        final BasicCommand basicCommand = new CommandDummy();
        Assert.assertEquals("Command dummy must run successful.", 0, basicCommand.run(new ArrayList<String>()));
    }

    /**
     * Tests that {@link BasicCommand#setExiting(Boolean)} and {@link BasicCommand#isExiting()} work.
     * @throws Exception (unexpected)
     */
    @Test
    public void testExiting() throws Exception {
        final BasicCommand basicCommand = new CommandDummy();
        basicCommand.setExiting(false);
        Assert.assertFalse("Exiting value must be stored.", basicCommand.isExiting());
        basicCommand.setExiting(true);
        Assert.assertTrue("Exiting value must be stored.", basicCommand.isExiting());
    }

    /**
     * Tests that {@link BasicCommand#help()} works.
     * @throws Exception (unexpected)
     */
    @Test
    public void testHelp() throws Exception {
        final BasicCommand basicCommand = new CommandDummy();
        basicCommand.help();
    }

    /**
     * Tests that {@link BasicCommand#getBundle()} works.
     * @throws Exception (unexpected)
     */
    @Test
    public void testGetBundle() throws Exception {
        final BasicCommand basicCommand = new CommandDummy();
        final ResourceBundle bundle = basicCommand.getBundle();
        Assert.assertNotNull("CommandDummy must have a bundle.", bundle);
    }

    /**
     * Tests that {@link BasicCommand#getString(String)} returns the value from the correct bundle.
     * testGetString1 must be defined in RBMockCommand.properties with value String1FromDefaultBundle.
     * testGetString1 must be defined in RBMockCommandMyBundle.properties with value String1FromMyBundle.
     * testGetString2 must be defined in RBMockCommand.properties with value String2FromDefaultBundle.
     * testGetString2 must not be defined in RBMockCommandMyBundle.properties.
     */
    @Test
    public void testGetString() {
        final RBMockCommand mock = new RBMockCommand();
        mock.setReturnOwnBundle(false);
        Assert.assertEquals("if setReturnOwnBundle(false), getString() must retrieve from default bundle.", "String1FromDefaultBundle", mock.getString("testGetString1"));
        Assert.assertEquals("if setReturnOwnBundle(false), getString() must retrieve from default bundle.", "String2FromDefaultBundle", mock.getString("testGetString2"));
        mock.setReturnOwnBundle(true);
        Assert.assertEquals("if setReturnOwnBundle(true), getString() must retrieve from own bundle.", "String1FromMyBundle", mock.getString("testGetString1"));
        Assert.assertEquals("if setReturnOwnBundle(true), getString() must retrieve from own bundle.", "String2FromDefaultBundle", mock.getString("testGetString2"));
    }

    /**
     * Tests that {@link BasicCommand#getHelpHeader()} returns the value from the correct bundle.
     * Also, the help header must always be terminated with '\n', which will be auto-appended by {@link BasicCommand#getHelpHeader()}.
     * @see <a href="http://sourceforge.net/tracker/index.php?func=detail&amp;aid=1812417&amp;group_id=149894&amp;atid=776737">[ 1812417 ] auto-add newline to help header and footer</a>
     */
    @Test
    public void testGetHelpHeader() {
        final RBMockCommand mock = new RBMockCommand();
        mock.setReturnOwnBundle(false);
        Assert.assertEquals("if setReturnOwnBundle(false), getHelpHeader() must retrieve from default bundle and end with newline.", "HelpHeaderFromDefaultBundle\n", mock.getHelpHeader());
        mock.setReturnOwnBundle(true);
        Assert.assertEquals("if setReturnOwnBundle(true), getHelpHeader() must retrieve from own bundle and end with newline.", "HelpHeaderFromMyBundle\n", mock.getHelpHeader());
    }

    /**
     * Tests that {@link BasicCommand#getHelpFooter()} returns either the empty String or a String ending on "\n".
     * @see <a href="http://sourceforge.net/tracker/index.php?func=detail&amp;aid=1812417&amp;group_id=149894&amp;atid=776737">[ 1812417 ] auto-add newline to help header and footer</a>
     */
    @Test
    public void testGetHelpFooter() {
        final RBMockCommand mock = new RBMockCommand();
        mock.setReturnOwnBundle(false);
        Assert.assertEquals("if setReturnOwnBundle(false), getHelpFooter() must retrieve from default bundle and end with newline.", "HelpFooterFromDefaultBundle\n", mock.getHelpFooter());
        mock.setReturnOwnBundle(true);
        Assert.assertEquals("if setReturnOwnBundle(true), getHelpFooter() must retrieve from own bundle and end with newline.", "HelpFooterFromMyBundle\n", mock.getHelpFooter());
    }

} // class BasicCommandTest
