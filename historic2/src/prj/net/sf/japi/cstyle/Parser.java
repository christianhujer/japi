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

package net.sf.japi.cstyle;

import org.jetbrains.annotations.NotNull;

/** A Parser processes a file character by character.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public interface Parser {

    /** Initializes this Parser. */
    void init();

    /** Resets this parser. */
    void reset();

    /** Tells this parser that the stream it was parsing came to its regular end. */
    void end();

    /** Registers a ParseListener with this Parser.
     * @param listener ParseListener to register.
     */
    void addParseListener(@NotNull ParseListener listener);

    /** Removes a ParseListener from this Parser.
     * @param listener ParseListener to unregister.
     */
    void removeParseListener(@NotNull ParseListener listener);

} // interface Parser
