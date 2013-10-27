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

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Map;
import net.sf.japi.swing.misc.CollectionsListModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** A Job.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class Job extends AbstractChangeable {

    /** Serial version. */
    private static final long serialVersionUID = 1L;

    /** The title.
     * @serial include
     */
    private String title;

    /** The environment.
     * Inherited from parent if <code>null</code>.
     * @serial exclude
     */
    @Nullable private transient Map<String, String> env;

    /** The command line with its arguments.
     * @serial include
     */
    private CollectionsListModel<String> command = new CollectionsListModel<String>(new ArrayList<String>());

    /** The process builder.
     * @serial exclude
     */
    private transient ProcessBuilder processBuilder;

    /** Creates a Job.
     * @param title Title for this Job.
     */
    public Job(@NotNull final String title) {
        processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);
        env = processBuilder.environment();
        this.title = title;
    }

    /** {@inheritDoc} */
    private void readObject(@NotNull final ObjectInputStream in) throws ClassNotFoundException, IOException {
        in.defaultReadObject();
        processBuilder = new ProcessBuilder(command);
        env = processBuilder.environment();
    }

    /** Returns the current working directory.
     * @return The current working directory.
     */
    @Nullable public File getCurrentWorkingDirectory() {
        return processBuilder.directory();
    }

    /** Sets the current working directory.
     * @param directory The new current working directory.
     */
    public void setCurrentWorkingDirectory(@Nullable final File directory) {
        processBuilder.directory(directory);
    }

    /** Returns the arguments.
     * @return Arguments
     */
    @NotNull public CollectionsListModel<String> getCommand() {
        return command;
    }

    /** Starts a process.
     * @return Process just started.
     * @throws IOException in case the process could not be started.
     */
    public Process start() throws IOException {
        return processBuilder.start();
    }

}
