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

package net.sf.japi.swing;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** Layout similar to FlowLayout, but using columns (vertical layout) instead of rows (horizontal layout).
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
@SuppressWarnings({"NonPrivateFieldAccessedInSynchronizedContext", "FieldAccessedSynchronizedAndUnsynchronized"})
public class VerticalFlowLayout implements LayoutManager {

    /** Horizontal Gap. */
    private int hgap = 4;

    /** Vertical Gap. */
    private int vgap = 4;

    /** Create a VerticalFlowLayout with default gaps (4, 4). */
    public VerticalFlowLayout() {
    }

    /** Create a VerticalFlowLayout with defined gaps.
     * @param hgap horizontal gap
     * @param vgap vertical gap
     */
    public VerticalFlowLayout(final int hgap, final int vgap) {
        this.hgap = hgap;
        this.vgap = vgap;
    }

    /** Returns horizontal Gap.
     * @return horizontal Gap.
     */
    public int getHgap() {
        return hgap;
    }

    /** Sets horizontal Gap.
     * @param hgap horizontal Gap.
     */
    public void setHgap(final int hgap) {
        this.hgap = hgap;
    }

    /** Returns vertical Gap.
     * @return vertical Gap.
     */
    public int getVgap() {
        return vgap;
    }

    /** Sets vertical Gap.
     * @param vgap vertical Gap.
     */
    public void setVgap(final int vgap) {
        this.vgap = vgap;
    }

    /** {@inheritDoc} */
    public void addLayoutComponent(@Nullable final String name, @Nullable final Component comp) {
        // not needed
    }

    /** {@inheritDoc} */
    public void removeLayoutComponent(@Nullable final Component comp) {
        // not needed
    }

    /** {@inheritDoc} */
    @NotNull public Dimension preferredLayoutSize(@NotNull final Container parent) {
        synchronized (parent.getTreeLock()) {
            final Dimension preferredLayoutSize = new Dimension();
            final int nmembers = parent.getComponentCount();
            for (int i = 0; i < nmembers; i++) {
                final Component comp = parent.getComponent(i);
                if (comp.isVisible()) {
                    final Dimension dim = comp.getPreferredSize();
                    preferredLayoutSize.width = Math.max(preferredLayoutSize.width, dim.width);
                    if (i > 0) {
                        preferredLayoutSize.height += vgap;
                    }
                    preferredLayoutSize.height += dim.height;
                }
            }
            final Insets insets = parent.getInsets();
            preferredLayoutSize.width += insets.left + insets.right + (hgap << 1);
            preferredLayoutSize.height += insets.top + insets.bottom + (vgap << 1);
            return preferredLayoutSize;
        }
    }

    /** {@inheritDoc} */
    @NotNull public Dimension minimumLayoutSize(@NotNull final Container parent) {
        synchronized (parent.getTreeLock()) {
            final Dimension minimumLayoutSize = new Dimension();
            final int nmembers = parent.getComponentCount();
            for (int i = 0; i < nmembers; i++) {
                final Component comp = parent.getComponent(i);
                if (comp.isVisible()) {
                    final Dimension dim = comp.getMinimumSize();
                    minimumLayoutSize.width = Math.max(minimumLayoutSize.width, dim.width);
                    if (i > 0) {
                        minimumLayoutSize.height += vgap;
                    }
                    minimumLayoutSize.height += dim.height;
                }
            }
            final Insets insets = parent.getInsets();
            minimumLayoutSize.width += insets.left + insets.right + (hgap << 1);
            minimumLayoutSize.height += insets.top + insets.bottom + (vgap << 1);
            return minimumLayoutSize;
        }
    }

    /** {@inheritDoc} */
    public void layoutContainer(@NotNull final Container parent) {
        synchronized (parent.getTreeLock()) {
            final Insets insets = parent.getInsets();
            final int nmembers = parent.getComponentCount();
            final int maxheight = parent.getHeight() - (insets.left + insets.right + (vgap << 1));
            int maxwidth = 0;
            int y = 0;
            int x = insets.left + hgap;
            for (int i = 0; i < nmembers; i++) {
                final Component comp = parent.getComponent(i);
                if (comp.isVisible()) {
                    final Dimension dim = comp.getPreferredSize();
                    comp.setSize(dim);
                    if (y + vgap + comp.getHeight() > maxheight) {
                        y = 0;
                        x += maxwidth + hgap;
                        maxwidth = 0;
                    }
                    maxwidth = comp.getWidth() > maxwidth ? comp.getWidth() : maxwidth;
                    y += vgap;
                    comp.setLocation(x, y);
                    y += comp.getHeight();
                }
            }
        }
    }

} // class VerticalFlowLayout
