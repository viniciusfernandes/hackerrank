package br.com.challanges.algorithms.sorting;

import java.util.Random;

public class QuickSorter<T extends Comparable<T>> implements SorterCommand<T> {
    private static final Random random = new Random();

    @Override
    public void sort(T[] a) {
        if (a == null || a.length <= 1) return;
        sort(a, 0, a.length - 1);
    }

    private void sort(T[] a, int low, int high) {
        // partition size must have length > 1
        if (low >= high) {
            return;
        }
        int lastPivotIdx = sortPartition(a, low, high);
        // left partition
        sort(a, low, lastPivotIdx - 1);
        // right partition
        sort(a, lastPivotIdx + 1, high);

    }

    private int sortPartition(T[] a, int low, int high) {
        int pivotIdx = low + random.nextInt(high - low + 1);
        // need to keep pivot in the end of the array partition as the algorithm requires
        T pivot = a[pivotIdx];
        swap(a, pivotIdx, high);
        // will be eager increased
        int i = low - 1;
        // transversing only the partition
        for (int j = low; j < high; j++) {
            if (a[j].compareTo(pivot) <= 0) {
                swap(a, ++i, j);
            }
        }
        i++;
        swap(a, i, high);
        return i;
    }

    private void swap(T[] a, int i, int j) {
        T tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}
