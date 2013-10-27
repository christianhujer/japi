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

package net.sf.japi.progs.jhexview;

import net.sf.japi.io.args.ArgParser;
import net.sf.japi.swing.app.AppLaunchCommand;
import net.sf.japi.swing.app.Application;
import net.sf.japi.swing.app.Document;
import org.jetbrains.annotations.NotNull;

/**
 * TODO
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class JHexViewApplication extends Application<byte[]> {

    /** Main program.
     * @param args Command line arguments (try --help).
     */
    public static void main(final String... args) {
        ArgParser.simpleParseAndRun(new AppLaunchCommand(JHexViewApplication.class), args);
    }

    /** Creates a JHexViewApplication. */
    public JHexViewApplication() {
    }

    /** {@inheritDoc} */
    @Override
    public void save(@NotNull final Document<byte[]> doc, @NotNull final String uri) {
        // TODO:2009-02-22:christianhujer:Implement.
    }

    public Document<byte[]> createNew() {
        // TODO:2009-02-22:christianhujer:Implement.
        return null;
    }

    /** {@inheritDoc} */
    @NotNull
    public Document<byte[]> load(@NotNull final String uri) {
        throw new UnsupportedOperationException();
    }
}
