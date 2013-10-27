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

import net.sf.japi.cardlearn.CardDatabaseConfig;
import org.junit.Assert;
import org.junit.Test;

/**
 * Class Description.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
public class TestCardConfiguration {

    @Test
    public void testSetFieldname() {
        final CardDatabaseConfig card = new CardDatabaseConfig();
        for (int i = 0; i < 6; i++) {
            final String testValue = "foo" + i;
            Assert.assertFalse("Fieldname must initially be unset.", testValue.equals(card.getFieldname(i)));
            card.setFieldname(i, testValue);
            Assert.assertEquals("Fieldname must be stored.", card.getFieldname(i), testValue);
        }
    }
}
