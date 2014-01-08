package de.riedquat.java.lang;

/** Provides some additional convenience methods for comparing.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @param <T> Type to compare.
 */
@SuppressWarnings("UnusedDeclaration")
public interface NiceComparable<T> extends Comparable<T> {

    default boolean isGreaterThan(T o) {
        return compareTo(o) > 0;
    }
    default boolean isGreaterOrEqual(T o) {
        return compareTo(o) >= 0;
    }
    default boolean isLessThan(T o) {
        return compareTo(o) < 0;
    }
    default boolean isLessOrEqual(T o) {
        return compareTo(o) <= 0;
    }
}
