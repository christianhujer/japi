import java.io.IOException;
import static java.lang.System.out;
import net.sf.japi.io.ARGVInputStream;
import static net.sf.japi.io.IOHelper.copy;

/** JAPI-based implementation of the UNIX comand <code>cat</code>.
 * @author <a href="mailto:chris@riedquat.de">Christian Hujer</a>
 */
public class CatJAPI {

    /** Main program.
     * @param args command line arguments: names of files to concatenate
     * @throws IOException in case of I/O problems.
     */
    public static void main(final String... args) throws IOException {
        //noinspection IOResourceOpenedButNotSafelyClosed
        copy(new ARGVInputStream(args), out);
    }

} // class CatJAPI
