/* JAPI - (Yet another (hopefully) useful) Java API
 *
 * Copyright (C) 2004-2006 Christian Hujer
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package net.sf.japi.lang;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/** Class for working with Properties.
 * @author <a href="mailto:chris@riedquat.de">Christian Hujer</a>
 */
@SuppressWarnings({"UtilityClass"})
public class Properties {

    /** Get a property of an object.
     * @param object Object of which to get a property as String.
     * @param propertyName Name of the property to get.
     * @return Value of <var>propertyName</var> of <var>object</var> as String.
     * @see #setPropertyFromString(Object, String, String)
     */
    public static String getPropertyAsString(final Object object, final String propertyName) {
        final String getterName = new StringBuilder().append("get").append(propertyName.substring(0, 1).toUpperCase()).append(propertyName.substring(1)).toString();
        final String isserName = new StringBuilder().append("is").append(propertyName.substring(0, 1).toUpperCase()).append(propertyName.substring(1)).toString();
        Method getter = null;
        for (final Method method : object.getClass().getMethods()) {
            final Class<?>[] parameterTypes = method.getParameterTypes();
            final String methodName = method.getName();
            if (parameterTypes.length == 0 && (methodName.equals(getterName) || methodName.equals(isserName))) {
                getter = method;
            }
        }
        if (getter == null) {
            throw new NoSuchMethodError(getterName + '|' + isserName);
        }
        try {
            return getter.invoke(object).toString();
        } catch (final IllegalAccessException e) {
            throw new NoSuchMethodError(getterName + '|' + isserName + ": " + e);
        } catch (final InvocationTargetException e) {
            throw new NoSuchMethodError(getterName + '|' + isserName + ": " + e);
        }
    }

    /** Set a property of an object.
     * @param object Object to invoke setter on
     * @param propertyName Name of the property to get setter for
     * @param value Value to set as String (conversions from {@link java.beans} are used)
     * @see #getPropertyAsString(Object, String)
     */
    @SuppressWarnings({"IfStatementWithTooManyBranches", "ObjectEquality", "StringToUpperCaseOrToLowerCaseWithoutLocale", "OverlyComplexMethod"})
    public static void setPropertyFromString(final Object object, final String propertyName, final String value) {
        final String setterName = new StringBuilder().append("set").append(propertyName.substring(0, 1).toUpperCase()).append(propertyName.substring(1)).toString();
        Method setter = null;
        Object rawValue = null;
        for (final Method method : object.getClass().getMethods()) {
            final Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length == 1 && method.getName().equals(setterName)) {
                final Class<?> parameterType = parameterTypes[0];
                setter = method;
                if (parameterType == Boolean.TYPE) {
                    rawValue = Boolean.valueOf(value);
                } else if (parameterType == Character.TYPE) {
                    rawValue = value.charAt(0);
                } else if (parameterType == Byte.TYPE) {
                    rawValue = Byte.valueOf(value);
                } else if (parameterType == Short.TYPE) {
                    rawValue = Short.valueOf(value);
                } else if (parameterType == Integer.TYPE) {
                    rawValue = Integer.valueOf(value);
                } else if (parameterType == Long.TYPE) {
                    rawValue = Long.valueOf(value);
                } else if (parameterType == Float.TYPE) {
                    rawValue = Float.valueOf(value);
                } else if (parameterType == Double.TYPE) {
                    rawValue = Double.valueOf(value);
                } else {
                    rawValue = value;
                }
            }
        }
        if (setter == null) {
            throw new NoSuchMethodError(setterName);
        }
        try {
            setter.invoke(object, rawValue);
        } catch (final IllegalAccessException e) {
            throw new NoSuchMethodError(setterName + ": " + e);
        } catch (final InvocationTargetException e) {
            throw new NoSuchMethodError(setterName + ": " + e);
        }
    }

    /** Private constructor for Utility class. */
    private Properties() {
    }

} // class Properties
