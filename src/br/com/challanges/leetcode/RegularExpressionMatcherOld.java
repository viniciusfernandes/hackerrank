package br.com.challanges.leetcode;

import br.com.challanges.algorithms.datastructure.utils.Assertions;

public class RegularExpressionMatcherOld {
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

        boolean isLastOperator() {
            return next != null && next.isAccept;
        }

        @Override
        public String toString() {
            return "State{" +
                    "c=" + c +
                    ", op=" + op +
                    ", isAccept=" + isAccept +
                    '}';
        }
    }

    RegularExpressionMatcherOld(String regex) {
        root = buildNFA(regex);
    }

    public boolean match(String input) {
        if (!isValidLength(input) || !isValidInput(input)) {
            return false;
        }
        return match(root, input, 0);
    }

    private boolean match(State state, String input, int i) {
        if (state == null) {
            return false;
        }
        if (i >= input.length()) {
            while (state.op == '*') {
                state = state.next;
            }
            return state.isAccept;
        }
        if (state.op == '*') {
            if (state.c == '.') {
                if (state.isLastOperator()) {
                    return true;
                }
                // this is the case a.*.
                if (state.next.c == '.') {
                    return true;
                }
                while (i < input.length() && state.next.c != input.charAt(i)) {
                    i++;
                }
                return match(state.next, input, i);
            } else {
                if (state.c != input.charAt(i)) {
                    return match(state.next, input, i);
                }
                int stop = input.length() - 1;
                if (!state.isLastOperator()) {
                    stop--;
                }
                while (i <= stop && state.c == input.charAt(i)) {
                    i++;
                }
                return match(state.next, input, i);
            }

        }
        if (i < input.length() && (state.c == '.' || state.c == input.charAt(i))) {
            return match(state.next, input, i + 1);
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
            boolean isLast = i == chars.length - 1;
            if (last != null && last.op == '*' && c == '*') {
                continue;
            }
            if (last != null && last.op == '*' && last.c == '.' && c == '.' && !isLast) {
                continue;
            }
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
//        test1();
//        test2();
//        test3();
//        test4();
//        test5();
//        test6();
//        test7();
//        test8();
//        test9();
//        test10();
//        test11();
//        test12();
//        test13();
//        test14();
//        test15();
//        test16();
//        test17();
        test18();
    }

    private static void test1() {
        RegularExpressionMatcherOld matcher = new RegularExpressionMatcherOld("abc");
        Assertions.assertFalse(matcher.match(""));
        Assertions.assertFalse(matcher.match("a"));
        Assertions.assertFalse(matcher.match("ab"));
        Assertions.assertTrue(matcher.match("abc"));
        Assertions.assertFalse(matcher.match("b"));
        Assertions.assertFalse(matcher.match("c"));

    }

    private static void test2() {
        RegularExpressionMatcherOld matcher = new RegularExpressionMatcherOld("a*");
        Assertions.assertFalse(matcher.match(""));
        Assertions.assertTrue(matcher.match("a"));
        Assertions.assertTrue(matcher.match("aa"));
        Assertions.assertTrue(matcher.match("aaa"));
        Assertions.assertFalse(matcher.match("b"));
        Assertions.assertFalse(matcher.match("abxxxxxxx"));
    }


    private static void test3() {
        RegularExpressionMatcherOld matcher = new RegularExpressionMatcherOld("a");
        Assertions.assertFalse(matcher.match(""));
        Assertions.assertTrue(matcher.match("a"));
        Assertions.assertFalse(matcher.match("aa"));
        Assertions.assertFalse(matcher.match("b"));
        Assertions.assertFalse(matcher.match("abxxxxxxx"));
    }

    private static void test4() {
        RegularExpressionMatcherOld matcher = new RegularExpressionMatcherOld("a*b");
        Assertions.assertFalse(matcher.match(""));
        Assertions.assertTrue(matcher.match("ab"));
        Assertions.assertTrue(matcher.match("aab"));
        Assertions.assertTrue(matcher.match("aaab"));
        Assertions.assertTrue(matcher.match("b"));
        Assertions.assertFalse(matcher.match("abxxxxxxx"));
    }

    private static void test5() {
        RegularExpressionMatcherOld matcher = new RegularExpressionMatcherOld("a.*b");
        Assertions.assertFalse(matcher.match(""));
        Assertions.assertTrue(matcher.match("acb"));
        Assertions.assertTrue(matcher.match("acvb"));
        Assertions.assertTrue(matcher.match("ab"));
    }

    private static void test6() {
        RegularExpressionMatcherOld matcher = new RegularExpressionMatcherOld("a*.b");
        Assertions.assertTrue(matcher.match("acb"));
        Assertions.assertFalse(matcher.match(""));
        Assertions.assertTrue(matcher.match("cb"));

        Assertions.assertFalse(matcher.match("acxb"));
        Assertions.assertFalse(matcher.match("a"));
        Assertions.assertFalse(matcher.match("b"));
    }

    private static void test7() {
        RegularExpressionMatcherOld matcher = new RegularExpressionMatcherOld("a.*.b");
        Assertions.assertFalse(matcher.match(""));
        Assertions.assertFalse(matcher.match("cb"));
        Assertions.assertTrue(matcher.match("acb"));
        Assertions.assertTrue(matcher.match("acxb"));
        Assertions.assertTrue(matcher.match("aqwertxb"));
        Assertions.assertFalse(matcher.match("a"));
        Assertions.assertFalse(matcher.match("b"));
    }

    private static void test8() {
        RegularExpressionMatcherOld matcher = new RegularExpressionMatcherOld("a.*.*.*b");
        Assertions.assertFalse(matcher.match(""));
        Assertions.assertFalse(matcher.match("cb"));
        Assertions.assertTrue(matcher.match("acb"));
        Assertions.assertTrue(matcher.match("acxb"));
        Assertions.assertTrue(matcher.match("aqwertxb"));
        Assertions.assertFalse(matcher.match("a"));
        Assertions.assertFalse(matcher.match("b"));
    }

    private static void test9() {
        RegularExpressionMatcherOld matcher = new RegularExpressionMatcherOld("a.*.");
        Assertions.assertFalse(matcher.match(""));
        Assertions.assertFalse(matcher.match("cb"));
        Assertions.assertTrue(matcher.match("acb"));
        Assertions.assertTrue(matcher.match("acxb"));
        Assertions.assertTrue(matcher.match("aqwertx"));
        Assertions.assertFalse(matcher.match("a"));
        Assertions.assertTrue(matcher.match("ax"));
        Assertions.assertFalse(matcher.match("b"));
    }

    private static void test10() {
        RegularExpressionMatcherOld matcher = new RegularExpressionMatcherOld("a..");
        Assertions.assertFalse(matcher.match(""));
        Assertions.assertFalse(matcher.match("cb"));
        Assertions.assertTrue(matcher.match("acb"));
        Assertions.assertFalse(matcher.match("acxb"));
        Assertions.assertFalse(matcher.match("a"));
    }

    private static void test11() {
        RegularExpressionMatcherOld matcher = new RegularExpressionMatcherOld("..");
        Assertions.assertFalse(matcher.match(""));
        Assertions.assertTrue(matcher.match("cb"));
        Assertions.assertTrue(matcher.match("xz"));
        Assertions.assertFalse(matcher.match("acx"));
        Assertions.assertFalse(matcher.match("a"));
    }

    private static void test12() {
        RegularExpressionMatcherOld matcher = new RegularExpressionMatcherOld("a*a");
        Assertions.assertFalse(matcher.match(""));
        Assertions.assertTrue(matcher.match("a"));
        Assertions.assertTrue(matcher.match("aa"));
        Assertions.assertTrue(matcher.match("aaa"));
        Assertions.assertFalse(matcher.match("b"));
        Assertions.assertFalse(matcher.match("abxxxxxxx"));
    }

    private static void test13() {
        RegularExpressionMatcherOld matcher = new RegularExpressionMatcherOld("aa*a");
        Assertions.assertFalse(matcher.match(""));
        Assertions.assertFalse(matcher.match("a"));
        Assertions.assertTrue(matcher.match("aa"));
        Assertions.assertTrue(matcher.match("aaa"));
        Assertions.assertTrue(matcher.match("aaaa"));
        Assertions.assertFalse(matcher.match("b"));
        Assertions.assertFalse(matcher.match("abxxxxxxx"));
    }

    private static void test14() {
        RegularExpressionMatcherOld matcher = new RegularExpressionMatcherOld("aa*b");
        Assertions.assertFalse(matcher.match(""));
        Assertions.assertFalse(matcher.match("a"));
        Assertions.assertFalse(matcher.match("aa"));
        Assertions.assertTrue(matcher.match("ab"));
        Assertions.assertTrue(matcher.match("aab"));
        Assertions.assertTrue(matcher.match("aaaab"));
        Assertions.assertFalse(matcher.match("b"));
        Assertions.assertFalse(matcher.match("abx"));
    }

    private static void test15() {
        RegularExpressionMatcherOld matcher = new RegularExpressionMatcherOld("a**b");
        Assertions.assertFalse(matcher.match(""));
        Assertions.assertFalse(matcher.match("a"));
        Assertions.assertFalse(matcher.match("aa"));
        Assertions.assertTrue(matcher.match("ab"));
        Assertions.assertTrue(matcher.match("aab"));
        Assertions.assertTrue(matcher.match("aaaab"));
        Assertions.assertTrue(matcher.match("b"));
        Assertions.assertFalse(matcher.match("abx"));
    }

    private static void test16() {
        RegularExpressionMatcherOld matcher = new RegularExpressionMatcherOld("ab*a*c*a");
        Assertions.assertFalse(matcher.match(""));
        Assertions.assertTrue(matcher.match("aaa"));
        Assertions.assertTrue(matcher.match("aa"));
        Assertions.assertTrue(matcher.match("aba"));
        Assertions.assertFalse(matcher.match("ab"));
        Assertions.assertTrue(matcher.match("abca"));
        Assertions.assertTrue(matcher.match("aca"));
        Assertions.assertTrue(matcher.match("aaca"));
        Assertions.assertTrue(matcher.match("abbaacca"));
        Assertions.assertTrue(matcher.match("aaacca"));
        Assertions.assertTrue(matcher.match("abbcca"));
        Assertions.assertTrue(matcher.match("acca"));
        Assertions.assertTrue(matcher.match("abba"));
    }

    private static void test17() {
        RegularExpressionMatcherOld matcher = new RegularExpressionMatcherOld("ab*");
        Assertions.assertFalse(matcher.match(""));
        Assertions.assertTrue(matcher.match("a"));
        Assertions.assertFalse(matcher.match("aa"));
        Assertions.assertTrue(matcher.match("ab"));
        Assertions.assertTrue(matcher.match("abbb"));
        Assertions.assertFalse(matcher.match("aab"));
        Assertions.assertFalse(matcher.match("b"));
        Assertions.assertFalse(matcher.match("abx"));
    }

    private static void test18() {
        RegularExpressionMatcherOld matcher = new RegularExpressionMatcherOld(".*..a*");
//        Assertions.assertFalse(matcher.match(""));
        Assertions.assertTrue(matcher.match("a"));
        Assertions.assertFalse(matcher.match("aa"));
    }
}
