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
import java.awt.CardLayout;
import static java.awt.event.KeyEvent.VK_N;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import net.sf.japi.progs.jeduca.jtest.QuestionCollection;
import net.sf.japi.progs.jeduca.jtest.QuestionText;
import net.sf.japi.progs.jeduca.jtest.Settings;
import net.sf.japi.swing.action.ActionBuilder;
import net.sf.japi.swing.action.ActionBuilderFactory;

/** User Interface for a Collection of Questions.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class QuestionCollectionGUI extends JComponent {

    /** Serial Version. */
    private static final long serialVersionUID = 1L;

    /** Constant for showing the collection info. */
    private static final String COLLECTION_INFO = "CollectionInfo";

    /** Constant for showing the question. */
    private static final String QUESTION = "Question";

    /** Constant for showing the solution. */
    private static final String SOLUTION = "Solution";

    /** Constant for showing the review. */
    private static final String REVIEW = "Review";

    /** Action Builder. */
    private static final ActionBuilder ACTION_BUILDER = ActionBuilderFactory.getInstance().getActionBuilder("net.sf.japi.progs.jeduca.jtest.gui");

    /** Index of the currently displayed question.
     * The value of index is <code>questionCol.getSize() $gt; index $gt;= 0</code> or <code>-1</code> if <code>questionCol == null</code> or the
     * questions have not started yet.
     */
    private transient int index = -1;

    /** ViewPanel.
     * @serial include
     */
    private JPanel viewPanel;

    /** ViewPanel CardLayout.
     * @serial include
     */
    private CardLayout viewLayout;

    /** QuestionCollection. */
    private transient QuestionCollection col;

    /** QuestionUI.
     * @serial include
     */
    private QuestionGUI questionGUI;

    /** QuestionCollectionInfoUI.
     * @serial include
     */
    private QuestionCollectionInfoGUI questionInfoGUI;

    /** SolutionGUI.
     * @serial include
     */
    private SolutionGUI solutionGUI;

    /** ReviewGUI.
     * @serial include
     */
    private ReviewGUI reviewGUI;

    /** Whether currently showing a solution. */
    private transient boolean showingSolution;

    /** The Settings.
     * @serial include
     */
    private final Settings settings;

    /** Create a GUI for a Collection of Questions.
     * @param settings Settings to use.
     */
    public QuestionCollectionGUI(final Settings settings) {
        this.settings = settings;
        initUI();
        updateButtonState();
    }

    /** Set the QuestionCollection to be displayed.
     * @param col QuestionCollection to be displayed
     */
    public void setQuestionCollection(final QuestionCollection col) {
        this.col = col;
        index = -1;
        questionInfoGUI.setQuestionCollection(col);
        updateButtonState();
        reviewGUI.setQuestionCollection(col);
        viewLayout.show(viewPanel, COLLECTION_INFO);
    }

    /** Initialize the User Interface. */
    private void initUI() {
        viewLayout         = new CardLayout();
        viewPanel          = new JPanel(viewLayout);
        setLayout(new BorderLayout());
        add(viewPanel,         CENTER);

        questionInfoGUI    = new QuestionCollectionInfoGUI();
        questionGUI        = new QuestionGUI();
        solutionGUI        = new SolutionGUI();
        reviewGUI          = new ReviewGUI(this);
        viewPanel.add(questionInfoGUI, COLLECTION_INFO);
        viewPanel.add(questionGUI,     QUESTION);
        viewPanel.add(solutionGUI,     SOLUTION);
        viewPanel.add(reviewGUI,       REVIEW);
        //tryListFrame();
    }

    //public void tryListFrame() {
    //    JFrame f = new JFrame();
    //    JList l = new JList(navigationActions);
    //    l.setCellRenderer(new ActionListCellRenderer());
    //    l.addListSelectionListener(new ListSelectionActionAdapter());
    //    f.add(new JScrollPane(l));
    //    f.pack();
    //    f.setVisible(true);
    //}

    /** Display a certain question.
     * Nothing strange will happen if the index is out of range.
     * If the index is negative, the first question will be displayed.
     * If the index is beyond range, the last question will be displayed.
     * @param index number of question to display.
     */
    public void displayQuestion(final int index) {
        this.index = index;
        updateQuestion();
    }

    /** Display a certain question.
     * @param question Question to display
     */
    public void displayQuestion(final QuestionText question) {
        index = col.indexOf(question);
        updateQuestion();
    }

    /** Display the start question. */
    public void start() {
        first();
    }

    /** Display the first question. */
    public void first() {
        index = 0;
        updateQuestion();
    }

    /** Display the previous question. */
    public void prev() {
        if (!showingSolution) {
            index--;
        }
        updateQuestion();
    }

    /** Display the next question. */
    public void next() {
        if (!showingSolution && settings.isShowQuestionSolution()) {
            solutionGUI.setQuestion(col.getQuestion(index));
            viewLayout.show(viewPanel, SOLUTION);
            showingSolution = true;
            updateButtonState();
        } else {
            index++;
            updateQuestion();
        }
    }

    /** Display the last question. */
    public void last() {
        index = col.getSize() - 1;
        updateQuestion();
    }

    /** Display a review. */
    public void review() {
        reviewGUI.updateStates();
        viewLayout.show(viewPanel, "Review");
    }

    /** Display the solution for all questoins. */
    public void end() {
        solutionGUI.setQuestionCollection(col);
        viewLayout.show(viewPanel, SOLUTION);
        index = -1;
        showingSolution = true;
        updateButtonState();
    }

    /** Update the currently displayed question.
     * Covers common code of {@link #next()} and {@link #prev()}.
     * Checks the index for being within its desired bounds and corrects it if neccessary.
     */
    private void updateQuestion() {
        if (index >= col.getSize()) {
            index = col.getSize() - 1;
        }
        if (index < 0) {
            index = 0;
        }
        if (col == null) {
            index = -1;
        }
        if (index != -1 && col.getSize() != 0) {
            questionGUI.setQuestion(col.getQuestion(index));
        }
        showingSolution = false;
        viewLayout.show(viewPanel, QUESTION);
        updateButtonState();
    }

    /** Update the state of the actions. */
    public void updateButtonState() {
        if (col == null) {
            for (final Action action : navigationActions) {
                action.setEnabled(false);
            }
        } else {
            first.setEnabled(index > 0);
            prev.setEnabled(index > 0 || showingSolution && index != -1);
            start.setEnabled(index == -1);
            next.setEnabled(index >= 0 && index < col.getSize() - 1);
            last.setEnabled(index >= 0 && index < col.getSize() - 1);
            review.setEnabled(index != -1);
            end.setEnabled(index != -1);
        }
    }

    /** Action for start test.
     * @serial include
     */
    private final Action start  = ACTION_BUILDER.createAction(true, "start",  this);

    /** Action for first question.
     * @serial include
     */
    private final Action first  = ACTION_BUILDER.createAction(true, "first",  this);

    /** Action for previous question.
     * @serial include
     */
    private final Action prev   = ACTION_BUILDER.createAction(true, "prev",   this);

    /** Action for next question.
     * @serial include
     */
    private final Action next   = ACTION_BUILDER.createAction(true, "next",   this);

    /** Action for last question.
     * @serial include
     */
    private final Action last   = ACTION_BUILDER.createAction(true, "last",   this);

    /** Action for review.
     * @serial include
     */
    private final Action review = ACTION_BUILDER.createAction(true, "review", this);

    /** Action for end.
     * @serial include
     */
    private final Action end    = ACTION_BUILDER.createAction(true, "end",    this);

    /** All Actions related to navigation.
     * @serial include
     */
    private final Action[] navigationActions = { start, first, prev, next, last, review, end };

    /** Create Navigation Menu.
     * @return Navigation Menu
     */
    public JMenu createNavigationMenu() {
        final JMenu menu = new JMenu(ACTION_BUILDER.getString("nav.text"));
        menu.setName(ACTION_BUILDER.getString("nav.text"));
        for (final Action action : navigationActions) {
            menu.add(new JMenuItem(action));
        }
        menu.setMnemonic(VK_N);
        return menu;
    }

    /** Create Navigation Toolbar.
     * @return Navigation Toolbar
     */
    public JToolBar createNavigationToolBar() {
        return ACTION_BUILDER.createToolBar(this, "navigationToolBar");
    }

    /** Get the index of the question currently being displayed.
     * @return index
     */
    public int getIndex() {
        return index;
    }

} // class QuestionCollectionGUI
