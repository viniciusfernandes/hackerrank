package br.com.challanges.codility.sorting;

public class NumberOfDiscIntersection {

  public int solution(int[] a) {
    if (a.length <= 1) {
      return 0;
    }
    int intersection = 0;
    long prev;
    long next;
    final int last = a.length - 2;
    for (int i = 0; i <= last; i++) {
      prev = (long) a[i] + (long) i;
      for (int j = i + 1; j < a.length; j++) {
        next = j - a[j];
        if (prev >= next) {
          intersection++;
        }
      }
    }
    return intersection;
  }

  public static void main(String[] args) {
    System.out.println(new NumberOfDiscIntersection().solution(new int[]{1, 5, 2, 1, 4, 0}));
    System.out.println(new NumberOfDiscIntersection().solution(new int[]{0, 0, 0}));
  }

}

