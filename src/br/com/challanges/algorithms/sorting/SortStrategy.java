package br.com.challanges.algorithms.sorting;

interface SortStrategy {
    default void print(int[] arr) {
        var s = new StringBuilder();
        for (int i : arr) {
            s.append(i).append(" ");
        }
        System.out.println(s);
    }

    void doIt(int[] arr);

    default void sort(int[] arr) {
        doIt(arr);
        print(arr);
    }


}
