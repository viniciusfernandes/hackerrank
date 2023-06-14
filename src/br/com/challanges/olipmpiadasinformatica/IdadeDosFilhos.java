package br.com.challanges.olipmpiadasinformatica;


import java.util.Scanner;

public class IdadeDosFilhos {
	public static void main(String[] args) {
		final Scanner in = new Scanner(System.in);

		// lê duas variáveis inteiras
		final int m = in.nextInt();
		final int a = in.nextInt();
		final int b = in.nextInt();

		in.close();

		if (m < 40 || m > 110) {
			return;
		}

		if (a == b || a < 1 || a >= m || b < 1 || b >= m) {
			return;
		}

		final int c = m - a - b;
		int k = c;
		if (k < a) {
			k = a;
		}

		if (k < b) {
			k = b;
		}
		System.out.println(k);
	}
}
