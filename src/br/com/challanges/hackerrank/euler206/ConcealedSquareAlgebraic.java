package br.com.challanges.hackerrank.euler206;

public class ConcealedSquareAlgebraic {

	public static void main(String[] args) {
		new ConcealedSquareAlgebraic(new int[] { 8, 7, 6 }).calc();
	}

	int[][] Bn = null;
	int deg = 0;
	int degFat = 0;
	int[] An = null;
	int pol[];
	int units[][] = new int[10][];
	int[] Xn = null;

	public ConcealedSquareAlgebraic(int[] digits) {
		if (digits == null || digits.length < 3 || digits.length > 15) {
			throw new IllegalStateException();
		}
		int last = digits.length - 1;
		if (digits[last] == 2 || digits[last] == 3 || digits[last] == 7 || digits[last] == 8) {
			throw new IllegalStateException();
		}
		deg = 2 * digits.length - 1;

		this.An = new int[digits.length];
		this.Xn = new int[deg];
		this.Bn = new int[deg][];
		for (int i = 0; i < deg; i++) {
			this.Bn[i] = null;
		}

		for (int i = 0; i < digits.length; i++) {
			this.Xn[2 * i] = digits[digits.length - 1 - i];
		}
		units[0] = new int[] { 0 };
		units[1] = new int[] { 1, 9 };
		units[4] = new int[] { 2 };
		units[5] = new int[] { 5 };
		units[6] = new int[] { 4, 6, 8 };
		units[9] = new int[] { 3, 7 };
		degFat = fatorial(deg);
	}

	public int calc() {
		for (int n = 0, order = 0; n < Bn.length; n = n + 2, order++) {
			calcAn(n, order);
		}
		return 0;
	}

	private boolean calcAn(int n, int order) {
		if (n == 0) {
			Bn[0] = units[Xn[0]];
			return true;
		}
		int a0, a1 = 0, a2 = 0;
		for (int digt = 0; digt <= 9; digt++) {
			a0 = binomialElement(order, Bn[n][0], Bn[n][0]);
			if (a0 % 10 == digt) {
				Bn[n] = units[Xn[n + 3]];
				An[order] = Bn[n][0];
				return true;
			}

			if (Bn[n + 2].length <= 1) {
				continue;
			}
			a1 = 2 * digt * Bn[n + 2][1] + (Bn[n + 2][1] * Bn[n + 2][1] - Xn[n + 3]) / 10;
			if (a1 % 10 == digt) {
				Bn[n] = units[Xn[n + 3]];
				An[order] = Bn[n][1];
				return true;
			}

			if (Bn[n + 2].length <= 2) {
				continue;
			}
			a2 = 2 * digt * Bn[n + 2][2] + (Bn[n + 2][2] * Bn[n + 2][2] - Xn[n + 3]) / 10;
			if (a2 % 10 == digt) {
				Bn[n] = units[Xn[n + 3]];
				An[order] = Bn[n][2];
				return true;
			}

		}
		return false;
	}

	private int coeficBinomial(int k) {
		if (k == 0) {
			return 1;
		}

		int fat = 1;
		for (int i = 1; i <= k; i++) {
			fat *= i * (deg - i);
		}
		return degFat / fat;
	}

	private int fatorial(int k) {
		if (k == 0 || k == 1) {
			return 1;
		}
		int fat = 1;
		for (int i = 1; i <= k; i++) {
			fat *= i;
		}
		return fat;
	}

	private int binomialElement(int order, int x, int y) {
		return (int) (coeficBinomial(order) * Math.pow(x, deg - order) * Math.pow(y, order));
	}
}
