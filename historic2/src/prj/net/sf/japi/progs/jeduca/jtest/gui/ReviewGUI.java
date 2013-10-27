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
import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.WEST;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import net.sf.japi.progs.jeduca.jtest.QuestionCollection;
import net.sf.japi.progs.jeduca.jtest.QuestionText;
import net.sf.japi.swing.VerticalFlowLayout;

/** Graphical user interface for displaying a review page for all questions.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class ReviewGUI extends JComponent {

    /** Serial Version. */
    private static final long serialVersionUID = 1L;

    /** The QuestionCollection to review. */
    private transient QuestionCollection col;

    /** The Panel with the Review elements.
     * @serial include
     */
    private final JPanel reviews;

    /** The QuestionCollectionGUI.
     * @serial include
     */
    private final QuestionCollectionGUI questionCollectionGUI;

    /** The JCheckBoxes.
     * @serial include
     */
    private JCheckBox[] checkBoxes;

    /** Create a ReviewGUI.
     * @param questionCollectionGUI QuestionCollectionGUI to interact with
     */
    public ReviewGUI(final QuestionCollectionGUI questionCollectionGUI) {
        this.questionCollectionGUI = questionCollectionGUI;
        setLayout(new BorderLayout());
        reviews = new JPanel(new VerticalFlowLayout());
        add(reviews);
    }

    /** Set the QuestionCollection to review.
     * @param col QuestionCollection to review
     */
    public void setQuestionCollection(final QuestionCollection col) {
        this.col = col;
        reviews.removeAll();
        checkBoxes = new JCheckBox[col.getSize()];
        int i = 0;
        for (final QuestionText question : col) {
            reviews.add(new ReviewCheckbox(question, i++));
        }
        invalidate();
        validate();
        repaint();
    }

    /** Update checkbox states. */
    public void updateStates() {
        for (int i = 0; i < col.getSize(); i++) {
            checkBoxes[i].setSelected(col.getQuestion(i).isMarked());
        }
    }

    /** Class for a Review checkbox with link.
     * @author $Author: chris $
     * @version $Id: ReviewGUI.java,v 1.4 2006/01/31 23:09:18 chris Exp $
     */
    private class ReviewCheckbox extends JComponent implements ChangeListener, ActionListener {

        /** Serial Version. */
        private static final long serialVersionUID = 1L;

        /** The JCheckBox. */
        private JCheckBox cb;

        /** The JButton. */
        private JButton bb;

        /** Index of Question. */
        private int n;

        /** Question to review. */
        private QuestionText question;

        /** Create a ReviewCheckbox.
         * @param question QuestionText to create ReviewCheckbox for
         * @param n Question number
         */
        ReviewCheckbox(final QuestionText question, final int n) {
            this.n = n;
            this.question = question;
            setLayout(new BorderLayout());
            cb = new JCheckBox();
            checkBoxes[n] = cb;
            bb = new JButton("Frage: " + (n + 1));
            cb.setSelected(question.isMarked());
            cb.addChangeListener(this);
            bb.addActionListener(this);
            add(cb, WEST);
            add(bb, CENTER);
        }

        /** {@inheritDoc} */
        public void stateChanged(final ChangeEvent e) {
            if (e.getSource() != cb) { assert false; return; }
            question.setMarked(cb.isSelected());
        }

        /** {@inheritDoc} */
        public void actionPerformed(final ActionEvent e) {
            if (e.getSource() != bb) { assert false; return; }
            questionCollectionGUI.displayQuestion(n);
        }

    } // class ReviewCheckbox

} // class ReviewGUI
