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

package net.sf.japi.midilearn;

import java.util.Locale;
import java.util.ResourceBundle;
import org.jetbrains.annotations.NotNull;

/** An Interval, used for the {@link IntervalTrainer}.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class Interval {

    /** The distance of this interval, e.g. 0 for prime, 12 for octave. */
    private int distance;

    /** Returns the names of this interval in the current locale.
     * @return The names of this interval in the current locale.
     */
    public String[] getNames() {
        return ResourceBundle.getBundle("Interval").getString("I_" + distance + ".names").split(",");
    }

    /** Returns the names of this interval in the specified locale.
     * @param locale Locale in which to return the names.
     * @return The names of this interval in the specified locale.
     */
    public String[] getNames(@NotNull final Locale locale) {
        return ResourceBundle.getBundle("Interval", locale).getString("I_" + distance + ".names").split(",");
    }
}
