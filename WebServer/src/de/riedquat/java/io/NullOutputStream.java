package de.riedquat.java.io;

import java.io.IOException;
import java.io.OutputStream;

public class NullOutputStream extends OutputStream {
    private static OutputStream instance = new NullOutputStream();

    public static OutputStream getInstance() {
        return instance;
    }

    @Override
    public void write(final int b) throws IOException {
    }
}
