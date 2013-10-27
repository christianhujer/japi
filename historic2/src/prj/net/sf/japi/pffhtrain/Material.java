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

package net.sf.japi.pffhtrain;

import org.jetbrains.annotations.Nullable;

/** A material.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class Material {

    /** The type of this material. */
    @Nullable private MaterialType type;

    /** The id of this material. */
    @Nullable private String id;

    /** The title of this material. */
    @Nullable private String title;

    /** The month in which this material should be learned. */
    @Nullable private Integer month;

    /** The package number in which this material was delivered. */
    @Nullable private Integer pckg;

    /** Creates a new Material. */
    public Material() {
    }

    /** Sets the type of this material.
     * @param type The type of this material.
     */
    public void setType(@Nullable final MaterialType type) {
        this.type = type;
    }

    /** Returns the type of this material.
     * @return The type of this material.
     */
    @Nullable public MaterialType getType() {
        return type;
    }

    /** Sets the id of this material.
     * @param id The id of this material.
     */
    public void setId(@Nullable final String id) {
        this.id = id;
    }

    /** Returns the id of this material.
     * @return The id of this material.
     */
    @Nullable public String getId() {
        return id;
    }

    /** Sets the title of this material.
     * @param title The title of this material.
     */
    public void setTitle(@Nullable final String title) {
        this.title = title;
    }

    /** Returns the title of this material.
     * @return The title of this material.
     */
    @Nullable public String getTitle() {
        return title;
    }

    /** Sets the month of this material.
     * @param month The month of this material.
     */
    public void setMonth(@Nullable final Integer month) {
        this.month = month;
    }

    /** Returns the month of this material.
     * @return the month of this material.
     */
    @Nullable public Integer getMonth() {
        return month;
    }

    /** Sets the package of this material.
     * @param pckg The package of this material.
     */
    public void setPckg(@Nullable final Integer pckg) {
        this.pckg = pckg;
    }

    /** Returns the package of this material.
     * @return The package of this material.
     */
    @Nullable public Integer getPckg() {
        return pckg;
    }

}
