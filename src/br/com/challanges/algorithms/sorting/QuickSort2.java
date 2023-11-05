package br.com.challanges.algorithms.sorting;

public class QuickSort2 {
    private QuickSort2() {
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
        int pIdx = randomIndex(i, n);
        int piv = a[pIdx];
        int r = n - 1;
        int j = i;
        int l = i - 1;
        while (j < r) {
            if (a[j] > piv) {
                swap(a, j, r--);
            } else if (a[j] < piv) {
                swap(a, j++, ++l);
            } else {
                j++;
            }
        }
        sort(a, 0, j);
        sort(a, j  , n - (r -1) );
    }

    private static int randomIndex(int shift, int n) {
        return shift + (int) Math.random() * (n - 1);
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
    }

}
