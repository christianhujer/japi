package de.riedquat.java.lang;

public class Lambda {

    /** Sequentially runs the given code the specified number of times.
     * @param times Number of times to repeat the code.
     * @param code Code to repeat.
     */
    public static void repeat(final int times, final Runnable code) {
        for (int i = 0; i < times; i++) code.run();
    }
}
