package br.com.challanges.hackerrank.euler210;

import java.util.Scanner;

public class ObtusedAngledTriangles {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		// String[] s = in.nextLine().split("\\s+");
		in.close();
		// String[] s = { "2", "-1", "1", "0" };
		// String[] s = { "8", "0", "1", "1" };
		String[] s = { "17", "3", "7", "2" };
		// String[] s = { "15", "-13", "10", "0" };

		try {
			int r = Integer.parseInt(s[0]);
			int a = Integer.parseInt(s[1]);
			int b = Integer.parseInt(s[2]);
			int n = Integer.parseInt(s[3]);

			System.out.println(new ObtusedAngledTriangles(r, a, b, n).countPoints());
		} catch (Exception e) {
			return;
		}
	}

	private double Ax, Ay, Bx, By, normAB, ABx, ABy, normA, normB;
	long count;
	private long r;

	public ObtusedAngledTriangles(long r, long a, long b, long n) {
		boolean ok = r >= 2 && r <= Math.pow(10, 9);
		ok &= Math.abs(a) <= r;
		ok &= b > 0 && b <= r;
		ok &= n >= 0 && n <= r;
		ok &= Math.abs(2 * a / b) <= r && 2 * Math.abs(2 * n - a / b) <= r;
		ok &= Math.abs(n - a / b) <= Math.pow(3, 8);
		ok &= n * b > a;
		if (!ok) {
			// throw new IllegalArgumentException();
		}
		this.r = r;
		Ax = Ay = a / b;
		Bx = By = 2 * n - a / b;
		System.out.println("A=(" + Ax + ", " + Ay + ")" + " B=(" + Bx + ", " + By + ")");

		// analizando o problema no primeiro quadrante pois os outros sao
		// simetricos
		if (Ax <= 0 && Bx <= 0) {
			Ay = Ax *= -1;
			By = Bx *= -1;
		}

		// analizando o problema no primeiro quadrante pois os outros sao
		// simetricos
		if (Ax != 0 && Bx == 0) {
			By = Bx = Ax;
			Ay = Ax = 0;
		} else if (Bx < 0 && Ax > 0 || (Ax > 0 && Bx > 0 && Ax > Bx)) {
			double T = 0;
			By = Bx = Ax;
			Ay = Ax = T;
		}

		ABx = ABy = Bx - Ax;
		// distancia do segmento AB
		normAB = calcDist(ABx, ABy);

		normA = calcDist(Ax, Ay);
		normB = calcDist(Bx, By);
	}

	private double calcDist(double x, double y) {
		return Math.sqrt(x * x + y * y);
	}

	public long countPoints() {
		if (normAB == 0) {
			return 0;
		}
		// final double delta = 0.01;
		int t = 0;
		int e = 0;
		int f = 0;
		double ang = 0;
		for (long Cx = r; Cx >= -r; Cx--) {
			for (long Cy = r; Cy >= -r; Cy--) {
				t++;
				if (Math.abs(Cx) + Math.abs(Cy) > r || Cx == Cy) {
					e++;
					System.err.print("(" + Cx + ", " + Cy + ")\n");
					continue;
				}

				ang = largestAngle(Cx, Cy);
				// System.out.println("(" + Cx + ", " + Cy + ")=" + ang);
				// if (Math.abs(ang - 90d) < delta || Math.abs(ang - 180d) <
				// delta) {
				// continue;
				// }

				if (ang > 90d && ang < 180d) {
					count++;
					// System.out.println("(" + Cx + ", " + Cy + ")=" + ang);
				} else {
					f++;
				}
			}
		}
		System.out.println("A=(" + Ax + ", " + Ay + ")" + " B=(" + Bx + ", " + By + ")" + " Total:" + t + " Excluido: "
				+ e + " Fora: " + f);
		return count;
	}

	private double largestAngle(long Cx, long Cy) {

		double normCA = calcDist(Cx - Ax, Cy - Ay);
		if (normCA == 0) {
			return 0;
		}

		double normCB = calcDist(Cx - Bx, Cy - By);
		if (normCB == 0) {
			return 0;
		}

		double normC = calcDist(Cx, Cy);
		double cosA = 0;
		double cosB = 0;
		double cosC = 0;

		if (Ax == 0) {
			cosA = (Bx * Cx + By * Cy) / (normB * normC);
		} else if (Ax < 0 && Bx > 0) {
			cosA = (-Ax * (Cx - Ax) - Ay * (Cy - Ay)) / (normA * normCA);
		} else if (Ax > 0 && Bx > 0 && Ax < Bx) {
			cosA = ((Bx - Ax) * (Cx - Ax) - (By - Ay) * (Cy - Ay)) / (normAB * normCA);
		}

		cosB = (-Bx * (Cx - Bx) - By * (Cy - By)) / (normB * normCB);
		if (Ax == 0) {
			cosC = (-Cx * (Bx - Cx) - Cy * (By - Cy)) / (normC * normCB);
		} else {
			cosC = ((Ax - Cx) * (Bx - Cx) + (Ay - Cy) * (By - Cy)) / (normCA * normCB);
		}
		double angA = Math.acos(cosA);
		double angB = Math.acos(cosB);
		double angC = Math.acos(cosC);

		if (angA > angC && angA > angB) {
			System.out.println("(" + Cx + ", " + Cy + ")=" + Math.toDegrees(angA) + " (" + Math.toDegrees(angA) + ", "
					+ Math.toDegrees(angB) + ", " + Math.toDegrees(angC) + ")");
			return Math.toDegrees(angA);
		}

		if (angC > angB) {

			System.out.println("(" + Cx + ", " + Cy + ")=" + Math.toDegrees(angC) + " (" + Math.toDegrees(angA) + ", "
					+ Math.toDegrees(angB) + ", " + Math.toDegrees(angC) + ")");
			return Math.toDegrees(angC);
		}

		System.out.println("(" + Cx + ", " + Cy + ")=" + Math.toDegrees(angB) + " (" + Math.toDegrees(angA) + ", "
				+ Math.toDegrees(angB) + ", " + Math.toDegrees(angC) + ")");

		return Math.toDegrees(angB);
	}
}
