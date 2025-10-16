package br.com.challanges.leetcode;

import br.com.challanges.algorithms.datastructure.utils.Assertions;

import java.util.*;

public class OpenParenthesis {


    public boolean isVAlid(String s) {
        char[] p = s.toCharArray();
        Queue<Character> q = new LinkedList<>();
        Map<Character, Character> close = new HashMap<>();
        close.put('(', ')');
        close.put('[', ']');
        close.put('{', '}');
        for (int i = 0; i < p.length; i++) {
            char c = p[i];
            if (i + 1 < p.length && isOpen(c) && isClose(p[i + 1]) && close.get(c) != p[i + 1]) {
                return false;
            } else if (isClose(c)) {
                q.poll();
            } else if (isOpen(c)) {
                q.add(c);
            }
        }
        return q.isEmpty();
    }

    private boolean isOpen(char c) {
        return c == '[' || c == '{' || c == '(';
    }

    private boolean isClose(char c) {
        return !isOpen(c);
    }

    public static void main(String[] args) {
        OpenParenthesis o = new OpenParenthesis();
        Assertions.assertTrue(o.isVAlid("()"));
        Assertions.assertTrue(o.isVAlid("()[]{}"));
        Assertions.assertFalse(o.isVAlid("(]"));
        Assertions.assertTrue(o.isVAlid("([{}])"));
        Assertions.assertFalse(o.isVAlid("([{])"));
        Assertions.assertTrue(o.isVAlid("(){[]}"));
    }
}
