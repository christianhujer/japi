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

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import static java.util.ResourceBundle.getBundle;
import java.util.prefs.Preferences;
import static java.util.prefs.Preferences.userNodeForPackage;
import javax.swing.Action;
import static javax.swing.Action.ACCELERATOR_KEY;
import static javax.swing.Action.DISPLAYED_MNEMONIC_INDEX_KEY;
import static javax.swing.Action.LONG_DESCRIPTION;
import static javax.swing.Action.MNEMONIC_KEY;
import static javax.swing.Action.NAME;
import static javax.swing.Action.SHORT_DESCRIPTION;
import static javax.swing.Action.SMALL_ICON;
import javax.swing.ActionMap;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import static javax.swing.KeyStroke.getKeyStroke;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** Default implementation of {@link ActionBuilder}.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 * @todo Implement serialization.
 */
public class DefaultActionBuilder implements ActionBuilder {

    /** The parent ActionBuilders. */
    @NotNull private final List<ActionBuilder> parents = new LinkedList<ActionBuilder>();

    /** The ResourceBundles to look for.
     * Type: ResourceBundle
     */
    @NotNull private final List<ResourceBundle> bundles = new LinkedList<ResourceBundle>();

    /** The Preferences to look for.
     * Type: Preferences
     */
    @NotNull private final List<Preferences> prefs = new LinkedList<Preferences>();

    /** The ActionMap to which created Actions are automatically added. */
    @NotNull private final ActionMap actionMap = new NamedActionMap();

    /** The action providers that were registered and will be queried when an action should be created / retrieved. */
    private final List<ActionProvider> actionProviders = new ArrayList<ActionProvider>();

    /** Add a ResourceBundle to the list of used bundles.
     * @param baseName the base name of the resource bundle, a fully qualified class name
     * @see ResourceBundle#getBundle(String)
     */
    public void addBundle(@NotNull final String baseName) {
        //noinspection ConstantConditions
        if (baseName == null) {
            throw new NullPointerException("null bundle name not allowed");
        }
        final ResourceBundle newBundle = getBundle(baseName);
        addBundle(newBundle);
        try {
            final String additionalBundles = newBundle.getString("ActionBuilder.additionalBundles");
            if (additionalBundles != null) {
                for (final String additionalBundle : additionalBundles.split("\\s+")) {
                    addBundle(additionalBundle);
                }
            }
        } catch (final MissingResourceException e) {
            /* ignore - it is okay if a bundle specifies no additional bundles. */
        }
    }

    /** Method to find the JMenuItem for a specific Action.
     * @param menuBar JMenuBar to search
     * @param action Action to find JMenuItem for
     * @return JMenuItem for action or <code>null</code> if none found
     * @throws NullPointerException if <var>action</var> or <var>menuBar</var> is <code>null</code>
     */
    @Nullable public static JMenuItem find(@NotNull final JMenuBar menuBar, @NotNull final Action action) {
        //noinspection ConstantConditions
        if (menuBar == null) {
            throw new NullPointerException("null JMenuBar not allowed");
        }
        //noinspection ConstantConditions
        if (action == null) {
            throw new NullPointerException("null Action not allowed");
        }
        for (int i = 0; i < menuBar.getMenuCount(); i++) {
            final JMenu menu = menuBar.getMenu(i);
            if (menu.getAction() == action) {
                return menu;
            } else {
                final JMenuItem ret = find(menu, action);
                if (ret != null) {
                    return ret;
                }
            }
        }
        return null;
    }

    /** Method to find the JMenuItem for a specific Action.
     * @param menu JMenu to search
     * @param action Action to find JMenuItem for
     * @return JMenuItem for action or <code>null</code> if none found
     * @throws NullPointerException if <var>menu</var> or <var>action</var> is <code>null</code>
     */
    @Nullable public static JMenuItem find(@NotNull final JMenu menu, @NotNull final Action action) {
        //noinspection ConstantConditions
        if (menu == null) {
            throw new NullPointerException("null JMenuBar not allowed");
        }
        //noinspection ConstantConditions
        if (action == null) {
            throw new NullPointerException("null Action not allowed");
        }
        for (int i = 0; i < menu.getItemCount(); i++) {
            final JMenuItem item = menu.getItem(i);
            if (item == null) { // NOPMD
                // Ignore Separators
            } else if (item.getAction() == action) {
                return item;
            } else if (item instanceof JMenu) {
                final JMenuItem ret = find((JMenu) item, action);
                if (ret != null) {
                    return ret;
                }
            }
        }
        return null;
    }

    /**
     * Create an ActionBuilder.
     * Usually you wouldn't create an ActionBuilder yourself but use {@link ActionBuilderFactory#getActionBuilder(String)} for recycling ActionBuilders and profit of easy
     * access to the same ActionBuilder from within the whole application without passing around ActionBuilder references.
     * Don't use this constructor without knowing what you're doing.
     * It's mainly there for testing purposes.
     */
    public DefaultActionBuilder() {
    }

