package br.com.challanges.algorithms.dynamicprogramming;

public class Fibonacci {
  private Fibonacci() {
  }

  public static int calculate(int n) {
    if (n <= 1) {
      return 1;
    }

    int[] fib = new int[n + 1];
    fib[0] = 1;
    fib[1] = 1;
    for (int i = 2; i <= n; i++) {
      fib[i] = fib[i - 2] + fib[i - 1];
    }
    return fib[n];
  }

  public static void main(String[] args) {
    System.out.println(Fibonacci.calculate(10));
  }

}
