/* JAPI - (Yet another (hopefully) useful) Java API
 *
 * Copyright (C) 2004-2006 Christian Hujer
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package net.sf.japi.sql;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/** A Helper Class to make work with JDBC less painful in some situations.
 * @author <a href="mailto:chris@riedquat.de">Christian Hujer</a>
 */
public final class SQLHelper {

    /** Utility class - do not instantiate. */
    private SQLHelper() {
    }

    /** The Type information for a two-dimensional Object Array. */
    private static final Object[][] OBJECT_ARRAY_ARRAY_INSTANCE = new Object[0][0];

    /** Get a ResultSet's Column Names.
     * The names are physical.
     * For names for display @see #getColumnLabels(ResultSet).
     * @param rs ResultSet
     * @return column names
     * @throws SQLException on SQL problems
     */
    public static String[] getColumnNames(final ResultSet rs) throws SQLException {
        final ResultSetMetaData md = rs.getMetaData();
        final int columnCount = md.getColumnCount();
        final String[] columnNames = new String[columnCount];
        for (int i = 0; i < columnCount; i++) {
            columnNames[i] = md.getColumnName(i + 1);
        }
        return columnNames;
    }

    /** Get a ResultSet's Column Labels.
     * The names are for display.
     * For physical names @see #getColumnNames(ResultSet).
     * @param rs ResultSet
     * @return column labels
     * @throws SQLException on SQL problems
     */
    @NotNull
    public static String[] getColumnLabels(final ResultSet rs) throws SQLException {
        final ResultSetMetaData md = rs.getMetaData();
        final int columnCount = md.getColumnCount();
        final String[] columnNames = new String[columnCount];
        for (int i = 0; i < columnCount; i++) {
            columnNames[i] = md.getColumnLabel(i + 1);
        }
        return columnNames;
    }

    /** Get a ResultSet's result.
     * @param rs ResultSet
     * @return two-dimensional Array containing the results
     * @throws SQLException on SQL problems
     */
    public static Object[][] getData(final ResultSet rs) throws SQLException {
        final List<Object[]> resultList = new ArrayList<Object[]>();
        final ResultSetMetaData md = rs.getMetaData();
        final int columnCount = md.getColumnCount();
        while (rs.next()) {
            final Object[] row = new Object[columnCount];
            for (int i = 0; i < columnCount; i++) {
                row[i] = rs.getObject(i + 1);
            }
            resultList.add(row);
        }
        return resultList.toArray(OBJECT_ARRAY_ARRAY_INSTANCE);
    }

    /** Get a ResultSet's size.
     * Warning: The ResultSet's Cursor is moved by invoking this method!
     * @param rs ResultSet
     * @return the number of rows in this ResultSet
     * @throws SQLException on SQL problems
     */
    public static int getRowCount(final ResultSet rs) throws SQLException {
        return rs.last() ? rs.getRow() : 0;
    }

} // class SQLHelper
