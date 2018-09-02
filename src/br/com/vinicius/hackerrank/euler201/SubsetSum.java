package br.com.vinicius.hackerrank.euler201;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class SubsetSum {
	public static void main(String[] args) {
		// System.out.println(new SubsetSum(new int[] { 1, 3, 6, 8, 10, 11 },
		// 3).count());
		System.out.println(new SubsetSum(new int[] { 1, 2, 3, 4 }, 3).count());
	}

	int m = 0;

	Map<Integer, Boolean> map = new HashMap<>(1000);
	private int[] numbers = null;
	private int maxInit = 0;

	private Boolean ok = false;

	private int parcSum = 0;

	private Integer sum = 0;

	public SubsetSum(int[] numbers, int m) {
		if (numbers.length < 1 || numbers.length > 100 || m < 1 || m > numbers.length) {
			throw new IllegalArgumentException();
		}
		for (int i : numbers) {
			if (i < 1 || i > 100) {
				throw new IllegalArgumentException();
			}
		}
		Arrays.sort(numbers);
		this.numbers = numbers;
		this.m = m;
		maxInit = numbers.length - m;
	}

	public int count() {
		count(new int[m], 0, 0, 0);

		int totSum = 0;
		Set<Entry<Integer, Boolean>> entry = map.entrySet();
		for (Entry<Integer, Boolean> e : entry) {
			if (!e.getValue()) {
				totSum += e.getKey();
			}
		}

		return totSum;
	}

	public void count(int[] subset, int init, int idx, int shift) {
		if (init + m > numbers.length) {
			return;
		}
		if (idx < init + m - 1) {
			if (idx <= init) {
				subset[idx] = numbers[init + idx];
			} else {
				subset[idx] = numbers[init + idx + shift];
			}
			count(subset, init, idx + 1, shift);
		} else {
			sum = 0;
			ok = false;
			parcSum = 0;
			for (int i = 0; i < subset.length - 1; i++) {
				parcSum += subset[i];
			}

			for (int i = init + idx + shift; i < numbers.length; i++) {
				sum = parcSum + numbers[i];
				if ((ok = map.get(sum)) == null) {
					map.put(sum, Boolean.FALSE);
				} else if (ok.equals(Boolean.FALSE)) {
					map.put(sum, Boolean.TRUE);
				}
			}
			if (init + (++shift) + m - 1 < numbers.length) {
				count(subset, init, 0, shift);
			} else if ((++init) + m - 1 < numbers.length) {
				count(subset, init, 0, 0);
			} else {
				return;
			}

		}
	}
}
