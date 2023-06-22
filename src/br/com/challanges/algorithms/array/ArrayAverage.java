package br.com.challanges.algorithms.array;

public class ArrayAverage {
  public ArrayAverage() {
  }

  public void average(int[] nums, int radius) {

    //generic condition here
    if (nums == null || nums.length == 0) {
      return;
    }

    var avs = new int[nums.length];
    for (int i = 0; i < nums.length; i++) {
      if (nums[i] < 0 || nums[i] > 100000) {
        return;
      }
      if (i - radius < 0 || i + radius > nums.length - 1) {
        avs[i] = -1;
        continue;
      }
      var sum = 0;
      var n = 2 * radius + 1;
      for (int j = i - radius; j <= i + radius; j++) {
        sum += nums[j];
      }
      avs[i] = sum / n;
    }

    for (int i = 0; i < avs.length; i++) {
      System.out.print(avs[i] + " ");
    }
  }

  public static void main(String[] args) {
    new ArrayAverage().average(new int[]{7, 4, 3, 9, 1, 8, 5, 2, 6}, 4);
  }
}
