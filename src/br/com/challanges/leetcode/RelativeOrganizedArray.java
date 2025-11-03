package br.com.challanges.leetcode;

import java.util.*;

class RelativeOrganizedArray {
    public Integer[] relativeSortArray(int[] arr1, int[] arr2) {
        if (arr1.length == 0 || arr1.length > 1000 ||
                arr2.length == 0 || arr2.length > 1000) {
            return new Integer[]{};
        }

        Set<Integer> set = new HashSet();
        Map<Integer, Integer> count = new HashMap<>();
        Map<Integer, Integer> pos = new HashMap<>();
        for (int item : arr1) {
            if (item < 0 || item > 1000) {
                return new Integer[]{};
            }
            int c = count.getOrDefault(item, 0);
            count.put(item, c + 1);
        }

        for (int i = 0; i < arr2.length; i++) {
            if (arr2[i] < 0 || arr2[i] > 1000) {
                return new Integer[]{};
            }
            pos.put(i, arr2[i]);
            set.add(arr2[i]);
        }

        Integer[] x = new Integer[arr1.length];
        int k = 0;
        for (int value : arr2) {
            int c = count.get(value);
            for (int j = 0; j < c; j++) {
                x[k++] = value;
            }
        }
        Arrays.sort(arr1);
        for (int j : arr1) {
            if (!set.contains(j)) {
                x[k++] = j;
            }
        }
        return x;
    }

    public static void main(String[] args) {
        RelativeOrganizedArray solution = new RelativeOrganizedArray();
        System.out.println(Arrays.deepToString(solution.relativeSortArray(new int[]{4, 3, 2, 4, 2}, new int[]{4, 2, 3})));
        System.out.println(Arrays.deepToString(solution.relativeSortArray(
                new int[]{2, 3, 1, 3, 2, 4, 6, 7, 9, 2, 19}, new int[]{2, 1, 4, 3, 9, 6})));
    }
}