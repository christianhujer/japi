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

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.jetbrains.annotations.NotNull;

/**
 * The type of an {@link Option}.
 * It controls the behaviour of an {@link Option} when {@link ArgParser} parses a {@link Command}.
 * <h4>Usage example</h4>
 * <pre>
 * public class MyCommand extends {@link BasicCommand} {
 *     public static void main(final String... args) {
 *         {@link ArgParser}.{@link ArgParser#simpleParseAndRun(Command, String[]) simpleParseAndRun}(new MyCommand(), args);
 *     }
 *     &#x2f;** {&#x40;inheritDoc} *&#x2f;
 *     &#x40;Option
 *     public void run(final List&lt;String&gt; args) {
 *     }
 * }
 * </pre>
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.2
 */
public enum OptionType {

    /**
     * Optional options may be omitted.
     * This is the default and usually doesn't need to be changed.
     */
    OPTIONAL,

    /**
     * Required options must always be specified prior to command execution.
     * Please use required options sparingly - if possible none at all.
     * Instead provide your program with reasonable defaults.
     */
    REQUIRED,

    /**
     * Terminal options terminate argument parsing, no matter what happens.
     * This is only for special options like --help.
     * Normally there should be no need for you to declare your own terminal options.
     */
    TERMINAL;

    /**
     * Returns the localized name of this OptionType in the default locale if available, otherwise the lowercase enum constant name.
     * @return The localized name of this OptionType.
     */
    @NotNull public String getName() {
        String name;
        try {
            name = ResourceBundle.getBundle("net.sf.japi.io.args.messages").getString(getClass().getName() + "." + name());
        } catch (final MissingResourceException ignore) {
            name = name();
        }
        return name;
    }

    /**
     * Returns the localized name of this OptionType in the specified locale if available, otherwise the lowercase enum constant name.
     * @param locale Locale
     * @return The localized name of this OptionType.
     */
    @NotNull public String getName(@NotNull final Locale locale) {
        String name;
        try {
            name = ResourceBundle.getBundle("net.sf.japi.io.args.messages", locale).getString(getClass().getName() + "." + name());
        } catch (final MissingResourceException ignore) {
            name = name();
        }
        return name;
    }

    /**
     * {@inheritDoc}
     * Returns the same as {@link #getName()}.
     */
    @NotNull @Override public String toString() {
        return getName();
    }

    /**
     * Returns the command line description of this option type.
     * @return The command line description of this option type.
     */
    @NotNull public String getDescription() {
        String description;
        try {
            description = ResourceBundle.getBundle("net.sf.japi.io.args.messages").getString(getClass().getName() + "." + name() + ".description");
        } catch (final MissingResourceException ignore) {
            description = name();
        }
        return description.length() == 0 ? description : " (" + description + ")";
    }

    /**
     * Returns the command line description of this option type.
     * @param locale Locale
     * @return The command line description of this option type.
     */
    @NotNull public String getDescription(@NotNull final Locale locale) {
        String description;
        try {
            description = ResourceBundle.getBundle("net.sf.japi.io.args.messages", locale).getString(getClass().getName() + "." + name() + ".description");
        } catch (final MissingResourceException ignore) {
            description = name();
        }
        return description.length() == 0 ? description : " (" + description + ")";
    }

} // OptionType
