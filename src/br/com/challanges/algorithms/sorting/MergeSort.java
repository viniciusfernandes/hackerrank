package br.com.challanges.algorithms.sorting;

import java.util.Arrays;

public class MergeSort {
    private MergeSort() {
    }

    public static void sort(int[] a) {
        if (a == null || a.length <= 1) {
            return;
        }
        int[] a0 = Arrays.copyOfRange(a, 0, a.length / 2);
        int[] a1 = Arrays.copyOfRange(a, a.length / 2, a.length);
        sort(a0);
        sort(a1);
        merge(a0, a1, a);
    }

    private static void merge(int[] a0, int[] a1, int[] a) {
        int i0 = 0, i1 = 0;
        for (int i = 0; i < a.length; i++) {
            if (i0 == a0.length) {
                a[i] = a1[i1++];
            } else if (i1 == a1.length) {
                a[i] = a0[i0++];
            } else if (a0[i0] < a1[i1]) {
                a[i] = a0[i0++];
            } else {
                a[i] = a1[i1++];
            }
        }
    }

    public static void main(String[] args) {
        int[] values = new int[]{100, 2, 77, 3, -1};
        sort(values);
        for (int i = 0; i < values.length; i++) {
            System.out.print(values[i] + " ");
        }
    }
}
