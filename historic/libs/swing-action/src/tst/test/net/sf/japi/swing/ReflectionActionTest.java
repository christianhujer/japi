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

package test.net.sf.japi.swing;

import java.awt.event.ActionEvent;
import java.lang.reflect.Method;
import net.sf.japi.swing.action.ActionBuilder;
import net.sf.japi.swing.action.ActionBuilderFactory;
import net.sf.japi.swing.action.ActionMethod;
import net.sf.japi.swing.action.ReflectionAction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/** Test for {@link ReflectionAction}.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class ReflectionActionTest {

    /** Action Builder. */
    private static final ActionBuilder ACTION_BUILDER = ActionBuilderFactory.getInstance().getActionBuilder("test.net.sf.japi.swing");

    /** The testling: A ReflectionAction. */
    private ReflectionAction testling;

    /** The Action Mock. */
    private ActionMock actionMock;

    /** Create the testling. */
    @Before
    public void setUpTestling() {
        actionMock = new ActionMock();
        testling = (ReflectionAction) ACTION_BUILDER.createAction(false, "someAction", actionMock);
    }

    /** Remove the testling. */
    @SuppressWarnings({"AssignmentToNull"})
    @After
    public void tearDown() {
        testling = null;
    }

    /**
     * Tests that {@link ReflectionAction#putValue(String, Object)} works.
     * @throws Exception (unexpected)
     */
    @Test(expected = IllegalArgumentException.class)
    public void testPutValueArgumentsThrows() throws Exception {
        testling.putValue(ReflectionAction.REFLECTION_ARGUMENTS, new Object());
    }

    /**
     * Tests that {@link ReflectionAction#putValue(String, Object)} works.
     * @throws Exception (unexpected)
     */
    @Test(expected = IllegalArgumentException.class)
    public void testPutValueMethodNameThrows() throws Exception {
        testling.putValue(ReflectionAction.REFLECTION_METHOD_NAME, new Object());
    }

    /**
     * Tests that {@link ReflectionAction#putValue(String, Object)} works.
     * @throws Exception (unexpected)
     */
    @Test
    public void testPutValueMethodNameNull() throws Exception {
        testling.putValue(ReflectionAction.REFLECTION_METHOD_NAME, null);
        Assert.assertNull("After setting the method name to null method name must be null.", testling.getValue(ReflectionAction.REFLECTION_METHOD_NAME));
        Assert.assertNull("After setting the method name to null method must be null.", testling.getValue(ReflectionAction.REFLECTION_METHOD));
    }

    /**
     * Tests that {@link ReflectionAction#putValue(String, Object)} works.
     * @throws Exception (unexpected)
     */
    @Test
    public void testPutValueMethodName() throws Exception {
        testling.putValue(ReflectionAction.REFLECTION_METHOD_NAME, "foo");
        Assert.assertEquals("After setting the method name method name must be stored.", "foo", testling.getValue(ReflectionAction.REFLECTION_METHOD_NAME));
        Assert.assertNull("After setting the method name method must be null.", testling.getValue(ReflectionAction.REFLECTION_METHOD));
    }

    /**
     * Tests that {@link ReflectionAction#putValue(String, Object)} works.
     * @throws Exception (unexpected)
     */
    @Test(expected = IllegalArgumentException.class)
    public void testPutValueMethodThrowsString() throws Exception {
        testling.putValue(ReflectionAction.REFLECTION_METHOD, "foo");
    }

    /**
     * Tests that {@link ReflectionAction#putValue(String, Object)} works.
     * @throws Exception (unexpected)
     */
    @Test(expected = IllegalArgumentException.class)
    public void testPutValueMethodThrowsOther() throws Exception {
        testling.putValue(ReflectionAction.REFLECTION_METHOD, new Object());
    }

    /**
     * Tests that {@link ReflectionAction#putValue(String, Object)} works.
     * @throws Exception (unexpected)
     */
    @Test
    public void testPutValueMethod() throws Exception {
        testling.putValue(ReflectionAction.REFLECTION_METHOD, actionMock.getMethod());
        Assert.assertEquals(actionMock.getMethod(), testling.getValue(ReflectionAction.REFLECTION_METHOD));
    }

    /**
     * Tests that {@link ReflectionAction#putValue(String, Object)} works.
     * @throws Exception (unexpected)
     */
    @Test
    public void testPutValueTarget() throws Exception {
        testling.putValue(ReflectionAction.REFLECTION_METHOD_NAME, "foo");
        testling.putValue(ReflectionAction.REFLECTION_METHOD, actionMock.getMethod());
        testling.putValue(ReflectionAction.REFLECTION_TARGET, null);
        Assert.assertNull("After setting REFLECTION_TARGET to null, REFLECTION_METHOD must be reset to null.", testling.getValue(ReflectionAction.REFLECTION_METHOD));
        Assert.assertEquals("After setting REFLECTION_TARGET to null, REFLECTION_METHOD_NAME must be unchanged.", "foo", testling.getValue(ReflectionAction.REFLECTION_METHOD_NAME));
    }

    /**
     * Tests that {@link ReflectionAction#actionPerformed(ActionEvent)} works.
     * @throws Exception (unexpected)
     */
    @Test
    public void testActionPerformed() throws Exception {
        testling.putValue(ReflectionAction.REFLECTION_TARGET, actionMock);
        testling.putValue(ReflectionAction.REFLECTION_METHOD_NAME, "someAction");
        testling.actionPerformed(new ActionEvent(this, 0, null));
        Assert.assertEquals("actionPerformed() must lead to the target method being invoked exactly once.", 1, actionMock.getInvocationCount());
    }

    /**
     * Tests that {@link ReflectionAction#actionPerformed(ActionEvent)} works.
     * @throws Exception (unexpected)
     */
    @Test(expected = Exception.class)
    public void testActionPerformedException() throws Exception {
        actionMock.setThrowException(true);
        testling.putValue(ReflectionAction.REFLECTION_TARGET, actionMock);
        testling.putValue(ReflectionAction.REFLECTION_METHOD_NAME, "someAction");
        testling.actionPerformed(new ActionEvent(this, 0, null));
        Assert.assertEquals("actionPerformed() must lead to the target method being invoked exactly once.", 1, actionMock.getInvocationCount());
    }

    /**
     * ActionMock.
     * Provides a counter that counts how often the action was invoked.
     * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
     */
    public static class ActionMock {

        /**
         * Counter for counting the number of invocations of {@link #someAction()}.
         */
        private int invocationCount;

        /**
         * Whether to throw an exception during the execution of {@link #someAction()}.
         */
        private boolean throwException;

        /**
         * The ActionMethod.
         * Increments the invocation counter.
         * @throws Exception If {@link #throwException} is true.
         */
        @ActionMethod
        public void someAction() throws Exception {
            if (isThrowException()) {
                throw new Exception();
            }
            invocationCount++;
        }

        /**
         * Returns the invocation counter.
         * @return The invocation counter.
         */
        public int getInvocationCount() {
            return invocationCount;
        }

        /**
         * Returns the Action method.
         * @return The Action method.
         */
        public Method getMethod() {
            try {
                return getClass().getMethod("someAction");
            } catch (final NoSuchMethodException e) {
                throw new Error(e);
            }
        }

        /**
         * Returns whether an exception will be thrown when executing {@link #someAction()}.
         * @return <code>true</code> if an exception will be thrown, otherwise <code>false</code>.
         */
        public boolean isThrowException() {
            return throwException;
        }

        /**
         * Sets whether an exception should be thrown when executing {@link #someAction()}.
         * @param throwException <code>true</code> for throwing an exception, otherwise <code>false</code>.
         */
        public void setThrowException(final boolean throwException) {
            this.throwException = throwException;
        }

    } // class ActionMock

} // class ReflectionActionTest
