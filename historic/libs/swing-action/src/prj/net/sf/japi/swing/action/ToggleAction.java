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

import java.awt.event.ActionEvent;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;

/** The ToggleAction works similar as an ReflectionAction.
 * But it keeps track of the components.
 * Be sure to use its factory methodsA
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public final class ToggleAction extends AbstractAction {

    /** The key used for storing the target object to invoke the methods on.
     * Value Type: {@link Object}.
     */
    public static final String REFLECTION_TARGET = "ReflectionTarget";

    /** The key used for storing the target object's boolean property name to find the methods
     * Value Type: {@link String}.
     */
    public static final String REFLECTION_PROPERTY_NAME = "ReflectionPropertyName";

    /** Serial Version. */
    // Work around bug in IntelliJ IDEA
    @SuppressWarnings({"AnalyzingVariableNaming"})
    private static final long serialVersionUID = 1L;

    /** The selected state.
     * @serial include
     */
    private boolean selected;

    /** The buttons created. */
    private final transient List<WeakReference<AbstractButton>> buttons = new ArrayList<WeakReference<AbstractButton>>();

    /** Returns the state of the action.
     * @return selected state of this action
     */
    public boolean isSelected() {
        return selected;
    }

    /** {@inheritDoc} */
    public void actionPerformed(final ActionEvent e) {
        if (!isEnabled()) {
            return;
        }
        final Object instance = getValue(REFLECTION_TARGET);
        String property = (String) getValue(REFLECTION_PROPERTY_NAME);
        property = Character.toUpperCase(property.charAt(0)) + property.substring(1);
        final String getterName = "is" + property;
        final String setterName = "set" + property;
        try {
            final Method getter = instance.getClass().getMethod(getterName);
            final Method setter = instance.getClass().getMethod(setterName, boolean.class);
            setter.invoke(instance, !(Boolean) getter.invoke(instance));
            setSelected((Boolean) getter.invoke(instance));
        } catch (final NoSuchMethodException ex) {
            assert false : ex;
        } catch (final IllegalAccessException ex) {
            assert false : ex;
        } catch (final InvocationTargetException ex) {
            // XXX:2009-02-15:christianhujer:workaround bug in IntelliJ IDEA (ex is NOT ignored)
            //noinspection ThrowInsideCatchBlockWhichIgnoresCaughtException
            ex.printStackTrace();
            throw new RuntimeException(ex.getCause());
        }
    }

    /** {@inheritDoc} */
    @Override protected Object clone() throws CloneNotSupportedException { // NOPMD
        return super.clone();
    }

    /** Create a JCheckBox for this action.
     * @return JCheckBox for this action
     */
    public JCheckBox createCheckBox() {
        final JCheckBox ret = new JCheckBox(this);
        ret.setSelected(isSelected());
        buttons.add(new WeakReference<AbstractButton>(ret));
        return ret;
    }

    /** Create a JCheckBoxMenuItem.
     * @return JCheckBoxMenuItem for this action
     */
    public JCheckBoxMenuItem createCheckBoxMenuItem() {
        final JCheckBoxMenuItem ret = new JCheckBoxMenuItem(this);
        ret.setSelected(isSelected());
        buttons.add(new WeakReference<AbstractButton>(ret));
        return ret;
    }

    /** {@inheritDoc}
     * This implementation checks the type of <var>newValue</var> if the <var>key</var> is {@link #REFLECTION_TARGET} or {@link
     * #REFLECTION_PROPERTY_NAME}, so you'll know of errors quite soon.
     * @throws IllegalArgumentException if <var>newValue</var> is of the wrong type
     */
    @Override public void putValue(final String key, final Object newValue) throws IllegalArgumentException {
        if (REFLECTION_PROPERTY_NAME.equals(key)) {
            if (newValue != null && !(newValue instanceof String)) { // NOPMD
                throw new IllegalArgumentException("Value for key REFLECTION_PROPERTY_NAME must be of type " + String.class.getName() + " but was " + newValue.getClass().getName());
            }
        }
        super.putValue(key, newValue);
    }

    /** Update the selected state.
     * @param selected new selected state
     */
    public void setSelected(final boolean selected) {
        this.selected = selected;
        //noinspection ForLoopWithMissingComponent
        for (final Iterator<WeakReference<AbstractButton>> it = buttons.iterator(); it.hasNext();) {
            final WeakReference<AbstractButton> ref = it.next();
            final AbstractButton button = ref.get();
            if (button == null) {
                it.remove();
            } else {
                button.setSelected(selected);
            }
        }
    }

} // class ToggleAction
