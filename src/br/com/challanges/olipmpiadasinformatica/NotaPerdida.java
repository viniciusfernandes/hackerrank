package br.com.challanges.olipmpiadasinformatica;

import java.util.Scanner;

public class NotaPerdida {
	public static void main(String[] args) {
		final Scanner in = new Scanner(System.in);

		final int a = Integer.parseInt(in.nextLine());
		final int m = Integer.parseInt(in.nextLine());

		if (a < 0 || a > 100 || m < 0 || m > 100) {
			in.close();
			return;
		}

		System.out.println(2 * m - a);

		in.close();
	}
}
