package br.com.vinicius.hackerrank.euler218;

import java.util.Scanner;

class PerfectRightTriangle {
	private long[] hypotenuses;
	private long[] totNPerf;

	public PerfectRightTriangle(long[] hypotenuses) throws IllegalAccessException {
		long max = (long) (2 * Math.pow(10, 18));
		for (int i = 0; i < hypotenuses.length; i++) {
			if (hypotenuses[i] < 25 || hypotenuses[i] > max) {
				throw new IllegalAccessException("Invalid Parameters");
			}
		}
		this.hypotenuses = hypotenuses;
	}

	public void count() {
		totNPerf = new long[hypotenuses.length];

		boolean isAreaPerf = false;
		long c2 = 0, h = 0, c = 0;

		hyp: for (int i = 0; i < hypotenuses.length; i++) {
			c = hypotenuses[i];
			while (c > 0) {
				c--;
				if (!isHypotenusePerfectSquare(c)) {
					continue;
				}
				c2 = c * c;
				outer: for (int a = 1; a < c; a++) {
					for (int b = 1; b < c; b++) {
						if ((h = a * a + b * b) > c2) {
							break;
						} else if (h == c2) {
							// Inserindo os triangulos que sao simetricos por
							// reflexao.

							if (!isPrimitiveRightTriangle(a, b, c)) {
								continue;
							}

							isAreaPerf = isPerfectArea(a, b);

							// contando os perfeitos que nao sao super perfeitos
							if (!isAreaPerf) {
								totNPerf[i]++;
							} else {
								break hyp;
							}
							break outer;
						}
					}
				}
			}
		}
	}

	public void print() {
		for (int i = 0; i < totNPerf.length; i++) {
			System.out.println(totNPerf[i]);
		}
	}

	private boolean isPrimitiveRightTriangle(long a, long b, long c) {
		return GCD(a, b) == 1 && GCD(b, c) == 1;
	}

	private boolean isPerfectArea(long a, long b) {
		if (a % 2 != 0 && b % 2 != 0) {
			return false;
		}
		long area = a * b / 2;
		return area % 6 == 0 && area % 28 == 0;
	}

	private boolean isHypotenusePerfectSquare(long c) {
		if (c <= 1) {
			return true;
		}
		long m = c / 2;
		do {
			if (m * m == c) {
				return true;
			}
			m--;
		} while (m > 1);

		return false;
	}

	private long GCD(long x, long y) {
		if (x == 1 || y == 1) {
			return 1;
		}

		long m = x <= y ? x / 2 : y / 2;
		do {
			if (x % m == 0 & y % m == 0) {
				return m;
			}
			m--;
		} while (true);
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int q = in.nextInt();
		if (q < 1 || q > 100000) {
			in.close();
			return;
		}

		long[] hypotenuses = new long[q];
		while (--q >= 0) {
			hypotenuses[q] = in.nextLong();
		}
		in.close();

		PerfectRightTriangle p;
		try {
			p = new PerfectRightTriangle(hypotenuses);
			p.count();
			p.print();
		} catch (IllegalAccessException e) {
			return;
		}

	}
}
