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
import java.util.Collections;
import java.util.List;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.SysexMessage;
import javax.sound.midi.Transmitter;
import org.jetbrains.annotations.NotNull;

/** Utility functions for working with MIDI.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public final class MidiUtils {

    /** Utility class - do not instanciate. */
    private MidiUtils() {
    }

    /** Returns all {@link MidiDevice}s which provide {@link Receiver}s.
     * @return All {@link MidiDevice}s which provide {@link Receiver}s.
     * @throws MidiUnavailableException if one of the requested devices is unavailable due to resource restrictions.
     */
    public static MidiDevice[] getAllReceivingDevices() throws MidiUnavailableException {
        return getReceivingDevices(MidiSystem.getMidiDeviceInfo());
    }

    /** Returns all {@link MidiDevice}s which provide {@link Transmitter}s.
     * @return All {@link MidiDevice}s which provide {@link Transmitter}s.
     * @throws MidiUnavailableException if one of the requested devices is unavailable due to resource restrictions.
     */
    public static MidiDevice[] getAllTransmittingDevices() throws MidiUnavailableException {
        return getTransmittingDevices(MidiSystem.getMidiDeviceInfo());
    }

    /** Returns those {@link MidiDevice}s which provide {@link Receiver}s.
     * @param infos Device informations of which to return devices.
     * @return Those {@link MidiDevice}s from <var>infos</var> which provide {@link Receiver}s.
     * @throws MidiUnavailableException if one of the requested devices is unavailable due to resource restrictions.
     */
    public static MidiDevice[] getReceivingDevices(final MidiDevice.Info... infos) throws MidiUnavailableException {
        final List<MidiDevice> receivingDevices = new ArrayList<MidiDevice>();
        for (final MidiDevice.Info deviceInfo : infos) {
            final MidiDevice device = MidiSystem.getMidiDevice(deviceInfo);
            if (device.getMaxReceivers() != 0) {
                receivingDevices.add(device);
            }
        }
        Collections.sort(receivingDevices, DeviceComparator.getInstance());
        return receivingDevices.toArray(new MidiDevice[receivingDevices.size()]);
    }

    /** Returns those {@link MidiDevice}s which provide {@link Transmitter}s.
     * @param infos Device informations of which to return devices.
     * @return Those {@link MidiDevice}s from <var>infos</var> which provide {@link Transmitter}s.
     * @throws MidiUnavailableException if one of the requested devices is unavailable due to resource restrictions.
     */
    public static MidiDevice[] getTransmittingDevices(final MidiDevice.Info... infos) throws MidiUnavailableException {
        final List<MidiDevice> transmittingDevices = new ArrayList<MidiDevice>();
        for (final MidiDevice.Info deviceInfo : infos) {
            final MidiDevice device = MidiSystem.getMidiDevice(deviceInfo);
            if (device.getMaxTransmitters() != 0) {
                transmittingDevices.add(device);
            }
        }
        Collections.sort(transmittingDevices, DeviceComparator.getInstance());
        return transmittingDevices.toArray(new MidiDevice[transmittingDevices.size()]);
    }

    /** Creates a Sysex message from a String.
     * @param data String from which to create the Sysex message.
     * @return Sysex message created from <var>data</var>.
     * @throws InvalidMidiDataException if <var>data</var> does not denote a valid Sysex message (the first byte must be 0xF0 or 0xF7).
     */
    public static SysexMessage createSysexMessage(@NotNull final String data) throws InvalidMidiDataException {
        final SysexMessage message = new SysexMessage();
        final byte[] realData = new byte[data.length() / 2];
        for (int i = 0; i < realData.length; i++) {
            realData[i] = (byte) Integer.parseInt(data.substring(i * 2, i * 2 + 2), 16);
        }
        System.out.format("%n");
        message.setMessage(realData, realData.length);
        return message;
    }

    /** Returns the first device found in the system with the specified name that provides Receivers.
     * @param name Name of the receiving device to return.
     * @return The requested device or <code>null</code> if the requested device was not found.
     * @throws MidiUnavailableException if one of the requested devices is unavailable due to resource restrictions.
     */
    public static MidiDevice getReceivingDevice(@NotNull final String name) throws MidiUnavailableException {
        for (final MidiDevice.Info deviceInfo : MidiSystem.getMidiDeviceInfo()) {
            if (deviceInfo.getName().equals(name)) {
                final MidiDevice device = MidiSystem.getMidiDevice(deviceInfo);
                if (device.getMaxReceivers() != 0) {
                    return device;
                }
            }
        }
        return null;
    }

    /** Returns the first device found in the system with the specified name that provides Transmitters.
     * @param name Name of the receiving device to return.
     * @return The requested device or <code>null</code> if the requested device was not found.
     * @throws MidiUnavailableException if one of the requested devices is unavailable due to resource restrictions.
     */
    public static MidiDevice getTransmittingDevice(@NotNull final String name) throws MidiUnavailableException {
        for (final MidiDevice.Info deviceInfo : MidiSystem.getMidiDeviceInfo()) {
            if (deviceInfo.getName().equals(name)) {
                final MidiDevice device = MidiSystem.getMidiDevice(deviceInfo);
                if (device.getMaxTransmitters() != 0) {
                    return device;
                }
            }
        }
        return null;
    }

    /** Returns a device by its name.
     * @param name Name of the device to return.
     * @return The first device found that matches <var>name</var> or <code>null</code> if the requested device was not found.
     * @throws MidiUnavailableException In case Midi is not available.
     */
    public static MidiDevice getDeviceByName(@NotNull final String name) throws MidiUnavailableException {
        for (final MidiDevice.Info deviceInfo : MidiSystem.getMidiDeviceInfo()) {
            if (deviceInfo.getName().equals(name)) {
                return MidiSystem.getMidiDevice(deviceInfo);
            }
        }
        return null;
    }
}
