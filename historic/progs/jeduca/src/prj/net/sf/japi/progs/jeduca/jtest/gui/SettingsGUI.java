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

import java.awt.FlowLayout;
import java.awt.GridLayout;
import static javax.swing.BorderFactory.createCompoundBorder;
import static javax.swing.BorderFactory.createEtchedBorder;
import static javax.swing.BorderFactory.createTitledBorder;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import net.sf.japi.progs.jeduca.jtest.Settings;

/** User Interface for loading, saving and changing the settings.
 * @todo do some rework to use SettingsPanels that contain a certain field of settings and some index function to make it look similar as the settings
 * of KDE / stuff look like.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class SettingsGUI extends JComponent {

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

    /** Create a SettingsGUI.
     * @param settings Settings to create a GUI for.
     */
    public SettingsGUI(final Settings settings) {
        setLayout(new FlowLayout());
        final JPanel solutionPanel = new JPanel(new GridLayout(2, 1));
        solutionPanel.setBorder(createCompoundBorder(createTitledBorder("Lösungen anzeigen"), createEtchedBorder()));
        final ButtonGroup solutions = new ButtonGroup();
        showSolutionsOn = new JRadioButton("Auflösung nach jeder Frage anzeigen");
        showSolutionsOff = new JRadioButton("Auflösung für alle Fragen erst am Ende des Tests anzeigen");
        solutionPanel.add(showSolutionsOn);
        solutionPanel.add(showSolutionsOff);
        solutions.add(showSolutionsOn);
        solutions.add(showSolutionsOff);
        add(solutionPanel);
        setSettings(settings);
    }

    /** Set the Settings Object to be attached.
     * @param settings Settings object to be attached
     */
    public void setSettings(final Settings settings) {
        this.settings = settings;
        restore();
    }

    /** Store settings in Settings object. */
    public void store() {
        settings.setShowQuestionSolution(showSolutionsOn.isSelected());
    }

    /** Restore settings from Settings object. */
    public void restore() {
        showSolutionsOn.setSelected(settings.isShowQuestionSolution());
        showSolutionsOff.setSelected(!settings.isShowQuestionSolution());
    }

    /** Save settings from Settings object (Convenience method). */
    public void save() {
        settings.save();
    }

} // class SettingsGUI
