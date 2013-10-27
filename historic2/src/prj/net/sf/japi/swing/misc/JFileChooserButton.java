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

import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import net.sf.japi.swing.action.ActionBuilder;
import net.sf.japi.swing.action.ActionBuilderFactory;
import net.sf.japi.swing.action.ActionMethod;

/** JButton for choosing a file from hd.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class JFileChooserButton extends JButton {

    /** Action Builder. */
    private static final ActionBuilder ACTION_BUILDER = ActionBuilderFactory.getInstance().getActionBuilder("net.sf.japi.swing.misc");

    /** The JTextField to read/write the file path to.
     * @serial include
     */
    private final JTextField textField;

    /** The file selection mode.
     * @serial include
     */
    private final int fileSelectionMode;

    /** The base directory for choosing files.
     * @serial include
     */
    private File base;

    /** The JFileChooser to use.
     * @serial include
     */
    private JFileChooser chooser;

    /** Create a ChooseButton.
     * A default chooser is used.
     * The current working directory is used as base directory.
     * @param textField JTextField to create button for
     * @param fileSelectionMode see {@link JFileChooser}
     */
    public JFileChooserButton(final JTextField textField, final int fileSelectionMode) {
        this(new File(System.getProperty("user.dir")), null, textField, fileSelectionMode);
    }

    /** Create a ChooseButton.
     * A default chooser is used.
     * @param base base directory to use
     * @param textField JTextField to create button for
     * @param fileSelectionMode see {@link JFileChooser}
     */
    public JFileChooserButton(final File base, final JTextField textField, final int fileSelectionMode) {
        this(base, null, textField, fileSelectionMode);
    }

    /** Create a ChooseButton.
     * A default chooser is used.
     * The current working directory is used as base directory.
     * @param chooser JFileChooser to associate with
     * @param textField JTextField to create button for
     * @param fileSelectionMode see {@link JFileChooser}
     */
    public JFileChooserButton(final JFileChooser chooser, final JTextField textField, final int fileSelectionMode) {
        this(new File(System.getProperty("user.dir")), chooser, textField, fileSelectionMode);
    }

    /** Create a ChooserButton.
     * @param base base directory to use
     * @param chooser JFileChooser to associate with
     * @param textField JTextField to create button for
     * @param fileSelectionMode see {@link JFileChooser}
     */
    public JFileChooserButton(final File base, final JFileChooser chooser, final JTextField textField, final int fileSelectionMode) {
        setAction(ACTION_BUILDER.createAction(false, "optionsChooseFile", this));
        this.textField = textField;
        this.fileSelectionMode = fileSelectionMode;
        setMargin(new Insets(0, 0, 0, 0));
        setChooser(chooser);
        setBase(base);
    }

    /** Set the JFileChooser associated with this JFileChooserButton.
     * @param chooser new JFileChooser to associate or <code>null</code> to instruct the button to create its own
     */
    public void setChooser(final JFileChooser chooser) {
        this.chooser = chooser != null ? chooser : new JFileChooser();
    }

    /** Set the base directory to choose files from.
     * This method always tries to convert the base file to canonical form.
     * If that fails, the base file is converted to absolute form instead.
     * @param base directory to choose files from
     */
    public void setBase(final File base) {
        try {
            this.base = base.getCanonicalFile();
        } catch (final IOException ignore) {
            this.base = base.getAbsoluteFile();
        }
    }

    /** Get the base directory to choose files from.
     * @return base directrory to which chosen files are resolved
     */
    public File getBase() {
        return base;
    }

    /** Get the JFileChooser associated with this JFileChooserButton.
     * @return associated JFileChooser
     */
    public JFileChooser getChooser() {
        return chooser;
    }

    /** Action method. */
    @ActionMethod
    public void optionsChooseFile() {
        final String oldFilename = textField.getText();
        final File oldFile = new File(base, oldFilename);
        chooser.setFileSelectionMode(fileSelectionMode);
        chooser.setMultiSelectionEnabled(false);
        if (oldFilename.length() > 0) {
            chooser.setCurrentDirectory(oldFile.getParentFile());
            chooser.setSelectedFile(oldFile);
        } else {
            chooser.setCurrentDirectory(base);
            chooser.setSelectedFile(null);
        }
        final int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            textField.setText(base.toURI().relativize(chooser.getSelectedFile().toURI()).toString());
        }
    }

} // class JFileChooserButton
