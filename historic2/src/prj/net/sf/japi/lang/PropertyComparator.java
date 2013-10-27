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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A Comparator for properties, compares two objects based on a Java Beans Property.
 *
 * @param <T> Property type to compare.
 * @param <C> Class of which to compare a property.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 * @todo 2009-02-16:christianhujer:Think whether net.sf.japi.lang really is the correct package or this should rather go to something like net.sf.japi.beans or net.sf.japi.util.
 * @todo 2009-02-16:christianhujer:This class is not yet implemented Serializable.
 */
public class PropertyComparator<T, C> implements Comparator<C>, Serializable {

    /** Serial version. */
    private static final long serialVersionUID = 1L;

    /** The target class.
     * @serial include
     */
    @Nullable private final Class<C> targetClass;

    /** The comparator to compare the property value.
     * @serial include
     */
    // If this field cannot be serialized, the default error mechanisms are sufficient and shall apply.
    @SuppressWarnings({"NonSerializableFieldInSerializableClass"})
    @Nullable private final Comparator<T> delegate;

    /** The name of the property to get.
     * @serial include
     */
    @NotNull private final String propertyName;

    /** The getter to read the property.
     * @serial include
     */
    @Nullable private transient Method getter;

    /** Create a PropertyComparator.
     * @param targetClass The target class of which properties should be compared, maybe <code>null</code> in which case the target class will be evaluated dynamically.
     * @param propertyName Name of the property to compare.
     * @param delegate Delegate comparator for the property, maybe <code>null</code> in which natural ordering of the property will be assumed; the property type must implement {@link Comparable} then.
     */
    public PropertyComparator(@Nullable final Class<C> targetClass, @NotNull final String propertyName, @Nullable final Comparator<T> delegate) {
        this.targetClass = targetClass;
        this.propertyName = propertyName;
        this.delegate = delegate;
        getter = targetClass == null ? null : getPropertyGetter(targetClass, propertyName);
    }

    /** {@inheritDoc} */
    private void readObject(@NotNull final ObjectInputStream in) throws ClassNotFoundException, IOException {
        in.defaultReadObject();
        getter = targetClass == null ? null : getPropertyGetter(targetClass, propertyName);
    }

    /** {@inheritDoc} */
    public int compare(final C o1, final C o2) {
        try {
            final Method o1Getter = getter != null ? getter : getPropertyGetter(o1.getClass(), propertyName);
            final Method o2Getter = getter != null ? getter : getPropertyGetter(o2.getClass(), propertyName);
            final Object o1Value = o1Getter.invoke(o1);
            final Object o2Value = o2Getter.invoke(o2);
            return delegate == null ? ((Comparable) o1Value).compareTo((Comparable) o2Value) : delegate.compare((T) o1Value, (T) o2Value);
        } catch (final IllegalAccessException e) {
            throw new IllegalAccessError(e.getMessage());
        } catch (final InvocationTargetException e) {
            throw new RuntimeException(e); // TODO:2009-02-15:christianhujer:use something better than RuntimeException
        }
    }

    /** Returns the property getter for the specified class and property name.
     * @param targetClass Class to get property getter for.
     * @param propertyName Name of the property to get getter for.
     * @return Getter Method for the specified property.
     */
    public static Method getPropertyGetter(@NotNull final Class targetClass, @NotNull final String propertyName) {
        try { // try getFoo()
            final StringBuilder getterName = new StringBuilder();
            getterName.append("get");
            getterName.append(propertyName);
            getterName.setCharAt(3, Character.toUpperCase(getterName.charAt(3)));
            return targetClass.getMethod(getterName.toString());
        } catch (final NoSuchMethodException ignore) {
            // one more try: boolean property name.
        }
        try { // try isFoo()
            final StringBuilder getterName = new StringBuilder();
            getterName.append("is");
            getterName.append(propertyName);
            getterName.setCharAt(2, Character.toUpperCase(getterName.charAt(2)));
            return targetClass.getMethod(getterName.toString());
        } catch (final NoSuchMethodException ignore) {
            // one more try: property name.
        }
        try { // try foo()
            return targetClass.getMethod(propertyName);
        } catch (final NoSuchMethodException ignore) {
            // one more try: property name.
        }
        throw new NoSuchMethodError("Property Getter for property " + propertyName + " in class " + targetClass);
    }

    /** Returns the property name of the property that's compared by this PropertyComparator.
     * @return Property name.
     */
    @NotNull public String getPropertyName() {
        return propertyName;
    }

    /** Returns the target class this PropertyComparator operates on.
     * @return Target class or <code>null</code> if this PropertyComparator uses dynamic class evaluation.
     */
    @Nullable
    public Class<C> getTargetClass() {
        return targetClass;
    }

    /** Returns the delegate this PropertyComparator uses for the property value comparison.
     * @return Delegate or <code>null</code> if this PropertyComparator uses natural ordering.
     */
    @Nullable
    public Comparator<T> getDelegate() {
        return delegate;
    }

} // class PropertyComparator
