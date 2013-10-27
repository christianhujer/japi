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

package test.net.sf.japi.swing.prefs;

import java.util.HashMap;
import java.util.Map;
import javax.swing.JTextField;
import net.sf.japi.swing.prefs.AbstractPrefs;
import net.sf.japi.swing.prefs.Prefs;
import org.jetbrains.annotations.NotNull;

/** MockPrefs is a {@link Prefs} implementation for testing.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @todo Extract invocation counter. This probably would be a good use case for AOP.
 */
public class MockPrefs extends AbstractPrefs {

    /** Invocation Counter.
     * @serial include
     */
    private final Map<String, int[]> invocationCounter = new HashMap<String, int[]>();

    /** The Textfield for changes.
     * @serial include
     */
    private final JTextField textfield = new JTextField();

    /** The text for changes.
     * @serial include
     */
    private String text = "";

    /** Creates MockPrefs. */
    public MockPrefs() {
        // Initialize the invocation counter.
        final String[] keys = { "apply", "defaults", "isChanged", "revert" };
        for (final String key : keys) {
            invocationCounter.put(key, new int[1]);
        }
        add(textfield);
    }

    /** {@inheritDoc} */
    public void apply() {
        increment("apply");
        text = textfield.getText();
    }

    /** {@inheritDoc} */
    public void defaults() {
        increment("defaults");
        text = "";
        textfield.setText(text);
    }

    /** {@inheritDoc} */
    public boolean isChanged() {
        increment("isChanged");
        return !text.equals(textfield.getText());
    }

    /** {@inheritDoc} */
    public void revert() {
        increment("revert");
        textfield.setText(text);
    }

    /** Increments an invocation count.
     * @param key Key to increment.
     */
    protected void increment(@NotNull final String key) {
        invocationCounter.get(key)[0]++;
    }

    /** Resets an invocation count.
     * @param key Key to reset.
     */
    public void reset(@NotNull final String key) {
        invocationCounter.get(key)[0] = 0;
    }

    /** Resets all invocation counts. */
    public void resetAll() {
        for (final int[] val : invocationCounter.values()) {
            val[0] = 0;
        }
    }

    /** Returns the text of this preferences.
     * @return The text of this preferences.
     */
    public String getText() {
        return text;
    }

    /** Sets the text of this preferences.
     * @param text The text of this preferences.
     */
    public void setText(final String text) {
        this.text = text;
    }

    /** Returns the textfield of this preferences.
     * @return The textfield of this preferences.
     */
    public JTextField getTextfield() {
        return textfield;
    }

} // class MockPrefs
