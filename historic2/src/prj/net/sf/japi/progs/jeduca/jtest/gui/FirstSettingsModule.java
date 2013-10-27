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
public class FirstSettingsModule extends AbstractPrefs {

    /** Serial Version. */
    @SuppressWarnings({"AnalyzingVariableNaming"})
    private static final long serialVersionUID = 1L;

    /** The Settings. */
    private transient Settings settings;

    /** ShowSolutionsOn.
     * @serial include
     */
    private final JRadioButton showSolutionsOn;

    /** ShowSolutionsOff.
     * @serial include
     */
    private final JRadioButton showSolutionsOff;

    /** Create a FirstSettingsModule.
     * @param settings Settings to modify
     */
    public FirstSettingsModule(final Settings settings) {
        setLayout(new GridBagLayout());
        final JPanel showSolutionPanel = new JPanel(new GridLayout(2, 1));
        //setBorder(createCompoundBorder(createTitledBorder("Dummy Lösungen anzeigen"), createEtchedBorder()));
        showSolutionPanel.setBorder(createTitledBorder("Dummy Lösungen anzeigen"));
        final ButtonGroup solutions = new ButtonGroup();
        showSolutionsOn = new JRadioButton("Dummy Auflösung nach jeder Frage anzeigen");
        showSolutionsOff = new JRadioButton("Auflösung für alle Fragen erst am Ende des Tests anzeigen");
        showSolutionPanel.add(showSolutionsOn);
        showSolutionPanel.add(showSolutionsOff);
        solutions.add(showSolutionsOn);
        solutions.add(showSolutionsOff);
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = REMAINDER;
        gbc.fill = HORIZONTAL;
        gbc.anchor = NORTHWEST;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        add(showSolutionPanel, gbc);
        setSettings(settings);
        setHelpURL(getClass().getClassLoader().getResource("help/FirstSettingsModule.html"));
        setListLabelText("Dummy Auflösung");
        setLabelText("Dummy Einstellungen zum Anzeigen der Auflösungen");
    }

    /** Returns the Settings.
     * @return the Settings.
     */
    public Settings getSettings() {
        return settings;
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
        showSolutionsOn.setSelected(settings.isShowQuestionSolution());
        showSolutionsOff.setSelected(!settings.isShowQuestionSolution());
    }

    /** {@inheritDoc} */
    public String getSettingsId() {
        return "displaySolution";
    }

    /** {@inheritDoc} */
    public void apply() {
        settings.setShowQuestionSolution(showSolutionsOn.isSelected());
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

} // class FirstSettingsModule
