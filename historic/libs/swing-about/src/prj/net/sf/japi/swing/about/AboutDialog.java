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

package net.sf.japi.swing.about;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.TreeSet;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import net.sf.japi.swing.action.ActionBuilder;
import net.sf.japi.swing.action.ActionBuilderFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Class for constructing and showing About dialogs.
 *
 * It features:
 * <ul>
 *  <li>logo</li>
 *  <li>about text - a descriptive text, maybe HTML, for instance to list the developers</li>
 *  <li>Showing the developer that built the software, the version, the date built</li>
 *  <li>All runtime properties</li>
 *  <li>All licenses the software uses</li>
 * </ul>
 *
 * It requires the following properties to be available in the named ActionBuilder:
 * <ul>
 *  <li><code>about.logo</code> specifies the logo to display (optional)</li>
 *  <li><code>application.name</code> specifies the name of the application for the title of the about dialog (required)</li>
 *  <li>
 *      <code>about</code> specifies the format string for the about text in the about tab.
 *      It takes the following parameters:
 *      <ol>
 *          <li><code>{0}</code>: The runtime's Java Version</li>
 *          <li><code>{1}</code>: The build number (see build resource bundle below)</li>
 *          <li><code>{2}</code>: The build developer (see build resource bundle below)</li>
 *          <li><code>{3}</code>: The build timestamp (see build resource bundle below)</li>
 *      </ol>
 *      (See information on the build for how to specify the build information)
 *      Hint: Use Swing HTML 3.2 if you want formatted text (tables etc.).
 *  </li>
 *  <li>
 *      Licenses:
 *      <ul>
 *          <li><code>license.<var>n</var>.title</code> specifies the title for license <var>n</var></li>
 *          <li><code>license.<var>n</var>.file</code> specifies the file for license <var>n</var></li>
 *          <li><var>n</var> starts with 1 and must be ascending without skips.</li>
 *      </ul>
 *  </li>
 *  <li><code>aboutBuildProperties.title</code> specifies the title for the build properties tab (optional - only required if the build properties tab is used)</li>
 *  <li><code>aboutRuntimeProperties.title</code> specifies the title for the runtime properties tab (required)</li>
 * </ul>
 *
 * <h2>build resource bundle</h2>
 * The information on the build is specified by:
 * <ul>
 *  <li>
 *      A ResourceBundle <code>build</code> (optional, e.g. <code>build.properties</code> toplevel on classpath).
 *      All properties in that bundle are going to be displayed on the build tab.
 *      The following properties have special meaning and are message format parameters to about (see above):
 *      <ul>
 *          <li>
 *              <code>build.developer</code> The developer that made the build (optional).
 *              Note: This is meant to be the developer that performed the build, not the developers that developed the software.
 *              Use the <code>about</code> property described above to specify copyright / authors / contributors / developers.
 *          </li>
 *          <li><code>build.number</code> The build number, e.g. incremented by Ant or the Subversion revision (optional)</li>
 *          <li><code>build.tstamp</code> The timestamp when the build was made (optional)</li>
 *      </ul>
 *  </li>
 * </ul>
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @author Andreas Kirschbaum
 * @since 0.1
 * @todo Allow configuring the build information
 * @todo Allow reading the information from a specified bundle instead of an ActionBuilder.
 */
public class AboutDialog extends JPanel {

    /** Action Builder to create Actions. */
    @NotNull protected static final ActionBuilder ACTION_BUILDER = ActionBuilderFactory.getInstance().getActionBuilder("net.sf.japi.swing.about");

    /** Default buffer size for I/O, e.g. when reading a license. */
    private static final int BUF_SIZE = 4096;

    /** Default number of rows in a textarea in an AboutDialog. */
    private static final int TEXT_ROWS = 16;

    /** Default number of columns in a textarea in an AboutDialog. */
    private static final int TEXT_COLUMNS = 80;

    /** Action Builder of the application, used to retrieve application logo, name and licenses.
     * @serial include
     */
    @NotNull private final ActionBuilder actionBuilder;

    /**
     * The main tabs.
     * @serial include
     */
    @NotNull private final JTabbedPane tabs;

    /**
     * The tabs in the license tab.
     * @serial include
     */
    @NotNull private final JTabbedPane licensePane;

