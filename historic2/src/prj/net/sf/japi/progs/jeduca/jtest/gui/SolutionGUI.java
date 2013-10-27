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
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import net.sf.japi.progs.jeduca.jtest.MCAnswerText;
import net.sf.japi.progs.jeduca.jtest.MCQuestionText;
import net.sf.japi.progs.jeduca.jtest.OpenQuestionText;
import net.sf.japi.progs.jeduca.jtest.QuestionCollection;
import net.sf.japi.progs.jeduca.jtest.QuestionText;

/** Class to display the solution for one or more questions.
 * Choose yourself how many questions to show by simple choosing between {@link #setQuestion(QuestionText)} and {@link
 * #setQuestionCollection(QuestionCollection)}.
 * <p />
 * Basically, this class just wraps a JEditorPane in a JScrollPane and provides some methods to generate the HTML solution for a question.
 * Currently, the following question types are supported:
 * <ul>
 *  <li>Multiple Choice ({@link MCQuestionText})</li>
 *  <li>Open Questions ({@link OpenQuestionText})</li>
 * </ul>
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class SolutionGUI extends JComponent {

    /** Serial Version. */
    @SuppressWarnings({"AnalyzingVariableNaming"})
    private static final long serialVersionUID = 1L;

    /** The HTML Start String. */
    private static final String HTML_START = "<html><head><title>Auflösung</title><style type='text/css'>.not{font-weight:bold;color:#0000cc;}.right{font-weight:bold;color:#00cc00;}.wrong{font-weight:bold;color:#cc0000;}</style></head><body><h1>Auflösung</h1>";

    /** The HTML End String. */
    private static final String HTML_END = "</body></html>";

    /** The JEditorPane to show the solution.
     * @serial include
     */
    private final JEditorPane solution;

    /** Create a SolutionGUI. */
    public SolutionGUI() {
        setLayout(new BorderLayout());
        solution = new JEditorPane("text/html", "");
        solution.setEditable(false);
        add(new JScrollPane(solution));
    }

    /** Set the Question to show the solution for.
     * @param question Question to show solution for
     * @see #setQuestionCollection(QuestionCollection)
     */
    public void setQuestion(final QuestionText question) {
        final StringBuilder sb = new StringBuilder();
        sb.append(HTML_START);
        appendQuestion(sb, question);
        sb.append(HTML_END);
        solution.setText(sb.toString());
    }

    /** Set the QuestionCollection to show the solution for.
     * @param col QuestionCollection to show solution for
     * @see #setQuestion(QuestionText)
     */
    public void setQuestionCollection(final QuestionCollection col) {
        final StringBuilder sb = new StringBuilder();
        sb.append(HTML_START);
        for (final QuestionText question : col) {
            appendQuestion(sb, question);
        }
        sb
            .append("<hr>")
            .append("<h2>Statistik</h2>")
            .append("<p>Fragen insgesamt: ").append(col.getSize()).append("</p>")
            .append("<p>Richtig beantwortet: ").append(col.countRightAnswers()).append("</p>")
            .append("<p>Falsch beantwortet: ").append(col.countWrongAnswers()).append("</p>");
        sb.append(HTML_END);
        solution.setText(sb.toString());
    }

    /** Append HTML for a QuestionText.
     * @param sb StringBuilder to append to
     * @param question QuestionText to append HTML for
     * @return supplied StringBuilder for convenience
     */
    private static StringBuilder appendQuestion(final StringBuilder sb, final QuestionText question) {
        sb
            .append("<hr>")
            .append("<h2>Frage:</h2>")
            .append("<p>").append(question.getText()).append("</p>")
            .append("<p>Ihre Antwort war: ").append(question.isAnsweredCorrectly() ? "<span class='right'>richtig</span>" : "<span class='wrong'>falsch</span>").append("</p>")
            .append("<h2>Antwort:</h2>");
        if (question instanceof MCQuestionText) {
            appendMCQuestion(sb, (MCQuestionText) question);
        } else if (question instanceof OpenQuestionText) {
            appendOpenQuestion(sb, (OpenQuestionText) question);
        }
        return sb;
    }

    /** Append HTML for MCQuestionText.
     * @param sb StringBuilder to append to
     * @param question QuestionText to append HTML for
     * @return supplied StringBuilder for convenience
     */
    private static StringBuilder appendMCQuestion(final StringBuilder sb, final MCQuestionText question) {
        sb.append("<ul>");
        for (final MCAnswerText answer : question.getAnswerTexts()) {
            sb.append("<li><span");
            if (answer.isCorrect() && !answer.isCheckedCorrectly()) {
                sb.append(" class='not'");
            }
            if (answer.isCorrect() && answer.isCheckedCorrectly()) {
                sb.append(" class='right'");
            }
            if (!answer.isCorrect() && !answer.isCheckedCorrectly()) {
                sb.append(" class='wrong'");
            }
            sb
                .append('>')
                .append(answer.getText())
                .append("</span>")
                .append("</li>");
        }
        sb.append("</ul>");
        return sb;
    }

    /** Append HTML for OpenQuestionText.
     * @param sb StringBuilder to append to
     * @param question QuestionText to append HTML for
     * @return supplied StringBuilder for convenience
     */
    private static StringBuilder appendOpenQuestion(final StringBuilder sb, final OpenQuestionText question) {
        // TODO:2009-02-22:christianhujer:Implement appending.
        //sb.append("<ul>");
        //sb.append(question.getAnswerExplanantion());
        return sb;
    }

} // class SolutionGUI
