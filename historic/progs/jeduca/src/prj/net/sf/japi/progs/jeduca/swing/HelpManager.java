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

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.WeakHashMap;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JScrollPane;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

/** Class for displaying help to the user.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 * @todo initial page
 */
public class HelpManager extends WindowAdapter {

    /** The HelpManager instances. */
    private static final Map<String, HelpManager> MANAGERS = new WeakHashMap<String, HelpManager>();

    /** Get a HelpManager.
     * If no HelpManager already exists, a new one will be created and remembered.
     * @param key Key to get HelpManager for.
     * @return HelpManager for <var>key</var>.
     */
    public static HelpManager getHelp(final String key) {
        HelpManager help = MANAGERS.get(key);
        if (help == null) {
            help = new HelpManager();
            MANAGERS.put(key, help);
        }
        return help;
    }

    /** The dialog. */
    private final JFrame dialog;

    /** The HTML Viewer. */
    private final JEditorPane helpView;

    /** Create a HelpManager. */
    public HelpManager() {
        dialog = new JFrame();
        dialog.addWindowListener(this);
        dialog.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        // TODO:2009-02-22:christianhujer:Dialog title.
        helpView = new JEditorPane("text/html", "");
        helpView.setEditable(false);
        dialog.add(new JScrollPane(helpView));
    }

    /** Show a URL.
     * @param url URL to show by this HelpManager.
     */
    public void show(final URL url) {
        try {
            helpView.setPage(url);
            dialog.setVisible(true);
        } catch (IOException e) {
            showMessageDialog(dialog, e, "Dummy Error", ERROR_MESSAGE);
        }
    }

    /** Hide. */
    public void hide() {
        dialog.setVisible(false);
    }

    /** {@inheritDoc} */
    @Override
    public void windowClosing(final WindowEvent e) {
        hide();
    }

} // class HelpManager
