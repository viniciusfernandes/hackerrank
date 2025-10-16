package br.com.challanges.leetcode;

import java.math.BigInteger;
import java.util.Scanner;

public class FibonacciV2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] values = sc.nextLine().split(" ");
        var a = new BigInteger(values[0]);
        var b = new BigInteger(values[1]);
        var N = new BigInteger(values[2]);
        var MAX_AB = new BigInteger("4");
        var MIN_N = new BigInteger("3");
        var MAX_N = new BigInteger("20");
//        if (a.compareTo(BigInteger.ZERO) < 0 || a.compareTo(MAX_AB) > 0 ||
//                b.compareTo(BigInteger.ZERO) < 0 || b.compareTo(MAX_AB) > 0 ||
//                N.compareTo(MIN_N) < 0 || a.compareTo(MAX_N) > 0) {
//            return;
//        }
        int n = N.intValue();
        int[] nth = new int[n];
        for (int i = 0; i < n; i++) {
            if (i == 0) {
                nth[i] = a.intValue();
            } else if (i == 1) {
                nth[i] = b.intValue();
            } else {
                nth[i] = nth[i - 1] * nth[i - 1] + nth[i - 2];
            }

        }
        System.out.println(nth[nth.length - 1]);
    }
}
