/*
 * Copyright (C) 2009  Christian Hujer.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package net.sf.japi.io.args;

import net.sf.japi.io.args.converter.ConverterRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Parser for command line arguments.
 * <p>
 * The most popular usage is by extending {@link BasicCommand} and invoking {@link #simpleParseAndRun(Command, String[])}, like this:
 * {@listing java import net.sf.japi.io.args.*;
 * public class HelloCommand extends BasicCommand {
 *     public static void main(final String... args) {
 *         ArgParser.simpleParseAndRun(new HelloCommand(), args);
 *     }
 *     public void run(final List<String> args) throws Exception {
 *         System.out.println("Hello, world!");
 *     }
 * }}
 *
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.2
 *
 * @see net.sf.japi.io.args
 * @see Command
 * @see BasicCommand
 * @see ConverterRegistry
 *
 * @todo better handling of boolean arguments
 * @todo Handling of - for STDIN as input argument filestream
 * @todo automatic printout of default values if property getter available.
 * @todo Let the programmer choose whether long options with one dash should be supported (for Ragnor).
 *       If long options with one dash are supported, short option concatenation should be disabled.
 * @todo Abbreviation of long options as long as the abbreviation is unique
 * @todo Alternative way of getting arguments by a callback mechanism which processes single arguments.
 * @todo Move option names into a / the properties file.
 */
public final class ArgParser {

    /** The command to parse arguments to. */
    @NotNull private final Command command;

    /** The command class. */
    @NotNull private final Class<? extends Command> commandClass;

    /** The argument methods. */
    @NotNull private final Map<String, Method> argumentMethods = new HashMap<String, Method>();

    /** The required methods. */
    @NotNull private final Set<Method> requiredMethods = new HashSet<Method>();

    /** The iterator for the arguments. */
    @NotNull private final ListIterator<String> argIterator;

    /** The currently used option. */
    @Nullable private String currentOption;

    /**
     * Create a new ArgParser.
     * This ArgParser uses {@link System#in}, {@link System#out} and {@link System#err}.
     * @param command Command to initialize and run
     * @param args Arguments to parse
     * @throws RequiredOptionsMissingException in case an option is missing
     * @throws TerminalException in case argument parsing was stopped
     * @throws MissingArgumentException in the required argument for an option was missing
     * @throws UnknownOptionException In case an option was specified that's not supported.
     * @throws ArgumentFileNotFoundException in case an argument file was not found.
     */
    private ArgParser(@NotNull final Command command, @NotNull final String... args) throws TerminalException, RequiredOptionsMissingException, UnknownOptionException, MissingArgumentException, ArgumentFileNotFoundException {
        this.command = command;
        commandClass = command.getClass();
        initMethods();
        final List<String> argList = getAllArguments(new File("."), Arrays.asList(args));
        argIterator = argList.listIterator();
        parse();
        checkRequiredMethods();
        int returnCode;
        try {
            returnCode = command.run(argList);
        } catch (final Exception e) {
            System.err.println(e);
            e.printStackTrace();
            returnCode = 1;
        }
        if (command.isExiting()) {
            System.exit(returnCode);
        }
    }

    /**
     * Returns a list of all arguments after parsing arguments files.
     * @param context File relative to which @-inclusions have to be resolved.
     * @param args arguments before parsing argument files.
     * @return all arguments after parsing argument files.
     * @throws ArgumentFileNotFoundException in case an argument file was not found.
     */
    public List<String> getAllArguments(@NotNull final File context, @NotNull final List<String> args) throws ArgumentFileNotFoundException {
        final List<String> argList = new ArrayList<String>(args);
        for (final ListIterator<String> iterator = argList.listIterator(); iterator.hasNext();) {
            final String arg = iterator.next();
            if ("--".equals(arg)) {
                break;
            }
            if (arg.startsWith("@")) {
                iterator.remove();
                if (arg.startsWith("@@")) {
                    iterator.add(arg.substring(1));
                } else {
                    final String filename = arg.substring(1);
                    final File file = new File(context.getParentFile(), filename);
                    for (final String insertArg : getAllArguments(file, readFromFile(file))) {
                        iterator.add(insertArg);
                    }
                }
            }
        }
        return argList;
    }

