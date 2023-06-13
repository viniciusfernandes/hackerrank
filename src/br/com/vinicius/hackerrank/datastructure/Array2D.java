package br.com.vinicius.hackerrank.datastructure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Array2D {

  public static void main(String[] args) throws IOException {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    List<List<Integer>> arr = new ArrayList<>();

    IntStream.range(0, 6).forEach(i -> {
      try {
        arr.add(
            Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList())
        );
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    });

    int result = Array2D.hourglassSum(arr);
    System.out.println(result);

    bufferedReader.close();
  }

  public static int hourglassSum(List<List<Integer>> arr) {
    return new Matrix(arr).maxHourglassSum();
  }

  private static class Matrix {
    private List<List<Integer>> arr;
    int lines;
    int cols;
    int hourglassSize = 3;
    int revertShift = 1;

    public Matrix(List<List<Integer>> arr) {
      if (arr == null) {
        throw new IllegalArgumentException();
      }
      this.arr = arr;
      lines = arr.size();
      if (lines == 0 || lines > 6) {
        throw new IllegalArgumentException();
      }
      for (var line : arr) {
        if ((cols = line.size()) > 6) {
          throw new IllegalArgumentException();
        }
        for (var col : line) {
          if (col < -9 || col > 9) {
            throw new IllegalArgumentException();
          }
        }
      }
    }

    public int maxHourglassSum() {
      var summation = new TreeSet<Integer>();
      for (int i = 0; i <= lines - hourglassSize; i++) {
        for (int j = 0; j <= cols - hourglassSize; j++) {
          summation.add(hourglassSum(i, j));
        }
      }
      return summation.pollLast();
    }

    private Integer hourglassSum(int line, int col) {
      int total = 0;
      int shift = 0;
      for (int i = line; i < line + hourglassSize; i++) {
        for (int j = col + shift; j < col + hourglassSize - shift; j++) {
          total += arr.get(i).get(j);
        }
        if (shift < revertShift) {
          shift++;
        } else {
          shift--;
        }
      }
      return Integer.valueOf(total);
    }
  }
}
