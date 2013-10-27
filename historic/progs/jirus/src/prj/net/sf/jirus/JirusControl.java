/*
 * Copyright (c) 2009, Your Corporation. All Rights Reserved.
 */

package net.sf.jirus;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;
import java.util.List;

/**
 * TODO:2009-06-21:christianhujer:Documentation.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class JirusControl implements MidiDevice {

    /** Constant that indicates that timestamps are not supported. */
    private static final int MICROSECOND_POSITION_UNSUPPORTED = 1;

    /** Constant that indicates support for an unlimited number of transmitters. */
    private static final int UNLIMITED_TRANSMITTERS = -1;

    /** Constant that indicates support for an unlimited number of receivers. */
    private static final int UNLIMITED_RECEIVERS = -1;

    /** Whether or not JirusControl is open. */
    private boolean open;

    /** {@inheritDoc} */
    public Info getDeviceInfo() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /** {@inheritDoc} */
    public void open() throws MidiUnavailableException {
        open = true;
    }

    /** {@inheritDoc} */
    public void close() {
        open = false;
    }

    /** {@inheritDoc} */
    public boolean isOpen() {
        return open;
    }

    /** {@inheritDoc} */
    public long getMicrosecondPosition() {
        return MICROSECOND_POSITION_UNSUPPORTED;
    }

    /** {@inheritDoc} */
    public int getMaxReceivers() {
        return UNLIMITED_RECEIVERS;
    }

    /** {@inheritDoc} */
    public int getMaxTransmitters() {
        return UNLIMITED_TRANSMITTERS;
    }

    /** {@inheritDoc} */
    public Receiver getReceiver() throws MidiUnavailableException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /** {@inheritDoc} */
    public List<Receiver> getReceivers() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /** {@inheritDoc} */
    public Transmitter getTransmitter() throws MidiUnavailableException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /** {@inheritDoc} */
    public List<Transmitter> getTransmitters() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
