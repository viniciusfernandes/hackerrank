package br.com.challanges.olipmpiadasinformatica;

import java.util.Scanner;

public class MatrizSuperLegal {

	final static int MAX = 1000000;
	final static int MIN = -1000000;

	public static void main(String[] args) {
		final Scanner in = new Scanner(System.in);

		final String[] size = in.nextLine().split(" ");
		final int rows = Integer.parseInt(size[0]);
		final int cols = Integer.parseInt(size[1]);

		if (rows < 2 || rows > 1000 || cols < 2 || cols > 1000) {
			in.close();
			return;
		}

		final int[][] matrix = new int[rows][cols];
		String[] line = null;
		int val;
		for (int i = 0; i < rows; i++) {
			line = in.nextLine().split(" ");
			for (int j = 0; j < line.length; j++) {
				val = Integer.parseInt(line[j]);
				if (val < MIN || val > MAX) {
					in.close();
					return;
				}
				matrix[i][j] = val;
			}
		}
		in.close();

		System.out.println(countSuperLegal(matrix));
	}

	private static boolean isLegal(int[][] matrix, int initRow, int initCol, int endRow, int endCol) {
		final int a1_1 = matrix[0][0];
		int ai_j;
		int a1_j;
		int ai_1;
		for (int i = initRow; i <= endRow; i++) {
			ai_1 = matrix[i][1];
			for (int j = initCol; j <= endCol; j++) {
				a1_j = matrix[0][j];
				ai_j = matrix[i][j];
				if (a1_1 + ai_j > a1_j + ai_1) {
					return false;
				}
			}
		}
		return true;
	}

	private static int countSuperLegal(int[][] matrix) {

		int initRow = 0;
		int initCol = 0;
		int lengthRow = 1;
		int lengthCol = 1;

		final int MAX_LENGTH_ROW = matrix.length;
		final int MAX_LENGTH_COL = matrix[0].length;
		final int LAST_ROW = matrix.length - 2;
		final int LAST_COL = matrix[0].length - 1;
		int totalElements = 0;
		int mexElements = 0;
		while (initRow <= LAST_ROW) {
			while (initCol <= LAST_COL) {
				lengthRow = MAX_LENGTH_ROW - initRow - 1;

				while (lengthRow >= 1) {
					lengthCol = MAX_LENGTH_COL - initCol - 1;

					while (lengthCol >= 1) {
						if (isLegal(matrix, initRow, initCol, lengthRow, lengthCol)) {
							totalElements = (lengthRow - initRow + 1) * (lengthCol - initCol + 1);
							if (totalElements > mexElements) {
								mexElements = totalElements;
							}
						}
						lengthCol--;
					}
					lengthRow--;
					lengthCol = MAX_LENGTH_COL - initCol - 1;
				}

				lengthRow = 1;
				initCol++;
			}

			initRow++;
			initCol = 1;
		}
		return mexElements;
	}

}
