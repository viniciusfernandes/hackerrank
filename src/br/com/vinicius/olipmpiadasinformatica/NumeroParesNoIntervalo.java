package br.com.vinicius.olipmpiadasinformatica;

import java.util.Scanner;

public class NumeroParesNoIntervalo {
	public static void main(String[] args) {
		final Scanner in = new Scanner(System.in);

		// lê duas variáveis inteiras
		String line = in.nextLine();
		final String[] params = line.split(" ");

		line = in.nextLine();
		final String[] vector = line.split(" ");

		in.close();

		final int n = Integer.parseInt(params[0]);
		final int i = Integer.parseInt(params[1]);
		final int f = Integer.parseInt(params[2]);

		if (n < 2 || n > 1000) {
			return;
		}

		if (i > f || i < -2000 || i > 2000 || f < -2000 || f > 2000) {
			return;
		}

		int a = 0, b = 0, sum;
		int pairs = 0;
		for (int j = 0; j < vector.length; j++) {
			a = Integer.valueOf(vector[j]);
			if (a < -1000 || a > 1000) {
				return;
			}
			for (int k = j + 1; k < vector.length; k++) {
				b = Integer.valueOf(vector[k]);
				if (a == b || b < -1000 || b > 1000) {
					return;
				}
				sum = a + b;
				if (sum >= i && sum <= f) {
					pairs++;
				}
			}
		}
		System.out.println(pairs);
	}
}
