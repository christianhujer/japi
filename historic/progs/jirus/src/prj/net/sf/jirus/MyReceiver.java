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

package net.sf.jirus;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

/**
 * Class Description.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class MyReceiver implements Receiver {

    /** Note states (on / off). */
    private final boolean[] noteState = new boolean[Jirus.MIDI_NOTE_RANGE];

    /** Currently palying note. */
    private int playingNote = 0xFF;

    /** Receiver to which the data will be forwarded. */
    private final Receiver receiver;

    /** Creates MyReceiver.
     * @param receiver Receiver to which the data shall be forwarded.
     */
    public MyReceiver(final Receiver receiver) {
        this.receiver = receiver;
    }

    /** {@inheritDoc} */
    public void close() { }

    /** {@inheritDoc} */
    public void send(final MidiMessage message, final long timeStamp) {
        try {
            final byte[] messageData = message.getMessage();
            boolean echoMessage = true;
            if (messageData.length != 1 || messageData[0] != (byte) ShortMessage.TIMING_CLOCK) {
                if (message instanceof ShortMessage) { // NOPMD
                    final ShortMessage smg = (ShortMessage) message;
                    final int cmd = smg.getCommand();
                    if (cmd == ShortMessage.NOTE_ON || cmd == ShortMessage.NOTE_OFF) {
                        final int note = smg.getData1();
                        final int velo = smg.getData2();
                        noteState[note] = cmd == ShortMessage.NOTE_ON;
                        if (note < Jirus.SPLIT_KEY) {
                            receiver.send(message, timeStamp);
                            if (cmd == ShortMessage.NOTE_OFF) {
                                playingNote = 0xFF;
                            } else {
                                //noinspection ConstantConditions
                                assert cmd == ShortMessage.NOTE_ON;
                                if (note < playingNote) {
                                    smg.setMessage(ShortMessage.NOTE_OFF, Jirus.SPLIT_CHANNEL, playingNote, velo);
                                    receiver.send(message, timeStamp);
                                    smg.setMessage(ShortMessage.NOTE_ON, Jirus.SPLIT_CHANNEL, note, velo);
                                    playingNote = note;
                                } else {
                                    echoMessage = false;
                                }
                            }
                            smg.setMessage(cmd, Jirus.SPLIT_CHANNEL, note, velo);
                        }
                    }
                }
            }
            if (echoMessage) {
                receiver.send(message, timeStamp);
            }
        } catch (final InvalidMidiDataException e) {
            // This should not happen.
            // It would mean that the program itself did something wrong.
            e.printStackTrace();
        }
    }
}
