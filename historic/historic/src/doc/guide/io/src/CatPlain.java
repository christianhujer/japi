import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import static java.lang.System.err;
import static java.lang.System.in;
import static java.lang.System.out;

/** Plain implementation of the UNIX comand <code>cat</code>.
 * @author <a href="mailto:chris@riedquat.de">Christian Hujer</a>
 */
public class CatPlain {

    /** Main program.
     * @param args command line arguments: names of files to concatenate
     */
    public static void main(final String... args) {
        if (args.length == 0) {
            try {
                copy(in, out);
            } catch (final IOException e) {
                err.println(e);
            }
        } else {
            for (final String arg : args) {
                try {
                    final InputStream in = new FileInputStream(arg);
                    try {
                        copy(in, out);
                    } finally {
                        in.close();
                    }
                } catch (final IOException e) {
                    err.println(e);
                }
            }
        }
    }

    /** Copies all data from one stream to another.
     * @param in source stream
     * @param out sink stream
     * @throws IOException  in case reading or writing caused an error.
     */
    private static void copy(final InputStream in, final OutputStream out) throws IOException {
        final byte[] buf = new byte[4096];
        for (int bytesRead; (bytesRead = in.read(buf)) != -1; ) {
            out.write(buf, 0, bytesRead);
        }
    }

} // class CatPlain
