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

/** Abstract base implementation of {@link Changeable}.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public abstract class AbstractChangeable implements Changeable {

    /** Change status.
     * @serial include
     */
    private boolean changed;

    /** {@inheritDoc} */
    public boolean hasChanged() {
        return changed;
    }

    /** {@inheritDoc} */
    public void setChanged(final boolean changed) {
        this.changed = changed;
    }

}
