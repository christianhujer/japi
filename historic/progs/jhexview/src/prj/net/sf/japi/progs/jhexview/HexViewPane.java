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

package net.sf.japi.progs.jhexview;

import java.awt.BorderLayout;
import java.util.Formatter;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/** Component for viewing binary data.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class HexViewPane extends JPanel {


    /** The data to show.
     * @serial include
     */
    private final byte[] data;

    /** The columns to show.
     * @serial include
     */
    private final int cols = 16;

    /** The rows to show.
     * @serial include
     */
    private final int rows;

    /** The TextArea that displays the hex info.
     * @serial include
     */
    private final JTextArea textArea = new JTextArea(25, cols * 4 + 8);

    /** Creates a HexViewPane.
     * The data is referenced, not copied.
     * Changes to the array will write through to the HexViewPane and vice versa.
     * @param data Data to view.
     */
    public HexViewPane(final byte[] data) {
        setLayout(new BorderLayout());
        textArea.setEditable(false);
        add(new JScrollPane(textArea));
        this.data = data;
        rows = (data.length + cols - 1) / cols;
        updateData();
    }

    /** Updates the data. */
    private void updateData() {
        final Formatter format = new Formatter();
        for (int i = 0; i < rows; i++) {
            format.format("%08x  ", i * cols);
            for (int j = 0; j < cols; j++) {
                final int offset = i * cols + j;
                if (offset < data.length) {
                    format.format("%02x ", data[offset]);
                } else {
                    format.format("   ");
                }
            }
            for (int j = 0; j < cols; j++) {
                final int offset = i * cols + j;
                if (offset < data.length) {
                    format.format(" %c", (char) data[offset]);
                } else {
                    format.format("  ");
                }
            }
            format.format("\n");
        }
        textArea.setText(format.toString());
    }
}
