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

package net.sf.japi.midilearn;

import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import javax.swing.JApplet;
import javax.swing.JFrame;

/** A program for learning things about music that uses MIDI.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class MidiLearn extends JApplet implements WindowListener {

    /**
     * Main program.
     * @param args Command line arguments (ignored).
     */
    public static void main(final String... args) {
        final JFrame f = new JFrame("MidiLearn");
        final MidiLearn midiLearn = new MidiLearn();
        f.add(midiLearn);
        f.addWindowListener(midiLearn);
        f.setVisible(true);
    }

    /** Creates a MidiLearn Applet. */
    public MidiLearn() {
    }

    /** {@inheritDoc} */
    public void windowOpened(final WindowEvent e) {
        // nothing to do
    }

    /** {@inheritDoc} */
    public void windowClosing(final WindowEvent e) {
        // TODO:2009-02-22:christianhujer:Implement.
    }

    /** {@inheritDoc} */
    public void windowClosed(final WindowEvent e) {
        // nothing to do
    }

    /** {@inheritDoc} */
    public void windowIconified(final WindowEvent e) {
        // nothing to do
    }

    /** {@inheritDoc} */
    public void windowDeiconified(final WindowEvent e) {
        // nothing to do
    }

    /** {@inheritDoc} */
    public void windowActivated(final WindowEvent e) {
        // nothing to do
    }

    /** {@inheritDoc} */
    public void windowDeactivated(final WindowEvent e) {
        // nothing to do
    }
}
