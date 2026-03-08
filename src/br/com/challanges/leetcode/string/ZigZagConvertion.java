package br.com.challanges.leetcode.string;

import br.com.challanges.algorithms.datastructure.utils.Assertions;

public class ZigZagConvertion {
    public static String convert(String s, int numRows) {
        if (s.length() <= numRows || numRows <= 1) {
            return s;
        }

        // this have to be considered as an index
        int cStep = 2 * (numRows - 1);
        if (cStep <= 0) {
            return s;
        }
        final int numCols = s.length() / cStep;
        char[] chars = s.toCharArray();
        // char cursor
        int c = 0;
        // diagonal cursor
        int d = 0;
        // next char form the first row
        int row = 0;
        int count = 1;
        boolean isEnd;
        StringBuilder conv = new StringBuilder();
        while (count <= chars.length) {
            isEnd = c / cStep > numCols || c >= s.length();
            if (!isEnd) {
                conv.append(chars[c]);
                if (d > 0 && c + d < chars.length && c % cStep != 0 && c < s.length() - 1) {
                    conv.append(chars[c + d]);
                    count++;
                }
                c += cStep;
                count++;
                continue;
            }
            row++;
            c = row;
            d = d == 0 ? 2 * numRows - c - 3 : d - 2;
        }
        return conv.toString();
    }

    public static void main(String[] args) {
        Assertions.assertEquals("PAHNAPLSIIGYIR", convert("PAYPALISHIRING", 3));
        Assertions.assertEquals("PINALSIGYAHRPI", convert("PAYPALISHIRING", 4));
        Assertions.assertEquals("A", convert("A", 1));
        Assertions.assertEquals("qwert", convert("qwert", 0));
        Assertions.assertEquals("ABC", convert("ABC", 3));
        Assertions.assertEquals("ABCED", convert("ABCDE", 4));
    }
}
