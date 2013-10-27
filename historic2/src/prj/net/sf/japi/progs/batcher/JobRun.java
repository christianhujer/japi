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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.IOException;
import java.util.List;
import javax.swing.SwingWorker;
import org.jetbrains.annotations.NotNull;

/** A job run.
 *
 * A job run can be executed only once.
 * If you want to execute the same job a second time, use a new JobRun instance.
 *
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class JobRun extends SwingWorker<Object, String> {

    /** The view on which the job run shall be displayed. */
    private final JobRunView jobRunView;

    /** The job that is run. */
    private final Job job;

    /** Creates a JobRun.
     * @param jobRunView JobRunView for displaying the job run.
     * @param job Job to run.
     */
    public JobRun(@NotNull final JobRunView jobRunView, @NotNull final Job job) {
        this.job = job;
        this.jobRunView = jobRunView;
    }

    /** {@inheritDoc} */
    @Override
    public Object doInBackground() {
        try {
            final Process p = job.start();
            final Reader in = /*new BufferedReader*/(new InputStreamReader(p.getInputStream()));
            try {
                final char[] buf = new char[4096];
                for (int charsRead; (charsRead = in.read(buf)) != -1;) {
                    publish(new String(buf, 0, charsRead));
                }
            } finally {
                in.close();
            }
        } catch (final Throwable e) {
            publish(e.toString());
        }
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public void process(final List<String> chunks) {
        for (final String s : chunks) {
            jobRunView.append(s);
        }
    }

    /** Stops this JobRun. */
    public void stop() {
        cancel(true);
    }
}
