package br.com.challanges.algorithms.sorting;

public class CountingSort {
    public static int[] sort(int[] a, int k) {
        int c[] = new int[k];
        for (int i = 0; i < a.length; i++)
            c[a[i]]++;
        for (int i = 1; i < k; i++)
            c[i] += c[i - 1];
        int b[] = new int[a.length];
        for (int i = a.length - 1; i >= 0; i--)
            b[--c[a[i]]] = a[i];
        return b;
    }

    public static void main(String[] args) {
        int[] values = new int[]{2, 2, 2};
        //int[] values = new int[]{4, 9, 8, 17};
        values = CountingSort.sort(values, 4);

        for (int i = 0; i < values.length; i++) {
            System.out.print(values[i] + " ");
        }
    }
}
