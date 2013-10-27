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

package net.sf.japi.tools.keystrokes;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DateFormat;
import static java.text.DateFormat.getTimeInstance;
import static java.util.Arrays.asList;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.text.JTextComponent;
import net.sf.japi.io.args.ArgParser;
import net.sf.japi.io.args.BasicCommand;
import net.sf.japi.swing.action.ActionBuilder;
import net.sf.japi.swing.action.ActionBuilderFactory;
import net.sf.japi.swing.action.ActionMethod;
import org.jetbrains.annotations.NotNull;

// TODO:2009-02-18:christianhujer:Finish InputMethod support.
/** A small tool that displays the keystrokes while typing.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class KeyStrokes extends BasicCommand implements InputMethodListener, KeyListener {

    /** Action Builder. */
    private static final ActionBuilder ACTION_BUILDER = ActionBuilderFactory.getInstance().getActionBuilder("net.sf.japi.tools.keystrokes");

    /** The field for displaying the last keystroke of the type "pressed". */
    private final JTextField fieldPressed  = new JTextField("          ");

    /** The field for displaying the last keystroke of the type "released". */
    private final JTextField fieldReleased = new JTextField("          ");

    /** The field for displaying the last keystroke of the type "typed". */
    private final JTextField fieldTyped    = new JTextField("          ");

    /** The textarea for displaying the history of keystrokes. */
    @SuppressWarnings({"MagicNumber"})
    private final JTextArea history = new JTextArea(20, 32);

    /** The button for clearing. */
    private final JButton clear = new JButton(ACTION_BUILDER.createAction(false, "clear", this));

    /** The TimeFormat. */
    private final DateFormat format = getTimeInstance(DateFormat.MEDIUM);

    /** Main program.
     * @param args command line arguments
     */
    public static void main(@NotNull final String... args) {
        ArgParser.simpleParseAndRun(new KeyStrokes(), args);
    }

    /** Creates the content pane.
     * @return The newly created content pane.
     */
    @NotNull private Component createContentPane() {
        final Container contentPane = new JPanel(new GridBagLayout());
        final Insets insets = new Insets(2, 2, 2, 2);
        final GridBagConstraints labelGbc = new GridBagConstraints();
        final GridBagConstraints fieldGbc = new GridBagConstraints();
        final GridBagConstraints areaGbc  = new GridBagConstraints();
        for (final GridBagConstraints gbc : asList(labelGbc, fieldGbc, areaGbc)) {
            gbc.insets = insets;
        }
        labelGbc.anchor = GridBagConstraints.EAST;
        fieldGbc.gridwidth = GridBagConstraints.REMAINDER;
        fieldGbc.fill = GridBagConstraints.HORIZONTAL;
        fieldGbc.weightx = 1.0;
        areaGbc.weightx = 1.0;
        areaGbc.weighty = 1.0;
        areaGbc.gridwidth = GridBagConstraints.REMAINDER;
        areaGbc.fill = GridBagConstraints.BOTH;
        for (final JTextComponent c : asList(fieldPressed, fieldReleased, fieldTyped, history)) {
            c.setEditable(false);
        }
        for (final JTextComponent c : asList(fieldPressed, fieldReleased, fieldTyped, history)) {
            c.setEditable(false);
        }
        for (final Component c : asList(fieldPressed, fieldReleased, fieldTyped, history, clear)) {
            c.setFocusable(false);
        }
        contentPane.add(ACTION_BUILDER.createLabel(fieldPressed, "fieldPressed.label"), labelGbc);
        contentPane.add(fieldPressed, fieldGbc);
        contentPane.add(ACTION_BUILDER.createLabel(fieldPressed, "fieldReleased.label"), labelGbc);
        contentPane.add(fieldReleased, fieldGbc);
        contentPane.add(ACTION_BUILDER.createLabel(fieldPressed, "fieldTyped.label"), labelGbc);
        contentPane.add(fieldTyped, fieldGbc);
        contentPane.add(clear, labelGbc);
        contentPane.add(new JScrollPane(history), areaGbc);
        contentPane.addInputMethodListener(this);
        contentPane.enableInputMethods(true);
        return contentPane;
    }

    /** {@inheritDoc} */
    public void keyPressed(@NotNull final KeyEvent e) {
        recordEvent(fieldPressed, e);
    }

    /** {@inheritDoc} */
    public void keyReleased(@NotNull final KeyEvent e) {
        recordEvent(fieldReleased, e);
    }

    /** {@inheritDoc} */
    public void keyTyped(@NotNull final KeyEvent e) {
        recordEvent(fieldTyped, e);
    }

    /** Records an event.
     * @param field Field to record at
     * @param event KeyEvent to record
     */
    private void recordEvent(@NotNull final JTextComponent field, @NotNull final KeyEvent event) {
        final String keyStrokeText = KeyStroke.getKeyStrokeForEvent(event).toString();
        field.setEditable(true);
        field.setText(keyStrokeText);
        field.setEditable(false);
        history.setEditable(true);
        history.append(format.format(new Date(event.getWhen())));
        history.append(": ");
        history.append(keyStrokeText);
        history.append("(" + (int) event.getKeyChar() + " " + event.getKeyCode() + " " + event.getKeyLocation() + ")");
        history.append("\n");
        history.setEditable(false);
    }

    /** Clears the history. */
    @ActionMethod public void clear() {
        history.setText("");
        fieldPressed.setText("");
        fieldReleased.setText("");
        fieldTyped.setText("");
    }

    /** {@inheritDoc} */
    public void inputMethodTextChanged(final InputMethodEvent event) {
        System.err.println(event);
    }

    /** {@inheritDoc} */
    public void caretPositionChanged(final InputMethodEvent event) {
        System.err.println(event);
    }

    /** {@inheritDoc} */
    public int run(@NotNull final List<String> args) throws Exception {
        final JFrame frame = new JFrame(ACTION_BUILDER.getString("frame.title"));
        //noinspection ThisEscapedInObjectConstruction
        frame.addKeyListener(this);
        //noinspection ThisEscapedInObjectConstruction
        frame.addInputMethodListener(this);
        frame.enableInputMethods(true);
        frame.add(createContentPane());
        frame.pack();
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        return 0;
    }

} // class KeyStrokes
