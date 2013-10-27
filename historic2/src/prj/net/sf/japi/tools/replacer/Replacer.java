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

package net.sf.japi.tools.replacer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Formatter;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import net.sf.japi.io.args.ArgParser;
import net.sf.japi.io.args.BasicCommand;
import net.sf.japi.io.args.CharsetDisplaynameComparator;
import net.sf.japi.io.args.Option;
import net.sf.japi.io.args.OptionType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** Performs one or more regular expression based substitutions on one or more files.
 * Files will only be written if at least one of the substitution steps caused a change.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class Replacer extends BasicCommand {

    /** The default file ending for backup files. */
    @NotNull
    public static final String DEFAULT_BACKUP_EXTENSION = ".bak";

    /** Buffer size for buffers, e.g. read or copy buffer. */
    private static final int BUF_SIZE = 4096;

    /** Main program.
     * @param args Command line arguments (use -h or --help for info)
     */
    public static void main(final String... args) {
        ArgParser.simpleParseAndRun(new Replacer(), args);
    }

    /** The list of substitutions that shall be performed. */
    @NotNull private final Collection<Substitution> substitutions = new ArrayList<Substitution>();

    /** Unmodifiable view to the list of substitutions that shall be performed. */
    @NotNull private final Collection<Substitution> unmodifiableSubstitutions = Collections.unmodifiableCollection(substitutions);

    /** Whether or not to force overwriting files or not.
     * If <code>true</code>, files will be made writable if possible.
     * If <code>false</code> (default), files can only be written if they were writable before.
     */
    private boolean force;

    /** Whether or not to recurse into subdirectories or not.
     * If <code>true</code>, directories will be opened recursively and substitution performed on all files found.
     * If <code>false</code> (default), directories will simply be ignored.
     */
    private boolean recursive;

    /** Whether or not to create backups of modified files.
     * If <code>true</code>, backups with the original file content will be made if a file has changed.
     * If <code>false</code> (default), no backups will be made.
     * Intermediate backups will always be created to make writing pseudo-atomic.
     */
    private boolean backup;

    /** Whether or not to be verbose.
     * If <code>true</code>, verbose output like the changed file will be printed to stderr.
     * If <code>false</code> (default), the program behaves silently.
     */
    private boolean verbose;

    /** Whether or not to pretend.
     * If <code>true</code>, changes are just pretended to be made, but files are never changed.
     * If <code>false</code> (default), the program changes files.
     */
    private boolean dryRun;

    /** The number of errors that occurred.
     * Errors are only cases where actual exceptions were thrown, i.e. {@link FileNotFoundException} or {@link IOException}.
     * If 0 (zero) no errors occurred.
     * This variable will somehow be used for the exit code.
     */
    private int errors;

    /** The default file extension for backup files. */
    @NotNull private String backupExtension = DEFAULT_BACKUP_EXTENSION;

    /** The file encoding that is used for reading and writing files as weil es parsing substitutions from {@link System#in}. */
    private Charset encoding = Charset.defaultCharset();

    /** The command to make the file writable. */
    @Nullable private String makeWritableCommand = null;

    /** {@inheritDoc} */
    @SuppressWarnings({"InstanceMethodNamingConvention", "ProhibitedExceptionDeclared"})
    public int run(@NotNull final List<String> args) throws Exception {
        if (args.size() == 0) {
            final CharSequence content = read(System.in);
            final CharSequence newContent = replaceAll(content);
            System.out.append(newContent);
            System.out.flush();
        }
        for (final String arg : args) {
            replace(arg);
        }
        return errors;
    }

    /** Performs substitutions on the file or directory specified by <var>filename</var>.
     * The behaviour on directories depends on {@link #recursive}.
     * @param filename Name of the file or directory to perform substitutions on.
     */
    public void replace(@NotNull final String filename) {
        replace(new File(filename));
    }

    /** Performs substitutions on the file or directory specified by <var>file</var>.
     * The behaviour on directories depends on {@link #recursive}.
     * @param file The file or directory to perform substitutions on.
     */
    public void replace(@NotNull final File file) {
        if (recursive && file.isDirectory()) {
            for (final File f : file.listFiles()) {
                replace(f);
            }
        }
        // Checking for non-existant files is intentional.
        // Opening the non-existant file will lead to the exception and thus the error message to inform the user.
        if (file.isFile() || !file.exists()) {
            try {
                final CharSequence content = readFile(file);
                final CharSequence newContent = replaceAll(content);
                if (!equals(content, newContent)) {
                    if (backup) {
                        final File tmpFile = new File(file.getParentFile(), file.getName() + backupExtension);
                        copyFile(file, tmpFile);
                        writeFile(file, newContent);
                        tmpFile.deleteOnExit();
                    } else {
                        writeFile(file, newContent);
                    }
                }
            } catch (final IOException e) {
                System.err.println(e);
                errors++;
            }
        }
    }

    /** Returns whether or not two Objects are equal regarding their String representation.
     * This method is particularly useful for CharSequences because CharSequence does not define a general contract about <code>equals()</code> and <code>hashCode()</code>.
     * Therefore it is inappropriate to compare CharSequences using <code>equals()</code>.
     * @param s1 First Object to compare.
     * @param s2 Second Object to compare.
     * @return <code>true</code> if <code><var>s1</var></code> and <code><var>s2</var></code> are equal regarding their String representation, otherwise <code>false</code>.
     */
    public static boolean equals(@NotNull final Object s1, @NotNull final Object s2) {
        return s1.toString().equals(s2.toString());
    }

    /** Performs all substitutions on the specified char sequence.
     * @param orig Original char sequence to perform substitution on.
     * @return New CharSequence with all substitutions performed.
     */
    @NotNull public CharSequence replaceAll(@NotNull final CharSequence orig) {
        CharSequence temp = orig;
        for (final Substitution substitution : substitutions) {
            temp = substitution.replace(temp);
        }
        return temp;
    }

    /** Reads a String from an InputStream.
     * @param stream InputStream to read.
     * @return Contents of the InputStream.
     * @throws IOException In case of I/O problems.
     */
    @NotNull public CharSequence read(@NotNull final InputStream stream) throws IOException {
        final StringBuilder sb = new StringBuilder();
        @SuppressWarnings({"IOResourceOpenedButNotSafelyClosed"})
        final Reader in = new InputStreamReader(stream instanceof BufferedInputStream ? stream : new BufferedInputStream(stream), encoding);
        final char[] buf = new char[BUF_SIZE];
        //noinspection NestedAssignment
        for (int charsRead; (charsRead = in.read(buf)) != -1;) {
            sb.append(buf, 0, charsRead);
        }
        return sb;
    }

    /** Reads a (text) file and returns a CharSequence to the file read.
     * The encoding for reading files will be {@link #encoding}.
     * @param file File to read.
     * @return Contents of the file.
     * @throws IOException In case of I/O problems.
     */
    @NotNull public CharSequence readFile(@NotNull final File file) throws IOException {
        final InputStream in = new FileInputStream(file);
        try {
            return read(in);
        } finally {
            in.close();
        }
    }

    /** Writes a CharSequence into a (text) file.
     * @param file File to write.
     * @param text CharSequence to write.
     * @throws IOException In case of I/O problems.
     */
    public void writeFile(@NotNull final File file, @NotNull final CharSequence text) throws IOException {
        if (verbose) {
            System.err.print("File : " + file);
            System.err.flush();
        }
        if (dryRun) {
            System.err.println();
            return;
        }
        if (force && !file.canWrite()) {
            makeWritable(file);
        }
        final Writer out = new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(file)), encoding);
        try {
            out.append(text);
            out.flush();
            if (verbose) {
                System.err.println("\rChanged file: " + file);
            }
        } finally {
            out.close();
        }
    }

    /** Copies a file into another.
     * @param src File from which to copy.
     * @param dest File to copy to.
     * @throws IOException in case of I/O problems.
     */
    private void copyFile(@NotNull final File src, @NotNull final File dest) throws IOException {
        if (dryRun) {
            return;
        }
        final InputStream in = new FileInputStream(src);
        try {
            final OutputStream out = new FileOutputStream(dest);
            try {
                copy(in, out);
            } finally {
                out.close();
            }
        } finally {
            in.close();
        }
    }

    /** Copies a stream to another.
     * @param in Stream from which to copy.
     * @param out Stream to copy to.
     * @throws IOException in case of I/O problems.
     */
    private static void copy(@NotNull final InputStream in, @NotNull final OutputStream out) throws IOException {
        final byte[] buf = new byte[BUF_SIZE];
        //noinspection NestedAssignment
        for (int bytesRead; (bytesRead = in.read(buf, 0, BUF_SIZE)) != -1;) {
            out.write(buf, 0, bytesRead);
        }
        out.flush();
    }

    /** Makes a file writable.
     * If no command is defined, this is simply done by trying to change the file's access permissions.
     * If a command is defined, the command will be executed.
     * @param file File to make writable.
     * @throws IOException In case of I/O problems when trying to make the file writable.
     */
    private void makeWritable(@NotNull final File file) throws IOException {
        if (dryRun) {
            return;
        }
        final String makeWritableCommand = this.makeWritableCommand;
        if (makeWritableCommand != null) {
            final String cmd = makeWritableCommand.replaceAll("(?<!%)%f", file.toString());
            if (verbose) {
                System.err.println("\rExecuting \"" + cmd + "\" to make " + file + " writable.");
            }
            final Process p = Runtime.getRuntime().exec(cmd);
            copy(p.getInputStream(), System.err);
            copy(p.getErrorStream(), System.err);
            System.err.flush();
            try {
                p.waitFor();
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
            System.err.println(p.exitValue());
        } else {
            if (!file.setWritable(true)) {
                System.err.println("Couldn't make " + file + " writable.");
            }
        }
    }

    /** Sets the command to make a file writable.
     * @param makeWritableCommand Command to make a file writable.
     */
    @Option({"writeCmd"})
    public void setMakeWritableCommand(@NotNull final String makeWritableCommand) {
        this.makeWritableCommand = makeWritableCommand;
    }

    /** Sets or unsets the force option.
     * @param force Value of the force option.
     */
    @Option({"f", "force"})
    public void setForce(final boolean force) {
        this.force = force;
    }

    /** Returns whether or not the force option is set.
     * @return <code>true</code> if the force option is set, otherwise <code>false</code>.
     */
    public boolean isForce() {
        return force;
    }

    /** Sets or unsets the verbose option.
     * @param verbose Value of the verbose option.
     */
    @Option({"v", "verbose"})
    public void setVerbose(final boolean verbose) {
        this.verbose = verbose;
    }

    /** Returns whether or not the verbose option is set.
     * @return <code>true</code> if the verbose option is set, otherwise <code>false</code>.
     */
    public boolean isVerbose() {
        return verbose;
    }

    /** Sets or unsets the dry-run option.
     * @param dryRun Value of the dry-run option.
     */
    @Option({"n", "dry-run"})
    public void setDryRun(final boolean dryRun) {
        this.dryRun = dryRun;
    }

    /** Returns whether or not the dry-run option is set.
     * @return <code>true</code> if the dry-run option is set, otherwise <code>false</code>.
     */
    public boolean isDryRun() {
        return dryRun;
    }

    /** Sets or unsets the recurse option.
     * @param recursive Value of the recurse option.
     */
    @Option({"r", "recurse"})
    public void setRecursive(final boolean recursive) {
        this.recursive = recursive;
    }

    /** Returns whether or not the recurse option is set.
     * @return <code>true</code> if the recurse option is set, otherwise <code>false</code>.
     */
    public boolean isRecursive() {
        return recursive;
    }

    /** Sets or unsets the backup option.
     * @param backup Value of the backup option.
     */
    @Option({"b", "backup"})
    public void setBackup(final boolean backup) {
        this.backup = backup;
    }

    /** Returns whether or not the backup option is set.
     * @return <code>true</code> if the backup option is set, otherwise <code>false</code>.
     */
    public boolean isBackup() {
        return backup;
    }

    /** Sets the extension for backup files.
     * @param backupExtension File extension for backup files.
     */
    @Option({"backupExtension"})
    public void setBackupExtension(@NotNull final String backupExtension) {
        this.backupExtension = backupExtension;
    }

    /** Returns the extension for backup files.
     * @return The extension for backup files.
     */
    @NotNull public String getBackupExtension() {
        return backupExtension;
    }

    /** Adds a substitution.
     * @param substitution Substitution to add
     */
    @Option({"s", "substitution"})
    public void addSubstitution(@NotNull final String substitution) {
        substitutions.add(new Substitution(substitution));
    }

    /** Returns the substitutions as unmodifiable collection.
     * @return Unmodifiable collection with substitutions.
     */
    @NotNull public Collection<Substitution> getSubstitutions() {
        //noinspection ReturnOfCollectionOrArrayField
        return unmodifiableSubstitutions;
    }

    /** Adds substitutions from a file.
     * @param filename Filename of the file to read substitutions from.
     * @throws IOException in case of I/O problems.
     */
    @Option({"i", "input"})
    public void addSubstitutionsFromFile(@NotNull final String filename) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(filename), encoding));
        try {
            //noinspection NestedAssignment
            for (String line; (line = in.readLine()) != null;) {
                addSubstitution(line);
            }
        } finally {
            in.close();
        }
    }

    /** Sets the encoding to use for reading and writing files.
     * @param charsetName Name of the charset to use as encoding for reading and writing files.
     */
    @Option({"e", "encoding"})
    public void setEncoding(@NotNull final String charsetName) {
        encoding = Charset.forName(charsetName);
    }

    /** Returns the currently set encoding for reading and writing files.
     * @return The currently set encoding for reading and writing files.
     */
    @NotNull public String getEncoding() {
        return encoding.name();
    }

    /** Lists available encodings for reading and writing files. */
    @Option(type = OptionType.TERMINAL, value = {"listEncodings"})
    public void listEncodings() {
        final Formatter format = new Formatter(System.err);
        final Map<String, Charset> availableCharsets = Charset.availableCharsets();
        //SortedSet because it is important that they are sorted.
        //noinspection TypeMayBeWeakened
        final SortedSet<Charset> charsetsByDisplayname = new TreeSet<Charset>(new CharsetDisplaynameComparator());
        charsetsByDisplayname.addAll(availableCharsets.values());
        for (final Charset charset : charsetsByDisplayname) {
            format.format("%s (Aliases:", charset.name());
            for (final String alias : charset.aliases()) {
                format.format(" %s", alias);
            }
            format.format(")%n");
        }
        format.flush();
    }

    /** Prints help about the supported flags. */
    @Option(type = OptionType.TERMINAL, value = {"helpFlags"})
    public void helpFlags() {
        final Formatter format = new Formatter(System.err);
        for (final Character c : new TreeSet<Character>(Substitution.getFlagMap().keySet())) {
            format.format("%s %s%n", c, getString("flag." + c));
        }
        format.flush();
    }

    /** Prints some more examples. */
    @Option(type = OptionType.TERMINAL, value = {"helpExamples"})
    public void helpExamples() {
        final Formatter format = new Formatter(System.err);
        format.format(getString("examples"));
        format.flush();
    }
}
