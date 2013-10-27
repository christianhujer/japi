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

import java.util.Locale;
import java.lang.reflect.InvocationTargetException;
import net.sf.japi.io.args.converter.Converter;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Base class for tests for Converters.
 * Provides some basic testing methods.
 * @param <V> target type to convert to.
 * @param <T> converter type to test
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
public abstract class AbstractPrimitiveConverterTest<V, T extends Converter<V>> {

    /** The target class. */
    private final Class<V> targetClass;

    /** The converter testling. */
    private T converter;

    /** The class of the converter. */
    private final Class<T> converterClass;

    /**
     * Create an AbstractConverterTest.
     * @param targetClass Class of the Converter's target to test.
     * @param converterClass Class of the Converter to test.
     */
    protected AbstractPrimitiveConverterTest(@NotNull final Class<V> targetClass, @NotNull final Class<T> converterClass) {
        this.targetClass = targetClass;
        this.converterClass = converterClass;
    }

    /** Creates a converter instance.
     * @throws IllegalAccessException (unexpected)
     * @throws InstantiationException (unexpected)
     * @throws NoSuchMethodException (unexpected)
     * @throws InvocationTargetException (unexpected)
     */
    @Before
    public void createConverter() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        converter = converterClass.getConstructor(Class.class).newInstance(targetClass);
    }

    /**
     * Tests that creating a Converter works.
     * @throws Exception (unexpected)
     */
    public void testAbstractConverter() throws Exception {
        // Tested by the constructor.
    }

    /**
     * Tests that convert throws a NullPointerException if invoked with <code>null</code>.
     * @throws Exception In case of test problems.
     */
    @SuppressWarnings({"ConstantConditions"})
    public void testConvertThrowsNPE() throws Exception {
        try {
            converter.convert(null);
            Assert.fail("Expected exception IllegalArgumentException or NullPointerException but got no exception when invoking convert(null).");
        } catch (final IllegalArgumentException ignore) {
            // expected Exception if compiled with @NotNull support
        } catch (final NullPointerException ignore) {
            // expected Exception if compiled without @NotNull support
        }
    }

    /**
     * Tests that getting the target class works.
     * @throws Exception In case of test problems.
     */
    @Test
    public void testGetTargetClass() throws Exception {
        Assert.assertSame("The target class must be stored.", targetClass, converter.getTargetClass());
    }

    /**
     * Tests that the testling has a proper description for the default locale.
     * @throws Exception In case of unexpected errors.
     */
    @Test
    public void testGetDescription() throws Exception {
        final String description = converter.getDescription();
        Assert.assertNotNull("The description must not be null.", description);
        Assert.assertTrue("The description must not be empty.", description.length() > 0);
        Assert.assertEquals("The description must be returned in the default locale.", description, converter.getDescription(Locale.getDefault()));
    }

    /**
     * Tests that the testling has a proper display name.
     * @throws Exception In case of unexpected errors.
     */
    @Test
    public void testGetDisplayName() throws Exception {
        final String displayName = converter.getDisplayName();
        Assert.assertNotNull("The display name must not be null.", displayName);
        Assert.assertTrue("The display name must not be empty.", displayName.length() > 0);
        Assert.assertEquals("The display name must be returned in the default locale.", displayName, converter.getDisplayName(Locale.getDefault()));
    }

    /** Returns the converter that's being tested.
     * @return The converter that's being tested.
     */
    protected T getConverter() {
        return converter;
    }

} // class AbstractConverterTest
