package br.com.challanges.algorithms.datastructure;

import java.math.BigInteger;

public class TwoSum {
  public void sum(int[] l1, int[] l2) {
    BigInteger sum1 = BigInteger.ZERO;
    BigInteger sum2 = BigInteger.ZERO;
    BigInteger ten = BigInteger.TEN;
    for (int i = 0; i < l1.length; i++) {
      sum1 = sum1.add(BigInteger.valueOf(l1[i]).multiply(ten.pow(i)));
    }
    for (int i = 0; i < l2.length; i++) {
      sum2 = sum2.add(BigInteger.valueOf(l2[i]).multiply(ten.pow(i)));
    }
    var total = sum1.add(sum2);
    var digits = String.valueOf(total).split("");
    for (int i = digits.length - 1; i >= 0; i--) {
      System.out.print(digits[i] + " ");
    }
  }

  public static void main(String[] args) {
    new TwoSum().sum(new int[]{9, 2}, new int[]{9, 9});
  }
}
