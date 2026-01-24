package br.com.challanges.leetcode;

import br.com.challanges.algorithms.datastructure.utils.Assertions;

import java.util.*;

enum Type {EPSILON, CHAR, DOT, ACCEPT}

// ===== NFA Core =====
final class State {
    final Type type;
    final char ch;       // only for CHAR
    State next;          // primary edge
    State alt;           // secondary edge (used by epsilon "split" states)

    State(Type type) {
        this(type, '\0');
    }

    State(Type type, char ch) {
        this.type = type;
        this.ch = ch;
    }

    boolean consumes(char c) {
        return (type == Type.DOT) || (type == Type.CHAR && ch == c);
    }

    @Override
    public String toString() {
        return "{" +
                "ch=" + ch +
                ", type=" + type +
                '}';
    }
}

// ===== Builder: pattern -> NFA (linked states) =====
final class RegexNfaBuilder {
    /**
     * Builds an NFA for the given pattern using only '.', '*'.
     * Uses "split" EPSILON states for Kleene star:
     * <p>
     * For token T* we build:
     * tail -> [SPLIT ε] -> (to LOOP) ----> [T node] ----> (back to SPLIT)
     * -> (to AFTER)  (zero occurrence path)
     */
    public State build(String pattern) {
        State start = new State(Type.EPSILON); // dummy head
        State tail = start;

        for (int i = 0; i < pattern.length(); i++) {
            char c = pattern.charAt(i);
            boolean isStar = (i + 1 < pattern.length() && pattern.charAt(i + 1) == '*');
            // skip first and last positons
            if (isStar) {
                // Build T* fragment
                State split = new State(Type.EPSILON);       // split to loop or skip
                State loop = (c == '.')
                        ? new State(Type.DOT)
                        : new State(Type.CHAR, c);
                State after = new State(Type.EPSILON);       // continue after star

                // Wire up: tail -> split
                tail.next = split;

                // split.next = loop (take the loop path), split.alt = after (skip zero times)
                split.next = loop;
                split.alt = after;

                // loop.next goes back to split (Kleene closure)
                loop.next = split;

                // The new "tail" for subsequent tokens is AFTER
                tail = after;

                i++; // skip '*'
            } else {
                // Single token ('.' or literal char)
                State node = (c == '.')
                        ? new State(Type.DOT)
                        : new State(Type.CHAR, c);
                tail.next = node;
                tail = node;
            }
        }

        // Final accept state
        tail.next = new State(Type.ACCEPT);
        return start; // return start (dummy head with epsilon -> first real)
    }
}

// ===== Simulator: run NFA against input =====
final class RegexAutomaton {
    final String pattern;
    State start;
    State tail;

    RegexAutomaton(String pattern) {
        this.pattern = pattern;
    }
    public boolean match(String text) {
        return match(text, pattern, 0, 0);
    }
    private boolean match(String text, String pattern, int i, int j) {
        // === Base cases ===

        // If we reached the end of the pattern:
        if (j == pattern.length()) {
            // Match only if text also fully consumed
            return i == text.length();
        }

        // Determine if first characters match
        boolean firstMatch =
                (i < text.length()) &&
                        (pattern.charAt(j) == text.charAt(i) || pattern.charAt(j) == '.');

        // === Handle '*' case ===
        if (j + 1 < pattern.length() && pattern.charAt(j + 1) == '*') {
            // Two possibilities:
            // 1️⃣ Skip "x*" entirely  (zero occurrences)
            // 2️⃣ Use it once, if it matches, and stay on same pattern token
            return (
                    match(text, pattern, i, j + 2) ||
                            (firstMatch && match(text, pattern, i + 1, j))
            );
        }

        // === Normal character ===
        return firstMatch && match(text, pattern, i + 1, j + 1);
    }

