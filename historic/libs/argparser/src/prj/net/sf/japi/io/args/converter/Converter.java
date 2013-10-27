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
import org.jetbrains.annotations.NotNull;

/**
 * The Converter interface is used for converters that convert Strings into other types.
 * @param <T> target type to convert to.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.2
 */
public interface Converter<T> {

    /**
     * Convert the given argument to the desired return type.
     * @param arg Argument to convert
     * @return Argument converted to T.
     * @throws Exception In case of conversion failure.
     * @throws NullPointerException In case <code><var>arg</var> == null</code>.
     */
    @NotNull T convert(@NotNull final String arg) throws Exception;

    /**
     * Convert the given argument to the desired return type.
     * @param arg Argument to convert
     * @param locale Locale to get perform conversion for.
     * @return Argument converted to T.
     * @throws Exception In case of conversion failure.
     * @throws NullPointerException In case <code><var>arg</var> == null</code>.
     */
    @NotNull T convert(@NotNull final Locale locale, @NotNull final String arg) throws Exception;

    /**
     * Returns the Class this Converter is for.
     * @return The Class this Converter is for.
     */
    @NotNull Class<T> getTargetClass();

    /**
     * Returns a display name for the type of this Converter.
     * @return A display name for the type of this Converter.
     */
    @NotNull String getDisplayName();

    /**
     * Returns a display name for the type of this Converter.
     * @param locale Locale to get display name for.
     * @return A display name for the type of this Converter in the specified locale.
     */
    @NotNull String getDisplayName(@NotNull final Locale locale);

    /**
     * Returns a description for this Converter.
     * @return A description for this Converter.
     */
    @NotNull String getDescription();

    /**
     * Returns a description for this Converter.
     * @param locale Locale to get the description for.
     * @return A description for this Converter in the specified locale.
     */
    @NotNull String getDescription(@NotNull final Locale locale);

} // interface Convert
