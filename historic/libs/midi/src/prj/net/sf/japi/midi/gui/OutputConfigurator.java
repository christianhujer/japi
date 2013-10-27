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

package net.sf.japi.midi.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import net.sf.japi.midi.MidiUtils;
import net.sf.japi.midi.OutputConfiguration;
import net.sf.japi.swing.action.ActionBuilder;
import net.sf.japi.swing.action.ActionBuilderFactory;
import org.jetbrains.annotations.Nullable;

/** UI component for configuring a midi output.
 * The user can choose of one of the receiving devices and select a channel.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class OutputConfigurator extends JComponent {

    /** ActionBuilder. */
    private static final ActionBuilder ACTION_BUILDER = ActionBuilderFactory.getInstance().getActionBuilder(OutputConfigurator.class);

    /** The List of Devices from which to choose.
     * @serial include
     */
    private final JList deviceList;

    /** The Spinner for selecting the channel.
     * @serial include
     */
    private final JSpinner channelSpinner;

    /** Creates an OutputConfigurator with no initial configuration.
     * The first device and channel 0 will be selected as defaults.
     * @throws MidiUnavailableException if one of the requested devices is unavailable due to resource restrictions.
     */
    public OutputConfigurator() throws MidiUnavailableException {
        setLayout(new BorderLayout());
        deviceList = new JList(MidiUtils.getAllReceivingDevices());
        deviceList.setCellRenderer(new MidiDeviceListCellRenderer());
        deviceList.setSelectedIndex(0);
        channelSpinner = new JSpinner(new SpinnerNumberModel(0, 0x0, 0xF, 1));
        //channelSpinner = new JSpinner(new ChannelSpinnerModel());
        add(new JScrollPane(deviceList));
        final Container spinnerPanel = new JPanel();
        spinnerPanel.setLayout(new FlowLayout());
        spinnerPanel.add(new JLabel(ACTION_BUILDER.getString("Channel.label")));
        spinnerPanel.add(channelSpinner);
        add(spinnerPanel, BorderLayout.SOUTH);
    }

    /** Shows a dialog where the user can choose a receiving device and channel once.
     * @param parent Parent component on which to show the dialog.
     * @return OutputConfiguration or <code>null</code> if the user chose to abort the dialog.
     * @throws MidiUnavailableException if one of the requested devices is unavailable due to resource restrictions.
     */
    @Nullable public static OutputConfiguration showDialog(@Nullable final Component parent) throws MidiUnavailableException {
        final OutputConfigurator configurator = new OutputConfigurator();
        final int result = JOptionPane.showConfirmDialog(parent, configurator, ACTION_BUILDER.getString("Dialog.title"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        return result == JOptionPane.OK_OPTION ? configurator.getConfiguration() : null;
    }

    /** Returns the selected configuration.
     * @return The selected configuration.
     */
    public OutputConfiguration getConfiguration() {
        return new OutputConfiguration(getSelectedDevice(), getSelectedChannel());
    }

    /** Returns the selected device.
     * @return The selected device.
     */
    public MidiDevice getSelectedDevice() {
        return (MidiDevice) deviceList.getSelectedValue();
    }

    /** Returns the selected channel.
     * @return The selected channel.
     */
    public int getSelectedChannel() {
        return (Integer) channelSpinner.getValue();
    }

    /** Main program of OutputConfigurator, used for trying the most common use cases.
     * @param args Command line arguments (ignored).
     * @throws MidiUnavailableException if one of the requested devices is unavailable due to resource restrictions.
     */
    public static void main(final String... args) throws MidiUnavailableException {
        System.out.println(showDialog(null));
    }
}
