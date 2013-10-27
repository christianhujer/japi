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

/** This is a simple test case for accessing AWT / Swing from the script interpreter.
 */

/** ActionListener that prints a message.
 * @param e ActionEvent.
 */
function aa(e) {
    print("Test.\n");
}

/** WindowListener that closes the window.
 * @param e WindowEvent.
 */
function wl1(e) {
    if (e.ID == java.awt.event.WindowEvent.WINDOW_CLOSING) {
        e.source.dispose();
    }
}

frame = new javax.swing.JFrame("Test");
button = new javax.swing.JButton("Click");
button.addActionListener(aa);
frame.add(button);
frame.addWindowListener(wl1);
frame.pack();
frame.setVisible(true);
