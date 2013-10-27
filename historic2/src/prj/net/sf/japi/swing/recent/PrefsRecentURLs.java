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

package net.sf.japi.swing.recent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.prefs.Preferences;
import javax.swing.event.EventListenerList;

/** Class for managing recent URLs using Preferences.
 * It also manages the list of recent URLs.
 * Warning: The behaviour of creating two PrefsRecentURLs objects for the same class is undefined.
 * @todo perhaps make this class manage its instances
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class PrefsRecentURLs implements RecentURLs {

    /** How many files to show in the last opened list. */
    private int maxLastOpened;

    /** List with recently opened files. */
    private final List<String> recentlyOpenedURLs = new ArrayList<String>();

    /** List with listeners. */
    private final EventListenerList listeners = new EventListenerList();

    /** The Preferences to manage the recent URLs in. */
    private final Preferences prefs;

    /** The PrefsRecentURls. */
    private static final Map<Class<?>, PrefsRecentURLs> INSTANCES = new WeakHashMap<Class<?>, PrefsRecentURLs>();

    /** Create a new RecentURLs object for managing recent URLs.
     * Instances created with this constructor are unmanaged.
     * @param prefs Preferences to use
     */
    public PrefsRecentURLs(final Preferences prefs) {
        this.prefs = prefs;
        load();
    }

    /** Create a new RecentURLs object for managing recent URLs.
     * @param c Class to get Preferences for
     */
    private PrefsRecentURLs(final Class<?> c) {
        prefs = Preferences.userNodeForPackage(c);
        load();
    }

    /** Get an instance of PrefsRecentURLs fitting to the supplied class.
     * @param c Class to get Preferences for
     * @return RecentURLs for Class c
     * @see Preferences#userNodeForPackage(Class) Preferences.userNodeForPackage(Class&lt;?&gt;) which is invoked to get the Preferences instance.
     */
    public static PrefsRecentURLs getInstance(final Class<?> c) {
        PrefsRecentURLs instance = INSTANCES.get(c);
        if (instance == null) {
            INSTANCES.put(c, instance = new PrefsRecentURLs(c));
        }
        return instance;
    }

    /** Load preferences. */
    private void load() {
        maxLastOpened = prefs.getInt("maxLastOpened", 4);
        recentlyOpenedURLs.clear();
        for (int i = 0; i < maxLastOpened; i++) {
            final String file = prefs.get("recentlyOpenedURL[" + i + "]", null);
            if (file == null) {
                break;
            }
            recentlyOpenedURLs.add(file);
        }
    }

    /** Save preferences. */
    private void save() {
        prefs.putInt("maxLastOpened", maxLastOpened);
        for (int i = 0, size = recentlyOpenedURLs.size(); i < size; i++) {
            prefs.put("recentlyOpenedURL[" + i + "]", recentlyOpenedURLs.get(i));
        }
    }

    /** Get the maximum number of files to show in the last opened list.
     * @return maximum number of files to show in the last opened list.
     */
    public int getMaxLastOpened() {
        return maxLastOpened;
    }

    /** Set the maximum number of files to show in the last opened list.
     * @param maxLastOpened maximum number of files to show in the last opened list.
     */
    public void setMaxLastOpened(final int maxLastOpened) {
        this.maxLastOpened = maxLastOpened;
    }

    /** {@inheritDoc} */
    public void addRecentlyURL(final String url) {
        if (recentlyOpenedURLs.contains(url)) {
            recentlyOpenedURLs.remove(url);
        }
        recentlyOpenedURLs.add(url);
        if (recentlyOpenedURLs.size() > maxLastOpened) {
            recentlyOpenedURLs.remove(0);
        }
        save();
        fireREcentURlsChanged();
    }

    /** Get the recently opened files.
     * @return recently opened files
     */
    public String[] getRecentlyURLs() {
        return recentlyOpenedURLs.toArray(new String[recentlyOpenedURLs.size()]);
    }

    /** {@inheritDoc} */
    public void addRecentURLsListener(final RecentURLsListener listener) {
        listeners.add(RecentURLsListener.class, listener);
    }

    /** {@inheritDoc} */
    public void removeRecentURLsListener(final RecentURLsListener listener) {
        listeners.remove(RecentURLsListener.class, listener);
    }

    /** Inform all registered listeners that the list of recent urls changed. */
    private void fireREcentURlsChanged() {
        final RecentURLsEvent e = new RecentURLsEvent(this);
        for (final RecentURLsListener listener : listeners.getListeners(RecentURLsListener.class)) {
            listener.recentURLsChanged(e);
        }
    }

} // class PrefsRecentURLs
