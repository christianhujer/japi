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

/**
 * This package contains useful Swing extension classes about handling user interfaces to user preferences.
 * <p/>
 * The recommended way in using this package is:
 * <ol>
 *  <li>
 *      Create instances of {@link net.sf.japi.swing.prefs.Prefs}
 *      <p />
 *      For this, you can do either or both:
 *      <ul>
 *          <li>subclass {@link net.sf.japi.swing.prefs.AbstractPrefs}</li>
 *          <li>implement {@link net.sf.japi.swing.prefs.Prefs}</li>
 *      </ul>
 *      The most important methods of {@link net.sf.japi.swing.prefs.Prefs} are {@link net.sf.japi.swing.prefs.Prefs#isChanged()},
 *      {@link net.sf.japi.swing.prefs.Prefs#defaults()}, {@link net.sf.japi.swing.prefs.Prefs#revert()} and
 *      {@link net.sf.japi.swing.prefs.Prefs#apply()}.
 *      You implement these methods to get notified about the user's wishes about his / her input.
 *  </li>
 *  <li>
 *      Create a {@link net.sf.japi.swing.prefs.PreferencesGroup} that contains these Prefs objects.
 *  </li>
 *  <li>
 *      Invoke
 *      {@link net.sf.japi.swing.prefs.PreferencesPane#showPreferencesDialog(java.awt.Component,net.sf.japi.swing.prefs.PreferencesGroup,boolean)}
 *      to let the user change the preferences.
 *  </li>
 * </ol>
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
package net.sf.japi.swing.prefs;
