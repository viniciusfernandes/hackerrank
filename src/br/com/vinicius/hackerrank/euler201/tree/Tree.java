package br.com.vinicius.hackerrank.euler201.tree;

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
	Node root;
	final Map<Integer, Integer> partialSums = new HashMap<>(1000);

	public Tree(int[] numbers, int setSize) {
		this.numbers = numbers;
		this.setSize = setSize;
		stopLevel = setSize - 1;
		stopIndex = numbers.length - setSize;
	}

	public int sumSubsets() {
		if (setSize == 1 || setSize == numbers.length) {
			for (int i = 0; i < numbers.length; i++) {
				addPartialSum(numbers[i]);
			}
		}
		else {

			Node parent = null;
			for (int i = 0; i <= stopIndex; i++) {
				parent = new Node(numbers[i], numbers[i], i, 1, null);
				addSubnode(parent);
			}
		}
		int totalSum = 0;
		final Set<Entry<Integer, Integer>> set = partialSums.entrySet();
		for (final Entry<Integer, Integer> entry : set) {
			if (entry.getValue().intValue() == 1) {
				totalSum += entry.getKey();
			}
		}
		return totalSum;
	}

	private void addPartialSum(int sum) {
		final Integer occur = partialSums.get(sum);
		if (occur == null) {
			partialSums.put(sum, 1);
		}
		else {
			partialSums.put(sum, 2);
		}
	}

	private void addSubnode(Node node) {
		if (node.level >= stopLevel) {
			// final String s = stringfy(node);

			for (int i = node.index + 1; i < numbers.length; i++) {
				// System.out.println(s + numbers[i] + "}");
				addPartialSum(node.cost + numbers[i]);
			}
		}
		else {
			int cost = -1;
			Node subnode = null;
			final int level = node.level + 1;
			for (int i = node.index + 1; i < numbers.length; i++) {
				cost = node.cost + numbers[i];
				subnode = new Node(numbers[i], cost, i, level, node);
				node.subnodes.add(subnode);
				addSubnode(subnode);
			}
		}

	}

	private String stringfy(Node node) {
		String s = "";
		Node n = node;

		while (n.parent != null) {
			s += n.value + ",";
			n = n.parent;
		}
		s += n.value;
		final String[] nums = s.split(",");
		s = "";
		for (int i = nums.length - 1; i >= 0; i--) {
			s += nums[i] + ",";
		}

		return "{" + s;
	}

	public static void main(String[] args) {
		// EXEMPLO
		// 20 3
		// 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20

		// 6 3
		// 1 2 3 4 5 6

		// 100 50
		// 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35
		// 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52 53 54 55 56 57 58 59 60 61 62 63 64 65 66 67
		// 68 69 70 71 72 73 74 75 76 77 78 79 80 81 82 83 84 85 86 87 88 89 90 91 92 93 94 95 96 97 98 99
		// 100
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

class Node {
	public int value;
	public int cost;
	public int index;
	public int level;
	public Node parent;
	public List<Node> subnodes = new ArrayList<>(100);

	public Node(int value, int cost, int index, int level, Node parent) {
		this.value = value;
		this.cost = cost;
		this.index = index;
		this.level = level;
		this.parent = parent;
	}

	@Override
	public String toString() {
		return "{level=" + level + ", value=" + value + ", cost=" + cost + ", index=" + index + "}";
	}
}
