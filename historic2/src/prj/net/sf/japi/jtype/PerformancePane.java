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

package net.sf.japi.jtype;

import javax.swing.JComponent;
import javax.swing.JButton;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.FlowLayout;
import java.awt.Component;
import java.util.Collection;
import java.util.ArrayList;
import net.sf.japi.swing.action.ActionBuilder;
import net.sf.japi.swing.action.ActionBuilderFactory;
import net.sf.japi.swing.action.ActionMethod;

/** Display for simple typing statistics.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class PerformancePane extends JComponent implements KeyListener {

    /** The ActionBuilder with which the Actions will be built. */
    private static final ActionBuilder ACTION_BUILDER = ActionBuilderFactory.getInstance().getActionBuilder(PerformancePane.class);

    /** Default delay after which a keystoke will be treated as new key after a pause. */
    private static final int DEFAULT_PAUSE_DETECTION_DELAY = 2000;

    /** Delay after which a keystroke will be treated as new key after a pause.
     * This allows users to pause their excercises without having to explicitely stop the program.
     * @serial include
     */
    private final int pauseDetectionDelay = DEFAULT_PAUSE_DETECTION_DELAY;

    /** The number of keys typed correctly.
     * @serial include
     */
    private int keyTypes;

    /** The number of keys typed wrongly.
     * @serial include
     */
    private int correctionTypes;

    /** The timestamp of the previous keystroke.
     * @serial include
     */
    private long lastTime;

    /** The sum of time all keystrokes took alltogether so far.
     * @serial include
     */
    private long timeSums;

    /** Reflection fields.
     * @serial include
     */
    private final Collection<ReflectionField> reflectionFields = new ArrayList<ReflectionField>();

    /** Creates a PerformancePane. */
    public PerformancePane() {
        setLayout(new FlowLayout());
        reflectionFields.add(new ReflectionField("Speed", "%3f", this, "speed"));
        reflectionFields.add(new ReflectionField("Correctness", "%3.2f %%", this, "correctness"));
        for (final Component reflectionField : reflectionFields) {
            add(reflectionField);
        }
        updateDisplay();
        ACTION_BUILDER.createAction(true, "reset", this);
        final Component button = new JButton(ACTION_BUILDER.getAction("reset"));
        button.setFocusable(false);
        add(button);
    }

    /** Resets the statistics of this PerformancePane. */
    @ActionMethod
    public void reset() {
        keyTypes = 0;
        timeSums = 0;
        correctionTypes = 0;
        lastTime = 0;
        updateDisplay();
    }

    /** Updates the displayed statistics. */
    public void updateDisplay() {
        for (final ReflectionField fieldLabelHandler : reflectionFields) {
            fieldLabelHandler.update();
        }
    }

    /** Returns the speed.
     * @return The speed.
     */
    public double getSpeed() {
        return keyTypes * 1000.0 * 60 / timeSums;
    }

    /** Returns the correctness.
     * @return The correctness.
     */
    public double getCorrectness() {
        return keyTypes * 100.0 / (keyTypes + correctionTypes);
    }

    /** Returns the speed of typing, measured in keystrokes per minute.
     * @return The speed of typing, measured in keystrokes per minute.
     */
    public double getTypingSpeedInKeystrokesPerMinute() {
        return keyTypes * 1000.0 * 60 / timeSums;
    }

    /** {@inheritDoc} */
    public void keyTyped(final KeyEvent e) {
        final long time = System.currentTimeMillis();
        final long deltaTime = time - lastTime;
        if (deltaTime > pauseDetectionDelay) {
            lastTime = 0;
        }
        if (e.getKeyChar() == 8) {
            correctionTypes++;
            keyTypes--;
        } else {
            keyTypes++;
        }
        if (lastTime != 0) {
            timeSums += deltaTime;
        } else {
            timeSums += pauseDetectionDelay;
        }
        updateDisplay();
        lastTime = time;
    }

    /** {@inheritDoc} */
    public void keyPressed(final KeyEvent e) {
        // nothing to do
    }

    /** {@inheritDoc} */
    public void keyReleased(final KeyEvent e) {
        // nothing to do
    }
}
