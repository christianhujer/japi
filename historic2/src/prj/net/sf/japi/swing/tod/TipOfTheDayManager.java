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

package net.sf.japi.swing.tod;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Random;
import java.util.ResourceBundle;
import static java.util.ResourceBundle.getBundle;
import java.util.prefs.Preferences;
import static java.util.prefs.Preferences.userNodeForPackage;
import javax.swing.Action;
import static javax.swing.Action.ACCELERATOR_KEY;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import static javax.swing.SwingConstants.TRAILING;
import net.sf.japi.swing.action.ActionBuilder;
import static net.sf.japi.swing.action.ActionBuilder.ACCELERATOR_KEY_2;
import net.sf.japi.swing.action.ActionBuilderFactory;
import net.sf.japi.swing.action.ActionMethod;
import static net.sf.japi.swing.action.IconManager.getDefaultIconManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** Class that manages tips of the day.
 * The tips of the day are read from a property file.
 * The name of the property file is tried to retrieve in exactly the following order:
 * <ol>
 *  <li>The System Property <code>net.sf.japi.swing.tod</code> is queried and taken as a ResourceBundle base name.</li>
 *  <li>The Service file <code>META-INF/services/net.sf.japi.swing.tod</code> is read and its first line taken as a ResourceBundle base name.</li>
 * </ol>
 * If both fails, the behaviour is undefined.
 * <p/>
 * Setting the ResourceBundle for the TipOfTheDayManager via services is the preferred way, because you do not need any additional coding apart from
 * invoking the TipOfTheDayManager somewhere at startup.
 * <p />
 * The format of that property file follows the normal Java Properties convention, with the property keys being numbered, starting at "tod.text.1".
 * Example:
 * <pre># Tip Of The Days, English Version
 * tod.text.1=&lt;html&gt;For analysis with other tools you can export the symbol map to XML, MS-Excel and CSV.
 * tod.text.2=&lt;html&gt;For analysis with other tools you can export the mapping map to XML, MS-Excel and CSV.
 * tod.text.3=&lt;html&gt;The supported map file formats are: Intel, GCC and MSVC.
 * </pre>
 * @fixme The preferences properties lastTipOfTheDayNumber and showTipOfTheDayAtStartup are stored in the wrong package, they must be stored in the client package instead of this package
 * @todo Allow parametrization of properties, e.g. via a String sequence like <code>${property.name}</code>, which should then be looked up using
 * a defined scheme from one or perhaps more definable {@link ActionBuilder ActionBuilders}.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 * @serial exclude
 */
public final class TipOfTheDayManager extends JOptionPane {

    /** Action Builder. */
    @NotNull private static final ActionBuilder ACTION_BUILDER = ActionBuilderFactory.getInstance().getActionBuilder("net.sf.japi.swing.tod");

    /** Random number generator for random tods. */
    @NotNull private static final Random RND = new Random();

    /** Preferences. */
    @NotNull private static final Preferences PREFS = userNodeForPackage(TipOfTheDayManager.class);

    /** The Action keys used for accelerators. */
    @NotNull private static final String[] ACCELERATOR_KEYS = { ACCELERATOR_KEY, ACCELERATOR_KEY_2 };

    /** The static instance. */
    @NotNull private static final TipOfTheDayManager INSTANCE = new TipOfTheDayManager();

    /** Width of the scroll region. */
    private static final int SCROLLER_WIDTH = 512;

    /** Height of the scroll region. */
    private static final int SCROLLER_HEIGHT = 128;

    /** Scale of the heading font. */
    private static final double HEADING_SCALE = 1.5;

    /** JButton for close. */
    @NotNull private JButton closeButton;

    /** JDialog. */
    @Nullable private JDialog dialog;

    /** JCheckBox for showing at startup. */
    @NotNull private final JCheckBox showAtStartup = new JCheckBox(ACTION_BUILDER.createAction(false, "todShowAtStartup", null));

    /** The JLabel showing the current number of the tod. */
    @NotNull private final JLabel currentTodIndex = new JLabel();

