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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.sf.japi.swing.action.ActionBuilder;
import net.sf.japi.swing.action.ActionBuilderFactory;
import net.sf.japi.swing.action.ActionMethod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A Controller that controles two lists, offering to add or remove elements from the second list.
 * Elements from the first list can be added to the second list based on the selection made in the first list.
 * Elements from the second list can be removed from the second list based on the selection made in the second list.
 * The first list, which is not modified by this controller, is called source list.
 * The second list, which can be modified by this controller, is called target list.
 * @todo move to an appropriate JAPI swing library module.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class DoubleListController extends JComponent implements ListSelectionListener {

    /** Action Builder. */
    @NotNull private static final ActionBuilder ACTION_BUILDER = ActionBuilderFactory.getInstance().getActionBuilder("net.sf.japi.string2bytes");

    /**
     * The first list (source list) to operate on.
     * @serial include
     */
    @NotNull private final JList sourceList;

    /**
     * The second list (target list) to operate on.
     * @serial include
     */
    @NotNull private final JList targetList;

    /**
     * The Action for adding elements to the second (target) list based on the selection from the first list.
     * @serial include
     */
    @NotNull private final Action add = ACTION_BUILDER.createAction(false, "add", this);

    /**
     * The Action for removing elements from the second (target) list.
     * @serial include
     */
    @NotNull private final Action remove = ACTION_BUILDER.createAction(false, "remove", this);

    /**
     * Create a DoubleListController.
     * @param sourceList First list to operate on (source list).
     * @param targetList Second list to operate on (target list).
     */
    public DoubleListController(@NotNull final JList sourceList, @NotNull final JList targetList) {
        this.sourceList = sourceList;
        this.targetList = targetList;
        setLayout(new GridBagLayout());
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.weighty = 1.0;
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.gridy = 1;
        add(new JButton(add), gbc);
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridy = 2;
        add(new JButton(remove), gbc);
        sourceList.addListSelectionListener(this);
        targetList.addListSelectionListener(this);
        valueChanged(null);
    }

    /**
     * Action for adding an element to the target list based on the selection from the source list.
     */
    @ActionMethod
    public void add() {
        final int index = sourceList.getSelectionModel().getMinSelectionIndex();
        if (index == -1) {
            return;
        }
        final Object element = sourceList.getModel().getElementAt(index);
        int targetIndex = targetList.getSelectionModel().getMaxSelectionIndex();
        if (targetIndex == -1) {
            targetIndex = targetList.getModel().getSize();
        }
        ((DefaultListModel) targetList.getModel()).insertElementAt(element, targetIndex);
    }

    /**
     * Action for removing an element from the target list.
     */
    @ActionMethod public void remove() {
        final int index = targetList.getSelectionModel().getMinSelectionIndex();
        if (index == -1) {
            return;
        }
        ((DefaultListModel) targetList.getModel()).remove(index);
    }

    /** {@inheritDoc} */
    public void valueChanged(@Nullable final ListSelectionEvent listSelectionEvent) {
        @Nullable final Object source = listSelectionEvent != null ? listSelectionEvent.getSource() : null;
        if (source == null || source == sourceList || source == sourceList.getSelectionModel()) {
            add.setEnabled(sourceList.getSelectionModel().getMinSelectionIndex() >= 0);
        }
        if (source == null || source == targetList || source == targetList.getSelectionModel()) {
            remove.setEnabled(targetList.getSelectionModel().getMinSelectionIndex() >= 0);
        }
    }

} // class DoubleListController
