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

import java.awt.Window;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import static javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW;
import javax.swing.JDialog;
import static javax.swing.KeyStroke.getKeyStroke;
import org.jetbrains.annotations.NotNull;

/** An Action implementation that disposes a window when activated.
 * Usually, you'd put an instance of this class in an actionmap, eventually pointing an inputmap to it.
 * Usage example:
 * <pre>
 *  JDialog d = new JDialog();
 *  DisposeAction da = new DisposeAction(d);
 *  d.getRootPane().getActionMap().put("close", da);
 *  d.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "close");
 * </pre>
 * The convenience method {@link #install(JDialog)} will do exactly that for an existing JDialog.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 * @todo basically this is the same as using an ActionBuilder with dispose as method, so why not use that?
 */
public final class DisposeAction extends AbstractAction {

    /** Serial version. */
    private static final long serialVersionUID = 1L;

    /** The window to be disposed.
     * @serial include
     */
    private final Window window;

    /** Install the DisposeAction to a JDialog for the ESCAPE key.
     * For most users of this method, the return value is irrelevant and can be ignored.
     * @param dialog JDialog to install to
     * @return the created DisposeAction
     */
    public static DisposeAction install(@NotNull final JDialog dialog) {
        final DisposeAction da = new DisposeAction(dialog);
        dialog.getRootPane().getActionMap().put("close", da);
        dialog.getRootPane().getInputMap(WHEN_IN_FOCUSED_WINDOW).put(getKeyStroke("ESCAPE"), "close");
        return da;
    }

    /** Create a DisposeAction.
     * @param window Window to be disposed when this action is activated
     */
    public DisposeAction(@NotNull final Window window) {
        this.window = window;
    }

    /** {@inheritDoc} */
    public void actionPerformed(@NotNull final ActionEvent e) {
        window.dispose();
    }

    /** {@inheritDoc} */
    @Override protected Object clone() throws CloneNotSupportedException { // NOPMD
        return super.clone();
    }

} // class DisposeAction
