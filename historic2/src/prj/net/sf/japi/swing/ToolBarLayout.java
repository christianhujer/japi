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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.io.Serializable;
import static java.lang.Math.max;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JToolBar;
import javax.swing.plaf.basic.BasicToolBarUI;
import org.jetbrains.annotations.Nullable;

/** A LayoutManager that manages a layout of a {@link Container} similar to {@link BorderLayout} but with an important difference, it is possible to
 * add as many components to a side layout region as you want. The desired purpose is to serve as LayoutManager for containers that shall contain
 * toolbars. So this is a LayoutManager you always were looking for.
 * <p />
 * Technically, this class is not a 100% replacement for {@link BorderLayout}. {@link JToolBar}'s UI ({@link
 * BasicToolBarUI}) directly looks for some features of the class {@link BorderLayout}, and if it is not {@link BorderLayout},
 * it uses some defaults.
 * This class has been developed to make these defaults work as good as possible.
 * Though this class doesn't technically replace {@link BorderLayout} - neither is this class a subclass of {@link BorderLayout} nor does it provide
 * <em>all</em> methods {@link BorderLayout} does - it still does practically.
 * <p />
 * The constant values {@link #NORTH}, {@link #SOUTH}, {@link #EAST}, {@link #WEST} and {@link #CENTER} are references to those of {@link
 * BorderLayout}.
 * The behaviour of {@link #CENTER} is that of {@link BorderLayout}: only one component can be added, subsequently added components overried all
 * previously added.
 * <p />
 * The behaviour of {@link #NORTH}, {@link #SOUTH}, {@link #EAST} and {@link #WEST} differs from {@link BorderLayout}. The position and layout
 * behaviour is the same, with the slight difference that this LayoutManager is able to manage more than on single component in these four regions.
 * Subsequently added components are placed from the outer to the inner. The first added component is the outmost component of that region, the last
 * added component is the innermost component of that region. To place a component to the innermost level, simply add it to the same region again.
 * <p />
 * Placing a component another level than the innermost of its destination region is currently not supported but might well be supported in future.
 * <p />
 * There are four possible ways of specifying a constraint:
 * <ul>
 *  <li>Use the geographical region constants from {@link BorderLayout}</li>
 *  <li>Use the geographical region constraints from this class</li>
 *  <li>Use an instance of {@link ToolBarConstraints}</li>
 *  <li>Use an enum region constraint from {@link ToolBarConstraints.Region}</li>
 * </ul>
 * . The constraint may be one of the String constants from this class or {@link BorderLayout} or it may
 * be a {@link ToolBarConstraints}.
 * @author <a href="mailto:chris@riedquat.de">Christian Hujer</a>
 * @since 0.1
 * @see BorderLayout
 * @see JToolBar
 * @todo support rtl containers the same way as {@link BorderLayout} does it.
 * @todo test {@link ToolBarConstraints} and {@link ToolBarConstraints.Region}
 */
@SuppressWarnings({"NonPrivateFieldAccessedInSynchronizedContext", "FieldAccessedSynchronizedAndUnsynchronized"})
public class ToolBarLayout extends BorderLayout {

    /** Serial Version. */
    @SuppressWarnings({"AnalyzingVariableNaming"})
    private static final long serialVersionUID = 1L;

    /** Horizontal Gap.
     * @serial include
     */
    private int hgap;

    /** Vertical Gap.
     * @serial include
     */
    private int vgap;

    /** Components in the north region.
     * @serial include
     */
    private final List<Component> north = new ArrayList<Component>();

    /** Components in the south region.
     * @serial include
     */
    private final List<Component> south = new ArrayList<Component>();

    /** Components in the east region.
     * @serial include
     */
    private final List<Component> east = new ArrayList<Component>();

    /** Components in the west region.
     * @serial include
     */
    private final List<Component> west = new ArrayList<Component>();

    /** Component in the center region.
     * @serial include
     */
    @Nullable private Component center;

    /** Create a ToolBarLayout with zero gaps. */
    public ToolBarLayout() {
        this(0, 0);
    }

    /** Create a TooLBarLayout.
     * @param hgap horizontal gap between components
     * @param vgap vertical gap between components
     */
    public ToolBarLayout(final int hgap, final int vgap) {
        this.hgap = hgap;
        this.vgap = vgap;
    }

    private void addLayoutComponent(final ToolBarConstraints.Region region, final Component comp) {
        synchronized (comp.getTreeLock()) {
            List<Component> list = null;
            switch (region) {
                case NORTH: list = north; break;
                case SOUTH: list = south; break;
                case EAST:  list = east;  break;
                case WEST:  list = west;  break;
                case CENTER: center = comp; return;
                default: assert false;
            }
            assert list != null;
            list.add(comp);
        }
    }

