package br.com.challanges.algorithms.sorting;

public class BubbleSort {
    private BubbleSort() {
    }

    public static void sort(int[] a) {
        if (a == null || a.length <= 1) {
            return;
        }
        for (int i = 0; i < a.length; i++) {
            for (int j = i; j < a.length - 1; j++) {
                if (a[j + 1] < a[j]) {
                    int temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                }
            }
        }
    }
}
