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
import java.net.URLEncoder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A Codec which converts a String into a URLEncoded String.
 * @note When using this Codec, using UTF-8 is strongly recommended.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class URLEncodeCodec extends AbstractStringCodec {

    /** {@inheritDoc} */
    @NotNull public String code(@NotNull final String input, @Nullable final String charsetName) throws UnsupportedEncodingException {
        // The invocation of the deprecated method is intentional in case no charset is specified.
        // TODO:2009-02-20:christianhujer:Review whether or not this decision was good.
        //noinspection deprecation
        return charsetName == null ? URLEncoder.encode(input) : URLEncoder.encode(input, charsetName);
    }

    /** {@inheritDoc} */
    @Override @Nullable public String getDisplayName() {
        return "URLEncode";
    }

} // clsas URLEncodeCodec