    /**
     * Create an ActionBuilder.
     * Usually you wouldn't create an ActionBuilder yourself but use {@link ActionBuilderFactory#getActionBuilder(String)} for recycling ActionBuilders and profit of easy
     * access to the same ActionBuilder from within the whole application without passing around ActionBuilder references.
     * Use this constructor if you explicitely need a new ActionBuilder that doesn't share information (especially the action cache) with other ActionBuilder instances.
     * @param key name of ActionBuilder.
     */
    public DefaultActionBuilder(final String key) {
        addBundle(key + ".action");
    }

    /** Get the ActionMap.
     * @return ActionMap
     */
    @NotNull public ActionMap getActionMap() {
        return actionMap;
    }

    /** Add a ResourceBundle to the list of used bundles.
     * @param bundle ResourceBundle to add
     * @throws NullPointerException if <code>bundle == null</code>
     */
    public void addBundle(@NotNull final ResourceBundle bundle) throws NullPointerException {
        //noinspection ConstantConditions
        if (bundle == null) {
            throw new NullPointerException("null ResourceBundle not allowed");
        }
        if (!bundles.contains(bundle)) {
            // insert first because new bundles override old bundles
            bundles.add(0, bundle);
        }
    }

    /** Add a parent to the list of used parents.
     * @param parent Parent to use if lookups failed in this ActionBuilder
     * WARNING: Adding a descendents as parents of ancestors or vice versa will result in endless recursion and thus stack overflow!
     * @throws NullPointerException if <code>parent == null</code>
     */
    public void addParent(@NotNull final ActionBuilder parent) throws NullPointerException {
        //noinspection ConstantConditions
        if (parent == null) {
            throw new NullPointerException("null ActionBuilder not allowed");
        }
        parents.add(parent);
    }

    /** Add a Preferences to the list of used preferences.
     * @param pref Preferences to add
     * @throws NullPointerException if <code>pref == null</code>
     */
    public void addPref(@NotNull final Preferences pref) throws NullPointerException {
        //noinspection ConstantConditions
        if (pref == null) {
            throw new NullPointerException("null ResourceBundle not allowed");
        }
        if (!prefs.contains(pref)) {
            prefs.add(pref);
        }
    }

    /** Add a Preferences to the list of used preferences.
     * @param clazz the class whose package a user preference node is desired
     * @see Preferences#userNodeForPackage(Class)
     * @throws NullPointerException if <code>clazz == null</code>
     */
    public void addPref(@NotNull final Class<?> clazz) {
        //noinspection ConstantConditions
        if (clazz == null) {
            throw new NullPointerException("null Class not allowed");
        }
        addPref(userNodeForPackage(clazz));
    }

    /** Creates actions.
     * This is a loop variant of {@link #createAction(boolean,String)}.
     * The actions created can be retrieved using {@link #getAction(String)} or via the ActionMap returned by {@link #getActionMap()}.
     * @param store whether to store the initialized Action in the ActionMap of this ActionBuilder
     * @param keys Keys of actions to create
     * @return Array with created actions
     * @throws NullPointerException in case keys is or contains <code>null</code>
     */
    public Action[] createActions(final boolean store, @NotNull final String... keys) throws NullPointerException {
        final Action[] actions = new Action[keys.length];
        for (int i = 0; i < keys.length; i++) {
            actions[i] = createAction(store, keys[i]);
        }
        return actions;
    }

    /** Create an Action.
     * The created Action is automatically stored together with all other Actions created by this Builder instance in an ActionMap, which you can
     * retreive using {@link #getActionMap()}.
     * @param store whether to store the initialized Action in the ActionMap of this ActionBuilder
     * @param key Key for Action, which is used as basename for access to Preferences and ResourceBundles and as ActionMap key (may not be
     * <code>null</code>)
     * @return created Action, which is a dummy in the sense that its {@link Action#actionPerformed(ActionEvent)} method does not do anything
     * @throws NullPointerException in case <var>key</var> was <code>null</code>
     * @see #createAction(boolean,String,Object)
     * @see #createToggle(boolean,String,Object)
     * @see #initAction(boolean,Action,String)
     */
    public Action createAction(final boolean store, @NotNull final String key) throws NullPointerException {
        // initAction() checks for null key
        return initAction(store, new DummyAction(), key);
    }