    /**
     * Returns a tokenized unparsed list of arguments from an arguments file.
     * @param file Argument file to read.
     * @return all arguments from that argument file.
     * @throws ArgumentFileNotFoundException in case an argument file was not found.
     */
    @NotNull public static List<String> readFromFile(@NotNull final File file) throws ArgumentFileNotFoundException {
        final TokenReader in;
        try {
            //noinspection IOResourceOpenedButNotSafelyClosed
            in = new TokenReader(new FileInputStream(file));
        } catch (final FileNotFoundException e) {
            throw new ArgumentFileNotFoundException(e);
        }
        final List<String> args = new ArrayList<String>();
        for (final String token : in) {
            args.add(token);
        }
        return args;
    }

    /**
     * Checks that all required methods have been invoked.
     * @throws RequiredOptionsMissingException in case a required command line argument was missing
     */
    private void checkRequiredMethods() throws RequiredOptionsMissingException {
        if (command.isCheckRequiredOptions() && requiredMethods.size() > 0) {
            final List<String> missingOptions = new ArrayList<String>();
            for (final Method requiredMethod : requiredMethods) {
                final Option option = requiredMethod.getAnnotation(Option.class);
                assert option != null;
                missingOptions.add(option.value()[0]);
            }
            throw new RequiredOptionsMissingException(missingOptions.toArray(new String[missingOptions.size()]));
        }
    }

    /**
     * Determines which methods are argument methods.
     * All argument methods are stored in {@link #argumentMethods}.
     * All required methods are additionally stored in {@link #requiredMethods}.
     */
    private void initMethods() {
        for (final Method method : getOptionMethods(commandClass)) {
            final Option option = method.getAnnotation(Option.class);
            assert option != null;
            final String[] optionNames = option.value();
            if (optionNames.length == 0) {
                throw new IllegalArgumentException(commandClass.getName() + " declared " + method.getName() + " as option but specified no option names");
            }
            final Class<?>[] parameterTypes = method.getParameterTypes();
            final int parameterCount = parameterTypes.length;
            if (parameterCount > 1) {
                throw new IllegalArgumentException("Currently only options with 0 or 1 parameters are supported.");
            }
            for (final String optionName : optionNames) {
                if (optionName == null) {
                    // It's probably impossible to specify a null option name.
                    // But it's safer to check this.
                    throw new NullPointerException("null is not allowed for an option name.");
                }
                if ("".equals(optionName)) {
                    throw new IllegalArgumentException("The empty String is not allowed as option name.");
                }
                if (optionName.startsWith("-")) {
                    throw new IllegalArgumentException("option names must not start with a dash ('-'). The dash is handled automatically by the ArgParser.");
                }
                if ("W".equals(optionName)) {
                    throw new IllegalArgumentException("W is not an allowed option name.");
                }
                if (argumentMethods.containsKey(optionName)) {
                    throw new IllegalArgumentException(commandClass.getName() + " declared option " + optionName + " twice.");
                }
                argumentMethods.put(optionName, method);
            }
            final OptionType type = option.type();
            if (type == OptionType.REQUIRED) {
                requiredMethods.add(method);
            }
        }
    }

    /**
     * Get all option methods from a command.
     * @param command Command to get option methods for
     * @return option methods for the command.
     */
    @NotNull public static Set<Method> getOptionMethods(@NotNull final Command command) {
        return getOptionMethods(command.getClass());
    }

    /**
     * Get all option methods from a command class.
     * @param commandClass Class of the Command to get option methods for
     * @return Option methods for the command class.
     */
    @NotNull public static Set<Method> getOptionMethods(@NotNull final Class<? extends Command> commandClass) {
        final Method[] methods = commandClass.getMethods();
        final Set<Method> optionMethods = new HashSet<Method>();
        for (final Method method : methods) {
            if (method.isAnnotationPresent(Option.class)) {
                optionMethods.add(method);
            }
        }
        return optionMethods;
    }

