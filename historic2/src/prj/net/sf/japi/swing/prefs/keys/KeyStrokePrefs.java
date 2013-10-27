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

import java.awt.BorderLayout;
import static java.util.Arrays.asList;
import java.util.List;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.sf.japi.swing.action.ActionBuilder;
import net.sf.japi.swing.action.ActionBuilderFactory;
import net.sf.japi.swing.prefs.AbstractPrefs;
import net.sf.japi.swing.treetable.JTreeTable;

/** Prefs implementation for configuring keystrokes of one or more {@link ActionBuilder ActionBuilders}.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class KeyStrokePrefs extends AbstractPrefs implements ListSelectionListener {

    /** Action Builder. */
    private static final ActionBuilder ACTION_BUILDER = ActionBuilderFactory.getInstance().getActionBuilder("net.sf.japi.swing.prefs.keys");

    /** The ActionKeyDisplay.
     * @serial include
     */
    private final ActionKeyDisplay actionKeyDisplay;

    /** The table.
     * @serial include
     */
    private final JTreeTable<KeyStrokeRootNode, AbstractSimpleNode<AbstractSimpleNode>> table;

    /** Create KeyStrokePrefs for a list of ActionBuilders.
     * @param actionBuilders ActionBuilders
     */
    public KeyStrokePrefs(final ActionBuilder... actionBuilders) {
        setListLabelText(ACTION_BUILDER.getString("prefs.listLabelText"));
        setLabelText(ACTION_BUILDER.getString("prefs.labelText"));
        final List<ActionBuilder> builders = asList(actionBuilders);
        setLayout(new BorderLayout());
        table = new JTreeTable<KeyStrokeRootNode, AbstractSimpleNode<AbstractSimpleNode>>(new KeyStrokeTreeTableModel(builders));
        add(new JScrollPane(table));
        table.getSelectionModel().addListSelectionListener(this);
        actionKeyDisplay = new ActionKeyDisplay();
        add(actionKeyDisplay, BorderLayout.SOUTH);
    }

    /** Create KeyStrokePrefs for a list of named ActionBuilders.
     * @param builderNames names of ActionBuilders
     */
    public KeyStrokePrefs(final String... builderNames) {
        this(getBuildersForNames(builderNames));
    }

    private static ActionBuilder[] getBuildersForNames(final String[] builderNames) {
        final ActionBuilder[] builders = new ActionBuilder[builderNames.length];
        for (int i = 0; i < builderNames.length; i++) {
            builders[i] = ActionBuilderFactory.getInstance().getActionBuilder(builderNames[i]);
        }
        return builders;
    }

    /** {@inheritDoc} */
    public boolean isChanged() {
        return false;  // TODO:2009-02-23:christianhujer:Implement.
    }

    /** {@inheritDoc} */
    public void defaults() {
        // TODO:2009-02-23:christianhujer:Implement.
    }

    /** {@inheritDoc} */
    public void revert() {
        // TODO:2009-02-23:christianhujer:Implement.
    }

    /** {@inheritDoc} */
    public void apply() {
        // TODO:2009-02-23:christianhujer:Implement.
    }

    /** {@inheritDoc} */
    public void valueChanged(final ListSelectionEvent e) {
        if (table.getSelectedRow() == -1) {
            actionKeyDisplay.setAction(null);
        } else {
            System.err.println(table.getTreeTableModel().getValueAt(null, table.getSelectedRow()));
        }
    }

} // class KeyStrokePrefs
