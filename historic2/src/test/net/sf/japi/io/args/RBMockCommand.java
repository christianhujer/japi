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

package test.net.sf.japi.io.args;

import java.util.List;
import java.util.ResourceBundle;
import net.sf.japi.io.args.BasicCommand;
import org.jetbrains.annotations.NotNull;

/**
 * Mock Command for {@link ResourceBundle} related tests of {@link BasicCommand}.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
public class RBMockCommand extends BasicCommand {

    /** My own ResourceBundle. */
    private final ResourceBundle myBundle = ResourceBundle.getBundle("test.net.sf.japi.io.args.RBMockCommandMyBundle");

    /** Whether to return its own ResourceBundle. */
    private boolean returnOwnBundle;

    /** {@inheritDoc} */
    @SuppressWarnings({"InstanceMethodNamingConvention"})
    public int run(@NotNull final List<String> args) throws Exception {
        return 0;
    }

    /** {@inheritDoc} */
    @Override @NotNull public ResourceBundle getBundle() {
        return returnOwnBundle ? myBundle : super.getBundle();    //To change body of overridden methods use File | Settings | File Templates.
    }

    /**
     * Sets whether {@link #getBundle()} should return the mock's own bundle or the superclass' bundle.
     * @param returnOwnBundle <code>true</code> if {@link #getBundle()} should return the mock's own bundle, otherwise <code>false</code>.
     */
    public void setReturnOwnBundle(final boolean returnOwnBundle) {
        this.returnOwnBundle = returnOwnBundle;
    }

} // class RBMockCommand