    /**
     * Parses arguments into an arguments container.
     * @throws TerminalException in case argument parsing was stopped
     * @throws MissingArgumentException In case a required argument was missing.
     * @throws UnknownOptionException In case a given option is not known.
     */
    private void parse() throws TerminalException, UnknownOptionException, MissingArgumentException {
        while (argIterator.hasNext()) {
            final String arg = argIterator.next();
            if (arg.length() > 1 && arg.charAt(0) == '-') {
                argIterator.remove();
                if ("--".equals(arg)) { // '--': stop parsing
                    //noinspection BreakStatement
                    break;
                }
                final boolean doubleDash = arg.charAt(1) == '-';
                if ("-W".equals(arg)) {
                    currentOption = argIterator.next();
                    argIterator.remove();
                } else {
                    currentOption = arg.substring(doubleDash ? 2 : 1);
                }
                final int indexOfEq = currentOption.indexOf('=');
                if (indexOfEq != -1) {
                    argIterator.add(currentOption.substring(indexOfEq + 1));
                    argIterator.previous();
                    currentOption = currentOption.substring(0, indexOfEq);
                }
                if (doubleDash) { // '--foo' option
                    invokeMethod();
                } else { // '-xyz'
                    if (argumentMethods.get(currentOption) != null) { // '-foo' option
                        invokeMethod();
                    } else { // '-abc' options
                        for (final String co : arg.substring(1).split("")) {
                            if (co.length() == 1) {
                                currentOption = co;
                                invokeMethod();
                            }
                        }
                    }
                }
            }
            // empty arguments are intentionally not removed.
        }
    }

    /**
     * Invoke the argument method for the current option.
     * @throws TerminalException in case the invoked exception was terminal
     * @throws UnknownOptionException In case a given option is not known.
     * @throws MissingArgumentException In case the required argument for an option was missing.
     */
    private void invokeMethod() throws TerminalException, UnknownOptionException, MissingArgumentException {
        final Method method = argumentMethods.get(currentOption);
        if (method == null) {
            throw new UnknownOptionException(currentOption);
        }
        if (method.isAnnotationPresent(Obsolete.class)) {
            System.err.println("Warning: option " + currentOption + " is obsolete and thus no longer supported.");
        }
        requiredMethods.remove(method);
        try {
            final Class<?>[] parameterTypes = method.getParameterTypes();
            final int parameterCount = parameterTypes.length;
            if (parameterCount == 1) {
                final String arg = argIterator.next();
                argIterator.remove();
                method.invoke(command, ConverterRegistry.convert(parameterTypes[0], arg));
            } else if (parameterCount == 0) {
                method.invoke(command);
            } else {
                assert false : "Should already be checked in initMethods().";
            }
        } catch (final IllegalAccessException e) {
            System.err.println(e);
        } catch (final InvocationTargetException e) {
            final Throwable cause = e.getCause();
            if (cause instanceof TerminalException) {
                throw (TerminalException) cause;
            }
            System.err.println(e.getCause());
        } catch (final NoSuchElementException ignore) {
            throw new MissingArgumentException(currentOption);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        if (method.getAnnotation(Option.class).type() == OptionType.TERMINAL) {
            throw new TerminalException();
        }
    }

    /**
     * Parses arguments of a command and runs that command.
     * @param command Command to run
     * @param args Arguments to parse
     */
    public static void simpleParseAndRun(@NotNull final Command command, @NotNull final String... args) {
        try {
            parseAndRun(command, args);
        } catch (final TerminalException ignore) {
            /* ignore, nothing serious has happend. */
        } catch (final RequiredOptionsMissingException e) {
            System.err.println(e);
        } catch (final UnknownOptionException e) {
            System.err.println(e);
        } catch (final MissingArgumentException e) {
            System.err.println(e);
        } catch (final ArgumentFileNotFoundException e) {
            System.err.println(e);
        }
    }

    /**
     * Parses arguments of a command and runs that command.
     * @param command Command to run
     * @param args Arguments to parse
     * @throws RequiredOptionsMissingException in case one or more required options were missing.
     * @throws TerminalException in case a terminal option was encountered.
     * @throws UnknownOptionException in case an option given was not known.
     * @throws MissingArgumentException in case an option was missing its argument
     * @throws ArgumentFileNotFoundException in case an argument file was not found.
     */
    public static void parseAndRun(@NotNull final Command command, @NotNull final String... args) throws RequiredOptionsMissingException, TerminalException, UnknownOptionException, MissingArgumentException, ArgumentFileNotFoundException {
        new ArgParser(command, args);
    }

} // class ArgParser
