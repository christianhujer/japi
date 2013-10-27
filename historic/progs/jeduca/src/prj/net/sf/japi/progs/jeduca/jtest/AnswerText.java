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

import java.io.Serializable;
import net.sf.japi.progs.jeduca.util.Text;

/** An Answer.
 * Mutable object for answers.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public abstract class AnswerText implements Serializable, Cloneable {

    /** Serial Version. */
    @SuppressWarnings({"AnalyzingVariableNaming"})
    private static final long serialVersionUID = 1L;

    /** The HTML state of this AnswerText.
     * @serial include
     */
    private boolean html;

    /** The text of this answer.
     * @serial include
     */
    private String text;

    /** The explanation of this answer.
     * @serial include
     */
    private Text explanation;

    /** Create an Answer. */
    protected AnswerText() {
    }

    /** Create an Answer.
     * @param text Text for this answer.
     */
    protected AnswerText(final String text) {
        this.text = text;
    }

    /** Create an Answer.
     * @param text Text for this answer.
     * @param html HTML state for this answer
     */
    protected AnswerText(final String text, final boolean html) {
        this.text = text;
        this.html = html;
    }

    /** Get the text of this answer.
     * @return text of this answer
     */
    public String getText() {
        return text;
    }

    /** Set the text of this answer.
     * @param text new text for this answer
     */
    public void setText(final String text) {
        this.text = text;
    }

    /** Get the HTML state of this answer.
     * @return true if this answer is HTML, false otherwise
     */
    public boolean isHTML() {
        return html;
    }

    /** Set the HTML state of this answer.
     * @param html new HTML state for this answer
     */
    public void setHTML(final boolean html) {
        this.html = html;
    }

    /** Get the explanation for this answer.
     * Returns <code>null</code> if this answer has no explanation
     * @return Explanation or <code>null</code> if this answer has no explanation.
     */
    public Text getExplanation() {
        return explanation;
    }

    /** Set the explanation for this answer.
     * @param explanation explanation for this answer
     */
    public void setExplanation(final Text explanation) {
        this.explanation = explanation;
    }

    /** {@inheritDoc} */
    @Override public AnswerText clone() throws CloneNotSupportedException {
        return (AnswerText) super.clone();
    }

} // class AnswerText
