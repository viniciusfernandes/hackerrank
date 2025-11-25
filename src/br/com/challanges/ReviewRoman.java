package br.com.challanges;

import br.com.challanges.algorithms.datastructure.utils.Assertions;
import br.com.challanges.leetcode.BiggestPalindrome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReviewRoman {
    final static Map<Integer, String> toRomanMap;

    static {
        toRomanMap = new HashMap<>();
        toRomanMap.put(1, "I");
        toRomanMap.put(4, "IV");
        toRomanMap.put(5, "V");
        toRomanMap.put(9, "IX");
        toRomanMap.put(10, "X");
        toRomanMap.put(40, "XL");
        toRomanMap.put(50, "L");
        toRomanMap.put(90, "XC");
        toRomanMap.put(100, "C");
        toRomanMap.put(400, "CD");
        toRomanMap.put(500, "D");
        toRomanMap.put(900, "CM");
        toRomanMap.put(1000, "M");
    }

    final static Map<Character, Integer> toIntMap;

    static {
        toIntMap = new HashMap<>();
        toIntMap.put('I', 1);
        toIntMap.put('V', 5);
        toIntMap.put('X', 10);
        toIntMap.put('L', 50);
        toIntMap.put('C', 100);
        toIntMap.put('D', 500);
        toIntMap.put('M', 1000);
    }

    final static int[] integers;

    static {
        integers = new int[13];
        integers[0] = 1000;
        integers[1] = 900;
        integers[2] = 500;
        integers[3] = 400;
        integers[4] = 100;
        integers[5] = 90;
        integers[6] = 50;
        integers[7] = 40;
        integers[8] = 10;
        integers[9] = 9;
        integers[10] = 5;
        integers[11] = 4;
        integers[12] = 1;
    }

    public static String intToRoman(int number) {
        String roman = "";
        int q;
        for (int i = 0; i < integers.length; i++) {
            if (integers[i] > number) {
                continue;
            }

            q = number / integers[i];
            number -= (integers[i] * q);
            while (q > 0) {
                roman += toRomanMap.get(integers[i]);
                q--;
            }
        }
        return roman;
    }

    public static int romanToInt(String roman) {
        int integer = 0;
        for (int i = 0; i < roman.length() - 1; i++) {
            int curr = toIntMap.get(roman.charAt(i));
            int next = toIntMap.get(roman.charAt(i + 1));
            if (curr < next) {
                integer -= curr;
            } else {
                integer += curr;
            }
        }
        integer += toIntMap.get(roman.charAt(roman.length() - 1));
        return integer;
    }

    public static boolean isPalindrome(String string) {
        // xxsxx
        // xxxxx
        // x
        // 898
        // 99
        // chars from the string have to respect this condition i == length -i for each i
        if (string == null || string.isEmpty()) {
            return false;
        }

        for (int i = 0; i < string.length() / 2; i++) {
            if (string.charAt(i) != string.charAt(string.length() - 1 - i)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPalindrome(int number) {
        List<Integer> l = new ArrayList<>();
        while (number > 0) {
            int r = number % 10;
            number -= r;
            number /= 10;
            l.add(r);
        }

        for (int i = 0; i < l.size() / 2; i++) {
            if (l.get(i).intValue() != l.get(l.size() - 1 - i).intValue()) {
                return false;
            }
        }
        return true;
    }

    public static String biggestPalindrome(String string) {
        // xxsxx
        // xxxxx
        // x
        // 898
        // 99
        // chars from the string have to respect this condition i == length -i for each i
        if (string == null || string.isEmpty()) {
            return "";
        }
        if (string.length() == 1) {
            return string;
        }

        String biggest = "";
        for (int i = 0; i < string.length(); i++) {
            String pal = getPalindrome(string, i, i);
            if (pal.length() > biggest.length()) {
                biggest = pal;
            }

            pal = getPalindrome(string, i, i + 1);
            if (pal.length() > biggest.length()) {
                biggest = pal;
            }
        }
        return biggest;
    }

    private static String getPalindrome(String s, int i, int j) {
        while (i >= 0 && j < s.length() && s.charAt(i) == s.charAt(j)) {
            i--;
            j++;
        }
        return s.substring(i + 1, j);
    }

    public static void main(String[] args) {
        test1();
        test2();
        test3();
//        test4();
        test5();
    }

    private static void test1() {
        Assertions.assertEquals(9, romanToInt("IX"));
        Assertions.assertEquals(3, romanToInt("III"));
        Assertions.assertEquals(4, romanToInt("IV"));
        Assertions.assertEquals(58, romanToInt("LVIII"));
        Assertions.assertEquals(1994, romanToInt("MCMXCIV"));
    }

    private static void test2() {
        Assertions.assertEquals("IX", intToRoman(9));
        Assertions.assertEquals("III", intToRoman(3));
        Assertions.assertEquals("IV", intToRoman(4));
        Assertions.assertEquals("LVIII", intToRoman(58));
        Assertions.assertEquals("MCMXCIV", intToRoman(1994));
        Assertions.assertEquals("XIV", intToRoman(14));
        Assertions.assertEquals("CXL", intToRoman(140));
        Assertions.assertEquals("XIX", intToRoman(19));
        Assertions.assertEquals("CXC", intToRoman(190));
    }

    private static void test3() {
        Assertions.assertTrue(isPalindrome("x"));
        Assertions.assertTrue(isPalindrome("xx"));
        Assertions.assertTrue(isPalindrome("xxx"));
        Assertions.assertTrue(isPalindrome("xxxx"));
        Assertions.assertTrue(isPalindrome("123321"));
        Assertions.assertFalse(isPalindrome(""));
        Assertions.assertFalse(isPalindrome("xa"));
        Assertions.assertFalse(isPalindrome("aax"));
        Assertions.assertFalse(isPalindrome("aaxx"));
    }


    private static void test4() {
//        Assertions.assertEquals("aba", biggestPalindrome("aba"));
//        Assertions.assertEquals("aaa", biggestPalindrome("aaa"));
//        Assertions.assertEquals("xxx", biggestPalindrome("abxxx"));
//        Assertions.assertEquals("xxffxx", biggestPalindrome("xxxffxxg"));
//        Assertions.assertEquals("xxffxx", biggestPalindrome("xxffxxx"));
//        Assertions.assertEquals("fffffff", biggestPalindrome("xxffxxxefffffffkk"));
//        Assertions.assertEquals("xxfffffffxx", biggestPalindrome("xxffxxxfffffffxx"));
//        Assertions.assertEquals("z", biggestPalindrome("z"));
//        Assertions.assertEquals("zz", biggestPalindrome("zzx"));
//        Assertions.assertEquals("zz", biggestPalindrome("xzz"));
        Assertions.assertEquals("", biggestPalindrome("xzu"));
    }

    private static void test5() {
        Assertions.assertTrue(isPalindrome(1));
        Assertions.assertTrue(isPalindrome(11));
        Assertions.assertTrue(isPalindrome(111));
        Assertions.assertTrue(isPalindrome(121));
        Assertions.assertTrue(isPalindrome(123321));
        Assertions.assertFalse(isPalindrome(12));
        Assertions.assertFalse(isPalindrome(113));
        Assertions.assertFalse(isPalindrome(1122));
    }
}
