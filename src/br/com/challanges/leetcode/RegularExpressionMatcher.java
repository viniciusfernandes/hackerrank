package br.com.challanges.leetcode;

import java.util.HashSet;
import java.util.Set;

public class RegularExpressionMatcher {
    private final State root;

    private static class State {
        char c;         // transition character, or 0 for epsilon
        State out1;
        State out2;
        boolean isAccept;

        State(char c) {
            this.c = c;
        }

        State(char c, boolean isAccept) {
            this.c = c;
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
        return match(root, input, 0, new HashSet<>());
    }

    private boolean match(State state, String input, int i, Set<String> visited) {
        if (state == null) {
            return false;
        }
        if (!visited.add(state.hashCode() + ":" + i)) {
            return false;
        }
        if (state.isAccept && i == input.length()) {
            return true;
        }
        if (state.c == 0) {
            return match(state.out1, input, i, visited) ||
                    match(state.out2, input, i, visited);
        }
        if (i < input.length() && (state.c == '.' || state.c == input.charAt(i))) {
            return match(state.out1, input, i + 1, visited);
        }
        return false;
    }


    // 🆕 NEW helper replaces `findTail()` and prevents infinite ε-loops
    private static State findAttachPoint(State s) {
        Set<State> visited = new HashSet<>();          // 🆕 avoid ε-cycles
        while (s != null && s.out1 != null && s.c != 0) {
            if (!visited.add(s)) break;                // 🆕 prevent looping forever
            s = s.out1;
        }
        return s;
    }

    private State buildNFA(String pattern) {
        if (!isValidLength(pattern) || !isValidRegex(pattern)) {
            return null;
        }

        char[] chars = pattern.toCharArray();
        State start = null;
        State last = null;   // last created node
        State prev = null;   // track previous literal or '.'

        for (char c : chars) {
            if (c == '*') {
                if (prev == null) {
                    throw new IllegalStateException("Invalid pattern: '*' cannot appear first");
                }

                State split = new State((char) 0);
                State accept = new State((char) 0, true);

                split.out1 = prev;
                split.out2 = accept;
                prev.out1 = split;

                if (last != prev) {
                    last.out1 = split;
                }

                if (start == prev) {
                    start = split;
                }

                last = accept;
                prev = null;
                continue;
            } else if (c == '.') {
                State dot = new State('.');
                State accept = new State((char) 0, true);
                dot.out1 = accept;

                if (last != null) {
                    last.out1 = dot;
                } else {
                    start = dot;
                }

                last = accept;
                prev = dot;
                continue;
            }

            if (last != null) {
                State next = new State(c);
                last.out1 = next;
                last = next;
                prev = next;
            } else {
                start = new State(c);   // consumes one character: 'a'
                State accept = new State((char) 0, true);  // ε accept (no transitions)
                start.out1 = accept;
                last = accept;
                prev = start;
            }

        }

        if (last != null) {
            last.isAccept = true;
        }

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
        RegularExpressionMatcher matcher = new RegularExpressionMatcher("a*b");
//        System.out.println(matcher.match(""));
        System.out.println(matcher.match("aab"));
//        System.out.println(matcher.match("aa"));
//        System.out.println(matcher.match("aaa"));
        System.out.println(matcher.match("b"));
        System.out.println(matcher.match("abxxxxxxx"));


    }


}
