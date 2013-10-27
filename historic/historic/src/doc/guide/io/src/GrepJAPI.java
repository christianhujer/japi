import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.sf.japi.io.ARGV;
import net.sf.japi.io.args.ArgParser;
import net.sf.japi.io.args.BasicCommand;
import net.sf.japi.io.args.Option;
import net.sf.japi.io.args.OptionType;
import org.jetbrains.annotations.NotNull;

/** Implementation of Grep using JAPI.
 * @author <a href="mailto:chris@itcqis.com">Christian Hujer</a>
 */
public class GrepJAPI extends BasicCommand {

    /** Whether to display the total line number. */
    private boolean totalLine;

    /** Main program.
     * @param args command line arguments
     */
    public static void main(final String... args) {
        ArgParser.simpleParseAndRun(new GrepJAPI(), args);
    }

    /** {@inheritDoc} */
    @SuppressWarnings({"InstanceMethodNamingConvention"})
    public int run(@NotNull final List<String> args) {
        int totalLines = 0;
        final String regex = args.remove(0);
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher("");
        for (final String line : new ARGV(args.toArray(new String[args.size()]))) {
            totalLines++;
            if (matcher.reset(line).find()) {
                if (totalLine) {
                    System.out.format("%d:%s%n", totalLines, line);
                } else {
                    System.out.format("%s%n", line);
                }
            }
        }
        return 0;
    }

    /** Print version and quit. */
    @Option(type = OptionType.TERMINAL, value = {"V", "version"})
    public void version() {
        System.out.println("GrepJAPI V 0.1");
    }

    /** Set displaying the total line number. */
    @Option({"l", "total-line"})
    public void totalLine() {
        totalLine = true;
    }

} // class GrepJAPI
