package br.com.challanges.hackerrank.datastructure.stack;

import br.com.challanges.hackerrank.ResultChecker;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MaximumElement {

  public static List<Integer> getMax(List<String> operations) {
    var result = new ArrayList<Integer>();
    var stack = new Stack<Integer>();
    var maxStack = new Stack<Integer>();
    char oper;
    int num;
    for (var query : operations) {
      oper = query.charAt(0);
      if (oper != '1' && oper != '2' && oper != '3') {
        return Collections.emptyList();
      }

      switch (oper) {
        case '1':
          num = Integer.valueOf(query.substring(2));
          stack.push(num);
          if (num <= 0 || num > 1000000000) {
            return Collections.emptyList();
          }
          if (maxStack.isEmpty() || num >= maxStack.peek()) {
            maxStack.push(num);
          }
          break;
        case '2':
          num = stack.pop();
          if (!maxStack.isEmpty() && maxStack.peek() == num) {
            maxStack.pop();
          }
          break;
        case '3':
          if (!maxStack.isEmpty()) {
            result.add(maxStack.peek());
          }
          break;
      }
    }
    return result;
  }

  public static void main(String[] args) throws IOException {
    BufferedReader bufferedReader = new BufferedReader(new FileReader("/home/vinicius/projects/hackerrank/inputs/maximum-element-2.txt"));
    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("/home/vinicius/projects/hackerrank/inputs/maximum-element-solution.txt"));

    int n = Integer.parseInt(bufferedReader.readLine().trim());
    if (n <= 0 || n > 100000) {
      return;
    }
    List<String> ops = IntStream.range(0, n).mapToObj(i -> {
          try {
            return bufferedReader.readLine();
          } catch (IOException ex) {
            throw new RuntimeException(ex);
          }
        })
        .collect(Collectors.toList());


    List<Integer> maxValues = getMax(ops);
    var string = new StringBuilder();
    for (var max : maxValues) {
      string.append(max).append("\n");
    }
    bufferedWriter.write(string.toString());
    bufferedReader.close();
    bufferedWriter.close();

    ResultChecker.check("/home/vinicius/projects/hackerrank/inputs/maximum-element-output-2.txt",
            "/home/vinicius/projects/hackerrank/inputs/maximum-element-solution.txt")
        .forEach(message -> System.out.println(message));

  }
}
