package br.com.challanges.algorithms.dynamicprogramming;

public class LongestIncreasingSubsequence {
    public static int findLongestIncreasingSubsequence(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];

        // Initialize each element of dp as 1
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
        }

        // Compute the length of the longest increasing subsequence
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j] && dp[i] < dp[j] + 1) {
                    dp[i] = dp[j] + 1;
                }
            }
        }

        // Find the maximum value in dp
        int max = 0;
        for (int i = 0; i < n; i++) {
            if (dp[i] > max) {
                max = dp[i];
            }
        }

        return max;
    }

    public static void main(String[] args) {
        int[] nums = {10, 9, 2, 5, 3, 7, 101, 18};
        int result = findLongestIncreasingSubsequence(nums);
        System.out.println("Length of the longest increasing subsequence: " + result);
    }
}
