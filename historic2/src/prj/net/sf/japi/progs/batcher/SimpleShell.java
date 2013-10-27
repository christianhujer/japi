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

package net.sf.japi.progs.batcher;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

/** SimpleShell example application.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class SimpleShell implements ActionListener {

    /** Main program of SimpleShell.
     * @param args Command line arguments (ignored).
     */
    public static void main(final String... args) {
        new SimpleShell();
    }

    /** The tabs. */
    private final JTabbedPane tabs;

    /** Create a SimpleShell instance. */
    public SimpleShell() {
        final JFrame frame = new JFrame("SimpleShell");
        tabs = new JTabbedPane();
        frame.getContentPane().add(tabs, BorderLayout.CENTER);
        final JMenuBar menuBar = new JMenuBar();
        final JMenu file = new JMenu("File");
        menuBar.add(file);
        final JMenuItem newFile = new JMenuItem("New");
        newFile.addActionListener(this);
        file.add(newFile);
        frame.setJMenuBar(menuBar);
        frame.pack();
        frame.setVisible(true);
    }
    public void actionPerformed(final ActionEvent e) {
        tabs.add(new ShellPane());
    }
}
