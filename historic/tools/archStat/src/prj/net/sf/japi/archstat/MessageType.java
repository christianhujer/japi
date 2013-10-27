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

package net.sf.japi.archstat;

import org.jetbrains.annotations.NotNull;

/** A MessageType.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public enum MessageType {

    /** An error. */
    ERROR("error"),

    /** A warning. */
    WARNING("warning"),

    /** An informational message. */
    INFO("info"),

    /** Statistical information. */
    STAT("stat");

    /** The name of this message type. */
    @NotNull private final String name;

    /** Create a MessageType.
     * @param name Name of this message type.
     */
    MessageType(@NotNull final String name) {
        this.name = name;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return name;
    }
}
