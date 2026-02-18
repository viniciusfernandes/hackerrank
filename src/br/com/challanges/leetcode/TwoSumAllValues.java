package br.com.challanges.leetcode;

import br.com.challanges.algorithms.datastructure.utils.Assertions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TwoSumAllValues {

    public static List<int[]> allUniqueValuePairs(int[] nums, int target) {
        if (nums.length < 2) {
            throw new IllegalArgumentException("Nums array must have at least 2 elements, but it has only " + nums.length);
        }
        List<int[]> pairs = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        for (int x : nums) {
            int y = target - x;
            if (visited.contains(y)) {
                int min = Math.min(x, y);
                int max = Math.max(x, y);
                pairs.add(new int[]{min, max});
            }
            visited.add(x);
        }
        return pairs;
    }

    public static void main(String[] args) {
        var expected = List.of(new int[]{0, 10}, new int[]{5, 5});
        var pairs = allUniqueValuePairs(new int[]{0, 10, 5, 6, 5}, 10);
        Assertions.assertEquals(expected, pairs);

        expected = List.of();
        pairs = allUniqueValuePairs(new int[]{6, 5}, 10);
        Assertions.assertEquals(expected, pairs);

        Assertions.assertThrows(IllegalArgumentException.class, () -> allUniqueValuePairs(new int[]{11}, 10));
    }
}