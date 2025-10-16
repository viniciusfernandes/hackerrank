package br.com.challanges.leetcode;// File: FileProcessor.java

import java.util.*;

public class TwoSum {
    public static int[] twoSum(int[] nums, int target) {
        if (nums.length < 2 || nums.length > 10000) {
            return new int[]{};
        }
        if (target < -1000000000 || target > 1000000000) {
            return new int[]{};
        }
        Map<Integer, Queue<Integer>> indexes = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < -1000000000 || nums[i] > 1000000000) {
                return new int[]{};
            }
            if (!indexes.containsKey(nums[i])) {
                indexes.put(nums[i], new LinkedList<>());
            }
            indexes.get(nums[i]).add(i);
        }
        Arrays.sort(nums);
        int i = 0, j = 1;
        while (true) {
            if (nums[i] + nums[j] == target) {
                int i1 = indexes.get(nums[i]).poll();
                int i2 = indexes.get(nums[j]).poll();
                return new int[]{i1, i2};
            }
            if (nums[i] + nums[j] < target) {
                if (j == nums.length - 1) {
                    i++;
                    j = i + 1;
                } else {
                    j++;
                }
            } else if (nums[i] + nums[j] > target) {
                i++;
                j = i + 1;
            }
        }
    }

    public static void main(String[] args) {
        int[] result = twoSum(new int[]{2, 5, 5, 11}, 10);
        System.out.println(Arrays.toString(result));
    }
}
