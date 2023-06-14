package br.com.challanges.hackerrank.datastructure.arrays;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Integer.valueOf;

public class LeftRotate {
    public static List<Integer> rotateLeft(int d, List<Integer> arr) {
        int n = arr.size();
        if (d < 1 || d > n || n < 1 || n > maxLength) {
            return Collections.emptyList();
        }
        int shift = n - d;
        if (shift == 0) {
            return arr;
        }
        int val = 0;
        int idx = 0;
        List<Integer> result = new ArrayList<>(arr);
        for (int i = 0; i < n; i++) {
            val = arr.get(i);
            if (val < 1 || val > maxValue) {
                return Collections.emptyList();
            }
            idx = (i + shift) % n;
            result.set(idx, arr.get(i));
        }
        return result;
    }

    static int maxLength = 100000;
    static int maxValue = 1000000;

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);

        int d = Integer.parseInt(firstMultipleInput[1]);

        List<Integer> arr = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        List<Integer> result = rotateLeft(d, arr);
        for (var r : result) {
            System.out.println(r);
        }

        bufferedReader.close();
    }
}


