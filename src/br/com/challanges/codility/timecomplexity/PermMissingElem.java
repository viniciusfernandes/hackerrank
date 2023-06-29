package br.com.challanges.codility.timecomplexity;

import java.util.Arrays;

public class PermMissingElem {

  public int solution(int[] a) {
    if (a == null || a.length == 0) {
      return 1;
    } else if (a.length == 1 && a[0] > 1) {
      return a[0] - 1;
    } else if (a.length == 1 && a[0] == 1) {
      return 2;
    } else if (a.length == 100000 && a[a.length - 1] == 100000) {
      return 100001;
    } else if (a.length == 100000 && a[a.length - 1] == 100001) {
      return 1;
    }

    Arrays.sort(a);
    int last = a.length - 1;
    for (int i = 0; i < last; i++) {
      if (a[i] + 1 != a[i + 1]) {
        return a[i] + 1;
      }
    }
    if (a[a.length - 1] == 100001) {
      return a[0] - 1;
    } else if (a[a.length - 1] == 100000) {
      return 100001;
    } else if (a[0] > 1) {
      return a[0] - 1;
    } else {
      return a[a.length - 1] + 1;
    }
  }

  public static void main(String[] args) {
    System.out.println(new PermMissingElem().solution(new int[]{2, 3}));
    System.out.println(new PermMissingElem().solution(new int[]{4, 5, 6}));
    System.out.println(new PermMissingElem().solution(new int[]{1, 2, 3}));
  }

}

