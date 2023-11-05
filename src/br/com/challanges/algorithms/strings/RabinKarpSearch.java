package br.com.challanges.algorithms.strings;

import java.util.ArrayList;
import java.util.List;

public class RabinKarpSearch {
    private RabinKarpSearch() {
    }

    public static List<Integer> search(String text, String pattern) {
        var tArr = text.toCharArray();
        var pArr = pattern.toCharArray();
        var p = toDigit(pArr, pArr.length);
        var t = 0L;
        var shifts = new ArrayList<Integer>(10);
        var last = tArr.length - pArr.length;
        for (int s = 0; s < last; s++) {
            t = nextTextDigit(s, pArr.length, t, tArr);
            if (p == t && isEqual(s, pArr, tArr)) {
                shifts.add(s);
            }
        }
        return shifts;
    }

    private static boolean isEqual(int start, char[] pattern, char[] text) {
        for (int i = 0; i < pattern.length; i++) {
            if (pattern[i] != text[start + i]) {
                return false;
            }
        }
        return true;
    }

    private static long toDigit(char[] chars, int limit) {
        long digit = 0;
        var q = 1000000000003L;
        for (int i = 0; i < limit; i++) {
            digit = (10 * digit + chars[i]) % q;
        }
        return digit;
    }

    private static long nextTextDigit(int start, int length, long digit, char[] text) {
        if (start == 0) {
            return toDigit(text, length);
        }
        var q = 1000000000003L;
        long power = (long) Math.pow(10d, length - 1);
        digit = 10 * (digit - power * text[start - 1]) + text[start + length - 1];
        digit %= q;
        return digit;
    }

    public static void main(String[] args) {
        var pattern = "vin";
        var text = " Meu nome vinicius Fernandes de ddvinicius";
        var lenght = pattern.length();
        var shifts = search(text, pattern);
        System.out.println("total matchs: " + shifts.size());
        if (shifts.isEmpty()) {
            return;
        }
        System.out.println(text);
        for (int i = 0; i < text.length() - lenght; i++) {
            if (shifts.isEmpty()) {
                return;
            }
            int s = shifts.get(0);
            if (i == s) {
                for (int j = 0; j < lenght; j++) {
                    System.out.print("x");
                }
                shifts.remove(0);
                i += lenght;
            }
            System.out.print(" ");
        }
    }


}

