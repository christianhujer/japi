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

/**
 * ArgParser is a library for parsing command line arguments.
 * <p/>
 * It supports short options (e.g. <samp>-h</samp>) and long options (e.g. <samp>-help</samp> or <samp>--help</samp>).
 * <p/>
 * If an option takes a parameter, the argument value may follow the option as separate argument.
 * If the option is a long option, the value may alternatively be included in the argument string using the <code>'='</code>-character.
 * For instance, if an option has short name <samp>i</samp> and long name <samp>in</samp> and the option takes a String argument, the following variants are possible:
 * <samp>-i foo</samp>, <samp>-in foo</samp>, <samp>--in foo</samp>, <samp>-in=foo</samp> and <samp>--in=foo</samp>.
 * <p/>
 * Short options may be given in the same String.
 * For instance, if there are the short options <samp>v</samp>, <samp>i</samp> and <samp>s</samp>, all of them may be given together in a single String.
 * That means the following variants are possible:
 * <samp>-vis</samp>, <samp>-v -i -s</samp> and any combination of separated and concatenated versions like <samp>-v -is</samp>.
 * If short options take argument values and they are concatenated, the argument values are separate strings following the concatenated option in exactly the order of the concatenated options.
 * In <samp>-io foo bar</samp>, given that both, <samp>i</samp> and <samp>o</samp> take an argument value, <samp>foo</samp> is value for <samp>i</samp>, while <samp>bar</samp> is value for <samp>o</samp>.
 * <p/>
 * If there is an ambiguity between concatenated short options and a long option of the same name, the long option takes precedence.
 * Users be warned: this ambiguity may cause trouble to you if you use programs in batches and then update them.
 * One day -xyz means -x -y -z, the other day after updating the program introduced long option xyz, -xyz means --xyz.
 * <p/>
 * The special option <code>--</code> will stop argument parsing.
 * All strings that follow the <code>--</code>-option are treated as command arguments, not options, even if they start with <code>-</code>.
 *
 * <h3>Usage Pattern</h3>
 * The ArgParser library is built upon the JavaBeans concept.
 * A command is a JavaBean, the command's options are bean properties.
 * The corresponding setter method, usually a setter, needs to be marked as {@link net.sf.japi.io.args.Option}.
 * <ul>
 *  <li>
 *      Create a class which shall be the command.
 *      Make that class implement {@link net.sf.japi.io.args.Command}.
 *  </li>
 *  <li>
 *      Write a method for each option.
 *      Annotate that method with {@link net.sf.japi.io.args.Option}.
 *  </li>
 *  <li>
 *      Add a main method which passes an instance of your command class to {@link net.sf.japi.io.args.ArgParser#simpleParseAndRun(net.sf.japi.io.args.Command, String...)}.
 *  </li>
 * </ul>
 *
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.2
 */
package net.sf.japi.io.args;
