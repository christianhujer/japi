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

package test.net.sf.japi.swing.about;

import java.security.Permission;
import java.util.PropertyPermission;
import net.sf.japi.swing.about.AboutDialog;
import net.sf.japi.swing.action.ActionBuilder;
import net.sf.japi.swing.action.ActionBuilderFactory;
import org.jetbrains.annotations.NotNull;
import org.junit.After;
import org.junit.Test;

/**
 * Unit test for {@link AboutDialog}.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class AboutDialogTest {

    /** Resets the security manager. */
    @After
    public void resetSecurityManager() {
        System.setSecurityManager(null);
    }

    /** Tests that creating an AboutDialog also is possible if the SecurityManager denies access to {@link System#getProperties()}.
     * @see <a href="https://sourceforge.net/tracker/index.php?func=detail&amp;aid=2630867&amp;group_id=149894&amp;atid=776737">[ 2630867 ] System.getProperties() throws SecurityException</a>
     */
    @SuppressWarnings({"JUnitTestMethodWithNoAssertions"})
    @Test
    public void testAboutDialog() {
        System.setSecurityManager(new TestSecurityManager());
        final ActionBuilder actionBuilder = ActionBuilderFactory.getInstance().getActionBuilder(getClass());
        new AboutDialog(actionBuilder);
    }

    /** Security Manager for testing AboutDialog. */
    private static class TestSecurityManager extends SecurityManager {

        /** Permission to set a new security manager. */
        private static final Permission SET_SECURITY_MANAGER = new RuntimePermission("setSecurityManager");

        /** Permission to access properties. */
        private static final Permission ACCESS_PROPERTIES = new PropertyPermission("*", "read,write");

        /** {@inheritDoc} */
        @Override
        public void checkPermission(@NotNull final Permission perm) {
            if (SET_SECURITY_MANAGER.equals(perm)) {
                return;
            }
            if (ACCESS_PROPERTIES.equals(perm)) {
                super.checkPermission(perm);
            }
        }
    }
}
