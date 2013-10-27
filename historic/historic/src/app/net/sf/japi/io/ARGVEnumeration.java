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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.NoSuchElementException;
import net.sf.japi.util.ThrowableHandler;
import org.jetbrains.annotations.Nullable;

/** Implementation of {@link Enumeration} for ARGV.
 * Used by {@link ARGVInputStream} and {@link ARGVReader}.
 * This class looks as if it could instead be an Iterator, but it is intended for use with {@link SequenceInputStream}.
 * @author <a href="mailto:chris@riedquat.de">Christian Hujer</a>
 * @see ARGVInputStream
 * @see ARGVReader
 */
public class ARGVEnumeration implements Enumeration<InputStream> {

    /** Command line arguments. */
    private final String[] args;

    /** Index. */
    private int index;

    /** Current InputStream. */
    private InputStream currentStream;

    /** Next InputStream. */
    @Nullable private InputStream nextStream;

    /** Current filename. */
    private String currentFilename;

    /** Next filename. */
    private String nextFilename;

    /** ThrowableHandlers. */
    private final List<ThrowableHandler<? super FileNotFoundException>> handlers = new ArrayList<ThrowableHandler<? super FileNotFoundException>>();

    /** Create an ARGVEnumeration.
     * @param args Command line arguments or some other String array containing 0 or more file names.
     */
    public ARGVEnumeration(final String... args) {
        this.args = args.clone();
        if (args.length == 0) {
            nextStream = System.in;
        } else {
            nextStream();
        }
    }

    /** Switch to the next stream. */
    private void nextStream() {
        if (currentStream != null) {
            try {
                currentStream.close();
            } catch (final IOException ignore) {
                /* ignore. */
            }
        }
        //noinspection InstanceVariableUsedBeforeInitialized
        currentStream = nextStream;
        currentFilename = nextFilename;
        nextStream = null;
        while (index < args.length && nextStream == null) {
            try {
                //noinspection IOResourceOpenedButNotSafelyClosed,NestedAssignment
                nextStream = new FileInputStream(nextFilename = args[index++]);
            } catch (final FileNotFoundException e) {
                log(e);
            }
        }
    }

    /** Log an exception.
     * Currently does nothing but tell the registered exception handlers to handle the exception.
     * @param e Exception to log
     */
    protected void log(final FileNotFoundException e) {
        for (final ThrowableHandler<? super FileNotFoundException> handler : handlers) {
            handler.handleThrowable(e);
        }
    }

    /** Create an ARGVEnumeration.
     * @param handler ThrowableHandler to add
     * @param args Command line arguments or some other String array containing 0 or more file names.
     */
    public ARGVEnumeration(final ThrowableHandler<? super FileNotFoundException> handler, final String... args) {
        addThrowableHandler(handler);
        this.args = args.clone();
        if (args.length == 0) {
            nextStream = System.in;
        } else {
            nextStream();
        }
    }

    /** Register an exception handler.
     * @param handler New ThrowableHandler
     */
    public void addThrowableHandler(final ThrowableHandler<? super FileNotFoundException> handler) {
        handlers.add(handler);
    }

    /** Get the name of the current file.
     * @return name of the current file
     */
    public String getCurrentFilename() {
        return currentFilename;
    }

    /** @see Enumeration */
    public boolean hasMoreElements() {
        return nextStream != null;
    }

    /** @see Enumeration
     * Elements returned are InputStreams.
     */
    public InputStream nextElement() {
        if (hasMoreElements()) {
            nextStream();
            return currentStream;
        } else {
            throw new NoSuchElementException();
        }
    }

    /** Unregister an exception handler.
     * @param handler ThrowableHandler to be removed
     */
    public void removeThrowableHandler(final ThrowableHandler<? super FileNotFoundException> handler) {
        handlers.remove(handler);
    }

} // class ARGVEnumeration
