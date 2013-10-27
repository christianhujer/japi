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

package net.sf.japi.swing.misc;

import java.awt.Component;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import org.jetbrains.annotations.Nullable;

/**
 * ConsoleProgress is a {@link Progress} implementation for headless systems.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class ConsoleProgress implements Progress {

    /** The destination for messages. */
    private final Appendable out;

    /** The flushable for out. */
    @Nullable private final Flushable flushOut;

    /** The closeable for out. */
    @Nullable private final Closeable closeOut;

    /** Current maximum. */
    private int max;

    /** Create a ConsoleProgress that uses System.out. */
    public ConsoleProgress() {
        this(System.out, true, false);
    }

    /** Create a ConsoleProgress that uses the specified Appendable.
     * @param out Appendable to use
     * @param flush Whether to flush the Appendable
     * @param close Whether to close the Appendable
     */
    public ConsoleProgress(final Appendable out, final boolean flush, final boolean close) {
        this.out  = out;
        flushOut = flush && out instanceof Flushable ? (Flushable) out : null;
        closeOut = close && out instanceof Closeable ? (Closeable) out : null;
    }

    /** Close the closeable. */
    private void close() {
        if (closeOut != null) {
            try {
                closeOut.close();
            } catch (final IOException ignore) {
                // ignore
            }
        }
    }

    /** Flush the flushable. */
    private void flush() {
        if (flushOut != null) {
            try {
                flushOut.flush();
            } catch (final IOException ignore) {
                // ignore
            }
        }
    }

    /** {@inheritDoc} */
    public void finished() {
        try {
            out.append("100\n");
        } catch (final IOException ignore) {
            // ignore
        }
        flush();
        close();
    }

    /** {@inheritDoc} */
    @Nullable
    public Component getParentComponent() {
        return null;
    }

    /** {@inheritDoc} */
    public void setLabel(final String msg, final int max) {
        this.max = max;
        try {
            out.append("\n  0%: ");
            flush();
            if (msg != null) {
                out.append(msg);
                flush();
                for (int i = 0; i < msg.length(); i++) {
                    out.append('\b');
                }
            }
            for (int i = 0; i < "  0%: ".length(); i++) {
                out.append('\b');
            }
        } catch (final IOException ignore) {
            // ignore
        }
    }

    /** {@inheritDoc} */
    public void setValue(final int value) {
        if (max == 0) {
            return;
        }
        try {
            //noinspection MagicNumber
            final int prog = value * 100 / max;
            out.append(String.format("%3d", prog));
            flush();
            out.append("\b\b\b");
        } catch (final IOException ignore) {
            // ignore
        }
    }

} // class ConsoleProgress
