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
 * Converter which converts a String into a an Integer.
 * The Converter uses a method that at minimum supports the same conversions as {@link Integer#decode(String)}.
 * That means the following formats are supported:
 * @note This converter always behaves the same independently of the {@link Locale}.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.2
 * @see Integer#decode(String) Minimum grammar supported.
 */
public class IntegerConverter extends AbstractConverter<Integer> {

    /**
     * Create an IntegerConverter.
     * @param targetClass Target class, should be either <code>Integer.class</code> or <code>Integer.TYPE</code> resp. <code>int.class</code>.
     */
    public IntegerConverter(final Class<Integer> targetClass) {
        super(targetClass);
    }

    /** {@inheritDoc} */
    @NotNull public Integer convert(@NotNull final Locale locale, @NotNull final String arg) throws NumberFormatException {
        return Integer.decode(arg);
    }

} // class IntegerConverter
