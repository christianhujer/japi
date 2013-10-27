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

package net.sf.japi.tools.fontbrowser;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.sf.japi.swing.action.ActionBuilder;
import net.sf.japi.swing.action.ActionBuilderFactory;
import org.jetbrains.annotations.NotNull;

/** A Font Browser.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public final class FontBrowser {

    /** Action Builder. */
    @NotNull private static final ActionBuilder ACTION_BUILDER = ActionBuilderFactory.getInstance().getActionBuilder("net.sf.japi.tools.fontbrowser");

    /** Number of rows for the sample text. */
    private static final int SAMPLETEXT_ROWS = 16;

    /** Number of columns for the sample text. */
    private static final int SAMPLETEXT_COLUMNS = 64;

    /** Default font size for font samples. */
    private static final int DEFAULT_FONT_SIZE = 14;

    /** No instantiation needed. */
    private FontBrowser() {
    }

    /** Main program.
     * @param args command line arguments
     */
    public static void main(@NotNull final String... args) {
        final JFrame frame = new JFrame(ACTION_BUILDER.getString("frame.title"));
        final Container tabs = new JTabbedPane();
        frame.add(tabs);
        tabs.add(ACTION_BUILDER.getString("names.title"), createNamesTab());
        tabs.add(ACTION_BUILDER.getString("fonts.title"), createFontsTab());
        final Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        size.width >>= 1;
        size.height >>= 1;
        frame.setSize(size);
        frame.setLocation(new Point(size.width >> 1, size.height >> 1));
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    /** Create the tab component for font names.
     * @return The tab component for font names.
     */
    @NotNull private static Component createNamesTab() {
        final JList fontNameList = new JList(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
        final JTextArea fontExample = new JTextArea(SAMPLETEXT_ROWS, SAMPLETEXT_COLUMNS);
        fontNameList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(final ListSelectionEvent e) {
                final String value = (String) fontNameList.getSelectedValue();
                fontExample.setFont(new Font(value, Font.PLAIN, DEFAULT_FONT_SIZE));
                System.err.println(value);
            }
        });
        fontExample.setText(ACTION_BUILDER.getString("font.example.text"));
        return new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, new JScrollPane(fontNameList), new JScrollPane(fontExample));
    }

    /** Create the tab component for fonts.
     * @return The tab component for fonts.
     */
    @NotNull private static Component createFontsTab() {
        final JList fontList = new JList(GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts());
        return new JScrollPane(fontList);
    }

} // class FontBrowser
