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

package net.sf.japi.progs.jeduca.swing.text;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

/** ComboBox for choosing or entering a MimeType.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class MimeComboBox extends JComboBox {

    /** Serial Version. */
    @SuppressWarnings({"AnalyzingVariableNaming"})
    private static final long serialVersionUID = 1L;

    /** Default types. */
    private static final String[] DEFAULT_TYPES = { "text/plain", "text/html" };

    /** MimeComboBox constructor. */
    public MimeComboBox() {
        super(new DefaultComboBoxModel(DEFAULT_TYPES));
    }

    /** Get the selected type.
     * @return selected type
     */
    public String getType() {
        return (String) getSelectedItem();
    }

} // class MimeComboBox
