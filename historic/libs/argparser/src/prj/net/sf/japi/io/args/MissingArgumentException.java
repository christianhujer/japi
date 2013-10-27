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
 * This exception is thrown in case a required argument for an option is missing.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.2
 */
public class MissingArgumentException extends Exception {

    /** Serial version. */
    private static final long serialVersionUID = 1L;

    /** The option that was missing its argument.
     * @serial include
     */
    @NotNull private final String option;

    /**
     * Create a RequiredOptionsMissingException.
     * @param option that was missing its argument
     */
    public MissingArgumentException(@NotNull final String option) {
        super("Argument missing for option " + option);
        this.option = option;
    }

    /**
     * Get the option that is missing its argument.
     * @return option that is missing its argument.
     */
    @NotNull public String getOption() {
        return option;
    }

} // class MissingArgumentException
