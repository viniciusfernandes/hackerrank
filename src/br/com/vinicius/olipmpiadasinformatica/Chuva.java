package br.com.vinicius.olipmpiadasinformatica;

import java.util.Scanner;

public class Chuva {
	private static char[][] c = null;
	private static int n;
	private static int m;

	public static void main(String[] args) {
		final Scanner in = new Scanner(System.in);

		final String[] dimension = in.nextLine().split(" ");
		n = Integer.parseInt(dimension[0]);
		m = Integer.parseInt(dimension[1]);
		c = new char[n][m];

		for (int i = 0; i < n; i++) {
			c[i] = in.nextLine().toCharArray();
		}

		in.close();

		final boolean outOfRange = n < 3 || n > 500 || m < 3 || m > 500;
		final boolean isOdd = n % 2 != 0;
		if (!isOdd || outOfRange || !firstLineOk() || !columnsOk()) {
			return;
		}

		final StringBuilder wall = new StringBuilder();

		for (int row = 0; row < n; row++) {
			for (int col = 0; col < m; col++) {
				replace(row, col);
			}
		}

		for (int row = 0; row < n; row++) {
			for (int col = 0; col < m; col++) {
				wall.append(c[row][col]);
			}
			wall.append("\n");
		}

		System.out.println(wall);
	}

	private static void replace(int row, int col) {
		if (row < 0 || col < 0) {
			return;
		}
		if (c[row][col] == '.' && (hasWaterDown(row, col) || hasWaterRight(row, col) || hasWaterLeft(row, col))) {
			c[row][col] = 'o';

			replace(row, col - 1);
		}
	}

	private static boolean firstLineOk() {
		int count = 0;
		for (int i = 0; i < c[0].length; i++) {
			if (c[0][i] == 'o' && ++count > 1) {
				return false;
			}
		}
		return true;
	}

	private static boolean columnsOk() {
		for (int row = 0; row < c.length; row++) {
			if (row % 2 != 0 && (c[row][0] == '#' || c[row][m - 1] == '#')) {
				return false;
			}
		}

		return true;
	}

	private static boolean hasWaterDown(int row, int col) {
		if (row <= 0) {
			return false;
		}

		return c[row - 1][col] == 'o';
	}

	private static boolean hasWaterRight(int row, int col) {
		if (row >= n - 1 || col <= 0) {
			return false;
		}

		return c[row][col - 1] == 'o' && c[row + 1][col - 1] == '#';
	}

	private static boolean hasWaterLeft(int row, int col) {
		if (row >= n - 1 || col >= m - 1) {
			return false;
		}

		return c[row][col + 1] == 'o' && c[row + 1][col + 1] == '#';
	}
}