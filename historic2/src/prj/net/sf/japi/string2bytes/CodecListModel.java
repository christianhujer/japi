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

package net.sf.japi.string2bytes;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import org.jetbrains.annotations.NotNull;

/**
 * ListModel for codecs.
 * Immutable, holds all available codecs.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class CodecListModel implements ListModel {

    /** {@inheritDoc} */
    public int getSize() {
        return StringCodec.ALL_CODECS.size();
    }

    /** {@inheritDoc} */
    @NotNull public Object getElementAt(final int i) {
        return StringCodec.ALL_CODECS.get(i);
    }

    /** {@inheritDoc} */
    public void addListDataListener(@NotNull final ListDataListener listDataListener) {
        // Because we don't fire events, we ignore listeners.
    }

    /** {@inheritDoc} */
    public void removeListDataListener(@NotNull final ListDataListener listDataListener) {
        // Because we don't fire events, we ignore listeners.
    }

} // class CodecListModel
