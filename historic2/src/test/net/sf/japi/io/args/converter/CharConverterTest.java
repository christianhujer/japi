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

import java.util.Arrays;
import java.util.Collection;
import net.sf.japi.io.args.converter.CharConverter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * Tests for {@link CharConverter}.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
@RunWith(Parameterized.class)
public class CharConverterTest extends AbstractPrimitiveConverterTest<Character, CharConverter> {

    /** Creates the parameter arrays for the parameterized test execution.
     * @return The parameter arrays.
     * @see #CharConverterTest(Class)
     */
    @Parameterized.Parameters public static Collection getClasses() {
        return Arrays.asList(new Class[][] {
                {char.class},
                {Character.class}
        });
    }

    /**
     * Create an CharConverterTest.
     * @param clazz Class to test with.
     * @throws Exception in case of setup problems.
     */
    public CharConverterTest(final Class<Character> clazz) throws Exception {
        super(clazz, CharConverter.class);
    }

    /**
     * Tests that instanciating a CharConverter works.
     * @throws Exception (unexpected)
     */
    @Test
    public void testCharConverter() throws Exception {
        // Tested by this test class's superclass constructor.
    }

    /**
     * Tests that converting an arbitrary text throws a NumberFormatException.
     * @throws NumberFormatException Expected exception that's thrown if the test case is successful.
     * @throws Exception In case of unexpected errors.
     */
    @Test(expected = NumberFormatException.class)
    public void testConvertWithText() throws Exception {
        getConverter().convert("foo");
    }

    /**
     * Tests that converting decimal (10-base) char numbers works correclty.
     * @throws Exception In case of unexpected errors.
     */
    @Test
    public void testConvertDecimalNumbers() throws Exception {
        for (final char number : new char[] { Character.MIN_VALUE, (char) -100, (char) -1, 0, 1, 100, Character.MAX_VALUE }) {
            Assert.assertEquals(number, (char) getConverter().convert(Character.toString(number)));
        }
    }

    /**
     * Tests that converting hexadecimal (16-base) char numbers works correctly.
     * @throws Exception In case of unexpected errors.
     */
    @Test
    public void testConvertHexadecimalNumbers() throws Exception {
        Assert.assertEquals(' ', (char) getConverter().convert(" "));
        Assert.assertEquals('$', (char) getConverter().convert("$"));
        Assert.assertEquals('A', (char) getConverter().convert("A"));
    }

    /**
     * Tests that converting octal (8-base) char numbers works correctly.
     * @throws Exception In case of unexpected errors.
     */
    @Test
    public void testConvertOctalNumbers() throws Exception {
        Assert.assertEquals('b', (char) getConverter().convert("b"));
    }

} // class CharConverterTest
