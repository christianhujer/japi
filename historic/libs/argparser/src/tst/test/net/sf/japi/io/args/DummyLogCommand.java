package test.net.sf.japi.io.args;

import net.sf.japi.io.args.LogCommand;
import org.jetbrains.annotations.NotNull;
import java.util.List;

/**
 * TODO:2009-02-21:christianhujer:Documentation.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
class DummyLogCommand extends LogCommand {

    /** {@inheritDoc} */
    public int run(@NotNull final List<String> args) {
        return 0;
    }
}
