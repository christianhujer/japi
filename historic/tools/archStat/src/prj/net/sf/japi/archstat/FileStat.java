/*
 * Copyright (C) 2009 - 2010  Christian Hujer
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

package net.sf.japi.archstat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** Per-File statistics.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class FileStat {

    /** The checks that should be performed. */
    private final List<RegexLineCheck> checkers;

    /** The warnings that occurred in that individual check. */
    private final Map<RegexLineCheck, Integer> checkWarnings = new HashMap<RegexLineCheck, Integer>();

    /** The children of this statistic. */
    private final Map<File, FileStat> children = new HashMap<File, FileStat>();

    /** The list of errors that occurred. */
    private final Collection<Throwable> errors = new ArrayList<Throwable>();

    /** The title of this statistic. */
    private final String title;

    /** The file (regular file or directory) of this statistic. */
    @Nullable private final File file;

    /** Number of raw lines. */
    private int linesRaw;

    /** Number of lines which are not a comment. */
    private int linesNoComment;

    /** Number of true source lines. */
    private int sourceLines;

    /** Number of lines which are a comment. */
    private int commentLines;

    /** The text of the file to which this FileStat applies. */
    private CharSequence fileText;

    /** Number of warnings that occurred. */
    private int warnings;

    @SuppressWarnings({"AssignmentToCollectionOrArrayFieldFromParameter"})
    FileStat(final List<RegexLineCheck> checkers) {
        this.checkers = checkers;
        title = "<SUM>";
        file = null;
    }

    @SuppressWarnings({"AssignmentToCollectionOrArrayFieldFromParameter"})
    FileStat(final List<RegexLineCheck> checkers, @NotNull final File file, final boolean printSummaries) {
        this.checkers = checkers;
        this.file = file;
        title = file.toString();
        if (file.isFile()) {
            CharSequence fileText;
            try {
                fileText = ArchStat.readFile(file);
            } catch (final IOException e) {
                System.err.println(e);
                fileText = "";
                errors.add(e);
            }
            this.fileText = fileText;
            linesRaw = ArchStat.countLines(fileText);
            final CharSequence noComments = ArchStat.removeCComments(fileText);
            linesNoComment = ArchStat.countLines(noComments);
            sourceLines = ArchStat.countSourceLines(noComments);
            commentLines = linesRaw - linesNoComment;
            final String[] lines = ArchStat.getLines(fileText);
            for (int i = 0; i < lines.length; i++) {
                checkLine(lines[i], i + 1);
            }
        }
        if (file.isDirectory()) {
            for (final File member : file.listFiles()) {
                addChild(member, printSummaries);
            }
        }
        if (printSummaries) {
            treeWarnings();
        }
    }
    void treeWarnings() {
        for (final RegexLineCheck regexLineCheck : checkers) {
            final Integer i = checkWarnings.get(regexLineCheck);
            if (i != null && i > 0) {
                System.err.printf("%s: %s: %d %s%n", file, regexLineCheck.getMessageType(), i, regexLineCheck.getPlural());
            }
        }
        if (getWarnings() > 0) {
            System.err.println(file + ": " + "warning: " + getWarnings() + " warnings.");
        }
    }
    void checkLine(final String line, final int lineNumber) {
        for (final RegexLineCheck regexLineCheck : checkers) {
            if (regexLineCheck.hasProblem(file, line, lineNumber)) {
                incWarning(regexLineCheck);
            }
        }
    }
    public void incWarning(final RegexLineCheck regexLineCheck) {
        warnings = getWarnings() + 1;
        final Integer i = checkWarnings.get(regexLineCheck);
        checkWarnings.put(regexLineCheck, i != null ? i + 1 : 1);
    }

    /** Adds a child with the specified filename.
     * @param filename Filename of the child to add.
     * @param printSummaries Whether or not to print summaries.
     */
    public void addChild(final String filename, final boolean printSummaries) {
        addChild(new File(filename), printSummaries);
    }

    /** Adds a child with the specified file.
     * @param file File of the child to add.
     * @param printSummaries Whether or not to print summaries.
     */
    public void addChild(final File file, final boolean printSummaries) {
        if (!isIgnored(file)) {
            addChild(new FileStat(checkers, file, printSummaries));
        }
    }

    /** Returns whether or not a file is ignored.
     * @param file File that might be ignored.
     * @return <code>true</code> if <var>file</var> is ignored, otherwise <code>false</code>.
     */
    public boolean isIgnored(@NotNull final File file) {
        final String filename = file.getName();
        // TODO:2009-02-18:christianhujer:This should be configurable.
        return ".svn".equals(filename);
    }

    /** Adds a child with the specified statistics.
     * @param child Child to add.
     */
    public void addChild(final FileStat child) {
        children.put(child.file, child);
        linesRaw += child.linesRaw;
        linesNoComment += child.linesNoComment;
        sourceLines += child.sourceLines;
        commentLines += child.commentLines;
        warnings = getWarnings() + child.getWarnings();
        for (final RegexLineCheck regexLineCheck : checkers) {
            final Integer childI = child.checkWarnings.get(regexLineCheck);
            if (childI != null) {
                final Integer i = checkWarnings.get(regexLineCheck);
                checkWarnings.put(regexLineCheck, i != null ? i + childI : childI);
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return title + ';' + errors.size() + ';' + linesRaw + ';' + linesNoComment + ';' + commentLines + ';' + sourceLines;
    }

    /** Prints recursive statistics including table header to the specified appendable.
     * @param out Appendable to print to.
     * @param level Nesting level for which to print statistics.
     * @throws IOException In case of I/O problems.
     */
    public void printStatistic(@NotNull final Appendable out, final int level) throws IOException {
        if (level > 0) {
            out.append("Title;Errors;Raw lines;Lines w/o comments;Comment lines;Sloc\n");
            print(out, level);
        }
    }

    /** Prints recursive statistics to the specified appendable.
     * @param out Appendable to print to.
     * @param level Nesting level for which to print statistics.
     * @throws IOException In case of I/O problems.
     */
    public void print(final Appendable out, final int level) throws IOException {
        out.append(toString());
        out.append("\n");
        if (level > 1) {
            for (final File file : new TreeSet<File>(children.keySet())) {
                children.get(file).print(out, level - 1);
            }
        }
    }

    public int getWarnings() {
        return warnings;
    }
}
