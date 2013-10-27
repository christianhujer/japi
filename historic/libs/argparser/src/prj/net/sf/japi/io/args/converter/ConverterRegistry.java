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

package net.sf.japi.io.args.converter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ServiceLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Registry for {@link Converter}s.
 * <p>
 * Per default, the following {@link Converter}s are supported:
 * <ul>
 *  <li>String (this is an identity conversion)</li>
 *  <li>All primitive types (boolean, byte, short, int, long, char, float, double).</li>
 *  <li>All wrapper types for primitives (Boolean, Byte, Short, Integer, Long, Character, Float, Double).</li>
 *  <li>All types which have a public constructor that takes a single String argument.</li>
 *  <li>All Enums.</li>
 * </ul>
 * The ConverterRegistry uses the {@link ServiceLoader ServiceLoader} to find additional {@link Converter}s.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.2
 */
public class ConverterRegistry {

    /** Singleton INSTANCE. */
    private static final ConverterRegistry INSTANCE = createSingletonInstance();

    /** Map for converters. */
    private final Map<Class<?>, Converter<?>> converters = new HashMap<Class<?>, Converter<?>>();

    /**
     * Creates a ConverterRegistry.
     * Usually you want a shared global ConverterRegistry and use {@link #getInstance()} instead of creating your own ConverterRegistry INSTANCE.
     * @see #getInstance()
     */
    public ConverterRegistry() {
    }

    /**
     * Creates the global shared instance of ConverterRegistry.
     * @return The global shared instance of ConverterRegistry.
     */
    @SuppressWarnings({"unchecked"})
    private static ConverterRegistry createSingletonInstance() {
        final ConverterRegistry instance = new ConverterRegistry();
        instance.registerDefaultConverters();
        // Add more from META-INF/services/net.sf.japi.io.args.converter.Converter
        for (final Iterator<Converter> converters = ServiceLoader.load(Converter.class).iterator(); converters.hasNext();) {
            instance.register(converters.next());
        }
        return instance;
    }

    /** Registers the default converters with this ConverterRegistry. */
    public void registerDefaultConverters() {
        // To see what's really there, also look at META-INF/services/net.sf.japi.io.args.converter.Converter
        register(new BooleanConverter(boolean.class));
        register(new BooleanConverter(Boolean.class));
        register(new CharConverter(char.class));
        register(new CharConverter(Character.class));
        register(new ByteConverter(byte.class));
        register(new ByteConverter(Byte.class));
        register(new ShortConverter(short.class));
        register(new ShortConverter(Short.class));
        register(new IntegerConverter(int.class));
        register(new IntegerConverter(Integer.class));
        register(new LongConverter(long.class));
        register(new LongConverter(Long.class));
        register(new FloatConverter(float.class));
        register(new FloatConverter(Float.class));
        register(new DoubleConverter(double.class));
        register(new DoubleConverter(Double.class));
    }

    /**
     * Returns the global shared instance of ConverterRegistry.
     * @return The global shared instance of ConverterRegistry.
     */
    public static ConverterRegistry getInstance() {
        return INSTANCE;
    }

    /**
     * Get the Converter for a specific class.
     * @param <T> target type of the class to get a converter for.
     * @param clazz Class to get Converter for.
     * @return <code>null</code> if no suited converter was found.
     */
    @Nullable public <T> Converter<T> getConverter(@NotNull final Class<T> clazz) {
        //noinspection unchecked
        @Nullable Converter<T> converter = (Converter<T>) converters.get(clazz);
        if (converter == null) {
            converter = getConstructorConverter(clazz);
        }
        if (converter == null && Enum.class.isAssignableFrom(clazz)) {
            converter = (Converter<T>) getEnumConverter((Class<? extends Enum>) clazz);
        }
        if (converter != null) {
            register(converter);
        }
        return converter;
    }

    /**
     * Register a Converter for a specific class.
     * @param <T> target type of the class to register a converter for.
     * @param converter Converter to register
     */
    public <T> void register(@NotNull final Converter<T> converter) {
        converters.put(converter.getTargetClass(), converter);
        //noinspection NestedAssignment
        for (Class<?> superClass = converter.getTargetClass(); (superClass = superClass.getSuperclass()) != null;) {
            if (!converters.containsKey(superClass)) {
                converters.put(superClass, converter);
            }
        }
        for (final Class<?> interf : converter.getTargetClass().getInterfaces()) {
            if (!converters.containsKey(interf)) {
                converters.put(interf, converter);
            }
        }
    }

    /**
     * Convenience method to convert a String to the desired target type using the default ConverterRegistry.
     * @param <T> target type of the class to converter to.
     * @param targetType target type to convert to.
     * @param s String to convert
     * @return Converted String in the desired target type.
     * @throws Exception in case the conversion failed.
     */
    @NotNull public static <T> T convert(@NotNull final Class<T> targetType, @NotNull final String s) throws Exception {
        final Converter<T> converter = getInstance().getConverter(targetType);
        if (converter != null) {
            return converter.convert(s);
        }
        throw new NoConverterFoundException(targetType);
    }

    /**
     * Convenience method to convert a String to the desired target type using the default ConverterRegistry.
     * @param <T> target type of the class to converter to.
     * @param targetType target type to convert to.
     * @param locale Locale to perform the conversion in.
     * @param s String to convert.
     * @return Converted String in the desired target type.
     * @throws Exception in case the conversion failed.
     */
    @NotNull public static <T> T convert(@NotNull final Class<T> targetType, @NotNull final Locale locale, @NotNull final String s) throws Exception {
        final Converter<T> converter = getInstance().getConverter(targetType);
        if (converter != null) {
            return converter.convert(locale, s);
        }
        throw new NoConverterFoundException(targetType);
    }

    /** Returns a constructor converter for the target type.
     * @param targetType target type to convert to.
     * @return ConstructorConverter for the target type.
     */
    @Nullable public static <T> ConstructorConverter<T> getConstructorConverter(@NotNull final Class<T> targetType) {
        try {
            return new ConstructorConverter<T>(targetType);
        } catch (final Exception ignore) {
            return null;
        }
    }

    /** Returns an enum converter for the target type.
     * @param targetType target type to convert to.
     * @return EnumConverter for the target type.
     */
    @Nullable public static <T extends Enum> EnumConverter<T> getEnumConverter(@NotNull final Class<T> targetType) {
        return new EnumConverter<T>(targetType);
    }

} // class ConverterRegistry
