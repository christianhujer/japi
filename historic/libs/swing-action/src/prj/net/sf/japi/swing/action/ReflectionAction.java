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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import net.sf.japi.lang.SuperClassIterable;
import static net.sf.japi.swing.action.ActionBuilder.ACTION_ID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** Action implementation which invokes the desired method using Reflection.
 * Usage example:
 * <pre>
 *  class SomeClass {
 *      SomeClass() {
 *          ReflectionAction action = new ReflectionAction();
 *          action.putValue(REFLECTION_TARGET, this);
 *          action.putValue(REFLECTION_METHOD_NAME, "myAction");
 *          new JMenuItem(action);
 *      }
 *      void myAction() {
 *          // do something
 *      }
 *  }
 * </pre>
 * Note that because of Reflection this Action is slightly slower than implementing your own Action instance, but in most cases this really does not matter at all.
 * Usually you won't use ReflectionAction yourself. Instead you'll let {@link ActionBuilder} create an instance for you.
 * <p />
 * You can use {@link #REFLECTION_ARGUMENTS} for providing arguments for the action method that's called via Reflection.
 * Because that Object[] is not cloned, you can keep the reference and dynamically change the arguments of the invoked method.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 * @todo ReflectionAction should be able to invoke methods with parameters, three variants: Object..., ActionEvent or void
 */
public final class ReflectionAction extends AbstractAction {

    /** The empty object array used to denote zero arguments. */
    private static final Object[] NO_ARGUMENTS = new Object[0];

    /** Action Builder for reading strings. */
    private static final ActionBuilder ACTION_BUILDER = ActionBuilderFactory.getInstance().getActionBuilder("net.sf.japi.swing.action");

    /** The key used for storing the target object to invoke the method on.
     * Value Type: {@link Object}.
     */
    public static final String REFLECTION_TARGET = "ReflectionTarget";

    /** The key used for storing the method key to use when searching for a method using reflection.
     * Value Type: {@link String} (checked).
     */
    public static final String REFLECTION_METHOD_NAME = "ReflectionMethodName";

    /** The key used for storing the method object to use when invoking the method.
     * Value Type: {@link Method} (checked).
     */
    public static final String REFLECTION_METHOD = "ReflectionMethod";

    /**
     * The key used for storing the method arguments to use when invoking the method.
     * Value Type: {@link Object Object[]} (checked).
     */
    public static final String REFLECTION_ARGUMENTS = "ReflectionArguments";

    /** The key used for storing the action builder that provides access to error handlers strings. */
    public static final String REFLECTION_MESSAGE_PROVIDER = "ActionBuilder";

    /** Serial Version. */
    @SuppressWarnings({"AnalyzingVariableNaming"})
    private static final long serialVersionUID = 1L;

    /** Create an uninitialized ReflectionAction. */
    public ReflectionAction() {
        putValue(REFLECTION_ARGUMENTS, NO_ARGUMENTS);
    }

    /** Create a ReflectionAction with method and target.
     * @param methodName Name of method to invoke
     * @param target Target object to invoke method at
     */
    public ReflectionAction(final String methodName, final Object target) {
        putValue(REFLECTION_ARGUMENTS, NO_ARGUMENTS);
        putValue(REFLECTION_METHOD_NAME, methodName);
        putValue(REFLECTION_TARGET, target);
    }

    /** Defines an <code>Action</code> object with the specified description string and a the specified icon.
     * @param name description string
     * @param icon icon
     * @param methodName Name of method to invoke
     * @param target Target object to invoke method at
     */
    public ReflectionAction(final String name, final Icon icon, final String methodName, final Object target) {
        super(name, icon);
        putValue(REFLECTION_ARGUMENTS, NO_ARGUMENTS);
        putValue(REFLECTION_METHOD_NAME, methodName);
        putValue(REFLECTION_TARGET, target);
    }

    /** {@inheritDoc}
     * This implementation checks the type of <var>newValue</var> if the <var>key</var> is {@link #REFLECTION_METHOD_NAME} or {@link
     * #REFLECTION_METHOD}, so you'll know of errors quite soon.
     * @throws IllegalArgumentException if <var>newValue</var> is of the wrong type
     */
    @Override public void putValue(final String key, final Object newValue) throws IllegalArgumentException {
        if (REFLECTION_METHOD_NAME.equals(key)) {
            if (!(newValue == null || newValue instanceof String)) {
                throw new IllegalArgumentException("Value for key REFLECTION_METHOD_NAME must be of type " + String.class.getName() + " but was " + newValue.getClass().getName());
            }
            putValue(REFLECTION_METHOD, null);
        }
        if (REFLECTION_METHOD.equals(key)) {
            if (!(newValue == null || newValue instanceof Method)) { // NOPMD
                if (newValue instanceof String) {
                    throw new IllegalArgumentException("Value for key REFLECTION_METHOD must be of type " + Method.class.getName() + " but was " + String.class.getName() + " so you might want to use the key REFLECTION_METHOD_NAME instead.");
                } else {
                    throw new IllegalArgumentException("Value for key REFLECTION_METHOD must be of type " + Method.class.getName() + " but was " + newValue.getClass().getName());
                }
            }
        }
        if (REFLECTION_TARGET.equals(key)) {
            if (newValue == null) { // NOPMD
                putValue(REFLECTION_METHOD, null);
            }
        }
        if (REFLECTION_ARGUMENTS.equals(key)) {
            if (newValue != null && !(newValue instanceof Object[])) {
                throw new IllegalArgumentException("Value for key REFLECTION_ARGUMENTS must be of type " + Object[].class.getName() + " but was " + newValue.getClass().getName());
            }
            super.putValue(key, newValue == null ? NO_ARGUMENTS : newValue);
        } else {
            super.putValue(key, newValue);
        }
    }

    /** {@inheritDoc}
     * The implementation of this method first looks whether the Action is enabled.
     * If it isn't, the method simply returns.
     * Otherwise, instance and method are looked up.
     * If both are null, the method again returns.
     * If the method is null, it is reflected upon the instance usign the method name. If the method name is null, the method returns.
     * Finally the method is invoked upon the instance, which may be null for static methods.
     * @throws RuntimeException with cause in case the invocation of the method threw an exception and there was no handler for that exception.
     * @throws IllegalAccessError In case the target method is non-public.
     */
    public void actionPerformed(final ActionEvent e) throws RuntimeException {
        if (!isEnabled()) { return; }
        final Object instance = getValue(REFLECTION_TARGET);
        try {
            // TODO:2009-02-15:christianhujer:Special value for REFLECTION_ARGUMENTS which determines whether to use ActionEvent.
            final Method method = getMethod(instance);
            if (method != null) {
                final Object[] arguments = getArguments(method, e);
                method.invoke(instance, arguments);
            } else {
                // TODO:2009-02-15:christianhujer:Improve logging / debugging
                System.err.println("Missing implementation for action " + getValue(ACTION_ID));
            }
        } catch (final IllegalAccessException ex) {
            throw new IllegalAccessError(ACTION_BUILDER.format("ReflectionAction.nonPublicMethod", ex));
        } catch (final InvocationTargetException ex) {
            final ActionBuilder actionBuilder = (ActionBuilder) getValue(REFLECTION_MESSAGE_PROVIDER);
            final Throwable cause = ex.getCause();
            if (actionBuilder != null) {
                for (final Class<?> c : new SuperClassIterable(cause.getClass())) {
                    final String dialogKey = getValue(ACTION_ID) + ".exception." + c.getName();
                    final String title = actionBuilder.getString(dialogKey + ".title");
                    if (title != null) {
                        // source cannot be null because the ActionEvent constructor will not allow a null source.
                        @NotNull final Object source = e.getSource();
                        final Component parent = source instanceof Component ? (Component) source : null; // TODO:2009-02-15:christianhujer:find better alternative to null
                        actionBuilder.showMessageDialog(parent, dialogKey, cause.getLocalizedMessage());
                        return;
                    }
                }
            }
            //noinspection ThrowInsideCatchBlockWhichIgnoresCaughtException
            throw new RuntimeException("No exception handler found. For good default handling, specify a dialog for " + getValue(ACTION_ID) + ".exception.FullyQualifiedExceptionClassName with at least title and message.", ex.getCause());
        }
    }

    /**
     * Returns the arguments suited for invoking the specified method.
     * @param method Method to invoke
     * @param e ActionEvent which eventually might be used as method argument.
     * @return Argument array.
     */
    @NotNull private Object[] getArguments(@NotNull final Method method, @NotNull final ActionEvent e) {
        final Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length == 1 && parameterTypes[0].equals(ActionEvent.class)) {
            return new Object[] { e };
        }
        return (Object[]) getValue(REFLECTION_ARGUMENTS);
    }

    /** Get the method associated with this action.
     * @param instance Instance to get method for
     * @return associated method or <code>null</code> if no method could be found
     */
    @Nullable private Method getMethod(final Object instance) {
        if (instance == null) {
            return null;
        }
        Method method = (Method) getValue(REFLECTION_METHOD);
        if (method == null) {
            final String methodName = (String) getValue(REFLECTION_METHOD_NAME);
            if (methodName == null) {
                return null;
            }
            try {
                method = getActionMethod(instance, methodName);
                putValue(REFLECTION_METHOD, method);
            } catch (final NoSuchMethodException ex) {
                assert false : "Action Method not found: " + ex;
                return null;
            }
        }
        return method;
    }

    /** Get the named action method from the target class.
     * @param clazz Class to search for method
     * @param methodName Method name to get
     * @return Method found
     * @throws NoSuchMethodException In case the method was not found.
     */
    @Nullable public static Method getActionMethod(@NotNull final Class<?> clazz, @NotNull final String methodName) throws NoSuchMethodException {
        // First search for explicit ActionMethods.
        for (final Method method : clazz.getMethods()) {
            if (method.isAnnotationPresent(ActionMethod.class) && (method.getName().equals(methodName) || method.getAnnotation(ActionMethod.class).value().equals(methodName))) {
                return method;
            }
        }
        // Second, if no explicit ActionMethod was found, try implicit.
        // This could be improved.
        for (final Method method : clazz.getMethods()) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        throw new NoSuchMethodException(methodName + " (error: no public method named " + methodName + " and annotated as @ActionMethod found)");
    }

    /** Get the named action method from the target object.
     * @param target Object to search for method
     * @param methodName Method name to get
     * @return Method found
     * @throws NoSuchMethodException In case the method was not found or is not annotated as @{@link ActionMethod}.
     */
    @Nullable public static Method getActionMethod(@NotNull final Object target, @NotNull final String methodName) throws NoSuchMethodException {
        return getActionMethod(target.getClass(), methodName);
    }

    /** {@inheritDoc} */
    @Override protected Object clone() throws CloneNotSupportedException { // NOPMD
        return super.clone();
    }

} // class ReflectionAction
