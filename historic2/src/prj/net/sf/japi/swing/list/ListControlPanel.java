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

package net.sf.japi.swing.list;

import java.awt.GridLayout;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.sf.japi.swing.action.ActionBuilder;
import net.sf.japi.swing.action.ActionBuilderFactory;
import net.sf.japi.swing.action.ActionMethod;
import org.jetbrains.annotations.NotNull;

/** A Panel for a {@link MutableListModel} rendered by a {@link JList}.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 * @todo the selection should automatically change with the move.
 */
public class ListControlPanel extends JComponent implements ListSelectionListener {

    /** The ActionBuilder for creating the actions. */
    @NotNull private static final ActionBuilder ACTION_BUILDER = ActionBuilderFactory.getInstance().getActionBuilder("net.sf.japi.swing.list");

    /** Action for moving to top.
     * @serial include
     */
    @NotNull private final Action moveToTop = ACTION_BUILDER.createAction(false, "moveToTop", this);

    /** Action for moving up.
     * @serial include
     */
    @NotNull private final Action moveUp = ACTION_BUILDER.createAction(false, "moveUp", this);

    /** Action for moving down.
     * @serial include
     */
    @NotNull private final Action moveDown = ACTION_BUILDER.createAction(false, "moveDown", this);

    /** Action for moving to bottom.
     * @serial include
     */
    @NotNull private final Action moveToBottom = ACTION_BUILDER.createAction(false, "moveToBottom", this);

    /** The list that provides the information about the current selection.
     * @serial include
     */
    @NotNull private JList list;

    /** The model to control.
     * @serial include
     */
    @NotNull private MutableListModel model;

    /** Creates a ListControlPanel.
     * @param list JList that provides the list selection information.
     * @param model Model to control.
     */
    public ListControlPanel(@NotNull final JList list, @NotNull final MutableListModel model) {
        setLayout(new GridLayout(4, 1, 5, 5));
        setList(list);
        setModel(model);
        add(new JButton(moveToTop));
        add(new JButton(moveUp));
        add(new JButton(moveDown));
        add(new JButton(moveToBottom));
        updateActionStates();
    }

    /** Action for moving the currently selected element to top. */
    @ActionMethod public void moveToTop() {
        model.moveToTop(list.getSelectedIndex());
    }

    /** Action for moving the currently selected element up. */
    @ActionMethod
    public void moveUp() {
        model.moveUp(list.getSelectedIndex());
    }

    /** Action for moving the currently selected element down. */
    @ActionMethod public void moveDown() {
        model.moveDown(list.getSelectedIndex());
    }

    /** Action for moving the currently selected element to bottom. */
    @ActionMethod public void moveToBottom() {
        model.moveToBottom(list.getSelectedIndex());
    }

    /** Returns the list that provides the information about the current selection.
     * @return JList queried for selection.
     */
    @NotNull public JList getList() {
        return list;
    }

    /** Sets the list that provides the information about the current selection.
     * @param list JList to query for selection.
     */
    public void setList(@NotNull final JList list) {
        if (this.list != null) {
            this.list.removeListSelectionListener(this);
        }
        this.list = list;
        list.addListSelectionListener(this);
    }

    /** Returns the model that's controlled by this ListControlPanel.
     * @return The controlled model.
     */
    @NotNull public MutableListModel getModel() {
        return model;
    }

    /** Sets the model to control by this ListControlPanel.
     * @param model The model to control.
     */
    public void setModel(@NotNull final MutableListModel model) {
        this.model = model;
    }

    /** {@inheritDoc} */
    public void valueChanged(@NotNull final ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            updateActionStates();
        }
    }

    /** Updates the states of the actions. */
    protected void updateActionStates() {
        assert list == null || list.getModel() == model;
        final boolean noSelection = list == null || model.getSize() == 0 || list.getSelectedIndex() == -1;
        final boolean top    = noSelection || list.getSelectedIndex() == 0;
        final boolean bottom = noSelection || list.getSelectedIndex() == model.getSize() - 1;
        moveToTop.setEnabled(!top);
        moveUp.setEnabled(!top);
        moveDown.setEnabled(!bottom);
        moveToBottom.setEnabled(!bottom);
    }

    /** Returns the action for moving an element to top.
     * @return The action for moving an element to top.
     */
    @NotNull public Action getMoveToTop() {
        return moveToTop;
    }

    /** Returns the action for moving an element up.
     * @return The action for moving an element up.
     */
    @NotNull public Action getMoveUp() {
        return moveUp;
    }

    /** Returns the action for moving an element down.
     * @return The action for moving an element down.
     */
    @NotNull public Action getMoveDown() {
        return moveDown;
    }

    /** Returns the action for moving an element to bottom.
     * @return The action for moving an element to bottom.
     */
    @NotNull public Action getMoveToBottom() {
        return moveToBottom;
    }

} // class ListControlPanel
