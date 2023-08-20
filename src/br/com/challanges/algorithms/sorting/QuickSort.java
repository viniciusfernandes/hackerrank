package br.com.challanges.algorithms.sorting;

public class QuickSort {
    private QuickSort() {
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
        int p = i - 1, q = i + n, j = i;
        while (j < q) {
            if (a[j] < x) {
                swap(a, j++, ++p);
            } else if (a[j] > x) {
                swap(a, j, --q);
            } else {
                j++;
            }
        }

        sort(a, i, p - (i - 1));
        sort(a, q, n - (q - i));
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
        //int[] values = new int[]{13, 8, 5, 4, 02, 6, 9, 7, 3, 12, 1, 10, 11};
        int[] values = new int[]{7, 0, 0, 0, 0, 0, 0, 0, 0, 7};
        sort(values);
        for (int i = 0; i < values.length; i++) {
            System.out.print(values[i] + " ");
        }
    }

}
