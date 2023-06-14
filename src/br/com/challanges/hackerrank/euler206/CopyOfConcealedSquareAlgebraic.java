package br.com.challanges.hackerrank.euler206;

public class CopyOfConcealedSquareAlgebraic {

	public static void main(String[] args) {
		new CopyOfConcealedSquareAlgebraic(new int[] { 8, 7, 6 }).calc();
	}

	int[][] An = null;
	int deg = 0;
	int degFat = 0;
	int[] Dn = null;
	int pol[];
	int sqrtUnits[][] = new int[10][];
	int[] Xn = null;

	public CopyOfConcealedSquareAlgebraic(int[] digits) {
		if (digits == null || digits.length < 3 || digits.length > 15) {
			throw new IllegalStateException();
		}
		int last = digits.length - 1;
		if (digits[last] == 2 || digits[last] == 3 || digits[last] == 7 || digits[last] == 8) {
			throw new IllegalStateException();
		}
		deg = 2 * digits.length - 1;

		this.Dn = new int[digits.length];
		this.Xn = new int[deg];
		this.An = new int[deg][];
		for (int i = 0; i < deg; i++) {
			this.An[i] = null;
		}

		for (int i = 0; i < digits.length; i++) {
			this.Xn[2 * i] = digits[i];
		}
		sqrtUnits[0] = new int[] { 0 };
		sqrtUnits[1] = new int[] { 1, 9 };
		sqrtUnits[4] = new int[] { 2 };
		sqrtUnits[5] = new int[] { 5 };
		sqrtUnits[6] = new int[] { 4, 6, 8 };
		sqrtUnits[9] = new int[] { 3, 7 };
		degFat = fatorial(deg);
	}

	public int calc() {
		for (int k = An.length - 2, d = Dn.length - 1; k >= 0; k = k - 2, d--) {
			calcAn(k, d);
		}
		return 0;
	}

	private boolean calcAn(int k, int order) {
		if (k == An.length - 2) {
			An[k] = sqrtUnits[Xn[k + 1]];
			return true;
		}
		int a0, a1 = 0, a2 = 0;
		for (int i = 0; i <= 9; i++) {
			a0 = 2 * i * An[k + 2][0] + (An[k + 2][0] * An[k + 2][0] - Xn[k + 3]) / 10;
			if (a0 % 10 == i) {
				An[k] = sqrtUnits[Xn[k + 3]];
				Dn[order] = An[k][0];
				return true;
			}

			if (An[k + 2].length <= 1) {
				continue;
			}
			a1 = 2 * i * An[k + 2][1] + (An[k + 2][1] * An[k + 2][1] - Xn[k + 3]) / 10;
			if (a1 % 10 == i) {
				An[k] = sqrtUnits[Xn[k + 3]];
				Dn[order] = An[k][1];
				return true;
			}

			if (An[k + 2].length <= 2) {
				continue;
			}
			a2 = 2 * i * An[k + 2][2] + (An[k + 2][2] * An[k + 2][2] - Xn[k + 3]) / 10;
			if (a2 % 10 == i) {
				An[k] = sqrtUnits[Xn[k + 3]];
				Dn[order] = An[k][2];
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

	private double binomialElement(int k, int x, int y) {
		return coeficBinomial(k) * Math.pow(x, deg - k) * Math.pow(y, k);
	}
}
