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

package test.net.sf.japi.pffhtrain;

import net.sf.japi.pffhtrain.Material;
import org.junit.Assert;
import org.junit.Test;

/** Test for {@link Material}.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>.
 */
public class MaterialTest {

    /** Tests that the field id is initially null. */
    @Test
    public void testNewMaterialHasNullId() {
        final Material oUT = new Material();
        Assert.assertNull("Expecting newly created Material to have null id", oUT.getId());
    }

    /** Tests that the field month is initially null. */
    @Test
    public void testNewMaterialHasNullMonth() {
        final Material oUT = new Material();
        Assert.assertNull("Expecting newly created Material to have null month", oUT.getMonth());
    }

    /** Tests that the field pckg is initially null. */
    @Test
    public void testNewMaterialHasNullPckg() {
        final Material oUT = new Material();
        Assert.assertNull("Expecting newly created Material to have null pckg", oUT.getPckg());
    }

    /** Tests that the field title is initially null. */
    @Test
    public void testNewMaterialHasNullTitle() {
        final Material oUT = new Material();
        Assert.assertNull("Expecting newly created Material to have null title", oUT.getTitle());
    }

    /** Tests that the field type is initially null. */
    @Test
    public void testNewMaterialHasNullType() {
        final Material oUT = new Material();
        Assert.assertNull("Expecting newly created Material to have null type", oUT.getType());
    }

}
