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

import net.sf.japi.io.args.converter.ConverterRegistry;
import net.sf.japi.io.args.converter.EnumConverter;
import org.junit.Assert;
import org.junit.Test;

/** Test for {@link EnumConverter}.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
public class EnumConverterTest {

    /** Tests that existing enum constants are found by the EnumConverter.
     * @throws Exception (unexpected).
     */
    @Test
    public void testGetEnumConverter() throws Exception {
        TestEnum v;

        v = ConverterRegistry.convert(TestEnum.class, "EC1");
        Assert.assertSame("Expecting \"EC1\" for TestEnum.class to be converted to TestEnum.EC1.", v, TestEnum.EC1);

        v = ConverterRegistry.convert(TestEnum.class, "EC2");
        Assert.assertSame("Expecting \"EC2\" for TestEnum.class to be converted to TestEnum.EC2.", v, TestEnum.EC2);
    }

    /** Test enum. */
    enum TestEnum {
        /** Test enum constant 1. */
        EC1,

        /** Test enum constant 2. */
        EC2
    }
}
