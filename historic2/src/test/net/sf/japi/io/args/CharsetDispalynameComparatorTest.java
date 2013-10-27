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

package test.net.sf.japi.io.args;

import java.nio.charset.Charset;
import net.sf.japi.io.args.CharsetDisplaynameComparator;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link CharsetDisplaynameComparator}.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
public class CharsetDispalynameComparatorTest {

    /** Tests that {@link CharsetDisplaynameComparator#compare(Charset, Charset)} works. */
    @Test
    public void testCompare() {
        @SuppressWarnings({"TypeMayBeWeakened"})
        final CharsetDisplaynameComparator testling = new CharsetDisplaynameComparator();
        final Charset charset1 = Charset.forName("utf-8");
        final Charset charset2 = Charset.forName("iso-8859-1");
        Assert.assertTrue("Expecting iso-8859-1 to be sorted before utf-8.", testling.compare(charset1, charset2) > 0);
    }
}
