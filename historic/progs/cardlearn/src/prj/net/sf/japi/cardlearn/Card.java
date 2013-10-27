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

package net.sf.japi.cardlearn;

import java.io.Serializable;

/**
 * Class Description.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class Card implements Serializable {

    /** Serial version. */
    private static final long serialVersionUID = 1L;

    /** Contents of this card.
     * @serial include
     */
    private final String[] fieldContents = new String[6];

    /** Sets a field of this card.
     * @param fieldIndex Index of the field to set.
     * @param fieldContent New value for the field.
     */
    public void setField(final int fieldIndex, final String fieldContent) {
        fieldContents[fieldIndex] = fieldContent;
    }

    /** Returns a field of this card.
     * @param fieldIndex Index of the field to return.
     * @return The value of that field.
     */
    public String getField(final int fieldIndex) {
        return fieldContents[fieldIndex];
    }
}
