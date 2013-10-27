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

package net.sf.japi.progs.jeduca.swing.list;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import static java.awt.Font.BOLD;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;
import org.jetbrains.annotations.Nullable;

/** ListCellRenderer for Actions.
 * It is used to display a JList that shows Actions as Icons with Text.
 * A possible use is an index view of different topics used in program settings (like KDE kcontrol or konqueror configuration).
 * Note: This class does not make your Actions activated (actionPerformed), it solely displays icons / text in a list.
 * To get your actions activated, you need to program JList.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class ActionListCellRenderer extends JButton implements ListCellRenderer {

    /** The factor to multiply the font with to derive a slightly larger font. */
    private static final double FONT_SCALE = 4.0 / 3.0;

    /** Serial Version. */
    @SuppressWarnings({"AnalyzingVariableNaming"})
    private static final long serialVersionUID = 1L;

    /** Create an ActionListCellRenderer. */
    public ActionListCellRenderer() {
        setOpaque(true);
        setHorizontalTextPosition(CENTER);
        setVerticalTextPosition(BOTTOM);
        setVerticalAlignment(CENTER);
        setHorizontalAlignment(CENTER);
        setBorder(new EmptyBorder(3, 3, 3, 3));
        final Font f = getFont();
        setFont(f.deriveFont(BOLD, (float) (f.getSize2D() * FONT_SCALE)));
    }

    /** {@inheritDoc} */
    @Nullable public Component getListCellRendererComponent(final JList list, final Object value, final int index, final boolean isSelected, final boolean cellHasFocus) {
        if (value instanceof Action) {
            setAction((Action) value);
            setIcon((Icon) ((Action) value).getValue("MEDIUM_ICON"));
            setBackground(isSelected ? Color.cyan : Color.white);
            return this;
        } else {
            return null; // ? or perhaps throw new IllegalArgumentException();
        }
    }

} // class ActionListCellRenderer
