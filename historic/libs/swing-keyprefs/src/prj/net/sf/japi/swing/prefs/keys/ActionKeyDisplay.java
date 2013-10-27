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

package net.sf.japi.swing.prefs.keys;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JRadioButton;
import javax.swing.KeyStroke;
import javax.swing.AbstractButton;
import javax.swing.border.LineBorder;
import net.sf.japi.swing.action.ActionBuilder;
import net.sf.japi.swing.action.ActionBuilderFactory;
import org.jetbrains.annotations.Nullable;

/** A component for displaying the accellerators of an Action.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
// This class is only public to get the action methods working.
public class ActionKeyDisplay extends JComponent {

    /** Action Builder. */
    private static final ActionBuilder ACTION_BUILDER = ActionBuilderFactory.getInstance().getActionBuilder("net.sf.japi.swing.prefs.keys");

    /** The action of the button that displays the keystroke and allows changing it.
     * @serial include
     */
    private final ActionKeyAction actionKeyAction = new ActionKeyAction(this);

    /** The action that is currently handled.
     * @serial include
     */
    @Nullable private Action action;

    /** The radio actions.
     * @serial include
     */
    private final Action[] radioActions = ACTION_BUILDER.createActions(true, this, "keystrokeNone", "keystrokeStandard", "keystrokeUserdefined");

    /** Create an ActionKeyDisplay. */
    public ActionKeyDisplay() {
        final AbstractButton keyboardButton = new JButton(actionKeyAction);
        setBorder(BorderFactory.createTitledBorder(ACTION_BUILDER.getString("keystroke.border.title")));
        setLayout(new FlowLayout(FlowLayout.LEADING));
        final ButtonGroup bg = new ButtonGroup();
        for (final Action radioAction : radioActions) {
            final AbstractButton button = new JRadioButton(radioAction);
            add(button);
            bg.add(button);
        }
        keyboardButton.setBorder(new LineBorder(Color.BLACK, 4, true));
        keyboardButton.setPreferredSize(new Dimension(140, 30));
        keyboardButton.setText("ctrl alt shift pressed N");
        add(keyboardButton);
        actionKeyAction.setAction(ACTION_BUILDER.createAction(true, "save"));
        setEnabled(false);
    }

    /** {@inheritDoc} */
    @Override public void setEnabled(final boolean enabled) {
        super.setEnabled(enabled);
        actionKeyAction.setEnabled(enabled);
        for (final Action radioAction : radioActions) {
            radioAction.setEnabled(enabled);
        }
    }

    /** Action method for using no keystroke. */
    public void keystrokeNone() {
        actionKeyAction.setEnabled(false);
    }

    /** Action method for using standard keystroke. */
    public void keystrokeStandard() {
        actionKeyAction.setEnabled(false);
    }

    /** Action method for using a user defined keystroke. */
    public void keystrokeUserdefined() {
        actionKeyAction.setEnabled(true);
    }

    /** Returns the action that is currently handled.
     * @return the action that is currently handled
     */
    @Nullable public Action getAction() {
        return action;
    }

    /** Sets the action that is currently handled.
     * @param action the action that is currently handled
     */
    public void setAction(@Nullable final Action action) {
        this.action = action;
        actionKeyAction.setAction(action);
    }

} // class KeyChooser

/** Action for action keys.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
class ActionKeyAction extends AbstractAction {

    /** Serial version. */
    private static final long serialVersionUID = 1L;

    /** The Action to be displayed. */
    private Action action;

    /** The parent component. */
    private final Component parent;

    ActionKeyAction(final Component parent) {
        this.parent = parent;
    }

    /** {@inheritDoc} */
    public void actionPerformed(final ActionEvent e) {
        // TODO:2009-02-23:christianhujer:Implement.
    }

    /** Set the Action to be displayed.
     * @param action Action to be displayed and changed
     */
    public void setAction(@Nullable final Action action) {
        this.action = action;
        putValue(NAME, getLocalizedKeyStrokeText(action));
        setEnabled(action != null);
    }

    /** Get the localized text of the keystroke of an Action.
     * @param action Action to get text for
     * @return localized text for the accelerator of <var>action</var>
     * @retval "" if <var>action</var> == null
     * @retval "" if the accelerator of <var>action</var> == null
     * @retval String containing localized text for the accelerator of <var>action</var>
     * @throws ClassCastException in case the bound accelerator property is not a {@link KeyStroke}
     */
    public static String getLocalizedKeyStrokeText(@Nullable final Action action) {
        if (action == null) {
            return "";
        }
        return getLocalizedKeyStrokeText((KeyStroke) action.getValue(ACCELERATOR_KEY));
    }

    /** Get the localized text of a KeyStroke.
     * @param keyStroke KeyStroke to get text for
     * @return localized text for the accelerator of <var>action</var>
     * @retval "" if <var>keyStroke</var> == null
     * @retval String containing localized text for <var>keyStroke</var>
     */
    public static String getLocalizedKeyStrokeText(final KeyStroke keyStroke) {
        if (keyStroke == null) {
            return "";
        }
        final StringBuilder newName = new StringBuilder();
        final int modifiers = keyStroke.getModifiers();
        if (modifiers != 0) {
            newName.append(KeyEvent.getKeyModifiersText(modifiers));
            newName.append('+');
        }
        final int keyCode = keyStroke.getKeyCode();
        if (keyCode == 0) {
            newName.append(keyStroke.getKeyChar());
        } else {
            newName.append(KeyEvent.getKeyText(keyCode));
        }
        return newName.toString();
    }

} // class ActionKeyAction
