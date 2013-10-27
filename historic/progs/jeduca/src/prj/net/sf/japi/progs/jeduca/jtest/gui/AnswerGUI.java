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

import java.awt.BorderLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import net.sf.japi.progs.jeduca.jtest.MCAnswerText;

/** Class to vizualize a single MCAnswerText.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class AnswerGUI extends JComponent implements ChangeListener {

    /** Serial Version. */
    @SuppressWarnings({"AnalyzingVariableNaming"})
    private static final long serialVersionUID = 1L;

    /** HTML Start string. */
    private static final String HTML_START = "<html><head><title></title></head><body>";

    /** HTML End string. */
    private static final String HTML_END = "</body></html>";

    /** Answer to display GUI for. */
    private final transient MCAnswerText answer;

    /** The button the user may push.
     * @serial include
     */
    private final JToggleButton button;

    /** Create an AnswerGUI.
     * @param answer Answer to vizualize
     * @param buttons ButtonGroup to add to (maybe null, in which case a CheckBox instead of a RadioButton will be created)
     * @param index index of question, used for Mnemonic, starting with 1 for 'A'
     */
    public AnswerGUI(final MCAnswerText answer, final ButtonGroup buttons, final int index) {
        setLayout(new BorderLayout());
        this.answer = answer;
        final char mnemo = (char) ('A' + index - 1);
        final String answerText = answer.isHTML()
                ? HTML_START + "<u>" + mnemo + "</u>. " + answer.getText() + HTML_END
                : answer.getText();
        if (buttons == null) {
            //button = new JCheckBox((answer.isHTML() ? "<html>&nbsp;" : "") + answer.getText());
            button = new JCheckBox(answerText);
        } else {
            //button = new JRadioButton((answer.isHTML() ? "<html>&nbsp;" : "") + answer.getText());
            button = new JRadioButton(answerText);
            buttons.add(button);
        }
        button.setMnemonic(mnemo);
        button.addChangeListener(this);
        if (answer.isChecked()) {
            button.setSelected(true);
        }
        add(button);
    }

    /** Get whether this single answer has been checked correctly.
     * @return true if the user checked this answer in correct state, false otherwise
     */
    public boolean isCorrectlyChecked() {
        return button.isSelected() == answer.isCorrect();
    }

    /** {@inheritDoc} */
    public void stateChanged(final ChangeEvent e) {
        //noinspection ObjectEquality
        if (e.getSource() != button) {
            assert false;
            return;
        }
        answer.setChecked(button.isSelected());
    }

} // class AnswerGUI
