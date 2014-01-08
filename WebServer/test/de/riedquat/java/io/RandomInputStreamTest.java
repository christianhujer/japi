package de.riedquat.java.io;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertTrue;

public class RandomInputStreamTest {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    public void byteReading_usesRandom() throws IOException {
        final RandomMock randomMock = new RandomMock();
        randomMock.setExpectedCallsNextInt(2);
        final InputStream in = new RandomInputStream(randomMock);
        final int b1 = in.read();
        final int b2 = in.read();
        assertTrue(b1 >= 0 && b1 < 256);
        assertTrue(b2 >= 0 && b2 < 256);
        assertTrue(b1 != b2);
        randomMock.assertExpectations();
    }

    @Test
    public void bulkReading_usesRandom() throws IOException {
        final RandomMock randomMock = new RandomMock();
        randomMock.setExpectedCallsNextBytes(1);
        final InputStream in = new RandomInputStream(randomMock);
        final byte[] buf = new byte[32];
        final int bytesRead = in.read(buf);
        Assert.assertEquals(32, bytesRead);
        randomMock.assertExpectations();
    }

    @Test
    public void boundBulkReading_usesRandom() throws IOException {
        final RandomMock randomMock = new RandomMock();
        randomMock.setExpectedCallsNextBytes(1);
        final InputStream in = new RandomInputStream(randomMock);
        final byte[] buf = new byte[32];
        final int bytesRead = in.read(buf, 1, 30);
        Assert.assertEquals(30, bytesRead);
        randomMock.assertExpectations();
    }
}