    /** Initialize an Action.
     * This is a convenience method for Action users which want to use the services provided by this {@link ActionBuilder} class but need more
     * sophisticated Action objects they created on their own.
     * So you can simply create an Action and pass it to this Initialization method to fill its data.
     * The initialized Action is automatically stored together with all other Actions created by this Builder instance in an ActionMap, which you can
     * retreive using {@link #getActionMap()}.
     * @param store whether to store the initialized Action in the ActionMap of this ActionBuilder
     * @param action Action to fill
     * @param key Key for Action, which is used as basename for access to Preferences and ResourceBundles and as ActionMap key (may not be <code>null</code>)
     * @return the supplied Action object (<var>action</var>) is returned for convenience
     * @throws NullPointerException in case <var>key</var> was <code>null</code>
     */
    @SuppressWarnings({"NestedAssignment"})
    @NotNull public Action initAction(final boolean store, @NotNull final Action action, @NotNull final String key) throws NullPointerException {
        action.putValue(ACTION_ID, key);
        String value;
        final String text = getString(key + ".text");
        if ((value = text)                                 != null) { action.putValue(NAME,              value); }
        if ((value = getString(key + ".shortdescription")) != null) { action.putValue(SHORT_DESCRIPTION, value); }
        if ((value = getString(key + ".longdescription"))  != null) { action.putValue(LONG_DESCRIPTION,  value); }
        if ((value = getString(key + ".accel"))            != null) { action.putValue(ACCELERATOR_KEY,   getKeyStroke(value)); }
        if ((value = getString(key + ".accel2"))           != null) { action.putValue(ACCELERATOR_KEY_2, getKeyStroke(value)); }
        if ((value = getString(key + ".mnemonic"))         != null) {
            final KeyStroke keyStroke = getKeyStroke(value);
            if (keyStroke != null) {
                action.putValue(MNEMONIC_KEY, keyStroke.getKeyCode());
            } else {
                System.err.println("Warning: Action key " + key + " has " + key + ".mnemonic value " + value + " which is a null KeyStroke.");
            }
            if (text == null) {
                System.err.println("Warning: Action key " + key + " has " + key + ".mnemonic value " + value + " but no text. Either define " + key + ".text or remove " + key + ".mnemonic.");
            }
        }
        if ((value = getString(key + ".mnemonicIdx"))      != null) { action.putValue(DISPLAYED_MNEMONIC_INDEX_KEY, Integer.parseInt(value)); }
        if ((value = getString(key + ".icon"))             != null) {
            final Icon image = IconManager.getDefaultIconManager().getIcon(value);
            if (image != null) {
                action.putValue(SMALL_ICON, image);
            } else {
                System.err.println("Warning: Action key " + key + " has " + key + ".icon value " + value + " but no corresponding icon was found.");
            }
        }
        action.putValue(ReflectionAction.REFLECTION_MESSAGE_PROVIDER, this);
        if (store) {
            actionMap.put(key, action);
        }
        return action;
    }

    /** Get a String.
     * First looks one pref after another, in their addition order.
     * Then looks one bundle after another, in their addition order.
     * @param key Key to get String for
     * @return the first value found or <code>null</code> if no value could be found
     * @throws NullPointerException if <var>key</var> is <code>null</code>
     */
    @Nullable public String getString(@NotNull final String key) throws NullPointerException {
        if (key == null) {
            throw new NullPointerException("null key not allowed");
        }
        String value = null;
        for (final Preferences pref : prefs) {
            value = pref.get(key, value);
            if (value != null) {
                return value;
            }
        }
        for (final ResourceBundle bundle : bundles) {
            try {
                value = bundle.getString(key);
                return value;
            } catch (final MissingResourceException ignore) { /* ignore */
            } catch (final ClassCastException ignore) { /* ignore */
            } // ignore exceptions because they don't mean errors just there's no resource, so parents are checked or null is returned.
        }
        for (final ActionBuilder parent : parents) {
            value = parent.getString(key);
            if (value != null) {
                return value;
            }
        }
        return null;
    }

    /** Get a String from the preferences, ignoring the resource bundles.
     * @param key Key to get String for
     * @return the first value found or <code>null</code> if no value could be found
     * @throws NullPointerException if <var>key</var> is <code>null</code>
     */
    @Nullable public String getStringFromPrefs(@NotNull final String key) throws NullPointerException {
        String value = null;
        for (final Preferences pref : prefs) {
            value = pref.get(key, value);
            if (value != null) {
                return value;
            }
        }
        for (final ActionBuilder parent : parents) {
            value = parent.getStringFromPrefs(key);
            if (value != null) {
                return value;
            }
        }
        return null;
    }

    /** Get a String from the resource bundles, ignoring the preferences.
     * @param key Key to get String for
     * @return the first value found or <code>null</code> if no value could be found
     * @throws NullPointerException if <var>key</var> is <code>null</code>
     */
    @Nullable public String getStringFromBundles(@NotNull final String key) throws NullPointerException {
        if (key == null) {
            throw new NullPointerException("null key not allowed");
        }
        String value;
        for (final ResourceBundle bundle : bundles) {
            try {
                value = bundle.getString(key);
                return value;
            } catch (final MissingResourceException ignore) { /* ignore */
            } catch (final ClassCastException ignore) { /* ignore */
            } // ignore exceptions because they don't mean errors just there's no resource, so parents are checked or null is returned.
        }
        for (final ActionBuilder parent : parents) {
            value = parent.getStringFromBundles(key);
            if (value != null) {
                return value;
            }
        }
        return null;
    }
    /** Creates actions.
     * This is a loop variant of {@link #createAction(boolean,String,Object)}.
     * The actions created can be retrieved using {@link #getAction(String)} or via the ActionMap returned by {@link #getActionMap()}.
     * @param store whether to store the initialized Action in the ActionMap of this ActionBuilder
     * @param target Target object
     * @param keys Keys of actions to create
     * @return Array with created actions
     * @throws NullPointerException in case keys is or contains <code>null</code>
     */
    public Action[] createActions(final boolean store, final Object target, final String... keys) throws NullPointerException {
        final Action[] actions = new Action[keys.length];
        for (int i = 0; i < keys.length; i++) {
            actions[i] = createAction(store, keys[i], target);
        }
        return actions;
    }

