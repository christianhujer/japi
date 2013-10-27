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

import java.awt.Font;
import static java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComboBox;

/** ComboBox to choose the font family.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class FontFamilyComboBox extends JComboBox {

    /** Serial Version. */
    private static final long serialVersionUID = 1L;

    /** The fonts to render.
     * @serial include
     */
    private Map<String, Font> fonts;

    /** Create a FontFamilyComboBox. */
    public FontFamilyComboBox() {
        super(getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
        // initFonts(); the super class construction invokes setFont and thus initFonts();
    }

    /** {@inheritDoc} */
    @Override public void setFont(final Font font) {
        super.setFont(font);
        initFonts();
    }

    /** Initialize fonts. */
    private void initFonts() {
        if (fonts == null) {
            final FontFamilyListCellRenderer cellRenderer = new FontFamilyListCellRenderer();
            setRenderer(cellRenderer);
            fonts = new HashMap<String, Font>();
            cellRenderer.setFonts(fonts);
        }
        Font base = getFont();
        if (base == null) { base = Font.decode(null); }
        final int style = base.getStyle();
        final int size = base.getSize();
        for (final String family : getLocalGraphicsEnvironment().getAvailableFontFamilyNames()) {
            fonts.put(family, new Font(family, style, size));
        }
    }

} // class FontFamilyComboBox
