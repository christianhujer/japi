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

/** A Question.
 * Mutable object for questions.
 * To avoid needless defensive copying, apply this general usage contract:
 * <ul>
 *  <li>While staying in the same QuestionCollection, only clone if a copy is desired at user's will, else refer.</li>
 *  <li>When going to another QuestionCollection, clone</li>
 *  <li>When being at the GUI, clone if you must write changes to the object which are not yet to go the the QuestionCollection. Otherwise refer.</li>
 * </ul>
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public abstract class QuestionText implements Serializable, Cloneable {

    /** Serial Version. */
    private static final long serialVersionUID = 1L;

    /** The HTML state of this QuestionText.
     * @serial include
     */
    private boolean html;

    /** The marked state of this QuestionText. */
    private transient boolean marked;

    /** The question text.
     * @serial include
     */
    private String text;

    /** The question type (KEduca).
     * @serial include
     */
    private String type;

    /** Create a Question.
     */
    protected QuestionText() {
    }

    /** Create a Question.
     * @param text Question text
     */
    protected QuestionText(final String text) {
        this.text = text;
    }

    /** Set the text of this question.
     * @param text new text for this question
     */
    public void setText(final String text) {
        this.text = text;
    }

    /** Get the text of this question.
     * @return text of this question
     */
    public String getText() {
        return text;
    }

    /** Set the type of this question (KEduca).
     * @param type new type for this question
     */
    public void setType(final String type) {
        this.type = type;
    }

    /** Get the type of this question.
     * @return type of this question
     */
    public String getType() {
        return type;
    }

    /** Get the HTML state of this question.
     * @return true if this question is HTML, false otherwise
     */
    public boolean isHTML() {
        return html;
    }

    /** Set the HTML state of this question.
     * @param html new HTML state for this question
     */
    public void setHTML(final boolean html) {
        this.html = html;
    }

    /** Get the marked state of this question.
     * @return marked state of this question
     */
    public boolean isMarked() {
        return marked;
    }

    /** Set the marked state of this question.
     * @param marked new marked state for this question
     */
    public void setMarked(final boolean marked) {
        this.marked = marked;
    }

    /** Get whether this question has been answered correctly.
     * @return <code>true</code> if this question has been answered correctly, <code>false</code> otherwise
     */
    public abstract boolean isAnsweredCorrectly();

    /** {@inheritDoc}
     * Subclasses should eventually override this method if they are compositions thats components need cloning as well.
     */
    @Override public QuestionText clone() throws CloneNotSupportedException {
        try {
            return (QuestionText) super.clone();
        } catch (CloneNotSupportedException e) {
            assert false;
            throw new Error(e);
        }
    }

} // class QuestionText