    /** Create an Action.
     * The created Action is automatically stored together with all other Actions created by this Builder instance in an ActionMap, which you can
     * retreive using {@link #getActionMap()}.
     * The supplied object needs to have a zero argument method named <var>key</var>.
     * You may pass <code>null</code> as object, which means that the object the method is invoked on is not defined yet.
     * You may safely use the Action, it will not throw any Exceptions upon {@link Action#actionPerformed(ActionEvent)} but simply silently do nothing.
     * The desired object can be set later using <code>action.putValue({@link ReflectionAction#REFLECTION_TARGET}, <var>object</var>)</code>.
     * <p />
     * Users of this method can assume that the returned object behaves quite like {@link ReflectionAction}.
     * Whether or not this method returns an instance of {@link ReflectionAction} should not be relied on.
     * @param store whether to store the initialized Action in the ActionMap of this ActionBuilder
     * @param key Key for Action, which is used as basename for access to Preferences and ResourceBundles, as ActionMap key and as Reflection Method
     * name within the supplied object (may not be <code>null</code>)
     * @param object Instance to invoke method on if the Action was activated ({@link Action#actionPerformed(ActionEvent)})
     * @return created Action
     * @throws NullPointerException in case <var>key</var> was <code>null</code>
     * @see #createAction(boolean,String)
     * @see #createToggle(boolean,String,Object)
     * @see #initAction(boolean,Action,String)
     */
    public Action createAction(final boolean store, final String key, final Object object) throws NullPointerException {
        if (key == null) { throw new NullPointerException("null key for Action creation not allowed."); }
        final Action action = new ReflectionAction(key, object);
        initAction(store, action, key);
        return action;
    }

    /** Method for creating a Menu.
     * @param store whether to store the initialized Action in the ActionMap of this ActionBuilder
     * @param menuKey action key for Menu
     * @param keys Action keys for menu items
     * @return menu created from the menu definition found
     * @throws Error in case action definitions for <var>keys</var> weren't found
     */
    public JMenu createMenu(final boolean store, final String menuKey, final String... keys) throws Error {
        final JMenu menu = new JMenu(createAction(store, menuKey));
        for (final String key : keys) {
            if (key != null && key.length() == 0) { // NOPMD
                /* ignore this for empty menus */
            } else if (key == null || "-".equals(key) || "|".equals(key)) {
                menu.addSeparator();
            } else {
                final Action action = getAction(key);
                if (action == null) {
                    throw new MissingResourceException(this + " has no definitin for action " + key, getClass().getName(), key);
                }
                if (action instanceof ToggleAction) {
                    menu.add(((ToggleAction) action).createCheckBoxMenuItem());
                } else {
                    menu.add(action);
                }
            }
        }
        return menu;
    }

    /** Get an Action.
     * For an action to be retrieved with this method, it must have been initialized with {@link #initAction(boolean,Action,String)}, either directly by
     * invoking {@link #initAction(boolean,Action,String)} or indirectly by invoking one of this class' creation methods like {@link #createAction(boolean,String)},
     * {@link #createAction(boolean,String,Object)} or {@link #createToggle(boolean,String,Object)}.
     * @param key Key of action to get
     * @return Action for <var>key</var> or <code>null</code> if the action does not exist.
     * This method does the same as <code>getActionMap().get(key)</code>.
     */
    @Nullable public Action getAction(@NotNull final String key) {
        Action action = actionMap.get(key);
        if (action == null) {
            for (final ActionProvider actionProvider : actionProviders) {
                action = actionProvider.getAction(key);
                if (action != null) {
                    actionMap.put(key, action);
                    break;
                }
            }
        }
        if (action == null) {
            for (final ActionBuilder parent : parents) {
                action = parent.getAction(key);
                if (action != null) {
                    actionMap.put(key, action);
                    break;
                }
            }
        }
        return action;
    }

    /** Method for creating a menubar.
     * @param store whether to store the initialized Actions in the ActionMap of this ActionBuilder
     * @param barKey Action key of menu to create
     * @return JMenuBar created for <var>barKey</var>
     * @throws NullPointerException if no menubar definition was found
     * @todo make error handling consistent (see createMenu and others)
     */
    @NotNull public JMenuBar createMenuBar(final boolean store, final String barKey) throws NullPointerException {
        final JMenuBar menuBar = new JMenuBar();
        //noinspection ConstantConditions
        for (final String key : getString(barKey + ".menubar").split("\\s+")) {
            menuBar.add(createMenu(store, key));
        }
        return menuBar;
    }

    /** Method for creating a popup menu.
     * @param store Whether to store the initialized Actions in the ActionMap of this ActionBuilder.
     * @param popupKey Action key of popup menu to create.
     * @return JPopupMenu created for <var>popupKey</var>
     * @throws MissingResourceException if no menubar definition was found.
     * @todo make error handling consistent (see createMenu and others)
     */
    @NotNull public JPopupMenu createPopupMenu(final boolean store, final String popupKey) throws MissingResourceException {
        final JPopupMenu menu = new JPopupMenu();
        final String popupDefinition = getString(popupKey + ".menu");
        if (popupDefinition == null) {
            throw new MissingResourceException(this + " has no definition for popup " + popupKey, getClass().getName(), popupKey + ".menu");
        }
        for (final String key : popupDefinition.split("\\s+")) {
            if (key != null && key.length() == 0) { // NOPMD
                /* ignore this for empty menus */
            } else if (key == null || "-".equals(key) || "|".equals(key)) {
                menu.addSeparator();
            } else if (getString(key + ".menu") != null) {
                menu.add(createMenu(store, key));
            } else {
                final Action action = getAction(key);
                if (action == null) {
                    throw new MissingResourceException(this + " has no definitin for action " + key, getClass().getName(), key);
                }
                if (action instanceof ToggleAction) {
                    menu.add(((ToggleAction) action).createCheckBoxMenuItem());
                } else {
                    menu.add(action);
                }
            }
        }
        return menu;
    }

