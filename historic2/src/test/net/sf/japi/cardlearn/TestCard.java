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

package test.net.sf.japi.cardlearn;

import net.sf.japi.cardlearn.Card;
import org.junit.Assert;
import org.junit.Test;

/**
 * Class Description.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
public class TestCard {

    @Test
    public void testFields() {
        final Card card = new Card();
        for (int i = 0; i < 6; i++) {
            final String testValue = "foo" + i;
            Assert.assertFalse("Field value must initially be unset.",  testValue.equals(card.getField(i)));
            card.setField(i, testValue);
            Assert.assertEquals("Field value must be stored.", card.getField(i), testValue);
        }
    }
}
