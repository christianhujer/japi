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

package net.sf.japi.progs.jeduca.jtest.gui;

import java.awt.GridBagConstraints;
import static java.awt.GridBagConstraints.HORIZONTAL;
import static java.awt.GridBagConstraints.NORTHWEST;
import static java.awt.GridBagConstraints.REMAINDER;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import static javax.swing.BorderFactory.createTitledBorder;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import net.sf.japi.progs.jeduca.jtest.Settings;
import net.sf.japi.swing.prefs.AbstractPrefs;

/** Settings module.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class GUIOptionsSettingsModule extends AbstractPrefs {

    /** Serial Version. */
    @SuppressWarnings({"AnalyzingVariableNaming"})
    private static final long serialVersionUID = 1L;

    /** The Settings. */
    private transient Settings settings;

    /** Create a FirstSettingsModule.
     * @param settings Settings to modify
     */
    public GUIOptionsSettingsModule(final Settings settings) {
        setLayout(new GridBagLayout());
        final JPanel askForQuitPanel = new JPanel(new GridLayout(2, 1));
        //setBorder(createCompoundBorder(createTitledBorder("Dummy Lösungen anzeigen"), createEtchedBorder()));
        askForQuitPanel.setBorder(createTitledBorder("Dummy Beim Beenden nachfragen"));
        final ButtonGroup askForQuit = new ButtonGroup();
        final JRadioButton askForQuitOn = new JRadioButton("Dummy Beim Beenden immer nachfragen");
        final JRadioButton askForQuitMaybe = new JRadioButton("Dummy Nur nachfragen, wenn ungespeicherte Daten vorhanden sind");
        final JRadioButton askForQuitOff = new JRadioButton("Dummy Nie nachfragen");
        askForQuitPanel.add(askForQuitOn);
        askForQuitPanel.add(askForQuitMaybe);
        askForQuitPanel.add(askForQuitOff);
        askForQuit.add(askForQuitOn);
        askForQuit.add(askForQuitMaybe);
        askForQuit.add(askForQuitOff);
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = REMAINDER;
        gbc.fill = HORIZONTAL;
        gbc.anchor = NORTHWEST;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        add(askForQuitPanel, gbc);
        setSettings(settings);
        setHelpURL(getClass().getClassLoader().getResource("help/GUIOptionsSettingsModule.html")); // TODO:2009-02-22:christianhujer:localize this
        setListLabelText("Dummy Auflösung2");
        setLabelText("Dummy Einstellungen zum Anzeigen der Auflösungen");
    }

    /** Set the Settings Object to be attached.
     * Automatically sets the state of all GUI components to the current settings of the Settings object.
     * @param settings Settings object to be attached
     */
    public void setSettings(final Settings settings) {
        this.settings = settings;
        restore();
    }

    /** ??? */ // TODO:2009-02-22:christianhujer:Provide in supertype and use {@inheritDoc} ?
    public void restore() {
        // TODO:2009-02-22:christianhujer:Implement.
    }

    /** {@inheritDoc} */
    public String getSettingsId() {
        return "askForQuit";
    }

    /** {@inheritDoc} */
    public void apply() {
        // TODO:2009-02-22:christianhujer:Implement.
        //settings.setShowQuestionSolution(showSolutionsOn.isSelected());
    }

    /** {@inheritDoc} */
    public void defaults() {
        // TODO:2009-02-22:christianhujer:Implement.
        restore();
    }

    public boolean isChanged() {
        // TODO:2009-02-22:christianhujer:Implement.
        return false;
    }

    public void revert() {
        // TODO:2009-02-22:christianhujer:Implement.
    }

    /** Returns the Settings.
     * @return the Settings.
     */
    public Settings getSettings() {
        return settings;
    }

} // class FirstSettingsModule