    /** Method for creating a Menu.
     * This method assumes that the underlying properties contain an entry like <code>key + ".menu"</code> which lists the menu element's keys.
     * Submenus are build recursively.
     * @param store Whether to store the initialized Action in the ActionMap of this ActionBuilder.
     * @param menuKey Action key for menu.
     * @return menu created from the menu definition found.
     * @throws MissingResourceException In case a menu definition for <var>menuKey</var> wasn't found
     */
    public JMenu createMenu(final boolean store, final String menuKey) throws MissingResourceException {
        final JMenu menu = new JMenu(createAction(store, menuKey));
        final String menuDefinition = getString(menuKey + ".menu");
        if (menuDefinition == null) {
            throw new MissingResourceException(this + " has no definition for menu " + menuKey, getClass().getName(), menuKey + ".menu");
        }
        for (final String key : menuDefinition.split("\\s+")) {
            if (key != null && key.length() == 0) { // NOPMD
                /* ignore this for empty menus */
            } else if (key == null || "-".equals(key) || "|".equals(key)) {
                menu.addSeparator();
            } else if (getString(key + ".menu") != null) {
                menu.add(createMenu(store, key));
            } else {
                final Action action = getAction(key);
                if (action == null) {
                    throw new MissingResourceException(this + " has no definitin for action " + key, getClass().getName(), key);
                }
                if (action instanceof ToggleAction) {
                    menu.add(((ToggleAction) action).createCheckBoxMenuItem());
                } else {
                    menu.add(action);
                }
            }
        }
        return menu;
    }

    /** Method for creating a menubar.
     * @param store whether to store the initialized Actions in the ActionMap of this ActionBuilder
     * @param barKey Action key of menu to create
     * @param target Target object
     * @return JMenuBar created for <var>barKey</var>
     * @throws MissingResourceException In case a menu or menubar definition was not found.
     */
    public JMenuBar createMenuBar(final boolean store, final String barKey, final Object target) throws MissingResourceException {
        final JMenuBar menuBar = new JMenuBar();
        final String menuBarDefinition = getString(barKey + ".menubar");
        if (menuBarDefinition == null) {
            throw new MissingResourceException(this + " has no definition for menubar " + barKey, getClass().getName(), barKey);
        }
        for (final String key : menuBarDefinition.split("\\s+")) {
            menuBar.add(createMenu(store, key, target));
        }
        return menuBar;
    }

    /** Method for creating a popup menu.
     * @param store whether to store the initialized Actions in the ActionMap of this ActionBuilder
     * @param popupKey Action key of popup menu to create
     * @param target Target object
     * @return JPopupMenu created for <var>barKey</var>
     * @throws MissingResourceException In case a menu definition was not found.
     */
    public JPopupMenu createPopupMenu(final boolean store, final String popupKey, final Object target) throws MissingResourceException {
        final JPopupMenu menu = new JPopupMenu();
        final String popupDefinition = getString(popupKey + ".menu");
        if (popupDefinition == null) {
            throw new MissingResourceException(this + " has no definition for popup " + popupKey, getClass().getName(), popupKey + ".menu");
        }
        for (final String key : popupDefinition.split("\\s+")) {
            if (key != null && key.length() == 0) { // NOPMD
                /* ignore this for empty menus */
            } else if (key == null || "-".equals(key) || "|".equals(key)) {
                menu.addSeparator();
            } else if (getString(key + ".menu") != null) {
                menu.add(createMenu(store, key, target));
            } else {
                Action action = null;
                if (store) {
                    action = getAction(key);
                }
                if (action == null) {
                    action = createAction(store, key, target);
                }
                if (action == null) {
                    throw new MissingResourceException(this + " has no definitin for action " + key, getClass().getName(), key);
                }
                if (action instanceof ToggleAction) {
                    menu.add(((ToggleAction) action).createCheckBoxMenuItem());
                } else {
                    menu.add(action);
                }
            }
        }
        return menu;
    }

