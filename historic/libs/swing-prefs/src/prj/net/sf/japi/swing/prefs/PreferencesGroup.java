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

package net.sf.japi.swing.prefs;

import static java.util.Arrays.asList;
import java.util.Iterator;
import java.util.List;
import javax.swing.AbstractListModel;

/** A PreferencesGroup is an ordered set of {@link Prefs}, for use with {@link PreferencesPane}.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class PreferencesGroup extends AbstractListModel implements Iterable<Prefs> {

    /** Serial version. */
    private static final long serialVersionUID = 1L;

    /** The preferences modules.
     * @serial include
     */
    private final List<Prefs> prefs;

    /** The preferences title.
     * @serial include
     */
    private final String title;

    /** Create a Preferences group.
     * @param prefs Preferences modules to initially add
     * @param title Title for Preferences
     */
    public PreferencesGroup(final String title, final Prefs... prefs) {
        this.title = title;
        this.prefs = asList(prefs);
    }

    /** Get the title.
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /** {@inheritDoc} */
    public Iterator<Prefs> iterator() {
        return prefs.iterator();
    }

    /** {@inheritDoc} */
    public int getSize() {
        return prefs.size();
    }

    /** {@inheritDoc} */
    public Prefs getElementAt(final int index) {
        return prefs.get(index);
    }

} // class PreferencesGroup
