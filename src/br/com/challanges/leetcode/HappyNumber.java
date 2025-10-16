package br.com.challanges.leetcode;

import br.com.challanges.algorithms.datastructure.utils.Assertions;

import java.util.ArrayList;

public class HappyNumber {


    public boolean isHappy(int n) {
        if (n == 1) {
            return true;
        }

        if (n > 1 && n <= 9) {
            return false;
        }
        var l = new ArrayList<Integer>();
        while (true) {
            l.add(n % 10);
            n /= 10;
            if (n == 0) {
                for (var x : l) {
                    n += x * x;
                }
            }
            if (n < 10) {
                return n == 1;
            }
            l.clear();
        }
    }

    private boolean isOpen(char c) {
        return c == '[' || c == '{' || c == '(';
    }

    private boolean isClose(char c) {
        return !isOpen(c);
    }

    public static void main(String[] args) {
        HappyNumber o = new HappyNumber();
        Assertions.assertTrue(o.isHappy(1));
        Assertions.assertTrue(o.isHappy(10));
        Assertions.assertTrue(o.isHappy(1000000000));
        Assertions.assertFalse(o.isHappy(23));
        Assertions.assertTrue(o.isHappy(19));
    }
}
