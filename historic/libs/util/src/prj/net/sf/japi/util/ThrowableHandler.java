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

package net.sf.japi.util;

import java.util.EventListener;
import org.jetbrains.annotations.NotNull;

/** Interface that is implemented by classes that handle exceptions via callbacks.
 * This is useful during operations that can throw more than one exception during an operation.
 * Even more, when these exceptions eventually are more or less recoverable.
 * @param <T> Exception type handled by this handler.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public interface ThrowableHandler<T extends Throwable> extends EventListener {

    /** Handle a Throwable.
     * @param t Throwable to handle
     */
    void handleThrowable(@NotNull T t);

} // interface ThrowableHandler
