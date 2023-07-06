package br.com.challanges.codility.counting;

public class MaxCounters {
  public int[] solution(int n, int[] a) {
    var counters = new int[n];
    int max = 0;
    int baseMax = 0;
    for (int i = 0, k = 0; i < a.length; i++) {
      if (a[i] >= 1 && a[i] <= n) {
        k = a[i] - 1;
        counters[k]++;
        max = Math.max(max, counters[k]);
      } else {
        baseMax += max;
        max = 0;
        counters = new int[n];
      }
    }
    for (int i = 0; i < counters.length; i++) {
      counters[i] += baseMax;
    }
    return counters;
  }

  public static void main(String[] args) {
    var counters = new MaxCounters().solution(5, new int[]{3, 4, 4, 6, 1, 4, 4});
    for (int counter : counters) {
      System.out.print(counter + ", ");
    }
  }
}
