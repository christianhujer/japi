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

package net.sf.japi.swing;

/** Windows Look And Feel on UNIX.
 * This class extends the original WindowsLookAndFeel to circumveniate the checks that let it run on Windows only and makes it run on any other OS as well.
 * @author <a href="mailto:chris@riedquat.de">Christian Hujer</a>
 */
@SuppressWarnings({"ClassNameSameAsAncestorName"})
public class WindowsLookAndFeel extends com.sun.java.swing.plaf.windows.WindowsLookAndFeel {

    /** Serial version. */
    private static final long serialVersionUID = 1L;

    /** {@inheritDoc} */
    @Override public void initialize() {
        final String osVersion = System.getProperty("os.version");
        System.setProperty("os.version", "5.0");
        super.initialize();
        System.setProperty("os.version", osVersion);
    }

    /** {@inheritDoc} */
    @Override public boolean isSupportedLookAndFeel() {
        return true;
    }

} // class WindowsLookAndFeel
