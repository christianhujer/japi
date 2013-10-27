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

package net.sf.japi.string2bytes;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.prefs.Preferences;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import net.sf.japi.swing.action.ActionBuilder;
import net.sf.japi.swing.action.ActionMethod;
import net.sf.japi.swing.action.ActionBuilderFactory;
import net.sf.japi.swing.action.ToggleAction;
import net.sf.japi.swing.about.AboutDialog;
import org.jetbrains.annotations.NotNull;

/**
 * String2Bytes is a small tool that converts Strings into Bytes.
 * You can invoke it from the command line or use a graphical user interface.
 * @todo intermediate field which shows url or html encoding
 * @todo optional url or html encoding (replace ä with &auml; etc.)
 * @todo support removal of leading and trailing "
 * @todo support escape sequences
 * @todo choice between converting all lines at once or each line separately
 * @todo choice to add line endings and choose between \r, \n and \r\n
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class String2Bytes implements ItemListener {

    /** Action Builder. */
    @NotNull private static final ActionBuilder ACTION_BUILDER = ActionBuilderFactory.getInstance().getActionBuilder("net.sf.japi.string2bytes");

    /** About Dialog. */
    @NotNull private final AboutDialog aboutDialog = new AboutDialog("net.sf.japi.string2bytes");

    /** Preferences. */
    @NotNull private static final Preferences PREFS = Preferences.userNodeForPackage(String2Bytes.class);

    /** Textfield for the input String. */
    @NotNull private final JTextArea input  = new JTextArea();

    /** Textfield for the output String. */
    @NotNull private final JTextArea output = new JTextArea();

    /** ComboBox for selecting the encoding. */
    @NotNull private final JComboBox encoding = new JComboBox(Charset.availableCharsets().keySet().toArray());

    /** Whether the Clipboard is used for input. */
    private boolean usingClipboardForInput;

    /** Whether the Clipboard is used for output. */
    private boolean usingClipboardForOutput;

    /** The application frame. */
    @NotNull private final JFrame frame;

    String2Bytes() {
        ACTION_BUILDER.createActions(true, this, "convert", "encodingDefault", "encodingSystem", "clearInput", "clearOutput");
        encoding.addItemListener(this);
        frame = new JFrame(ACTION_BUILDER.getString("window.title"));
        frame.setJMenuBar(ACTION_BUILDER.createMenuBar(true, "menuBar", this));
        output.setEditable(false);
        final Container contentPane = frame.getContentPane();
        contentPane.setLayout(new GridBagLayout());
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        contentPane.add(createSettingsPanel(), gbc);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        contentPane.add(createInputPanel(), gbc);
        contentPane.add(createOutputPanel(), gbc);
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        contentPane.add(createCommandPanel());
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    /**
     * Displays the about dialog.
     */
    @ActionMethod public void about() {
        aboutDialog.showAboutDialog(frame);
    }

    /**
     * Quits the application.
     */
    @ActionMethod public void quit() {
        frame.dispose();
    }

    /**
     * Performs a Conversion.
     * @throws UnsupportedEncodingException in case the selected encoding is not available (shouldn't happen).
     */
    @ActionMethod public void convert() throws UnsupportedEncodingException {
        if (usingClipboardForInput) {
            input.selectAll();
            input.paste();
        }
        output.setText(convert(getInputText()));
        if (usingClipboardForOutput) {
            output.selectAll();
            output.copy();
        }
    }

    /**
     * Performs a Conversion.
     * @param from String to convert
     * @return Converted String
     * @throws UnsupportedEncodingException in case the selected encoding is not available (shouldn't happen).
     */
    @NotNull public String convert(@NotNull final String from) throws UnsupportedEncodingException {
        return new JavaBytesCodec().code(from, encoding.getSelectedItem().toString());
    }

    /**
     * Clears the input text.
     */
    @ActionMethod
    public void clearInput() {
        input.setText("");
    }

    /**
     * Clears the output text.
     */
    @ActionMethod public void clearOutput() {
        output.setText("");
    }

    /**
     * Returns the input String after filtering it through optional filters.
     * @return input String after filtering it through optional filters.
     */
    public String getInputText() {
        return input.getText();
    }

    /**
     * Selects the underlying operating system's default encoding.
     */
    @ActionMethod public void encodingSystem() {
        encoding.setSelectedItem(Charset.defaultCharset().name());
    }

    /**
     * Selects the default encoding, which is specified by <code>encoding.default</code>.
     */
    @ActionMethod public void encodingDefault() {
        encoding.setSelectedItem(Charset.forName(ACTION_BUILDER.getString("encoding.default")).name());
    }

    /**
     * Creates the panel which holds the settings.
     * @return Newly created panel which holds the settings.
     */
    @NotNull private JPanel createSettingsPanel() {
        final JPanel settingsPanel = new JPanel();
        settingsPanel.setBorder(BorderFactory.createTitledBorder(ACTION_BUILDER.getString("border.settings.title")));
        settingsPanel.add(createEncodingPanel());
        return settingsPanel;
    }

    /**
     * Creates the panel which allows the user to select the encoding.
     * @return Newly created panel for selecting the encoding.
     */
    @NotNull private JPanel createEncodingPanel() {
        encoding.setSelectedItem(PREFS.get("encoding", ACTION_BUILDER.getString("encoding.default")));
        encoding.setEditable(true);
        final JPanel encodingPanel = new JPanel();
        encodingPanel.setBorder(BorderFactory.createTitledBorder(ACTION_BUILDER.getString("border.encoding.title")));
        encodingPanel.add(encoding);
        encodingPanel.add(new JButton(ACTION_BUILDER.getAction("encodingDefault")));
        encodingPanel.add(new JButton(ACTION_BUILDER.getAction("encodingSystem")));
        return encodingPanel;
    }

    /**
     * Creates the input panel.
     * @return Newly created input panel.
     */
    @NotNull private JPanel createInputPanel() {
        final JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder(ACTION_BUILDER.getString("border.input.title")));
        final GridBagConstraints gbc = new GridBagConstraints();
        inputPanel.add(((ToggleAction) ACTION_BUILDER.createToggle(true, "usingClipboardForInput", this)).createCheckBox(), gbc);
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridy = 1;
        gbc.weighty = 1.0;
        inputPanel.add(new JButton(ACTION_BUILDER.getAction("clearInput")), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        inputPanel.add(new JScrollPane(input), gbc);
        return inputPanel;
    }

    /**
     * Creates the output panel.
     * @return Newly created output panel.
     */
    @NotNull private JPanel createOutputPanel() {
        final JPanel outputPanel = new JPanel(new GridBagLayout());
        outputPanel.setBorder(BorderFactory.createTitledBorder(ACTION_BUILDER.getString("border.output.title")));
        final GridBagConstraints gbc = new GridBagConstraints();
        outputPanel.add(((ToggleAction) ACTION_BUILDER.createToggle(true, "usingClipboardForOutput", this)).createCheckBox(), gbc);
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridy = 1;
        gbc.weighty = 1.0;
        outputPanel.add(new JButton(ACTION_BUILDER.getAction("clearOutput")), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        outputPanel.add(new JScrollPane(output), gbc);
        return outputPanel;
    }

    /**
     * Creates the command panel which holds the button to perform the conversion.
     * @return Newly created command panel.
     */
    @NotNull private JPanel createCommandPanel() {
        final JPanel commandPanel = new JPanel();
        commandPanel.setBorder(BorderFactory.createTitledBorder(ACTION_BUILDER.getString("border.commands.title")));
        commandPanel.add(new JButton(ACTION_BUILDER.getAction("convert")));
        return commandPanel;
    }

    /** {@inheritDoc} */
    public void itemStateChanged(@NotNull final ItemEvent itemEvent) {
        if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
            PREFS.put("encoding", encoding.getSelectedItem().toString());
        }
    }

    /**
     * Main program.
     * @param args Command line arguments (ignored)
     */
    public static void main(@NotNull final String... args) {
        new String2Bytes();
    }

    /**
     * Get whether the clipboard should be used as the source for input.
     * @return <code>true</code> if the clipboard should be used as the source for input, otherwise <code>false</code>.
     */
    public boolean isUsingClipboardForInput() {
        return usingClipboardForInput;
    }

    /**
     * Sets whether the clipboard should be used as the source for input.
     * @param usingClipboardForInput <code>true</code> if the clipboard should be used as the source for input, otherwise <code>false</code>.
     */
    public void setUsingClipboardForInput(final boolean usingClipboardForInput) {
        this.usingClipboardForInput = usingClipboardForInput;
        PREFS.putBoolean("usingClipboardForInput", usingClipboardForInput);
    }

    /**
     * Get whether the clipboard should be used as the destination for output.
     * @return <code>true</code> if the clipboard should be used as the destination for output, otherwise <code>false</code>.
     */
    public boolean isUsingClipboardForOutput() {
        return usingClipboardForOutput;
    }

    /**
     * Sets whether the clipboard should be used as the destination for output.
     * @param usingClipboardForOutput <code>true</code> if the clipboard should be used as the destination for output, otherwise <code>false</code>.
     */
    public void setUsingClipboardForOutput(final boolean usingClipboardForOutput) {
        this.usingClipboardForOutput = usingClipboardForOutput;
        PREFS.putBoolean("usingClipboardForOutput", usingClipboardForOutput);
    }

} // class ConversionAction
