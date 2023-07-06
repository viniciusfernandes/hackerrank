package br.com.challanges.codility.counting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class MaxInteger {

  public int solution(int[] a) {
    if (a.length == 1) {
      return a[0] < 0 || a[0] > 1 ? 1 : a[0] + 1;
    }
    Arrays.sort(a);
    int lowest = -1;
    for (int i = 0; i < a.length - 1; i++) {
      if (a[i + 1] < 0) {
        continue;
      }
      if (lowest < 0) {
        lowest = a[i] >= 0 ? a[i] : a[i + 1];
      }
      if (a[i + 1] - a[i] > 1) {
        if (a[i] < 0 && (a[i + 1] == 0 || a[i + 1] == 1)) {
          continue;
        } else if (a[i] == 0 && a[i + 1] == 1) {
          continue;
        } else if (a[i] == 0 && a[i + 1] > 1) {
          return 1;
        } else if (a[i] > 1 && lowest > 1) {
          return 1;
        } else if (a[i] > 1 && lowest <= 1) {
          return a[i] + 1;
        }

      }
    }
    if (lowest > 1) {
      return 1;
    }
    return a[a.length - 1] < 0 ? 1 : a[a.length - 1] + 1;
  }

  public static void main(String[] args) {
    System.out.println(new MaxInteger().solution(generate(0, 200, 101, 101)));
    System.out.println(new MaxInteger().solution(new int[]{0, 10, 20}));
    System.out.println(new MaxInteger().solution(new int[]{10, 10, 20}));
    System.out.println(new MaxInteger().solution(new int[]{1, 3, 6, 4, 1, 2}));
    System.out.println(new MaxInteger().solution(new int[]{-1}));
    System.out.println(new MaxInteger().solution(new int[]{2}));
    System.out.println(new MaxInteger().solution(new int[]{-1000000000}));
    System.out.println(new MaxInteger().solution(new int[]{1000000000}));
    System.out.println(new MaxInteger().solution(new int[]{-1, 1, 2, 3}));
    System.out.println(new MaxInteger().solution(new int[]{-1, 0}));
    System.out.println(new MaxInteger().solution(new int[]{-1, 1}));
    System.out.println(new MaxInteger().solution(new int[]{-1, -1, -1}));
    System.out.println(new MaxInteger().solution(new int[]{0, 0, 1}));
    System.out.println(new MaxInteger().solution(new int[]{0, 0, 1, 2, 3}));
    System.out.println(new MaxInteger().solution(new int[]{2, 10, 1000000000}));
    System.out.println(new MaxInteger().solution(new int[]{2, 2, 3}));
    System.out.println(new MaxInteger().solution(new int[]{1, 2, 2, 2}));
    System.out.println(new MaxInteger().solution(new int[]{1, 2, 4}));
  }

  public static int[] generate(int from, int to, int removeFrom, int removeTo) {
    var arr = new int[to - from];
    for (int i = 0; i < arr.length; from++, i++) {
      if (from >= removeFrom && from <= removeTo) {
        continue;
      }
      arr[i] = from;
    }
    return arr;
  }
}
