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

package net.sf.japi.cardlearn;

import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** The CardDatabaseConfigEditor allows editing a CardDatabaseConfig.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public final class CardDatabaseConfigEditor extends JComponent {

    /** The CardDatabaseConfig to edit.
     * @serial include
     */
    private final CardDatabaseConfig config;

    /** The labels for the fields.
     * @serial include
     */
    private final JLabel[] labels = new JLabel[6];

    /** The textfields for the fields.
     * @serial include
     */
    private final JTextField[] fields = new JTextField[6];

    /** Creates a CardDatabaseConfigEditor.
     * @param config CardDatabaseConfiguration to edit.
     */
    private CardDatabaseConfigEditor(final CardDatabaseConfig config) {
        this.config = config;
        setLayout(new GridLayout(6, 2));
        for (int i = 0; i < 6; i++) {
            labels[i] = new JLabel("Field " + i + ": ");
            fields[i] = new JTextField(config.getFieldname(i));
            add(labels[i]);
            add(fields[i]);
        }
    }

    /** Updates the config from the editor values. */
    private void updateValues() {
        for (int i = 0; i < 6; i++) {
            config.setFieldname(i, fields[i].getText());
        }
    }

    /** Displays a dialog for editing a CardDatabaseConfig.
     * @param parent Parent component on which to show the editor.
     * @param config Configuration to edit.
     * @return edited configuration (same object as <var>config</var>) or <code>null</code> if the dialog was cancelled, in which case <var>config</var> is unchanged.
     */
    @Nullable public static CardDatabaseConfig showConfigEditor(@Nullable final Component parent, @NotNull final CardDatabaseConfig config) {
        final CardDatabaseConfigEditor editor = new CardDatabaseConfigEditor(config);
        final int returnValue = JOptionPane.showConfirmDialog(parent, editor, "XXX", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (returnValue == JOptionPane.OK_OPTION) {
            editor.updateValues();
            return config;
        }
        return null;
    }

}