    /** The JEditorPane displaying the tod text. */
    @NotNull private final JEditorPane todText = new JEditorPane("text/html", "");

    /** List with tod texts. */
    @NotNull private final List<String> todTexts = new ArrayList<String>();

    /** Number of current Tip of the day.
     * In visible state, the index must range between 0 and the number of tods available - 1, inclusive.
     */
    private int todIndex;

    /** Show Tip Of The Day at startup.
     * This method is only a proxy for show(Component) but looks at user preferences.
     * If the user chose not to see TipOfTheDays this method simply returns.
     * @param parentComponent  the parent component of this dialog.
     */
    public static void showAtStartup(@Nullable final Component parentComponent) {
        if (PREFS.getBoolean("showTipOfTheDayAtStartup", true)) {
            show(parentComponent);
        }
    }

    /** Show a Tip Of The Day.
     * @param parentComponent  the parent component of this dialog.
     */
    public static void show(@Nullable final Component parentComponent) {
        if (INSTANCE.dialog == null) {
            INSTANCE.dialog = INSTANCE.createDialog(parentComponent, ACTION_BUILDER.getString("tipOfTheDay.windowTitle"));
        }
        INSTANCE.dialog.getRootPane().setDefaultButton(INSTANCE.closeButton);
        INSTANCE.dialog.setModal(false);
        INSTANCE.dialog.setResizable(true);
        INSTANCE.dialog.setVisible(true);
        INSTANCE.closeButton.requestFocusInWindow();
    }

    /** Finds the bundle name.
     * @return bundle name
     */
    @Nullable private static String getBundleName() {
        String bundleName = System.getProperty("net.sf.japi.swing.tod");
        if (bundleName == null) {
            bundleName = getServiceValue(getClassLoader());
        }
        return bundleName;
    }

    /** This method attempts to return the first line of the resource META_INF/services/net.sf.japi.swing.tod from the provided ClassLoader.
     * @param classLoader ClassLoader, may not be <code>null</code>.
     * @return first line of resource, or <code>null</code>
     */
    @Nullable private static String getServiceValue(@Nullable final ClassLoader classLoader) {
        final String serviceId = "META-INF/services/net.sf.japi.swing.tod";
        final InputStream in = getResourceAsStream(classLoader, serviceId);
        if (in != null) {
            try {
                final BufferedReader rd = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                try {
                    return rd.readLine();
                } finally {
                    rd.close();
                }
            } catch (final IOException ignore) {
                /* ignore. */
            }
        }
        return null;
    }

    /** Get a resource from a classloader as stream.
     * @param classLoader ClassLoader to get resource from
     * @param serviceId service file to get stream from
     * @return stream for resource <var>serviceId</var> in <var>classLoader</var>
     */
    @Nullable private static InputStream getResourceAsStream(@Nullable final ClassLoader classLoader, @NotNull final String serviceId) {
        return AccessController.doPrivileged(new PrivilegedAction<InputStream>() {

            /** {@inheritDoc} */
            public InputStream run() {
                return classLoader == null
                    ? ClassLoader.getSystemResourceAsStream(serviceId)
                    : classLoader.getResourceAsStream(serviceId);
            }

        });
    }

    /** Get the ClassLoader.
     * @return ClassLoader
     */
    @Nullable private static ClassLoader getClassLoader() {
        try {
            final ClassLoader contextClassLoader = getContextClassLoader();
            if (contextClassLoader != null) {
                return contextClassLoader;
            }
        } catch (final Exception ignore) {
            /* ignore */
        }
        return TipOfTheDayManager.class.getClassLoader();
    }

