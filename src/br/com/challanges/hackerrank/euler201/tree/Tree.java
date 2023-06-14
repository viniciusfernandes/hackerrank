package br.com.challanges.hackerrank.euler201.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

public class Tree {
	final int[] numbers;
	final int setSize;
	final int stopLevel;
	final int stopIndex;
	final Map<Integer, Integer> sumSubsets = new HashMap<>(1000);
	final Map<Integer, List<Integer>> cachedSums = new HashMap<>(100);

	public Tree(int[] numbers, int setSize) {
		this.numbers = numbers;
		this.setSize = setSize;
		stopLevel = setSize - 1;
		stopIndex = numbers.length - setSize + 1;
	}

	public int sumSubsets() {
		if (setSize == 1 || setSize == numbers.length) {
			sumSubsetsSize1();
		}
		else if (setSize == 2) {
			sumSubsetsSize2();
		}
		else {
			sumSubsetsSizeHigher();
		}

		int totalSum = 0;
		final Set<Entry<Integer, Integer>> set = sumSubsets.entrySet();
		for (final Entry<Integer, Integer> entry : set) {
			if (entry.getValue().intValue() == 1) {
				totalSum += entry.getKey();
			}
		}
		return totalSum;
	}

	public void sumSubsetsSize1() {
		for (int i = 0; i < numbers.length; i++) {
			addSumSubsets(numbers[i]);
		}
	}

	public void sumSubsetsSize2() {
		for (int i = 0; i <= stopIndex; i++) {
			for (int j = i + 1; j < numbers.length; j++) {
				cost = numbers[i] + numbers[j];
				addSumSubsets(cost);
			}

		}
	}

	public void sumSubsetsSizeHigher() {
		addSubnode(numbers[0], numbers[0], 0, 0, numbers[0], -1);

		for (int i = 1; i <= stopIndex; i++) {
			addSubnodesCached(i, numbers[i]);
		}

	}

	private void addSumSubsets(int sum) {
		final Integer occur = sumSubsets.get(sum);
		if (occur == null) {
			sumSubsets.put(sum, 1);
		}
		else {
			sumSubsets.put(sum, 2);
		}
	}

	private int cost = -1;

	private void addSubnode(int nodeValue, int nodeCost, int nodeIndex, int nodeLevel, int rootValue,
			int firstChildValue) {
		if (nodeIndex >= stopIndex) {
			return;
		}

		if (nodeLevel >= stopLevel) {
			final int partialCost = rootValue + nodeCost;
			final List<Integer> costs = new ArrayList<>(50);
			int cachedCost = -1;
			for (int i = nodeIndex + 1; i < numbers.length; i++) {
				cost = partialCost + numbers[i];
				cachedCost = nodeValue + numbers[i];

				addSumSubsets(cost);
				costs.add(cachedCost);
			}
			cachedSums.put(nodeIndex, costs);

		}
		else {
			final int level = nodeLevel + 1;
			for (int idx = nodeIndex + 1; idx <= stopIndex; idx++) {
				System.out.println("parent=" + nodeIndex + " child=" + idx + " level=" + level + " valor=" + numbers[idx]);

				if (level == 1) {
					rootValue = nodeValue;
					addSubnode(numbers[idx], numbers[idx], idx, level, rootValue, numbers[idx]);
				}
				else {
					cost = nodeCost + numbers[idx];
					addSubnode(numbers[idx], cost, idx, level, rootValue, nodeValue);
				}
			}
		}
	}

	private List<Integer> sums = null;

	private void addSubnodesCached(int parentIndex, int parentValue) {
		for (int i = parentIndex; i <= stopIndex; i++) {
			sums = cachedSums.get(i + 1);
			if (sums == null) {
				throw new IllegalStateException(
						"Nao existe a somas parcial para o parent =" + parentValue + " e valor=" + parentValue);
			}
			for (final int sum : sums) {
				cost = parentValue + sum;
				addSumSubsets(parentValue + sum);
			}
		}
	}

	public static void main(String[] args) {
		final Scanner in = new Scanner(System.in);
		String[] line = in.nextLine().split("\\s+");
		int n = 0;
		int m = 0;
		try {
			n = Integer.parseInt(line[0]);
			m = Integer.parseInt(line[1]);
		}
		catch (final Exception e) {
			in.close();
			return;
		}
		if ((n < 1 || n > 100) && (m < 1 || m > n)) {
			in.close();
			return;
		}

		final int[] numbers = new int[n];

		line = in.nextLine().split("\\s+");
		in.close();

		if (line.length != n) {
			return;
		}
		for (int i = 0; i < line.length; i++) {
			try {
				numbers[i] = Integer.parseInt(line[i]);
				if (numbers[i] < 1 || numbers[i] > 100) {
					return;
				}
			}
			catch (final Exception e) {
				return;
			}
		}
		try {
			System.out.println(new Tree(numbers, m).sumSubsets());
		}
		catch (final Exception e) {
			e.printStackTrace();
			return;
		}
	}

}