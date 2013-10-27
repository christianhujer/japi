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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/** Class for holding a Collection of Questions.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class QuestionCollection implements Serializable, Iterable<QuestionText> {

    /** Serial Version. */
    private static final long serialVersionUID = 1L;

    /** The Questions.
     * @serial include
     */
    private final List<QuestionText> questions;

    /** The title of this collection.
     * @serial include
     */
    private String title;

    /** The category of this collection.
     * @serial include
     */
    private String category;

    /** The type of this collection (KEduca).
     * @serial include
     */
    private String type;

    /** The level of this collection (KEduca).
     * @serial include
     */
    private String level;

    /** The language of this collection (ISO 2-Letter).
     * @serial include
     */
    private String language;

    /** The Author name of this collection.
     * @serial include
     */
    private String authorName;

    /** The Author mail of this collection.
     * @serial include
     */
    private String authorEMail;

    /** The Author www of this collection.
     * @serial include
     */
    private String authorWWW;

    /** Create an empty QuestionCollection. */
    public QuestionCollection() {
        questions = new ArrayList<QuestionText>();
    }

    /** Create a QuestionCollection.
     * @param questions Questions for collection
     */
    public QuestionCollection(final QuestionText... questions) {
        this.questions = new ArrayList<QuestionText>(Arrays.asList(questions));
    }

    /** Create a QuestionCollection.
     * @param questions Questions for collection
     */
    public QuestionCollection(final Collection<QuestionText> questions) {
        this.questions = new ArrayList<QuestionText>(questions);
    }

    /** Add a Question.
     * Because this method cannot know whether or not the QuestionText is or is not made solely for this QuestionCollection, the QuestionText is not cloned.
     * But it is generally a good idea to clone QuestionTexts coming from other QuestionCollections before adding them to this QuestionCollection,
     * otherwise strange effects might occur if a user copies a QuestionText from one QuestionCollection to another and the copy is not a duplicate.
     * @param question Question to add
     */
    public void addQuestion(final QuestionText question) {
        questions.add(question);
    }

    /** Remove a Question.
     * @param question Question to remove
     */
    public void removeQuestion(final QuestionText question) {
        questions.remove(question);
    }

    /** Remove a Question.
     * @param index Index of Question to remove
     */
    public void removeQuestion(final int index) {
        questions.remove(index);
    }

    /** Get a Question from the Collection.
     * @param index Index of Question to get
     * @return Question with the specified index.
     */
    public QuestionText getQuestion(final int index) {
        return questions.get(index);
    }

    /** Get the number of Questions in this QuestionCollection.
     * @return number of Questions
     */
    public int getSize() {
        return questions.size();
    }

    /** Set the title of this collection.
     * @param title new title for this collection
     */
    public void setTitle(final String title) {
        this.title = title;
    }

    /** Get the title of this collection.
     * @return title of this collection
     */
    public String getTitle() {
        return title;
    }

    /** Set the category of this collection.
     * @param category new category for this collection
     */
    public void setCategory(final String category) {
        this.category = category;
    }

    /** Get the category of this collection.
     * @return category of this collection
     */
    public String getCategory() {
        return category;
    }

    /** Set the type of this collection (KEduca).
     * @param type new type for this collection
     */
    public void setType(final String type) {
        this.type = type;
    }

    /** Get the type of this collection (KEduca).
     * @return type of this collection
     */
    public String getType() {
        return type;
    }

    /** Set the level of this collection (KEduca).
     * @param level new level for this collection
     */
    public void setLevel(final String level) {
        this.level = level;
    }

    /** Get the level of this collection.
     * @return level of this collection
     */
    public String getLevel() {
        return level;
    }

    /** Set the language of this collection.
     * @param language new language for this collection
     */
    public void setLanguage(final String language) {
        this.language = language;
    }

    /** Get the language of this collection.
     * @return language of this collection
     */
    public String getLanguage() {
        return language;
    }

    /** Set the author name of this collection.
     * @param authorName new author name for this collection
     */
    public void setAuthorName(final String authorName) {
        this.authorName = authorName;
    }

    /** Get the author name of this collection.
     * @return author name of this collection
     */
    public String getAuthorName() {
        return authorName;
    }

    /** Set the author email of this collection.
     * @param authorEMail new author email for this collection
     */
    public void setAuthorEMail(final String authorEMail) {
        this.authorEMail = authorEMail;
    }

    /** Get the author email of this collection.
     * @return author email of this collection
     */
    public String getAuthorEMail() {
        return authorEMail;
    }

    /** Set the author www of this collection.
     * @param authorWWW new author www for this collection
     */
    public void setAuthorWWW(final String authorWWW) {
        this.authorWWW = authorWWW;
    }

    /** Get the author www of this collection.
     * @return author www of this collection
     */
    public String getAuthorWWW() {
        return authorWWW;
    }

    /** {@inheritDoc} */
    public Iterator<QuestionText> iterator() {
        return Collections.unmodifiableList(questions).iterator();
    }

    /** Count the number of questions answered correctly.
     * @return number of questions answered correctly
     */
    public int countRightAnswers() {
        int n = 0;
        for (final QuestionText question : this) {
            if (question.isAnsweredCorrectly()) {
                n++;
            }
        }
        return n;
    }

    /** Count the number of questions answered falsely.
     * @return number of questions answered wrong
     */
    public int countWrongAnswers() {
        int n = 0;
        for (final QuestionText question : this) {
            if (!question.isAnsweredCorrectly()) {
                n++;
            }
        }
        return n;
    }

    /** Get the index of a question.
     * @param question QuestionText to get index for
     * @return index of Question or -1 if <var>question</var> was <code>null</code> or could not be found
     */
    public int indexOf(final QuestionText question) {
        return questions.indexOf(question);
    }

} // class QuestionCollection
