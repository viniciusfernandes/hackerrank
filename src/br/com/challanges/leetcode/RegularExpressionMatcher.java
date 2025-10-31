package br.com.challanges.leetcode;

import br.com.challanges.algorithms.datastructure.utils.Assertions;

public class RegularExpressionMatcher {
    private final State root;

    private static class State {
        final char c;
        final char op;
        State next;
        boolean isAccept;

        State(char c) {
            this.c = c;
            this.op = c;
        }

        State(char c, char operator) {
            this.c = c;
            this.op = operator;
        }

        State(char c, boolean isAccept) {
            this(c);
            this.isAccept = isAccept;
        }

        @Override
        public String toString() {
            return "State{" +
                    "c=" + c +
                    ", isAccept=" + isAccept +
                    '}';
        }
    }

    RegularExpressionMatcher(String regex) {
        root = buildNFA(regex);
    }

    public boolean match(String input) {
        if (!isValidLength(input) || !isValidInput(input)) {
            return false;
        }
        return match(root, input, 0);
    }

    private boolean match(State curr, String input, int i) {
        if (curr == null) {
            return false;
        }
        if (curr.isAccept && i == input.length()) {
            return true;
        }
        if (curr.op == '*') {
            if (curr.next.c == input.charAt(i)) {
                return match(curr.next, input, i);
            }
            while (i < input.length() && curr.c == input.charAt(i)) {
                i++;
            }
            return match(curr.next, input, i);
        }
        if (i < input.length() && (curr.c == '.' || curr.c == input.charAt(i))) {
            return match(curr.next, input, i + 1);
        }
        return false;
    }

    private State buildNFA(String pattern) {
        if (!isValidLength(pattern) || !isValidRegex(pattern)) {
            return null;
        }

        char[] chars = pattern.toCharArray();
        State start = null;
        State last = null;
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            State state;
            if (i + 1 < chars.length && chars[i + 1] == '*') {
                if (c == '*' && last == null) {
                    throw new IllegalStateException("Invalid pattern: '*' cannot appear first");
                }
                state = new State(c, '*');
                i++;
            } else {
                state = new State(c);
            }
            if (last != null) {

                last.next = state;
                last = state;
            } else {
                start = state;
                last = start;
            }
        }
        last.next = new State('@', true);
        return start;
    }

    private boolean isValidLength(String input) {
        return input != null && !input.isBlank() && input.length() <= 20;
    }

    private boolean isValidInput(String input) {
        char[] chars = input.toCharArray();
        for (char aChar : chars) {
            if (!isLowercaseLetter(aChar)) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidRegex(String input) {
        char[] chars = input.toCharArray();
        for (char aChar : chars) {
            if ((!isOperator(aChar) && isLowercaseLetter(aChar)) || !isOperator(aChar) && !isLowercaseLetter(aChar)) {
                return false;
            }
        }
        return true;
    }

    private boolean isLowercaseLetter(char c) {
        return c >= 'a' && c <= 'z';
    }

    private boolean isOperator(char c) {
        return isLowercaseLetter(c) || c == '.' || c == '*';
    }

    public static void main(String[] args) {
        test1();
        test2();
        test3();
        test4();
    }

    private static void test1() {
        RegularExpressionMatcher matcher = new RegularExpressionMatcher("abc");
        Assertions.assertFalse(matcher.match(""));
        Assertions.assertFalse(matcher.match("a"));
        Assertions.assertFalse(matcher.match("ab"));
        Assertions.assertTrue(matcher.match("abc"));
        Assertions.assertFalse(matcher.match("b"));
        Assertions.assertFalse(matcher.match("c"));

    }

    private static void test2() {
        RegularExpressionMatcher matcher = new RegularExpressionMatcher("a*");
        Assertions.assertFalse(matcher.match(""));
        Assertions.assertTrue(matcher.match("a"));
        Assertions.assertTrue(matcher.match("aa"));
        Assertions.assertTrue(matcher.match("aaa"));
        Assertions.assertFalse(matcher.match("b"));
        Assertions.assertFalse(matcher.match("abxxxxxxx"));
    }

    private static void test3() {
        RegularExpressionMatcher matcher = new RegularExpressionMatcher("a");
        Assertions.assertFalse(matcher.match(""));
        Assertions.assertTrue(matcher.match("a"));
        Assertions.assertFalse(matcher.match("aa"));
        Assertions.assertFalse(matcher.match("b"));
        Assertions.assertFalse(matcher.match("abxxxxxxx"));
    }

    private static void test4() {
        RegularExpressionMatcher matcher = new RegularExpressionMatcher("a*b");
        Assertions.assertFalse(matcher.match(""));
        Assertions.assertTrue(matcher.match("ab"));
        Assertions.assertTrue(matcher.match("aab"));
        Assertions.assertTrue(matcher.match("aaab"));
        Assertions.assertTrue(matcher.match("b"));
        Assertions.assertFalse(matcher.match("abxxxxxxx"));
    }

}
