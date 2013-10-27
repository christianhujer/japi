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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.AbstractListModel;
import org.jetbrains.annotations.NotNull;

/** List of Changeables.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @param <C> Changeable type to list.
 * @since 0.1
 */
public class ChangeableList<C extends Changeable> extends AbstractListModel implements Changeable {

    /** Changeables in this ChangeableList.
     * @serial include
     */
    private List<C> elements = new ArrayList<C>();

    /** Changed state.
     * @serial include
     */
    private boolean changed;

    /** Creates a ChangeableList. */
    public ChangeableList() {
    }

    /** {@inheritDoc} */
    public boolean hasChanged() {
        boolean changed = this.changed;
        for (final Iterator<C> it = elements.iterator(); !changed && it.hasNext();) {
            changed |= it.next().hasChanged();
        }
        return changed;
    }

    /** {@inheritDoc} */
    public void setChanged(final boolean changed) {
        this.changed = changed;
        if (!changed) {
            for (final Iterator<C> it = elements.iterator(); it.hasNext();) {
                it.next().setChanged(false);
            }
        }
    }

    /** Adds an element to this ChangeableList.
     * @param c Element to add.
     */
    protected void add(final C c) {
        int index = elements.size();
        elements.add(c);
        setChanged(true);
        fireIntervalAdded(this, index, index);
    }

    /** Removes an element from this ChangeableList.
     * @param c Element to remove.
     */
    protected void remove(final C c) {
        int index = elements.indexOf(c);
        if (index == -1) {
            assert false : "Trying to remove an element from a list which is not there.";
            return;
        }
        elements.remove(c);
        setChanged(true);
        fireIntervalRemoved(this, index, index);
    }

    /** {@inheritDoc} */
    public Object getElementAt(final int index) {
        return elements.get(index);
    }

    /** {@inheritDoc} */
    public int getSize() {
        return elements.size();
    }

}
