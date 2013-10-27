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

package net.sf.japi.midi;

import java.util.ArrayList;
import java.util.Collection;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import org.jetbrains.annotations.NotNull;

/** A Midi Receiver which sends its received message to an arbitrary number of other receivers.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class MultiReceiver implements Receiver {

    /** The Receivers. */
    private final Collection<Receiver> receivers = new ArrayList<Receiver>();

    /** Adds a Receiver.
     * @param receiver Receiver to add.
     */
    public void addReceiver(@NotNull final Receiver receiver) {
        receivers.add(receiver);
    }

    /** Removes a Receiver.
     * @param receiver Receiver to remove.
     */
    public void removeReceiver(@NotNull final Receiver receiver) {
        receivers.remove(receiver);
    }

    /** {@inheritDoc} */
    public void send(final MidiMessage message, final long timeStamp) {
        for (final Receiver receiver : receivers) {
            receiver.send(message, timeStamp);
        }
    }

    /** {@inheritDoc} */
    public void close() {
        for (final Receiver receiver : receivers) {
            receiver.close();
        }
    }
}
