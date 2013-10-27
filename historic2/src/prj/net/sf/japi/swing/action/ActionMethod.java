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

package net.sf.japi.swing.action;

import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.METHOD;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import org.jetbrains.annotations.NotNull;

/** Annotation for methods that are Actions.
 * {@link ActionBuilder} in future will automatically configure and store Actions with properties for methods that are annotated with this Annotation.
 * In future, this Annotation might get some attributes, but currently it hasn't got any.
 * There is no guarantee for future attributes to be optional.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
@Documented
@Inherited
@Retention(RUNTIME)
@Target(METHOD)
public @interface ActionMethod {

    /**
     * Determines the Action key.
     * Defaults to the method name.
     * @return The Action key for this ActionMethod or <code>""</code> if the method name should be used.
     */
    @NotNull String value() default "";

} // @interface ActionMethod
