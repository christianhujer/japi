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
 * Converter which converts a String into a an Short.
 * The Converter uses a method that at minimum supports the same conversions as {@link Short#decode(String)}.
 * That means the following formats are supported:
 * @note This converter always behaves the same independently of the {@link Locale}.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.3
 * @see Short#decode(String) Minimum grammar supported.
 */
public class ShortConverter extends AbstractConverter<Short> {

    /**
     * Create an ShortConverter.
     * @param targetClass Target class, should be either <code>Short.class</code> or <code>Short.TYPE</code> resp. <code>short.class</code>.
     */
    public ShortConverter(final Class<Short> targetClass) {
        super(targetClass);
    }

    /** {@inheritDoc} */
    @NotNull public Short convert(@NotNull final Locale locale, @NotNull final String arg) throws NumberFormatException {
        return Short.decode(arg);
    }

} // class ShortConverter
