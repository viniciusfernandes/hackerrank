package br.com.challanges.algorithms.sorting;

import br.com.challanges.algorithms.datastructure.utils.Assertions;

public class SorterFactory {
    private final static SorterFactory instance = new SorterFactory();

    public static SorterFactory getInstance() {
        return instance;
    }

    public <T extends Comparable<T>> SorterCommand<T> createSorter(SortType type) {
        if (type == SortType.MERGESORT) {
            return new MergeSorter<>();
        } else if (type == SortType.QUICKSORT) {
            return new QuickSorter<>();
        } else {
            throw new IllegalArgumentException("Sort type not supported");
        }
    }

    public static void main(String[] args) {
        testSorter(SorterFactory.getInstance().createSorter(SortType.MERGESORT));
        testSorter(SorterFactory.getInstance().createSorter(SortType.QUICKSORT));
    }

    private static void testSorter(SorterCommand<Integer> sorter) {
        Integer[] arr = new Integer[]{30, -1, 5, -60, 60, 80, 0};
        Integer[] expected = new Integer[]{-60, -1, 0, 5, 30, 60, 80};
        sorter.sort(arr);
        Assertions.assertEquals(expected, arr);

        Integer[] arr2 = new Integer[]{-1, -1, 5, -60, 60, 80, 0};
        Integer[] expected2 = new Integer[]{-60, -1, -1, 0, 5, 60, 80};
        sorter.sort(arr2);
        Assertions.assertEquals(expected2, arr2);

        Integer[] arr3 = new Integer[]{2, -1, -1};
        Integer[] expected3 = new Integer[]{-1, -1, 2};
        sorter.sort(arr3);
        Assertions.assertEquals(expected3, arr3);

        Integer[] arr4 = new Integer[]{-1, 2, -1, -1};
        Integer[] expected4 = new Integer[]{-1, -1, -1, 2};
        sorter.sort(arr4);
        Assertions.assertEquals(expected4, arr4);
    }
}
