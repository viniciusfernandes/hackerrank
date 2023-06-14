package br.com.challanges.hackerrank.euler207;

public class IntegerPartition {
	double proportion = 0;

	public IntegerPartition(double a, double b) {
		this.proportion = a / b;
	}

	public long infPartition() {
		long m = 1;
		long k = 1;
		long t = 0;
		long Pm = 0;
		double x = 0, y = 0;
		while (Pm == 0 || 1 / Pm > proportion) {
			Pm = 0;
			while (k <= m) {
				while (x <= y) {
					x = Math.pow(4, t);
					y = Math.pow(2, t) + k;

					if (x == y) {
						Pm++;
						break;
					}

					if (t == 1 && x > y) {
						break;
					}
					t++;
				}
				k++;
				x = y = 0;
				t = 0;
			}
			m++;
			k = 1;
		}
		return m;
	}

	public static void main(String[] args) {
		System.out.println(new IntegerPartition(1, 2).infPartition());
	}

}
