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
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import net.sf.japi.swing.action.ActionBuilder;
import net.sf.japi.swing.action.ActionBuilderFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** ListCellRenderer for font styles.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 * @todo improve performance
 */
public class FontStyleListCellRenderer extends DefaultListCellRenderer {

    /** Serial Version. */
    private static final long serialVersionUID = 1L;

    /** Action Builder. */
    private static final ActionBuilder ACTION_BUILDER = ActionBuilderFactory.getInstance().getActionBuilder("net.sf.japi.swing.font");

    /** {@inheritDoc} */
    @Override public Component getListCellRendererComponent(@NotNull final JList list, @Nullable final Object value, final int index, final boolean isSelected, final boolean cellHasFocus) {
        final Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        final int style = value != null ? (Integer) value : 0;
        setText(getTextFor(style));
        setFont(getFontFor(style));
        return c;
    }

    /** Get the text representation of a style.
     * @param style style to get text representation for
     * @return text representation of style
     * @todo store values
     */
    private static String getTextFor(final int style) {
        switch (style) {
            case PLAIN:         return ACTION_BUILDER.getString("font.style.plain");
            case BOLD:          return ACTION_BUILDER.getString("font.style.bold");
            case ITALIC:        return ACTION_BUILDER.getString("font.style.italic");
            case BOLD | ITALIC: return ACTION_BUILDER.getString("font.style.bolditalic");
            default:            return ACTION_BUILDER.getString("font.style.unknown");
        }
    }

    /** Get the font representation of a style.
     * @param style style to get font representation for
     * @return font representation of style
     */
    private Font getFontFor(final int style) {
        switch (style) {
            case PLAIN:         return getFont().deriveFont(PLAIN);
            case BOLD:          return getFont().deriveFont(BOLD);
            case ITALIC:        return getFont().deriveFont(ITALIC);
            case BOLD | ITALIC: return getFont().deriveFont(BOLD | ITALIC);
            default:            return getFont().deriveFont(PLAIN);
        }
    }

} // class FontStyleListCellRenderer