    private void addLayoutComponent(final ToolBarConstraints constraints, final Component comp) {
        synchronized (comp.getTreeLock()) {
            List<Component> list = null;
            switch (constraints.region) {
                case NORTH: list = north; break;
                case SOUTH: list = south; break;
                case EAST:  list = east;  break;
                case WEST:  list = west;  break;
                case CENTER: center = comp; return;
                default: assert false;
            }
            int pos = constraints.position;
            assert list != null;
            if (pos > list.size() || pos < 0) {
                pos = list.size();
            }
            list.add(pos, comp);
        }
    }

    /** {@inheritDoc} */
    @SuppressWarnings({"deprecation"})
    @Override public void addLayoutComponent(final String name, final Component comp) {
        synchronized (comp.getTreeLock()) {
            if (name == null || CENTER.equals(name)) {
                center = comp;
            } else if (NORTH.equals(name)) {
                north.add(comp);
            } else if (SOUTH.equals(name)) {
                south.add(comp);
            } else if (EAST.equals(name)) {
                east.add(comp);
            } else if (WEST.equals(name)) {
                west.add(comp);
            } else {
                throw new IllegalArgumentException("cannot add to layout: unknown constraint: " + name);
            }
        }
    }

    /** {@inheritDoc} */
    @Override public void addLayoutComponent(final Component comp, final Object constraints) {
        synchronized (comp.getTreeLock()) {
            if (constraints == null || constraints instanceof String) {
                addLayoutComponent((String) constraints, comp);
            } else if (constraints instanceof ToolBarConstraints) {
                addLayoutComponent((ToolBarConstraints) constraints, comp);
            } else if (constraints instanceof ToolBarConstraints.Region) {
                addLayoutComponent((ToolBarConstraints.Region) constraints, comp);
            } else {
                throw new IllegalArgumentException("cannot add to layout: constraint must be a string (or null)");
            }
        }
    }

    /** {@inheritDoc} */
    @Override public float getLayoutAlignmentX(final Container parent) {
        //noinspection MagicNumber
        return 0.5f;
    }

    /** {@inheritDoc} */
    @Override public float getLayoutAlignmentY(final Container parent) {
        //noinspection MagicNumber
        return 0.5f;
    }

    /** {@inheritDoc} */
    @Override public void invalidateLayout(final Container target) {
        /* Do Nothing, like BorderLayout. */
    }

    /** {@inheritDoc} */
    @Override public void layoutContainer(final Container target) {
        synchronized (target.getTreeLock()) {
            final Insets insets = target.getInsets();
            Dimension dim;
            int top = insets.top;
            int bottom = target.getHeight() - insets.bottom;
            int left = insets.left;
            int right = target.getWidth() - insets.right;
            for (final Component comp : north) {
                if (comp.isVisible()) {
                    comp.setSize(right - left, comp.getHeight());
                    dim = comp.getPreferredSize();
                    comp.setBounds(left, top, right - left, dim.height);
                    top += dim.height + vgap;
                }
            }
            for (final Component comp : south) {
                if (comp.isVisible()) {
                    comp.setSize(right - left, comp.getHeight());
                    dim = comp.getPreferredSize();
                    comp.setBounds(left, bottom - dim.height, right - left, dim.height);
                    bottom -= dim.height + vgap;
                }
            }
            for (final Component comp : east) {
                if (comp.isVisible()) {
                    comp.setSize(comp.getWidth(), bottom - top);
                    dim = comp.getPreferredSize();
                    comp.setBounds(right - dim.width, top, dim.width, bottom - top);
                    right -= dim.width + hgap;
                }
            }
            for (final Component comp : west) {
                if (comp.isVisible()) {
                    comp.setSize(comp.getWidth(), bottom - top);
                    dim = comp.getPreferredSize();
                    comp.setBounds(left, top, dim.width, bottom - top);
                    left += dim.width + hgap;
                }
            }
            if (center != null) {
                center.setBounds(left, top, right - left, bottom - top);
            }
        }
    }

