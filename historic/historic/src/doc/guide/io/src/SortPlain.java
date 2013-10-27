import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.System.out;
import java.util.ArrayList;
import static java.util.Collections.sort;
import java.util.List;

/** Plain implementation of the UNIX command <code>sort</code>.
 * @author <a href="mailto:chris@riedquat.de">Christian Hujer</a>
 */
public class SortPlain {

    /** Main program.
     * @param args command line arguments: names of files to sort
     */
    public static void main(final String... args) {
        final List<String> lineList = new ArrayList<String>();
        if (args.length == 0) {
            try {
                final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                try {
                    String line;
                    while ((line = in.readLine()) != null) {
                        lineList.add(line);
                    }
                } catch (final IOException e) {
                    System.err.println(e);
                } finally {
                    in.close();
                }
            } catch (final IOException e) {
                System.err.println(e);
            }
        } else {
            for (final String arg : args) {
                try {
                    final BufferedReader in = new BufferedReader(new FileReader(arg));
                    try {
                        String line;
                        while ((line = in.readLine()) != null) {
                            lineList.add(line);
                        }
                    } finally {
                        in.close();
                    }
                } catch (final IOException e) {
                    System.err.println(e);
                }
            }
        }
        sort(lineList);
        for (final String line : lineList) {
            out.println(line);
        }
    }

} // class SortJAPI
