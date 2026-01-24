package br.com.challanges.leetcode;

import br.com.challanges.algorithms.datastructure.utils.Assertions;

import java.math.BigInteger;

class CountIntegerPairs {
    public int solution(int[] nums) {
        if (nums == null || nums.length == 0 || nums.length > 100000) {
            return 0;
        }
        int limit = Integer.MAX_VALUE;
        int count = 0;
        for (int j : nums) {
            if (j < 10 || j >= limit) {
                return 0;
            }

            int last = j % 10;
            for (int num : nums) {
                int first = firstDigit(num);
                if (first == last) {
                    count++;
                }
            }
        }
        return count % limit;
    }

    private int firstDigit(int number) {
        while (number >= 10) {
            number /= 10;
        }
        return number;
    }

    public static void main(String[] args) {
        Assertions.assertEquals(3, new CountIntegerPairs().solution(new int[]{30, 12, 29, 91}));
        Assertions.assertEquals(5, new CountIntegerPairs().solution(new int[]{122, 21, 21, 23}));
    }


    public BigInteger fibBig(int n) {
        if (n <= 1) return BigInteger.valueOf(n);
        BigInteger a = BigInteger.ZERO;
        BigInteger b = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            BigInteger c = a.add(b);
            a = b;
            b = c;
        }
        return b;
    }
}