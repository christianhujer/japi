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
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** Interface for creating and initializing {@link Action}s that are localized, user configurable and may invoke their final methods using Reflection;
 * also handles extremely much useful stuff for i18n/l10n.
 * It is meant as a general service for creating Action objects that have some comfort for the programmer in several aspects:
 * <ul>
 *  <li>Allow zero or more ResourceBundles to be used when creating Actions</li>
 *  <li>Allow zero or more UserPreferences to be used when creating Actions</li>
 *  <li>Manage an ActionMap to which created Actions are automatically added</li>
 * </ul>
 * You may choose to use one or more ActionBuilders, depending on the size of your application.
 * You may spread Action configuration information accross one or more ResourceBundles and one or more Preferences, as you wish.
 * When looking for values, the Preferences are queried first, then the ResourceBundles, until a value was found.
 * The Preferences as well as the ResourceBundles are queried in the order in which they've been added.
 * The behaviour when no value was found is undefined.
 * <h3>Usage</h3>
 * The recommended usage is to
 * <ol>
 *  <li>
 *      create and initialize an ActionBuilder similar as the following example code, put it somewhere at the start of your program:
 * <pre>
 * ActionBuilder myActionBuilder = ActionBuilderFactory.getActionBuilder("MyApplication");
 * myActionBuilder.addBundle("com.mycompany.mypackage.myresource"); // Not always required.
 * myActionBuilder.addPref(MyClass.class); // Not always required.
 * </pre>
 *  </li>
 *  <li>
 *      then use the ActionBuilder from anywhere within the application like this:
 * <pre>
 * ActionBuilder myActionBuilder = ActionBuilderFactory.getActionBuilder("MyApplication");
 * Action myAction = myActionBuilder.createAction("load", this);
 * </pre>
 *  </li>
 * </ol>
 * <p>
 *  All actions created or initialized by an instance of this class are optionally put into that instance's {@link ActionMap}.
 *  If they are stored, you can use that map for later retrieval.
 * </p>
 * <h4>Usage Notes: Builder Name</h4>
 * <ul>
 *  <li>
 *      The builder name is used to try to load a resource bundle when a bundle is created.
 *      The builder name is used as package name for the bundle, the bundle name itself is "action".
 *      Example: When calling <code>ActionBuilderFactory.getActionBuilder("net.sf.japi.swing");</code> for the first time, it is tried to load a
 *      {@link ResourceBundle} named <code>net.sf.japi.swing.actions</code> for that <code>ActionBuilder</code>.
 *      This automatism has been implemented to save you from the need of initializing an ActionBuilder before use.
 *  </li>
 * </ul>
 * <h4>Usage Notes: Action Key / Action Name</h4>
 * The key you supply as first argument of {@link #createAction(boolean, String, Object)} determines several things:
 * <ul>
 *  <li>The base name for the different keys in the preferences / resource bundle and other known Action Keys:
 *      <table border="1">
 *          <tr><th>What</th><th>Preferences / Bundle key</th><th>Action key if stored in an action</th></tr>
 *          <tr><td>An (somewhat unique) ID</td><td>(<var>basename</var> itself)</td><td>{@link #ACTION_ID}</td></tr>
 *          <tr><td>The icon</td><td><code><var>basename</var> + ".icon"</code></td><td>{@link Action#SMALL_ICON}</td></tr>
 *          <tr><td>The tooltip help</td><td><code><var>basename</var> + ".shortdescription"</code></td><td>{@link Action#SHORT_DESCRIPTION}</td></tr>
 *          <tr><td>The long help</td><td><code><var>basename</var> + ".longdescription"</code></td><td>{@link Action#LONG_DESCRIPTION}</td></tr>
 *          <tr><td>The text label</td><td><code><var>basename</var> + ".text"</code></td><td>{@link Action#NAME}</td></tr>
 *          <tr><td>The keyboard accelerator</td><td><code><var>basename</var> + ".accel"</code></td><td>{@link Action#ACCELERATOR_KEY}</td></tr>
 *          <tr><td>The alternate keyboard accelerator</td><td><code><var>basename</var> + ".accel2"</code></td><td>{@link #ACCELERATOR_KEY_2}</td></tr>
 *          <tr><td>The mnemonic</td><td><code><var>basename</var> + ".mnemonic"</code></td><td>{@link Action#MNEMONIC_KEY}</td></tr>
 *          <tr><td>The mnemonic index</td><td><code><var>basename</var> + ".mnemonicIdx"</code></tr><td>{@link Action#DISPLAYED_MNEMONIC_INDEX_KEY}</td></tr>
 *          <tr><td>The method name</td><td></td><td>{@link ReflectionAction#REFLECTION_METHOD_NAME}</td></tr>
 *          <tr><td>The method</td><td></td><td>{@link ReflectionAction#REFLECTION_METHOD}</td></tr>
 *          <tr><td>The boolean property name</td><td></td><td>{@link ToggleAction#REFLECTION_PROPERTY_NAME}</td></tr>
 *          <tr><td>The target instance</td><td></td><td>{@link ReflectionAction#REFLECTION_TARGET}</td></tr>
 *          <tr><td>Exception handler dialogs</td><td><code><var>basename</var> + ".exception." + <var>exception class name</var> + ...</code><br/>The message can be formatted with 1 parameter that will be the localized message of the thrown exception.</td><td>n/a</td></tr>
 *      </table>
 *  </li>
 * </ul>
 * <p>Some methods are not related to actions, yet take base keys:</p>
 * <ul>
 *  <li>The methods for dialogs, e.g. {@link #showMessageDialog(Component, String, Object...)}:
 *      <table border="1">
 *          <tr><th>What</th><th>Preferences / Bundle key</th></tr>
 *          <tr><td>Dialog title</td><td><code><var>basename</var> + ".title"</code></td></tr>
 *          <tr><td>Dialog message</td><td><code><var>basename</var> + ".message"</code></td></tr>
 *          <tr><td>Dialog messagetype </td><td><code><var>basename</var> + ".messagetype"</code><br/>The message type should be one of the message types defined in {@link JOptionPane}, e.g. {@link JOptionPane#PLAIN_MESSAGE}.</td></tr>
 *      </table>
 *  </li>
 * </ul>
 * <h4>Final Notes</h4>
 * <ul>
 *  <li>
 *      If by having read all this you think it might often be a good idea to use a package name as a builder name: this is completely right and the
 *      most common way of using an ActionBuilder.
 *  </li>
 *  <li>
 *      If you think you're too lazy to hold your own ActionBuilder reference and instead more often call {@link ActionBuilderFactory#getActionBuilder(String)}, just go ahead
 *      and do so.
 *      Looking up created ActionBuilders is extremely fast, and of course they are initialized exactly once, not more.
 *  </li>
 * </ul>
 * @see AbstractAction
 * @see Action
 * @see Preferences
 * @see ResourceBundle
 * @see ActionBuilderFactory
 * @todo think about toolbar interaction
 * @todo think about toolbar configuration
 * @todo whether a dialog is a onetime dialog should be a property and user configurable
 * @todo Implement serialization.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public interface ActionBuilder {

    /** The key used for storing a somewhat unique id for this Action. */
    @NotNull String ACTION_ID = "ActionID";

    /** The key used for storing an alternative accelerator for this Action.
     * Currently unused.
     */
    @NotNull String ACCELERATOR_KEY_2 = "AcceleratorKey2";

    /** Add a ResourceBundle to the list of used bundles.
     * @param baseName the base name of the resource bundle, a fully qualified class name
     * @see ResourceBundle#getBundle(String)
     */
    void addBundle(@NotNull String baseName);

    /** Get the ActionMap.
     * @return ActionMap
     */
    @NotNull ActionMap getActionMap();

    /** Add a ResourceBundle to the list of used bundles.
     * @param bundle ResourceBundle to add
     * @throws NullPointerException if <code>bundle == null</code>
     */
    void addBundle(@NotNull ResourceBundle bundle) throws NullPointerException;

    /** Add a parent to the list of used parents.
     * @param parent Parent to use if lookups failed in this ActionBuilder
     * WARNING: Adding a descendents as parents of ancestors or vice versa will result in endless recursion and thus stack overflow!
     * @throws NullPointerException if <code>parent == null</code>
     */
    void addParent(@NotNull ActionBuilder parent) throws NullPointerException;

    /** Add a Preferences to the list of used preferences.
     * @param pref Preferences to add
     * @throws NullPointerException if <code>pref == null</code>
     */
    void addPref(@NotNull Preferences pref) throws NullPointerException;

    /** Add a Preferences to the list of used preferences.
     * @param clazz the class whose package a user preference node is desired
     * @see Preferences#userNodeForPackage(Class)
     * @throws NullPointerException if <code>clazz == null</code>
     */
    void addPref(@NotNull Class<?> clazz);

    /** Creates actions.
     * This is a loop variant of {@link #createAction(boolean,String)}.
     * The actions created can be retrieved using {@link #getAction(String)} or via the ActionMap returned by {@link #getActionMap()}.
     * @param store whether to store the initialized Action in the ActionMap of this ActionBuilder
     * @param keys Keys of actions to create
     * @return Array with created actions
     * @throws NullPointerException in case keys is or contains <code>null</code>
     */
    Action[] createActions(boolean store, @NotNull String... keys) throws NullPointerException;

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
    Action createAction(boolean store, @NotNull String key) throws NullPointerException;

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
    @NotNull Action initAction(boolean store, @NotNull Action action, @NotNull String key) throws NullPointerException;

    /** Get a String.
     * First looks one pref after another, in their addition order.
     * Then looks one bundle after another, in their addition order.
     * @param key Key to get String for
     * @return the first value found or <code>null</code> if no value could be found
     * @throws NullPointerException if <var>key</var> is <code>null</code>
     */
    @Nullable String getString(@NotNull String key) throws NullPointerException;

    /** Get a String from the preferences, ignoring the resource bundles.
     * @param key Key to get String for
     * @return the first value found or <code>null</code> if no value could be found
     * @throws NullPointerException if <var>key</var> is <code>null</code>
     */
    @Nullable String getStringFromPrefs(@NotNull String key) throws NullPointerException;

    /** Get a String from the resource bundles, ignoring the preferences.
     * @param key Key to get String for
     * @return the first value found or <code>null</code> if no value could be found
     * @throws NullPointerException if <var>key</var> is <code>null</code>
     */
    @Nullable String getStringFromBundles(@NotNull String key) throws NullPointerException;

    /** Creates actions.
     * This is a loop variant of {@link #createAction(boolean,String,Object)}.
     * The actions created can be retrieved using {@link #getAction(String)} or via the ActionMap returned by {@link #getActionMap()}.
     * @param store whether to store the initialized Action in the ActionMap of this ActionBuilder
     * @param target Target object
     * @param keys Keys of actions to create
     * @return Array with created actions
     * @throws NullPointerException in case keys is or contains <code>null</code>
     */
    Action[] createActions(boolean store, Object target, String... keys) throws NullPointerException;

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
    Action createAction(boolean store, String key, Object object) throws NullPointerException;

    /** Method for creating a Menu.
     * @param store whether to store the initialized Action in the ActionMap of this ActionBuilder
     * @param menuKey action key for Menu
     * @param keys Action keys for menu items
     * @return menu created from the menu definition found
     * @throws Error in case action definitions for <var>keys</var> weren't found
     */
    JMenu createMenu(boolean store, String menuKey, String... keys) throws Error;

    /** Get an Action.
     * For an action to be retrieved with this method, it must have been initialized with {@link #initAction(boolean,Action,String)}, either directly by
     * invoking {@link #initAction(boolean,Action,String)} or indirectly by invoking one of this class' creation methods like {@link #createAction(boolean,String)},
     * {@link #createAction(boolean,String,Object)} or {@link #createToggle(boolean,String,Object)}.
     * @param key Key of action to get
     * @return Action for <var>key</var> or <code>null</code> if the action does not exist.
     * This method does the same as <code>getActionMap().get(key)</code>.
     */
    @Nullable Action getAction(@NotNull String key);

    /** Method for creating a menubar.
     * @param store whether to store the initialized Actions in the ActionMap of this ActionBuilder
     * @param barKey Action key of menu to create
     * @return JMenuBar created for <var>barKey</var>
     * @throws NullPointerException if no menubar definition was found
     * @todo make error handling consistent (see createMenu and others)
     */
    @NotNull JMenuBar createMenuBar(boolean store, String barKey) throws NullPointerException;

    /** Method for creating a popup menu.
     * @param store Whether to store the initialized Actions in the ActionMap of this ActionBuilder.
     * @param popupKey Action key of popup menu to create.
     * @return JPopupMenu created for <var>popupKey</var>
     * @throws MissingResourceException if no menubar definition was found.
     * @todo make error handling consistent (see createMenu and others)
     */
    @NotNull JPopupMenu createPopupMenu(boolean store, String popupKey) throws MissingResourceException;

    /** Method for creating a Menu.
     * This method assumes that the underlying properties contain an entry like <code>key + ".menu"</code> which lists the menu element's keys.
     * Submenus are build recursively.
     * @param store Whether to store the initialized Action in the ActionMap of this ActionBuilder.
     * @param menuKey Action key for menu.
     * @return menu created from the menu definition found.
     * @throws MissingResourceException In case a menu definition for <var>menuKey</var> wasn't found
     */
    JMenu createMenu(boolean store, String menuKey) throws MissingResourceException;

    /** Method for creating a menubar.
     * @param store whether to store the initialized Actions in the ActionMap of this ActionBuilder
     * @param barKey Action key of menu to create
     * @param target Target object
     * @return JMenuBar created for <var>barKey</var>
     * @throws MissingResourceException In case a menu or menubar definition was not found.
     */
    JMenuBar createMenuBar(boolean store, String barKey, Object target) throws MissingResourceException;

    /** Method for creating a popup menu.
     * @param store whether to store the initialized Actions in the ActionMap of this ActionBuilder
     * @param popupKey Action key of popup menu to create
     * @param target Target object
     * @return JPopupMenu created for <var>barKey</var>
     * @throws MissingResourceException In case a menu definition was not found.
     */
    JPopupMenu createPopupMenu(boolean store, String popupKey, Object target) throws MissingResourceException;

    /** Method for creating a Menu.
     * This method assumes that the underlying properties contain an entry like <code>key + ".menu"</code> which lists the menu element's keys.
     * Submenus are build recursively.
     * @param store whether to store the initialized Action in the ActionMap of this ActionBuilder
     * @param menuKey action key for menu
     * @param target Target object
     * @return menu created from the menu definition found
     * @throws Error in case a menu definition for <var>menuKey</var> wasn't found
     */
    JMenu createMenu(boolean store, String menuKey, Object target) throws Error;

    /** Creates actions.
     * This is a loop variant of {@link #createToggle(boolean,String,Object)}.
     * The actions created can be retrieved using {@link #getAction(String)} or via the ActionMap returned by {@link #getActionMap()}.
     * @param store whether to store the initialized Action in the ActionMap of this ActionBuilder
     * @param target Target object
     * @param keys Keys of actions to create
     * @throws NullPointerException in case <var>keys</var> was or contained <code>null</code>
     */
    void createToggles(boolean store, Object target, String... keys) throws NullPointerException;

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
    Action createToggle(boolean store, String key, Object object) throws NullPointerException;

    /** Method for creating a toolbar.
     * @param barKey Action keys of toolbar to create
     * @return JToolBar created for <var>barKey</var>
     * @throws MissingResourceException In case there is no definition for the requested toolbar.
     */
    JToolBar createToolBar(String barKey) throws MissingResourceException;

    /** Method for creating a toolbar.
     * @param object Instance to invoke method on if the Action was activated ({@link Action#actionPerformed(ActionEvent)})
     * @param barKey Action keys of toolbar to create
     * @return JToolBar created for <var>barKey</var>
     * @throws MissingResourceException In case there is no definition for the requested toolbar.
     */
    JToolBar createToolBar(Object object, String barKey) throws MissingResourceException;

    /** Method to find the JMenuItem for a specific Action key.
     * @param menuBar JMenuBar to search
     * @param key Key to find JMenuItem for
     * @return JMenuItem for key or <code>null</code> if none found
     */
    @Nullable JMenuItem find(@NotNull JMenuBar menuBar, @NotNull String key);

    /** Get an icon.
     * @param key i18n key for icon
     * @return icon
     */
    Icon getIcon(String key);

    /** Show a localized message dialog.
     * @param parentComponent determines the Frame in which the dialog is displayed; if <code>null</code>, or if the <code>parentComponent</code> has
     * no <code>Frame</code>, a default <code>Frame</code> is used
     * @param key localization key to use for the title, the message and eventually the icon
     * @param args formatting arguments for the message text
     */
    void showMessageDialog(@Nullable Component parentComponent, @NotNull String key, Object... args);

    /** Get the message type for a dialog.
     * @param dialogKey dialog key
     * @return message type
     */
    int getMessageType(@NotNull String dialogKey);

    /** Formats a message with parameters.
     * It's a proxy method for using {@link MessageFormat}.
     * @param key message key
     * @param args parameters
     * @return formatted String
     * @see MessageFormat#format(String,Object...)
     */
    String format(@NotNull String key, Object... args);

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
    int showOnetimeConfirmDialog(@Nullable Component parentComponent, int optionType, int messageType, @NotNull String key, Object... args) throws IllegalStateException;

    /** Show a localized message dialog which the user can suppress in future.
     * @param parentComponent determines the Frame in which the dialog is displayed; if <code>null</code>, or if the <code>parentComponent</code> has
     * no <code>Frame</code>, a default <code>Frame</code> is used
     * @param messageType the type of message to be displayed
     * @param key localization key to use for the title, the message and eventually the icon
     * @param args formatting arguments for the message text
     * @throws IllegalStateException if no preferences are associated
     */
    void showOnetimeMessageDialog(@Nullable Component parentComponent, int messageType, @NotNull String key, Object... args) throws IllegalStateException;

    /** Show a localized question dialog.
     * @param parentComponent determines the Frame in which the dialog is displayed; if <code>null</code>, or if the <code>parentComponent</code> has
     * no <code>Frame</code>, a default <code>Frame</code> is used
     * @param key localization key to use for the title, the message and eventually the icon
     * @param args formatting arguments for the message text
     * @return <code>true</code> if user confirmed, otherwise <code>false</code>
     */
    boolean showQuestionDialog(Component parentComponent, String key, Object... args);

    /** Show a localized confirmation dialog.
     * @param parentComponent determines the Frame in which the dialog is displayed; if <code>null</code>, or if the <code>parentComponent</code> has
     * no <code>Frame</code>, a default <code>Frame</code> is used
     * @param optionType the option type
     * @param messageType the type of message to be displayed
     * @param key localization key to use for the title, the message and eventually the icon
     * @param args formatting arguments for the message text
     * @return an integer indicating the option selected by the user
     */
    int showConfirmDialog(Component parentComponent, int optionType, int messageType, String key, Object...  args);

    /** Creates a label for a specified key.
     * @param key Key to create label for
     * @param args formatting arguments for the label text
     * @return JLabel for key Key
     * @note the label text will be the key if no String for the key was found
     */
    JLabel createLabel(String key, Object... args);

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
    JLabel createLabel(Component component, String key, Object... args);

    /** Registers an ActionProvider with this ActionBuilder.
     * @param actionProvider ActionProvider to register
     */
    void addActionProvider(ActionProvider actionProvider);

    /** Sets the enabled state of an action.
     * @param key Key of the action to enable.
     * @param enabled New enabled state for that action, <code>true</code> to enable, <code>false</code> to disable.
     */
    void setActionEnabled(@NotNull String key, boolean enabled);
}
