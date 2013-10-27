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

package net.sf.japi.log;

import static java.lang.Class.forName;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.LogManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The LoggerFactory is used to instanciate a Logger of a specific implementation.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public final class LoggerFactory {

    /** Singleton Instance. */
    @NotNull public static final LoggerFactory INSTANCE = new LoggerFactory();

    /**
     * The implementations.
     * Key: logger name, as with {@link LogManager#getLogger(String)}.
     * Value: Logger associated with the Key
     */
    @NotNull private final Map<String, Logger> loggers = new HashMap<String, Logger>();

    /**
     * Private Singleton Constructor.
     */
    private LoggerFactory() {
    }

    /**
     * Method to find a named logger.
     * @param name Name of the logger to find
     * @return matching logger (a new logger will be created if none could be found)
     * @throws LogConfigurationError in case the logger couldn't be created
     */
    @NotNull public Logger getLogger(final String name) throws LogConfigurationError {
        Logger logger = loggers.get(name);
        if (logger == null) {
            logger = createLogger(name);
            loggers.put(name, logger);
        }
        return logger;
    }

    /**
     * Convenience method to find a named logger.
     * This is the same as calling {@link #getLogger(String)} with <code>c.getName()</code> as argument.
     * @param c Class name of the Logger to find
     * @return matching logger (a new logger will be created if none could be found)
     * @throws LogConfigurationError in case the logger couldn't be created
     */
    @NotNull public Logger getLogger(final Class c) throws LogConfigurationError {
        return getLogger(c.getName());
    }

    /**
     * Creates a Logger.
     * @param name Name for the logger to create
     * @return matching logger
     * @throws LogConfigurationError in case the logger couldn't be created
     */
    @NotNull private Logger createLogger(final String name) throws LogConfigurationError {
        // 1 Find configuration for name
        // 1a Find explicit configuration for name
        // 1b Use default configuration for name
        // 2 Use configuration to instanciate Logger
        final String loggerClassName = getLoggerClassName(name);
        try {
            //noinspection unchecked
            final Class<? extends Logger> loggerClass = (Class<? extends Logger>) forName(loggerClassName);
            final Constructor<? extends Logger> constructor = loggerClass.getConstructor(String.class);
            return constructor.newInstance(name);
        } catch (final InstantiationException e) {
            throw new LogConfigurationError("Logger class not instantiatable.", e);
        } catch (final IllegalAccessException e) {
            throw new LogConfigurationError("Logger class or it's constructor not accessible.", e);
        } catch (final ClassNotFoundException e) {
            throw new LogConfigurationError("Logger class not found while instantiating logger.", e);
        } catch (final ClassCastException e) {
            throw new LogConfigurationError("Specified Logger class doesn't implement net.sf.japi.log.Logger interface.", e);
        } catch (final ExceptionInInitializerError e) {
            throw new LogConfigurationError("Exception in Initializer during Logger instantiation.", e.getException());
        } catch (final NoSuchMethodException e) {
            throw new LogConfigurationError("Specified Logger class doesn't provide a constructor of type (String).", e);
        } catch (final InvocationTargetException e) {
            throw new LogConfigurationError("Exception in Constructor during Logger instantiation.", e.getTargetException());
        } catch (final IllegalArgumentException e) {
            // This shouldn't happen because if we get a constructor of type String and then use it of type String there shouldn't be any problems.
            assert false;
            throw new Error(e);
        }
    }

    /**
     * Finds the class name of the logger that should be used for a specific name.
     * @param name Name of the logger the class name should be found for
     * @return class name of the logger for <var>name</var>
     */
    @NotNull private String getLoggerClassName(final String name) {
        // Currently, simply return the system property
        return System.getProperty("net.sf.japi.Logger", "net.sf.japi.Logger");
    }

} // class LogFactory
