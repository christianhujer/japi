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

package net.sf.japi.jtype;

import javax.swing.JComponent;
import javax.swing.JLabel;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.awt.GridBagLayout;
import java.util.Locale;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** A component that displays a property value along with a label.
 * It uses reflection to get the object's value.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class ReflectionField extends JComponent {

    /** Serial version. */
    private static final long serialVersionUID = 1L;

    /** The label to update.
     * @serial include
     */
    private final JLabel label;

    /** The format to create the label text.
     * @serial include
     */
    private final String format;

    /** The target object from which the field value will be read.
     * @serial include
     */
    private final Object target;

    /** The name of the property field. */
    private final String fieldName;

    /** The method used to retrieve the value of the property.
     * @serial include
     */
    private transient Method getter;

    /** Creates a ReflactionField.
     * @param label The label that shall be displayed.
     * @param format The format with which the property value shall be formatted for display.
     * @param target The target object of which the property value shall be displayed.
     * @param fieldName The name of the property that shall be displayed.
     */
    public ReflectionField(@NotNull final String label, @NotNull final String format, @NotNull final Object target, @NotNull final String fieldName) {
        setLayout(new GridBagLayout());
        add(new JLabel(label + ": "));
        this.label = new JLabel();
        add(this.label);
        this.format = format;
        this.target = target;
        this.fieldName = fieldName;
        getter = getGetterMethod(target.getClass(), fieldName);

    }

    /** {@inheritDoc} */
    private void readObject(@NotNull final ObjectInputStream in) throws ClassNotFoundException, IOException {
        getter = getGetterMethod(target.getClass(), fieldName);
    }

    /** Updates the display to reflect the latest property value. */
    public void update() {
        try {
            label.setText(String.format(format, getter.invoke(target)));
        } catch (final IllegalAccessException e) {
            e.printStackTrace();
        } catch (final InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /** Returns a getter method for the specified property of the specified class.
     * It supports the standard Java / JavaBeans names (isPropertyName() for boolean properties and getPropertyName() for others).
     * @param clazz Class for which the Getter shall be returned.
     * @param propertyName Name of the property for which the getter method shall be returned.
     * @return The getter method for the specified property of the specified class or <code>null</code> if such a getter method cannot be returned.
     */
    @Nullable private static Method getGetterMethod(@NotNull final Class<?> clazz, @NotNull final String propertyName) {
        final String capName = propertyName.substring(0, 1).toUpperCase(Locale.ENGLISH) + propertyName.substring(1);
        try {
            return clazz.getMethod("get" + capName);
        } catch (final NoSuchMethodException e) {
            System.err.println(e);
            try {
                return clazz.getMethod("is" + capName);
            } catch (final NoSuchMethodException e1) {
                System.err.println(e1);
                return null;
            }
        }
    }
}
