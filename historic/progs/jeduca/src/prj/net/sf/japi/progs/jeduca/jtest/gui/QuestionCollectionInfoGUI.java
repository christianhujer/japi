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
import javax.swing.JLabel;
import net.sf.japi.progs.jeduca.jtest.QuestionCollection;

/** Displays information about a QuestionCollection.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class QuestionCollectionInfoGUI extends JComponent {

    /** Serial Version. */
    @SuppressWarnings({"AnalyzingVariableNaming"})
    private static final long serialVersionUID = 1L;

    /** The Label.
     * @serial include
     */
    private final JLabel label;

    /** Create a new QuestionCollectionInfoGUI.
     */
    public QuestionCollectionInfoGUI() {
        setLayout(new BorderLayout());
        label = new JLabel();
        add(label);
    }

    /** Create a new QuestionCollectionInfoGUI.
     * @param col QuestionCollection to display information for
     */
    public QuestionCollectionInfoGUI(final QuestionCollection col) {
        this();
        setQuestionCollection(col);
    }

    /** Set the QuestionCollection to display information for.
     * @param col QuestionCollection to display information for
     */
    public void setQuestionCollection(final QuestionCollection col) {
        final StringBuilder sb = new StringBuilder();
        sb.append("<html><head><title>");
        sb.append(col.getTitle());
        sb.append("</title></head><body><div stlye='text-align:center';><h1>");
        sb.append(col.getTitle());
        sb.append("</h1>");
        sb.append("<p>Autor: ");
        sb.append(col.getAuthorName());
        sb.append("</p></body></html>");
        label.setText(sb.toString());
    }

} // class QuestionCollectionInfoGUI
