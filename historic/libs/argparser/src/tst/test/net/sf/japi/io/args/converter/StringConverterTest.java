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

import net.sf.japi.io.args.converter.StringConverter;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link StringConverter}.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
public class StringConverterTest extends AbstractConverterTest<String, StringConverter> {

    /**
     * Create a StringConverterTest.
     * @throws Exception in case of setup problems.
     */
    public StringConverterTest() throws Exception {
        super(String.class, StringConverter.class);
    }

    /**
     * Tests that instanciating a StringConverter works.
     * @throws Exception (unexpected)
     */
    @Test
    public void testStringConverter() throws Exception {
        // Tested by this test class's superclass constructor.
    }

    /**
     * Tests that {@link StringConverter} has a public default constructor.
     * @throws Exception In case of unexpected errors.
     */
    @Test
    public void testConvert() throws Exception {
        final String foo = getConverter().convert("foo");
        Assert.assertEquals("StringConverter.convert(\"foo\") must return \"foo\".", "foo", foo);
    }

} // class StringConverterTest
