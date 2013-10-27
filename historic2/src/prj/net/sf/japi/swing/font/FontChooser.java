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

package net.sf.japi.swing.font;

import java.awt.Component;
import java.awt.Font;
import static java.awt.Font.BOLD;
import static java.awt.Font.ITALIC;
import static java.awt.Font.PLAIN;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import static javax.swing.BorderFactory.createCompoundBorder;
import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.BorderFactory.createTitledBorder;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import static javax.swing.JOptionPane.OK_CANCEL_OPTION;
import static javax.swing.JOptionPane.OK_OPTION;
import static javax.swing.JOptionPane.PLAIN_MESSAGE;
import static javax.swing.JOptionPane.showConfirmDialog;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import static javax.swing.ListSelectionModel.SINGLE_SELECTION;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.sf.japi.swing.action.ActionBuilder;
import net.sf.japi.swing.action.ActionBuilderFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** Class for letting the user choose a font.
 * There are two possibilities to use this class:
 * <ul>
 *  <li>You can use an instance of FontChooser as a Pane and add it to the desired Container.</li>
 *  <li>You can use this class' static methods to display a Dialog which lets the user choose a font.</li>
 * </ul>
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class FontChooser extends JComponent implements ListSelectionListener, ChangeListener {

    /** Serial Version. */
    private static final long serialVersionUID = 1L;

    /** Action Builder. */
    private static final ActionBuilder ACTION_BUILDER = ActionBuilderFactory.getInstance().getActionBuilder("net.sf.japi.swing.font");

    /** JList for Font Family.
     * @serial include
     */
    @NotNull private final JList familyList;

    /** JList for Font Style.
     * @serial include
     */
    @NotNull private final JList styleList;

    /** JList for Font Size.
     * @serial include
     */
    @NotNull private final JList sizeList;

    /** JSpinner for Font Size.
     * @serial include
     */
    @NotNull private final JSpinner sizeSpinner;

    /** FontPreview for Font.
     * @serial include
     */
    @NotNull private final FontPreview preview;

    /** Selected Font.
     * @serial include
     */
    @NotNull private Font selectedFont = Font.decode(null);

    /** Create a new FontChooser. */
    @SuppressWarnings({"MagicNumber"})
    public FontChooser() {
        setBorder(createCompoundBorder(createCompoundBorder(createEmptyBorder(8, 8, 8, 8), createTitledBorder(ACTION_BUILDER.getString("desiredFont_borderTitle"))), createEmptyBorder(8, 4, 4, 4)));
        setLayout(new GridBagLayout());
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);
        final JLabel familyLabel = ACTION_BUILDER.createLabel("family.label");
        final JLabel styleLabel = ACTION_BUILDER.createLabel("style.label");
        final JLabel sizeLabel = ACTION_BUILDER.createLabel("size.label");
        familyList = new JList(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
        styleList = new JList(new Integer[] { PLAIN, ITALIC, BOLD, BOLD | ITALIC });
        styleList.setCellRenderer(new FontStyleListCellRenderer());
        sizeList = new JList(new Integer[] { 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 22, 24, 26, 28, 32, 48, 64 });
        preview = new FontPreview();
        sizeSpinner = new JSpinner(new SpinnerNumberModel(12, 4, 100, 1));
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(familyLabel, gbc);
        add(styleLabel, gbc);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(sizeLabel, gbc);
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        gbc.weighty = 1.0;
        add(new JScrollPane(familyList), gbc);
        add(new JScrollPane(styleList), gbc);
        gbc.gridheight = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weighty = 0.0;
        add(sizeSpinner, gbc);
        gbc.weighty = 1.0;
        add(new JScrollPane(sizeList), gbc);
        gbc.gridwidth = 3;
        add(preview, gbc);
        familyList.addListSelectionListener(this);
        styleList.addListSelectionListener(this);
        sizeList.addListSelectionListener(this);
        sizeSpinner.addChangeListener(this);
        familyList.setSelectionMode(SINGLE_SELECTION);
        styleList.setSelectionMode(SINGLE_SELECTION);
        sizeList.setSelectionMode(SINGLE_SELECTION);
    }

    /** Set the selected font.
     * @param selectedFont currently selected font.
     */
    public void setSelectedFont(@Nullable final Font selectedFont) {
        @NotNull final Font realFont = selectedFont != null ? selectedFont : Font.decode(null);
        this.selectedFont = realFont;
        preview.setFont(realFont);
        //lock = true;
        sizeSpinner.setValue(realFont.getSize());
        sizeList.setSelectedValue(realFont.getSize(), true);
        styleList.setSelectedValue(realFont.getStyle(), true);
        familyList.setSelectedValue(realFont.getFamily(), true);
        //lock = false;
    }

    /** Set the selected family. */
    private void updateFont() {
        //if (lock) { return; }
        final String family = familyList.getSelectedValue() == null ? selectedFont.getFamily() : (String) familyList.getSelectedValue();
        final int style = styleList.getSelectedValue() == null ? selectedFont.getStyle() : (Integer) styleList.getSelectedValue();
        final int size = sizeList.getSelectedValue() == null ? selectedFont.getSize() : (Integer) sizeSpinner.getValue();
        selectedFont = new Font(family, style, size);
        preview.setFont(selectedFont);
    }

    /** {@inheritDoc} */
    public void valueChanged(@NotNull final ListSelectionEvent e) {
        final Object source = e.getSource();
        if     (source == familyList) { // NOPMD
            // No special action except updateFont()
        } else if (source == styleList) { // NOPMD
            // No special action except updateFont()
        } else if (source == sizeList) {
            final Object size = sizeList.getSelectedValue();
            if (!sizeSpinner.getValue().equals(size) && size != null) {
                sizeSpinner.setValue(size);
            }
        } else {
            assert false;
        }
        updateFont();
    }

    /** {@inheritDoc} */
    public void stateChanged(@NotNull final ChangeEvent e) {
        final Object source = e.getSource();
        if (source == sizeSpinner) {
            final Object size = sizeSpinner.getValue();
            if (!size.equals(sizeList.getSelectedValue())) {
                sizeList.setSelectedValue(size, true);
            }
        } else {
            assert false;
        }
        updateFont();
    }

    /** Show a dialog.
     * @param parent Parent component
     * @return seleced font or null
     */
    public static Font showChooseFontDialog(@Nullable final Component parent) {
        return showChooseFontDialog(parent, Font.decode(null));
    }

    /** Show a dialog.
     * @param parent Parent compnent
     * @param font Font to modify
     * @return selected font or null
     */
    @Nullable public static Font showChooseFontDialog(@Nullable final Component parent, @Nullable final Font font) {
        final FontChooser chooser = new FontChooser();
        chooser.setSelectedFont(font);
        return showConfirmDialog(parent, chooser, ACTION_BUILDER.getString("chooser.title"), OK_CANCEL_OPTION, PLAIN_MESSAGE) == OK_OPTION ? chooser.selectedFont : null;
    }

} // class FontChooser
