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

import net.sf.japi.swing.action.DefaultActionBuilder;

/**
 * The MockActionBuilder is a replacement of {@link DefaultActionBuilder} for testing purposes.
 * All methods of {@link DefaultActionBuilder} that would require user interaction are replaced by this Mock's robot.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
public class MockActionBuilder extends DefaultActionBuilder {

    /** Create an empty MockActionBuilder.
     * Not recommended.
     * @see DefaultActionBuilder#DefaultActionBuilder()
     */
    public MockActionBuilder() {
    }

    /** Create a MockActionBuilder with the specified key.
     * @param key Key for which to create a MockActionBuilder.
     * @see DefaultActionBuilder#DefaultActionBuilder(String)
     */
    public MockActionBuilder(final String key) {
        super(key);
    }

} // class MockActionBuilder
