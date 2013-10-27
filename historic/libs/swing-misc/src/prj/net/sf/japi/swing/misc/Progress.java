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

package net.sf.japi.swing.misc;

import java.awt.Component;
import org.jetbrains.annotations.Nullable;

/** Interface for classes that are able to display progress.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public interface Progress {

    /** Progress has finished. */
    void finished();

    /** Get the Component that is responsible for rendering the progress.
     * This is useful if it is neccessary to open dialogs during the progress.
     * @return Component responsible for rendering the progress
     */
    @Nullable
    Component getParentComponent();

    /** Set progress information text.
     * @param msg progress information text
     * @param max maximum progress value
     */
    void setLabel(String msg, int max);

    /** Set progress value.
     * @param value progress value
     */
    void setValue(int value);

} // interface Progress
