package br.com.vinicius.hackerrank.euler201.teste;

import br.com.vinicius.hackerrank.euler201.tree.Tree;

public class Test {
	public static void main(String[] args) {
		final int[] numbers = new int[] {
				1, 2, 3, 4, 5
		};
		final int setSize = 3;
		final int result = 36;
		final int sum = new Tree(numbers, setSize).sumSubsets();
		if (result != sum) {
			System.err.println("A soma deveria ser " + result + ", mas foi calculado " + sum);
		}
	}
}
