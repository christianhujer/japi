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

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;
import net.sf.japi.io.args.BasicCommand;
import net.sf.japi.io.args.MethodOptionComparator;
import net.sf.japi.io.args.Option;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link MethodOptionComparator}.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
public class MethodOptionComparatorTest {

    /** Tests that comparing two methods yields the expected result.
     * @throws NoSuchMethodException (unexpected)
     */
    @Test
    public void testCompare() throws NoSuchMethodException {
        final Comparator<Method> comparator = MethodOptionComparator.INSTANCE;
        final Class<MockCommand> cmdClass = MockCommand.class;
        final Method mFoo  = cmdClass.getMethod("foo");
        final Method mBar  = cmdClass.getMethod("bar");
        final Method mBuzz = cmdClass.getMethod("buzz");
        final Method mA    = cmdClass.getMethod("a");
        final Method mZ    = cmdClass.getMethod("z");
        Assert.assertTrue("-a must be sorted before -b / --bar.", comparator.compare(mA, mBar) < 0);
        Assert.assertTrue("-b / --bar must be sorted before --buzz.", comparator.compare(mBar, mBuzz) < 0);
        Assert.assertTrue("--buzz must be sorted before -f / --foo.", comparator.compare(mBuzz, mFoo) < 0);
        Assert.assertTrue("-f / --foo must be sorted before -Z.", comparator.compare(mFoo, mZ) < 0);
    }

    /** Tests that comparing two methods that only differ in case yields the difference.
     * Test for <a href="http://sourceforge.net/tracker/index.php?func=detail&amp;aid=1748308&amp;group_id=149894&amp;atid=776737">[ 1748308 ] Options that only differ in case are not listed in --help</a>
     * @throws NoSuchMethodException (unexpected)
     */
    @Test
    public void testCompareCase() throws NoSuchMethodException {
        final Comparator<Method> comparator = MethodOptionComparator.INSTANCE;
        final Class<MockCommand> cmdClass = MockCommand.class;
        final Method mC1   = cmdClass.getMethod("c1");
        final Method mC2   = cmdClass.getMethod("c2");
        Assert.assertNotSame("-c and -C must not be the same.", 0, comparator.compare(mC1, mC2));
    }

    /** Mock Command with methods that should be properly sorted.
     * The sorting should be:
     * a(), bar(), buzz(), foo(), Z().
     */
    private static class MockCommand extends BasicCommand {

        /** Dummy command method. */
        @Option({"f", "foo"})
        public void foo() { }

        /** Dummy command method. */
        @Option({"b", "bar"})
        public void bar() { }

        /** Dummy command method. */
        @Option({"buzz"})
        public void buzz() { }

        /** Dummy command method. */
        @Option({"Z"})
        public void z() { }

        /** Dummy command method. */
        @Option({"a"})
        public void a() { }

        /** Dummy command method. */
        @Option({"c"})
        public void c1() { }

        /** Dummy command method. */
        @Option({"C"})
        public void c2() { }

        /** {@inheritDoc} */
        @SuppressWarnings({"InstanceMethodNamingConvention"})
        public int run(@NotNull final List<String> args) throws Exception {
            return 0;
        }
    }

} // class MethodOptionComparatorTest
