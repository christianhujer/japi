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

package net.sf.japi.log;

/**
 * This error is thrown when a configuration error prevented a Logger from being created.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class LogConfigurationError extends Error {

    /** Serial Version. */
    private static final long serialVersionUID = 1L;

    /**
     * Create a LogConfigurationError without message.
     */
    public LogConfigurationError() {
    }

    /**
     * Create a LogConfigurationError with a message.
     * @param msg Message
     */
    public LogConfigurationError(final String msg) {
        super(msg);
    }

    /**
     * Create a LogConfigurationError with a cause.
     * @param cause Cause
     */
    public LogConfigurationError(final Throwable cause) {
        super(cause);
    }

    /**
     * Create a LogConfigurationError with a message and a cause.
     * @param cause Cause
     * @param msg Message
     */
    public LogConfigurationError(final String msg, final Throwable cause) {
        super(msg, cause);
    }

} // class LogConfigurationError
