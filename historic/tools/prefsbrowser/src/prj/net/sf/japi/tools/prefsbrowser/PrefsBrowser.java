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

package net.sf.japi.tools.prefsbrowser;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import net.sf.japi.swing.action.ActionBuilder;
import net.sf.japi.swing.action.ActionBuilderFactory;
import net.sf.japi.swing.treetable.JTreeTable;
import org.jetbrains.annotations.NotNull;

/** Main class of PrefsBrowser.
 * @author <a href="mailto:chris@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class PrefsBrowser {

    /** Action Builder. */
    @NotNull private static final ActionBuilder ACTION_BUILDER = ActionBuilderFactory.getInstance().getActionBuilder("net.sf.japi.tools.prefsbrowser");

    /** Main program.
     * @param args command line arguments
     */
    public static void main(@NotNull final String... args) {
        // TODO:2009-02-19:christianhujer:Use japi-lib-argparser.
        //noinspection ResultOfObjectAllocationIgnored,InstantiationOfUtilityClass
        new PrefsBrowser();
    }

    /** Create a PrefsBrowser. */
    public PrefsBrowser() {
        final JFrame frame = new JFrame(ACTION_BUILDER.getString("frame.title"));
        frame.add(new JScrollPane(new JTreeTable<PrefsRootNode, PrefsTreeNode>(new PrefsTreeTableModel())));
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    /** Dummy to make this not look like a utility class. */
    // XXX:2009-02-19:christianhujer:Replace this dummy with something better.
    public void dummy() {
    }

} // class PrefsBrowser