    public boolean matchOld(String text) {
        State start = new RegexNfaBuilder().build(pattern);

        // Current set = epsilon-closure of start
        Set<State> current = epsilonClosure(Set.of(start));

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            Set<State> step = new HashSet<>();

            for (State s : current) {
                if (s.type == Type.CHAR || s.type == Type.DOT) {
                    if (s.consumes(c)) {
                        // After consuming, move to epsilon-closure of s.next
                        if (s.next != null) {
                            step.addAll(epsilonClosure(Set.of(s.next)));
                        }
                    }
                }
            }
            current = step;

            if (current.isEmpty()) return false; // early fail
        }

        // Accept if any state in the epsilon-closure is ACCEPT
        for (State s : current) {
            if (reachesAcceptViaEpsilon(s)) return true;
            if (s.type == Type.ACCEPT) return true;
        }
        return false;
    }

    // Compute epsilon-closure starting from a set of states
    private Set<State> epsilonClosure(Set<State> seeds) {
        //FILO
        Deque<State> stack = new ArrayDeque<>(seeds);
        Set<State> seen = new HashSet<>(seeds);

        while (!stack.isEmpty()) {
            State s = stack.pop();
            if (s.type == Type.EPSILON) {
                if (s.next != null && seen.add(s.next)) {
                    stack.push(s.next);
                }
                if (s.alt != null && seen.add(s.alt)) {
                    stack.push(s.alt);
                }
            }
        }
        return seen;
    }

    // Check if ACCEPT is reachable through only epsilon paths
    private boolean reachesAcceptViaEpsilon(State s) {
        Deque<State> stack = new ArrayDeque<>();
        Set<State> seen = new HashSet<>();
        stack.push(s);
        seen.add(s);

        while (!stack.isEmpty()) {
            State cur = stack.pop();
            if (cur.type == Type.ACCEPT) return true;
            if (cur.type == Type.EPSILON) {
                if (cur.next != null && seen.add(cur.next)) stack.push(cur.next);
                if (cur.alt != null && seen.add(cur.alt)) stack.push(cur.alt);
            }
        }
        return false;
    }
}

// ===== Quick demo =====
public class RegexLinkedAutomataDemo {
    public static void main(String[] args) {
        test1();
        test2();
        test3();
        test4();
        test5();
        test6();
        test7();
        test8();
        test9();
        test10();
        test11();
        test12();
        test13();
        test14();
        test15();
        test16();
        test17();
        test18();
    }

    private static void test1() {
        RegexAutomaton matcher = new RegexAutomaton("abc");
        Assertions.assertFalse(matcher.match(""));
        Assertions.assertFalse(matcher.match("a"));
        Assertions.assertFalse(matcher.match("ab"));
        Assertions.assertTrue(matcher.match("abc"));
        Assertions.assertFalse(matcher.match("b"));
        Assertions.assertFalse(matcher.match("c"));

    }

    private static void test2() {
        RegexAutomaton matcher = new RegexAutomaton("a*");
        Assertions.assertTrue(matcher.match(""));
        Assertions.assertTrue(matcher.match("a"));
        Assertions.assertTrue(matcher.match("aa"));
        Assertions.assertTrue(matcher.match("aaa"));
        Assertions.assertFalse(matcher.match("b"));
        Assertions.assertFalse(matcher.match("abxxxxxxx"));
    }


    private static void test3() {
        RegexAutomaton matcher = new RegexAutomaton("a");
        Assertions.assertFalse(matcher.match(""));
        Assertions.assertTrue(matcher.match("a"));
        Assertions.assertFalse(matcher.match("aa"));
        Assertions.assertFalse(matcher.match("b"));
        Assertions.assertFalse(matcher.match("abxxxxxxx"));
    }

    private static void test4() {
        RegexAutomaton matcher = new RegexAutomaton("a*b");
        Assertions.assertFalse(matcher.match(""));
        Assertions.assertTrue(matcher.match("ab"));
        Assertions.assertTrue(matcher.match("aab"));
        Assertions.assertTrue(matcher.match("aaab"));
        Assertions.assertTrue(matcher.match("b"));
        Assertions.assertFalse(matcher.match("abxxxxxxx"));
    }

