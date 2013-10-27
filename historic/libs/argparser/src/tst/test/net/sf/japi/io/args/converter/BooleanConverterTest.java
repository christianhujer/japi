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
import java.util.Locale;
import net.sf.japi.io.args.converter.BooleanConverter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * Tests for {@link BooleanConverter}.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
@RunWith(Parameterized.class)
public class BooleanConverterTest extends AbstractPrimitiveConverterTest<Boolean, BooleanConverter> {

    /** Creates the parameter arrays for the parameterized test execution.
     * @return The parameter arrays.
     * @see #BooleanConverterTest(Class)
     */
    @Parameterized.Parameters public static Collection getClasses() {
        return Arrays.asList(new Class[][] {
                {boolean.class},
                {Boolean.class}
        });
    }

    /**
     * Create a BooleanConverterTest.
     * @param clazz Class to test with.
     * @throws Exception in case of setup problems.
     */
    public BooleanConverterTest(final Class<Boolean> clazz) throws Exception {
        super(clazz, BooleanConverter.class);
    }

//    /** Creates a converter instance.
//     * @throws IllegalAccessException (unexpected)
//     * @throws InstantiationException (unexpected)
//     */
//    @Before
//    public void createConverter() throws IllegalAccessException, InstantiationException {
//        converter = new BooleanConverter(boolean.class);
//    }

    /**
     * Tests that instanciating a BooleanConverter works.
     * @throws Exception (unexpected)
     */
    @Test
    public void testBooleanConverter() throws Exception {
        // Tested by this test class's superclass constructor.
    }

    /**
     * Tests that convert works for true.
     * @throws Exception In case of unexpected errors.
     */
    @Test
    public void testConvertTrue() throws Exception {
        Assert.assertTrue(getConverter().convert("true"));
        Assert.assertTrue(getConverter().convert("TRUE"));
        Assert.assertTrue(getConverter().convert("True"));
        Assert.assertTrue(getConverter().convert("1"));
    }

    /**
     * Tests that convert works for true.
     * @throws Exception In case of unexpected errors.
     */
    @Test
    public void testConvertFalse() throws Exception {
        Assert.assertFalse(getConverter().convert("false"));
        Assert.assertFalse(getConverter().convert("FALSE"));
        Assert.assertFalse(getConverter().convert("False"));
        Assert.assertFalse(getConverter().convert("0"));
    }

    /**
     * Tests that convert works for true.
     * @throws Exception In case of unexpected errors.
     */
    @Test
    public void testLocalizedConvertTrue() throws Exception {
        Assert.assertTrue(getConverter().convert(Locale.GERMANY, "true"));
        Assert.assertTrue(getConverter().convert(Locale.GERMANY, "TRUE"));
        Assert.assertTrue(getConverter().convert(Locale.GERMANY, "True"));
        Assert.assertTrue(getConverter().convert(Locale.GERMANY, "1"));
        Assert.assertTrue(getConverter().convert(Locale.GERMANY, "wahr"));
        Assert.assertTrue(getConverter().convert(Locale.GERMANY, "ja"));
    }

    /**
     * Tests that convert works for true.
     * @throws Exception In case of unexpected errors.
     */
    @Test
    public void testLocalizedConvertFalse() throws Exception {
        Assert.assertFalse(getConverter().convert(Locale.GERMANY, "false"));
        Assert.assertFalse(getConverter().convert(Locale.GERMANY, "FALSE"));
        Assert.assertFalse(getConverter().convert(Locale.GERMANY, "False"));
        Assert.assertFalse(getConverter().convert(Locale.GERMANY, "0"));
        Assert.assertFalse(getConverter().convert(Locale.GERMANY, "falsch"));
        Assert.assertFalse(getConverter().convert(Locale.GERMANY, "nein"));
    }

    /**
     * Tests that convert works for other Strings (IllegalArgumentException must be thrown).
     * @throws IllegalArgumentException Expected exception that's thrown if the test case is successful.
     * @throws Exception In case of unexpected errors.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConvertOther() throws Exception {
        getConverter().convert("foobarbuzz");
    }

} // class BooleanConverterTest