    /** Method for creating a Menu.
     * This method assumes that the underlying properties contain an entry like <code>key + ".menu"</code> which lists the menu element's keys.
     * Submenus are build recursively.
     * @param store whether to store the initialized Action in the ActionMap of this ActionBuilder
     * @param menuKey action key for menu
     * @param target Target object
     * @return menu created from the menu definition found
     * @throws Error in case a menu definition for <var>menuKey</var> wasn't found
     */
    public JMenu createMenu(final boolean store, final String menuKey, final Object target) throws Error {
        final JMenu menu = new JMenu(createAction(store, menuKey));
        final String menuDefinition = getString(menuKey + ".menu");
        if (menuDefinition == null) {
            throw new MissingResourceException(this + " has no definition for menu " + menuKey, getClass().getName(), menuKey + ".menu");
        }
        for (final String key : menuDefinition.split("\\s+")) {
            if (key != null && key.length() == 0) { // NOPMD
                /* ignore this for empty menus */
            } else if (key == null || "-".equals(key) || "|".equals(key)) {
                menu.addSeparator();
            } else if (getString(key + ".menu") != null) {
                menu.add(createMenu(store, key, target));
            } else {
                Action action = null;
                if (store) {
                    action = getAction(key);
                }
                if (action == null) {
                    action = createAction(store, key, target);
                }
                if (action == null) {
                    throw new MissingResourceException(this + " has no definitin for action " + key, getClass().getName(), key);
                }
                if (action instanceof ToggleAction) {
                    menu.add(((ToggleAction) action).createCheckBoxMenuItem());
                } else {
                    menu.add(action);
                }
            }
        }
        return menu;
    }

    /** Creates actions.
     * This is a loop variant of {@link #createToggle(boolean,String,Object)}.
     * The actions created can be retrieved using {@link #getAction(String)} or via the ActionMap returned by {@link #getActionMap()}.
     * @param store whether to store the initialized Action in the ActionMap of this ActionBuilder
     * @param target Target object
     * @param keys Keys of actions to create
     * @throws NullPointerException in case <var>keys</var> was or contained <code>null</code>
     */
    public void createToggles(final boolean store, final Object target, final String... keys) throws NullPointerException {
        for (final String key : keys) {
            createToggle(store, key, target);
        }
    }

    /** Create an Action.
     * The created Action is automatically stored together with all other Actions created by this Builder instance in an ActionMap, which you can
     * retreive using {@link #getActionMap()}.
     * The supplied object needs to have a boolean return void argument getter method and a void return boolean argument setter method matching the
     * <var>key</var>.
     * You may pass <code>null</code> as object, which means that the object the getters and setters are invoked on is not defined yet.
     * You may safely use the Action, it will not throw any Exceptions upon {@link Action#actionPerformed(ActionEvent)} but simply silently do nothing.
     * The desired object can be set later using <code>action.putValue({@link ToggleAction#REFLECTION_TARGET}, <var>object</var>)</code>.
     * <p />
     * Users of this method can assume that the returned object behaves quite like {@link ToggleAction}.
     * Whether or not this method returns an instance of {@link ToggleAction} should not be relied on.
     * @see #createAction(boolean,String)
     * @see #createAction(boolean,String,Object)
     * @see #initAction(boolean,Action,String)
     * @param store whether to store the initialized Action in the ActionMap of this ActionBuilder
     * @param key Key for Action, which is used as basename for access to Preferences and ResourceBundles, as ActionMap key and as Property name within
     * the supplied object (may not be <code>null</code>)
     * @param object Instance to invoke method on if the Action was activated ({@link Action#actionPerformed(ActionEvent)})
     * @throws NullPointerException in case <var>key</var> was <code>null</code>
     * @return ToggleAction
     */
    public Action createToggle(final boolean store, final String key, final Object object) throws NullPointerException {
        final Action action = new ToggleAction();
        initAction(store, action, key);
        action.putValue(ToggleAction.REFLECTION_PROPERTY_NAME, key);
        action.putValue(ReflectionAction.REFLECTION_TARGET, object);
        return action;
    }

    /** Method for creating a toolbar.
     * @param barKey Action keys of toolbar to create
     * @return JToolBar created for <var>barKey</var>
     * @throws MissingResourceException In case there is no definition for the requested toolbar.
     */
    public JToolBar createToolBar(final String barKey) throws MissingResourceException {
        final String toolbarDefinition = getString(barKey + ".toolbar");
        if (toolbarDefinition == null) {
            throw new MissingResourceException(this + " has no definition for toolbar " + barKey, getClass().getName(), barKey);
        }
        final String name = getString(barKey + ".name");
        final JToolBar toolBar = name != null ? new JToolBar(name) : new JToolBar();
        for (final String key : toolbarDefinition.split("\\s+")) {
            if (key == null || "-".equals(key) || "|".equals(key)) {
                toolBar.addSeparator();
            } else {
                final Action action = getAction(key);
                if (action == null) {
                    throw new MissingResourceException(this + " has no definitin for action " + key, getClass().getName(), key);
                }
                toolBar.add(action);
            }
        }
        return toolBar;
    }

    /** Method for creating a toolbar.
     * @param object Instance to invoke method on if the Action was activated ({@link Action#actionPerformed(ActionEvent)})
     * @param barKey Action keys of toolbar to create
     * @return JToolBar created for <var>barKey</var>
     * @throws MissingResourceException In case there is no definition for the requested toolbar.
     */
    public JToolBar createToolBar(final Object object, final String barKey) throws MissingResourceException {
        final String toolbarDefinition = getString(barKey + ".toolbar");
        if (toolbarDefinition == null) {
            throw new MissingResourceException(this + " has no definition for toolbar " + barKey, getClass().getName(), barKey);
        }
        final String name = getString(barKey + ".name");
        final JToolBar toolBar = name != null ? new JToolBar(name) : new JToolBar();
        for (final String key : toolbarDefinition.split("\\s+")) {
            if (key == null || "-".equals(key) || "|".equals(key)) {
                toolBar.addSeparator();
            } else {
                Action action = getAction(key);
                if (action == null) {
                    action = createAction(false, key, object);
                }
                toolBar.add(action);
            }
        }
        return toolBar;
    }

