package br.com.vinicius.hackerrank.euler215.parallel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Brick {
	List<Long> bitLines = null;

	long bits = 1;
	Brick bLeft;
	Brick bRight;
	int height = 0;
	int totWidth = 0;
	int width = 0;

	private void generate(Brick bParent, int maxWidth) {
		Brick bLeft = new Brick();
		Brick bRight = new Brick();

		bLeft.width = 2;
		bRight.width = 3;

		bParent.bLeft = bLeft;
		bParent.bRight = bRight;

		bLeft.totWidth = bLeft.width + bParent.totWidth;
		bRight.totWidth = bRight.width + bParent.totWidth;

		bLeft.bits = bLeft.bits << bLeft.totWidth;
		bLeft.bits = bLeft.bits | bParent.bits;

		bRight.bits = bRight.bits << bRight.totWidth;
		bRight.bits = bRight.bits | bParent.bits;

		if (bLeft.totWidth < maxWidth) {
			generate(bLeft, maxWidth);
		} else if (bLeft.totWidth == maxWidth) {
			bLeft.bits = bParent.bits;
			bitLines.add(bLeft.bits);
		}

		if (bRight.totWidth < maxWidth) {
			generate(bRight, maxWidth);
		} else if (bRight.totWidth == maxWidth) {
			bRight.bits = bParent.bits;
			bitLines.add(bRight.bits);
		}
	}

	protected void generate(int width) {
		bitLines = new ArrayList<>(width);
		Brick b = new Brick();
		b.bits = 0;
		generate(b, width);

	}

	public String linesToString() {
		StringBuilder s = new StringBuilder();
		for (long l : bitLines) {
			s.append(Long.toBinaryString(l)).append("\n");
		}
		return s.toString();
	}

	public String toString() {
		return Long.toBinaryString(bits);
	}

}

class CounterWalls implements Runnable {
	Map<Long, List<Long>> baseLine = new HashMap<>(100);
	int count = 0;
	int height = 0;
	Long[] lines = null;
	boolean ok = false;

	CounterWalls(Long[] lines, int height, Map<Long, List<Long>> baseLine) {
		this.baseLine = baseLine;
		this.height = height;
		this.lines = lines;
	}

	private void generateWalls(Long line, int depth) {

		if (depth >= height - 1) {
			count++;
			return;
		}

		if (baseLine.get(line) == null) {
			return;
		}

		for (long subLine : baseLine.get(line)) {
			generateWalls(subLine, depth + 1);
		}
	}

	@Override
	public void run() {
		for (Long line : lines) {
			generateWalls(line, 0);
		}
		ok = true;
	}

}

class Wall extends Brick {
	class Line {
		int bits;
		List<Line> subLines = new ArrayList<>(10);

		Line(int bits) {
			this.bits = bits;
		}

		public String toString() {
			return "L" + " " + Integer.toBinaryString(bits);
		}
	}

	Map<Long, List<Long>> baseLine = new HashMap<>(100);
	int mod;

	public Wall(int width, int height, int mod) throws Exception {
		this.width = width;
		this.height = height;
		this.mod = mod;
		if (height < 2 || height > 100 || width < 5 || width > 45 || mod < 1 || mod > Math.pow(2, 30)) {
			throw new Exception("Invalid parameters");
		}
	}

	public int countWalls() {
		Date ini = new Date();
		generateBitLines();
		generateBaseLines();

		System.out.println("Bit Lines=" + bitLines.size() + " Base Lines=" + baseLine.size());

		CounterWalls[] counters = null;
		int rate = baseLine.keySet().size();
		if (rate >= 10 && rate <= 10000) {
			rate = rate / 2;
			counters = new CounterWalls[2];

		} else if (rate > 10000) {
			rate = rate / 2;
			counters = new CounterWalls[2];
		} else {
			counters = new CounterWalls[1];
		}

		Long[] lines = bitLines.toArray(new Long[] {});
		if (counters.length == 1) {
			counters[0] = new CounterWalls(lines, height, baseLine);
			new Thread(counters[0]).start();
		} else {
			for (int init = 0, j = 0, end = 0; j < counters.length; end++) {
				if (end != 0 && end % rate == 0) {
					counters[j] = new CounterWalls(Arrays.copyOfRange(lines, init, end), height, baseLine);
					new Thread(counters[j]).start();
					j++;
					init = end;
				}
			}
		}
		boolean ok = false;
		while (!ok) {
			ok = true;
			for (CounterWalls c : counters) {
				ok &= c.ok;
			}

		}
		int countWalls = 0;
		for (CounterWalls c : counters) {
			countWalls += c.count;
		}

		System.out.println("seconds: " + (new Date().getTime() - ini.getTime()) / 1000);
		System.out.println("total=" + countWalls);
		return countWalls % mod;
	}

	private void generateBaseLines() {
		List<Long> adjLines = null;
		for (Long line : bitLines) {
			for (Long subBit : bitLines) {
				if ((line & subBit) == 0) {
					if (adjLines == null) {
						adjLines = new ArrayList<>(100);
					}
					adjLines.add(subBit);
				}
			}
			if (adjLines != null && !adjLines.isEmpty()) {
				baseLine.put(line, adjLines);
			}
			adjLines = null;
		}
	}

	private void generateBitLines() {
		bitLines = new ArrayList<>(width);
		generate(width);
	}
}

public class WallCrackFreeParallel {

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
		String[] s = in.nextLine().split("\\s+");
		in.close();

		int width = Integer.parseInt(s[0]);
		int heigth = Integer.parseInt(s[1]);
		int mod = Integer.parseInt(s[2]);

		Wall wall;
		try {
			wall = new Wall(width, heigth, mod);
			System.out.println(wall.countWalls());
		} catch (Exception e) {
			return;
		}
	}
}
