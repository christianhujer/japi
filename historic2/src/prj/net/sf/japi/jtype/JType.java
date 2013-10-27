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

package net.sf.japi.jtype;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import net.sf.japi.swing.about.AboutDialog;
import net.sf.japi.swing.action.ActionBuilder;
import net.sf.japi.swing.action.ActionBuilderFactory;
import net.sf.japi.swing.action.ActionMethod;
import org.jetbrains.annotations.NotNull;

/** JType is a program that measures the speed of typing.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
// TODO:2009-02-23:christianhujer:More detailled statistics.
// TODO:2009-02-23:christianhujer:Excercise texts.
// TODO:2009-02-23:christianhujer:Statistics about which characters were mistyped most frequently.
// TODO:2009-02-23:christianhujer:Show keyboard layout.
// TODO:2009-02-23:christianhujer:Make time at which a pause is detected configurable. Maybe use that time for the first char.
// TODO:2009-02-23:christianhujer:Split statistics into recent (e.g. last line, 100 chars), session, day, week and all time.
// TODO:2009-02-23:christianhujer:Per-char-statistics.
// TODO:2009-02-23:christianhujer:Statistics about which character combinations.
// TODO:2009-02-23:christianhujer:Use aspell to generate random word lists automatically.
public class JType extends JApplet implements WindowListener {

    /** The ActionBuilder with which the Actions will be built. */
    private static final ActionBuilder ACTION_BUILDER = ActionBuilderFactory.getInstance().getActionBuilder(PerformancePane.class);

    /** The program frame. */
    private static JFrame frame;

    /** The about dialog.
     * This field is lazy initialized in {@link #about()}.
     * @serial exclude
     */
    @SuppressWarnings({"InstanceVariableMayNotBeInitialized"})
    private transient AboutDialog aboutDialog;

    /** Main program.
     * @param args Commandline arguments (currently ignered).
     */
    public static void main(@NotNull final String... args) {
        frame = new JFrame("JType");
        final JType jtype = new JType();
        frame.add(jtype);
        frame.addWindowListener(jtype);
        frame.pack();
        frame.setVisible(true);
    }

    /** Creates a new JType. */
    public JType() {
        setLayout(new BorderLayout());
        final JTextArea textPane = new JTextArea(25, 81);
        textPane.setLineWrap(true);
        textPane.setWrapStyleWord(true);
        final Font oldFont = textPane.getFont();
        textPane.setFont(new Font(Font.MONOSPACED, oldFont.getStyle(), (int) (oldFont.getSize() * 1.5)));
        add(new JScrollPane(textPane));
        final PerformancePane pane = new PerformancePane();
        add(pane, BorderLayout.SOUTH);
        textPane.addKeyListener(pane);
        setJMenuBar(ACTION_BUILDER.createMenuBar(true, "main", this));
    }

    /** {@inheritDoc} */
    public void windowOpened(@NotNull final WindowEvent e) {
        // nothing to do
    }

    /** {@inheritDoc} */
    public void windowClosing(@NotNull final WindowEvent e) {
        frame.dispose();
    }

    /** {@inheritDoc} */
    public void windowClosed(@NotNull final WindowEvent e) {
        // nothing to do
    }

    /** {@inheritDoc} */
    public void windowIconified(@NotNull final WindowEvent e) {
        // nothing to do
    }

    /** {@inheritDoc} */
    public void windowDeiconified(@NotNull final WindowEvent e) {
        // nothing to do
    }

    /** {@inheritDoc} */
    public void windowActivated(@NotNull final WindowEvent e) {
        // nothing to do
    }

    /** {@inheritDoc} */
    public void windowDeactivated(@NotNull final WindowEvent e) {
        // nothing to do
    }

    /** Quits the application. */
    @ActionMethod
    public void quit() {
        frame.setVisible(false);
        frame.dispose();
    }

    /** Displays an about dialog. */
    @ActionMethod
    public void about() {
        if (aboutDialog == null) {
            aboutDialog = new AboutDialog(ACTION_BUILDER);
        }
        aboutDialog.showAboutDialog(frame);
        // TODO:2009-02-23:christianhujer:Implementation.
    }
}
