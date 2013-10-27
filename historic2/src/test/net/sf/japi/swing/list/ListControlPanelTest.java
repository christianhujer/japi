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

package test.net.sf.japi.swing.list;

import javax.swing.JList;
import javax.swing.ListSelectionModel;
import net.sf.japi.swing.list.ArrayListModel;
import net.sf.japi.swing.list.ListControlPanel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/** Test for {@link ListControlPanel}.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
public class ListControlPanelTest {

    /** The model for testing. */
    private ArrayListModel<String> model;

    /** The JList for testing. */
    private JList list;

    /** The ListControlPanel to test. */
    private ListControlPanel testling;

    /** Initializes the test data. */
    @Before
    public void setUp() {
        model = new ArrayListModel<String>();
        list = new JList(model);
        testling = new ListControlPanel(list, model);
    }

    /** Removes the test data. */
    @After
    public void tearDown() {
    }

    /** Tests that the actions of a ListControlPanel for an empty model are disabled. */
    @Test
    public void testEmptyListDisabled() {
        Assert.assertFalse("For an empty model, top must be disabled.",    testling.getMoveToTop().isEnabled());
        Assert.assertFalse("For an empty model, up must be disabled.",     testling.getMoveUp().isEnabled());
        Assert.assertFalse("For an empty model, down must be disabled.",   testling.getMoveDown().isEnabled());
        Assert.assertFalse("For an empty model, bottom must be disabled.", testling.getMoveToBottom().isEnabled());
    }

    /** Tests that the actions of a ListControlPanel for a singleton model are disabled. */
    @Test
    public void testSingletonListDisabled() {
        model.add("foo");
        final ListSelectionModel selectionModel = list.getSelectionModel();
        selectionModel.setSelectionInterval(-1, -1);
        Assert.assertFalse("For a 1-element model, top must be disabled.",    testling.getMoveToTop().isEnabled());
        Assert.assertFalse("For a 1-element model, up must be disabled.",     testling.getMoveUp().isEnabled());
        Assert.assertFalse("For a 1-element model, down must be disabled.",   testling.getMoveDown().isEnabled());
        Assert.assertFalse("For a 1-element model, bottom must be disabled.", testling.getMoveToBottom().isEnabled());
        selectionModel.setSelectionInterval(0, 0);
        Assert.assertFalse("For a 1-element model, top must be disabled.",    testling.getMoveToTop().isEnabled());
        Assert.assertFalse("For a 1-element model, up must be disabled.",     testling.getMoveUp().isEnabled());
        Assert.assertFalse("For a 1-element model, down must be disabled.",   testling.getMoveDown().isEnabled());
        Assert.assertFalse("For a 1-element model, bottom must be disabled.", testling.getMoveToBottom().isEnabled());
    }

    /** Tests that the actions of a ListControlPanel for a two element model are disabled / enabled correctly. */
    @Test
    public void testTwoElementListEnabled() {
        model.add("foo");
        model.add("bar");
        final ListSelectionModel selectionModel = list.getSelectionModel();
        selectionModel.setSelectionInterval(-1, -1);
        Assert.assertFalse("For a 2-element model without selection, top must be disabled.",    testling.getMoveToTop().isEnabled());
        Assert.assertFalse("For a 2-element model without selection, up must be disabled.",     testling.getMoveUp().isEnabled());
        Assert.assertFalse("For a 2-element model without selection, down must be disabled.",   testling.getMoveDown().isEnabled());
        Assert.assertFalse("For a 2-element model without selection, bottom must be disabled.", testling.getMoveToBottom().isEnabled());
        selectionModel.setSelectionInterval(0, 0);
        Assert.assertFalse("For a 2-element model with top selection, top must be disabled.",    testling.getMoveToTop().isEnabled());
        Assert.assertFalse("For a 2-element model with top selection, up must be disabled.",     testling.getMoveUp().isEnabled());
        Assert.assertTrue("For a 2-element model with top selection, down must be enabled.",   testling.getMoveDown().isEnabled());
        Assert.assertTrue("For a 2-element model with top selection, bottom must be enabled.", testling.getMoveToBottom().isEnabled());
        selectionModel.setSelectionInterval(1, 1);
        Assert.assertTrue("For a 2-element model with bottom selection, top must be enabled.",    testling.getMoveToTop().isEnabled());
        Assert.assertTrue("For a 2-element model with bottom selection, up must be enabled.",     testling.getMoveUp().isEnabled());
        Assert.assertFalse("For a 2-element model with bottom selection, down must be disabled.",   testling.getMoveDown().isEnabled());
        Assert.assertFalse("For a 2-element model with bottom selection, bottom must be disabled.", testling.getMoveToBottom().isEnabled());
    }

    /** Tests that the actions of a ListControlPanel for a three element model are disabled / enabled correctly. */
    @Test
    public void testThreeElementListEnabled() {
        model.add("foo");
        model.add("bar");
        model.add("buzz");
        final ListSelectionModel selectionModel = list.getSelectionModel();
        selectionModel.setSelectionInterval(-1, -1);
        Assert.assertFalse("For a 3-element model without selection, top must be disabled.",    testling.getMoveToTop().isEnabled());
        Assert.assertFalse("For a 3-element model without selection, up must be disabled.",     testling.getMoveUp().isEnabled());
        Assert.assertFalse("For a 3-element model without selection, down must be disabled.",   testling.getMoveDown().isEnabled());
        Assert.assertFalse("For a 3-element model without selection, bottom must be disabled.", testling.getMoveToBottom().isEnabled());
        selectionModel.setSelectionInterval(0, 0);
        Assert.assertFalse("For a 3-element model with top selection, top must be disabled.",    testling.getMoveToTop().isEnabled());
        Assert.assertFalse("For a 3-element model with top selection, up must be disabled.",     testling.getMoveUp().isEnabled());
        Assert.assertTrue("For a 3-element model with top selection, down must be enabled.",   testling.getMoveDown().isEnabled());
        Assert.assertTrue("For a 3-element model with top selection, bottom must be enabled.", testling.getMoveToBottom().isEnabled());
        selectionModel.setSelectionInterval(1, 1);
        Assert.assertTrue("For a 3-element model with middle selection, top must be enabled.",    testling.getMoveToTop().isEnabled());
        Assert.assertTrue("For a 3-element model with middle selection, up must be enabled.",     testling.getMoveUp().isEnabled());
        Assert.assertTrue("For a 3-element model with middle selection, down must be enabled.",   testling.getMoveDown().isEnabled());
        Assert.assertTrue("For a 3-element model with middle selection, bottom must be enabled.", testling.getMoveToBottom().isEnabled());
        selectionModel.setSelectionInterval(2, 2);
        Assert.assertTrue("For a 3-element model with bottom selection, top must be enabled.",    testling.getMoveToTop().isEnabled());
        Assert.assertTrue("For a 3-element model with bottom selection, up must be enabled.",     testling.getMoveUp().isEnabled());
        Assert.assertFalse("For a 3-element model with bottom selection, down must be disabled.",   testling.getMoveDown().isEnabled());
        Assert.assertFalse("For a 3-element model with bottom selection, bottom must be disabled.", testling.getMoveToBottom().isEnabled());
    }

} // class ListControlPanelTest
