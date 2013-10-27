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

package net.sf.japi.progs.jeduca.jtest;

import java.util.List;

/** Class for Multiple Choice Answers.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class MCAnswerText extends AnswerText {

    /** Serial Version. */
    private static final long serialVersionUID = 1L;

    /** The checked state of this answertext. */
    private transient boolean checked;

    /** The correct state of this answertext.
     * @serial include
     */
    private boolean correct;

    /** Create an Answer.
     * @param text Text for this answer.
     * @param correct Correct state of this answer
     */
    public MCAnswerText(final String text, final boolean correct) {
        super(text);
        this.correct = correct;
    }

    /** Create an Answer.
     * @param text Text for this answer.
     * @param correct Correct state of this answer
     * @param html HTML state for this answer
     */
    public MCAnswerText(final String text, final boolean correct, final boolean html) {
        super(text, html);
        this.correct = correct;
    }

    /** Get the correct state of this answer.
     * @return correct state (true for correct, false otherwise)
     */
    public boolean isCorrect() {
        return correct;
    }

    /** Set the correct state of this answer.
     * @param correct new correct state
     */
    public void setCorrect(final boolean correct) {
        this.correct = correct;
    }

    /** Set the checked state of this answer.
     * @param checked new checked state
     */
    public void setChecked(final boolean checked) {
        this.checked = checked;
    }

    /** Get the checked state of this answer.
     * @return true if checked, false otherwise
     */
    public boolean isChecked() {
        return checked;
    }

    /** Get whether or not this answer has been checked correctly.
     * @return true if checked correctly, false otherwise
     */
    public boolean isCheckedCorrectly() {
        return checked ^ !correct;
    }

    /** Count the number of right answers in a list of answers.
     * @param answers List of answers
     * @return number of right answers in <var>answers</var>
     */
    public static int countRightAnswers(final List<MCAnswerText> answers) {
        int right = 0;
        for (final MCAnswerText answer : answers) {
            if (answer.correct) {
                right++;
            }
        }
        return right;
    }

    /** Count the number of wrong answers in a list of answers.
     * @param answers List of answers
     * @return number of wrong answers in <var>answers</var>
     */
    public static int countWrongAnswers(final List<MCAnswerText> answers) {
        int wrong = 0;
        for (final MCAnswerText answer : answers) {
            if (!answer.correct) {
                wrong++;
            }
        }
        return wrong;
    }

    /** {@inheritDoc} */
    @Override public MCAnswerText clone() throws CloneNotSupportedException {
        return (MCAnswerText) super.clone();
    }

} // class MCAnswerText
