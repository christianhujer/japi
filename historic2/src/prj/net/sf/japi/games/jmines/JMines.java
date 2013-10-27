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

package net.sf.japi.games.jmines;

import javax.swing.JApplet;
import javax.swing.JComponent;
import net.sf.japi.swing.action.ActionMethod;

/** A MineSweeper implementation in Java.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class JMines extends JApplet {

    /** Creates JMines. */
    public JMines() {
    }

    /** Starts a new game. */
    @ActionMethod
    public void newGame() {

    }

}

/** State of a single JMines field element.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
enum FieldState {

    /** The field is closed. */
    CLOSED,

    /** The field is closed and flagged by the user as suspected of having a mine. */
    FLAGGED,

    /** The field is closed and flagged by the user as being of unknown state. */
    UNKNOWN,

    /** The field is open. */
    OPEN,

    /** The field is open and has a mine. */
    MINE
}

/** A field.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
interface Field {

    /** Returns the width of this mine field.
     * @return The width of this mine field.
     */
    int getWidth();

    /** Returns the height of this mine field.
     * @return The height of this mine field.
     */
    int getHeight();

    /** Returns the number of mines on the whole field.
     * @return The number of mines on the whole field.
     */
    int getMineCount();
}

/** View for a single field.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
class FieldView extends JComponent {

    /** The field to view. */
    private Field field;

    /** Sets the field to view.
     * @param field Field to view.
     */
    public void setField(final Field field) {
        this.field = field;
    }
}
