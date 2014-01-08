package de.riedquat.java.io;

import de.riedquat.java.util.Arrays;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static de.riedquat.java.io.Util.copy;
import static de.riedquat.java.util.Arrays.EMPTY_BYTE_ARRAY;
import static org.junit.Assert.assertArrayEquals;

public class UtilTest {

    @Test
    public void emptyStream_copiesNothing() throws IOException {
        assertCopies(EMPTY_BYTE_ARRAY);
    }

    @Test
    public void copiesData() throws IOException {
        assertCopies(Arrays.createRandomByteArray(10001));
    }

    private void assertCopies(final byte[] testData) throws IOException {
        final ByteArrayInputStream in = new ByteArrayInputStream(testData);
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        copy(out, in);
        assertArrayEquals(testData, out.toByteArray());
    }

}
