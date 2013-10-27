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

import java.awt.LayoutManager;
import java.net.URL;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import net.sf.japi.swing.RowLayout;

/** Abstract preferences implementation.
 * Subclass this.
 * Build the panel in your constructor.
 * The default layout of an AbstractPrefs is {@link BoxLayout} with {@link BoxLayout#Y_AXIS}.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public abstract class AbstractPrefs extends JPanel implements Prefs {

    /** The icon to be displayed in the list where the user can choose amongst preferences.
     * @see #getListLabelIcon()
     * @serial include
     */
    private Icon listLabelIcon;

    /** The label text to be displayed in the list where the user can choose amongst preferences.
     * @see #getListLabelText()
     * @serial include
     */
    private String listLabelText;

    /** The title text to be displayed as title for this prefs module.
     * @see #getLabelText()
     * @serial include
     */
    private String labelText;

    /** The Help URL.
     * @serial include
     */
    private URL helpURL;

    /** The Help text (HTML).
     * @serial include
     */
    private String helpText;

    /** Constructor. */
    protected AbstractPrefs() {
        super(new RowLayout());
        // setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    /** Constructor that allows setting the initial layout.
     * @param layout Layout
     */
    protected AbstractPrefs(final LayoutManager layout) {
        super(layout);
    }

    /** {@inheritDoc} */
    public final JComponent getEditComponent() {
        return this;
    }

    /** {@inheritDoc} */
    public final Icon getListLabelIcon() {
        return listLabelIcon;
    }

    /** Set the icon that is to be displayed in the list where the user can choose amongst preferences.
     * @param listLabelIcon icon
     */
    protected final void setListLabelIcon(final Icon listLabelIcon) {
        this.listLabelIcon = listLabelIcon;
    }

    /** {@inheritDoc} */
    public final String getListLabelText() {
        return listLabelText;
    }

    /** Set the label text that is to be displayed in the list where the user can choose amongst preferences.
     * @param listLabelText text
     */
    protected final void setListLabelText(final String listLabelText) {
        this.listLabelText = listLabelText;
    }

    /** {@inheritDoc} */
    public final String getLabelText() {
        return labelText;
    }

    /** Set the title text that is to be displayed as title for this prefs module.
     * @param labelText text
     */
    protected final void setLabelText(final String labelText) {
        this.labelText = labelText;
    }

    /** {@inheritDoc} */
    public final URL getHelpURL() {
        return helpURL;
    }

    /** Set the help URL.
     * @param helpURL Help URL
     */
    protected final void setHelpURL(final URL helpURL) {
        this.helpURL = helpURL;
    }

    /** {@inheritDoc} */
    public final String getHelpText() {
        return helpText;
    }

    /** Set the help text.
     * @param helpText Help text
     */
    protected final void setHelpText(final String helpText) {
        this.helpText = helpText;
    }

} // class AbstractPrefs
