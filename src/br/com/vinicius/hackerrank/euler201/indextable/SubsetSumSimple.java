package br.com.vinicius.hackerrank.euler201.indextable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Map.Entry;

public class SubsetSumSimple {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String[] line = in.nextLine().split("\\s+");
		int n = 0, m = 0;
		try {
			n = Integer.parseInt(line[0]);
			m = Integer.parseInt(line[1]);
		} catch (Exception e) {
			in.close();
			return;
		}
		if (n < 1 || n > 100) {
			in.close();
			return;
		}
		int[] numbers = new int[n];

		line = in.nextLine().split("\\s+");
		in.close();

		if (line.length != n) {
			return;
		}
		for (int i = 0; i < line.length; i++) {
			try {
				numbers[i] = Integer.parseInt(line[i]);
			} catch (Exception e) {
				return;
			}
		}
		try {
			System.out.println(new SubsetSumSimple(numbers, m).count());
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

	}

	int m = 0;

	public int count() {
		int totSum = 0;
		if (m == 1 || m == numbers.length) {
			for (int i = 0; i < numbers.length; i++) {
				totSum += numbers[i];
			}
			return totSum;
		}

		int[] idxTable = new int[m];
		for (int i = 0; i < idxTable.length; i++) {
			idxTable[i] = i;
		}

		int[] idxMax = new int[m];
		for (int i = 0; i < idxMax.length; i++) {
			idxMax[i] = numbers.length - m + i;
		}

		count(new int[m], idxTable, idxMax, idxTable.length - 1);

		Set<Entry<Integer, Boolean>> entry = map.entrySet();
		for (Entry<Integer, Boolean> e : entry) {
			if (!e.getValue()) {
				totSum += e.getKey();
			}
		}

		return totSum;
	}

	private void count(int[] subset, int[] idxTable, int[] idxMax, int col) {
		if (col < 0) {
			return;
		}

		for (int j = 0; j < idxTable.length; j++) {
			subset[j] = numbers[idxTable[j]];
		}
		System.out.println(Arrays.toString(subset));
		sum(subset);

		if (idxTable[col] < idxMax[col]) {
			idxTable[col]++;
			for (int i = col + 1; i < idxTable.length; i++) {
				if (++idxTable[i] > idxMax[i]) {
					idxTable[i] = idxMax[i];
				}
			}
		} else if (idxTable[col] == idxMax[col] && --col >= 0) {
			idxTable[col]++;
			int idx = idxTable[col];
			for (int i = col + 1; i < idxTable.length; i++) {
				idxTable[i] = ++idx;
			}
		}
		count(subset, idxTable, idxMax, col);
	}

	private void sum(int[] subset) {
		int sum = 0;
		for (int i = 0; i < subset.length; i++) {
			sum += subset[i];
		}
		if ((ok = map.get(sum)) == null) {
			map.put(sum, Boolean.FALSE);
		} else if (ok.equals(Boolean.FALSE)) {
			map.put(sum, Boolean.TRUE);
		}
	}

	Map<Integer, Boolean> map = new HashMap<>(1000);

	private int[] numbers = null;
	private Boolean ok = false;
	private int parcSum = 0;
	private int sum = 0;

	public SubsetSumSimple(int[] numbers, int m) throws Exception {
		if (numbers.length < 1 || numbers.length > 100 || m < 1 || m > numbers.length) {
			throw new Exception();
		}
		for (int i : numbers) {
			if (i < 1 || i > 100) {
				throw new IllegalArgumentException();
			}
		}
		Arrays.sort(numbers);
		this.numbers = numbers;
		this.m = m;
	}
}
