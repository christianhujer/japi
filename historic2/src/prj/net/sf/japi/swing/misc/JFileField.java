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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;

/** Component for selecting a file.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class JFileField extends JComponent {

    /** The default number of textfield columns. */
    private static final int DEFAULT_TEXTFIELD_COLUMNS = 16;

    /** The label.
     * @serial include
     */
    private final JLabel label;

    /** The file field.
     * @serial include
     */
    private final JTextField fileField;

    /** The button to start the JFileChooser.
     * @serial include
     */
    private final JFileChooserButton chooserButton;

    /**
     * Create a JFileField.
     * @param labelText          The text for the label.
     * @param initial            The initial path.
     * @param fileSelectionMode  The mode for the file selection.
     * @see JFileChooser for fileSelectionMode.
     */
    public JFileField(final String labelText, final String initial, final int fileSelectionMode) {
        setLayout(new GridBagLayout());
        fileField = new JTextField(initial, DEFAULT_TEXTFIELD_COLUMNS);
        chooserButton = new JFileChooserButton(fileField, fileSelectionMode);
        label = new JLabel(labelText);
        addFields();
    }

    /**
     * Create a JFileField.
     * @param initial            The initial path.
     * @param fileSelectionMode  The mode for the file selection.
     * @see JFileChooser for fileSelectionMode.
     */
    public JFileField(final String initial, final int fileSelectionMode) {
        setLayout(new GridBagLayout());
        fileField = new JTextField(initial, DEFAULT_TEXTFIELD_COLUMNS);
        chooserButton = new JFileChooserButton(fileField, fileSelectionMode);
        label = null;
        addFields();
    }

    /**
     * Adds all fields to this component.
     */
    private void addFields() {
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        if (label != null) {
            gbc.weightx = 0.0;
            add(label, gbc);
        }
        gbc.gridx++;
        gbc.weightx = 1.0;
        add(fileField, gbc);
        gbc.gridx++;
        gbc.weightx = 0.0;
        add(chooserButton, gbc);
    }

    /** {@inheritDoc} */
    @Override public void setEnabled(final boolean enabled) {
        super.setEnabled(enabled);
        fileField.setEnabled(enabled);
        chooserButton.setEnabled(enabled);
        if (label != null) {
            label.setEnabled(enabled);
        }
    }

    /**
     * Set the text, which is the filename.
     * @param text filename
     */
    public void setText(final String text) {
        fileField.setText(text);
    }

    /**
     * Get the text, which is the filename.
     * @return text (filename)
     */
    public String getText() {
        return fileField.getText();
    }

} // class JFileField
