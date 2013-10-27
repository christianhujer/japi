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
import java.util.Locale;
import net.sf.japi.io.args.converter.ConstructorConverter;
import net.sf.japi.io.args.converter.Converter;
import net.sf.japi.io.args.converter.ConverterRegistry;
import net.sf.japi.io.args.converter.NoConverterFoundException;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link ConverterRegistry}.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
public class ConverterRegistryTest {

    /**
     * Tests that creating a new ConverterRegistry works.
     * @throws Exception (unexpected)
     */
    @Test
    public void testConverterRegistry() throws Exception {
        //noinspection UnusedDeclaration
        final ConverterRegistry converterRegistry = new ConverterRegistry();
    }

    /**
     * Tests that {@link ConverterRegistry#getInstance()} works.
     * @throws Exception (unexpected)
     */
    @Test
    public void testGetInstance() throws Exception {
        final ConverterRegistry converterRegistry = ConverterRegistry.getInstance();
        Assert.assertNotNull("ConverterRegistry.getInstance() must not return null.", converterRegistry);
    }

    /**
     * Tests that {@link ConverterRegistry#getConverter(Class)} works.
     * @throws Exception (unexpected)
     */
    @Test
    public void testGetConverter() throws Exception {
        final ConverterRegistry converterRegistry = new ConverterRegistry();
        final Converter<String> converter = new DummyConverter<String>(String.class);
        converterRegistry.register(converter);
        Assert.assertSame(converterRegistry.getConverter(String.class), converter);
    }

    /**
     * Tests that {@link ConverterRegistry#register(Converter)} works.
     * @throws Exception (unexpected)
     */
    @Test
    public void testRegister() throws Exception {
        // see #testGetConverter();
    }

    /**
     * Tests that {@link ConverterRegistry#convert(Class, String)} and {@link ConverterRegistry#convert(Class, Locale, String)} work.
     * @throws Exception (unexpected)
     */
    @Test
    public void testConvert() throws Exception {
        Assert.assertEquals("foo", ConverterRegistry.convert(String.class, "foo"));
        Assert.assertEquals("foo", ConverterRegistry.convert(String.class, Locale.GERMANY, "foo"));
    }

    /**
     * Tests that {@link ConverterRegistry#convert(Class, String)} will automatically create a converter with a constructor.
     * @throws Exception (unexpected)
     */
    @Test
    public void testConstruct() throws Exception {
        Assert.assertEquals(new File("foo"), ConverterRegistry.convert(File.class, "foo"));
        Assert.assertEquals(new File("foo"), ConverterRegistry.convert(File.class, Locale.GERMANY, "foo"));
    }

    /**
     * Tests that {@link ConverterRegistry#convert(Class, String)} will throw the appropriate exception if no constructor converter is available.
     * @throws Exception (unexpected)
     */
    @Test(expected = NoConverterFoundException.class)
    public void testConstructException() throws Exception {
        ConverterRegistry.convert(System.class, "foo");
    }

    /**
     * Tests that {@link ConverterRegistry#convert(Class, Locale, String)} will throw the appropriate exception if no constructor converter is available.
     * @throws Exception (unexpected)
     */
    @Test(expected = NoConverterFoundException.class)
    public void testConstructException2() throws Exception {
        ConverterRegistry.convert(System.class, Locale.GERMANY, "foo");
    }

    /**
     * Tests that {@link ConverterRegistry#getConstructorConverter(Class)} returns appropriate values.
     * @throws Exception (unexpected)
     */
    @Test
    public void testGetConstructorConverter() throws Exception {
        Assert.assertEquals(new ConstructorConverter<String>(String.class), ConverterRegistry.getConstructorConverter(String.class));
        Assert.assertNull(ConverterRegistry.getConstructorConverter(System.class));
    }

    /**
     * Tests that {@link ConverterRegistry#getConstructorConverter(Class)} returns a converter for every primitive type.
     * @throws Exception (unexpected)
     */
    @Test
    public void testHasPrimitiveConverters() throws Exception {
        final Class[] classes = {
                boolean.class, byte.class, short.class, int.class, long.class, char.class, float.class, double.class
        };
        for (final Class c : classes) {
            Assert.assertNotNull("Expecting ConverterRegistry to have a converter for primitive class " + c, ConverterRegistry.getInstance().getConverter(c));
        }
    }

    /**
     * Dummy Converter.
     * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
     */
    @SuppressWarnings({"ConstantConditions"})
    private static final class DummyConverter<T> implements Converter<T> {

        /** Class for this converter. */
        private final Class<T> targetClass;

        /**
         * Create a DummyConverter.
         * @param targetClass Target class.
         */
        private DummyConverter(final Class<T> targetClass) {
            this.targetClass = targetClass;
        }

        /** {@inheritDoc} */
        @NotNull public T convert(@NotNull final String arg) throws Exception {
            return null;
        }

        /** {@inheritDoc} */
        @NotNull public T convert(@NotNull final Locale locale, @NotNull final String arg) throws Exception {
            return null;
        }

        /** {@inheritDoc} */
        @NotNull public Class<T> getTargetClass() {
            return targetClass;
        }

        /** {@inheritDoc} */
        @NotNull public String getDisplayName() {
            return null;
        }

        /** {@inheritDoc} */
        @NotNull public String getDisplayName(@NotNull final Locale locale) {
            return null;
        }

        /** {@inheritDoc} */
        @NotNull public String getDescription() {
            return null;
        }

        /** {@inheritDoc} */
        @NotNull public String getDescription(@NotNull final Locale locale) {
            return null;
        }

    } // class DummyConverter

} // class ConverterRegistryTest
