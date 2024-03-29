package br.com.challanges.hackerrank.strings;

import java.util.Date;
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

    public Freq(char value) {
      this.value = value;

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
    var init = new Date().getTime();
    var chars1 = s1.toCharArray();
    var chars2 = s2.toCharArray();
    var map = new HashMap<Character, Freq>();
    for (int i = 0; i < chars1.length; i++) {
      var freq = map.getOrDefault(chars1[i], new Freq(chars1[i]));
      freq.freq1++;
      map.put(chars1[i], freq);
    }

    for (int i = 0; i < chars2.length; i++) {
      var freq = map.getOrDefault(chars2[i], new Freq(chars2[i]));
      freq.freq2++;
      map.put(chars2[i], freq);
    }

    var toRemove = 0;
    for (var freq : map.values()) {
      toRemove += Math.abs(freq.freq1 - freq.freq2);
    }
    System.out.println("time=" + (new Date().getTime() - init)/1000d);
    return toRemove;
  }

  public static void main(String[] args) {
    makingAnagrams("wabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakwwwwwwfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkjabsdjkvuahdakejfnfauhdsaavasdlkj", "djfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfadjfladfhiawasdkjvalskufhafablsdkashlahdfa");
  }

}
