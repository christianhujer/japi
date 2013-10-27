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

import java.util.Map;
import java.util.ResourceBundle;
import java.util.WeakHashMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** An ActionBuilderFactory provides implementations of {@link ActionBuilder}.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public abstract class ActionBuilderFactory {

    /** The ActionBuilders. */
    @NotNull private final Map<String, ActionBuilder> actionBuilders = new WeakHashMap<String, ActionBuilder>();

    /** The ActionBuilderFactory default instance. */
    private static final ActionBuilderFactory INSTANCE = new ActionBuilderFactory() {};

    /** Create an ActionBuilderFactory.
     * @return An ActionBuilderFactory.
     */
    public static ActionBuilderFactory getInstance() {
        return INSTANCE;
    }

    /** Returns an ActionBuilder for the package of the specified class.
     * @param clazz Class for which to return the action builder.
     * @return ActionBuilder for the given class. The builder is created in case it didn't already exist.
     */
    @NotNull public ActionBuilder getActionBuilder(@NotNull final Class<?> clazz) {
        return getActionBuilder(clazz.getPackage().getName());
    }

    /** Get an ActionBuilder.
     * If there is no ActionBuilder with name <var>key</var>, a new ActionBuilder is created and stored.
     * Future invocations of this method will constantly return that ActionBuilder unless the key is garbage collected.
     * If you must prevent the key from being garbage collected (and with it the ActionBuilder), you may internalize the key ({@link String#intern()}).
     * A good name for a key is the application or package name.
     * The <code><var>key</var></code> may be a package name, in which case it is tried to load a {@link ResourceBundle} named "action" from that
     * package and add it ({@link ActionBuilder#addBundle(ResourceBundle)}); nothing special happens if that fails.
     * @param baseName Name of ActionBuilder (which even may be <code>null</code> if you are too lazy to invent a key)
     * @return ActionBuilder for given key. The builder is created in case it didn't already exist.
     */
    @NotNull public ActionBuilder getActionBuilder(@Nullable final String baseName) {
        ActionBuilder builder = actionBuilders.get(baseName);
        if (builder == null) {
            builder = new DefaultActionBuilder(baseName);
            actionBuilders.put(baseName, builder);
        }
        return builder;
    }

    /** Stores an ActionBuilder.
     * In regular programs, an invocation of this operation should not be needed.
     * This operation is designed for tests that want to provide non-default ActionBuilders as Dummies, Stubs or Mocks.
     * @param baseName Basename to store an ActionBuilder for.
     * @param actionBuilder ActionBuilder to store.
     */
    public void putActionBuilder(@Nullable final String baseName, @NotNull final ActionBuilder actionBuilder) {
        actionBuilders.put(baseName, actionBuilder);
    }

} // class ActionBuilderFactory
