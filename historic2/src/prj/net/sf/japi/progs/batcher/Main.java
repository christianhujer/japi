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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import net.sf.japi.swing.action.ActionBuilder;
import net.sf.japi.swing.action.ActionBuilderFactory;
import net.sf.japi.swing.action.ActionMethod;
import org.jetbrains.annotations.NotNull;

/** Main class of Batcher.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class Main {

    /** Action Builder. */
    @NotNull
    private static final ActionBuilder ACTION_BUILDER = ActionBuilderFactory.getInstance().getActionBuilder(Main.class);

    /** Main program.
     * @param args Command line arguments (ignored).
     */
    public static void main(final String... args) {
        new Main(args);
    }

    /** The program frame. */
    private final JFrame frame;

    /** Creates Main.
     * @param args Command line arguments.
     */
    public Main(final String... args) {
        frame = new JFrame(ACTION_BUILDER.getString("window.title"));
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setJMenuBar(ACTION_BUILDER.createMenuBar(true, "Main", this));
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(final WindowEvent e) {
                quit();
            }
        });
    }

//    /** Creates Main.
//     * @param args Command line arguments
//     */
//    public Main(final String... args) {
//        frame = new JFrame(ACTION_BUILDER.getString("window.title"));
//        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
//        frame.setJMenuBar(ACTION_BUILDER.createMenuBar(true, "Main", this));
//        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
//        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
//        frame.addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosing(final WindowEvent e) {
//                quit();
//            }
//        });
//        final JDesktopPane desktop = new JDesktopPane();
//        frame.add(desktop, BorderLayout.CENTER);
//        for (int i = 0; i < 5; i++) {
//            final JInternalFrame iFrame = new JInternalFrame("app " + i, true, true, true, false);
//            init(iFrame);
//            desktop.add(iFrame);
//            try {
//                iFrame.setMaximum(true);
//            } catch (final PropertyVetoException e) {
//                System.err.println(e);
//            }
//        }
//        frame.setVisible(true);
//    }
//
//    public void init(final JInternalFrame frame) {
//        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
//        //final Job job = new Job("foo");
//        //job.getCommand().addAll(Arrays.asList(args));
//        //frame.getContentPane().add(new JobView(job));
//
//        final JTabbedPane tabs = new JTabbedPane(JTabbedPane.BOTTOM, JTabbedPane.SCROLL_TAB_LAYOUT);
//        for (int i = 0; i < 40; i++) {
//            final JTextArea textArea = new JTextArea(25, 80);
//            textArea.setForeground(Color.white);
//            textArea.setBackground(Color.black);
//            textArea.setEditable(false);
//            textArea.setFont(new Font(Font.MONOSPACED, textArea.getFont().getStyle(), textArea.getFont().getSize()));
//            final Component c = new JScrollPane(textArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//            tabs.add("Command " + i, c);
//        }
//        frame.getContentPane().add(tabs);
//
//        final JPanel east = new JPanel();
//        east.add(new JButton("foo"));
//        frame.getContentPane().add(east, BorderLayout.WEST);
//
//        frame.pack();
//        frame.setVisible(true);
//    }
//
    /** Quits the program. */
    @ActionMethod
    public void quit() {
        if (isQuitRequested()) {
            frame.setVisible(false);
            frame.dispose();
        }
    }

    /** Asks whether quitting is requested.
     * @return <code>true</code> if quitting is requested, otherwise <code>false</code>.
     */
    public boolean isQuitRequested() {
        return ACTION_BUILDER.showQuestionDialog(frame, "reallyQuit");
    }

} // class Main
