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

package net.sf.japi.progs.jeduca.jtest.gui;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/** FileFilter for JFileChooser that accepts only KEduca files.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class KEducaFileFilter extends FileFilter {

    /** {@inheritDoc} */
    @Override public boolean accept(final File f) {
        //noinspection StringToUpperCaseOrToLowerCaseWithoutLocale
        return f.getName().toLowerCase().endsWith(".edu");
    }

    /** {@inheritDoc} */
    @Override public String getDescription() {
        return "KEduca File (ohne Verz.)";
    }

} // class KEducaFileFilter
