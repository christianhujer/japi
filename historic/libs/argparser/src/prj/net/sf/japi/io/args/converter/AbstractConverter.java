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

import java.util.Locale;
import java.util.ResourceBundle;
import org.jetbrains.annotations.NotNull;

/**
 * Base class for the default converters.
 * @param <T> target type to convert to.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.2
 */
public abstract class AbstractConverter<T> implements Converter<T> {

    /** The target class. */
    @NotNull private final Class<T> targetClass;

    /**
     * Create an AbstractConverter.
     * @param targetClass TargetClass
     */
    protected AbstractConverter(@NotNull final Class<T> targetClass) {
        this.targetClass = targetClass;
    }

    /** {@inheritDoc} */
    @NotNull public final Class<T> getTargetClass() {
        return targetClass;
    }

    /** {@inheritDoc} */
    @NotNull public final T convert(@NotNull final String arg) throws Exception {
        return convert(Locale.getDefault(), arg);
    }

    /** {@inheritDoc} */
    @NotNull public final String getDisplayName() {
        return getDisplayName(Locale.getDefault());
    }

    /** {@inheritDoc} */
    @NotNull public final String getDisplayName(@NotNull final Locale locale) {
        return ResourceBundle.getBundle("net.sf.japi.io.args.converter.Converter", locale).getString(targetClass.getName() + ".displayName");
    }

    /** {@inheritDoc} */
    @NotNull public final String getDescription() {
        return getDescription(Locale.getDefault());
    }

    /** {@inheritDoc} */
    @NotNull public final String getDescription(@NotNull final Locale locale) {
        return ResourceBundle.getBundle("net.sf.japi.io.args.converter.Converter", locale).getString(targetClass.getName() + ".description");
    }

} // class AbstractConverter
