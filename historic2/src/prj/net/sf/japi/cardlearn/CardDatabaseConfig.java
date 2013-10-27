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

/** The Configuration of a CardDatabase.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class CardDatabaseConfig implements Serializable {

    /** Serial version. */
    private static final long serialVersionUID = 1L;

    /** Names of the fields of cards in this database.
     * @serial include
     */
    private final String[] fieldNames = new String[6];

    /** Creates a CardDatabaseConfig.
     */
    public CardDatabaseConfig() {
    }

    /** Sets the name of the specified field.
     * @param fieldIndex Index of the field of which to set the name.
     * @param fieldName Fieldname.
     */
    public void setFieldname(final int fieldIndex, final String fieldName) {
        fieldNames[fieldIndex] = fieldName;
    }

    /** Returns the name of the specified field.
     * @param fieldIndex Index of the field of which to return the name.
     * @return The name of the specified field.
     */
    public String getFieldname(final int fieldIndex) {
        return fieldNames[fieldIndex];
    }
}
