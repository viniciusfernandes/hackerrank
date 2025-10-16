package br.com.challanges.leetcode;

import java.util.HashSet;
import java.util.Set;

public class MiniRegexEngine {

    static class State {
        char c;          // transition character, or 0 for epsilon
        State out1;      // first transition
        State out2;      // second transition (for epsilon split)
        boolean accept;  // is this an accept state

        State(char c) {
            this.c = c;
        }
    }

    public static void main(String[] args) {
        State start = buildNFA("."); // build pattern a*

        MiniRegexEngine eng = new MiniRegexEngine();
        System.out.println("zz".matches("."));
//        System.out.println("\"\" -> " + eng.match(start, "", 0));     // ✅ true
//        System.out.println("\"a\" -> " + eng.match(start, "a", 0));   // ✅ true
//        System.out.println("\"aa\" -> " + eng.match(start, "aa", 0)); // ✅ true
//        System.out.println("\"aaa\" -> " + eng.match(start, "aaa", 0));// ✅ true
//        System.out.println("\"b\" -> " + eng.match(start, "b", 0));   // ❌ false
//        System.out.println("\"ab\" -> " + eng.match(start, "ab", 0)); // ❌ false
    }

    boolean match(State s, String input, int i) {
        return match(s, input, i, new HashSet<>());
    }

    boolean match(State s, String input, int i, Set<String> visited) {
        if (s == null) return false;
        String key = System.identityHashCode(s) + ":" + i;
        if (!visited.add(key)) return false;

        if (s.accept && i == input.length()) return true;

        if (s.c == 0)
            return match(s.out1, input, i, visited) || match(s.out2, input, i, visited);

        if (i < input.length() && (s.c == '.' || s.c == input.charAt(i)))
            return match(s.out1, input, i + 1, visited);

        return false;
    }

    private static State buildNFA(String pattern) {
        State start = null;
        State last = null;

        for (int i = 0; i < pattern.length(); i++) {
            char c = pattern.charAt(i);

            if (c == '*') {
                if (last == null) throw new IllegalArgumentException("Invalid pattern: starts with *");

                // last is the previous literal (like 'a')
                State aState = last;

                // Create ε-split
                State split = new State((char) 0);
                State accept = new State((char) 0);
                accept.accept = true;

                // SPLIT branches:
                //  1. to aState (loop body)
                //  2. directly to accept (zero occurrences)
                split.out1 = aState;
                split.out2 = accept;

                // after matching 'a', loop back to split
                aState.out1 = split;

                // set start if not defined
                if (start == null) start = split;

                last = accept; // accept is new tail
                continue;
            }

            // Literal or dot
            State s = new State(c);
            if (last != null) {
                last.out1 = s;
            } else {
                start = s;
            }
            last = s;
        }

        if (last != null) last.accept = true;

        return start;
    }
}
