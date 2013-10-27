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

package net.sf.japi.tools.midiDeviceLister;

import java.util.List;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;
import net.sf.japi.io.args.ArgParser;
import net.sf.japi.io.args.BasicCommand;
import org.jetbrains.annotations.NotNull;

/** A small command line program that lists Midi Devices.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class MidiDeviceLister extends BasicCommand {

    /** Main program.
     * @param args Command line arguments (ignored).
     */
    public static void main(final String... args) {
        ArgParser.simpleParseAndRun(new MidiDeviceLister(), args);
    }

    /** {@inheritDoc} */
    @SuppressWarnings({"InstanceMethodNamingConvention"})
    public int run(@NotNull final List<String> args) throws Exception {
        for (final MidiDevice.Info deviceInfo : MidiSystem.getMidiDeviceInfo()) {
            System.out.println("Name: " + deviceInfo.getName());
            System.out.println("Version: " + deviceInfo.getVersion());
            System.out.println("Vendor: " + deviceInfo.getVendor());
            System.out.println("Description: " + deviceInfo.getDescription());
            final MidiDevice device = MidiSystem.getMidiDevice(deviceInfo);
            System.out.println("Maximum transmitters: " + getMaxInfo(device.getMaxTransmitters()));
            System.out.println("Maximum receivers: " + getMaxInfo(device.getMaxReceivers()));
            System.out.println("Sequencer: " + (device instanceof Sequencer));
            System.out.println("Synthesizer: " + (device instanceof Synthesizer));
            System.out.println();
        }
        return 0;
    }

    /** Returns a String representing a transmitter or receiver maximum count information.
     * @param count Count to represent as String.
     * @return String representing that count.
     */
    private static String getMaxInfo(final int count) {
        switch (count) {
        case -1:
            return "-1 (Unlimited)";
        case 0:
            return "0 (Unsupported)";
        default:
            return Integer.toString(count);
        }
    }
}
