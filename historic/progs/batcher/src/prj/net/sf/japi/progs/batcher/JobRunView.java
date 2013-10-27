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

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.ScrollPaneConstants;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/** View to a Job Run.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class JobRunView extends JComponent {

    /** The textArea of this JobRunView. */
    private JTextArea textArea = new JTextArea(25, 80);

    /** Creates a JobRunView. */
    public JobRunView() {
        setLayout(new GridLayout(1, 1));
        textArea.setForeground(Color.white);
        textArea.setBackground(Color.black);
        textArea.setEditable(false);
        textArea.setFont(new Font(Font.MONOSPACED, textArea.getFont().getStyle(), textArea.getFont().getSize()));
        add(new JScrollPane(textArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED));
    }

    /** Appends a String to this JobRunView.
     * @param s String to append.
     */
    public void append(final String s) {
        textArea.append(s);
    }
}
