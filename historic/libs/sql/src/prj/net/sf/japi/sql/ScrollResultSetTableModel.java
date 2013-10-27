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

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Ref;
import java.sql.ResultSet;
import static java.sql.ResultSet.CONCUR_UPDATABLE;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Struct;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import javax.sql.RowSet;
import javax.sql.RowSetEvent;
import javax.sql.RowSetListener;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import net.sf.japi.util.ThrowableHandler;
import org.jetbrains.annotations.Nullable;

/** An implementation of <code>javax.swing.TableModel</code> for an SQL ResultSet.
 * It is required that the ResultSet is absolutely navigatable.
 * That feature heavily depends on the JDBC Driver implementation.
 * Please note that though this class does NOT store the ResultSet data except some meta data, the {@link JTable} probably will in its own private shadow copy table model.
 * @see ResultSet
 * @see TableModel
 * @author <a href="mailto:chris@riedquat.de">Christian Hujer</a>
 */
public class ScrollResultSetTableModel extends AbstractTableModel implements ResultSetTableModel, RowSetListener {

    /** Serial Version. */
    private static final long serialVersionUID = 1L;

    /** The ResultSet. */
    @Nullable private transient ResultSet resultSet;

    /** The ResultSetMetaData. */
    @Nullable private transient ResultSetMetaData metaData;

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

    /** Create a ResultSetTableModel. */
    public ScrollResultSetTableModel() {
    }

    /** Create a ResultSetTableModel.
     * @param rs Initial ResultSet
     */
    public ScrollResultSetTableModel(final ResultSet rs) {
        setResultSet(rs);
    }

