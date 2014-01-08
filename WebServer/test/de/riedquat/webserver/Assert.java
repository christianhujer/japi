package de.riedquat.webserver;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Assert {

    public static void assertHasThreadWithName(final String name) {
        assertTrue(hasThreadWithName(name));
    }

    public static void assertNotHasThreadWithName(final String name) {
        assertFalse(hasThreadWithName(name));
    }

    private static boolean hasThreadWithName(final String name) {
        final Thread[] threads = new Thread[Thread.activeCount() + 5];
        final int numberOfThreads = Thread.enumerate(threads);
        for (int i = 0; i < numberOfThreads; i++) {
            if (name.equals(threads[i].getName())) {
                return true;
            }
        }
        return false;
    }
}
