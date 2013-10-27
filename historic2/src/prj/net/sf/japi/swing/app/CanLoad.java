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

package net.sf.japi.swing.app;

import org.jetbrains.annotations.NotNull;

/** Interface to be implemented by classes that are able to load a URL.
 * @param <D> The document type that can be loaded.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public interface CanLoad<D> {

    /** Load a document.
     * The returned document will have its URI set.
     * @param uri URL to load.
     * @return The document that was just loaded.
     * @throws Exception in case of problems when loading.
     */
    @NotNull Document<D> load(@NotNull String uri) throws Exception;

} // interface CanLoad
