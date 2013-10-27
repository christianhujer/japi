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
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** The DummyActionBuilder is an implementation of {@link ActionBuilder} that actually does not provide any features.
 * All methods are implemented empty.
 * The purpose of the DummyActionBuilder is to be subclassed for the creation of MockActionBuilders for unit testing.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class DummyActionBuilder implements ActionBuilder {

    /** The ActionMap.
     * The DummyActionBuilder at least needs an ActionMap to fulfil the contract of ActionBuilder regarding nullability.
     */
    private final ActionMap actionMap = new ActionMap();

    /** {@inheritDoc} */
    public void addBundle(@NotNull final String baseName) {
    }

    /** {@inheritDoc} */
    @NotNull public ActionMap getActionMap() {
        return actionMap;
    }

    /** {@inheritDoc} */
    public void addBundle(@NotNull final ResourceBundle bundle) throws NullPointerException {
    }

    /** {@inheritDoc} */
    public void addParent(@NotNull final ActionBuilder parent) throws NullPointerException {
    }

    /** {@inheritDoc} */
    public void addPref(@NotNull final Preferences pref) throws NullPointerException {
    }

    /** {@inheritDoc} */
    public void addPref(@NotNull final Class<?> clazz) {
    }

    /** {@inheritDoc} */
    public Action[] createActions(final boolean store, @NotNull final String... keys) throws NullPointerException {
        return new Action[0];
    }

    /** {@inheritDoc} */
    public Action createAction(final boolean store, @NotNull final String key) throws NullPointerException {
        return null;
    }

    /** {@inheritDoc} */
    @SuppressWarnings({"NestedAssignment"}) @NotNull
    public Action initAction(final boolean store, @NotNull final Action action, @NotNull final String key) throws NullPointerException {
        return action;
    }

    /** {@inheritDoc} */
    @Nullable public String getString(@NotNull final String key) throws NullPointerException {
        return null;
    }

    /** {@inheritDoc} */
    @Nullable public String getStringFromPrefs(@NotNull final String key) throws NullPointerException {
        return null;
    }

    /** {@inheritDoc} */
    @Nullable public String getStringFromBundles(@NotNull final String key) throws NullPointerException {
        return null;
    }

    /** {@inheritDoc} */
    public Action[] createActions(final boolean store, final Object target, final String... keys) throws NullPointerException {
        return new Action[0];
    }

    /** {@inheritDoc} */
    public Action createAction(final boolean store, final String key, final Object object) throws NullPointerException {
        return null;
    }

    /** {@inheritDoc} */
    public JMenu createMenu(final boolean store, final String menuKey, final String... keys) throws Error {
        return null;
    }

    /** {@inheritDoc} */
    @Nullable public Action getAction(@NotNull final String key) {
        return null;
    }

    /** {@inheritDoc} */
    @NotNull public JMenuBar createMenuBar(final boolean store, final String barKey) throws NullPointerException {
        return new JMenuBar();
    }

    /** {@inheritDoc} */
    @NotNull public JPopupMenu createPopupMenu(final boolean store, final String popupKey) throws MissingResourceException {
        return new JPopupMenu();
    }

    /** {@inheritDoc} */
    public JMenu createMenu(final boolean store, final String menuKey) throws MissingResourceException {
        return null;
    }

    /** {@inheritDoc} */
    public JMenuBar createMenuBar(final boolean store, final String barKey, final Object target) throws MissingResourceException {
        return null;
    }

    /** {@inheritDoc} */
    public JPopupMenu createPopupMenu(final boolean store, final String popupKey, final Object target) throws MissingResourceException {
        return null;
    }

    /** {@inheritDoc} */
    public JMenu createMenu(final boolean store, final String menuKey, final Object target) throws Error {
        return null;
    }

    /** {@inheritDoc} */
    public void createToggles(final boolean store, final Object target, final String... keys) throws NullPointerException {
    }

    /** {@inheritDoc} */
    public Action createToggle(final boolean store, final String key, final Object object) throws NullPointerException {
        return null;
    }

    /** {@inheritDoc} */
    public JToolBar createToolBar(final String barKey) throws MissingResourceException {
        return null;
    }

    /** {@inheritDoc} */
    public JToolBar createToolBar(final Object object, final String barKey) throws MissingResourceException {
        return null;
    }

    /** {@inheritDoc} */
    @Nullable public JMenuItem find(@NotNull final JMenuBar menuBar, @NotNull final String key) {
        return null;
    }

    /** {@inheritDoc} */
    public Icon getIcon(final String key) {
        return null;
    }

    /** {@inheritDoc} */
    public void showMessageDialog(@Nullable final Component parentComponent, @NotNull final String key, final Object... args) {
    }

    /** {@inheritDoc} */
    public int getMessageType(@NotNull final String dialogKey) {
        return 0;
    }

    /** {@inheritDoc} */
    public String format(@NotNull final String key, final Object... args) {
        return null;
    }

    /** {@inheritDoc} */
    public int showOnetimeConfirmDialog(@Nullable final Component parentComponent, final int optionType, final int messageType, @NotNull final String key, final Object... args) throws IllegalStateException {
        return 0;
    }

    /** {@inheritDoc} */
    public void showOnetimeMessageDialog(@Nullable final Component parentComponent, final int messageType, @NotNull final String key, final Object... args) throws IllegalStateException {
    }

    /** {@inheritDoc} */
    public boolean showQuestionDialog(final Component parentComponent, final String key, final Object... args) {
        return false;
    }

    /** {@inheritDoc} */
    public int showConfirmDialog(final Component parentComponent, final int optionType, final int messageType, final String key, final Object... args) {
        return 0;
    }

    /** {@inheritDoc} */
    public JLabel createLabel(final String key, final Object... args) {
        return null;
    }

    /** {@inheritDoc} */
    public JLabel createLabel(final Component component, final String key, final Object... args) {
        return null;
    }

    /** {@inheritDoc} */
    public void addActionProvider(final ActionProvider actionProvider) {
    }

    /** {@inheritDoc} */
    public void setActionEnabled(@NotNull final String key, final boolean enabled) {
    }

} // class DummyActionBuilder
