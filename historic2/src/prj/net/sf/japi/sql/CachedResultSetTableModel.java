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
import java.sql.SQLException;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import net.sf.japi.util.ThrowableHandler;
import org.jetbrains.annotations.Nullable;

/** An implementation of <code>javax.swing.TableModel</code> for an SQL ResultSet.
 * In contrast to @see ResultSetTableModel this implementation reads all data upon setting the ResultSet.
 * The advantage is that reading the ResultSet does not require the database connection anymore.
 * The disadvantage is that this class requires much memory on large results and may fail for OutOfMemory on huge results.
 * This class is fully serializable, at least in the way Swing classes are at all serializable.
 * <p/>
 * Serialized instances will also serialize the cached data.
 * The data source information is not serialized.
 * @see ResultSet
 * @see TableModel
 * @author <a href="mailto:chris@riedquat.de">Christian Hujer</a>
 * @todo maybe setResultSet should throw SQLException?
 */
public class CachedResultSetTableModel extends AbstractTableModel implements ResultSetTableModel {

    /** Serial Version. */
    private static final long serialVersionUID = 1L;

    /** The number of rows.
     * @serial include
     */
    private int rowCount;

    /** The number of columns.
     * @serial include
     */
    private int columnCount;

    /** The column titles.
     * @serial include
     */
    @Nullable private String[] columnTitles;

    /** The Data.
     * @serial include
     */
    @Nullable private Object[][] data;

    /** The ResultSet. */
    @Nullable private ResultSet resultSet;

    /** Create a CachedResultSetTableModel. */
    public CachedResultSetTableModel() {
    }

    /** Create a CachedResultSetTableModel.
     * @param rs Initial ResultSet
     */
    public CachedResultSetTableModel(@Nullable final ResultSet rs) {
        setResultSet(rs);
    }

    /** Report an exception to all registered ThrowableHandlers.
     * @param exception Exception to report
     */
    private void handleException(final SQLException exception) {
        final Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            //noinspection ObjectEquality
            if (listeners[i] == ThrowableHandler.class) {
                ((ThrowableHandler<? super SQLException>) listeners[i + 1]).handleThrowable(exception);
            }
        }
    }

    /** {@inheritDoc} */
    public void setResultSet(@Nullable final ResultSet resultSet) {
        if (resultSet == null) {
            this.resultSet = resultSet;
            rowCount = 0;
            columnCount = 0;
            columnTitles = null;
            data = null;
        } else {
            try {
                columnTitles = SQLHelper.getColumnLabels(resultSet);
                columnCount = columnTitles.length;
                rowCount = SQLHelper.getRowCount(resultSet);
                data = SQLHelper.getData(resultSet);
                this.resultSet = resultSet;
            } catch (final SQLException e) {
                handleException(e);
                rowCount = 0;
                columnCount = 0;
                columnTitles = null;
                data = null;
                return; // don't invoke fireTableStructureChanged() twice then.
            }
        }
        fireTableStructureChanged();
    }

    /** {@inheritDoc} */
    @Nullable public ResultSet getResultSet() {
        return resultSet;
    }

    /** {@inheritDoc} */
    public void addThrowableHandler(final ThrowableHandler<? super SQLException> throwableHandler) {
        listenerList.add(ThrowableHandler.class, throwableHandler);
    }

    /** {@inheritDoc} */
    public void removeThrowableHandler(final ThrowableHandler<? super SQLException> throwableHandler) {
        listenerList.remove(ThrowableHandler.class, throwableHandler);
    }

    /** @see TableModel */
    public int getColumnCount() {
        return columnCount;
    }

    /** @see TableModel */
    public int getRowCount() {
        return rowCount;
    }

    /** @see TableModel */
    @Nullable public Object getValueAt(final int rowIndex, final int columnIndex) {
        try {
            return data[rowIndex][columnIndex];
        } catch (final NullPointerException ignore) {
            return null;
        } catch (final ArrayIndexOutOfBoundsException ignore) {
            return null;
        }
    }

    /** {@inheritDoc}
     * Always returns <code>String.class</code>.
     */
    @Override public Class<?> getColumnClass(final int columnIndex) {
        return String.class;
    }

    /** {@inheritDoc} */
    @Override @Nullable public String getColumnName(final int column) {
        return columnTitles != null ? columnTitles[column] : null;
    }

} // class CachedResultSetTableModel
