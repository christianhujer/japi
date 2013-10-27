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

import java.lang.reflect.Constructor;
import java.util.Locale;
import org.jetbrains.annotations.NotNull;

/**
 * Converter that performs a conversion by
 * @param <T> target type to convert to.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.2
 */
public class ConstructorConverter<T> extends AbstractConverter<T> {

    /** The Constructor to invoke. */
    @NotNull private final Constructor<T> constructor;

    /**
     * Create an AbstractConverter.
     * @param targetClass TargetClass
     * @throws NoSuchMethodException in case the target class does not supply a matching constructor.
     */
    public ConstructorConverter(@NotNull final Class<T> targetClass) throws NoSuchMethodException {
        super(targetClass);
        constructor = getConstructor(targetClass);
    }

    @NotNull public T convert(@NotNull final Locale locale, @NotNull final String arg) throws Exception {
        return constructor.newInstance(arg);
    }

    /** Returns a constructor that takes a single String argument for the target type.
     * @param targetType type to get constructor for.
     * @return Constructor for <var>targetType</var>
     * @throws NoSuchMethodException in case the conversion failed.
     */
    @NotNull public static <T> Constructor<T> getConstructor(@NotNull final Class<T> targetType) throws NoSuchMethodException {
        return targetType.getConstructor(String.class);
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(final Object o) {
        return o != null && o instanceof ConstructorConverter && constructor.equals(((ConstructorConverter) o).constructor);
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return constructor.hashCode();
    }

} // class ConstructorConverter
