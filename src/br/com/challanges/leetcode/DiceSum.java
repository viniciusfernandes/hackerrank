package br.com.challanges.leetcode;

public class DiceSum {

    public static int numRollsToTarget(int n, int k, int target) {
        int[][] dp = new int[n + 1][target + 1];
        dp[0][0] = 1;

        for (int dice = 1; dice <= n; dice++) {
            for (int sum = 1; sum <= target; sum++) {
                for (int face = 1; face <= k && face <= sum; face++) {
                    dp[dice][sum] += dp[dice - 1][sum - face];
                }
            }
        }
        return dp[n][target];
    }

    public static void main(String[] args) {
        System.out.println(numRollsToTarget(2, 2, 10)); // expected 4
    }
}
