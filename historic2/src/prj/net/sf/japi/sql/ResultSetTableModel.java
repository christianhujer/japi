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
import javax.swing.table.TableModel;
import net.sf.japi.util.ThrowableHandler;
import org.jetbrains.annotations.Nullable;

/** Interface for TableModels which handle information from ResultSets.
 * @author <a href="mailto:chris@riedquat.de">Christian Hujer</a>
 */
public interface ResultSetTableModel extends TableModel {

    /** Set the ResultSet.
     * @param resultSet ResultSet, maybe <code>null</code>
     */
    void setResultSet(ResultSet resultSet);

    /** Get the ResultSet.
     * @return ResultSet or <code>null</code> if there is no current result set
     */
    @Nullable ResultSet getResultSet();

    /** Adds a ThrowableHandler to this model.
     * @param throwableHandler ThrowableHandler to add
     */
    void addThrowableHandler(ThrowableHandler<? super SQLException> throwableHandler);

    /** Removes a ThrowableHandler from this model.
     * @param throwableHandler ThrowableHandler to add
     */
    void removeThrowableHandler(ThrowableHandler<? super SQLException> throwableHandler);

} // interface ResultSetTableModel
