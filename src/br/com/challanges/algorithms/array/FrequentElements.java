package br.com.challanges.algorithms.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FrequentElements {
  private class Frequency implements Comparable<Frequency> {
    int value;
    int freq = 1;

    @Override
    public int compareTo(Frequency o) {
      return Integer.compare(o.freq, freq);
    }
  }

  public int[] topKFrequentelements(int[] nums, int k) {
    Arrays.sort(nums);
    List<Frequency> freqs = new ArrayList<>();
    Frequency freq = null;
    for (int i = 0; i < nums.length - 1; i++) {
      if (freq == null) {
        freq = new Frequency();
        freq.value = nums[i];
        freqs.add(freq);
      }
      if (nums[i] == nums[i + 1]) {
        freq.freq += 1;
        continue;
      }
      freq = null;
    }
    Collections.sort(freqs);
    var mostFrequents = new int[k];
    for (int i = 0; i < k; i++) {
      mostFrequents[i] = freqs.get(i).value;
    }
    return mostFrequents;
  }

  public static void main(String[] args) {
    var elements = new int[]{1, 2, 3, 1, 2, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 41, 2, 1, 1};
    new FrequentElements().topKFrequentelements(elements, 2);
  }
}
