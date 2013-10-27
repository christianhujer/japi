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

package test.net.sf.japi.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import net.sf.japi.util.Collections2;
import org.junit.Assert;
import org.junit.Test;

/** Test for {@link Collections2}.
 * @author <a href="mailto:chris@riedquat.de">Christian Hujer</a>
 */
public class Collections2Test {

    /** Test case for {@link Collections2#collect(Collection, Iterator)}. */
    @Test
    public void testCollect() {
        final Set<String> original = Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("foo", "bar")));
        final Set<String> collected = Collections2.collect(new HashSet<String>(), original.iterator());
        Assert.assertEquals("Collect must collect all elements.", original, collected);
    }

}
