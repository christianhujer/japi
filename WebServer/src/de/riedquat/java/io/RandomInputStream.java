package de.riedquat.java.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class RandomInputStream extends InputStream {

    private final Random random;

    public RandomInputStream(final Random random) {
        this.random = random;
    }

    public RandomInputStream() {
        this(new Random());
    }

    @Override
    public int read() throws IOException {
        return random.nextInt(256);
    }

    @Override
    public int read(final byte[] b, final int off, final int len) throws IOException {
        if (off == 0 && len == b.length) {
            return read(b);
        }
        final byte[] b2 = new byte[len];
        try {
            return read(b2);
        } finally {
            System.arraycopy(b2, 0, b, off, len);
        }
    }

    @Override
    public int read(final byte[] b) throws IOException {
        random.nextBytes(b);
        return b.length;
    }
}
