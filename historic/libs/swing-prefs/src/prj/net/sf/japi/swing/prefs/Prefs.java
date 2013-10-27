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

package net.sf.japi.swing.prefs;

import java.net.URL;
import javax.swing.Icon;
import javax.swing.JComponent;

/** Interface that is to be implemented by classes that provide preferences.
 * <p />
 * Often, implementations of this interface will subclass JComponent or JPanel.
 * In that case {@link #getEditComponent()} will <code>return this</code>.
 * {@link AbstractPrefs} provides a useful basic implementation of this interface.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public interface Prefs {

    /** Applies the changes in the UI to get into effect / be stored.
     * The implementation should read all widget states and apply the corresponding settings on the application.
     */
    void apply();

    /** Reverts the preferences to their default values.
     * The implementation should reset all widgets to a default state as if neither there were preferences nor the user changed anything.
     */
    void defaults();

    /** Provides a component for editing the prefs.
     * The edit component MUST NOT automatically change preferences itself.
     * Preferences MUST only changed when the method {@link #apply()} is invoked.
     * @return component for editing the preferences.
     */
    JComponent getEditComponent();

    /** Provide help (HTML).
     * This method will only be queried if {@link #getHelpURL()} returns <code>null</code>.
     * This method may return <code>null</code> as well, which means that this prefs does not provide any help.
     * @return help text (HTML) or <code>null</code>
     */
    String getHelpText();

    /** Provide help.
     * This method may return <code>null</code> in which case the method {@link #getHelpText()} will be queried instead.
     * @return help url or <code>null</code>
     */
    URL getHelpURL();

    /** Provide text to be displayed as title for this prefs module.
     * @return title of this prefs module
     */
    String getLabelText();

    /** Provide an icon to be displayed in the list where the user can choose amongst preferences.
     * @return icon to be displayed in the list or <code>null</code> if no icon is available
     */
    Icon getListLabelIcon();

    /** Provide a label to be displayed in the list where the user can choose amongst preferences.
     * @return text to be displayed in the list
     */
    String getListLabelText();

    /** Check whether there are unsaved changes.
     * @return <code>true</code> if there are unsaved changes, otherwise <code>false</code>
     */
    boolean isChanged();

    /** Revert the preferences to the previously stored settings. */
    void revert();

} // class Prefs
