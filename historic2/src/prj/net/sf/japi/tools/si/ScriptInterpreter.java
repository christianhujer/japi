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

package net.sf.japi.tools.si;

import java.io.InputStreamReader;
import java.io.IOException;
import java.util.List;
import java.util.TreeSet;
import java.util.Collection;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import net.sf.japi.io.args.ArgParser;
import net.sf.japi.io.args.BasicCommand;
import net.sf.japi.io.args.Option;
import net.sf.japi.io.args.OptionType;
import org.jetbrains.annotations.NotNull;

/** Interpreter for Scripts.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class ScriptInterpreter extends BasicCommand {

    /** The mime type of the script language to execute. */
    @SuppressWarnings({"InstanceVariableMayNotBeInitialized"})
    @NotNull private String mimeType;

    /** Main program.
     * @param args Command line arguments (try --help).
     */
    public static void main(final String... args) {
        ArgParser.simpleParseAndRun(new ScriptInterpreter(), args);
    }

    /** Sets the mime type of the scripting language.
     * @param mimeType Mime type of the scripting language.
     */
    @Option(type = OptionType.REQUIRED, value = {"m"})
    public void setMimeType(@NotNull final String mimeType) {
        this.mimeType = mimeType;
    }

    /** {@inheritDoc} */
    @SuppressWarnings({"InstanceMethodNamingConvention"})
    public int run(@NotNull final List<String> args) throws IOException, ScriptException {
        final ScriptEngineManager sem = new ScriptEngineManager();
        final ScriptEngine se = sem.getEngineByMimeType(mimeType);
        se.getBindings(ScriptContext.ENGINE_SCOPE).put("cmd", this);
        final InputStreamReader in = new InputStreamReader(System.in);
        try {
            se.eval(in);
        } finally {
            in.close();
        }
        return 0;
    }

    /** Lists the available mime types. */
    @Option(type = OptionType.TERMINAL, value = {"l", "list"})
    public void list() {
        final Collection<String> mimeTypes = new TreeSet<String>();
        final ScriptEngineManager sem = new ScriptEngineManager();
        for (final ScriptEngineFactory factory : sem.getEngineFactories()) {
            mimeTypes.addAll(factory.getMimeTypes());
        }
        for (final String mimeType : mimeTypes) {
            System.out.println(mimeType);
        }
    }
}
