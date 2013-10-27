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

import org.jetbrains.annotations.NotNull;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Common Superclass for converters that convert to Boolean or boolean.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.2
 */
public class BooleanConverter extends AbstractConverter<Boolean> {

    /** Strings for true. */
    private static final String[] TRUE_STRINGS = { "true", "yes", "on", "1" };

    /** Strings for true. */
    private static final String[] FALSE_STRINGS = { "false", "no", "off", "0" };

    /** Creates a BooleanConverter.
     * @param targetClass Target class, should be either <code>Boolean.class</code> or <code>Boolean.TYPE</code> resp. <code>boolean.class</code>.
     */
    public BooleanConverter(@NotNull final Class<Boolean> targetClass) {
        super(targetClass);
    }

    /** {@inheritDoc} */
    @NotNull public Boolean convert(@NotNull final Locale locale, @NotNull final String arg) throws Exception {
        for (final String s : TRUE_STRINGS) {
            if (s.equalsIgnoreCase(arg)) {
                return Boolean.TRUE;
            }
        }
        for (final String s : FALSE_STRINGS) {
            if (s.equalsIgnoreCase(arg)) {
                return Boolean.FALSE;
            }
        }
        for (final String s : ResourceBundle.getBundle("net.sf.japi.io.args.converter.Converter", locale).getString("java.lang.Boolean.true").split("\\s+")) {
            if (s.equalsIgnoreCase(arg)) {
                return Boolean.TRUE;
            }
        }
        for (final String s : ResourceBundle.getBundle("net.sf.japi.io.args.converter.Converter", locale).getString("java.lang.Boolean.false").split("\\s+")) {
            if (s.equalsIgnoreCase(arg)) {
                return Boolean.FALSE;
            }
        }
        throw new IllegalArgumentException(arg + " is not a valid String for a boolean.");
    }
}
