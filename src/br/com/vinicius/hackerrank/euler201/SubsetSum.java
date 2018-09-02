package br.com.vinicius.hackerrank.euler201;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.Set;

class SubsetSum {
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
			System.out.println(new SubsetSum(numbers, m).count());
		} catch (Exception e) {
			return;
		}

	}

	int m = 0;

	Map<Integer, Boolean> map = new HashMap<>(1000);
	private int[] numbers = null;
	private Boolean ok = false;
	private int parcSum = 0;
	private Integer sum = 0;

	public SubsetSum(int[] numbers, int m) throws Exception {
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

	public int count() {
		count(new int[m - 1], 0, 0, 0);

		int totSum = 0;
		Set<Entry<Integer, Boolean>> entry = map.entrySet();
		for (Entry<Integer, Boolean> e : entry) {
			if (!e.getValue()) {
				totSum += e.getKey();
			}
		}

		return totSum;
	}

	// private StringBuilder s = null;

	public void count(int[] subset, int init, int idx, int shift) {
		// limitacao do cursor inicial
		if (init + m > numbers.length) {
			return;
		}
		// s = new StringBuilder();

		// limitacao do indice que parte do valor inicial ate o limite do
		// subset.
		// s.append("{");
		while (idx < subset.length) {
			if (idx == 0) {
				subset[idx] = numbers[init];
			} else {
				subset[idx] = numbers[init + idx + shift];
			}
			// s.append(subset[idx]).append(", ");
			idx++;
		}

		sum = 0;
		ok = false;
		parcSum = 0;
		for (int i = 0; i < subset.length; i++) {
			parcSum += subset[i];
		}

		for (int i = init + idx + shift; i < numbers.length; i++) {
			sum = parcSum + numbers[i];
			// System.out.println(s.toString() + numbers[i] + "}=" + sum);
			if ((ok = map.get(sum)) == null) {
				map.put(sum, Boolean.FALSE);
			} else if (ok.equals(Boolean.FALSE)) {
				map.put(sum, Boolean.TRUE);
			}
		}
		if (init + (shift + 1) + m - 1 < numbers.length) {
			count(subset, init, 0, shift + 1);
		} else if ((++init) + m - 1 < numbers.length) {
			count(subset, init, 0, 0);
		} else {
			return;
		}

	}
}