    private static void test5() {
        RegexAutomaton matcher = new RegexAutomaton("a.*b");
        Assertions.assertFalse(matcher.match(""));
        Assertions.assertTrue(matcher.match("acb"));
        Assertions.assertTrue(matcher.match("acvb"));
        Assertions.assertTrue(matcher.match("ab"));
    }

    private static void test6() {
        RegexAutomaton matcher = new RegexAutomaton("a*.b");
        Assertions.assertTrue(matcher.match("acb"));
        Assertions.assertFalse(matcher.match(""));
        Assertions.assertTrue(matcher.match("cb"));

        Assertions.assertFalse(matcher.match("acxb"));
        Assertions.assertFalse(matcher.match("a"));
        Assertions.assertFalse(matcher.match("b"));
    }

    private static void test7() {
        RegexAutomaton matcher = new RegexAutomaton("a.*.b");
        Assertions.assertFalse(matcher.match(""));
        Assertions.assertFalse(matcher.match("cb"));
        Assertions.assertTrue(matcher.match("acb"));
        Assertions.assertTrue(matcher.match("acxb"));
        Assertions.assertTrue(matcher.match("aqwertxb"));
        Assertions.assertFalse(matcher.match("a"));
        Assertions.assertFalse(matcher.match("b"));
    }

    private static void test8() {
        RegexAutomaton matcher = new RegexAutomaton("a.*.*.*b");
        Assertions.assertFalse(matcher.match(""));
        Assertions.assertFalse(matcher.match("cb"));
        Assertions.assertTrue(matcher.match("acb"));
        Assertions.assertTrue(matcher.match("acxb"));
        Assertions.assertTrue(matcher.match("aqwertxb"));
        Assertions.assertFalse(matcher.match("a"));
        Assertions.assertFalse(matcher.match("b"));
    }

    private static void test9() {
        RegexAutomaton matcher = new RegexAutomaton("a.*.");
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
        RegexAutomaton matcher = new RegexAutomaton("a..");
        Assertions.assertFalse(matcher.match(""));
        Assertions.assertFalse(matcher.match("cb"));
        Assertions.assertTrue(matcher.match("acb"));
        Assertions.assertFalse(matcher.match("acxb"));
        Assertions.assertFalse(matcher.match("a"));
    }

    private static void test11() {
        RegexAutomaton matcher = new RegexAutomaton("..");
        Assertions.assertFalse(matcher.match(""));
        Assertions.assertTrue(matcher.match("cb"));
        Assertions.assertTrue(matcher.match("xz"));
        Assertions.assertFalse(matcher.match("acx"));
        Assertions.assertFalse(matcher.match("a"));
    }

    private static void test12() {
        RegexAutomaton matcher = new RegexAutomaton("a*a");
        Assertions.assertFalse(matcher.match(""));
        Assertions.assertTrue(matcher.match("a"));
        Assertions.assertTrue(matcher.match("aa"));
        Assertions.assertTrue(matcher.match("aaa"));
        Assertions.assertFalse(matcher.match("b"));
        Assertions.assertFalse(matcher.match("abxxxxxxx"));
    }

    private static void test13() {
        RegexAutomaton matcher = new RegexAutomaton("aa*a");
        Assertions.assertFalse(matcher.match(""));
        Assertions.assertFalse(matcher.match("a"));
        Assertions.assertTrue(matcher.match("aa"));
        Assertions.assertTrue(matcher.match("aaa"));
        Assertions.assertTrue(matcher.match("aaaa"));
        Assertions.assertFalse(matcher.match("b"));
        Assertions.assertFalse(matcher.match("abxxxxxxx"));
    }

    private static void test14() {
        RegexAutomaton matcher = new RegexAutomaton("aa*b");
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
        RegexAutomaton matcher = new RegexAutomaton("a*b");
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
        RegexAutomaton matcher = new RegexAutomaton("ab*a*c*a");
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
        RegexAutomaton matcher = new RegexAutomaton("ab*");
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
        RegexAutomaton matcher = new RegexAutomaton(".*..a*");
        Assertions.assertFalse(matcher.match(""));
        Assertions.assertFalse(matcher.match("a"));
        Assertions.assertTrue(matcher.match("aa"));
    }
}
