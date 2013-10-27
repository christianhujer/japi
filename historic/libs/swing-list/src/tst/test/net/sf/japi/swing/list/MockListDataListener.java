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

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import net.sf.japi.swing.list.ArrayListModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

/** A ListDataListener useful for testing that the event system in {@link ArrayListModel} works.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
public class MockListDataListener implements ListDataListener {

    /** Last event. */
    @Nullable
    private ListDataEvent lastEvent;

    /** {@inheritDoc} */
    public void intervalAdded(@NotNull final ListDataEvent e) {
        lastEvent = e;
        assertEquals("intervalAdded() must be invoked with a ListDataEvent of type INTERVAL_ADDED.", ListDataEvent.INTERVAL_ADDED, e.getType());
    }

    /** {@inheritDoc} */
    public void intervalRemoved(@NotNull final ListDataEvent e) {
        lastEvent = e;
        assertEquals("intervalRemoved() must be invoked with a ListDataEvent of type INTERVAL_REMOVED.", ListDataEvent.INTERVAL_REMOVED, e.getType());
    }

    /** {@inheritDoc} */
    public void contentsChanged(@NotNull final ListDataEvent e) {
        lastEvent = e;
        assertEquals("contentsChanged() must be invoked with a ListDataEvent of type CONTENTS_CHANGED.", ListDataEvent.CONTENTS_CHANGED, e.getType());
    }

    /** Asserts that there was an event and it has the expected properties.
     * @param type Type of the event to assert.
     * @param index0 Lower bound of the event's range.
     * @param index1 Upper bound of the event's range.
     */
    public void assertEvent(final int type, final int index0, final int index1) {
        @Nullable final ListDataEvent lastEvent = this.lastEvent;
        assertNotNull("Expected to have an event.", lastEvent);
        assert lastEvent != null;
        assertEquals("Expecting last event to be of type " + getTypeName(type) + ".", type, lastEvent.getType());
        assertEquals("Expecting last event to have correct index0.", index0, lastEvent.getIndex0());
        assertEquals("Expecting last event to have correct index1.", index1, lastEvent.getIndex1());
        this.lastEvent = null;
    }

    /** Asserts that there was no event. */
    public void assertNoEvent() {
        assertNull("Expected to not have an event.", lastEvent);
    }

    /** Returns the name of an event type.
     * @param type Type to return name for.
     * @return Name for <var>type</var>
     */
    private static String getTypeName(final int type) {
        switch (type) {
            case 0: return "CONTENTS_CHANGED";
            case 1: return "INTERVAL_ADDED";
            case 2: return "INTERVAL_REMOVED";
            default:
                fail("Internal test error: unexpected event type for check " + type);
                assert false;
                return ""; // Never reaches this statement, but the compiler expects a return.
        }
    }

} // class MockListDataListener
