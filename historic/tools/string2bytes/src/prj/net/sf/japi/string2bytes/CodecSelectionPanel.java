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
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.sf.japi.swing.action.ActionBuilder;
import net.sf.japi.swing.action.ActionBuilderFactory;
import org.jetbrains.annotations.NotNull;

/**
 * The CodecSelectionPanel allows the selection and configuration of one or more CodecSteps.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class CodecSelectionPanel extends JPanel implements ListSelectionListener {

    /** Action Builder. */
    @NotNull private static final ActionBuilder ACTION_BUILDER = ActionBuilderFactory.getInstance().getActionBuilder("net.sf.japi.string2bytes");

    /** The list with the available codecs.
     * @serial include
     */
    @NotNull private final JList availableCodecsList = new JList(new CodecListModel());

    /** The list with the configured codec steps.
     * @serial include
     */
    @NotNull private final JList configuredCodecSteps = new JList(new CodecStepsListModel());

    /**
     * Creates a CodecSelectionPanel.
     */
    public CodecSelectionPanel() {
        super(new GridBagLayout());
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(new JLabel(ACTION_BUILDER.getString("availableCodecs.label")), gbc);
        gbc.gridx = 3;
        add(new JLabel(ACTION_BUILDER.getString("selectedCodecs.label")), gbc);
        gbc.gridx = 1;
        gbc.weighty = 1.0;
        gbc.gridy = 2;
        add(new JScrollPane(availableCodecsList), gbc);
        gbc.gridx = 3;
        add(new JScrollPane(configuredCodecSteps), gbc);
        gbc.weightx = 0.0;
        gbc.gridx = 2;
        add(new DoubleListController(availableCodecsList, configuredCodecSteps), gbc);
        gbc.gridx = 4;
        add(new ListOrderController((DefaultListModel) configuredCodecSteps.getModel(), configuredCodecSteps.getSelectionModel()), gbc);
        configuredCodecSteps.addListSelectionListener(this);
    }

    /**
     * Main method, used to try the CodecSelectionPanel.
     * @param args Command line arguments (ignored)
     */
    public static void main(@NotNull final String... args) {
        final JFrame f = new JFrame();
        f.add(new CodecSelectionPanel());
        f.pack();
        f.setVisible(true);
    }

    /** {@inheritDoc} */
    public void valueChanged(@NotNull final ListSelectionEvent listSelectionEvent) {
        // TODO:2009-02-20:christianhujer:Implement.
    }

} // class CodecSelectionPanel
