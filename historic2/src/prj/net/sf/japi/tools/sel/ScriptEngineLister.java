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

package net.sf.japi.tools.sel;

import java.util.List;
import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import net.sf.japi.io.args.ArgParser;
import net.sf.japi.io.args.BasicCommand;
import org.jetbrains.annotations.NotNull;

/** Program for listing available script engines.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class ScriptEngineLister extends BasicCommand {

    /** Main program.
     * @param args Command line arguments (try --help).
     */
    public static void main(final String... args) {
        ArgParser.simpleParseAndRun(new ScriptEngineLister(), args);
    }

    /** {@inheritDoc} */
    @SuppressWarnings({"InstanceMethodNamingConvention"})
    public int run(@NotNull final List<String> args) throws Exception {
        final ScriptEngineManager sem = new ScriptEngineManager();
        for (final ScriptEngineFactory factory : sem.getEngineFactories()) {
            System.out.println("Name: " + factory.getEngineName());
            System.out.println("Version: " + factory.getEngineVersion());
            System.out.println("Extensions: " + factory.getExtensions());
            System.out.println("Language Name: " + factory.getLanguageName());
            System.out.println("Language Version: " + factory.getLanguageVersion());
            System.out.println("Mime Types: " + factory.getMimeTypes());
            System.out.println("Names: " + factory.getNames());
            printBindings(factory);
            System.out.println();
        }
        return 0;
    }

    private void printBindings(final ScriptEngineFactory factory) {
        final ScriptEngine se = factory.getScriptEngine();
        printBindings(se, ScriptContext.GLOBAL_SCOPE);
        printBindings(se, ScriptContext.ENGINE_SCOPE);
    }

    private void printBindings(final ScriptEngine engine, final int scope) {
        System.out.print("Scope: ");
        switch (scope) {
        case ScriptContext.GLOBAL_SCOPE:
            System.out.print("GLOBAL_SCOPE");
            break;
        case ScriptContext.ENGINE_SCOPE:
            System.out.print("ENGINE_SCOPE");
            break;
        default:
            System.out.print(scope);
        }
        System.out.println();
        final Bindings bindings = engine.getBindings(scope);
        if (bindings == null) {
            System.out.println("null");
        } else {
            for (final Object entry : bindings.entrySet()) {
                System.out.println(entry);
            }
        }
    }
}
