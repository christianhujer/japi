/*
 * Copyright (C) 2009  Christian Hujer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.sf.japi.string2bytes;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.sf.japi.swing.action.ActionBuilder;
import net.sf.japi.swing.action.ActionBuilderFactory;
import net.sf.japi.swing.action.ActionMethod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A Control for changing the order of items in a {@link JList}.
 * @todo move to an appropriate JAPI swing library module.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class ListOrderController extends JComponent implements ListSelectionListener {

    /** Action Builder. */
    @NotNull private static final ActionBuilder ACTION_BUILDER = ActionBuilderFactory.getInstance().getActionBuilder("net.sf.japi.string2bytes");

    /**
     * The JList to operate on.
     * If <code>null</code>, this ListOrderController is currently disconnected.
     * @serial include
     */
    @Nullable private DefaultListModel listModel;

    /**
     * The list selection model to operate on.
     * If <code>null</code>, this ListOrderController is currently disconnected.
     * @serial include
     */
    @Nullable private ListSelectionModel selectionModel;

    /**
     * Action for top.
     * @serial include
     */
    @NotNull private final Action top = ACTION_BUILDER.createAction(false, "top", this);

    /**
     * Action for up.
     * @serial include
     */
    @NotNull private final Action up = ACTION_BUILDER.createAction(false, "up", this);

    /**
     * Action for down.
     * @serial include
     */
    @NotNull private final Action down = ACTION_BUILDER.createAction(false, "down", this);

    /**
     * Action for bottom.
     * @serial include
     */
    @NotNull private final Action bottom = ACTION_BUILDER.createAction(false, "bottom", this);

    /**
     * Create a ListOrderController that's not yet connected to a list.
     */
    public ListOrderController() {
        this(null, null);
    }

    /**
     * Create a ListOrderController for a list.
     * @param listModel List model to create ListOrderController for.
     * @param selectionModel Selection model to create ListOrderController for.
     */
    public ListOrderController(@Nullable final DefaultListModel listModel, @Nullable final ListSelectionModel selectionModel) {
        setLayout(new GridBagLayout());
        setListAndSelectionModels(listModel, selectionModel);
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.weighty = 1.0;
        add(new JButton(top), gbc);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weighty = 0.0;
        add(new JButton(up), gbc);
        add(new JButton(down), gbc);
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weighty = 1.0;
        add(new JButton(bottom), gbc);
    }

    /**
     * Sets the list model to use.
     * @param listModel The list model to use.
     */
    public void setListModel(@Nullable final DefaultListModel listModel) {
        if (this.listModel == listModel) {
            return;
        }
        this.listModel = listModel;
        updateActions();
    }

    /**
     * Returns the list model that's currently used.
     * @return Currently used list model.
     */
    @Nullable public DefaultListModel getListModel() {
        return listModel;
    }

    /**
     * Sets the selection model to use.
     * @param selectionModel The selection model to use.
     */
    public void setSelectionModel(@Nullable final ListSelectionModel selectionModel) {
        if (this.selectionModel == selectionModel) {
            return;
        }
        if (this.selectionModel != null) {
            this.selectionModel.removeListSelectionListener(this);
        }
        this.selectionModel = selectionModel;
        if (selectionModel != null) {
            selectionModel.addListSelectionListener(this);
        }
        updateActions();
    }

    /**
     * Returns the selection model that's currently used.
     * @return Currently used selection model.
     */
    @Nullable public ListSelectionModel getSelectionModel() {
        return selectionModel;
    }

    /**
     * Sets the list and selection model to use.
     * @param listModel The list model to use.
     * @param selectionModel The selection model to use.
     */
    public void setListAndSelectionModels(@Nullable final DefaultListModel listModel, @Nullable final ListSelectionModel selectionModel) {
        if (this.selectionModel == selectionModel && this.listModel == listModel) {
            return;
        }
        this.listModel = listModel;
        setSelectionModel(selectionModel);
    }

    /**
     * Updates the enabled state of the actions.
     * Use this in case all actions need to be updated.
     */
    private void updateActions() {
        final boolean globalEnabled = listModel != null && selectionModel != null;
        final int index = selectionModel != null ? selectionModel.getMinSelectionIndex() : -1;
        final int maxIndex = listModel != null ? listModel.size() : -1;
        top.setEnabled(globalEnabled && index > 0);
        up.setEnabled(globalEnabled && index > 0);
        down.setEnabled(globalEnabled && index != -1 && index < maxIndex - 1);
        bottom.setEnabled(globalEnabled && index != -1 && index < maxIndex - 1);
    }

    /**
     * Mode the currently selected entry to top.
     */
    @ActionMethod public void top() {
        final ListSelectionModel tmpSelectionModel = selectionModel;
        final DefaultListModel tmpListModel = listModel;
        if (tmpSelectionModel == null || tmpListModel == null) {
            return;
        }
        final int oldIndex = tmpSelectionModel.getMinSelectionIndex();
        final int newIndex = 0;
        if (oldIndex <= 0) {
            return;
        }
        tmpListModel.insertElementAt(tmpListModel.remove(oldIndex), newIndex);
        tmpSelectionModel.setSelectionInterval(newIndex, newIndex);
    }

    /**
     * Move the currently selected entry up.
     */
    @ActionMethod public void up() {
        final ListSelectionModel tmpSelectionModel = selectionModel;
        final DefaultListModel tmpListModel = listModel;
        if (tmpSelectionModel == null || tmpListModel == null) {
            return;
        }
        final int oldIndex = tmpSelectionModel.getMinSelectionIndex();
        final int newIndex = oldIndex - 1;
        if (oldIndex <= 0) {
            return;
        }
        tmpListModel.insertElementAt(tmpListModel.remove(oldIndex), newIndex);
        tmpSelectionModel.setSelectionInterval(newIndex, newIndex);
    }

    /**
     * Move the currently selected entry down.
     */
    @ActionMethod public void down() {
        final ListSelectionModel tmpSelectionModel = selectionModel;
        final DefaultListModel tmpListModel = listModel;
        if (tmpSelectionModel == null || tmpListModel == null) {
            return;
        }
        final int oldIndex = tmpSelectionModel.getMinSelectionIndex();
        final int newIndex = oldIndex + 1;
        if (oldIndex >= tmpListModel.size() - 1 || oldIndex < 0) {
            return;
        }
        tmpListModel.insertElementAt(tmpListModel.remove(oldIndex), newIndex);
        tmpSelectionModel.setSelectionInterval(newIndex, newIndex);
    }

    /**
     * Move the currently selected entry to bottom.
     */
    @ActionMethod
    public void bottom() {
        final ListSelectionModel tmpSelectionModel = selectionModel;
        final DefaultListModel tmpListModel = listModel;
        if (tmpSelectionModel == null || tmpListModel == null) {
            return;
        }
        final int oldIndex = tmpSelectionModel.getMinSelectionIndex();
        final int newIndex = tmpListModel.size() - 1;
        if (oldIndex >= tmpListModel.size() - 1 || oldIndex < 0) {
            return;
        }
        tmpListModel.insertElementAt(tmpListModel.remove(oldIndex), newIndex);
        tmpSelectionModel.setSelectionInterval(newIndex, newIndex);
    }

    /** {@inheritDoc} */
    public void valueChanged(@NotNull final ListSelectionEvent listSelectionEvent) {
        updateActions();
    }

} // class ListOrderController
