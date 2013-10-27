/*
 * Copyright (C) 2009  Christian Hujer.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package net.sf.japi.swing.prefs;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Action;
import static javax.swing.BorderFactory.createEmptyBorder;
import javax.swing.Box;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.sf.japi.swing.action.ActionBuilder;
import net.sf.japi.swing.action.ActionBuilderFactory;
import net.sf.japi.swing.action.ActionMethod;

/** Panel to display preferences.
 * @serial exclude This class is not intended to be serialized.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
@SuppressWarnings({"FieldAccessedSynchronizedAndUnsynchronized"})
public final class PreferencesPane extends JOptionPane implements ListSelectionListener {

    /** Action Builder. */
    private static final ActionBuilder ACTION_BUILDER = ActionBuilderFactory.getInstance().getActionBuilder("net.sf.japi.swing.prefs");

    /** Width of the horizontal struts between button groups. */
    private static final int HORIZONTAL_STRUTS_WIDTH = 50;

    /** A map for DIALOGS that are already displaying.
     * This map is used to prevent the dialog for the same PreferencesGroup be shown twice within the same application.
     */
    private static final Map<PreferencesGroup, JDialog> DIALOGS = new HashMap<PreferencesGroup, JDialog>();

    /** The group of preferences to display. */
    private final PreferencesGroup prefs;

    /** The currently selected preferences module. */
    private Prefs currentPref;

    /** Action for help. */
    private final Action helpAction     = ACTION_BUILDER.createAction(false, "help"    , this);

    /** Action for defaults. */
    private final Action defaultsAction = ACTION_BUILDER.createAction(false, "defaults", this);

    /** Action for ok. */
    private final Action okAction       = ACTION_BUILDER.createAction(false, "ok"      , this);

    /** Action for apply. */
    private final Action applyAction    = ACTION_BUILDER.createAction(false, "apply"   , this);

    /** Action for revert. */
    private final Action revertAction   = ACTION_BUILDER.createAction(false, "revert"  , this);

    /** Action for cancel. */
    private final Action cancelAction   = ACTION_BUILDER.createAction(false, "cancel"  , this);

    /** CardLayout for switching between prefs modules.
     * @see #cardPanel
     */
    private final CardLayout cards = new CardLayout();

    /** Panel where the CardLayout for switching between prefs modules is used.
     * @see #cards
     */
    private final JPanel cardPanel = new JPanel(cards);

    /** The JList that displays the list of prefs modules. */
    private final JList prefsList;

    /** Show Preferences.
     * @param parentComponent determines the Frame in which the dialog is displayed; if <code>null</code>, or if the <code>parentComponent</code> has
     * no <code>Frame</code>, a default <code>Frame</code> is used
     * @param prefs PreferencesGroup to be displayed
     * @param modal <code>true</code> if the displayed dialog should be modal, otherwise <code>false</code>
     */
    public static void showPreferencesDialog(final Component parentComponent, final PreferencesGroup prefs, final boolean modal) {
        synchronized (DIALOGS) {
            if (DIALOGS.containsKey(prefs)) {
                DIALOGS.get(prefs).toFront();
            } else {
                final PreferencesPane pane = new PreferencesPane(prefs);
                final JDialog dialog = pane.createDialog(parentComponent, prefs.getTitle());
                DIALOGS.put(prefs, dialog);
                dialog.setResizable(true);
                dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                dialog.addWindowListener(new WindowAdapter() {
                    /** {@inheritDoc} */
                    @Override
                    public void windowClosing(final WindowEvent e) {
                        pane.cancel();
                    }
                });
                dialog.setModal(modal);
                dialog.setVisible(true);
            }
        }
    }

    /** Create a PreferencesPane.
     * @param prefs PreferencesGroup to create panel for
     */
    public PreferencesPane(final PreferencesGroup prefs) {
        this.prefs = prefs;
        prefsList = new JList(prefs);
        setMessage(createMessage());
        setOptions(createOptions());
    }

    /** Create the Message.
     * @return subpanel
     */
    private JComponent createMessage() {
        final JPanel panel = new JPanel(new BorderLayout());
        panel.add(createList(), BorderLayout.WEST);
        panel.add(createPanel(), BorderLayout.CENTER);
        return panel;
    }

    /** Create the list.
     * @return list
     */
    private JComponent createList() {
        prefsList.setCellRenderer(new PrefsListCellRenderer());
        prefsList.setSelectedIndex(0);
        prefsList.addListSelectionListener(this);
        return new JScrollPane(prefsList);
    }

    /** Create the Panel.
     * @return panel
     */
    private JComponent createPanel() {
        int index = 0;
        for (final Prefs pref : prefs) {
            cardPanel.add(Integer.toString(index++), pref.getEditComponent());
        }
        currentPref = prefs.getElementAt(0);
        return cardPanel;
    }

    /** Create the Options.
     * @return options
     */
    private Object[] createOptions() {
        return new Object[] {
            new JButton(helpAction),
            new JButton(defaultsAction),
            Box.createHorizontalStrut(HORIZONTAL_STRUTS_WIDTH),
            new JButton(okAction),
            new JButton(applyAction),
            Box.createHorizontalStrut(HORIZONTAL_STRUTS_WIDTH),
            new JButton(revertAction),
            new JButton(cancelAction),
        };
    }

    /** {@inheritDoc} */
    public void valueChanged(final ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            return;
        }
        final int index = ((JList) e.getSource()).getSelectedIndex();
        final Prefs newPref = prefs.getElementAt(index);
        //noinspection ObjectEquality
        if (currentPref == newPref) {
            return;
        }
        if (currentPref.isChanged()) {
            final int result = ACTION_BUILDER.showConfirmDialog(this, YES_NO_CANCEL_OPTION, QUESTION_MESSAGE, "prefsChanged", currentPref.getListLabelText());
            switch (result) {
                case CLOSED_OPTION:
                case CANCEL_OPTION:
                    ((JList) e.getSource()).setSelectedValue(currentPref, true);
                    return;
                case YES_OPTION:
                    currentPref.apply();
                    break;
                case NO_OPTION:
                    currentPref.revert();
                    break;
                default:
            }
        }
        currentPref = newPref;
        cards.show(cardPanel, Integer.toString(index));
    }

    /** Action method for cancel. */
    @ActionMethod
    public void cancel() {
        setValue(CANCEL_OPTION);
    }

    /** {@inheritDoc} */
    @Override public void setValue(final Object newValue) {
        super.setValue(newValue);
        //noinspection ObjectEquality
        if (newValue != null && newValue != UNINITIALIZED_VALUE) {
            synchronized (DIALOGS) {
                DIALOGS.remove(prefs).dispose();
            }
        }
    }

    /** Action method for defaults. */
    @ActionMethod
    public void defaults() {
        currentPref.defaults();
    }

    /** Action method for help. */
    @ActionMethod
    public void help() {
        // TODO:2009-02-15:christianhujer:Implement help feature.
    }

    /** Action method for ok. */
    @SuppressWarnings({"InstanceMethodNamingConvention"})
    @ActionMethod
    public void ok() {
        apply();
        setValue(OK_OPTION);
    }

    /** Action method for apply. */
    @ActionMethod
    public void apply() {
        if (currentPref.isChanged()) {
            currentPref.apply();
        }
    }

    /** Action method for revert. */
    @ActionMethod
    public void revert() {
        if (currentPref.isChanged()) {
            currentPref.revert();
        }
    }

    /** Returns the JList for selecting the prefs modules.
     * @return The JList for selecting the prefs modules.
     */
    public JList getPrefsList() {
        return prefsList;
    }

    /** Class for rendering preferences list items. */
    private static final class PrefsListCellRenderer extends DefaultListCellRenderer {

        /** The border.
         * For some reason it gets lost when set in the initializer, so we store it and set it each time the renderer is used.
         */
        private final Border border = createEmptyBorder(10, 10, 10, 10);

        /** Create a PrefsListCellRenderer. */
        PrefsListCellRenderer() {
            setHorizontalTextPosition(CENTER);
            setVerticalTextPosition(BOTTOM);
            setHorizontalAlignment(CENTER);
            setVerticalAlignment(CENTER);
        }

        /** {@inheritDoc} */
        @SuppressWarnings({"ReturnOfThis"})
        @Override public Component getListCellRendererComponent(final JList list, final Object value, final int index, final boolean isSelected, final boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            setBorder(border);
            final Prefs pref = (Prefs) value;
            setText(pref.getListLabelText());
            setIcon(pref.getListLabelIcon());
            return this;
        }

    } // class PrefsListCellRenderer

} // class PreferencesPane