    /**
     * Create an AboutDialog.
     * @param actionBuilder ActionBuilder to use.
     */
    public AboutDialog(@NotNull final ActionBuilder actionBuilder) {
        super(new BorderLayout());
        this.actionBuilder = actionBuilder;
        tabs = new JTabbedPane();
        licensePane = buildLicenseTab();
        final String logoURLString = actionBuilder.getString("about.logo");
        if (logoURLString != null) {
            final URL logoURL = getClass().getClassLoader().getResource(logoURLString);
            if (logoURL != null) {
                add(new JLabel(new ImageIcon(logoURL)), BorderLayout.NORTH);
            } else {
                System.err.println("Warning: Logo for about dialog (\"" + logoURLString + "\") not found.");
            }
        }
        add(tabs);
        addTab(buildAboutTab());
        if (licensePane.getComponentCount() > 0) {
            addTab(licensePane);
        }
        addTab(buildRuntimePropertiesTab());
        addTab(buildBuildPropertiesTab());
    }

    /**
     * Create an AboutDialog.
     * @param actionBuilderName Name of the ActionBuilder to use.
     */
    public AboutDialog(@NotNull final String actionBuilderName) {
        this(ActionBuilderFactory.getInstance().getActionBuilder(actionBuilderName));
    }

    /**
     * Show about dialog.
     * @param parent Parent component to show dialog on
     */
    public void showAboutDialog(@Nullable final Component parent) {
        tabs.setSelectedIndex(0);
        if (licensePane.getComponentCount() > 0) {
            licensePane.setSelectedIndex(0);
        }
        JOptionPane.showMessageDialog(parent, this, ACTION_BUILDER.format("about.title", actionBuilder.getString("application.name")), JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Adds a tab.
     * @param component Component to add as tab
     */
    public final void addTab(@NotNull final Component component) {
        tabs.add(component);
    }

    /**
     * Build the license tab.
     * @return component for license tab
     */
    @NotNull private JTabbedPane buildLicenseTab() {
        final JTabbedPane licensePane = new JTabbedPane();
        for (int i = 1; actionBuilder.getString("license." + i + ".title") != null; i++) {
            licensePane.add(buildLicenseSubTab(String.valueOf(i)));
        }
        licensePane.setName(ACTION_BUILDER.getString("license.title"));
        return licensePane;
    }

    /**
     * Build a XY license tab.
     * @param number number of license
     * @return component for XY license tab
     */
    @NotNull private Component buildLicenseSubTab(@NotNull final String number) {
        String licenseText;
        //noinspection ProhibitedExceptionCaught
        try {
            final Reader in = new InputStreamReader(new BufferedInputStream(getClass().getClassLoader().getResource(actionBuilder.getString("license." + number + ".file")).openStream()));
            try {
                final StringBuilder sb = new StringBuilder();
                final char[] buf = new char[BUF_SIZE];
                //noinspection NestedAssignment
                for (int bytesRead; (bytesRead = in.read(buf)) != -1;) {
                    sb.append(buf, 0, bytesRead);
                }
                licenseText = sb.toString();
            } finally {
                in.close();
            }
        } catch (final NullPointerException ignore) {
            licenseText = ACTION_BUILDER.getString("license.missing");
        } catch (final IOException ignore) {
            licenseText = ACTION_BUILDER.getString("license.missing");
        }
        final JTextArea license = new JTextArea(licenseText, TEXT_ROWS, TEXT_COLUMNS);
        license.setLineWrap(true);
        license.setWrapStyleWord(true);
        license.setEditable(false);
        license.setFont(new Font("Monospaced", Font.PLAIN, license.getFont().getSize()));
        final Component scroller = new JScrollPane(license);
        scroller.setFocusable(true);
        scroller.setName(actionBuilder.getString("license." + number + ".title"));
        return scroller;
    }

    /** Gets a String from a bundle.
     * @param bundle Bundle to get String from.
     * @param key Key for which to get the String.
     * @return String from the bundle or <code>null</code> if not available.
     */
    @Nullable private String getStringFromBundle(@NotNull final ResourceBundle bundle, @NotNull final String key) {
        try {
            return bundle.getString(key);
        } catch (final MissingResourceException ignore) {
            /* ignore */
        } catch (final ClassCastException ignore) {
            System.err.println("Internal error: value type for key " + key + " in " + bundle + " must be String.");
        }
        return null;
    }

    /**
     * Build the about tab.
     * @return component for about tab
     */
    @NotNull private Component buildAboutTab() {
        String buildNumber = ACTION_BUILDER.getString("unknown");
        String buildDeveloper = ACTION_BUILDER.getString("unknown");
        String buildTstamp = ACTION_BUILDER.getString("unknown");
        try {
            final ResourceBundle bundle = ResourceBundle.getBundle("build");
            buildDeveloper = getStringFromBundle(bundle, "build.developer");
            buildNumber    = getStringFromBundle(bundle, "build.number");
            buildTstamp    = getStringFromBundle(bundle, "build.tstamp");
        } catch (final MissingResourceException ignore) {
            /* ignore */
        }
        // use a JEditorPane which looks like a JLabel to allow copying its content
        final JEditorPane editorPane = new JEditorPane("text/html", actionBuilder.format("about", System.getProperty("java.version"), buildNumber, buildDeveloper, buildTstamp));
        editorPane.setEditable(false);
        editorPane.setBorder(null);
        editorPane.setForeground(UIManager.getColor("Label.foreground"));
        editorPane.setFont(UIManager.getFont("Label.font"));
        editorPane.setOpaque(false);
        final JPanel aboutTab = new JPanel(new GridBagLayout());
        final GridBagConstraints gbc = new GridBagConstraints();
        aboutTab.add(editorPane, gbc);
        aboutTab.setName(ACTION_BUILDER.getString("aboutTab.title"));
        return aboutTab;
    }

    /**
     * Build the runtime properties tab.
     * @return component for runtime properties tab
     */
    @NotNull private Component buildRuntimePropertiesTab() {
        final StringBuilder propertiesText = new StringBuilder();
        final Collection<String> keys = new TreeSet<String>();
        try {
            final Properties props = System.getProperties();
            for (final Object key : props.keySet()) {
                if (key instanceof String) {
                    keys.add((String) key);
                }
            }
            for (final String key : keys) {
                propertiesText
                        .append(key)
                        .append('=')
                        .append(props.getProperty(key))
                        .append('\n');
            }
        } catch (final SecurityException e) {
            propertiesText.append("<html>Properties unavailable. Reason:<p>").append(e);
        }
        final JTextArea properties = new JTextArea(propertiesText.toString(), TEXT_ROWS, TEXT_COLUMNS);
        properties.setEditable(false);
        properties.setFont(new Font("Monospaced", Font.PLAIN, properties.getFont().getSize()));
        final Component scroller = new JScrollPane(properties);
        scroller.setName(ACTION_BUILDER.getString("aboutRuntimeProperties.title"));
        scroller.setFocusable(true);
        return scroller;
    }

    /**
     * Build the build properties tab.
     * @return component for build properties tab
     */
    @NotNull private Component buildBuildPropertiesTab() {
        final StringBuilder propertiesText = new StringBuilder();
        final Collection<String> keys = new TreeSet<String>();
        try {
            final ResourceBundle bundle = ResourceBundle.getBundle("build");
            for (final Enumeration<String> uKeys = bundle.getKeys(); uKeys.hasMoreElements();) {
                keys.add(uKeys.nextElement());
            }
            for (final String key : keys) {
                try {
                    propertiesText
                            .append(key)
                            .append('=')
                            .append(bundle.getString(key))
                            .append('\n');
                } catch (final ClassCastException ignore) {
                    System.err.println("Internal error: expecting build properties to be Strings, but property for key " + key + " was not a String.");
                }
            }
        } catch (final MissingResourceException e) {
            propertiesText.append(e.toString());
        }
        final JTextArea properties = new JTextArea(propertiesText.toString(), TEXT_ROWS, TEXT_COLUMNS);
        properties.setEditable(false);
        properties.setFont(new Font("Monospaced", Font.PLAIN, properties.getFont().getSize()));
        final Component scroller = new JScrollPane(properties);
        scroller.setName(ACTION_BUILDER.getString("aboutBuildProperties.title"));
        scroller.setFocusable(true);
        return scroller;
    }

    /** Returns the ActionBuilder to create actions.
     * @return The ActionBuilder to create actions.
     */
    @NotNull protected ActionBuilder getActionBuilder() {
        return actionBuilder;
    }

} // class AboutDialog
