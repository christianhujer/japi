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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import org.jetbrains.annotations.NotNull;

/**
 * Converter that converts a String into an InputStream.
 * @note This converter always behaves the same independently of the {@link Locale}.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.2
 * @deprecated The concept of this class is not yet mature. Please don't use it.
 */
@Deprecated public class InputStreamConverter extends AbstractConverter<InputStream> {

    /**
     * Create an InputStreamConverter.
     */
    public InputStreamConverter() {
        super(InputStream.class);
    }

    /** {@inheritDoc} */
    @NotNull public InputStream convert(@NotNull final Locale locale, @NotNull final String arg) throws IOException {
        if ("-".equals(arg)) {
            return System.in;
        }
        try {
            return new URL(arg).openStream();
        } catch (final MalformedURLException ignore) {
            return new FileInputStream(arg);
        }
    }

} // class InputStreamConverter
