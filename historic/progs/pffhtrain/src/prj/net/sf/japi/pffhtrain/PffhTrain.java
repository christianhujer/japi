/*
 * Copyright (C) 2009  Christian Hujer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.sf.japi.pffhtrain;

import java.util.List;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import net.sf.japi.io.args.ArgParser;
import net.sf.japi.io.args.BasicCommand;
import net.sf.japi.swing.action.ActionBuilder;
import net.sf.japi.swing.action.ActionBuilderFactory;
import org.jetbrains.annotations.NotNull;

/** PffhTrain main program.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class PffhTrain extends BasicCommand {

    /** ActionBuilder. */
    private static final ActionBuilder ACTION_BUILDER = ActionBuilderFactory.getInstance().getActionBuilder(PffhTrain.class);

    /** Program frame. */
    private final JFrame frame;

    /** Creates an instance of PffhTrain. */
    public PffhTrain() {
        frame = new JFrame("PffhTrain");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setJMenuBar(ACTION_BUILDER.createMenuBar(true, "main", this));
    }

    /** Main program.
     * @param args Command line arguments (try --help).
     */
    public static void main(final String... args) {
        ArgParser.simpleParseAndRun(new PffhTrain(), args);
    }

    /** {@inheritDoc} */
    public int run(@NotNull final List<String> args) throws Exception {
        frame.setVisible(true);
        return 0;
    }

}
