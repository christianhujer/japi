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

package test.net.sf.japi.tools.replacer;

import net.sf.japi.io.args.BasicCommand;
import net.sf.japi.tools.replacer.Replacer;
import net.sf.japi.tools.replacer.Substitution;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;

/** Unit Test for {@link Replacer}.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
public class ReplacerTest {

    /** Tests that a default constructor exists, doesn't throw an exception and creates the command with proper default options. */
    @Test
    public void testConstructor() {
        final Replacer replacer = new Replacer();
        Assert.assertFalse("Default for force must be false.", replacer.isForce());
        Assert.assertFalse("Default for backup must be false.", replacer.isBackup());
        Assert.assertFalse("Default for recurse must be false.", replacer.isRecursive());
        Assert.assertFalse("Default for exiting must be false.", replacer.isExiting());
        Assert.assertEquals("Default for backup extension must be .bak.", Replacer.DEFAULT_BACKUP_EXTENSION, replacer.getBackupExtension());
    }

    /** Tests that setting the force option works. */
    @Test
    public void testForce() {
        final Replacer replacer = new Replacer();
        Assert.assertFalse("Default for force must be false.", replacer.isForce());
        replacer.setForce(true);
        Assert.assertTrue("Setting force must work.", replacer.isForce());
        replacer.setForce(false);
        Assert.assertFalse("Unsetting force must work.", replacer.isForce());
    }

    /** Tests that setting the backup option works. */
    @Test
    public void testBackup() {
        final Replacer replacer = new Replacer();
        Assert.assertFalse("Default for backup must be false.", replacer.isBackup());
        replacer.setBackup(true);
        Assert.assertTrue("Setting backup must work.", replacer.isBackup());
        replacer.setBackup(false);
        Assert.assertFalse("Unsetting backup must work.", replacer.isBackup());
    }

    /** Tests that setting the backup option works. */
    @Test
    public void testRecursive() {
        final Replacer replacer = new Replacer();
        Assert.assertFalse("Default for recurse must be false.", replacer.isRecursive());
        replacer.setRecursive(true);
        Assert.assertTrue("Setting recurse must work.", replacer.isRecursive());
        replacer.setRecursive(false);
        Assert.assertFalse("Unsetting recurse must work.", replacer.isRecursive());
    }

    /** Tests that setting the exiting option works. */
    @Test
    public void testExiting() {
        final BasicCommand replacer = new Replacer();
        Assert.assertFalse("Default for exiting must be false.", replacer.isExiting());
        replacer.setExiting(true);
        Assert.assertTrue("Setting exiting must work.", replacer.isExiting());
        replacer.setExiting(false);
        Assert.assertFalse("Unsetting exiting must work.", replacer.isExiting());
    }

    /** Tests that setting the backup extension option works. */
    @Test
    public void testBackupExtension() {
        final Replacer replacer = new Replacer();
        Assert.assertEquals("Default backup extension must be .bak.", Replacer.DEFAULT_BACKUP_EXTENSION, replacer.getBackupExtension());
        replacer.setBackupExtension(".foo");
        Assert.assertEquals("Setting the backup extension must work.", ".foo", replacer.getBackupExtension());
        replacer.setBackupExtension(Replacer.DEFAULT_BACKUP_EXTENSION);
        Assert.assertEquals("Setting the backup extension must work.", Replacer.DEFAULT_BACKUP_EXTENSION, replacer.getBackupExtension());
    }

    /** Tests that setting the substitutions works. */
    @Test
    public void testSubstitutions() {
        final Replacer replacer = new Replacer();
        final Collection<Substitution> substitutions = replacer.getSubstitutions();
        Assert.assertEquals("An initial replacer has no substitutions.", 0, substitutions.size());
        replacer.addSubstitution("s/foo/bar/");
        Assert.assertEquals("Adding substitutions must work.", 1, substitutions.size());
        replacer.addSubstitution("s/buzz/bazz/");
        Assert.assertEquals("Adding substitutions must work.", 2, substitutions.size());
    }

    /** Tests {@link Replacer#replaceAll(CharSequence)} with a very simple use case. */
    @Test
    public void testSimple() {
        final Replacer replacer = new Replacer();
        replacer.addSubstitution("s/foo/bar/");
        final String original = "foo";
        final String expected = "bar";
        final CharSequence replaced = replacer.replaceAll(original);
        Assert.assertTrue("Running substitutions through Replacer must work.", Replacer.equals(expected, replaced));
    }
}
