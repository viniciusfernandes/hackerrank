package br.com.challanges.codility.timecomplexity;

public class TapeEquilibrium {

  public int solution(int[] a) {
    if (a.length <= 1) {
      return -1;
    }
    if (a.length == 2) {
      return Math.abs(a[0] - a[1]);
    }
    int sumA = a[0];
    int sumB = 0;
    for (int p = 1; p < a.length; p++) {
      sumB += a[p];
    }
    int min = Integer.MAX_VALUE;
    int diff;
    for (int p = 1; p < a.length; p++) {
      diff = Math.abs(sumA - sumB);
      if (diff < min) {
        min = diff;
      }
      sumA += a[p];
      sumB -= a[p];
    }
    return min;
  }

  public static void main(String[] args) {
    System.out.println(new TapeEquilibrium().solution(new int[]{3, 1, 2, 4, 3}));
    System.out.println(new TapeEquilibrium().solution(new int[]{3, 1}));
    System.out.println(new TapeEquilibrium().solution(new int[]{3, 1, 4}));

  }

}

