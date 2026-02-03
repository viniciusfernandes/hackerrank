package br.com.challanges;

import br.com.challanges.algorithms.datastructure.utils.Assertions;

import java.util.*;

class HexadecimalParser {
    static Map<Integer, String> map = new HashMap<>();

    static {

        map.put(0, "0");
        map.put(1, "1");
        map.put(2, "2");
        map.put(3, "3");
        map.put(4, "4");
        map.put(5, "5");
        map.put(6, "6");
        map.put(7, "7");
        map.put(8, "8");
        map.put(9, "9");
        map.put(10, "a");
        map.put(11, "b");
        map.put(12, "c");
        map.put(13, "d");
        map.put(14, "e");
        map.put(15, "f");
    }

    static Map<String, Integer> symbol = new HashMap<>();

    static {
        symbol.put("0", 0);
        symbol.put("1", 1);
        symbol.put("2", 2);
        symbol.put("3", 3);
        symbol.put("4", 4);
        symbol.put("5", 5);
        symbol.put("6", 6);
        symbol.put("7", 7);
        symbol.put("8", 8);
        symbol.put("9", 9);
        symbol.put("a", 10);
        symbol.put("b", 11);
        symbol.put("c", 12);
        symbol.put("d", 13);
        symbol.put("e", 14);
        symbol.put("f", 15);
    }


    public static String toHexa(int n) {
        if (n == 0) {
            return map.get(0);
        }
        String H = "";
        while (n > 0) {
            int r = n % 16;
            n -= r;
            n /= 16;
            String t = map.get(r);
            t += H;
            H = t;
        }
        return H;
    }

    public static int fromHexa(String s) {
        int l = s.length() - 1;
        int h = 0;
        int total = 0;
        do {
            String c = String.valueOf(s.charAt(l));
            if (!symbol.containsKey(c)) {
                throw new IllegalArgumentException();
            }
            int v = symbol.get(c);
            if (h == 0) {
                total = v;
                h = 16;
            } else {
                total += v * h;
                h *= 16;
            }

        } while (--l >= 0);
        return total;
    }

    public static void main(String[] args) {
        assertToHexa();
        assertFromHexa();
    }

    private static void assertToHexa() {
        Assertions.assertEquals("aa", toHexa(170));
        Assertions.assertEquals("abc", toHexa(2748));
        Assertions.assertEquals("0", toHexa(0));
        Assertions.assertEquals("10", toHexa(16));
        Assertions.assertEquals("1", toHexa(1));
        Assertions.assertEquals("2", toHexa(2));
        Assertions.assertEquals("3", toHexa(3));
        Assertions.assertEquals("4", toHexa(4));
        Assertions.assertEquals("5", toHexa(5));
        Assertions.assertEquals("6", toHexa(6));
        Assertions.assertEquals("7", toHexa(7));
        Assertions.assertEquals("8", toHexa(8));
        Assertions.assertEquals("9", toHexa(9));
        Assertions.assertEquals("a", toHexa(10));
        Assertions.assertEquals("faf", toHexa(4015));
    }

    private static void assertFromHexa() {
        Assertions.assertEquals(170, fromHexa("aa"));
        Assertions.assertEquals(0, fromHexa("0"));
        Assertions.assertEquals(16, fromHexa("10"));
        Assertions.assertEquals(10, fromHexa("a"));
        Assertions.assertEquals(1, fromHexa("1"));
        Assertions.assertEquals(4015, fromHexa("faf"));
        Assertions.assertEquals(2748, fromHexa("abc"));
        Assertions.assertEquals(256, fromHexa("100"));
    }
}