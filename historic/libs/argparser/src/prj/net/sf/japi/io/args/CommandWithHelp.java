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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import org.jetbrains.annotations.NotNull;

/** CommandWithHelp is a Command which implements a help option.
 * If you're looking for the best gui command base class, this probably is suited for you.
 * If you're looking for the best cmd command base class, better go for {@link BasicCommand}.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.2
 */
public abstract class CommandWithHelp implements Command {

    /** The ResourceBundle for locale-specific output of this class. */
    @NotNull private final ResourceBundle ownBundle = ResourceBundle.getBundle("net.sf.japi.io.args.messages");

    /** Help Option. */
    // TODO:2009-02-15:christianhujer:Determine terminal width and perform automatic line wrapping.
    @Option(type = OptionType.TERMINAL, value = {"h", "help"})
    public void help() {
        final Set<Method> optionMethods = ArgParser.getOptionMethods(this);
        int maxLong = 0;
        int maxShort = 0;
        for (final Method optionMethod : optionMethods) {
            final Option option = optionMethod.getAnnotation(Option.class);
            final String[] names = option.value();
            int currentLong = 0;
            int currentShort = 0;
            for (final CharSequence name : names) {
                if (name.length() > 1) {
                    currentLong += name.length() + ", --".length();
                } else {
                    currentShort += name.length() + ", -".length();
                }
            }
            maxLong = Math.max(maxLong, currentLong - ", ".length());
            maxShort = Math.max(maxShort, currentShort - ", ".length());
        }
        final String formatString = "%-" + maxShort + "s%s%-" + maxLong + "s: %s%s%n";
        final Formatter format = new Formatter(System.err);
        format.format(getHelpHeader());
        format.format("%n");
        //Sorted output is nicer for the user. Type not weakened to document that.
        //noinspection TypeMayBeWeakened
        final SortedSet<Method> sortedMethods = new TreeSet<Method>(MethodOptionComparator.INSTANCE);
        sortedMethods.addAll(optionMethods);
        for (final Method optionMethod : sortedMethods) {
            final Option option = optionMethod.getAnnotation(Option.class);
            final OptionType optionType = option.type();
            final String[] names = option.value();
            final List<String> shortNames = new ArrayList<String>();
            final List<String> longNames  = new ArrayList<String>();
            for (final String name : names) {
                if (name.length() > 1) {
                    longNames.add("--" + name);
                } else {
                    shortNames.add("-" + name);
                }
            }
            Collections.sort(shortNames);
            Collections.sort(longNames);
            final String delim = shortNames.size() > 0 && longNames.size() > 0 ? ", " : "  ";
            String description;
            try {
                final String optionKey = "".equals(option.key()) ? optionMethod.getName() : option.key();
                description = getString(optionKey);
            } catch (final MissingResourceException ignore) {
                description = "";
            }
            format.format(formatString, StringJoiner.join(", ", shortNames), delim, StringJoiner.join(", ", longNames), description, optionType.getDescription());
        }
        format.format("%n");
        format.format(getHelpFooter());
        format.flush();
    }

    /**
     * {@inheritDoc}
     * @see System#exit(int)
     */
    @NotNull public Boolean isExiting() {
        return false;
    }

    /** {@inheritDoc} */
    public boolean isCheckRequiredOptions() {
        return true;
    }

    /**
     * Get the ResourceBundle for the default locale.
     * If you override this method, the methods of BasicCommand will still use BasicCommand's own ResourceBundle as a backup.
     * That means overriding this method will not break any default behaviour of BasicCommand.
     * @return ResourceBundle for default locale.
     */
    @NotNull public ResourceBundle getBundle() {
        return ownBundle;
    }

    /**
     * Get the help header.
     * The default implementation returns a bundle String (see {@link #getBundle()}) for the key "helpHeader".
     * The default String for "helpHeader" is the empty String.
     * @return Help header.
     */
    @NotNull public String getHelpHeader() {
        final String helpHeader = getString("helpHeader");
        return helpHeader.endsWith("\n") ? helpHeader : helpHeader + "\n";
    }

    /**
     * Get the help footer.
     * The default implementation returns a bundle String (see {@link #getBundle()}) for the key "helpFooter".
     * The default String for "helpFooter" is the empty String.
     * @return Help footer.
     */
    @NotNull public String getHelpFooter() {
        final String helpFooter = getString("helpFooter");
        return helpFooter.endsWith("\n") ? helpFooter : helpFooter + "\n";
    }

    /**
     * Returns a String from the ResourceBundles.
     * This method first tries the subclass's ResourceBundle.
     * If that fails, the String is returned from BasicCommand's own resource bundle.
     * If that fails too, a MissingResourceException is thrown.
     * If that fails, the String is returned from the subclass's ResourceBundle.
     * @param key Name of the key to look up
     * @return String from the ResourceBundles.
     * @throws MissingResourceException In case all tries for retrieving a value for <var>key</var> failed.
     */
    @NotNull public String getString(@NotNull final String key) throws MissingResourceException {
        final ResourceBundle bundle = getBundle();
        if (bundle != ownBundle) {
            try {
                return getBundle().getString(key);
            } catch (final MissingResourceException ignore) {
                // not serious
            }
        }
        try {
            return ResourceBundle.getBundle(getClass().getName()).getString(key);
        } catch (final MissingResourceException ignore) {
            return ownBundle.getString(key);
        }
    }

    /** Print version information. */
    @Option(type = OptionType.TERMINAL, value = {"version"})
    public void version() {
        // TODO:2009-02-22:christianhujer:Implement this.
        System.err.println("Not yet implemented.");
    }

}
