package br.com.challanges.leetcode;

import br.com.challanges.algorithms.datastructure.utils.Assertions;

import java.util.HashSet;
import java.util.Set;

class MinInteger {

    public int minimum(int[] d) {
        if (d.length == 0 || d[d.length - 1] <= 0) {
            return 1;
        }
        Set<Integer> positives = new HashSet<>();
        int min = Integer.MAX_VALUE;
        int minPos = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int j : d) {
            if (j >= 0) {
                positives.add(j);
            }
            if (min > j) {
                min = j;
            }

            if (minPos > j && j >= 0) {
                minPos = j;
            }

            if (max < j) {
                max = j;
            }
        }
        if ((min < 0 && max <= 0) || (min < 0 && !positives.contains(1))) {
            return 1;
        }
        for (int i = minPos; i <= max; i++) {
            if (!positives.contains(i)) {
                return i;
            }
        }
        return max + 1;
    }

    public static void main(String[] args) {
        Assertions.assertEquals(2, new MinInteger().minimum(new int[]{0, 1}));
        Assertions.assertEquals(2, new MinInteger().minimum(new int[]{-1, 1}));
        Assertions.assertEquals(1, new MinInteger().minimum(new int[]{-10, -50}));
        Assertions.assertEquals(1, new MinInteger().minimum(new int[]{0, 2}));
        Assertions.assertEquals(1, new MinInteger().minimum(new int[]{-1, -1}));
        Assertions.assertEquals(2, new MinInteger().minimum(new int[]{1}));
        Assertions.assertEquals(1, new MinInteger().minimum(new int[]{0}));
        Assertions.assertEquals(3, new MinInteger().minimum(new int[]{2, 2}));
        Assertions.assertEquals(1, new MinInteger().minimum(new int[]{-1, 4}));
        Assertions.assertEquals(1, new MinInteger().minimum(new int[]{-1}));
        Assertions.assertEquals(4, new MinInteger().minimum(new int[]{1, 2, 3}));
        Assertions.assertEquals(1, new MinInteger().minimum(new int[]{0, 5, 6}));
        Assertions.assertEquals(1000001, new MinInteger().minimum(new int[]{1000000}));
    }
}