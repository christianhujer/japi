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

package net.sf.japi.io.args;

import org.jetbrains.annotations.NotNull;

/**
 * This exception is thrown in case one or more unknown options were encountered.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.2
 */
public class UnknownOptionException extends Exception {

    /** Serial version. */
    private static final long serialVersionUID = 1L;

    /** The list of options that were unknown.
     * @serial include
     */
    @NotNull private final String[] unknownOptions;

    /**
     * Create a RequiredOptionsMissingException.
     * @param unknownOptions options that were missing.
     * @throws IllegalArgumentException in case <var>unknownOptions</var> was null or of zero length
     */
    public UnknownOptionException(@NotNull final String... unknownOptions) throws IllegalArgumentException {
        super(createMessage(unknownOptions));
        this.unknownOptions = unknownOptions.clone();
    }

    /**
     * Creates the message.
     * @param unknownOptions options that were missing
     * @return message string
     * @throws IllegalArgumentException in case <var>unknownOptions</var> was null or of zero length
     */
    private static String createMessage(final String... unknownOptions) throws IllegalArgumentException {
        if (unknownOptions == null || unknownOptions.length < 1) {
            throw new IllegalArgumentException("UnknownOptionException created but no unknown options given.");
        }
        return StringJoiner.join(new StringBuilder("unknown options: "), ", ", unknownOptions).toString();
    }

    /**
     * Get the options that were missing.
     * @return options that were missing
     */
    @NotNull public String[] getUnknownOptions() {
        return unknownOptions.clone();
    }

} // class UnknownOptionException
