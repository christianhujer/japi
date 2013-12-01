package net.sf.japi.java.util;

import java.util.Iterator;

/** Utility methods for arrays.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 1.0
 * @version 1.0
 */
public class Arrays {

    private Arrays() {
    }

    /** Returns an iterator for the specified Array.
     *
     * @param elements Elements for which to return an Iterator.
     * @param <T> Type of the elements.
     * @return an Iterator for the elements.
     */
    @SafeVarargs
    public static <T> Iterator<T> iterator(final T... elements) {
        return new ArrayIterator<>(elements);
    }
}
