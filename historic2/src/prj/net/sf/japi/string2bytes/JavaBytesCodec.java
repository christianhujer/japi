/*
 * Copyright (C) 2009  Christian Hujer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.sf.japi.string2bytes;

import java.io.UnsupportedEncodingException;
import java.util.Formatter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A Codec which converts the input String into a suitable Java Array source code.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class JavaBytesCodec extends AbstractStringCodec {

    /** {@inheritDoc} */
    @NotNull public String code(@NotNull final String input, final String charsetName) throws UnsupportedEncodingException {
        final Formatter result = new Formatter();
        result.format("{ ");
        final byte[] bytes = input.getBytes(charsetName);
        for (int i = 0; i < bytes.length; i++) {
            if (i > 0) {
                result.format(", ");
            }
            result.format("(byte) 0x%02x", bytes[i]);
        }
        result.format(" }");
        result.flush();
        return result.toString();
    }

    /** {@inheritDoc} */
    @Override @Nullable public String getDisplayName() {
        return "Java Bytes";
    }

} // class JavaBytesCodec
