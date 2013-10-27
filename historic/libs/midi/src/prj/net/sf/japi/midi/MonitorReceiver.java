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

import java.io.Closeable;
import java.io.IOException;
import java.util.Formatter;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** A MonitorReceiver is a Receiver that prints messages to System.out.
 * It optionally can delegate to another receiver.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class MonitorReceiver implements Receiver {

    /** The Receiver to which to delegate. */
    @Nullable private final Receiver receiver;

    /** The prefix for messages printed by this MonitorReceiver. */
    private final String prefix;

    /** The Appendable to which messages are printed. */
    private final Appendable out;

    /** The Formatter that is used to format messages to {@link #out}. */
    private final Formatter format;

    /** Create a MonitorReceiver.
     * @param prefix Prefix for messages printed by this MonitorReceiver.
     */
    public MonitorReceiver(@NotNull final String prefix) {
        this(prefix, null);
    }

    /** Create a MonitorReceiver.
     * @param prefix Prefix for messages printed by this MonitorReceiver.
     * @param receiver Receiver to which messages shall be delegated.
     */
    public MonitorReceiver(@NotNull final String prefix, @Nullable final Receiver receiver) {
        this.prefix = prefix;
        out = System.out;
        format = new Formatter(out);
        this.receiver = receiver;
    }

    /** {@inheritDoc} */
    public void send(final MidiMessage message, final long timeStamp) {
        format.format("[%s] %08x ", prefix, timeStamp);
        final byte[] msg = message.getMessage();
        for (final byte b : msg) {
            format.format("%02x", b);
        }
        format.format("%n");
        format.flush();
        if (receiver != null) {
            receiver.send(message, timeStamp);
        }
    }

    /** {@inheritDoc} */
    public void close() {
        if (receiver != null) {
            receiver.close();
        }
        format.flush();
        if (out instanceof Closeable) {
            try {
                ((Closeable) out).close();
            } catch (final IOException ignore) {
                // ignore
            }
        }
    }

}
