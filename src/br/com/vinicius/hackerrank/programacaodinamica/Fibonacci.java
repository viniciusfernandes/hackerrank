package br.com.vinicius.hackerrank.programacaodinamica;

import java.util.Arrays;

public class Fibonacci {

	public static void main(String[] args) {
		System.out.println(Arrays.toString(new Fibonacci(10).calc()));
	}
	private int[] memo;

	private int n;

	public Fibonacci(int n) {
		this.n = n;
		memo = new int[n + 1];
		memo[0] = memo[1] = 1;
	}

	public int[] calc() {
		calc(n);
		return memo;
	}

	private int calc(int n) {
		if (n <= 1) {
			return 1;
		}
		if (memo[n] == 0) {
			memo[n] = calc(n - 1) + calc(n - 2);
		}
		return memo[n];
	}

}
