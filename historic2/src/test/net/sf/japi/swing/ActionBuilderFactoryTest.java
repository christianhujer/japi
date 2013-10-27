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

import net.sf.japi.swing.action.ActionBuilder;
import net.sf.japi.swing.action.ActionBuilderFactory;
import net.sf.japi.swing.action.DefaultActionBuilder;
import org.junit.Assert;
import org.junit.Test;

/** Test for {@link ActionBuilderFactory}.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class ActionBuilderFactoryTest {

    /** Tests that {@link ActionBuilderFactory#getInstance()} returns the same instance upon two invocations. */
    @Test
    public void testGetInstanceIdentity() {
        final ActionBuilderFactory instance1 = ActionBuilderFactory.getInstance();
        final ActionBuilderFactory instance2 = ActionBuilderFactory.getInstance();
        Assert.assertSame("Two invocations of ActionBuilderFactory.getInstance() must return the same instance.", instance1, instance2);
    }

    /** Tests that {@link ActionBuilderFactory#getActionBuilder(String)} and {@link ActionBuilderFactory#putActionBuilder(String, ActionBuilder)} work. */
    @Test
    public void testGetAndPut() {
        final ActionBuilderFactory testling = new ActionBuilderFactory() {};
        final ActionBuilder object = new DefaultActionBuilder();
        testling.putActionBuilder("foo", object);
        Assert.assertSame("Stored ActionBuilder must be retrievable under its basename.", object, testling.getActionBuilder("foo"));
    }

} // class ActionBuilderFactoryTest
