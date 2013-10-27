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

import org.jetbrains.annotations.NotNull;

/**
 * BasicCommand is a base class for commands that provides the options --help, --exit and --noexit.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.2
 */
public abstract class BasicCommand extends CommandWithHelp {

    /**
     * Whether to exit or not.
     * @see Command#isExiting()
     */
    private boolean exiting;

    /**
     * Whether to check for required options.
     * @see Command#isCheckRequiredOptions()
     */
    private boolean checkRequiredOptions = true;

    /** Create a BasicCommand. */
    protected BasicCommand() {
    }

    /**
     * {@inheritDoc}
     * @see System#exit(int)
     * @see #setExiting(Boolean)
     */
    @Override @NotNull public Boolean isExiting() {
        return exiting;
    }

    /**
     * Exit Option.
     * Normally you wouldn't override this method.
     * The default behaviour is to not exit.
     * This prevents {@link System#exit(int)} from being invoked when BasicCommand resp. ArgParser is invoked from a running VM.
     * If you invoke a command from a batch, setting the <code>exiting</code> property to <code>true</code> makes sense.
     * This usually should always be determined by the user of a program, not the programmer.
     * @param exiting <code>true</code> if {@link System#exit(int)} should be invoked, otherwise <code>false</code>.
     * @see System#exit(int)
     * @see #isExiting()
     */
    @Option(value = {"exit"})
    public void setExiting(@NotNull final Boolean exiting) {
        this.exiting = exiting;
    }

    /** {@inheritDoc} */
    @Override public boolean isCheckRequiredOptions() {
        return checkRequiredOptions;
    }

    /** Sets whether the check for required options should be performed.
     * @param checkRequiredOptions <code>true</code> if the check for required options should be performed, otherwise <code>false</code>.
     */
    public void setCheckRequiredOptions(final boolean checkRequiredOptions) {
        this.checkRequiredOptions = checkRequiredOptions;
    }

} // class BasicCommand
