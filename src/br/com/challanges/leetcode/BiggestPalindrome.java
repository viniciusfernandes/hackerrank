package br.com.challanges.leetcode;

import br.com.challanges.algorithms.datastructure.utils.Assertions;

public class BiggestPalindrome {
    public String calculate(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }
        if (input.length() == 1) {
            return input;
        }
        String maxPal = "";
        String pal;
        for (int i = 0; i <= input.length() - 1; i++) {
            pal = getPalindrome(input, i, i);
            if (pal.length() > maxPal.length()) {
                maxPal = pal;
            }
            pal = getPalindrome(input, i, i + 1);
            if (pal.length() > maxPal.length()) {
                maxPal = pal;
            }
        }
        return maxPal.length() == 1 ? "" : maxPal;
    }

    private String getPalindrome(String input, int s, int e) {
        while (s >= 0 && e < input.length() && input.charAt(s) == input.charAt(e)) {
            s--;
            e++;
        }
        return input.substring(s + 1, e);
    }

    public static void main(String[] args) {
        Assertions.assertEquals("aba", new BiggestPalindrome().calculate("aba"));
        Assertions.assertEquals("aaa", new BiggestPalindrome().calculate("aaa"));
        Assertions.assertEquals("xxx", new BiggestPalindrome().calculate("abxxx"));
        Assertions.assertEquals("xxffxx", new BiggestPalindrome().calculate("xxxffxxg"));
        Assertions.assertEquals("xxffxx", new BiggestPalindrome().calculate("xxffxxx"));
        Assertions.assertEquals("fffffff", new BiggestPalindrome().calculate("xxffxxxefffffffkk"));
        Assertions.assertEquals("xxfffffffxx", new BiggestPalindrome().calculate("xxffxxxfffffffxx"));
        Assertions.assertEquals("z", new BiggestPalindrome().calculate("z"));
        Assertions.assertEquals("zz", new BiggestPalindrome().calculate("zzx"));
        Assertions.assertEquals("zz", new BiggestPalindrome().calculate("xzz"));
        Assertions.assertEquals("", new BiggestPalindrome().calculate("xzu"));
    }
}
