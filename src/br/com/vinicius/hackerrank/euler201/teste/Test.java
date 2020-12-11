package br.com.vinicius.hackerrank.euler201.teste;

import br.com.vinicius.hackerrank.euler201.tree.Tree;

public class Test {
	public static void main(String[] args) {
		teste1();
		teste2();
		teste3();
		teste4();
	}

	private static void teste1() {
		final int[] numbers = new int[] {
				1, 2, 3, 4, 5
		};
		final int setSize = 3;
		final int result = 36;
		final int sum = new Tree(numbers, setSize).sumSubsets();
		if (result != sum) {
			System.err.println("A soma deveria ser " + result + ", mas foi calculado " + sum);
		}
		else {
			System.out.println("Teste 1 ok.");
		}
	}

	private static void teste2() {
		final int[] numbers = new int[] {
				1, 3, 6, 8, 10, 11
		};
		final int setSize = 3;
		final int result = 156;
		final int sum = new Tree(numbers, setSize).sumSubsets();
		if (result != sum) {
			System.err.println("A soma deveria ser " + result + ", mas foi calculado " + sum);
		}
		else {
			System.out.println("Teste 2 ok.");
		}
	}

	private static void teste3() {
		final int[] numbers = new int[] {
				1, 2, 3, 4, 5
		};
		final int setSize = 5;
		final int result = 15;
		final int sum = new Tree(numbers, setSize).sumSubsets();
		if (result != sum) {
			System.err.println("A soma deveria ser " + result + ", mas foi calculado " + sum);
		}
		else {
			System.out.println("Teste 3 ok.");
		}
	}

	private static void teste4() {
		final int[] numbers = new int[] {
				5
		};
		final int setSize = 1;
		final int result = 5;
		final int sum = new Tree(numbers, setSize).sumSubsets();
		if (result != sum) {
			System.err.println("A soma deveria ser " + result + ", mas foi calculado " + sum);
		}
		else {
			System.out.println("Teste 4 ok.");
		}
	}

}
