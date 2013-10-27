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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sun.misc.Service;

/**
 * Lookup mechanism for {@link NamedService} implementations.
 * The current implementation for Java 1.5 is based on {@link Service}.
 * Future implementations (Java 1.6+) will be based on the corresponding replacement.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public final class NamedRegistry {

    /** Utility class - don't instanciate. */
    private NamedRegistry() {
    }

    /**
     * Returns the implementation of the supplied {@link NamedService} with the specified name.
     * @param service NamedService to get
     * @param name Name of the implementation of the NamedService to get
     * @return Implementation or <code>null</code> if none found.
     */
    @Nullable public static <T extends NamedService> T getInstance(@NotNull final Class<T> service, @NotNull final String name) {
        for (final Iterator<T> it = instanceIterator(service); it.hasNext();) {
            final T t = it.next();
            if (t.getName().equals(name)) {
                return t;
            }
        }
        return null;
    }

    /**
     * Returns all implementations of the supplied class.
     * The returned list is yours, you may do with it whatever you like.
     * It's not cached or so.
     * Because this method is anonymous (not bound to names), it can be used for any kind of service, not just implementations of {@link NamedService}.
     * @param service NamedService to get implementations for.
     * @return List with all implementations, empty collection if none found.
     */
    @NotNull public static <T> Collection<T> getAllInstances(@NotNull final Class<T> service) {
        final List<T> instances = new ArrayList<T>();
        for (final Iterator<T> it = instanceIterator(service); it.hasNext();) {
            instances.add(it.next());
        }
        return instances;
    }

    /**
     * Returns the first implementation of the supplied {@link NamedService} with the specified name using the specified ClassLoader.
     * @param service NamedService to get
     * @param name Name of the implementation of the NamedService to get
     * @param classLoader ClassLoader to use.
     * @return Implementation or <code>null</code> if none found.
     */
    @Nullable public static <T extends NamedService> T getInstance(@NotNull final Class<T> service, @NotNull final String name, @NotNull final ClassLoader classLoader) {
        for (final Iterator<T> it = instanceIterator(service, classLoader); it.hasNext();) {
            final T t = it.next();
            if (t.getName().equals(name)) {
                return t;
            }
        }
        return null;
    }

    /**
     * Returns all implementations of the supplied class using the specified ClassLoader.
     * The returned list is yours, you may do with it whatever you like.
     * It's not cached or so.
     * Because this method is anonymous (not bound to names), it can be used for any kind of service, not just implementations of {@link NamedService}.
     * @param service Class to get implementations for.
     * @param classLoader ClassLoader to use.
     * @return List with all implementations, empty collection if none found.
     */
    @NotNull public static <T> Collection<T> getAllInstances(@NotNull final Class<T> service, @NotNull final ClassLoader classLoader) {
        final Collection<T> instances = new ArrayList<T>();
        for (final Iterator<T> it = instanceIterator(service, classLoader); it.hasNext();) {
            instances.add(it.next());
        }
        return instances;
    }

    /**
     * Returns a Service iterator.
     * On JDK1.6 and later, the Iterator is created using {@link ServiceLoader}, on earlier platforms it is created using {@link Service}.
     * @param service Class for which to return a service iterator.
     * @return Iterator for {@code service}.
     */
    @NotNull public static <T> Iterator<T> instanceIterator(@NotNull final Class<T> service) {
        try {
            return ServiceLoader.load(service).iterator();
        } catch (final NoClassDefFoundError e) {
            return Service.providers(service);
        }
    }

    /**
     * Returns a Service iterator.
     * On JDK1.6 and later, the Iterator is created using {@link ServiceLoader}, on earlier platforms it is created using {@link Service}.
     * @param service Class for which to return a service iterator.
     * @param classLoader ClassLoader to use.
     * @return Iterator for {@code service}.
     */
    @NotNull public static <T> Iterator<T> instanceIterator(@NotNull final Class<T> service, @NotNull final ClassLoader classLoader) {
        try {
            return ServiceLoader.load(service, classLoader).iterator();
        } catch (final NoClassDefFoundError e) {
            return Service.providers(service, classLoader);
        }
    }
}
