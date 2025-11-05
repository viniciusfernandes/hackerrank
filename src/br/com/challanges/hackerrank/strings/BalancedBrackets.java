package br.com.challanges.hackerrank.strings;

import br.com.challanges.algorithms.datastructure.Stack;

import java.util.HashMap;
import java.util.Map;

public class BalancedBrackets {
    public static String isBalanced(String s) {
        Map<Character, Character> closure = new HashMap<>();
        closure.put('(', ')');
        closure.put('[', ']');
        closure.put('{', '}');
        Stack<Character> stack = new Stack<>();

        char[] arr = s.toCharArray();
        for (char c : arr) {
            if (c == '}' || c == ']' || c == ')') {
                if (stack.isEmpty()) {
                    return "NO";
                }
                char prev = stack.pop();
                if (!closure.getOrDefault(prev, '@').equals(c)) {
                    return "NO";
                }
            } else {
                stack.push(c);
            }
        }
        return stack.isEmpty() ? "YES" : "NO";
    }

    public static void main(String[] args) {
        System.out.println(isBalanced("{()}(("));
    }

}
