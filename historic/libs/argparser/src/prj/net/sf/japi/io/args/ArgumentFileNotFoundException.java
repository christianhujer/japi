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

import java.io.FileNotFoundException;
import org.jetbrains.annotations.NotNull;

/** This type of exception is thrown when an argument file was not found.
 * Created by IntelliJ IDEA.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.2
 */
public class ArgumentFileNotFoundException extends FileNotFoundException {

    /** Serial version. */
    private static final long serialVersionUID = 1L;

    /** Create an ArgumentFileNotFoundException.
     * @param cause The FileNotFoundException that caused this ArgumentFileNotFoundException.
     */
    public ArgumentFileNotFoundException(@NotNull final FileNotFoundException cause) {
        super("Argument file not found: " + cause.getMessage());
        initCause(cause);
    }

} // class ArgumentFileNotFoundException
