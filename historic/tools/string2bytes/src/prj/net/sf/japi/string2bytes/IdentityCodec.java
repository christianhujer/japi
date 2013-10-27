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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A Codec that doesn't perform any conversion.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class IdentityCodec extends AbstractStringCodec {

    /** {@inheritDoc} */
    @NotNull public String code(@NotNull final String input, final String charsetName) throws UnsupportedEncodingException {
        return input;
    }

    /** {@inheritDoc} */
    @Override @Nullable public String getDisplayName() {
        return "Identity";
    }

} // class IdentityCodec
