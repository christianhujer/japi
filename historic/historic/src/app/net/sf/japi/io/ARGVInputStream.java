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

import java.io.SequenceInputStream;
import static net.sf.japi.util.PrintStreamThrowableHandler.STDERR;

/** An ARGV InputStream, behaving similar as <code>&lt;ARGV&gt;</code> in Perl.
 * Just to make life a bit less painful to Perl programmers that were reborn as Java programmers.
 * <p />
 * Don't rely on this class being a subclass of {@link SequenceInputStream}.
 * That is subject to change.
 * <p />
 * An <code>ARGVInputStream</code> provides sequential access to one or more files.
 * To create an <code>ARGVInputStream</code> that is just a synonym on {@link System#in}, just pass an empty String array to its
 * constructor.
 * To create an <code>ARGVInputStream</code> that sequentially accesss one file after another (similar as <code>&lt;ARGV&gt;</code> does
 * in Perl), pass an array with the desired filenames to its constructor.
 * <p />
 * Errors that happen due to files that cannot be opened are always reported to {@link System#err} and otherwise silently ignored, i.e. not reported to the main program.
 * <p />
 * Usually, you'd use <code>ARGVInputStream</code> like this:
 * <pre>
 * // Cat in a similar way the UNIX command cat works like
 * import java.io.IOException;
 * import net.sf.japi.io.ARGVInputStream;
 * import static net.sf.japi.io.IOHelper.copy;
 *
 * public class Cat {
 *     public static void main(final String... args) {
 *         try {
 *             copy(new ARGVInputStream(args), System.out);
 *         } catch (final IOException e) {
 *             System.err.println(e);
 *         }
 *     }
 * }
 * </pre>
 * Internally this class uses {@link ARGVEnumeration} to sequentially access the Stream elements of ARGV.
 * @note it is not required to invoke {@link #close()}.
 * @author <a href="mailto:chris@riedquat.de">Christian Hujer</a>
 * @see ARGVEnumeration
 */
public class ARGVInputStream extends SequenceInputStream {

    /** The ARGVEnumeration to use. */
    private final ARGVEnumeration argvEnumeration;

    /** Create an ARGVInputStream.
     * @param args Command line arguments or some other String array containing 0 or more file names.
     */
    public ARGVInputStream(final String... args) {
        this(new ARGVEnumeration(STDERR, args));
    }

    /** Create an ARGVInputStream.
     * @param argvEnumeration ARGVEnumeration to use
     */
    private ARGVInputStream(final ARGVEnumeration argvEnumeration) {
        super(argvEnumeration);
        this.argvEnumeration = argvEnumeration;
    }

    /** Get the name of the current file.
     * @return name of the current file
     */
    public String getCurrentFilename() {
        return argvEnumeration.getCurrentFilename();
    }

} // class ARGVInputStream
