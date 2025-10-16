package br.com.challanges.leetcode;

import br.com.challanges.algorithms.datastructure.utils.Assertions;

import java.util.*;

public class OrderedArray {

    public int searchInsert(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] >= target) {
                return i;
            }
        }
        return arr.length;
    }


    private boolean isOpen(char c) {
        return c == '[' || c == '{' || c == '(';
    }

    private boolean isClose(char c) {
        return !isOpen(c);
    }

    public static void main(String[] args) {
        OrderedArray o = new OrderedArray();
        int[] arr = new int[]{1, 3, 5, 6};
        Assertions.assertEquals(2, o.searchInsert(arr, 5));
        Assertions.assertEquals(1, o.searchInsert(arr, 2));
        Assertions.assertEquals(4, o.searchInsert(arr, 7));
    }
}
