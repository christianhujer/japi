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

package net.sf.japi.util;

import java.util.HashMap;
import java.util.Map;

/**
 * HashMap implementation which provides default values for unmapped keys which can be distinct from <code>null</code>.
 * @warning This map implementation violates the contract of {@link Map#get(Object)} regarding the return value.
 * @param <K> Type for the map keys.
 * @param <V> Type for the map values.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
@SuppressWarnings({"ClassExtendsConcreteCollection"})
public class HashMapWithDefault<K, V> extends HashMap<K, V> {

    /** Serial Version. */
    private static final long serialVersionUID = 1L;

    /** The default value to return if a key is not mapped.
     * @serial include
     */
    // It is the responsibility of the user to choose a serializable V for a map that is serialized.
    @SuppressWarnings({"NonSerializableFieldInSerializableClass"})
    private V defaultValue;

    /** Creates a HashMapWithDefault.
     * @param defaultValue Value to be returned by {@link Map#get(Object)} for unmapped keys.
     */
    public HashMapWithDefault(final V defaultValue) {
        this.defaultValue = defaultValue;
    }

    /** {@inheritDoc} */
    @Override
    public V get(final Object key) {
        return containsKey(key) ? super.get(key) : defaultValue;
    }
}
