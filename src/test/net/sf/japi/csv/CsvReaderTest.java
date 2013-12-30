package net.sf.japi.csv;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CsvReaderTest {
    private static void assertCsvEquals(final String oneWordCsv, final String[][] expectedData) throws IOException {
        final String[][] actualData = readCsv(oneWordCsv);
        assertTrue(Arrays.deepEquals(expectedData, actualData));
    }

    private static String[][] readCsv(final String oneWordCsv) throws IOException {
        return CsvReader.readCsv(new StringReader(oneWordCsv));
    }

    @Test
    public void emptyCsv_emptyData() throws IOException {
        final String emptyCsv = "";
        final String[][] expectedData = new String[0][0];
        assertCsvEquals(emptyCsv, expectedData);
    }

    @Test
    public void oneWord_oneCell() throws IOException {
        final String oneWordCsv = "foo";
        final String[][] expectedData = {{"foo"}};
        assertCsvEquals(oneWordCsv, expectedData);
    }

    @Test
    public void twoWords_twoCells() throws IOException {
        final String twoWordCsv = "foo,bar";
        final String[][] expectedData = {{"foo", "bar"}};
        assertCsvEquals(twoWordCsv, expectedData);
    }

    @Test
    public void twoLinesCR_twoRows() throws IOException {
        final String twoLineCsv = "foo\rbar";
        final String[][] expectedData = {{"foo"}, {"bar"}};
        assertCsvEquals(twoLineCsv, expectedData);
    }

    @Test
    public void twoLinesCRLF_twoRows() throws IOException {
        final String twoLineCsv = "foo\r\nbar";
        final String[][] expectedData = {{"foo"}, {"bar"}};
        assertCsvEquals(twoLineCsv, expectedData);
    }

    @Test
    public void twoLinesLF_twoRows() throws IOException {
        final String twoLineCsv = "foo\nbar";
        final String[][] expectedData = {{"foo"}, {"bar"}};
        assertCsvEquals(twoLineCsv, expectedData);
    }

    @Test
    public void commaInQuotedField_oneCell() throws IOException {
        final String commaInQuotedField = "\"foo,bar\"";
        final String[][] expectedData = {{"foo,bar"}};
        assertCsvEquals(commaInQuotedField, expectedData);
    }

    @Test
    public void quoteInQuotedField_oneCell() throws IOException {
        final String quoteInQuotedField = "\"foo\"\"bar\"";
        final String[][] expectedData = {{"foo\"bar"}};
        assertCsvEquals(quoteInQuotedField, expectedData);
    }

    @Test
    public void crInQuotedField_oneCell() throws IOException {
        final String crInQuotedField = "\"foo\rbar\"";
        final String[][] expectedData = {{"foo\rbar"}};
        assertCsvEquals(crInQuotedField, expectedData);
    }

    @Test
    public void crlfInQuotedField_oneCell() throws IOException {
        final String crlfInQuotedField = "\"foo\r\nbar\"";
        final String[][] expectedData = {{"foo\r\nbar"}};
        assertCsvEquals(crlfInQuotedField, expectedData);
    }

    @Test
    public void lfInQuotedField_oneCell() throws IOException {
        final String lfInQuotedField = "\"foo\nbar\"";
        final String[][] expectedData = {{"foo\nbar"}};
        assertCsvEquals(lfInQuotedField, expectedData);
    }
}
