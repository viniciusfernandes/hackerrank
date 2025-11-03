package br.com.challanges.leetcode;

import br.com.challanges.algorithms.datastructure.utils.Assertions;

import java.util.Map;

public class RomanToInt {
    private Map<Character, Integer> values =
            Map.of(
                    'I', 1,
                    'V', 5,
                    'X', 10,
                    'L', 50,
                    'C', 100,
                    'D', 500,
                    'M', 1000
            );

    private static final int[] VALUES = {
            1000, 900, 500, 400,
            100, 90, 50, 40,
            10, 9, 5, 4, 1
    };

    private static final String[] SYMBOLS = {
            "M", "CM", "D", "CD",
            "C", "XC", "L", "XL",
            "X", "IX", "V", "IV", "I"
    };

    public RomanToInt() {
    }

    public String intToRoman(int i) {
        String s = "";
        int k = 0;
        while (i > 0) {
            int val = VALUES[k];
            if (i >= val) {
                i -= val;
                String t = SYMBOLS[k];
                s += t;
                continue;
            }
            k++;
        }
        return s;
    }


    public int romanToInt(String s) {
        int sum = 0;
        int next = 0, curr;
        int i = 0;
        do {
            curr = values.get(s.charAt(i));
            if (i + 1 < s.length()) {
                next = values.get(s.charAt(i + 1));
            }
            if (curr >= next) {
                sum += curr;
                i++;
            } else {
                sum += (next - curr);
                i += 2;
            }

        } while (i < s.length());
        return sum;
    }

    public static void main(String[] args) {
//        test1();
        test2();
    }

    private static void test1() {
        RomanToInt romanToInt = new RomanToInt();
        Assertions.assertEquals(9, romanToInt.romanToInt("IX"));
        Assertions.assertEquals(3, romanToInt.romanToInt("III"));
        Assertions.assertEquals(4, romanToInt.romanToInt("IV"));
        Assertions.assertEquals(58, romanToInt.romanToInt("LVIII"));
        Assertions.assertEquals(1994, romanToInt.romanToInt("MCMXCIV"));
    }

    private static void test2() {
        RomanToInt romanToInt = new RomanToInt();
        Assertions.assertEquals("IX", romanToInt.intToRoman(9));
        Assertions.assertEquals("III", romanToInt.intToRoman(3));
        Assertions.assertEquals("IV", romanToInt.intToRoman(4));
        Assertions.assertEquals("LVIII", romanToInt.intToRoman(58));
        Assertions.assertEquals("MCMXCIV", romanToInt.intToRoman(1994));
    }
}
