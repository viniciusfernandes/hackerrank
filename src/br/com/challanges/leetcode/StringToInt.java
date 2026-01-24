package br.com.challanges.leetcode;

import br.com.challanges.algorithms.datastructure.utils.Assertions;

import java.math.BigInteger;
import java.util.*;

class StringToInt {
    public int myAtoi(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        List<Integer> l = new ArrayList<>();
        boolean negative = false;
        char[] chars = s.toCharArray();
        for (int j = 0; j < chars.length; j++) {
            char c = chars[j];
            if (c == ' ') {
                continue;
            }
            if (c == '+' && l.isEmpty()) {
                l.add(0);
            } else if (c == '-' && l.isEmpty()) {
                negative = true;
                l.add(0);
            } else if ('0' <= c && '9' >= c) {
                l.add(Integer.valueOf(String.valueOf(c)));
            } else {
                break;
            }
        }
        BigInteger value = BigInteger.ZERO;
        BigInteger pow = pow(l.size() - 1);
        for (int i : l) {
            value = BigInteger.valueOf(i).multiply(pow).add(value);
            pow = pow.divide(BigInteger.TEN);
        }

        if (!negative) {
            return value.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) > 0 ? Integer.MAX_VALUE : value.intValue();
        }
        value = value.multiply(BigInteger.valueOf(-1));
        return value.compareTo(BigInteger.valueOf(Integer.MIN_VALUE)) < 0 ? Integer.MIN_VALUE : value.intValue();
    }

    private BigInteger pow(int n) {
        BigInteger pow = BigInteger.ONE;
        for (int i = 0; i < n; i++) {
            pow = BigInteger.TEN.multiply(pow);
        }
        return pow;
    }

    public static void main(String[] args) {
        Assertions.assertEquals(0, new StringToInt().myAtoi("words and 987"));
        Assertions.assertEquals(0, new StringToInt().myAtoi("+-12"));
        Assertions.assertEquals(-42, new StringToInt().myAtoi("   -042"));
        Assertions.assertEquals(0, new StringToInt().myAtoi("-+12"));
        Assertions.assertEquals(0, new StringToInt().myAtoi("0-1"));
        Assertions.assertEquals(1, new StringToInt().myAtoi("+1"));
        Assertions.assertEquals(-42, new StringToInt().myAtoi("-042"));
        Assertions.assertEquals(42, new StringToInt().myAtoi("+000042"));
        Assertions.assertEquals(0, new StringToInt().myAtoi("        "));
        Assertions.assertEquals(0, new StringToInt().myAtoi("+"));
        Assertions.assertEquals(42, new StringToInt().myAtoi("000042+"));
        Assertions.assertEquals(0, new StringToInt().myAtoi("0-1"));


    }
}