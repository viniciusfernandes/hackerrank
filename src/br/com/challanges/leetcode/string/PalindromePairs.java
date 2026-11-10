package br.com.challanges.leetcode.string;

import br.com.challanges.algorithms.datastructure.utils.Assertions;

import java.util.*;

class PalindromePairs {
    private static class Pairs {
        private final Map<Integer, Set<Integer>> pairs = new HashMap<>();
        final List<List<Integer>> l = new ArrayList<>();

        void add(int i, int j) {
            if (pairs.computeIfAbsent(i, x -> new HashSet<>()).add(j)) {
                l.add(List.of(i, j));
            }
        }

        List<List<Integer>> toList() {
            return l;
        }
    }

    public static List<List<Integer>> palindromePairs(String[] words) {
        if (words == null || words.length <= 1) {
            return List.of();
        }
        HashMap<String, List<Integer>> indexesMap = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            indexesMap.computeIfAbsent(words[i], w -> new ArrayList<>()).add(i);
        }

        Pairs pairs = new Pairs();
        for (int i = 0; i < words.length; i++) {
            int j = 1;
            do {
                String prefix = words[i].isEmpty() ? words[i] : words[i].substring(0, j);
                String reversedPrefix = new StringBuilder(prefix).reverse().toString();
                if (indexesMap.containsKey(reversedPrefix) && isPalindrome(words[i] + reversedPrefix)) {
                    for (Integer index : indexesMap.get(reversedPrefix)) {
                        if (i != index) {
                            pairs.add(i, index);
                        }
                    }
                }
                String suffix = words[i].isEmpty() ? words[i] : words[i].substring(j);
                String reversedSuffix = new StringBuilder(suffix).reverse().toString();
                if (indexesMap.containsKey(reversedSuffix) && isPalindrome(reversedSuffix + words[i])) {
                    for (Integer index : indexesMap.get(reversedSuffix)) {
                        if (i != index) {
                            pairs.add(index, i);
                        }
                    }
                }
                j++;
            } while (j <= words[i].length());
        }

        if (indexesMap.containsKey("")) {
            for (int i = 0; i < words.length; i++) {
                for (Integer index : indexesMap.get("")) {
                    if (i != index && isPalindrome(words[i])) {
                        pairs.add(i, index);
                        pairs.add(index, i);
                    }
                }
            }
        }
        return pairs.toList();
    }

    private static boolean isPalindrome(String w) {
        if (w.length() <= 1) {
            return true;
        }
        int i = 0, j = w.length() - 1;
        while (i < j) {
            if (w.charAt(i) != w.charAt(j)) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }

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
    }

    private static void test1() {
        Assertions.assertEquals(List.of(), palindromePairs(new String[]{"a", "aba"}));
    }

    private static void test2() {
        Assertions.assertEquals(List.of(), palindromePairs(new String[]{"a", "b"}));
    }

    private static void test3() {
        Assertions.assertEquals(List.of(), palindromePairs(new String[]{"abc", "b"}));
    }

    private static void test4() {
        Assertions.assertEquals(List.of(), palindromePairs(new String[]{"abc", ""}));
    }

    private static void test5() {
        Assertions.assertEquals(List.of(List.of(0, 1), List.of(1, 0)), palindromePairs(new String[]{"", ""}));
    }

    private static void test6() {
        Assertions.assertEquals(List.of(), palindromePairs(new String[]{""}));
    }

    private static void test7() {
        Assertions.assertEquals(List.of(), palindromePairs(new String[]{"a"}));
    }

    private static void test8() {
        Assertions.assertEquals(List.of(List.of(1, 0)), palindromePairs(new String[]{"lls", "s"}));
    }

    private static void test9() {
        Assertions.assertEquals(List.of(List.of(0, 1)), palindromePairs(new String[]{"lls", "sssll"}));
    }

    private static void test10() {
        Assertions.assertEquals(List.of(List.of(0, 1), List.of(1, 0), List.of(3, 2), List.of(2, 4)),
                palindromePairs(new String[]{"abcd", "dcba", "lls", "s", "sssll"}));
    }

    private static void test11() {
        Assertions.assertEquals(List.of(List.of(3, 0), List.of(3, 2), List.of(0, 3), List.of(2, 3)),
                palindromePairs(new String[]{"a", "abc", "aba", ""}));
    }
}
