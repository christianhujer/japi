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

package net.sf.japi.progs.batcher;

import org.jetbrains.annotations.NotNull;

/** A project.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class Project implements Changeable {

    /** Serial version. */
    private static final long serialVersionUID = 1L;

    /** List with all jobs.
     * @serial include
     */
    private JobList jobList = new JobList();

    /** List with all batches.
     * @serial include
     */
    private BatchList batchList = new BatchList();

    /** The title of this project.
     * @serial include
     */
    private String title;

    /** Changed state.
     * @serial include
     */
    private boolean changed;

    /** Create an unnamed project. */
    public Project() {
        this("unnamed project");
    }

    /** Create a project.
     * @param title The title of this project.
     */
    public Project(@NotNull final String title) {
        this.title = title;
    }

    /** Returns the job list of this project.
     * @return The job list.
     */
    public JobList getJobList() {
        return jobList;
    }

    /** Returns the batch list of this project.
     * @return The batch list.
     */
    public BatchList getBatchList() {
        return batchList;
    }

    /** Returns the title of this project.
     * @return The title.
     */
    public String getTitle() {
        return title;
    }

    /** Sets the title of this project.
     * @param title New title of this project.
     */
    public void setTitle(@NotNull final String title) {
        this.title = title;
        setChanged(true);
    }

    /** {@inheritDoc} */
    public boolean hasChanged() {
        return changed || jobList.hasChanged() || batchList.hasChanged();
    }

    /** {@inheritDoc} */
    public void setChanged(final boolean changed) {
        this.changed = changed;
        if (!changed) {
            jobList.setChanged(changed);
            batchList.setChanged(changed);
        }
    }

}
