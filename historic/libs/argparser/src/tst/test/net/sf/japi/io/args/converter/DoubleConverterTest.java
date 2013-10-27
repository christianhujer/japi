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
import net.sf.japi.io.args.converter.DoubleConverter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * Tests for {@link DoubleConverter}.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
@RunWith(Parameterized.class)
public class DoubleConverterTest extends AbstractPrimitiveConverterTest<Double, DoubleConverter> {

    /** Creates the parameter arrays for the parameterized test execution.
     * @return The parameter arrays.
     * @see #DoubleConverterTest(Class)
     */
    @Parameterized.Parameters public static Collection getClasses() {
        return Arrays.asList(new Class[][] {
                {double.class},
                {Double.class}
        });
    }

    /**
     * Create an DoubleConverterTest.
     * @param clazz Class to test with.
     * @throws Exception in case of setup problems.
     */
    public DoubleConverterTest(final Class<Double> clazz) throws Exception {
        super(clazz, DoubleConverter.class);
    }

    /**
     * Tests that instanciating a DoubleConverter works.
     * @throws Exception (unexpected)
     */
    @Test
    public void testDoubleConverter() throws Exception {
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
     * Tests that converting decimal (10-base) double numbers works correclty.
     * @throws Exception In case of unexpected errors.
     */
    @Test
    public void testConvertDecimalNumbers() throws Exception {
        for (final double number : new double[] { Double.MIN_VALUE, -100, -1, 0, 1, 100, Double.MAX_VALUE }) {
            Assert.assertEquals((Double) number, getConverter().convert(Double.toString(number)));
        }
    }

    /**
     * Tests that converting hexadecimal (16-base) double numbers works correctly.
     * @throws Exception In case of unexpected errors.
     */
    @Test
    public void testConvertHexadecimalNumbers() throws Exception {
        Assert.assertEquals((Double) 50000.0, getConverter().convert("50000.0"));
    }

    /**
     * Tests that converting octal (8-base) double numbers works correctly.
     * @throws Exception In case of unexpected errors.
     */
    @Test
    public void testConvertOctalNumbers() throws Exception {
        Assert.assertEquals((Double) 777.0, getConverter().convert("0777"));
    }

} // class DoubleConverterTest
