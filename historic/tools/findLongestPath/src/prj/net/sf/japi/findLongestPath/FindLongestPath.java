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

package net.sf.japi.findLongestPath;

import java.io.File;
import java.util.List;
import java.util.logging.Level;
import net.sf.japi.io.args.ArgParser;
import net.sf.japi.io.args.LogCommand;
import net.sf.japi.io.args.Option;
import org.jetbrains.annotations.NotNull;

/** Program that finds the longest path name.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class FindLongestPath extends LogCommand {

    /** Runs the program.
     * @param args Command line arguments.
     */
    public static void main(final String... args) {
        ArgParser.simpleParseAndRun(new FindLongestPath(), args);
    }

    /** The longest path name found so far. */
    @NotNull private String maxPathname = "";

    /** The length of the longest path name found so far. */
    private int maxLength = 0;

    /** Whether to operate with absolute paths.
     * <code>true</code> means paths are converted to absolute paths, even if they are relative.
     * <code>false</code> means paths stay relative if they are relative.
     */
    private boolean absolute;

    /** Create a FindLongestPath. */
    public FindLongestPath() {
    }

    /** Reset this FindLongestPath to its initial state. */
    public void reset() {
        maxPathname = "";
        maxLength = 0;
    }

    /** {@inheritDoc} */
    @SuppressWarnings({"InstanceMethodNamingConvention"})
    public int run(@NotNull final List<String> args) throws Exception {
        if (args.size() == 0) {
            find(".");
            System.out.println(this);
        } else {
            for (final String arg : args) {
                find(arg);
                System.out.println(this);
                reset();
            }
        }
        return 0;
    }

    /** Sets whether to operate with absolute paths.
     * @param absolute Whether to operate with absolute paths, <code>true</code> to convert paths to absolute paths, <code>false</code> to not perform any conversion.
     */
    @Option({"a", "absolute"})
    public void setAbsolute(final boolean absolute) {
        this.absolute = absolute;
    }

    /** Performs a search based on a pathname.
     * @param pathname Pathname to search for the longest pathname.
     */
    private void find(@NotNull final String pathname) {
        find(new File(pathname));
    }

    /** Performs a search based on a file.
     * @param path File to search for the longest pathname.
     */
    private void find(@NotNull final File path) {
        final String pathname = absolute ? path.getAbsolutePath() : path.getPath();
        final int length = pathname.length();
        if (length > maxLength) {
            maxLength = length;
            maxPathname = pathname;
        }
        if (path.isDirectory()) {
            log(Level.FINE, "descending", path);
            for (final File child : path.listFiles()) {
                find(child);
            }
        }
    }

    /** {@inheritDoc} */
    @Override public String toString() {
        return maxPathname + ":" + maxLength;
    }

} // class FindLongestPath
