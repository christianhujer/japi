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

package net.sf.japi.progs.jeduca.swing;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import static java.awt.GridBagConstraints.CENTER;
import static java.awt.GridBagConstraints.HORIZONTAL;
import static java.awt.GridBagConstraints.NONE;
import static java.awt.GridBagConstraints.WEST;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.net.MalformedURLException;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import net.sf.japi.swing.action.ActionBuilderFactory;
import org.jetbrains.annotations.Nullable;

/** Class for displaying a Dialog which requests that the user enters a URL or opens a File selection box.
 * It works quite like {@link JFileChooser}, even a bit simpler.
 * Just invoke one of the static methods {@link #showOpenURLDialog(Component)} or {@link #showOpenURLDialog(Component,JFileChooser)}.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public final class OpenURLPane extends JComponent {

    /** Serial Version. */
    private static final long serialVersionUID = 1L;

    /** Create a new OpenURLPane. */
    private OpenURLPane() {
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(3, 3, 3, 3));
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 8;
        gbc.anchor = WEST;
        add(new JLabel("Adresse:"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 7;
        gbc.fill = HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.anchor = CENTER;
        add(input, gbc);

        gbc.gridx = 7;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = NONE;
        gbc.weightx = 0.0;
        add(new JButton(selectFile), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 8;
        gbc.fill = HORIZONTAL;
        add(new JSeparator(), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = NONE;
        gbc.weightx = 1.0;
        add(new JLabel(), gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.0;
        add(new JButton(delete), gbc);

        gbc.gridx = 4;
        add(new JButton(ok), gbc);

        gbc.gridx = 6;
        add(new JButton(cancel), gbc);
    }

    /** Action for ok button.
     * @serial include
     */
    private final Action ok         = ActionBuilderFactory.getInstance().getActionBuilder("net.sf.japi.progs.jeduca.swing").createAction(true, "ok", this);

    /** Action for cancel button.
     * @serial include
     */
    private final Action cancel     = ActionBuilderFactory.getInstance().getActionBuilder("net.sf.japi.progs.jeduca.swing").createAction(true, "cancel", this);

    /** Action for delete button.
     * @serial include
     */
    private final Action delete     = ActionBuilderFactory.getInstance().getActionBuilder("net.sf.japi.progs.jeduca.swing").createAction(true, "delete", this);

    /** Action for selectFile button.
     * @serial include
     */
    private final Action selectFile = ActionBuilderFactory.getInstance().getActionBuilder("net.sf.japi.progs.jeduca.swing").createAction(true, "selectFile", this);

    /** TextField with user input.
     * @serial include
     */
    private final JTextField input  = new JTextField();

    /** Dialog to show.
     * @serial include
     */
    private JDialog dialog;

    /** JFileChooser to use.
     * @serial include
     */
    private JFileChooser fileChooser;

    /** String with result of user selection / input.
     * @serial include
     */
    @Nullable private String result;

    /** Show a dialog using a default JFileChooser.
     * @param parent Parent component
     * @return selected url or null
     */
    public static String showOpenURLDialog(final Component parent) {
        return showOpenURLDialog(parent, new JFileChooser());
    }

    /** Show a dialog using a specified JFileChooser.
     * Use this method if the default JFileChooser doesn't serve your needs, e.g. because you need special filefilters or you enhance your file chooser
     * with special components.
     * @param parent Parent component
     * @param fileChooser JFileChooser to use for the file selection dialog
     * @return selected url or null
     */
    public static String showOpenURLDialog(final Component parent, final JFileChooser fileChooser) {
        final OpenURLPane pane = new OpenURLPane();
        pane.fileChooser = fileChooser;
        final Window w = getWindowForComponent(parent);
        if (w instanceof Dialog) {
            pane.dialog = new JDialog((Dialog) w, ActionBuilderFactory.getInstance().getActionBuilder("net.sf.japi.progs.jeduca.swing").getString("openURL"), true);
        } else {
            assert w instanceof Frame;
            pane.dialog = new JDialog((Frame) w, ActionBuilderFactory.getInstance().getActionBuilder("net.sf.japi.progs.jeduca.swing").getString("openURL"), true);
        }
        pane.dialog.add(pane);
        pane.dialog.pack();
        pane.dialog.setVisible(true);
        pane.dialog.dispose();
        //noinspection AssignmentToNull
        pane.dialog = null;
        final String result = pane.result;
        if (rootFrame != null) {
            rootFrame.dispose();
            rootFrame = null;
        }
        return result;
    }

    /** Get the Window for a Component.
     * @param c Component to get Window for
     * @return Window for c
     */
    private static Window getWindowForComponent(final Component c) {
        if (c == null) {
            return getRootFrame();
        }
        if (c instanceof Frame || c instanceof Dialog) {
            return (Window) c;
        }
        return getWindowForComponent(c.getParent());
    }

    /** Frame if now owner frame. */
    @Nullable private static Frame rootFrame;

    /** Get the root frame to use if there is no owner frame.
     * @return frame
     */
    @Nullable private static synchronized Window getRootFrame() {
        if (rootFrame == null) {
            rootFrame = new JFrame();
        }
        return rootFrame;
    }

    /** OK Action. */
    public void ok() {
        result = input.getText();
        dialog.setVisible(false);
    }

    /** Cancel Action. */
    public void cancel() {
        result = null;
        dialog.setVisible(false);
    }

    /** Delete Action. */
    public void delete() {
        input.setText("");
    }

    /** File Selection Action. */
    public void selectFile() {
        final int res = fileChooser.showOpenDialog(this);
        if (res == JFileChooser.CANCEL_OPTION) { // NOPMD
            // Nothing to do in case of cancel.
        } else if (res == JFileChooser.APPROVE_OPTION) {
            try {
                input.setText(fileChooser.getSelectedFile().toURI().toURL().toString());
            } catch (MalformedURLException e) {
                JOptionPane.showMessageDialog(this, e, "Interner Fehler", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            assert res == JFileChooser.ERROR_OPTION;
            JOptionPane.showMessageDialog(this, "Fehler beim Auswählen der Datei", "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

} // class OpenURLPane
