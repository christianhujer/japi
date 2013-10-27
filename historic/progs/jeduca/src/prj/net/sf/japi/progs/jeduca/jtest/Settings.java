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

package net.sf.japi.progs.jeduca.jtest;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;
import net.sf.japi.swing.recent.RecentURLs;
import net.sf.japi.swing.recent.RecentURLsEvent;
import net.sf.japi.swing.recent.RecentURLsListener;

/** Class representing the settings for JTest.
 * It also manages the list of recent URLs.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class Settings implements RecentURLs {

    /** Whether to show the solution for each question. */
    private boolean showQuestionSolution;

    /** How many files to show in the last opened list. */
    private int maxLastOpened;

    /** List with recently opened files. */
    private final List<String> recentlyOpenedURLs = new ArrayList<String>();

    /** List with listeners. */
    private final List<RecentURLsListener> listeners = new ArrayList<RecentURLsListener>();

    /** Create a Settings with default values. */
    public Settings() {
        resetToDefaults();
    }

    /** Reset Settings to default values. */
    public void resetToDefaults() {
        showQuestionSolution = false;
        maxLastOpened = 4;
    }

    /** Load preferences. */
    public void load() {
        final Preferences prefs = Preferences.userNodeForPackage(getClass());
        showQuestionSolution = prefs.getBoolean("showQuestionSolution", false);
        maxLastOpened = prefs.getInt("maxLastOpened", 4);
        recentlyOpenedURLs.clear();
        for (int i = 0; i < maxLastOpened; i++) {
            final String file = prefs.get("recentlyOpenedURL[" + i + ']', null);
            if (file == null) {
                break;
            }
            recentlyOpenedURLs.add(file);
        }
    }

    /** Save preferences. */
    public void save() {
        final Preferences prefs = Preferences.userNodeForPackage(getClass());
        prefs.putBoolean("showQuestionSolution", showQuestionSolution);
        prefs.putInt("maxLastOpened", maxLastOpened);
        for (int i = 0, size = recentlyOpenedURLs.size(); i < size; i++) {
            prefs.put("recentlyOpenedURL[" + i + ']', recentlyOpenedURLs.get(i));
        }
    }

    /** Tell whether to show the solution for each question.
     * @return boolean
     */
    public boolean isShowQuestionSolution() {
        return showQuestionSolution;
    }

    /** Set whether to show the solution for each question
     * @param showQuestionSolution whether to show the solution for each question
     */
    public void setShowQuestionSolution(final boolean showQuestionSolution) {
        this.showQuestionSolution = showQuestionSolution;
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
        fireRecentURLsChanged();
    }

    /** Get the recently opened files.
     * @return recently opened files
     */
    public String[] getRecentlyURLs() {
        return recentlyOpenedURLs.toArray(new String[recentlyOpenedURLs.size()]);
    }

    /** {@inheritDoc} */
    public void addRecentURLsListener(final RecentURLsListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    /** {@inheritDoc} */
    public void removeRecentURLsListener(final RecentURLsListener listener) {
        listeners.remove(listener);
    }

    /** Inform all registered listeners that the list of recent urls changed. */
    private void fireRecentURLsChanged() {
        final RecentURLsEvent e = new RecentURLsEvent(this);
        for (final RecentURLsListener listener : listeners) {
            listener.recentURLsChanged(e);
        }
    }

} // class Settings
