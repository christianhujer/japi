package net.sf.japi.csv;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("StandardVariableNames")
public class CsvReader {
    /**
     * Reads all remaining data from the specified reader as CSV.
     *
     * @param reader
     *         Reader to read from.
     * @return Remaining data as CSV.
     * @throws IOException
     *         In case of I/O problems.
     */
    public static String[][] readCsv(final Reader reader) throws IOException {
        return readCsvImpl(getPushbackReader(reader));
    }

    private static PushbackReader getPushbackReader(final Reader reader) {
        return reader instanceof PushbackReader ? (PushbackReader) reader : new PushbackReader(reader);
    }

    private static String[][] readCsvImpl(final PushbackReader reader) throws IOException {
        final List<List<String>> data = new ArrayList<>();
        for (List<String> row; (row = readRow(reader)) != null; ) {
            data.add(row);
        }
        return toArray(data);
    }

    @Nullable
    private static List<String> readRow(final PushbackReader reader) throws IOException {
        final int c = reader.read();
        if (c == -1) {
            return null;
        }
        reader.unread(c);
        final List<String> row = new ArrayList<>();
        for (String field; (field = readField(reader)) != null; ) {
            row.add(field);
        }
        return row;
    }

    @SuppressWarnings("OverlyLongMethod")
    @Nullable
    private static String readField(final PushbackReader reader) throws IOException {
        final int c = reader.read();
        switch (c) {
        case -1:
        case '\n':
            return null;
        case '\r':
            final int nextChar = reader.read();
            if (nextChar != '\n' && nextChar != -1) {
                reader.unread(nextChar);
            }
            return null;
        case '"':
            return readQuotedField(reader);
        default:
            reader.unread(c);
            return readUnquotedField(reader);
        }
    }

    private static String readQuotedField(final PushbackReader reader) throws IOException {
        final StringBuilder sb = new StringBuilder();
        for (int c; (c = readQuotedFieldChar(reader)) != -1; ) {
            sb.append((char) c);
        }
        return sb.toString();
    }

    private static int readQuotedFieldChar(final PushbackReader reader) throws IOException {
        final int c = reader.read();
        switch (c) {
        case -1:
            return -1;
        case '"':
            return reader.read() == '"' ? '"' : -1;
        default:
            return c;
        }
    }

    private static String readUnquotedField(final PushbackReader reader) throws IOException {
        final StringBuilder sb = new StringBuilder();
        for (int c; (c = readUnquotedFieldChar(reader)) != -1; ) {
            sb.append((char) c);
        }
        return sb.toString();
    }

    @SuppressWarnings("fallthrough")
    private static int readUnquotedFieldChar(final PushbackReader reader) throws IOException {
        final int c = reader.read();
        switch (c) {
        case '\r':
        case '\n':
            reader.unread(c);
        case ',':
            return -1;
        default:
            return c;
        }
    }

    private static String[][] toArray(final List<List<String>> data) {
        final String[][] array = new String[getRows(data)][getCols(data)];
        for (int row = 0; row < array.length; row++) {
            final List<String> rowData = data.get(row);
            for (int col = 0; col < array[row].length; col++) {
                array[row][col] = rowData.get(col);
            }
        }
        return array;
    }

    private static int getCols(final Iterable<? extends Collection<String>> data) {
        int cols = 0;
        for (final Collection<String> row : data) {
            cols = Math.max(cols, row.size());
        }
        return cols;
    }

    private static int getRows(final Collection<?> data) {
        return data.size();
    }
}
