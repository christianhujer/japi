/*
 * Copyright (c) 2009, Your Corporation. All Rights Reserved.
 */

package net.sf.jirus;

import javax.sound.midi.spi.MidiDeviceProvider;
import javax.sound.midi.MidiDevice;

/**
 * TODO:2009-06-21:christianhujer:Documentation.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class JirusControlProvider extends MidiDeviceProvider {

    /** The DeficeInfos. */
    private MidiDevice.Info[] deviceInfo = {
        new MidiDevice.Info("Jirus Control", "Christian Hujer", "Jirus Control", "trunk") {}
    };
    public MidiDevice.Info[] getDeviceInfo() {
        return new MidiDevice.Info[0];
    }

    public MidiDevice getDevice(final MidiDevice.Info info) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
