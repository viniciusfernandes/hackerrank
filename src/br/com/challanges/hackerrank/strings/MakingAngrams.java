package br.com.challanges.hackerrank.strings;

import java.util.HashMap;

public class MakingAngrams {
  private static class Freq {
    char value;
    int freq1;
    int freq2;

    public Freq(char value, int freq1, int freq2) {
      this.value = value;
      this.freq1 = freq1;
      this.freq2 = freq2;
    }

    @Override
    public String toString() {
      return "Freq{" +
          "value=" + value +
          ", freq1=" + freq1 +
          ", freq2=" + freq2 +
          '}';
    }
  }

  public static int makingAnagrams(String s1, String s2) {
    if (s1.length() < 1 || s1.length() > 10000
        || s2.length() < 1 || s2.length() > 10000) {
      throw new IllegalArgumentException();
    }
    var longer = s1.length() >= s2.length() ? s1.toCharArray() : s2.toCharArray();
    var shorter = s1.length() < s2.length() ? s1.toCharArray() : s2.toCharArray();
    var map = new HashMap<Character, Freq>();
    for (int i = 0; i < longer.length; i++) {
      var freq = map.get(longer[i]);
      if (freq == null) {
        map.put(longer[i], new Freq(longer[i], 1, 0));
        continue;
      }
      freq.freq1++;
    }
    for (int i = 0; i < shorter.length; i++) {
      var freq = map.get(shorter[i]);
      if (freq == null) {
        map.put(shorter[i], new Freq(longer[i], 0, 1));
        continue;
      }
      freq.freq2++;
    }

    var toRemove = 0;
    for (var freq : map.values()) {
      toRemove += Math.abs(freq.freq1 - freq.freq2);
    }
    return toRemove;
  }

  public static void main(String[] args) {
    makingAnagrams("absdjkvuahdakejfnfauhdsaavasdlkj", "djfladfhiawasdkjvalskufhafablsdkashlahdfa");
  }

}
