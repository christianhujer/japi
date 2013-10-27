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
import net.sf.japi.io.args.converter.IntegerConverter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * Tests for {@link IntegerConverter}.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
@RunWith(Parameterized.class)
public class IntegerConverterTest extends AbstractPrimitiveConverterTest<Integer, IntegerConverter> {

    /** Creates the parameter arrays for the parameterized test execution.
     * @return The parameter arrays.
     * @see #IntegerConverterTest(Class)
     */
    @Parameterized.Parameters public static Collection getClasses() {
        return Arrays.asList(new Class[][] {
                {int.class},
                {Integer.class}
        });
    }

    /**
     * Create an IntegerConverterTest.
     * @param clazz Class to test with.
     * @throws Exception in case of setup problems.
     */
    public IntegerConverterTest(final Class<Integer> clazz) throws Exception {
        super(clazz, IntegerConverter.class);
    }

    /**
     * Tests that instanciating a IntegerConverter works.
     * @throws Exception (unexpected)
     */
    @Test
    public void testIntegerConverter() throws Exception {
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
     * Tests that converting decimal (10-base) integer numbers works correclty.
     * @throws Exception In case of unexpected errors.
     */
    @Test
    public void testConvertDecimalNumbers() throws Exception {
        for (final int number : new int[] { Integer.MIN_VALUE, -100, -1, 0, 1, 100, Integer.MAX_VALUE }) {
            Assert.assertEquals(number, (int) getConverter().convert(Integer.toString(number)));
        }
    }

    /**
     * Tests that converting hexadecimal (16-base) integer numbers works correctly.
     * @throws Exception In case of unexpected errors.
     */
    @Test
    public void testConvertHexadecimalNumbers() throws Exception {
        Assert.assertEquals(0x50000, (int) getConverter().convert("0x50000"));
        Assert.assertEquals(0x50000, (int) getConverter().convert("0X50000"));
        Assert.assertEquals(0x50000, (int) getConverter().convert("#50000"));
    }

    /**
     * Tests that converting octal (8-base) integer numbers works correctly.
     * @throws Exception In case of unexpected errors.
     */
    @Test
    public void testConvertOctalNumbers() throws Exception {
        Assert.assertEquals(0x1ff, (int) getConverter().convert("0777"));
    }

} // class IntegerConverterTest
