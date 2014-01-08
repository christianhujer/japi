package de.riedquat.java.io;

import java.util.Random;

import static org.junit.Assert.assertEquals;

public class RandomMock extends Random {
    private int expectedCallsNextInt;
    private int expectedCallsNextBytes;
    private int actualCallsNextBytes;
    private int actualCallsNextInt;

    @Override
    public void nextBytes(final byte[] bytes) {
        actualCallsNextBytes++;
        super.nextBytes(bytes);
    }

    @Override
    public int nextInt(final int bound) {
        actualCallsNextInt++;
        return super.nextInt(bound);
    }

    public void assertExpectations() {
        assertEquals(expectedCallsNextBytes, actualCallsNextBytes);
        assertEquals(expectedCallsNextInt, actualCallsNextInt);
    }

    public void setExpectedCallsNextInt(final int expectedCallsNextInt) {
        this.expectedCallsNextInt = expectedCallsNextInt;
    }

    public void setExpectedCallsNextBytes(final int expectedCallsNextBytes) {
        this.expectedCallsNextBytes = expectedCallsNextBytes;
    }
}
