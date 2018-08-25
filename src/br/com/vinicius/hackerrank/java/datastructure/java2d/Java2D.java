package br.com.vinicius.hackerrank.java.datastructure.java2d;

import java.util.Scanner;

public class Java2D {

	private static final Scanner scanner = new Scanner(System.in);

	public static int sumHourglasses(int[][] arr) {
		int tot = 0;
		int max = 0;
		boolean first = true;
		for (int l = 1; l <= 4; l++) {
			for (int c = 1; c <= 4; c++) {
				// linha de cima
				tot += arr[l - 1][c - 1];
				tot += arr[l - 1][c];
				tot += arr[l - 1][c + 1];

				// linha central
				tot += arr[l][c];

				// linha de baixo
				tot += arr[l + 1][c - 1];
				tot += arr[l + 1][c];
				tot += arr[l + 1][c + 1];
				if (first || tot > max) {
					max = tot;
					first = false;
				}
				tot = 0;
			}

		}
		return max;
	}

	public static void main(String[] args) {
		int[][] arr = new int[6][6];
		try {
			for (int i = 0; i < 6; i++) {
				// String[] arrRowItems = scanner.nextLine().split(" ");
				// scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

				for (int j = 0; j < 6; j++) {
					// int arrItem = Integer.parseInt(arrRowItems[j]);
					// arr[i][j] = arrItem;
				}
			}

			scanner.close();
		} catch (Exception e) {
			return;
		}

		arr[0] = new int[] { 1, 1, 1, 0, 0, 0 };
		arr[1] = new int[] { 0, 1, 0, 0, 0, 0 };
		arr[2] = new int[] { 1, 1, 1, 0, 0, 0 };
		arr[3] = new int[] { 0, 0, 2, 4, 4, 0 };
		arr[4] = new int[] { 0, 0, 0, 2, 0, 0 };
		arr[5] = new int[] { 0, 0, 1, 2, 4, 0 };

		arr[0] = new int[] { -1, -1, 0, -9, -2, -2 };
		arr[1] = new int[] { -2, -1, -6, -8, -2, -5 };
		arr[2] = new int[] { -1, -1, -1, -2, -3, -4 };
		arr[3] = new int[] { -1, -9, -2, -4, -4, -5 };
		arr[4] = new int[] { -7, -3, -3, -2, -9, -9 };
		arr[5] = new int[] { -1, -3, -1, -2, -4, -5 };

		System.out.println(Java2D.sumHourglasses(arr));

	}
}
