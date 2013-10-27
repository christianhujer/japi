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

package net.sf.japi.dircount;

import java.io.File;
import java.io.FileFilter;
import java.util.List;
import net.sf.japi.io.args.ArgParser;
import net.sf.japi.io.args.LogCommand;
import net.sf.japi.io.args.Option;
import org.jetbrains.annotations.NotNull;

/** A command that counts subdirectories and files.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class DirCount extends LogCommand {

    /** Filter for directories. */
    private static final FileFilter DIR_FILTER = new FileFilter() {
        /** {@inheritDoc} */
        public boolean accept(final File pathname) {
            return pathname.isDirectory();
        }
    };

    /** The default maximum depth. */
    private static final int DEFAULT_MAX_DEPTH = -1;

    /** The maximum depth to scan. */
    private int maxDepth = DEFAULT_MAX_DEPTH;

    /** The current depth in the scan. */
    private int depth = 0;

    /** The default whether to print the total. */
    private static final boolean DEFAULT_PRINT_TOTAL = true;

    /** Whether to print the total. */
    private boolean printTotal = DEFAULT_PRINT_TOTAL;

    /** Main program.
     * @param args Command line arguments (try --help).
     */
    public static void main(@NotNull final String... args) {
        ArgParser.simpleParseAndRun(new DirCount(), args);
    }

    /** Sets the maximum depth for printouts while scanning.
     * @param maxDepth Maximum depth for printouts while scanning.
     */
    @Option("maxDepth")
    public void setMaxDepth(@NotNull final Integer maxDepth) {
        this.maxDepth = maxDepth;
    }

    /** Sets whether to print the total.
     * @param printTotal <code>true</code> if the total should be printed, otherwise <code>false</code>.
     */
    @Option("printTotal")
    public void setPrintTotal(@NotNull final Boolean printTotal) {
        this.printTotal = printTotal;
    }

    /** {@inheritDoc} */
    @SuppressWarnings({"InstanceMethodNamingConvention"})
    public int run(@NotNull final List<String> args) throws Exception {
        int sum = 0;
        if (args.size() == 0) {
            sum += count(".");
        } else {
            for (final String dir : args) {
                sum += count(dir);
            }
        }
        if (printTotal) {
            System.out.println("Total: " + sum);
        }
        return 0;
    }

    /** Prints and returns the number of files found in the specified directory.
     * @param dir Directory to create count for.
     * @return The number of files found in the specified directory.
     */
    public int count(@NotNull final String dir) {
        return count(new File(dir));
    }

    /** Prints and returns the number of files found in the specified directory.
     * @param dir Directory to create count for.
     * @return The number of files found in the specified directory.
     * @throws IllegalArgumentException in case <var>dir</var> is not a directory.
     */
    public int count(@NotNull final File dir) {
        if (!dir.isDirectory()) {
            throw new IllegalArgumentException(dir + " is not a directory.");
        }
        int count = 0;
        final File[] subDirs = dir.listFiles(DIR_FILTER);
        for (final File subDir : subDirs) {
            depth++;
            count += count(subDir);
            depth--;
        }
        count += dir.listFiles().length;
        if (depth < (maxDepth & 0xFFFFFFFFL)) {
            System.out.println(dir + ": " + count);
        }
        return count;
    }

} // class DirCount
