package br.com.vinicius.hackerrank.euler210;

public class CopyOfObtusedAngledTriangles {
	public static void main(String[] args) {
		try {
			System.out.println(new CopyOfObtusedAngledTriangles(2, 0, 1, 1).countPoints());
		} catch (Exception e) {
			return;
		}
	}

	private double Ax, Ay, Bx, By, AB, ABx, ABy;
	long count;
	private long r;

	public CopyOfObtusedAngledTriangles(long r, long a, long b, long n) {
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

	private double calcMaxAngle(long Cx, long Cy) {

		double CA = calcDist(Cx - Ax, Cy - Ay);

		if (CA == 0) {
			return 0;
		}
		double CAx = Ax - Cx;
		double CAy = Ay - Cy;

		double BCx = Cx - Bx;
		double BCy = Cy - By;

		double CB = calcDist(BCx, BCy);
		if (CB == 0) {
			return 0;
		}

		double cosA = -1 * (CAx * ABx + CAy * ABy) / (CA * AB);
		double cosB = -1 * (BCx * ABx + BCy * ABy) / (CB * AB);
		double cosC = -1 * (CAx * BCx + CAy * BCy) / (CA * CB);

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

	public long countPoints() {
		if (AB == 0) {
			return 0;
		}

		double max = -10;
		boolean isRect = false;
		for (long Cx = r; Cx >= -r; Cx--) {
			for (long Cy = r; Cy >= -r; Cy--) {
				isRect = (Cx == Cy || Cx == -Cy) || (Cx == Bx && Cy == Ay) || (Cx == Ax && Cy == By);

				if (isRect || (Math.abs(Cx) + Math.abs(Cy) > r)) {
					// continue;
				}
				max = calcMaxAngle(Cx, Cy);
				System.out.println("(" + Cx + ", " + Cy + ")=" + max);
				if (max > 90d && max < 180d) {
					count++;
				}
			}
		}
		return count;
	}
}
