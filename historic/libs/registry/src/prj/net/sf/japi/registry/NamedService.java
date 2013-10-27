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

package net.sf.japi.registry;

import java.util.Locale;
import org.jetbrains.annotations.NotNull;

/**
 * A named service can be instantiated with a public non-arg (default) constructor and provides two names.
 * The first name is a technical name, suitable for locale <code>C_TYPE</code>, that's a language independent name.
 * If there is no language independent name, the English name is used.
 * The second name is a descriptive name, suitable for
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public interface NamedService {

    /**
     * Returns a technical name, suitable for locale <code>C_TYPE</code>, command line arguments etc..
     * The returned name might be the same as the name returned by {@link #getDisplayName()} for English.
     * @return A technical name.
     */
    @NotNull String getName();

    /**
     * Returns a display name for the default locale.
     * @return A display name.
     */
    @NotNull String getDisplayName();

    /**
     * Returns a display name for the specified locale.
     * @param locale Locale to get display name for.
     * @return A display name for the specified locale.
     */
    @NotNull String getDisplayName(@NotNull Locale locale);

} // interface NamedService
