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

/** Interface for classes that manage recent URLs.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 * @see RecentURLsEvent
 * @see RecentURLsListener
 * @todo enhance this to support a title.
 */
public interface RecentURLs {

    /** Get the maximum number of URLs shown in the list of recently opened URLs.
     * @return maximum URL number
     */
    int getMaxLastOpened();

    /** Set the maximum number of entries in the list of recently opened URLs.
     * @param maxLastOpened maximum number of URL entries
     */
    void setMaxLastOpened(int maxLastOpened);

    /** Add a URL to the list of recently opened URLs.
     * @param url URL to add
     */
    void addRecentlyURL(final String url);

    /** Get the recently opened URLs.
     * @return recently opened URLs
     */
    String[] getRecentlyURLs();

    /** Adds a <code>RecentURLs</code> listener.
     * Objects use this method to register interest in this RecentURLs object.
     * When the list of recent urls changes, the registered listeners are informed of the change.
     * @param listener a <code>RecentURLsListener</code> object
     */
    void addRecentURLsListener(RecentURLsListener listener);

    /** Remocves a <code>RecentURLs</code> listener.
     * @param listener a <code>RecentURLsListener</code> object
     */
    void removeRecentURLsListener(RecentURLsListener listener);

} // interface RecentURLs
