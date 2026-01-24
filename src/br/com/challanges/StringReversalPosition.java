package br.com.challanges;

import br.com.challanges.algorithms.datastructure.utils.Assertions;

class StringReversalPosition {
    public static int solution(String S) {
        if (S == null || S.length() % 2 == 0 || S.length() == 1) {
            return -1;
        }
        char[] chars = S.toCharArray();
        int half = chars.length / 2;
        for (int i = 1; i <= half; i++) {
            if (chars[half - i] != chars[half + i]) {
                return -1;
            }
        }
        return half;
    }

    public static void main(String[] args) {
        Assertions.assertEquals(3, solution("racecar"));
        Assertions.assertEquals(-1, solution("racecax"));
        Assertions.assertEquals(0, solution("raceca"));
        Assertions.assertEquals(0, solution("o"));
        Assertions.assertEquals(0, solution("xx"));


    }
}
