package br.com.vinicius.hackerrank.euler218;

import java.util.Scanner;

class CopyOfPerfectRightTriangle {
	private int[] hypotenuses;
	private int[][][] triangles;

	public CopyOfPerfectRightTriangle(int[] hypotenuses) throws IllegalAccessException {
		long max = (long) (2 * Math.pow(10, 18));
		for (int i = 0; i < hypotenuses.length; i++) {
			if (hypotenuses[i] < 25 || hypotenuses[i] > max) {
				throw new IllegalAccessException("Invalid Parameters");
			}
		}
		this.hypotenuses = hypotenuses;
	}

	public long count() {
		triangles = new int[hypotenuses.length][][];

		boolean isPerf = true;
		boolean isAreaPerf = false;
		long count = 0;
		int c2 = 0, h = 0, c = 0;

		for (int i = 0; i < hypotenuses.length; i++) {
			c = 0;
			while (c <= hypotenuses[i]) {
				c++;
				c2 = c * c;
				outer: for (int a = 1; a < c; a++) {
					for (int b = 1; b < c; b++) {
						if ((h = a * a + b * b) > c2) {
							break;
						} else if (h == c2) {
							triangles[i] = new int[2][3];
							// Inserindo os triangulos que sao simetricos por
							// reflexao.
							isAreaPerf = isPerfectArea(a, b);
							isPerf = isHypotenusePerfectSquare(c) && isPrimitiveRightTriangle(a, b, c);

							// contando os perfeitos que nao sao super perfeitos
							if (isPerf && !isAreaPerf) {
								count++;
								triangles[i][0] = new int[] { a, b, c };
								StringBuilder s = new StringBuilder();
								s.append("(").append(a).append(", ").append(b).append(", ").append(c).append(")");
								System.out.println(s.toString());
							}
							break outer;
						}
					}
				}
			}
		}
		return count;
	}

	public String printTriangles() {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < triangles.length; i++) {
			if (triangles[i] != null && triangles[i][0] != null) {
				s.append("(").append(triangles[i][0][0]).append(", ").append(triangles[i][0][1]).append(", ")
						.append(triangles[i][0][2]).append(")\n");
			}

			if (triangles[i] != null && triangles[i][1] != null) {
				s.append("(").append(triangles[i][1][0]).append(", ").append(triangles[i][1][1]).append(", ")
						.append(triangles[i][1][2]).append(")\n");
			}
		}
		return s.toString();
	}

	private boolean isPrimitiveRightTriangle(int a, int b, int c) {
		return GCD(a, b) == 1 && GCD(b, c) == 1;
	}

	private boolean isPerfectArea(int a, int b) {
		if (a % 2 != 0 && b % 2 != 0) {
			return false;
		}
		int area = a * b / 2;
		return area % 6 == 0 && area % 28 == 0;
	}

	private boolean isHypotenusePerfectSquare(int c) {
		if (c <= 1) {
			return true;
		}
		int m = c / 2;
		do {
			if (m * m == c) {
				return true;
			}
			m--;
		} while (m > 1);

		return false;
	}

	private int GCD(int x, int y) {
		if (x == 1 || y == 1) {
			return 1;
		}

		int m = x <= y ? x / 2 : y / 2;
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

		int[] hypotenuses = new int[q];
		while (--q > 0) {
			hypotenuses[q] = in.nextInt();
		}
		in.close();

		CopyOfPerfectRightTriangle p;
		try {
			p = new CopyOfPerfectRightTriangle(hypotenuses);
			System.out.println(p.count());
		} catch (IllegalAccessException e) {
			return;
		}

	}
}
