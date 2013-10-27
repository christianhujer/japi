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

package test.net.sf.japi.swing;

import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import net.sf.japi.swing.action.DefaultActionBuilder;
import net.sf.japi.swing.action.DummyAction;
import org.junit.Assert;
import org.junit.Test;

/** Test for {@link DefaultActionBuilder}.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class DefaultActionBuilderTest {

    /**
     * Tests that {@link DefaultActionBuilder#find(JMenuBar, Action)} works.
     * @throws Exception (unexpected)
     */
    //Multiple exceptions possible not yet supported by JUnit
    //@Test(expected = {NullPointerException.class, IllegalArgumentException.class})
    public void testFindNullBar() throws Exception {
        try {
            //noinspection ConstantConditions
            DefaultActionBuilder.find((JMenuBar) null, createSimple("item"));
            Assert.fail("Expected NullPointerException or IllegalArgumentException.");
        } catch (final NullPointerException ignore) {
            // expected without @NotNull support
        } catch (final IllegalArgumentException ignore) {
            // expected with @NotNull support
        }
    }

    /**
     * Tests that {@link DefaultActionBuilder#find(JMenuBar, Action)} works.
     * @throws Exception (unexpected)
     */
    //Multiple exceptions possible not yet supported by JUnit
    //@Test(expected = {NullPointerException.class, IllegalArgumentException.class})
    public void testFindNullActionInMenuBar() throws Exception {
        try {
            //noinspection ConstantConditions
            DefaultActionBuilder.find(new JMenuBar(), (Action) null);
            Assert.fail("Expected NullPointerException or IllegalArgumentException.");
        } catch (final NullPointerException ignore) {
            // expected without @NotNull support
        } catch (final IllegalArgumentException ignore) {
            // expected with @NotNull support
        }
    }

    /**
     * Tests that {@link DefaultActionBuilder#find(JMenuBar, Action)} works.
     * @throws Exception (unexpected)
     */
    //Multiple exceptions possible not yet supported by JUnit
    //@Test(expected = {NullPointerException.class, IllegalArgumentException.class})
    public void testFindNullMenu() throws Exception {
        try {
            //noinspection ConstantConditions
            DefaultActionBuilder.find((JMenu) null, createSimple("item"));
            Assert.fail("Expected NullPointerException or IllegalArgumentException.");
        } catch (final NullPointerException ignore) {
            // expected without @NotNull support
        } catch (final IllegalArgumentException ignore) {
            // expected with @NotNull support
        }
    }

    /**
     * Tests that {@link DefaultActionBuilder#find(JMenuBar, Action)} works.
     * @throws Exception (expected)
     */
    //Multiple exceptions possible not yet supported by JUnit
    //@Test(expected = {NullPointerException.class, IllegalArgumentException.class})
    public void testFindNullActionInMenu() throws Exception {
        try {
            //noinspection ConstantConditions
            DefaultActionBuilder.find(new JMenu(), null);
            Assert.fail("Expected NullPointerException or IllegalArgumentException.");
        } catch (final NullPointerException ignore) {
            // expected without @NotNull support
        } catch (final IllegalArgumentException ignore) {
            // expected with @NotNull support
        }
    }

    /**
     * Tests that {@link DefaultActionBuilder#find(JMenuBar, Action)} works.
     * @throws Exception (unexpected)
     */
    @Test
    public void testFind() throws Exception {
        final JMenuBar menuBar = new JMenuBar();
        final JMenu menu = new JMenu(createSimple("menu"));
        final Action action1 = createSimple("item1");
        final Action action2 = createSimple("item2");
        final JMenuItem item1 = new JMenu(action1);
        final JMenuItem item2 = new JMenu(action2);
        menuBar.add(menu);
        menu.add(item1);
        menu.add(item2);
        Assert.assertSame(item1, DefaultActionBuilder.find(menuBar, action1));
        Assert.assertSame(item2, DefaultActionBuilder.find(menuBar, action2));
    }

    /**
     * Creates a simple action that performs nothing.
     * @param key Key for the action
     * @return A simple action that performs nothing.
     */
    private Action createSimple(final String key) {
        final Action action = new DummyAction();
        action.putValue(Action.ACTION_COMMAND_KEY, key);
        return action;
    }

} // class DefaultActionBuilderTest
