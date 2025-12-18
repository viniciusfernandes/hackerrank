package br.com.challanges.leetcode;

import br.com.challanges.algorithms.datastructure.utils.Assertions;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class OpenParenthesis {


    public boolean isVAlid(String s) {
        Stack<Character> q = new Stack<>();
        Map<Character, Character> closureOf = new HashMap<>();
        closureOf.put('(', ')');
        closureOf.put('[', ']');
        closureOf.put('{', '}');
        for (char c : s.toCharArray()) {
            if (!isClose(c)) {
                q.add(c);
                continue;
            }
            Character x = q.pop();
            if (closureOf.get(x) == c) {
                continue;
            }
            return false;
        }
        return q.isEmpty();
    }


    private boolean isClose(char c) {
        return c == '}' || c == ']' || c == ')';
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
