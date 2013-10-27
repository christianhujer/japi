/*
 * Copyright (C) 2009  Christian Hujer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package test.net.sf.japi.cstyle;

import net.sf.japi.cstyle.CStyle;
import org.junit.Assert;
import org.junit.Test;

/** Unit test for {@link CStyle}.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
public class CStyleTest {

    /** Tests that {@link CStyle#parse(char)} works. */
    @Test public void testParse() {
        final CStyle testling = new CStyle();
        final String testString = "if (foo > 'x') { // check x\n    printf(\"%d\");\n}";
        final char[] chars = testString.toCharArray();
        for (final char c : chars) {
            testling.parse(c);
        }
        Assert.assertEquals("mode now must be NORMAL.", CStyle.PhysicalMode.NORMAL, testling.getPhysicalMode());
    }

    /** Tests that {@link CStyle#reset()} works. */
    @Test public void testReset() {
        final CStyle testling = new CStyle();
        testling.parse('\'');
        Assert.assertEquals("previous mode must stay NORMAL.", CStyle.PhysicalMode.NORMAL, testling.getPreviousPhysicalMode());
        Assert.assertEquals("mode now must be CHAR.", CStyle.PhysicalMode.CHAR, testling.getPhysicalMode());
        testling.reset();
        Assert.assertEquals("After reset, mode must be NORMAL", CStyle.PhysicalMode.NORMAL, testling.getPhysicalMode());
        Assert.assertEquals("After reset, previous mode must be NORMAL", CStyle.PhysicalMode.NORMAL, testling.getPreviousPhysicalMode());
    }

} // class CStyleTest
