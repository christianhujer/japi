/*
 * Copyright (C) 2009  Christian Hujer.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package net.sf.japi.swing.app;

import java.util.List;
import net.sf.japi.io.args.CommandWithHelp;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** Command for launching a GUI application.
 * If the application does not need additional command line parameters, this class is sufficient and needn't be subclassed.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class AppLaunchCommand extends CommandWithHelp {

    /** The Application Class. */
    @Nullable private final Class<? extends Application<?>> appClass;

    /** The Application. */
    @Nullable private final Application<?> application;

    /** Creates an AppLaunchCommand.
     * @param appClass Application Class to launch.
     */
    public AppLaunchCommand(@NotNull final Class<? extends Application<?>> appClass) {
        this.appClass = appClass;
        application = null;
    }

    /** Creates an AppLaunchCommand.
     * @param application Application to launch.
     */
    public AppLaunchCommand(@NotNull final Application<?> application) {
        appClass = null;
        this.application = application;
    }

    @SuppressWarnings({"InstanceMethodNamingConvention"})
    public int run(@NotNull final List<String> args) throws Exception {
        Application<?> application = this.application;
        assert !(application == null && appClass == null);
        if (application == null) {
            application = appClass.newInstance();
        }

        for (final String arg : args) {
            application.load(arg);
        }
        application.show();
        return 0;
    }
}
