package br.com.challanges;

import br.com.challanges.algorithms.datastructure.utils.Assertions;

import java.util.*;

class ToRGB {
    static class RGB {
        final Integer r;
        final Integer g;
        final Integer b;

        RGB(Integer r, Integer g, Integer b) {
            this.r = r;
            this.g = g;
            this.b = b;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            RGB rgb = (RGB) o;
            return Objects.equals(r, rgb.r) && Objects.equals(g, rgb.g) && Objects.equals(b, rgb.b);
        }

        @Override
        public int hashCode() {
            return Objects.hash(r, g, b);
        }

        @Override
        public String toString() {
            return "RGB{" +
                    "r=" + r +
                    ", g=" + g +
                    ", b=" + b +
                    '}';
        }
    }

    public static RGB parse(String color) {
        if (color == null || color.isBlank() || color.length() > 6) {
            throw new IllegalArgumentException();
        }
        char[] chars = color.toCharArray();
        String h = "";
        int c = 0;
        Integer[] codes = new Integer[3];
        int j = 0;
        for (char aChar : chars) {
            if (c < 2) {
                h += aChar;
                c++;
            } else {
                codes[j++] = HexadecimalParser.fromHexa(h);
                c = 1;
                h = "" + aChar;
            }
        }
        if (!h.isBlank()) {
            codes[2] = HexadecimalParser.fromHexa(h);
        }
        return new RGB(codes[0], codes[1], codes[2]);
    }

    public static void main(String[] args) {
        Assertions.assertEquals(new RGB(16, 16, 16), parse("101010"));
        Assertions.assertEquals(new RGB(0, 0, 0), parse("000000"));
        Assertions.assertEquals(new RGB(10, 11, 12), parse("0a0b0c"));
        Assertions.assertEquals(new RGB(255, 255, 255), parse("ffffff"));
    }


}