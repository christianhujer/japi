package de.riedquat.java.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Util {

    public static final int DEFAULT_BUFFER_SIZE = 4096;

    public static void copy(final InputStream in, final OutputStream out) throws IOException {
        copy(out, in);
    }

    public static void copy(final OutputStream out, final InputStream... ins) throws IOException {
        copy(out, DEFAULT_BUFFER_SIZE, ins);
    }

    public static void copy(final OutputStream out, final int bufferSize, final InputStream... ins) throws IOException {
        copy(out, new byte[bufferSize], ins);
    }

    public static void copy(final OutputStream out, final byte[] buffer, final InputStream... ins) throws IOException {
        for (final InputStream in : ins) {
            copyImpl(out, buffer, in);
        }
    }

    private static void copyImpl(final OutputStream out, final byte[] buffer, final InputStream in) throws IOException {
        for (int bytesRead; (bytesRead = in.read(buffer)) != -1; ) {
            out.write(buffer, 0, bytesRead);
        }
    }

    public static String readLine(final InputStream in) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        while (true) {
            final int b = in.read();
            switch (b) {
            case -1:
                if (out.size() != 0) {
                    return out.toString();
                }
                return null;
            case 13:
                break;
            case 10:
                return out.toString();
            default:
                out.write(b);
                break;
            }
        }
    }
}
