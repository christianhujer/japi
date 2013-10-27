import static java.lang.System.out;
import java.util.ArrayList;
import static java.util.Collections.sort;
import java.util.List;
import net.sf.japi.io.ARGV;

/** JAPI-based implementation of the UNIX command <code>sort</code>.
 * @author <a href="mailto:chris@riedquat.de">Christian Hujer</a>
 */
public class SortJAPI {

    /** Main program.
     * @param args command line arguments: names of files to sort
     */
    public static void main(final String... args) {
        final List<String> lineList = new ArrayList<String>();
        for (final String line : new ARGV(args)) {
            lineList.add(line);
        }
        sort(lineList);
        for (final String line : lineList) {
            out.println(line);
        }
    }

} // class SortJAPI
