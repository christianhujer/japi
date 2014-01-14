package de.riedquat.java.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Util {

    private static final int DEFAULT_BUFFER_SIZE = 4096;
    private static final int CR = 13;
    private static final int LF = 10;
    private static final int EOF = -1;

    public static void copy(final InputStream in, final OutputStream out) throws IOException {
        copy(out, in);
    }

    public static void copy(final OutputStream out, final InputStream... ins) throws IOException {
        copy(out, DEFAULT_BUFFER_SIZE, ins);
    }

    /** Just a test comment for markdown.
     * Let's see how this works out.
     *
     * This is a markdown test.
     * - One list item.
     * - Another list item.
     *
     * @param out
     *  OutputStream to write to.
     * @param bufferSize
     *  Size of the buffer for the copy operation.
     * @param ins
     *  InputStreams to read from.
     * @throws IOException
     */
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
            case EOF:
                if (out.size() != 0) {
                    return out.toString();
                }
                return null;
            case CR:
                break;
            case LF:
                return out.toString();
            default:
                out.write(b);
                break;
            }
        }
    }
}
