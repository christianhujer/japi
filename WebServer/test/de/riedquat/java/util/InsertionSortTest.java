package de.riedquat.java.util;

public class InsertionSortTest extends SortTest {

    @Override
    protected Sort getSort() {
        return InsertionSort.getInstance();
    }
}
