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
import static java.awt.BorderLayout.SOUTH;
import java.awt.GridBagConstraints;
import static java.awt.GridBagConstraints.HORIZONTAL;
import static java.awt.GridBagConstraints.NORTHWEST;
import static java.awt.GridBagConstraints.REMAINDER;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import static javax.swing.JSplitPane.VERTICAL_SPLIT;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import net.sf.japi.progs.jeduca.jtest.MCAnswerText;
import net.sf.japi.progs.jeduca.jtest.MCQuestionText;
import net.sf.japi.progs.jeduca.jtest.OpenQuestionText;
import net.sf.japi.progs.jeduca.jtest.QuestionText;

/** JComponent to visualize a question and its answers.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class QuestionGUI extends JComponent implements ChangeListener {

    /** Serial Version. */
    @SuppressWarnings({"SerialVersionUIDWithWrongSignature"})
    public static final long serialVersionUID = 1L;

    /** The QuestionPanel.
     * @serial include
     */
    private JEditorPane questionPanel;

    /** The AnswerPanel.
     * @serial include
     */
    private JPanel answersPanel;

    /** The GridBagConstraints.
     * @serial include
     */
    private GridBagConstraints gbc;

    /** The Question currently displayed.  */
    private transient QuestionText question;

    /** The CheckBox to mark a question.
     * @serial include
     */
    private JCheckBox marked;

    /** The JTextField for open questions.
     * @serial include
     */
    private JTextField textField;

    /** Create a QuestionGUI
     */
    public QuestionGUI() {
        createUI();
    }

    /** Create a QuestionGUI.
     * @param question Question to visualize
     */
    public QuestionGUI(final QuestionText question) {
        this();
        setQuestion(question);
    }

    /** Build the User Interface. */
    private void createUI() {
        setLayout(new BorderLayout());
        textField = new JTextField();
        gbc = new GridBagConstraints();
        final Insets insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = REMAINDER;
        gbc.fill = HORIZONTAL;
        gbc.anchor = NORTHWEST;
        gbc.weightx = 1.0;
        answersPanel = new JPanel(new GridBagLayout());
        final JScrollPane answersPane = new JScrollPane(answersPanel);
        answersPane.setBorder(new CompoundBorder(new EmptyBorder(insets), new TitledBorder(new EtchedBorder(), "Answers")));
        questionPanel = new JEditorPane("text/html", "");
        final JSplitPane split = new JSplitPane(VERTICAL_SPLIT, true, new JScrollPane(questionPanel), answersPane);
        split.setResizeWeight(0.5);
        add(split);
        marked = new JCheckBox("markieren");
        marked.addChangeListener(this);
        add(marked, SOUTH);
    }

    /** Set the Question to be displayed.
     * @param question Question to be displayed
     */
    public void setQuestion(final QuestionText question) {
        if (this.question instanceof OpenQuestionText) {
            ((OpenQuestionText) this.question).setAnswer(textField.getText());
        }
        this.question = question;
        answersPanel.removeAll();
        gbc.weighty = 1.0;
        questionPanel.setText("<html><h1>Frage:</h1>" + question.getText() + "</html>");
        questionPanel.setEditable(false);
        gbc.weighty = 0.0;
        if (question instanceof MCQuestionText) {
            final List<MCAnswerText> answers = ((MCQuestionText) question).getAnswerTexts();
            final ButtonGroup gp = ((MCQuestionText) question).countRightAnswers() == 1 ? new ButtonGroup() : null;
            int i = 1;
            for (final MCAnswerText answer : answers) {
                answersPanel.add(new AnswerGUI(answer, gp, i++), gbc);
            }
        } else if (question instanceof OpenQuestionText) {
            textField.setText(((OpenQuestionText) question).getAnswer());
            answersPanel.add(textField);
        }
        gbc.weighty = 1.0;
        answersPanel.add(new JLabel(), gbc);
        marked.setSelected(question.isMarked());
        invalidate();
        validate();
        repaint();
    }

    /**  {@inheritDoc} */
    public void stateChanged(final ChangeEvent e) {
        if (e.getSource() != marked) {
            return;
        }
        question.setMarked(marked.isSelected());
    }

} // class QuestionGUI
