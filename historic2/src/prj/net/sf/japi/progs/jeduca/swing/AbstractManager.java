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

package net.sf.japi.progs.jeduca.swing;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import net.sf.japi.swing.action.ActionBuilder;
import net.sf.japi.swing.action.ActionBuilderFactory;
import org.jetbrains.annotations.Nullable;

/** Base class for Manager classes that manage visibility of components and provide corresponding menus.
 * <p />
 * Usage:
 * <ul>
 *  <li>Create the kind of manager you like (appropriate subclass of this)</li>
 *  <li>Create components to be managed</li>
 *  <li>Add the components to be managed using {@link #add(JComponent)}</li>
 *  <li>Create a JMenu using {@link #createManagedMenu()} to let the user control the visibility of the managed components</li>
 * </ul>
 * You may create more than one JMenu. The state of the menu items are automatically kept uptodate with the visibility state of the managed components.
 * <p />
 * Most common implementations are:
 * <ul>
 *  <li>{@link InternalFrameManager} which manages the visibility of {@link JInternalFrame}s</li>
 *  <li>{@link MenuManager} which manages the visibility of {@link JMenu}s</li>
 *  <li>{@link ToolBarManager} which manages the visibility of {@link JToolBar}s</li>
 * </ul>
 * You may add and remove components to a Manager as you want.
 * All menus you created will automatically be uptodate.
 * @param <T> Type which is managed.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 * @see Component#setVisible(boolean)
 * @todo support icons for the managed components, e.g. use a client property ({@link JComponent#putClientProperty(Object,Object)} / {@link
 * JComponent#getClientProperty(Object)}) (partly done)
 * @todo support accelerators for the managed components (partly done)
 * @todo support short descriptions for the managed components
 * @todo support mnemonics for the managed components (partly done)
 * @todo provide access to SubAction, e.g. via an {@link ActionMap}
 * @todo change actions to Map with T as key and SubAction as value.
 * @todo support creation of single JCheckBoxes and JCheckBoxMenuitems.
 */
public abstract class AbstractManager<T extends JComponent> {

    /** The actions of the components this Manager manages. */
    private List<SubAction<?>> actions = new ArrayList<SubAction<?>>();

    /** The Menus created. */
    private Collection<JMenu> cMenus = new ArrayList<JMenu>();

    /** Action Builder. */
    private static final ActionBuilder ACTION_BUILDER = ActionBuilderFactory.getInstance().getActionBuilder("net.sf.japi.progs.jeduca.swing");

    /** View Show All action.
     * @see #viewShowAll()
     */
    private final Action viewShowAll = ACTION_BUILDER.createAction(true, "viewShowAll", this);

    /** View Show All action, shows all managed components. */
    public void viewShowAll() {
        for (final SubAction<?> action : actions) {
            action.setVisible(true);
        }
    }

    /** View Hide All action.
     * @see #viewHideAll()
     */
    private final Action viewHideAll = ACTION_BUILDER.createAction(true, "viewHideAll", this);

    /** View Hide All action, hides all managed components. */
    public void viewHideAll() {
        for (final SubAction<?> action : actions) {
            action.setVisible(false);
        }
    }

    /** Add an component for being managed.
     * The component is automatically added to all menus created via {@link #createManagedMenu()}.
     * @param comp component to be managed
     */
    public void add(final T comp) {
        final SubAction<T> action = createSubAction(comp);
        final int pos = actions.size();
        actions.add(action);
        for (final JMenu mMenu : cMenus) {
            mMenu.insert(action.createCheckBoxMenuItem(), pos);
        }
        addHook(comp);
    }

    /** Hook for subclasses that want to take special action on components that have been added to manage.
     * The default implementation does nothing.
     * Implement this method if you want to take special actions on the added ccomopnents.
     * @param comp component to be manabed
     */
    protected void addHook(final T comp) {
    }

    /** Remove an component from being managed.
     * The component is automatically removed from all menus created via {@link #createManagedMenu()}.
     * @param comp component to be removed
     */
    public void remove(final T comp) {
        final Action action = findActionFor(comp);
        final int index = actions.indexOf(action);
        actions.remove(index);
        for (final Container mMenu : cMenus) {
            mMenu.remove(index);
        }
    }

    /** Find the Action for an component.
     * @param comp component to find Action for
     * @return SubAction for the specified comp.
     */
    @Nullable private Action findActionFor(final T comp) {
        for (final SubAction<?> action : actions) {
            if (action.getComponent() == comp) {
                return action;
            }
        }
        return null;
    }

    /** Create a Menu for managing the visibility of the components.
     * The created menu contains one menuitem for each component added so far.
     * Future invocations of {@link #add(JComponent)} and {@link #remove(JComponent)} will automatically change this list of menuitems.
     * Additionally, after a separator the menu contains an entry for configuration (if available, see {@link #getConfigureAction()}), showing and
     * hding all managed components.
     * @return JMenu
     */
    public JMenu createManagedMenu() {
        final JMenu menu = new JMenu(getMenuAction());
        cMenus.add(menu);
        for (final SubAction action : actions) {
            menu.add(action.createCheckBoxMenuItem());
        }
        menu.addSeparator();
        final Action configureAction = getConfigureAction();
        if (configureAction != null) {
            menu.add(new JMenuItem(configureAction));
        }
        menu.add(new JMenuItem(viewShowAll));
        menu.add(new JMenuItem(viewHideAll));
        return menu;
    }

