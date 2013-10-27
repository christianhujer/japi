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

package net.sf.japi.io.args;

import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * Shell commands can implement this interface and make use of ArgParser.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.2
 * @see BasicCommand BasicCommand for a very convenient implementation of this interface
 */
public interface Command {

    /**
     * Run this command.
     * This method is invoked by {@link ArgParser} once it is finnished with parsing the arguments.
     * @param args the argument strings that were not parsed away by {@link ArgParser}.
     * @return return code suitable for passing to {@link System#exit(int)} (whether {@link System#exit(int)} is really invoked depends on the configuration of the {@link ArgParser}.)
     * @throws Exception in case of problems during command execution.
     */
    @SuppressWarnings({"InstanceMethodNamingConvention", "ProhibitedExceptionDeclared"})
    int run(@NotNull List<String> args) throws Exception;

    /**
     * Return whether after running this Command, {@link System#exit(int)} should be invoked.
     * @return <code>true</code> if {@link ArgParser} should invoke {@link System#exit(int)} after this command, otherwise <code>false</code>.
     */
    Boolean isExiting();

    /**
     * Return whether the check for required methods should be performed.
     * @return <code>true</code> if {@link ArgParser} should perform a check on required methods on this command, otherwise <code>false</code>.
     */
    boolean isCheckRequiredOptions();

} // interface Command
