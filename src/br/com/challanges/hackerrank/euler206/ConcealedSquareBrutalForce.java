package br.com.challanges.hackerrank.euler206;

import java.util.Scanner;

public class ConcealedSquareBrutalForce {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int l = in.nextInt();
		in.nextLine();
		String[] s = in.nextLine().split(" ");
		in.close();

		if (s.length != l) {
			return;
		}

		try {
			long[] digits = new long[l];
			for (int i = 0; i < digits.length; i++) {
				digits[i] = Long.parseLong(s[i]);
			}
			System.out.println(new ConcealedSquareBrutalForce(digits).concealedNumber());
		} catch (Exception e) {
			return;
		}
	}

	private long[] digits = null;

	final int firstDig;

	public ConcealedSquareBrutalForce(long[] digits) {
		if (digits == null || digits.length < 3 || digits.length > 15) {
			throw new IllegalStateException();
		}
		int l = digits.length - 1;
		if (digits[l] == 2 || digits[l] == 3 || digits[l] == 7 || digits[l] == 8) {
			throw new IllegalStateException();
		}

		this.digits = new long[2 * digits.length - 1];
		firstDig = this.digits.length - 2;
		for (int i = 0; i < digits.length; i++) {
			this.digits[2 * i] = digits[i];
		}
	}

	public long concealedNumber() {
		long x = 0;

		int dig = firstDig;
		double sqrt = 0;
		for (int j = 0; dig >= 1; j++) {
			if (digits[firstDig] == 9) {
				if (digits[dig] == 9) {
					dig -= 2;
					if (dig <= 0) {
						throw new IllegalStateException();
					}
				}
				increment(digits, dig);
			}
			if (j > 9) {
				j = 0;
			}
			digits[firstDig] = j;
			x = 0;
			for (int i = 0, k = digits.length - 1; i < digits.length; i++, k--) {
				x += digits[i] * Math.pow(10, k);
			}

			sqrt = Math.ceil(Math.sqrt(x));
			if (sqrt * sqrt == x) {
				return (long) sqrt;
			}
		}
		return 0;
	}

	private void increment(long[] digits, int dig) {
		for (int i = firstDig; i >= dig; i -= 2) {
			if (digits[i] == 9) {
				digits[i] = 0;
				continue;
			} else {
				digits[i]++;
				break;
			}
		}
	}

}
