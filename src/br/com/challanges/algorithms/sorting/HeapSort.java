package br.com.challanges.algorithms.sorting;

import br.com.challanges.algorithms.datastructure.BinaryHeap;

public class HeapSort {
    private HeapSort() {
    }

    public static void sort(int[] a) {
        new BinaryHeap().sort(a);
    }

    public static void main(String[] args) {
        //int[] values = new int[]{9, 6, 13, 8};
        int[] values = new int[]{5,4,3,2,1,0};
        HeapSort.sort(values);
        for (int value : values) {
            System.out.print (value+" ");
        }
    }
}
