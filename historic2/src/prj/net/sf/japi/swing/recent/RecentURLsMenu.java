/* JAPI - (Yet another (hopefully) useful) Java API
 *
 * Copyright (C) 2004-2006 Christian Hujer
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package net.sf.japi.swing.recent;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import net.sf.japi.swing.action.ActionBuilderFactory;
import net.sf.japi.swing.app.CanLoad;

/** Class for a menu showing the recently used URLs.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 * @todo think about serialization of recent and program
 */
public class RecentURLsMenu extends JMenu implements RecentURLsListener {

    /** Serial Version. */
    @SuppressWarnings({"AnalyzingVariableNaming"})
    private static final long serialVersionUID = 1L;

    /** The object providing the RecentURLs information. */
    private transient RecentURLs recent;

    /** The program opening documents. */
    private transient CanLoad program;

    /** Create a RecentURLsMenu.
     * @param recent object with RecentURLs information.
     * @param program program that opens documents
     */
    public RecentURLsMenu(final RecentURLs recent, final CanLoad program) {
        super(ActionBuilderFactory.getInstance().getActionBuilder("net.sf.japi.swing.recent").createAction(true, "recent"));
        this.program = program;
        this.recent = recent;
        recent.addRecentURLsListener(this);
        updateMenu();
    }

    /** Create a RecentURLsMenu.
     * The list of recent URLs is stored in the preferences for the class of <var>c</var>.
     * @param c Class to obtain RecentURLs information for
     * @param program program that opens documents
     * @see PrefsRecentURLs
     */
    public RecentURLsMenu(final Class<?> c, final CanLoad program) {
        this(PrefsRecentURLs.getInstance(c), program);
    }

    /** Create a RecentURLsMenu.
     * The list of recent URLs is stored in the preferences for the class of <var>program</var>.
     * @param program program that opens documents
     * @see PrefsRecentURLs
     */
    public RecentURLsMenu(final CanLoad program) {
        this(PrefsRecentURLs.getInstance(program.getClass()), program);
    }

    /** Update the menu. */
    private void updateMenu() {
        removeAll();
        for (final String url : recent.getRecentlyURLs()) {
            add(new JMenuItem(new URLAction(url)));
        }
    }

    /** {@inheritDoc} */
    public void recentURLsChanged(final RecentURLsEvent e) {
        assert e.getSource() == recent;
        updateMenu();
    }

    /** Class for URL Actions.
     */
    private class URLAction extends AbstractAction {

        /** Serial Version. */
        @SuppressWarnings({"AnalyzingVariableNaming"})
        private static final long serialVersionUID = 1L;

        /** The URL to be opened. */
        private final String url;

        /** Create a URLAction.
         * @param url URL to use for this action.
         */
        URLAction(final String url) {
            putValue(NAME, url);
            this.url = url;
        }

        /** {@inheritDoc} */
        public void actionPerformed(final ActionEvent e) {
            try {
                program.load(url);
            } catch (final Exception e1) {
                e1.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.
                // TODO:2009-02-15:christianhujer:Implement proper error handling.
            }
        }

    } // class URLAction

} // class RecentURLsMenu
