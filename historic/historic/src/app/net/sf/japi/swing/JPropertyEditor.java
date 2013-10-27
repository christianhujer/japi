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

package net.sf.japi.swing;

import java.awt.Component;
import java.awt.GridBagConstraints;
import static java.awt.GridBagConstraints.REMAINDER;
import java.awt.GridBagLayout;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

/** User Interface component for editing properties.
 * @author <a href="mailto:chris@riedquat.de">Christian Hujer</a>
 * @deprecated don't use this class yet, it's under development and everything is subject of change.
 */
@Deprecated public class JPropertyEditor extends JComponent {

    private static final Map<Class<?>, Class<? extends Component>> EDITORS = new HashMap<Class<?>, Class<? extends Component>>();

    static {
        EDITORS.put(Boolean.TYPE, JCheckBox.class); // TODO
        EDITORS.put(String.class, JTextField.class); // TODO
    }

    public JPropertyEditor(final Object object) throws IntrospectionException {
        this(object, Object.class);
    }

    public JPropertyEditor(final Object object, final Class<?> stopClass) throws IntrospectionException {
        setLayout(new GridBagLayout());
        final GridBagConstraints labelGbc = new GridBagConstraints();
        final GridBagConstraints componentGbc = new GridBagConstraints();
        componentGbc.gridwidth = REMAINDER;
        labelGbc.fill = GridBagConstraints.HORIZONTAL;
        componentGbc.fill = GridBagConstraints.HORIZONTAL;
        final BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass(), stopClass);
        final PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (final PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            final String propertyName = propertyDescriptor.getDisplayName();
            final Class<?> propertyType = propertyDescriptor.getPropertyType();
            PropertyEditor propertyEditor = propertyDescriptor.createPropertyEditor(object);
            if (propertyEditor == null) {
                propertyEditor = PropertyEditorManager.findEditor(propertyType);
            }
            if (propertyEditor != null) {
                final Component editor = propertyEditor.supportsCustomEditor() ? propertyEditor.getCustomEditor() : createEditor(propertyType);
                if (editor != null) {
                    //noinspection ObjectAllocationInLoop
                    add(new JLabel(propertyName), labelGbc);
                    add(editor, componentGbc);
                }
            }
        }
    }

    /** Create an editor for a property.
     * @param propertyType property type
     * @return Editor component for the specified property type or <code>null</code> if no editor is available.
     */
    @SuppressWarnings({"ReturnOfNull"})
    private static Component createEditor(final Class<?> propertyType) {
        // TODO: Attach property editor
        try {
            return EDITORS.get(propertyType).newInstance();
        } catch (final InstantiationException ignore) {
            return null;
        } catch (IllegalAccessException ignore) {
            return null;
        }
    }

} // class JPropertyEditor
