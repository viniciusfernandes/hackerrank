package br.com.challanges;

import java.util.Arrays;

public class SmallestInteger {
    public int solution2(int[] A) {
        Arrays.sort(A);
        if (A.length == 0 || A[A.length - 1] <= 0) {
            return 1;
        }
        for (int i = 0; i < A.length - 1; i++) {
            if (A[i] < 0 || (A[i + 1] - A[i] <= 1)) {
                continue;
            }

            return A[i + 1] - 1;
        }
        return A[A.length - 1] + 1;
    }

    public static int solution(int N) {
        int max = -1;
        int n = N;
        while (n > 0) {
            if ((n & 1) == 1) {
                int[] val = count(n);
                int gap = val[0];
                n = val[1];
                if (gap > max) {
                    max = gap;
                }
            } else {
                n = n >> 1;
            }
        }
        return max;
    }

    private static int[] count(int n) {
        int gap = 0;
        while (n > 1) {
            n = n >> 1;
            if ((n & 1) == 0) {
                gap++;
                continue;
            }
            return new int[]{gap, n};
        }
        return new int[]{0, 0};
    }

    public static void main(String[] args) {
        System.out.println(solution(529));
    }

    public int solution3(int[] A) {
        int pairs = 0;
        for (int i = 0; i < A.length; i++) {
            for (int j = i + 1; j < A.length; j++) {
                if (A[j] < A[i]) {
                    pairs++;
                }
                if (pairs > 1000000000) {
                    return -1;
                }
            }
        }
        return pairs;
    }


}
