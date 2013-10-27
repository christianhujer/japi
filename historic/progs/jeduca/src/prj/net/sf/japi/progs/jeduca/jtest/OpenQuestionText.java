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
import java.util.List;

/** Class for Open Questions.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class OpenQuestionText extends QuestionText {

    /** Serial Version. */
    private static final long serialVersionUID = 1L;

    /** The regular expressions.
     * @serial include
     */
    private List<String> expressions;

    /** Answer Text. */
    private transient String answer;

    /** Create an Open Question.
     */
    public OpenQuestionText() {
        expressions = new ArrayList<String>();
    }

    /** Create an Open Question.
     * @param text Question text
     */
    public OpenQuestionText(final String text) {
        super(text);
        expressions = new ArrayList<String>();
    }

    /** Create an Open Question.
     * @param text Question text
     * @param expressions Expressions
     */
    public OpenQuestionText(final String text, final Collection<String> expressions) {
        super(text);
        this.expressions = new ArrayList<String>(expressions);
    }

    /** Create an Open Question.
     * @param text Question text
     * @param expressions Expressions
     */
    public OpenQuestionText(final String text, final String... expressions) {
        super(text);
        this.expressions = new ArrayList<String>(Arrays.asList(expressions));
    }

    /** Set Answer Text.
     * @param answer Answer Text
     */
    public void setAnswer(final String answer) {
        this.answer = answer;
    }

    /** Get the Answer Text.
     * @return Answer Text
     */
    public String getAnswer() {
        return answer;
    }

    /** {@inheritDoc} */
    @Override public boolean isAnsweredCorrectly() {
        for (final String expression : expressions) {
            if (answer.matches(expression)) {
                return true;
            }
        }
        return false;
    }

    /** {@inheritDoc} */
    @Override public OpenQuestionText clone() throws CloneNotSupportedException {
        final OpenQuestionText clone = (OpenQuestionText) super.clone();
        // XXX:2009-02-22:christianhujer:IntelliJ IDEA: This is not the clone but required for deep clone of clone fields.
        //noinspection CloneCallsConstructors
        clone.expressions = new ArrayList<String>(expressions);
        return clone;
    }

} // class OpenQuestion
