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

package net.sf.japi.io.args.converter;

/**
 * Exception that is thrown in case no matching converter for a conversion was found.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.2
 */
public class NoConverterFoundException extends Exception {

    /** Serial version. */
    private static final long serialVersionUID = 1L;

    /**
     * The type for that no Converter was found.
     * @serial include
     */
    private final Class<?> targetType;

    /**
     * Create a NoConverterFoundException.
     * @param targetType Type for that no Converter was found.
     */
    public NoConverterFoundException(final Class<?> targetType) {
        this.targetType = targetType;
    }

    /**
     * Returns the type for that no Converter was found.
     * @return The type for that no Converter was found.
     */
    public Class<?> getTargetType() {
        return targetType;
    }

} // class NoConverterFoundException
