package br.com.challanges.algorithms.sorting;

import java.util.Map;

public class OldQuickSort {
    private OldQuickSort() {
    }

    public static void sort(int[] a) {
        if (a == null || a.length <= 1) {
            return;
        }
        sort(a, 0, a.length);
    }

    private static <T> void sort(int[] a, int i, int n) {
        if (n <= 1) {
            return;
        }
        int x = a[i + randomIndex(n)];
        int l = i - 1, r = i + n, j = i;
        while (j < r) {
            if (a[j] < x) {
                swap(a, j++, ++l);
            } else if (a[j] > x) {
                swap(a, j, --r);
            } else {
                j++;
            }
        }

        sort(a, i, l - (i - 1));
        sort(a, r, n - (r - i));
    }

    private static int randomIndex(int n) {
        return (int) Math.random() * (n - 1);
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static void main(String[] args) {
        int[] values = new int[]{13, 8, 5, 4, 2, 6, 9, 7, 3, 12, 1, 10, 11};
        //int[] values = new int[]{7, 0, 0, 0, 0, 0, 0, 0, 0, 7};
        sort(values);
        for (int i = 0; i < values.length; i++) {
            System.out.print(values[i] + " ");
        }
        Map.of("x", null);
    }


}
