package br.com.challanges.olipmpiadasinformatica;

import java.math.BigDecimal;
import java.util.Scanner;

public class PontoDoMeio {
	public static void main(String[] args) {
		final Scanner in = new Scanner(System.in);

		final int n = Integer.parseInt(in.nextLine());

		if (n < 1 || n > 50) {
			in.close();
			return;
		}

		final BigDecimal potencia_2 = BigDecimal.valueOf(2).pow(n);
		final BigDecimal potencia_2_minus_1 = potencia_2.subtract(BigDecimal.ONE);
		final BigDecimal sides = BigDecimal.valueOf(4).multiply(potencia_2);

		final BigDecimal internalPoints = potencia_2_minus_1.multiply(potencia_2_minus_1);
		System.out.println(sides.add(internalPoints));

		in.close();
	}
}