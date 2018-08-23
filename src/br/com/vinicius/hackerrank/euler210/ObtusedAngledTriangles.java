package br.com.vinicius.hackerrank.euler210;

import java.util.Scanner;

public class ObtusedAngledTriangles {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String[] s = in.nextLine().split("\\s+");
		in.close();

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

	private double Ax, Ay, Bx, By, AB, ABx, ABy;
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

		ABx = ABy = Bx - Ax;
		AB = calcDist(ABx, ABy);
	}

	private double calcDist(double x, double y) {
		return Math.sqrt(x * x + y * y);
	}

	public long countPoints() {
		if (AB == 0) {
			return 0;
		}
		final double delta = 0.01;

		double ang = 0;
		for (long Cx = r; Cx >= -r; Cx--) {
			for (long Cy = r; Cy >= -r; Cy--) {
				if (Math.abs(Cx) + Math.abs(Cy) > r) {
					continue;
				}
				ang = largestAngle(Cx, Cy);
				if (Math.abs(ang - 90d) < delta || Math.abs(ang - 180d) < delta) {
					continue;
				}
				if (ang > 90d && ang < 180d) {
					count++;
					System.out.println("(" + Cx + ", " + Cy + ")=" + ang);
				}
			}
		}
		return count;
	}

	private double largestAngle(long Cx, long Cy) {

		double CA = calcDist(Cx - Ax, Cy - Ay);

		if (CA == 0) {
			return 0;
		}

		double CB = calcDist(Cx - Bx, Cy - By);
		if (CB == 0) {
			return 0;
		}

		double cosA = ((Cx - Ax) * (Bx - Ax) + (Cy - Ay) * (By - Ay)) / (CA * AB);
		double cosB = ((Cx - Bx) * (Ax - Bx) + (Cy - By) * (Ay - By)) / (CB * AB);
		double cosC = ((Ax - Cx) * (Bx - Cx) + (Ay - Cy) * (By - Cy)) / (CA * CB);

		double angA = Math.acos(cosA);
		double angB = Math.acos(cosB);
		double angC = Math.acos(cosC);

		if (angA > angC && angA > angB) {
			return Math.toDegrees(angA);
		}

		if (angC > angB) {
			return Math.toDegrees(angC);
		}

		return Math.toDegrees(angB);
	}
}
