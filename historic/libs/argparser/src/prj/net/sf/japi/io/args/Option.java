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

import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.METHOD;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 * Annotation to mark a method as command argument method.
 * <p>
 * Examples:
 * {@listing java @Option("r")
 * public void recurse() {
 *     recursive = true;
 * }
 *
 * @Option("recursive")
 * public void setRecursive(final boolean recursive) {
 *     this.recursive = recursive;
 * }
 *
 * @Option(type = OptionType.TERMINAL, value = {"listEncodings"})
 * public void listEncodings() {
 *     for (final String encoding : encodings) {
 *          System.out.println(encoding);
 *     }
 * }
 * }
 * The commands derived from {@link CommandWithHelp} (incl. {@link BasicCommand}) support reading help descriptions from {@link ResourceBundle}s.
 * For that, the package of the command implementation needs a {Resourcebundle} with the same basename as the command class.
 * A properties file which matches above example could look like this:
 * {@listing properties recurse=Recurse into subdirectories.
 * setRecursive=Set whether or not to recurse into subdirectories.
 * listEncodings=List the encodings and exit.}
 *
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.2
 *
 * @see OptionType for the supported types of options.
 */
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface Option {

    /**
     * The option type.
     * Default is {@link OptionType#OPTIONAL}.
     * Normally you wouldn't change this.
     * @return option type
     */
    OptionType type() default OptionType.OPTIONAL;

    /**
     * The option names.
     * Usually this is two Strings, a single letter and a descriptive String.
     * Multiple descriptive Strings as well as no single letter String are allowed.
     * It is an error to use an empty array.
     * It is an error to use "W".
     * @note The supplied string values MUST consist of ASCII-letters only (match regex <code>[a-zA-Z]+</code>).
     * @note Once a String is in the value array of an Option, it shouldn't be removed easily.
     *       Think about the users of the Command.
     *       They will demand a certain stability from the Command's user interface.
     * @return option names
     */
    String[] value();

    /**
     * The option key, used for i18n/l10n.
     * Default is <code>""</code> (empty String) which is interpreted as the associated method's name being the key.
     * @return option key
     */
    // Cannot be null instead of "" because null is not interpreted as a constant here.
    String key() default "";

} // @interface Option
