package br.com.challanges;

import br.com.challanges.algorithms.datastructure.Stack;
import br.com.challanges.algorithms.datastructure.utils.Assertions;

class DisappearingPairs {
    public static String solution(String S) {
        if (S == null || S.length() <= 1) {
            return S;
        }
        Stack<Character> stack = new Stack<>();
        char[] chars = S.toCharArray();
        stack.push(chars[0]);
        for (int i = 1; i < chars.length; i++) {
            char c = stack.isEmpty()? 'x': stack.peek();
            if (c != chars[i]) {
                stack.push(chars[i]);
            } else {
                stack.pop();
            }
        }
        StringBuilder sb = new StringBuilder();
        for (var c : stack) {
            sb.append(c);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Assertions.assertEquals("CA", solution("ACCAABBC"));
        Assertions.assertEquals("", solution("ABCBBCBA"));
        Assertions.assertEquals("A", solution("A"));
        Assertions.assertEquals("BA", solution("AB"));
    }
}
