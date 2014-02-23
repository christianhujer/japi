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
     * @param targetClass Target class, should be either {@code Boolean.class} or {@code Boolean.TYPE} resp. {@code boolean.class}.
     */
    public BooleanConverter(@NotNull final Class<Boolean> targetClass) {
        super(targetClass);
    }

    @Override
    @NotNull public Boolean convert(@NotNull final Locale locale, @NotNull final String arg) throws Exception {
        if (isTrueString(locale, arg)) {
            return Boolean.TRUE;
        }
        if (isFalseString(locale, arg)) {
            return Boolean.FALSE;
        }
        throw new IllegalArgumentException(arg + " is not a valid String for a boolean.");
    }

    private static boolean isTrueString(final Locale locale, final String arg) {
        return isTrueString(arg) || isLocalizedTrueString(locale, arg);
    }

    private static boolean isFalseString(final Locale locale, final String arg) {
        return isFalseString(arg) || isLocalizedFalseString(locale, arg);
    }

    private static boolean isTrueString(final String arg) {
        return contains(TRUE_STRINGS, arg);
    }

    private static boolean isFalseString(final String arg) {
        return contains(FALSE_STRINGS, arg);
    }

    private static boolean contains(final String[] strings, final String arg) {
        for (final String s : strings) {
            if (s.equalsIgnoreCase(arg)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isLocalizedTrueString(final Locale locale, final String arg) {
        return containsStringInBundle(locale, "java.lang.Boolean.true", arg);
    }

    private static boolean isLocalizedFalseString(final Locale locale, final String arg) {
        return containsStringInBundle(locale, "java.lang.Boolean.false", arg);
    }

    private static boolean containsStringInBundle(final Locale locale, final String key, final String arg) {
        return contains(getStringsFromBundle(locale, key), arg);
    }

    private static String[] getStringsFromBundle(final Locale locale, final String key) {
        return getBundle(locale).getString(key).split("\\s+");
    }

    private static ResourceBundle getBundle(final Locale locale) {
        return ResourceBundle.getBundle("net.sf.japi.io.args.converter.Converter", locale);
    }
}
