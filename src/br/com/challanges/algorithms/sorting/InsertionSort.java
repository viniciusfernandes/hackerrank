package br.com.challanges.algorithms.sorting;

public class InsertionSort {
    private InsertionSort() {
    }

    public static void sort(int[] a) {
        if (a == null || a.length <= 1) {
            return;
        }
        for (int i = 1; i < a.length; i++) {
            int j = i;
            while (a[j] < a[j - 1]) {
                int temp = a[j - 1];
                a[j - 1] = a[j];
                a[j] = temp;
                j--;
            }
        }
    }

    public static void main(String[] args) {
        var a = new int[]{-2, 3, 11, 2, 1, 4};
        sort(a);
        print(a);
    }

    public static void print(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            System.out.print(arr[i] + ", ");
        }
    }
}
