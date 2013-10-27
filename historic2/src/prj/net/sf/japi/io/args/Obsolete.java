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
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.METHOD;

/**
 * An Obsolete {@link Option} is an Option that is no longer supported.
 * Instead of being completely removed, obsolete options should be retained in commands for a long period of time to give users a chance of adopting their scripts to the new version of the command.
 * The methods of obsolete options may perform no action.
 * The argument parser will take care of warning the user about the usage of an obsolete option.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.2
 * @todo eventually make this an attribute of {@link Option} instead.
 */
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface Obsolete {

}
