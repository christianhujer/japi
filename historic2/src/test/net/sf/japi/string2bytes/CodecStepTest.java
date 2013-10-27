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

package test.net.sf.japi.string2bytes;

import net.sf.japi.string2bytes.CodecStep;
import net.sf.japi.string2bytes.EntityCodec;
import net.sf.japi.string2bytes.IdentityCodec;
import net.sf.japi.string2bytes.StringCodec;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for {@link CodecStep}.
 *
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
public class CodecStepTest {

    /** The CodecStep to test. */
    private CodecStep codecStep;

    /** The Codec to use for most tests. */
    private StringCodec codec;

    /** The Charset to use for most tests. */
    private String charset;

    /**
     * Creates the test data and a testling.
     */
    @Before public void setUp() {
        codec = new IdentityCodec();
        charset = "utf-8";
        codecStep = new CodecStep(codec, charset);
    }

    /**
     * Removes the test data and the testling.
     */
    @SuppressWarnings({"AssignmentToNull"})
    @After public void tearDown() {
        codec = null;
        charset = null;
        codecStep = null;
    }

    /**
     * Tests that {@link CodecStep#CodecStep(StringCodec, String)} works.
     * @throws Exception (unexpected)
     */
    @Test public void testCodecStep() throws Exception {
        testGetCharset();
        testGetCodec();
    }


    /**
     * Tests that {@link CodecStep#getCharset()} works.
     * @throws Exception (unexpected)
     */
    @Test public void testGetCharset() throws Exception {
        Assert.assertEquals("Charset must be stored by constructor.", charset, codecStep.getCharset());
    }

    /**
     * Tests that {@link CodecStep#setCharset(String)} works.
     * @throws Exception (unexpected)
     */
    @Test public void testSetCharset() throws Exception {
        final String charset = "iso-8859-1";
        codecStep.setCharset(charset);
        Assert.assertEquals("Charset must be stored by setter.", charset, codecStep.getCharset());
    }

    /**
     * Tests that {@link CodecStep#getCodec()} works.
     * @throws Exception (unexpected)
     */
    @Test public void testGetCodec() throws Exception {
        Assert.assertSame("Codec must be stored by constructor.", codec, codecStep.getCodec());
    }

    /**
     * Tests that {@link CodecStep#setCodec(StringCodec)} works.
     * @throws Exception (unexpected)
     */
    @Test public void testSetCodec() throws Exception {
        final StringCodec codec = new EntityCodec();
        codecStep.setCodec(codec);
        Assert.assertSame("Codec must be stored by setter.", codec, codecStep.getCodec());
    }

} // class CodecStepTest