    /** Method to find the JMenuItem for a specific Action key.
     * @param menuBar JMenuBar to search
     * @param key Key to find JMenuItem for
     * @return JMenuItem for key or <code>null</code> if none found
     */
    @Nullable public JMenuItem find(@NotNull final JMenuBar menuBar, @NotNull final String key) {
        final Action action = getAction(key);
        return action == null ? null : find(menuBar, action);
    }

    /** Get an icon.
     * @param key i18n key for icon
     * @return icon
     */
    public Icon getIcon(final String key) {
        return IconManager.getDefaultIconManager().getIcon(getString(key));
    }

    /** Show a localized message dialog.
     * @param parentComponent determines the Frame in which the dialog is displayed; if <code>null</code>, or if the <code>parentComponent</code> has
     * no <code>Frame</code>, a default <code>Frame</code> is used
     * @param key localization key to use for the title, the message and eventually the icon
     * @param args formatting arguments for the message text
     */
    public void showMessageDialog(@Nullable final Component parentComponent, @NotNull final String key, final Object... args) {
        JOptionPane.showMessageDialog(parentComponent, format(key + ".message", args), format(key + ".title", args), getMessageType(key));
    }

    /** Get the message type for a dialog.
     * @param dialogKey dialog key
     * @return message type
     */
    public int getMessageType(@NotNull final String dialogKey) {
        final String typeText = getString(dialogKey + ".messageType");
        if (typeText == null) {
            return JOptionPane.PLAIN_MESSAGE;
        }
        if (!typeText.endsWith("_MESSAGE")) {
            System.err.println("Warning: Illegal type value " + typeText + " for dialog " + dialogKey); // TODO:2009-02-15:christianhujer:I18N/L10N
            return JOptionPane.PLAIN_MESSAGE;
        }
        if ("PLAIN_MESSAGE".equals(typeText)) {
            return JOptionPane.PLAIN_MESSAGE;
        } else if ("QUESTION_MESSAGE".equals(typeText)) {
            return JOptionPane.QUESTION_MESSAGE;
        } else if ("WARNING_MESSAGE".equals(typeText)) {
            return JOptionPane.WARNING_MESSAGE;
        } else if ("INFORMATION_MESSAGE".equals(typeText)) {
            return JOptionPane.INFORMATION_MESSAGE;
        } else if ("ERROR_MESSAGE".equals(typeText)) {
            return JOptionPane.ERROR_MESSAGE;
        } else {
            // key not known, try reflection.
            try {
                final Field f = JOptionPane.class.getField(typeText);
                if (f.getType() == Integer.TYPE) {
                    return f.getInt(null);
                }
            } catch (final NoSuchFieldException e) {
                System.err.println("Warning: Field " + typeText + " not found in JOptionPane (dialog: " + dialogKey + ")."); // TODO:2009-02-15:christianhujer:I18N/L10N
                e.printStackTrace();  //TODO:2009-02-15:christianhujer:Replace with proper logging.
            } catch (final IllegalAccessException e) {
                System.err.println("Warning: Field " + typeText + " not accessible in JOptionPane (dialog: " + dialogKey + ")."); // TODO:2009-02-15:christianhujer:I18N/L10N
                e.printStackTrace();  //TODO:2009-02-15:christianhujer:Replace with proper logging.
            }
            return JOptionPane.PLAIN_MESSAGE;
        }
    }

    /** Formats a message with parameters.
     * It's a proxy method for using {@link MessageFormat}.
     * @param key message key
     * @param args parameters
     * @return formatted String
     * @see MessageFormat#format(String,Object...)
     */
    public String format(@NotNull final String key, final Object... args) {
        try {
            return MessageFormat.format(getString(key), args);
        } catch (final NullPointerException e) {
            throw new NullPointerException("No format for key " + key + " (" + e + ")");
        }
    }

