package br.com.vinicius.hackerrank.euler218.tunned;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class PerfectRightTriangleTunned {
	private Map<Long, Long[]> totNPerf = new HashMap<>();
	private long[] orderHyp;
	private long[] hyp;

	public PerfectRightTriangleTunned(long[] hypotenuses) throws IllegalAccessException {
		long max = (long) (2 * Math.pow(10, 18));
		for (int i = 0; i < hypotenuses.length; i++) {
			if (hypotenuses[i] < 25 || hypotenuses[i] > max) {
				throw new IllegalAccessException("Invalid Parameters");
			}
		}

		hyp = hypotenuses;

		orderHyp = Arrays.copyOf(hypotenuses, hypotenuses.length);
		Arrays.sort(orderHyp);

		for (int i = 0; i < hypotenuses.length; i++) {
			totNPerf.put(hypotenuses[i], new Long[] { 0L });
		}
	}

	public void count() {
		long h = 0, b = 0, a = 0, c = 0, c2 = 0, nextHyp = 0;

		for (int i = 0; i < orderHyp.length; i++) {
			c = nextHyp;
			nextHyp = orderHyp[i];

			while (++c <= nextHyp) {

				if (!isHypotenusePerfectSquare(c)) {
					continue;
				} else {
					System.out.println("Quadr Perf: " + c);
				}
				c2 = c * c;
				a = c - 1;
				b = 1;
				while (a >= 1 && (h = a * a + b * b) <= c2) {
					if (h == c2) {
						if (!isPrimitiveRightTriangle(a, b, c)) {
							break;
						} else {
							System.out.println("Prime: " + a + " " + b + " " + c);
						}

						// contando os perfeitos que nao sao super perfeitos
						if (!isPerfectArea(a, b)) {
							totNPerf.get(h)[0]++;
						} else {
							System.out.println("Sup Perf: " + a + " " + b + " " + c);
						}
						break;
					}
					a--;
					b++;
				}
			}
		}
	}

	public void print() {
		for (int i = 0; i < hyp.length; i++) {
			System.out.println(totNPerf.get(hyp[i])[0]);
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

		PerfectRightTriangleTunned p;
		try {
			p = new PerfectRightTriangleTunned(hypotenuses);
			p.count();
			p.print();
		} catch (IllegalAccessException e) {
			return;
		}

	}
}
