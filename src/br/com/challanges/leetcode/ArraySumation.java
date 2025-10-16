package br.com.challanges.leetcode;

import java.math.BigInteger;
import java.util.Scanner;

public class ArraySumation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] values = scanner.nextLine().split(" ");
        BigInteger N = new BigInteger(values[0]);
        BigInteger M = new BigInteger(values[1]);
        BigInteger lowerLimit = new BigInteger("2");
        BigInteger upperLimit = new BigInteger("100000");
        if (N.compareTo(lowerLimit) < 0 || N.compareTo(upperLimit) > 0 ||
                M.compareTo(lowerLimit) < 0 || M.compareTo(upperLimit) > 0) {
            return;
        }
        int n = N.intValue();
        int m = M.intValue();
        int[] arr = new int[n];
        BigInteger[] out = new BigInteger[m];
        BigInteger sum = BigInteger.ZERO;
        for (int i = 1; i <= n; i++) {
            arr[i - 1] = i;
            sum = sum.add(BigInteger.valueOf(i));
        }

        lowerLimit = new BigInteger("1");
        upperLimit = new BigInteger("500000");
        for (int i = 0; i < m; i++) {
            BigInteger OP = scanner.nextBigInteger();
            if (OP.compareTo(lowerLimit) < 0 || OP.compareTo(upperLimit) > 0) {
                return;
            }
            int op = OP.intValue();
            boolean ok = false;
            for (int j = 0; j < n; j++) {
                if (op == arr[j]) {
                    ok = true;
                    break;
                }
            }
            if (ok) {
                int temp = arr[0];
                arr[0] = arr[n - 1];
                arr[n - 1] = temp;
            } else {
                sum = sum.subtract(BigInteger.valueOf( arr[n - 1]));
                sum = sum.add(OP);
                arr[n - 1] = op;
            }
            out[i] = sum;
        }

        for (int i = 0; i < m; i++) {
            System.out.println(out[i]);
        }

    }
}
