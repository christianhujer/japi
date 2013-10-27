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

package net.sf.japi.lang;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An Iterator for iterating through the superclasses (subclasses to superclasses) of a class.
 * Note: The supplied class is included in iteration. If you want to omit it, you'll have to invoke getSuperclass() once, e.g. use <code>new SuperClassIterator(clazz.getSuperClass())</code> instead of <code>new SuperClassIterator(clazz)</code>.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class SuperClassIterator implements Iterator<Class<?>>, Serializable {

    /** Serial Version. */
    private static final long serialVersionUID = 7437083475483387420L;

    /** The current class.
     * @serial include
     */
    @Nullable private Class<?> nextClass;

    /**
     * Create a SuperClassIterator.
     * @param clazz Class to create Iterator for
     */
    public SuperClassIterator(@Nullable final Class<?> clazz) {
        nextClass = clazz;
    }

    /** {@inheritDoc} */
    public boolean hasNext() {
        return nextClass != null;
    }

    /** {@inheritDoc} */
    @NotNull public Class<?> next() {
        if (nextClass == null) {
            throw new NoSuchElementException();
        }
        try {
            return nextClass;
        } finally {
            nextClass = nextClass.getSuperclass();
        }
    }

    /** {@inheritDoc} */
    public void remove() {
        throw new UnsupportedOperationException();
    }

} // class ClassIterator
