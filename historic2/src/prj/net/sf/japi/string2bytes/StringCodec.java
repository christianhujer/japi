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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A StringCodec is a class that can convert Strings into other Strings.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public interface StringCodec {

    /**
     * Unmodifiable list with all Codecs.
     * @deprecated This will be replaced using a registry.
     */
    @Deprecated @NotNull List<? extends StringCodec> ALL_CODECS = Collections.unmodifiableList(Arrays.asList(new IdentityCodec(), new JavaBytesCodec(), new EntityCodec(), new URLEncodeCodec()));

    /**
     * Convert a String into another String.
     * @param input String to convert.
     * @param charsetName Name of the charset to use (depends on implementation, may be ignored).
     * @return Converted String.
     * @throws UnsupportedEncodingException In case the specified encoding is not supported.
     */
    @NotNull String code(@NotNull String input, @Nullable String charsetName) throws UnsupportedEncodingException;

    /**
     * Returns a display name for this Codec.
     * @return A display name for this Codec.
     */
    @Nullable String getDisplayName();

} // interface StringCodec
