import static java.lang.System.out;
import net.sf.japi.io.ARGV;

/** JAPI-based implementation of the UNIX comand <code>uniq</code>.
 * @author <a href="mailto:chris@riedquat.de">Christian Hujer</a>
 */
public class UniqJAPI {

    /** Main program.
     * @param args command line arguments: names of files to print unique lines from
     */
    public static void main(final String... args) {
        String prevLine = null;
        for (final String line : new ARGV(args)) {
            if (!line.equals(prevLine)) {
                out.println(line);
            }
            prevLine = line;
        }
    }

} // class UniqJAPI