    /** {@inheritDoc} */
    @Override public Dimension maximumLayoutSize(final Container target) {
        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    /** {@inheritDoc} */
    @Override public Dimension minimumLayoutSize(final Container target) {
        synchronized (target.getTreeLock()) {
            Dimension dim;
            final Dimension minimumLayoutSize = new Dimension(0, 0);
            for (final Component comp : east) {
                dim = comp.getMinimumSize();
                minimumLayoutSize.width += dim.width + hgap;
                minimumLayoutSize.height = max(dim.height, minimumLayoutSize.height);
            }
            for (final Component comp : west) {
                dim = comp.getMinimumSize();
                minimumLayoutSize.width += dim.width + hgap;
                minimumLayoutSize.height = max(dim.height, minimumLayoutSize.height);
            }
            if (center != null) {
                dim = center.getMinimumSize();
                minimumLayoutSize.width += dim.width;
                minimumLayoutSize.height = max(dim.height, minimumLayoutSize.height);
            }
            for (final Component comp : north) {
                dim = comp.getMinimumSize();
                minimumLayoutSize.width = max(dim.width, minimumLayoutSize.width);
                minimumLayoutSize.height += dim.height + vgap;
            }
            for (final Component comp : south) {
                dim = comp.getMinimumSize();
                minimumLayoutSize.width = max(dim.width, minimumLayoutSize.width);
                minimumLayoutSize.height += dim.height + vgap;
            }
            final Insets insets = target.getInsets();
            minimumLayoutSize.width += insets.left + insets.right;
            minimumLayoutSize.height += insets.top + insets.bottom;
            return minimumLayoutSize;
        }
    }

    /** {@inheritDoc} */
    @Override public Dimension preferredLayoutSize(final Container target) {
        synchronized (target.getTreeLock()) {
            Dimension dim;
            final Dimension preferredLayoutSize = new Dimension(0, 0);
            for (final Component comp : east) {
                dim = comp.getPreferredSize();
                preferredLayoutSize.width += dim.width + hgap;
                preferredLayoutSize.height = max(dim.height, preferredLayoutSize.height);
            }
            for (final Component comp : west) {
                dim = comp.getPreferredSize();
                preferredLayoutSize.width += dim.width + hgap;
                preferredLayoutSize.height = max(dim.height, preferredLayoutSize.height);
            }
            if (center != null) {
                dim = center.getPreferredSize();
                preferredLayoutSize.width += dim.width;
                preferredLayoutSize.height = max(dim.height, preferredLayoutSize.height);
            }
            for (final Component comp : north) {
                dim = comp.getPreferredSize();
                preferredLayoutSize.width = max(dim.width, preferredLayoutSize.width);
                preferredLayoutSize.height += dim.height + vgap;
            }
            for (final Component comp : south) {
                dim = comp.getPreferredSize();
                preferredLayoutSize.width = max(dim.width, preferredLayoutSize.width);
                preferredLayoutSize.height += dim.height + vgap;
            }
            final Insets insets = target.getInsets();
            preferredLayoutSize.width += insets.left + insets.right;
            preferredLayoutSize.height += insets.top + insets.bottom;
            return preferredLayoutSize;
        }
    }

    /** {@inheritDoc} */
    @Override public void removeLayoutComponent(final Component comp) {
        synchronized (comp.getTreeLock()) {
            //noinspection ObjectEquality
            if (comp == center) {
                center = null;
            } else if (north.contains(comp)) {
                north.remove(comp);
            } else if (south.contains(comp)) {
                south.remove(comp);
            } else if (east.contains(comp)) {
                east.remove(comp);
            } else if (west.contains(comp)) {
                west.remove(comp);
            }
        }
    }

    /** {@inheritDoc} */
    @Override public String toString() {
        return getClass().getName() + "[hgap=" + hgap + ",vgap=" + vgap + ']';
    }

    /** Class for ToolBarLayout constraints.
     * @author <a href="mailto:chris@riedquat.de">Christian Hujer</a>
     */
    public static class ToolBarConstraints implements Serializable {

        /** Serial Version. */
        @SuppressWarnings({"AnalyzingVariableNaming"})
        private static final long serialVersionUID = 1L;

        /** Enum for region.
         * @author <a href="mailto:chris@riedquat.de">Christian Hujer</a>
         */
        public enum Region {

            /** Constraint for center region. */
            CENTER,

            /** Constraint for north region, last component. */
            NORTH,

            /** Constraint for south region, last component. */
            SOUTH,

            /** Constraint for east region, last component. */
            EAST,

            /** Constraint for west region, last component. */
            WEST,

        } // enum Region

        /** Constant for first position (0). */
        public static final int FIRST = 0;

        /** Constant for last position (-1). */
        public static final int LAST = -1;

        /** Region constraint.
         * @serial include
         */
        private Region region;

        /** Position constraint.
         * @serial include
         */
        private int position;

        /** Create Constraint. */
        public ToolBarConstraints() {
        }

        /** Create Constraint with values.
         * @param region Region
         * @param position Position, must be non-negative
         */
        public ToolBarConstraints(final Region region, final int position) {
            this.region = region;
            this.position = position;
        }

        /** Returns the region constraint of this ToolBarConstraints.
         * @return The region constraint of this ToolBarConstraints.
         */
        public Region getRegion() {
            return region;
        }

        /** Sets the region constraint of this ToolBarConstraints.
         * @param region The region constraint for this ToolBarConstraints.
         */
        public void setRegion(final Region region) {
            this.region = region;
        }

        /** Returns the position constraint of this ToolBarConstraints.
         * @return The position constraint of this ToolBarConstraints.
         */
        public int getPosition() {
            return position;
        }

        /** Sets the position constraint of this ToolBarConstraints.
         * @param position The position constraint of this ToolBarConstraints.
         */
        public void setPosition(final int position) {
            this.position = position;
        }

    } // class ToolBarConstraint

} // class ToolBarLayoutManager