    /** Get the Menu Action.
     * @return Menu Action
     */
    protected abstract Action getMenuAction();

    /** Get the Configure Action.
     * The default implementation returns <code>null</code>, you might want to override this method.
     * @return Configure Action
     */
    @Nullable protected Action getConfigureAction() {
        return null;
    }

    /** Create a SubAction.
     * Subclasses may override this method if they need extended SubActions.
     * When overriding, you may choose to call <code>super.createSubAction(comp)</code> and modify it or create a new one, perhaps for subclassing
     * SubAction.
     * @param comp Component to create SubAction for.
     * @return SubAction for the specified component.
     */
    protected SubAction<T> createSubAction(final T comp) {
        return new SubAction<T>(comp);
    }


    /** Class for component visibility management actions.
     * It implements {@link ComponentListener} so it can keep the Action state uptodate with the real visibility of the component.
     * This means that visibility handling works both ways round:
     * When a component is made (in)visible using its {@link Component#setVisible(boolean)} method, the Action is updated, which updates all {@link JCheckBoxMenuItem}s created from it.
     * When a {@link JCheckBoxMenuItem} is (de)selected, the Action is updated, which updates the Component's visibility state.
     * @todo find a better name for this class
     * @todo eventually provide <code>public</code> or at least <code>protected</code> access to createCheckBoxMenuItem
     * @todo eventually provide createCheckBox
     * @todo eventually use WeakReferences to the created objects
     * @author $Author: chris $
     * @version $Id: AbstractManager.java,v 1.4 2006/01/31 23:09:18 chris Exp $
     * @see Component#setVisible(boolean)
     */
    protected static class SubAction<T extends JComponent> extends AbstractAction implements ComponentListener {

        /** Serial Version. */
        private static final long serialVersionUID = 1L;

        /** The JComponent to be the Action for.
         * @serial include
         */
        private T comp;

        /** The list of JCheckBoxMenuItems and JCheckBoxes created for this JComponent.
         * @serial include
         */
        private final List<WeakReference<AbstractButton>> buttons = new ArrayList<WeakReference<AbstractButton>>();

        /** Create a SubAction.
         * @param comp JComponent to create Action for
         */
        public SubAction(final T comp) {
            this.comp = comp;
            Object value;
            if ((value = comp.getName()) != null) {
                putValue(NAME, value);
            } else if ((value = comp.getClientProperty(NAME)) != null) {
                putValue(NAME, value);
            }
            if ((value = comp.getClientProperty(ACCELERATOR_KEY)) != null) {
                putValue(ACCELERATOR_KEY, value);
            }
            if ((value = comp.getClientProperty(SMALL_ICON)) != null && value instanceof Icon) {
                putValue(SMALL_ICON, value);
            }
            if ((value = comp.getClientProperty(MNEMONIC_KEY)) != null) {
                putValue(MNEMONIC_KEY, value);
            }
            comp.addComponentListener(this);
        }

        /** Get the component this SubAction is the Action for
         * @return component
         */
        public T getComponent() {
            return comp;
        }

        /** {@inheritDoc} */
        public void actionPerformed(final ActionEvent e) {
            setVisible(!comp.isVisible());
        }

        /** Set visibility state.
         * @param visible new visibility state
         */
        void setVisible(final boolean visible) {
            comp.setVisible(visible);
            updateState();
        }

        /** Update the state of all created items. */
        private void updateState() {
            for (final Iterator<WeakReference<AbstractButton>> iterator = buttons.iterator(); iterator.hasNext();) {
                final WeakReference<AbstractButton> ref = iterator.next();
                final AbstractButton button = ref.get();
                if (button == null) {
                    iterator.remove();
                } else {
                    button.setSelected(comp.isVisible());
                }
            }
        }

        /** Create a JCheckBoxMenuItem for this Action.
         * @return JCheckBoxMenuItem for this Action.
         */
        JMenuItem createCheckBoxMenuItem() {
            final JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem(this);
            buttons.add(new WeakReference<AbstractButton>(menuItem));
            menuItem.setSelected(comp.isVisible());
            return menuItem;
        }

        /** Create a JCheckBox for this Action.
         * @return JCheckBox for this Action.
         */
        JCheckBox createCheckBox() {
            final JCheckBox checkBox = new JCheckBox(this);
            buttons.add(new WeakReference<AbstractButton>(checkBox));
            checkBox.setSelected(comp.isVisible());
            return checkBox;
        }

        /** {@inheritDoc} */
        public void componentHidden(final ComponentEvent e) {
            updateState();
        }

        /** {@inheritDoc} */
        public void componentShown(final ComponentEvent e) {
            updateState();
        }

        /** {@inheritDoc} */
        public void componentMoved(final ComponentEvent e) { /* ignore */ }

        /** {@inheritDoc} */
        public void componentResized(final ComponentEvent e) { /* ignore */ }

    } // class SubAction


} // class AbstractManager
