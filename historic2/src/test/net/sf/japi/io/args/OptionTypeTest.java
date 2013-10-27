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

import java.util.Locale;
import net.sf.japi.io.args.OptionType;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link OptionType}.
 *
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
public class OptionTypeTest {

    /**
     * Tests that {@link OptionType#getName()} and {@link OptionType#getName(Locale)} work.
     * @throws Exception (unexpected)
     */
    @Test
    public void testGetName() throws Exception {
        for (final OptionType optionType : OptionType.values()) {
            Assert.assertNotNull(optionType.getName());
        }
        for (final OptionType optionType : OptionType.values()) {
            Assert.assertNotNull(optionType.getName(Locale.getDefault()));
        }
    }

    /**
     * Tests that {@link OptionType#toString()} works.
     * @throws Exception (unexpected)
     */
    @Test
    public void testToString() throws Exception {
        for (final OptionType optionType : OptionType.values()) {
            Assert.assertNotNull(optionType.toString());
        }
    }

    /**
     * Tests that {@link OptionType#getDescription()} and {@link OptionType#getDescription(Locale)} work.
     * @throws Exception (unexpected)
     */
    @Test
    public void testGetDescription() throws Exception {
        for (final OptionType optionType : OptionType.values()) {
            Assert.assertNotNull(optionType.getDescription());
        }
        for (final OptionType optionType : OptionType.values()) {
            Assert.assertNotNull(optionType.getDescription(Locale.getDefault()));
        }
    }

} // class OptionTypeTest