    /** Show a localized confirmation dialog which the user can suppress in future (remembering his choice).
     * @param parentComponent determines the Frame in which the dialog is displayed; if <code>null</code>, or if the <code>parentComponent</code> has
     * no <code>Frame</code>, a default <code>Frame</code> is used
     * @param optionType the option type
     * @param messageType the type of message to be displayed
     * @param key localization key to use for the title, the message and eventually the icon
     * @param args formatting arguments for the message text
     * @return an integer indicating the option selected by the user now or eventually in a previous choice
     * @throws IllegalStateException if no preferences are associated
     */
    public int showOnetimeConfirmDialog(@Nullable final Component parentComponent, final int optionType, final int messageType, @NotNull final String key, final Object... args) throws IllegalStateException {
        String showString = getString(key + ".show");
        if (showString == null) {
            showString = getString(key + ".showDefault");
        }
        if (showString == null) {
            showString = "true";
        }
        final boolean show = !"false".equalsIgnoreCase(showString); // undefined should be treated true
        if (!show) {
            try {
                return Integer.parseInt(getString(key + ".choice"));
            } catch (final Exception ignore) {
                /* ignore, continue with dialog then. */
            }
        }
        final JCheckBox dontShowAgain = new JCheckBox(getString("dialogDontShowAgain"), true);
        final int result = JOptionPane.showConfirmDialog(parentComponent, new Object[] { format(key + ".message", args), dontShowAgain }, format(key + ".title", args), optionType, messageType);
        if (!dontShowAgain.isSelected()) {
            if (prefs.size() > 0) {
                prefs.get(0).put(key + ".show", "false");
                prefs.get(0).put(key + ".choice", Integer.toString(result));
            } else {
                throw new IllegalStateException("Cannot store prefs for this dialog - no Preferences associated with this ActionBuilder!");
            }
        }
        return result;
    }

    /** Show a localized message dialog which the user can suppress in future.
     * @param parentComponent determines the Frame in which the dialog is displayed; if <code>null</code>, or if the <code>parentComponent</code> has
     * no <code>Frame</code>, a default <code>Frame</code> is used
     * @param messageType the type of message to be displayed
     * @param key localization key to use for the title, the message and eventually the icon
     * @param args formatting arguments for the message text
     * @throws IllegalStateException if no preferences are associated
     */
    public void showOnetimeMessageDialog(@Nullable final Component parentComponent, final int messageType, @NotNull final String key, final Object... args) throws IllegalStateException {
        String showString = getString(key + ".show");
        if (showString == null) {
            showString = getString(key + ".showDefault");
        }
        if (showString == null) {
            showString = "true";
        }
        final boolean show = !"false".equalsIgnoreCase(showString); // undefined should be treated true
        if (show) {
            final JCheckBox dontShowAgain = new JCheckBox(getString("dialogDontShowAgain"), true);
            JOptionPane.showMessageDialog(parentComponent, new Object[] { format(key + ".message", args), dontShowAgain }, format(key + ".title", args), messageType);
            if (!dontShowAgain.isSelected()) {
                if (prefs.size() > 0) {
                    prefs.get(0).put(key + ".show", "false");
                } else {
                    throw new IllegalStateException("Cannot store prefs for this dialog - no Preferences associated with this ActionBuilder!");
                }
            }
        }
    }

    /** Show a localized question dialog.
     * @param parentComponent determines the Frame in which the dialog is displayed; if <code>null</code>, or if the <code>parentComponent</code> has
     * no <code>Frame</code>, a default <code>Frame</code> is used
     * @param key localization key to use for the title, the message and eventually the icon
     * @param args formatting arguments for the message text
     * @return <code>true</code> if user confirmed, otherwise <code>false</code>
     */
    public boolean showQuestionDialog(final Component parentComponent, final String key, final Object... args) {
        return showConfirmDialog(parentComponent, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, key, args) == JOptionPane.YES_OPTION;
    }

    /** Show a localized confirmation dialog.
     * @param parentComponent determines the Frame in which the dialog is displayed; if <code>null</code>, or if the <code>parentComponent</code> has
     * no <code>Frame</code>, a default <code>Frame</code> is used
     * @param optionType the option type
     * @param messageType the type of message to be displayed
     * @param key localization key to use for the title, the message and eventually the icon
     * @param args formatting arguments for the message text
     * @return an integer indicating the option selected by the user
     */
    public int showConfirmDialog(final Component parentComponent, final int optionType, final int messageType, final String key, final Object...  args) {
        return JOptionPane.showConfirmDialog(parentComponent, format(key + ".message", args), format(key + ".title", args), optionType, messageType);
    }

    /** Creates a label for a specified key.
     * @param key Key to create label for
     * @param args formatting arguments for the label text
     * @return JLabel for key Key
     * @note the label text will be the key if no String for the key was found
     */
    public JLabel createLabel(final String key, final Object... args) {
        //noinspection RedundantCast
        return createLabel((Component) null, key, args);
    }

    /** Creates a label for a specified key and component.
     * @param component Component to associate label to (maybe <code>null</code>)
     * @param key Key to create label for
     * @param args formatting arguments for the label text
     * @return JLabel for key Key
     * @note the label text will be the key if no String for the key was found
     * @todo support icons
     * @todo support mnemonics
     * @todo alignments and textpositions
     */
    public JLabel createLabel(final Component component, final String key, final Object... args) {
        final String labelText = format(key, args);
        final JLabel label = new JLabel(labelText != null ? labelText : key);
        label.setLabelFor(component);
        return label;
    }

    /** Registers an ActionProvider with this ActionBuilder.
     * @param actionProvider ActionProvider to register
     */
    public void addActionProvider(final ActionProvider actionProvider) {
        actionProviders.add(actionProvider);
    }

    /** {@inheritDoc} */
    public void setActionEnabled(@NotNull final String key, final boolean enabled) {
        final Action actionToEnable = actionMap.get(key);
        if (actionToEnable == null) {
            throw new IllegalStateException("No action for key " + key + ".");
        }
        actionToEnable.setEnabled(enabled);
    }

} // class ActionBuilder
