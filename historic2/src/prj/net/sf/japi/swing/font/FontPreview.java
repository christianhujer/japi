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

import java.awt.Dimension;
import javax.swing.JTextField;
import net.sf.japi.swing.action.ActionBuilder;
import net.sf.japi.swing.action.ActionBuilderFactory;

/** Font Preview.
 * Uses a localized text to display the font, but the user may edit the text to try out the characters she's interested in.
 * This class is derived from JTextField, but never ever depend on that inheritance.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class FontPreview extends JTextField {

    /** Serial Version. */
    private static final long serialVersionUID = 1L;

    /** Action Builder. */
    private static final ActionBuilder ACTION_BUILDER = ActionBuilderFactory.getInstance().getActionBuilder("net.sf.japi.swing.font");

    /** Default height of the JTextField. */
    private static final int DEFAULT_HEIGHT = 64;

    /** Create a new FontPreview. */
    public FontPreview() {
        super(getDefaultText());
        setHorizontalAlignment(CENTER);
        final Dimension d = getMinimumSize();
        d.height = DEFAULT_HEIGHT;
        setMinimumSize(d);
        setPreferredSize(d);
    }

    /** Get the default text for previewing a font.
     * @return default text
     */
    public static String getDefaultText() {
        return ACTION_BUILDER.getString("font_preview_text");
    }

} // class FontPreview
