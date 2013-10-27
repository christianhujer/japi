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
 * This exception is thrown in case required options are missing.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.2
 */
public class RequiredOptionsMissingException extends Exception {

    /** Serial version. */
    private static final long serialVersionUID = 1L;

    /** The list of options that were missing.
     * @serial include
     */
    @NotNull private final String[] missingOptions;

    /**
     * Create a RequiredOptionsMissingException.
     * @param missingOptions options that were missing.
     * @throws IllegalArgumentException in case <var>missingOptions</var> was null or of zero length
     */
    public RequiredOptionsMissingException(@NotNull final String... missingOptions) throws IllegalArgumentException {
        super(createMessage(missingOptions));
        this.missingOptions = missingOptions.clone();
    }

    /**
     * Creates the message.
     * @param missingOptions options that were missing
     * @return message string
     * @throws IllegalArgumentException in case <var>missingOptions</var> was null or of zero length
     */
    private static String createMessage(final String... missingOptions) throws IllegalArgumentException {
        if (missingOptions == null || missingOptions.length < 1) {
            throw new IllegalArgumentException("RequiredOptionsMissingException created but no missing options given.");
        }
        return StringJoiner.join(new StringBuilder("required options missing: "), ", ", missingOptions).toString();
    }

    /**
     * Get the options that were missing.
     * @return options that were missing
     */
    @NotNull public String[] getMissingOptions() {
        return missingOptions.clone();
    }

} // class RequiredOptionsMissingException