    /** Get the context ClassLoader.
     * @return context ClassLoader
     */
    @Nullable private static ClassLoader getContextClassLoader() {
        return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {

            /** {@inheritDoc} */
            @Nullable public ClassLoader run() {
                try {
                    return Thread.currentThread().getContextClassLoader();
                } catch (final SecurityException ignore) {
                    return null;
                }
            }

        });
    }

    /** Constructor. */
    private TipOfTheDayManager() {
        final String[] keys = { "todPrev", "todRand", "todNext", "todClose" };
        final Action[] actions = new Action[keys.length];
        final Object[] newOptions = new Object[keys.length];
        for (int i = 0; i < keys.length; i++) {
            actions[i] = ACTION_BUILDER.createAction(false, keys[i], this);
            newOptions[i] = new JButton(actions[i]);
            if ("todClose".equals(keys[i])) {
                closeButton = (JButton) newOptions[i];
            }
            for (final String key : ACCELERATOR_KEYS) {
                final KeyStroke ks = (KeyStroke) actions[i].getValue(key);
                if (ks != null) {
                    getInputMap(WHEN_IN_FOCUSED_WINDOW).put(ks, keys[i]);
                    getActionMap().put(keys[i], actions[i]);
                }
            }
        }
        final Dimension size = new Dimension(SCROLLER_WIDTH, SCROLLER_HEIGHT);
        showAtStartup.setSelected(PREFS.getBoolean("showTipOfTheDayAtStartup", true));
        final JScrollPane todTextScroller = new JScrollPane(todText);
        todTextScroller.setMinimumSize(size);
        todTextScroller.setPreferredSize(size);
        todTextScroller.setFocusable(true);
        todTextScroller.setAutoscrolls(true);
        todText.setEditable(false);
        todText.setRequestFocusEnabled(false);
        currentTodIndex.setHorizontalAlignment(TRAILING);
        loadTodTexts();
        setTodIndex(PREFS.getInt("lastTipOfTheDayNumber", -1) + 1);
        setIcon(getDefaultIconManager().getIcon(ACTION_BUILDER.getString("tipOfTheDay.icon")));
        final JLabel heading = new JLabel(ACTION_BUILDER.getString("todHeading"));
        final Font oldFont = heading.getFont();
        heading.setFont(oldFont.deriveFont((float) (oldFont.getSize2D() * HEADING_SCALE)));
        setMessage(new Object[] { heading, todTextScroller, currentTodIndex, showAtStartup });
        setMessageType(INFORMATION_MESSAGE);
        setOptions(newOptions);
    }

    /** Loads the Tip of the day texts. */
    private void loadTodTexts() {
        final ResourceBundle todBundle = getBundle(getBundleName());
        for (int i = 1;; i++) {
            try {
                todTexts.add(todBundle.getString(new StringBuilder().append("tod.text.").append(i).toString()));
            } catch (final MissingResourceException ignore) {
                break;
            }
        }
    }

    /** Sets the tod index.
     * @param todIndex new todIndex
     */
    private void setTodIndex(final int todIndex) {
        try {
            this.todIndex = (todIndex + todTexts.size()) % todTexts.size();
            todText.setText(todTexts.get(this.todIndex));
            currentTodIndex.setText(ACTION_BUILDER.format("todIndex", this.todIndex + 1, todTexts.size()));
        } catch (final ArithmeticException ignore) {
            todText.setText(ACTION_BUILDER.getString("todsUnavailable"));
            currentTodIndex.setText("");
        }
    }

    /** Returns number of current Tip of the day.
     * In visible state, the index must range between 0 and the number of tods available - 1, inclusive.
     * @return number of current Tip of the day.
     */
    public int getTodIndex() {
        return todIndex;
    }

    /** Action method for close. */
    @ActionMethod public void todClose() {
        setValue(closeButton);
        PREFS.putBoolean("showTipOfTheDayAtStartup", showAtStartup.isSelected());
        PREFS.putInt("lastTipOfTheDayNumber", todIndex);
        final Dialog dialog = this.dialog;
        if (dialog != null) {
            dialog.setVisible(false);
            dialog.dispose();
            this.dialog = null;
        }
    }

    /** Action method for next. */
    @ActionMethod
    public void todNext() {
        setTodIndex(todIndex + 1);
    }

    /** Action method for previous. */
    @ActionMethod public void todPrev() {
        setTodIndex(todIndex - 1);
    }

    /** Action method for random. */
    @ActionMethod public void todRand() {
        setTodIndex(RND.nextInt(todTexts.size()));
    }

} // class TipOfTheDayManager
