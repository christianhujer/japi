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

package test.net.sf.japi.swing.prefs;

import net.sf.japi.swing.action.ActionBuilderFactory;
import net.sf.japi.swing.prefs.PreferencesGroup;
import net.sf.japi.swing.prefs.PreferencesPane;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.swing.*;

/** Test for {@link PreferencesPane}.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
public class PreferencesPaneTest {

    @BeforeClass
    public static void setUpSuite() {
        ActionBuilderFactory.getInstance().putActionBuilder("net.sf.japi.swing.prefs", new MockActionBuilder("net.sf.japi.swing.prefs"));
    }

    /** Tests that when selecting another preferences for changed preferences, "no" will revert the changes.
     * @see <a href="http://sourceforge.net/tracker/index.php?func=detail&amp;aid=1769634&amp;group_id=149894&amp;atid=776737">[ 1769634 ] Selecting "No" for changed preferences should revert</a>
     */
    @Test
    public void testSelectingNoForChangedPrefsReverts() {
        final MockPrefs prefs1 = new MockPrefs();
        final MockPrefs prefs2 = new MockPrefs();
        final PreferencesGroup prefsGroup = new PreferencesGroup("", prefs1, prefs2);
        final PreferencesPane pane = new PreferencesPane(prefsGroup);
        final JList list = pane.getPrefsList();
        final JTextField tf1 = prefs1.getTextfield();
        tf1.setText(tf1.getText() + "_");
        //list.setSelectedIndex(1); // FIXME: Opens dialog window on GUI during test, waits for input!
        // TODO:2009-02-15:christianhujer:Check whether the dialog (apply changes? yes | no | cancel) is shown
        // TODO:2009-02-15:christianhujer:Tell Dialog to select "No"
        // TODO:2009-02-15:christianhujer:Check whether revert() was called and actually reverted the value.
    }

} // class PreferencesPaneTest
