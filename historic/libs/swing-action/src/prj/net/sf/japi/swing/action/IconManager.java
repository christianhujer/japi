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

import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** Class to handle icons.
 * Default size is 16.
 * Instances must have an associated ClassLoader, otherwise several methods will not work properly but throw a NullPointerException instead.
 * So if you do not provide a ClassLoader, be sure the class you provide has one, or if you use the no-arg constructor resp. the default instance, be
 * sure the IconManager class itself was loaded with some ClassLoader other than <code>null</code>.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 * @todo this class should be refactored into a more generic version and accessible through ActionBuilder?
 * @todo it should be possible to initialize the paths through properties
 */
public final class IconManager {

    /** The default IconManager. */
    @NotNull
    private static final IconManager DEFAULT_ICON_MANAGER = createDefaultIconManager();

    /** Create the default IconManager.
     * @return default IconManager
     */
    @NotNull
    private static IconManager createDefaultIconManager() {
        final IconManager defaultIconManager = new IconManager();
        defaultIconManager.iconPaths.add("icons/");
        defaultIconManager.iconPaths.add("toolbarButtonGraphics/");
        return defaultIconManager;
    }

    /** ClassLoader to get icons from, must not be null. */
    @NotNull
    private final ClassLoader classLoader;

    /** The available sizes provided by this IconManager. */
    @NotNull
    private final int[] availableSizes = { 16, 24 };

    /** The icon cache.
     * Key: short name for icon, which is likely to be used as a relative file name.
     * Value: Icon
     */
    @NotNull
    private final Map<String, Icon> smallCache = new WeakHashMap<String, Icon>();

    /** The paths to search icons in. */
    @NotNull
    private final List<String> iconPaths = new ArrayList<String>();

    /** Get the default IconManager.
     * The ClassLoader in use is the classloader IconManager was loaded with, whatever classloader that was.
     * @return default IconManaager
     */
    @NotNull
    public static IconManager getDefaultIconManager() {
        return DEFAULT_ICON_MANAGER;
    }

    /** Create a IconManager.
     * Uses the IconManager's class loader.
     * Only use this if you want to be independent of the global icon size settings.
     * The recommended way to get a default IconManager instance is {#getDefaultIconManager()}.
     */
    public IconManager() {
        this(getContextClassLoader());
        //this(IconManager.class.getClassLoader());
    }

    @NotNull
    private static ClassLoader getContextClassLoader() {
        return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {
            public ClassLoader run() {
                try {
                    return Thread.currentThread().getContextClassLoader();
                } catch (final SecurityException ignore) {
                    return getClass().getClassLoader();
                }
            }
        });
    }

    /** Create an IconManager.
     * @param clazz Class to get ClassLoader for IconManager
     */
    public IconManager(@NotNull final Class<?> clazz) {
        classLoader = clazz.getClassLoader();
    }

    /** Create an IconManager.
     * @param cl ClassLoader to create IconManager for
     */
    public IconManager(@NotNull final ClassLoader cl) {
        classLoader = cl;
    }

    /** Return the available sizes for icons.
     * @return available icon sizes
     */
    @NotNull
    public int[] getAvailableSizes() {
        return availableSizes.clone();
    }

    /** Load an icon.
     * @param s icon name, like "general/About" or "navigation/Forward"
     * @return Icon for <var>s</var>
     */
    @Nullable
    public Icon getIcon(@NotNull final String s) {
        Icon icon = smallCache.get(s);
        if (icon == null) {
            URL url = null;
            // Search the configured class loader
            for (final Iterator<String> it = iconPaths.iterator(); url == null && it.hasNext();) {
                final String path = it.next();
                final String iconPath = path + (path.endsWith("/") ? "" : "/") + s + ".gif";
                url = classLoader.getResource(iconPath);
            }
            if (url == null) {
                // if searching the configured class loader failed, search the system class loader
                for (final Iterator<String> it = iconPaths.iterator(); url == null && it.hasNext();) {
                    final String path = it.next();
                    final String iconPath = path + (path.endsWith("/") ? "" : "/") + s + ".gif";
                    url = ClassLoader.getSystemResource(iconPath);
                }
            }
            if (url == null) {
                return null;
            }
            icon = new ImageIcon(url);
            smallCache.put(s, icon);
        }
        return icon;
    }

} // class IconManager
