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

package net.sf.japi.log.def;

/**
 * Enumeration of log levels.
 * <p />
 * The default JAPI Logging knows seven logging levels:
 * <ol>
 *  <li>trace (the finest / least serious)</li>
 *  <li>debug</li>
 *  <li>verbose</li>
 *  <li>info</li>
 *  <li>warn</li>
 *  <li>error</li>
 *  <li>fatal (the most serious)</li>
 * </ol>
 * It's regarded a feature that JAPI Logging is simple and well-defined and only knows exactly these seven levels.
 * However JAPI Logging is modular enough to allow you to use your own definition of log levels instead of these default levels.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public enum Level {

    // The ordinal of the enums is used for determining the log level.
    // Therefore the order of these enum constants MUST NOT be changed.

    /**
     * Level for tracing.
     * This is the finest and least severe level.
     * It should be used for diagnostic messages that are usually only interesting for the program's developers.
     * Usually, TRACE logging is only used during certain periods of development and later removed from the code.
     */
    TRACE,

    /**
     * Level for debugging.
     * This level should be used for debugging and diagnostic messages that are also interesting if a user runs a program and thinks she encountered a bug.
     */
    DEBUG,

    /**
     * Level for verbose.
     * Example: a program loops over files and wouldn't normally inform the user when it opens them unless the user wants the program to be verbose.
     */
    VERBOSE,

    /**
     * Level for info messages.
     * Example: print program's version number at startup or statistical information after a run.
     */
    INFO,

    /**
     * Level for warning messages.
     * Warning messages denote exceptional situations that do not keep the program from continueing normal flow.
     * Example: A config file was expected to be there but not found when the program can continue with a default configuration.
     */
    WARN,

    /**
     * Level for error messages.
     * Error messages denote exceptional situations that stop a program's current action to be performed properly but are not expected to change a program's state that it couldn't continue with other operations normally.
     * Example: The user wanted to open a file but it couldn't be opened due to an IOException. The program cannot continue loading the desired file, but the user can still use the program e.g. try to open another file.
     */
    ERROR,

    /**
     * Level for fatal error messages.
     * Fatal messages denote exceptional situations that indicate that the program's state is disrupted so severe that it cannot continue normally.
     * Usually, a program exits after encountering a fatal error situation.
     */
    FATAL

} // enum Level
