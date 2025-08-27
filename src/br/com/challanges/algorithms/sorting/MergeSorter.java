package br.com.challanges.algorithms.sorting;

import java.util.Arrays;

public class MergeSorter<T extends Comparable<T>> implements SorterCommand<T> {

    @Override
    public void sort(T[] array) {
        if (array == null || array.length <= 1) {
            return;
        }
        T[] a0 = Arrays.copyOfRange(array, 0, array.length / 2);
        T[] a1 = Arrays.copyOfRange(array, array.length / 2, array.length);
        sort(a0);
        sort(a1);
        merge(a0, a1, array);
    }


    private void merge(T[] a0, T[] a1, T[] array) {
        int i0 = 0, i1 = 0;
        for (int i = 0; i < array.length; i++) {
            if (i0 == a0.length) {
                array[i] = a1[i1++];
            } else if (i1 == a1.length) {
                array[i] = a0[i0++];
            } else if (a0[i0].compareTo(a1[i1]) < 0) {
                array[i] = a0[i0++];
            } else if (a0[i0].compareTo(a1[i1]) >= 0) {
                array[i] = a1[i1++];
            }
        }
    }
}
