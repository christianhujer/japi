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

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.Locale;
import net.sf.japi.io.args.converter.ConstructorConverter;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link ConstructorConverter}.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
public class ConstructorConverterTest {

    /** Tests that creating a ConstructorConverter works.
     * @throws Exception (unexpected)
     */
    @Test
    public void testConstructorConverter() throws Exception {
        final ConstructorConverter<String> conv = new ConstructorConverter<String>(String.class);
        Assert.assertEquals("Correct String must be returned.", "foo", conv.convert("foo"));
        Assert.assertEquals("Correct String must be returned.", "foo", conv.convert(Locale.GERMANY, "foo"));
    }

    /** Tests that creating a ConstructorConverter works.
     * @throws Exception (unexpected)
     */
    @Test
    public void testConstructorConverter2() throws Exception {
        final ConstructorConverter<File> conv = new ConstructorConverter<File>(File.class);
        Assert.assertEquals("Correct file must be returned.", new File("foo"), conv.convert("foo"));
        Assert.assertEquals("Correct file muts be returned.", new File("foo"), conv.convert(Locale.GERMANY, "foo"));
    }

    /** Tests that creating a ConstructorConverter for non-public constructors fails.
     * @throws Exception (unexpected)
     */
    @Test(expected = NoSuchMethodException.class)
    public void testConstructorConverter3() throws Exception {
        new ConstructorConverter<NonPublicConverter>(NonPublicConverter.class);
    }

    /** Tests that {@link ConstructorConverter#equals(Object)} works.
     * @throws Exception (unexpected)
     */
    @Test
    public void testEquals() throws Exception {
        final ConstructorConverter<String> conv1 = new ConstructorConverter<String>(String.class);
        final ConstructorConverter<String> conv2 = new ConstructorConverter<String>(String.class);
        final ConstructorConverter<File> conv3 = new ConstructorConverter<File>(File.class);
        Assert.assertEquals("Two Converter constructed for the same type must be equal.", conv1, conv2);
        Assert.assertFalse("A Converter must not be equal to an arbitrary Object.", conv1.equals(new Object()));
        //noinspection ObjectEqualsNull
        Assert.assertFalse("A Converter must not be equal to null.", conv1.equals(null));
        //noinspection EqualsBetweenInconvertibleTypes
        Assert.assertFalse("A Converter must not be equal to a Converter for a different type.", conv1.equals(conv3));
    }

    /** Tests that {@link ConstructorConverter#hashCode()} works.
     * @throws Exception (unexpected)
     */
    @Test
    public void testHashCode() throws Exception {
        final ConstructorConverter<File> conv = new ConstructorConverter<File>(File.class);
        final Constructor<File> constructor = File.class.getConstructor(String.class);
        Assert.assertEquals("The hashCode of a ConstructorConverter must be the hashCode of the constructor used.", conv.hashCode(), constructor.hashCode());
    }

    /** Tests that {@link ConstructorConverter#getConstructor(Class)} works.
     * @throws Exception (unexpected)
     */
    @Test
    public void testGetConstructor() throws Exception {
        final Constructor<File> constructor = ConstructorConverter.getConstructor(File.class);
        Assert.assertEquals("The Constructor returned by a ConstructorConverter must be the correct Constructor.", File.class.getConstructor(String.class), constructor);
    }

    /** Tests that {@link ConstructorConverter#getConstructor(Class)} throws an Exception for non-public constructors.
     * @throws Exception (unexpected)
     */
    @Test(expected = NoSuchMethodException.class)
    public void testGetConstructor2() throws Exception {
        ConstructorConverter.getConstructor(NonPublicConverter.class);
    }

    /** Dummy that represents a converter with a non-public constructor. */
    public static class NonPublicConverter {

        /** Non-public constructor for test.
         * @param arg String argument.
         */
        @SuppressWarnings({"UnusedDeclaration"})
        NonPublicConverter(final String arg) {
        }

    } // class NonPublicConverter

} // class ConstructorConverterTest