    /** {@inheritDoc} */
    public void setResultSet(@Nullable final ResultSet resultSet) {
        this.resultSet = resultSet;
        updateResultSetData();
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

    /** {@inheritDoc} */
    public void rowSetChanged(final RowSetEvent event) {
        System.err.println(event);
        updateResultSetData();
    }

    /** {@inheritDoc} */
    // Remove ConstantConditions once IntelliJ IDEA is smarter about null
    @SuppressWarnings({"ConstantConditions"})
    public void rowChanged(final RowSetEvent event) {
        System.err.println(event);
        if (resultSet == null) {
            return;
        }
        try {
            final int row = resultSet.getRow();
            if (resultSet.rowDeleted()) {
                fireTableRowsDeleted(row, row);
            } else if (resultSet.rowInserted()) {
                fireTableRowsInserted(row, row);
            } else if (resultSet.rowUpdated()) {
                fireTableRowsUpdated(row, row);
            }
        } catch (final SQLException e) {
            handleException(e);
        }
    }

    /** {@inheritDoc} */
    public void cursorMoved(final RowSetEvent event) {
        System.err.println(event);
        // ignore
    }

    /** {@inheritDoc} */
    public int getRowCount() {
        final ResultSet resultSet = this.resultSet;
        if (resultSet == null) {
            return 0;
        }
        try {
            return resultSet.last() ? resultSet.getRow() : 0;
        } catch (final SQLException e) {
            handleException(e);
            return 0;
        }
        //return rowCount;
    }

    /** {@inheritDoc} */
    public int getColumnCount() {
        if (metaData == null) {
            return 0;
        }
        try {
            return metaData.getColumnCount();
        } catch (final SQLException e) {
            handleException(e);
            return 0;
        }
        //return columnCount;
    }

    /** {@inheritDoc} */
    // Remove ConstantConditions once IntelliJ IDEA is smarter about null
    @SuppressWarnings({"ConstantConditions"})
    @Nullable public Object getValueAt(final int rowIndex, final int columnIndex) {
        if (resultSet == null) {
            return null;
        }
        try {
            resultSet.absolute(rowIndex + 1);
            return resultSet.getString(columnIndex + 1);
        } catch (final SQLException e) {
            handleException(e);
            setResultSet(null);
            return null;
        }
    }

    /** {@inheritDoc} */
    @SuppressWarnings({"OverlyComplexMethod", "OverlyLongMethod", "SwitchStatementWithTooManyBranches"})
    @Override public Class<?> getColumnClass(final int columnIndex) {
        final int type;
        try {
            if (metaData == null) {
                // TODO:2009-05-13:christianhujer:Display error to usage, reset state
                return super.getColumnClass(columnIndex);
            }
            type = metaData.getColumnType(columnIndex + 1);
        } catch (final SQLException e) {
            handleException(e);
            // TODO:2009-05-13:christianhujer:reset state
            return super.getColumnClass(columnIndex);
        }
        switch (type) {
            case Types.BIT:           return Boolean.class;
            case Types.TINYINT:       return Byte.class;
            case Types.SMALLINT:      return Short.class;
            case Types.INTEGER:       return Integer.class;
            case Types.BIGINT:        return Long.class;
            case Types.FLOAT:
            case Types.REAL:          return Float.class;
            case Types.DOUBLE:        return Double.class;
            case Types.NUMERIC:       return Number.class;
            case Types.DECIMAL:       return BigDecimal.class;
            case Types.CHAR:
            case Types.VARCHAR:
            case Types.LONGVARCHAR:   return String.class;
            case Types.DATE:          return Date.class;
            case Types.TIME:          return Time.class;
            case Types.TIMESTAMP:     return Timestamp.class;
            case Types.BINARY:
            case Types.VARBINARY:
            case Types.LONGVARBINARY: return byte[].class;
            case Types.OTHER:
            case Types.JAVA_OBJECT:   return Object.class;
            case Types.CLOB:          return Clob.class;
            case Types.BLOB:          return Blob.class;
            case Types.REF:           return Ref.class;
            case Types.STRUCT:        return Struct.class;
            default:                  return super.getColumnClass(columnIndex);
        }
    }

    /** {@inheritDoc} */
    @Override @Nullable public String getColumnName(final int column) {
        if (metaData == null) {
            // TODO:2009-05-13:christianhujer:Return alternative name?
            return null;
        }
        try {
            return metaData.getColumnName(column + 1);
        } catch (final SQLException e) {
            handleException(e);
            return null;
        }
        //return columnTitles[column];
    }

    /** Insert a row.
     * This is a proxy method to the underlying result set.
     * @param rowData data for new row
     * @throws SQLException in case of SQL problems
     */
    // Remove ConstantConditions once IntelliJ IDEA is smarter about null
    @SuppressWarnings({"ConstantConditions"})
    public void insert(final String[] rowData) throws SQLException {
        if (resultSet != null) {
            return;
        }
        resultSet.moveToInsertRow();
        for (int i = 0; i < rowData.length; i++) {
            resultSet.updateString(i + 1, rowData[i]);
        }
        resultSet.insertRow();
        resultSet.moveToCurrentRow();
        final int row = resultSet.getRow();
        fireTableRowsInserted(row, row);
    }

    /** {@inheritDoc} */
    @Override public boolean isCellEditable(final int rowIndex, final int columnIndex) {
        try {
            return resultSet != null && resultSet.getConcurrency() == CONCUR_UPDATABLE;
        } catch (final SQLException e) {
            handleException(e);
            // TODO:2009-05-13:christianhujer:Reset state.
            return false;
        }
    }

    /** {@inheritDoc} */
    // Remove ConstantConditions once IntelliJ IDEA is smarter about null
    @SuppressWarnings({"ConstantConditions"})
    @Override public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
        if (resultSet == null) {
            return;
        }
        try {
            if (!resultSet.absolute(rowIndex + 1)) {
                // TODO:2009-05-13:christianhujer:Display error to usage, reset state
                return;
            }
            resultSet.updateObject(columnIndex + 1, aValue);
            resultSet.updateRow();
        } catch (final SQLException e) {
            handleException(e);
            // TODO:2009-05-13:christianhujer:reset state
        }
    }

    /** Updates the data from the result set. */
    private void updateResultSetData() {
        if (resultSet == null) {
            rowCount = 0;
            columnCount = 0;
            columnTitles = null;
        } else {
            try {
                metaData = resultSet.getMetaData();
                if (resultSet instanceof RowSet) {
                    ((RowSet) resultSet).addRowSetListener(this);
                }
                columnTitles = SQLHelper.getColumnLabels(resultSet);
                columnCount = columnTitles != null ? columnTitles.length : 0;
                rowCount = SQLHelper.getRowCount(resultSet);
            } catch (final SQLException e) {
                handleException(e);
                resultSet = null;
                rowCount = 0;
                columnCount = 0;
                columnTitles = null;
            }
        }
        fireTableStructureChanged();
    }

    /** Delete a row.
     * This is a proxy method to the underlying result set.
     * @param n row number to delete (Java row number, 0 .. (number of rows - 1))
     * @throws SQLException in case of SQL problems
     */
    // Remove ConstantConditions once IntelliJ IDEA is smarter about null
    @SuppressWarnings({"ConstantConditions"})
    public void deleteRow(final int n) throws SQLException {
        if (resultSet == null) {
            return;
        }
        resultSet.absolute(n + 1);
        resultSet.deleteRow();
        fireTableRowsDeleted(n, n);
    }

    /** Get the meta data.
     * @return meta data
     */
    @Nullable public ResultSetMetaData getMetaData() {
        return metaData;
    }

} // class ResultSetTableModel
