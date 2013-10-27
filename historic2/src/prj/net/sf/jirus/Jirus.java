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

import java.util.List;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequencer;
import javax.sound.midi.SysexMessage;
import javax.sound.midi.Transmitter;
import javax.swing.JFrame;
import net.sf.japi.io.args.ArgParser;
import net.sf.japi.io.args.BasicCommand;
import net.sf.japi.midi.MidiUtils;
import org.jetbrains.annotations.NotNull;

/** Jirus Main program.
 * Jirus is a software for working with midi devices.
 * It is especially designed for being used live, e.g. on stage.
 *
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
// Requirements / Wishlist
// - Allow more complex routing than just a simple keyboard split.
// - Allow setups and have them changed dynamically during runtime.
public class Jirus extends BasicCommand {

    /** Secondary channel for the split. */
    public static final int SPLIT_CHANNEL = 0x02;

    /** Split key note. */
    public static final int SPLIT_KEY = 0x30;

    /** Range of midi notes. */
    public static final int MIDI_NOTE_RANGE = 128;

    /** Main Program.
     * @param args Command line arguments (try --help).
     */
    public static void main(@NotNull final String... args) {
        ArgParser.simpleParseAndRun(new Jirus(), args);
    }

    /** {@inheritDoc} */
    @SuppressWarnings({"InstanceMethodNamingConvention"})
    public int run(@NotNull final List<String> args) throws Exception {
        final JFrame frame = new JFrame("foo");
        if (false) {
            final MidiDevice device1 = MidiUtils.getTransmittingDevice("Virus TI Synth");
            final MidiDevice device2 = MidiUtils.getReceivingDevice("Virus TI Synth");
            final MidiDevice device3 = MidiUtils.getTransmittingDevice("Virus TI MIDI");
            final MidiDevice device4 = MidiUtils.getReceivingDevice("Virus TI MIDI");
            final Sequencer sequencer = (Sequencer) MidiUtils.getDeviceByName("Real Time Sequencer");
            device1.open();
            device2.open();
            device3.open();
            device4.open();
            final Transmitter transmitter = device1.getTransmitter();
            final Receiver receiver = device4.getReceiver();
            transmitter.setReceiver(new MyReceiver(receiver));

            // The following SysEx message is known to do the following:
            // - It sets the tempo to 0x41 which is 128 BPM (63 is the base, + 65 which is 0x41 so that's 128 BPM)
            // It sets "Local" to "Off", disabling the Synthesizer's internal feedback loop so all Midi is routed through this program.
            final SysexMessage sysexMessage = MidiUtils.createSysexMessage("f0002033011071401041f7");
            receiver.send(new SysexMessage(), 0);
            // The following sets logo groove to NN (0x00 - 0x7F) : f00020330100730834NNf7

            System.out.println("Go!");
        }
        return 0;
    }

}
