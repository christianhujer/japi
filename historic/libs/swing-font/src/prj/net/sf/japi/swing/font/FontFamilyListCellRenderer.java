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
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** List cell renderer for letting the user choose the font family.
 * This list cell renderer displays each font in its font.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class FontFamilyListCellRenderer extends DefaultListCellRenderer {

    /** Serial Version. */
    private static final long serialVersionUID = 1L;

    /** The fonts to render.
     * @serial include
     */
    @Nullable private Map<String, Font> fonts;

    /** Set the fonts to render.
     * The key of the map is the family name, the value of the map is the font to render.
     * @param fonts fonts to render
     */
    public void setFonts(@NotNull final Map<String, Font> fonts) {
        this.fonts = new HashMap<String, Font>(fonts);
    }

    /** {@inheritDoc} */
    @Override public Component getListCellRendererComponent(@NotNull final JList list, @Nullable final Object value, final int index, final boolean isSelected, final boolean cellHasFocus) {
        final Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        final String fontName = (String) value;
        //Font f = FontFamilyComboBox.this.getFont();
        //c.setFont(new Font((String)value, f.getStyle(), f.getSize()));
        c.setFont(fonts.get(fontName));
        return c;
    }

} // class FontFamilyCellRenderer
