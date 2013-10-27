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

import org.jetbrains.annotations.NotNull;

/**
 * A CodecStep represents a single configurable step for running a Codec with a specified Charset.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class CodecStep {

    /** The Charset to use for this CodecStep. */
    @NotNull private String charset;

    /** The Codec to run. */
    @NotNull private StringCodec codec;

    /**
     * Create a CodecStep.
     * @param codec   The Codec to run.
     * @param charset The Charset to use.
     */
    public CodecStep(@NotNull final StringCodec codec, @NotNull final String charset) {
        this.codec = codec;
        this.charset = charset;
    }

    /**
     * Returns the charset to use for coding.
     * @return The charset to use for coding.
     */
    @NotNull public String getCharset() {
        return charset;
    }

    /**
     * Sets the charset to use for coding.
     * @param charset Charset to use for coding.
     */
    public void setCharset(@NotNull final String charset) {
        this.charset = charset;
    }

    /**
     * Returns the codec to use for coding.
     * @return The codec to use for coding.
     */
    @NotNull public StringCodec getCodec() {
        return codec;
    }

    /**
     * Sets the codec to use for coding.
     * @param codec Codec to use for coding.
     */
    public void setCodec(@NotNull final StringCodec codec) {
        this.codec = codec;
    }

    /** {@inheritDoc} */
    @Override @NotNull public String toString() {
        return codec + " [" + getCharset() + "]";
    }

} // class CodecStep
