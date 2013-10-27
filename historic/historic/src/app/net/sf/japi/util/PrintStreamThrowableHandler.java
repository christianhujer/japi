/* JAPI - (Yet another (hopefully) useful) Java API
 *
 * Copyright (C) 2004-2006 Christian Hujer
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package net.sf.japi.util;

import java.io.PrintStream;
import org.jetbrains.annotations.NotNull;

/** A ThrowableHandler that prints exceptions to a defined PrintStream.
 * @author <a href="mailto:chris@riedquat.de">Christian Hujer</a>
 */
@SuppressWarnings({"UseOfSystemOutOrSystemErr"})
public class PrintStreamThrowableHandler<T extends Throwable> implements ThrowableHandler<T> {

    /** Static instance for printing to STDERR. */
    public static final PrintStreamThrowableHandler<Throwable> STDERR = new PrintStreamThrowableHandler<Throwable>(System.err);

    private final PrintStream stream;

    /** Creates a PrintStreamThrowableHandler for the specified PrintStream.
     * @param stream Stream for which to create a PrintStreamThrowableHandler.
     * @see #STDERR for a default global PrintStreamThrowableHandler that wraps {@link System#in}.
     */
    public PrintStreamThrowableHandler(final PrintStream stream) {
        this.stream = stream;
    }

    /** {@inheritDoc} */
    public void handleThrowable(@NotNull final T t) {
        stream.println(t);
    }

} // class DefaultStderrThrowableHandler
