package br.com.tutorial.dockernetworking.networkingmicroserviceconsumer;

import java.io.*;
import java.util.*;

import static java.lang.Integer.*;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DynamicArray {

    public static void main(String[] args) throws IOException {
        Scanner bufferedReader = new Scanner(System.in);
        String[] firstMultipleInput = bufferedReader.nextLine().replaceAll("\\s+$", "").split(" ");
        int n = Integer.parseInt(firstMultipleInput[0]);

        int q = Integer.parseInt(firstMultipleInput[1]);
        List<List<Integer>> queries = new ArrayList<>();

        String line = null;

        String[] values = null;
        while (bufferedReader.hasNextLine()) {
            line = bufferedReader.nextLine();
            if (line.isEmpty()) {
                break;
            }
            values = line.trim().split(" ");
            queries.add(List.of(valueOf(values[0]), valueOf(values[1]), valueOf(values[2])));
        }
        List<Integer> result = DynamicArray.dynamicArray(n, queries);
        for (var r : result) {
            System.out.println(r);
        }

        bufferedReader.close();


    }

    private static int max = 100000;
    private static int MAX = 1000000000;

    public static List<Integer> dynamicArray(int n, List<List<Integer>> queries) {
        if (n < 1 || n > max || queries.size() < 1 || queries.size() > max) {
            return new ArrayList<>();
        }


        var result = new ArrayList<Integer>();
        int x = 0, y = 0, lastAnswer = 0;
        List<List<Integer>> arr = new ArrayList<>();


        List<Integer> values = null;
        List<Integer> subarr = null;
        int type = 0;
        int idx = 0;
        for (int i = 0; i < n; i++) {
            arr.add(new ArrayList<>());
        }
        for (int i = 0; i < queries.size(); i++) {
            try {
                values = queries.get(i);
                type = values.get(0);
                x = values.get(1);
                y = values.get(2);

                if (x < 0 || x > MAX || y < 0 || y > MAX) {
                    return new ArrayList<>();
                }

                idx = (x ^ lastAnswer) % n;
                subarr = arr.get(idx);
                if (type == 1) {
                    subarr.add(y);
                  //  System.out.println("y = " + y);

                } else {
                    lastAnswer = !subarr.isEmpty() ? subarr.get(y % subarr.size()) : 0;
                    result.add(lastAnswer);
                   // System.out.println("*** = " + lastAnswer);
                }
            } catch (Exception e) {
                break;
            }
        }
        return result;

    }

}
