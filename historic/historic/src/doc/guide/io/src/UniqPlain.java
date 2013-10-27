import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.System.err;
import static java.lang.System.out;

/** JAPI-based implementation of the UNIX comand <code>uniq</code>.
 * @author <a href="mailto:chris@riedquat.de">Christian Hujer</a>
 */
public class UniqPlain {

    /** Main program.
     * @param args command line arguments: names of files to print unique lines from
     */
    public static void main(final String... args) {
        String prevLine = null;
        if (args.length == 0) {
            try {
                final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                try {
                    for (String line; (line = in.readLine()) != null;) {
                        if (!line.equals(prevLine)) {
                            out.println(line);
                        }
                        prevLine = line;
                    }
                } finally {
                    in.close();
                }
            } catch (final IOException e) {
                err.println(e);
            }
        } else {
            for (final String arg : args) {
                try {
                    final BufferedReader in = new BufferedReader(new FileReader(arg));
                    try {
                        for (String line; (line = in.readLine()) != null;) {
                            if (!line.equals(prevLine)) {
                                out.println(line);
                            }
                            prevLine = line;
                        }
                    } finally {
                        in.close();
                    }
                } catch (final IOException e) {
                    err.println(e);
                }
            }
        }
    }

} // class UniqJAPI
