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

package net.sf.japi.midi.gui;

import javax.swing.AbstractSpinnerModel;
import org.jetbrains.annotations.Nullable;

/** A SpinnerModel for MIDI Channels.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class ChannelSpinnerModel extends AbstractSpinnerModel {

    /** The current channel. */
    private int value;

    /** Creates a SpinnerChannelModel which initially selects channel 0. */
    public ChannelSpinnerModel() {
        value = 0;
    }

    /** {@inheritDoc} */
    public Object getValue() {
        return value;
    }

    /** {@inheritDoc} */
    public void setValue(final Object value) {
        final int newValue = (Integer) value;
        if (newValue < 0x0 || newValue > 0xF) {
            throw new IllegalArgumentException("Not a valid MIDI channel.");
        }
        this.value = newValue;
        fireStateChanged();
    }

    /** {@inheritDoc} */
    @Nullable public Object getNextValue() {
        return value < 0xF ? value + 1 : null;
    }

    /** {@inheritDoc} */
    @Nullable public Object getPreviousValue() {
        return value > 0x0 ? value - 1 : null;
    }
}
