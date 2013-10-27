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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

/** ProgressDisplay handles a popup dialog for the mainview
 * which displays a process progressBar.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public final class ProgressDisplay extends JDialog implements Progress {

    /** Serial Version UID. */
    private static final long serialVersionUID = 1L;

    /** The progress progressBar.
     * @serial include
     */
    private final JProgressBar progressBar;  // the progress progressBar

    /** The text label.
     * @serial include
     */
    private final JLabel label;

    /** Create a ProgressDisplay.
     * @param parent Frame to display dialog on
     * @param title  Title string for progress dialog
     * @param max    initial maximum of progress points
     * @param text   the initial label text
     */
    @SuppressWarnings({"MagicNumber"})
    public ProgressDisplay(final JFrame parent, final String title, final int max, final String text) {
        super(parent, title, false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); // can't close

        final JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 15, 30, 15));
        label = new JLabel(text);
        label.setForeground(Color.BLACK);
        panel.add(label);

        progressBar = new JProgressBar(0, max);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setAlignmentY(SwingConstants.CENTER);
        panel.add(progressBar);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel);
        setSize(300, 140);
        setLocationRelativeTo(parent);

        setVisible(true);
    }

    /** {@inheritDoc} */
    public void finished() {
        setVisible(false);
        removeAll();
        dispose();
    }

    /** {@inheritDoc} */
    public void setValue(final int value) {
        final int max = progressBar.getMaximum();
        progressBar.setValue(value > max ? max : value);
    }

    /** {@inheritDoc} */
    public void setLabel(final String msg, final int max) {
        label.setText(msg);
        progressBar.setMaximum(max);
        progressBar.setValue(0);
    }

    /** {@inheritDoc} */
    public Component getParentComponent() {
        return this;
    }

} // class ProgressDisplay
