package br.com.challanges.codility.counting;

import java.util.Arrays;

public class PermutationCheck {
  public int solution(int[] a) {
    Arrays.sort(a);
    for (int i = 0; i < a.length; i++) {
      if (a[i] == i + 1) {
        continue;
      }
      return 0;
    }
    return 1;
  }

  public static void main(String[] args) {
    System.out.println(new PermutationCheck().solution(new int[]{4, 1, 3, 2}));
  }
}
