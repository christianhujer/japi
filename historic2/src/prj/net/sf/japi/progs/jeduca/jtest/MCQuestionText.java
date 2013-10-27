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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/** Class for Multiple Choice Questions.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class MCQuestionText extends QuestionText {

    /** Serial Version. */
    private static final long serialVersionUID = 1L;

    /** The answers.
     * @serial include
     */
    private List<MCAnswerText> answers;

    /** Create a Question. */
    public MCQuestionText() {
        answers = new ArrayList<MCAnswerText>();
    }

    /** Create a Question.
     * @param text Question text
     */
    public MCQuestionText(final String text) {
        super(text);
        answers = new ArrayList<MCAnswerText>();
    }

    /** Create a Question.
     * @param text Question text
     * @param answers AnswerTexts
     */
    public MCQuestionText(final String text, final MCAnswerText... answers) {
        super(text);
        this.answers = new ArrayList<MCAnswerText>(Arrays.asList(answers)); // Wrap since Arrays.asList() returns an immutable list
    }

    /** Create a Queston.
     * @param text Question text
     * @param answers AnswerTexts
     */
    public MCQuestionText(final String text, final Collection<MCAnswerText> answers) {
        super(text);
        this.answers = new ArrayList<MCAnswerText>(answers);
    }

    /** Add an answer to this question.
     * @param answer Answer to add
     */
    public void addAnswer(final MCAnswerText answer) {
        answers.add(answer);
    }

    /** Remove an answer from this question.
     * @param answer Answer to remove
     */
    public void removeAnswer(final MCAnswerText answer) {
        answers.remove(answer);
    }

    /** Remove an answer from this question
     * @param index Answer index to remove
     */
    public void removeAnswer(final int index) {
        answers.remove(index);
    }

    /** Remove all answers. */
    public void clearAnswers() {
        answers.clear();
    }

    /** Return the number of answers for this question.
     * @return number of answers for this question
     */
    public int getSize() {
        return answers.size();
    }

    /** Return an answer with a certain index.
     * @param index index of answer to return
     * @return answer with index
     */
    public MCAnswerText getAnswer(final int index) {
        return answers.get(index);
    }

    /** Get the number of right answers.
     * @return number of right answers
     */
    public int countRightAnswers() {
        return MCAnswerText.countRightAnswers(answers);
    }

    /** Get the number of wrong answers.
     * @return number of wrong answers
     */
    public int countWrongAnswers() {
        return MCAnswerText.countWrongAnswers(answers);
    }

    /** Get all right answers in original order.
     * @return List with right answers
     */
    public List<MCAnswerText> getRightAnswerTexts() {
        final List<MCAnswerText> right = new ArrayList<MCAnswerText>();
        for (final MCAnswerText answer : answers) {
            if (answer.isCorrect()) {
                right.add(answer);
            }
        }
        return right;
    }

    /** Get all wrong answers in original order.
     * @return List with wrong answers
     */
    public List<MCAnswerText> getWrongAnswerTexts() {
        final List<MCAnswerText> wrong = new ArrayList<MCAnswerText>();
        for (final MCAnswerText answer : answers) {
            if (!answer.isCorrect()) {
                wrong.add(answer);
            }
        }
        return wrong;
    }

    /** Get all answers.
     * @return List with all answers in original order
     */
    public List<MCAnswerText> getAnswerTexts() {
        return new ArrayList<MCAnswerText>(answers);
    }

    /** Get all answers.
     * @return List with all answers in random order
     */
    public List<MCAnswerText> getRandomAnswerTexts() {
        final List<MCAnswerText> randomAnswers = new ArrayList<MCAnswerText>(answers);
        Collections.shuffle(randomAnswers);
        return randomAnswers;
    }

    /** Get a certain set of AnswerTexts.
     * @param n maximum number of answer texts.
     * @return a set of not more than <var>n</var> answertexts with at least 1 right answertext
     */
    public List<MCAnswerText> getAnswerTexts(final int n) {
        final List<MCAnswerText> rightAnswers = getRightAnswerTexts();
        final List<MCAnswerText> wrongAnswers = getWrongAnswerTexts();
        final List<MCAnswerText> answerTexts = new ArrayList<MCAnswerText>();
        final int rightAnswersLeft = rightAnswers.size();
        if (rightAnswersLeft > 0) {
            answerTexts.add(rightAnswers.remove((int) (Math.random() * rightAnswersLeft)));
        }
        final List<MCAnswerText> allAnswers = new ArrayList<MCAnswerText>();
        allAnswers.addAll(rightAnswers);
        allAnswers.addAll(wrongAnswers);
        int allAnswersLeft = allAnswers.size();
        for (int i = 0; i < 4 && allAnswersLeft > 0; i++) {
            answerTexts.add(allAnswers.remove((int) (Math.random() * allAnswersLeft--)));
        }
        return answerTexts;
    }

    /** Get whether or not this question has been answered correctly.
     * @return <code>true</code> if this question has been answered correctly, <code>false</code> otherwise
     */
    @Override public boolean isAnsweredCorrectly() {
        boolean answeredCorrectly = true;
        for (final MCAnswerText answer : answers) {
            answeredCorrectly &= answer.isCheckedCorrectly();
        }
        return answeredCorrectly;
    }

    /** {@inheritDoc} */
    @Override public MCQuestionText clone() throws CloneNotSupportedException {
        final MCQuestionText clone = (MCQuestionText) super.clone();
        //noinspection CloneCallsConstructors
        clone.answers = new ArrayList<MCAnswerText>(answers);
        for (int i = 0, size = clone.answers.size(); i < size; i++) {
            clone.answers.set(i, clone.answers.get(i).clone());
        }
        return clone;
    }

} // class MCQuestionText
