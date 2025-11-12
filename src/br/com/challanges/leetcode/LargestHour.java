package br.com.challanges.leetcode;

import br.com.challanges.algorithms.datastructure.utils.Assertions;

import java.util.*;

class LargestHour {
    public String largestTimeFromDigits(int[] arr) {
        List<int[]> hours = new ArrayList<>();
        List<int[]> minutes = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (i != j && arr[i] * 10 + arr[j] <= 23) {
                    hours.add(new int[]{i, j});
                }
                if (i != j && arr[i] * 10 + arr[j] <= 59) {
                    minutes.add(new int[]{i, j});
                }
            }
        }
        int maxH = Integer.MIN_VALUE;
        int maxM = Integer.MIN_VALUE;
        int[] maxHour = null;
        for (int[] hour : hours) {
            for (int[] minute : minutes) {
                if (hour[0] != minute[0] && hour[0] != minute[1] &&
                        hour[1] != minute[0] && hour[1] == minute[1]) {
                    int h = arr[hour[0]] * 10 + arr[hour[1]];
                    if (h > maxH) {
                        maxH = h;
                        maxHour = hour;
                    }
                }
            }
        }
        if (maxH == Integer.MIN_VALUE) {
            return "";
        }
        for (int[] minute : minutes) {
            if (maxHour[0] != minute[0] && maxHour[0] != minute[1] &&
                    maxHour[1] != minute[0] && maxHour[1] != minute[1]) {
                int m = arr[minute[0]] * 10 + arr[minute[1]];
                if (m > maxM) {
                    maxM = m;
                }
            }
        }
        if (maxM == Integer.MIN_VALUE) {
            return "";
        }
        return String.format("%02d:%02d", maxH, maxM);
    }

    public static void main(String[] args) {
        Assertions.assertEquals("23:41", new LargestHour().largestTimeFromDigits(new int[]{1, 2, 3, 4}));
        Assertions.assertEquals("23:33", new LargestHour().largestTimeFromDigits(new int[]{3, 3, 3, 2}));
        Assertions.assertEquals("10:00", new LargestHour().largestTimeFromDigits(new int[]{0, 0, 1, 0}));
        Assertions.assertEquals("03:00", new LargestHour().largestTimeFromDigits(new int[]{0, 0, 3, 0}));
        Assertions.assertEquals("05:30", new LargestHour().largestTimeFromDigits(new int[]{0, 0, 3, 5}));
        Assertions.assertEquals("", new LargestHour().largestTimeFromDigits(new int[]{5, 5, 3, 5}));
        Assertions.assertEquals("04:00", new LargestHour().largestTimeFromDigits(new int[]{0, 4, 0, 0}));
        Assertions.assertEquals("06:26", new LargestHour().largestTimeFromDigits(new int[]{2, 0, 6, 6}));
    }
}