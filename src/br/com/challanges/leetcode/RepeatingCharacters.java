package br.com.challanges.leetcode;

import java.util.HashSet;
import java.util.Set;

public class RepeatingCharacters {
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() <=0 || s.length() > 50000) {
            return 0;
        }
        if (s.length() == 1) {
            return 1;
        }
        int l = s.length();
        int max = 0;
        int count = 0;
        char[] arr = s.toCharArray();
        int j = 0;
        int i = 0;
        Set<Character> chars = new HashSet<>();
        do {
            if (!isValid(arr[j])) {
                return 0;
            }
            if (!chars.contains(arr[j])) {
                chars.add(arr[j]);
                count = j - i + 1;
            } else {
                i = shift(arr, i, j);
                reset(arr, chars, i, j);
            }
            if (count > max) {
                max = count;
            }
            j++;

        } while (j <= l - 1);
        return max;
    }

    private int shift(char[] arr, int i, int j) {
        for (int k = i; k <= j; k++) {
            if (arr[k] == arr[j]) {
                return k + 1;
            }
        }
        throw new IllegalStateException("Invalid substring index");
    }

    private void reset(char[] arr, Set<Character> chars, int i, int j) {
        chars.clear();
        int k = i;
        do {
            chars.add(arr[k]);
        } while (++k <= j);
    }

    private boolean isValid(char c) {
        return isEnglishLetter(c) || isDigit(c) || isSpace(c) || isAsciiSymbol(c);
    }

    private static boolean isEnglishLetter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    private static boolean isDigit(char c) {
        return (c >= '0' && c <= '9');
    }

    private static boolean isSpace(char c) {
        return c == ' ' || c == '\t' || c == '\r';
    }

    private static boolean isAsciiSymbol(char c) {
        // printable ASCII symbols from 33 (!) to 126 (~), excluding letters/digits
        return (c >= 33 && c <= 126) && !isEnglishLetter(c) && !isDigit(c);
    }

    public static void main(String[] args) {
        RepeatingCharacters r = new RepeatingCharacters();
        System.out.println(r.lengthOfLongestSubstring("abcabcxxx"));
        System.out.println(r.lengthOfLongestSubstring("xxx"));
        System.out.println(r.lengthOfLongestSubstring("abc"));
        System.out.println(r.lengthOfLongestSubstring("x"));
        System.out.println(r.lengthOfLongestSubstring(""));
        System.out.println(r.lengthOfLongestSubstring("v\tx"));
        System.out.println(r.lengthOfLongestSubstring("abcbef"));
        System.out.println(r.lengthOfLongestSubstring("wqabcxybef"));
    }
}



