/*
 * Copyright (C) 2009  Christian Hujer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.sf.japi.progs.batcher;

import java.io.Serializable;

/** Interface for application data elements which are queried for the changed state of a project or document.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public interface Changeable extends Serializable {

    /** Returns whether or not this Changeable has changed.
     * A changeable has changed if itself or one of its components is changed.
     * @return <code>true</code> if this changeable has changed, otherwise <code>false</code>.
     */
    boolean hasChanged();

    /** Sets whether or not this Changeable has changed.
     * @param changed <code>true</code> if this changeable is changed, otherwise <code>false</code>.
     * @note Setting changed to true is a shallow, setting changed to false is a deep operation.
     */
    void setChanged(boolean changed);

}
