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

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.List;
import java.util.ArrayList;
import java.io.Reader;
import java.io.InputStreamReader;

/** A Shell Pane.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class ShellPane extends JPanel implements ActionListener {

    /** Serial version. */
    private static final long serialVersionUID = 1L;

    /** The terminal screen. */
    private final JTextArea textArea = new JTextArea(25, 80);

    /** The textfield to enter commands. */
    private final JTextField command = new JTextField();

    /** Button for clearing the terminal screen. */
    private final JButton clear = new JButton("clear");

    /** Button for stopping the current process. */
    private final JButton stop = new JButton("stop");

    /** ProcessBuilder for starting processes.
     * @serial exclude
     */
    private transient ProcessBuilder processBuilder = new ProcessBuilder().redirectErrorStream(true);

    /** Running shell process.
     * @serial exclude
     */
    private transient ShellWorker worker;

    /** Create a ShellPane. */
    public ShellPane() {
        setLayout(new BorderLayout());
        command.setForeground(Color.white);
        command.setBackground(Color.black);
        command.setFont(new Font(Font.MONOSPACED, textArea.getFont().getStyle(), textArea.getFont().getSize()));
        textArea.setForeground(Color.white);
        textArea.setBackground(Color.black);
        textArea.setFont(new Font(Font.MONOSPACED, textArea.getFont().getStyle(), textArea.getFont().getSize()));
        textArea.setEditable(false);
        add(new JScrollPane(textArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);
        add(command, BorderLayout.SOUTH);
        add(clear, BorderLayout.NORTH);
        add(stop, BorderLayout.EAST);
        command.addActionListener(this);
        clear.addActionListener(this);
        stop.addActionListener(this);
    }

    /** {@inheritDoc} */
    public void actionPerformed(final ActionEvent e) {
        if (e.getSource() == command) {
            processBuilder.command(parse(command.getText()));
            worker = new ShellWorker();
            worker.execute();
        }
        if (e.getSource() == clear) {
            textArea.setText("");
        }
        if (e.getSource() == stop) {
            worker.stop();
        }
    }

    /** Parses a command String into a list suitable for starting a process with arguments.
     * @param text Command String to parse.
     * @return List of command and arguments
     */
    public List<String> parse(final String text) {
        final List<String> strings = new ArrayList<String>();
        final StringBuilder sb = new StringBuilder();
        int state = 0;
        for (final char c : text.toCharArray()) {
            switch (state) {
            case 0:
                switch (c) {
                case ' ': state = 0; break;
                case '"': state = 2; break;
                default: state = 1; sb.append(c); break;
                }
                break;
            case 1:
                switch (c) {
                case ' ': state = 0; strings.add(sb.toString()); sb.setLength(0); break;
                case '"': state = 2; break;
                default: state = 1; sb.append(c); break;
                }
                break;
            case 2:
                switch (c) {
                case '"': state = 1; break;
                default: state = 2; sb.append(c); break;
                }
                break;
            default:
                assert false;
            }
        }
        if (sb.length() != 0) {
            strings.add(sb.toString());
        }
        return strings;
    }

    /** SwingWorker to run a process and read its output.
     * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
     * @since 0.1
     */
    class ShellWorker extends SwingWorker<Object, String> {

        /** The process to run. */
        private Process p;

        /** {@inheritDoc} */
        @Override
        public Object doInBackground() {
            try {
                p = processBuilder.start();
                try {
                    final Reader in = new InputStreamReader(p.getInputStream());
                    try {
                        final char[] buf = new char[4096];
                        for (int charsRead; !isCancelled() && (charsRead = in.read(buf)) != -1;) {
                            publish(new String(buf, 0, charsRead));
                        }
                    } finally {
                        in.close();
                    }
                } finally {
                    p.destroy();
                }
            } catch (final Throwable e) {
                publish(e.toString() + "\n");
            }
            return null;
        }

        /** {@inheritDoc} */
        @Override
        public void process(final List<String> chunks) {
            for (final String s : chunks) {
                textArea.append(s);
            }
        }

        /** Stop. */
        public void stop() {
            if (p != null) {
                p.destroy();
            }
            cancel(true);
        }
    }
}
