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

package net.sf.japi.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/** An ARGV Reader.
 * Just to make life a bit less painful to Perl programmers that were reborn as Java programmers.
 * <p />
 * Don't rely on this class being a subclass of {@link BufferedReader}.
 * That is subject to change.
 * But you can rely on this class retaining all important methods (like <code>readLine()</code>, for instance).
 * <p />
 * An <code>ARGVReader</code> provides sequential access to one or more files.
 * To create an <code>ARGVReader</code> that is just a {@link BufferedReader} on {@link System#in}, just pass an empty
 * String array to its constructor.
 * To create an <code>ARGVReader</code> that sequentially accesss one file after another (like <code>&lt;ARGV&gt;</code> does in Perl),
 * pass an array with the desired filenames to its constructor.
 * <p />
 * Usually, you'd use <code>ARGVReader</code> like this:
 * <pre>
 * // Sort in a similar way the UNIX command sort works like
 * public class Sort {
 *     public static void main(final String... args) throws IOException {
 *         final ARGVReader in = new ARGVReader(args);
 *         final List&lt;String&gt; lineList = new ArrayList&lt;String&gt;();
 *         for (String line; (line = in.readLine()) != null; ) {
 *             lineList.append(line);
 *         }
 *         Collections.sort(lineList);
 *         for (final String line : lineList) {
 *             System.out.println(lines[i]);
 *         }
 *     }
 * }
 * </pre>
 * Internally this class uses {@link ARGVInputStream}, which uses {@link ARGVEnumeration} to sequentially access the Stream elements of ARGV.
 * @note it is not required to invoke {@link #close()}.
 * @author <a href="mailto:chris@riedquat.de">Christian Hujer</a>
 * @see ARGVInputStream
 * @see ARGVEnumeration
 */
public class ARGVReader extends BufferedReader {

    /** The ARGVInputStream to read from. */
    private final ARGVInputStream argvInputStream;

    /** Create an ARGVReader.
     * @param args Command line arguments or some other String array containing 0 or more file names.
     */
    @SuppressWarnings({"IOResourceOpenedButNotSafelyClosed"})
    public ARGVReader(final String... args) {
        this(new ARGVInputStream(args));
    }

    /** Create an ARGVReader.
     * @param argvInputStream ARGVInputstream to read from
     */
    @SuppressWarnings({"IOResourceOpenedButNotSafelyClosed"})
    private ARGVReader(final ARGVInputStream argvInputStream) {
        super(new InputStreamReader(argvInputStream));
        this.argvInputStream = argvInputStream;
    }

    /** Get the name of the current file.
     * @return name of the current file
     */
    public String getCurrentFilename() {
        return argvInputStream.getCurrentFilename();
    }

} // class ARGVReader
