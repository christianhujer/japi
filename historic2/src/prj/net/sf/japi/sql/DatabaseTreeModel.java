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

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/** A TreeModel displaying the catalogs of a database (usually tables and views) as tree.
 * @author <a href="mailto:chris@riedquat.de">Christian Hujer</a>
 */
@SuppressWarnings({"ObjectEquality"})
public class DatabaseTreeModel implements TreeModel {

    /** The event listeners. */
    private final EventListenerList listenerList = new EventListenerList();

    /** The Database nodes in the tree. */
    private final List<CatalogTreeNode> catalogs = new ArrayList<CatalogTreeNode>();

    /** The Database Metadata of the database this treemodel reflects. */
    private DatabaseMetaData databaseMetaData;

    /** Create a DatabaseTreeModel.
     * Thw tree model is not yet connected to databaseMetaData.
     */
    public DatabaseTreeModel() {
    }

    /** Create a DatabaseTreeModel for a Database.
     * @param databaseMetaData Database Metadata for this tree model
     * @throws SQLException in case of database problems
     */
    public DatabaseTreeModel(final DatabaseMetaData databaseMetaData) throws SQLException {
        this.databaseMetaData = databaseMetaData;
        refresh();
    }

    /** Refresh the data of this model from the database.
     * @throws SQLException in case of database problems
     */
    public void refresh() throws SQLException {
        catalogs.clear();
        if (databaseMetaData != null) {
            final ResultSet rs = databaseMetaData.getCatalogs();
            try {
                while (rs.next()) {
                    try {
                        catalogs.add(new CatalogTreeNode(rs.getString(1)));
                    } catch (final SQLException e) {
                        System.err.println(e);
                        // TODO:2009-05-13:christianhujer:Improve error handling.
                    }
                }
            } finally {
                rs.close();
            }
        }
        fireTreeStructureChanged();
    }

    /** Set the databaseMetaData for this model.
     * @param databaseMetaData database meta data for this model
     * @throws SQLException in case of database problems
     */
    public void setDatabaseMetaData(final DatabaseMetaData databaseMetaData) throws SQLException {
        this.databaseMetaData = databaseMetaData;
        refresh();
    }

    /** Get the databaseMetaData of this model.
     * @return databaseMetaData of this model
     */
    public DatabaseMetaData getDatabaseMetaData() {
        return databaseMetaData;
    }

    /** {@inheritDoc} */
    public void addTreeModelListener(final TreeModelListener l) {
        listenerList.add(TreeModelListener.class, l);
    }

    /** {@inheritDoc} */
    public Object getChild(final Object parent, final int index) {
        if (parent == this) {
            return catalogs.get(index);
        } else {
            assert parent instanceof CatalogTreeNode;
            return ((CatalogTreeNode) parent).getTable(index);
        }
    }

    /** {@inheritDoc} */
    public int getChildCount(final Object parent) {
        return parent == this ? catalogs.size() : ((CatalogTreeNode) parent).getTableCount();
    }

    /** {@inheritDoc} */
    public int getIndexOfChild(final Object parent, final Object child) {
        return parent == this ? catalogs.indexOf(child) : ((CatalogTreeNode) parent).getTableIndex((CatalogTreeNode.TableTreeNode) child);
    }

    /** {@inheritDoc} */
    public Object getRoot() {
        return this;
    }

    /** {@inheritDoc} */
    public boolean isLeaf(final Object node) {
        return node instanceof CatalogTreeNode.TableTreeNode;
    }

    /** {@inheritDoc} */
    public void removeTreeModelListener(final TreeModelListener l) {
        listenerList.remove(TreeModelListener.class, l);
    }

    /** {@inheritDoc} */
    public void valueForPathChanged(final TreePath path, final Object newValue) {
    }

    /** Event fire if the tree has changed. */
    private void fireTreeStructureChanged() {
        TreeModelEvent e = null;
        final Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == TreeModelListener.class) {
                if (e == null) {
                    e = new TreeModelEvent(this, new Object[] { this });
                }
                ((TreeModelListener) listeners[i + 1]).treeStructureChanged(e);
            }
        }
    }

    /** A TreeNode reflecting a Database. */
    public class CatalogTreeNode {

        /** The name of this catalog. */
        private String catalog;

        /** The tables of this catalog. */
        private final List<TableTreeNode> tables = new ArrayList<TableTreeNode>();

        /** Create a CatalogTreeNode.
         * @param catalog Catalog for which to create a CatalogTreeNode.
         * @throws SQLException in case of database problems
         */
        CatalogTreeNode(final String catalog) throws SQLException {
            this.catalog = catalog;
            final ResultSet rs = databaseMetaData.getTables(catalog, null, null, null);
            try {
                while (rs.next()) {
                    tables.add(new TableTreeNode(rs.getString("TABLE_NAME")));
                }
            } finally {
                rs.close();
            }
        }

        /** Get a table with a certain index.
         * @param index index of table to get
         * @return table for <var>index</var>
         */
        TableTreeNode getTable(final int index) {
            return tables.get(index);
        }

        /** Get the number of tables.
         * @return number of tables
         */
        public int getTableCount() {
            return tables.size();
        }

        /** Get the index of a table.
         * @param table Table to get index of
         * @return index of <var>table</var>
         */
        public int getTableIndex(final TableTreeNode table) {
            return tables.indexOf(table);
        }

        /** {@inheritDoc} */
        @Override public String toString() {
            return catalog;
        }

        /** A TreeNode reflecting a Table. */
        public class TableTreeNode {

            /** The name of this table. */
            private String table;

            /** Create a TableTreeNode.
             * @param table Table name for which to create a TableTreeNode.
             */
            TableTreeNode(final String table) {
                this.table = table;
            }

            /** Get the name of the table this TableTreeNode represents.
             * @return table name
             */
            public String getTableName() {
                return catalog + '.' + table;
            }

            /** Get the name of the catalog this TableTreeNode is in.
             * @return catalog name
             */
            public String getCatalogName() {
                return catalog;
            }

            /** {@inheritDoc} */
            @Override public String toString() {
                return table;
            }

        } // class TableTreeNode

    } // class CatalogTreeNode

} // class DatabaseTreeModel
