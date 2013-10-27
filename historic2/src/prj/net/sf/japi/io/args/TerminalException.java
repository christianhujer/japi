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

/**
 * This exception is thrown when an argument method is terminal, i.e. stops further command processing.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.2
 */
public class TerminalException extends Exception {

    /** Serial version. */
    private static final long serialVersionUID = 1L;

    /** The return code (eventually reported to the operating system in {@link System#exit(int)}).
     * @serial include
     */
    private final int returnCode;

    /**
     * Create a TerminalException.
     * The return code is set to 0.
     */
    public TerminalException() {
        this(0);
    }

    /**
     * Create a TerminalException.
     * @param returnCode Return Code
     */
    public TerminalException(final int returnCode) {
        this.returnCode = returnCode;
    }

    /**
     * Return the return code.
     * The return code is eventually reported to the operating system in {@link System#exit(int)}.
     * @return return code
     */
    public int getReturnCode() {
        return returnCode;
    }

} // class TerminalException
