package br.com.challanges.hackerrank.euler215;

import java.util.ArrayList;
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
	long height = 0;
	long totWidth = 0;
	int width = 0;

	private void generate(Brick bParent, long maxWidth) {
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

class Wall extends Brick {
	class Line {
		long bits;
		List<Line> subLines = new ArrayList<>(10);

		Line(long bits) {
			this.bits = bits;
		}

		public String toString() {
			return "L" + " " + Long.toBinaryString(bits);
		}
	}

	Map<Long, List<Long>> baseLine = new HashMap<>(100);
	long countWalls;
	long mod;

	public Wall(int width, int height, long mod) throws Exception {
		this.width = width;
		this.height = height;
		this.mod = mod;
		if (height < 2 || height > 100 || width < 5 || width > 45 || mod < 1 || mod > Math.pow(2, 30)) {
			throw new Exception("Invalid parameters");
		}
	}

	public long countWalls() {
		Date ini = new Date();
		generateBitLines();
		generateBaseLines();

		System.out.println("Bit Lines=" + bitLines.size() + " Base Lines=" + baseLine.size());

		generateWalls();

		System.out.println("seconds: " + (new Date().getTime() - ini.getTime()) / 1000);
		System.out.println("count=" + countWalls);
		return countWalls % mod;
	}

	private void generateBaseLines() {
		List<Long> sub = null;
		for (Long bit : bitLines) {
			sub = new ArrayList<>(bitLines.size() > 2 ? bitLines.size() / 2 : 2);
			for (Long subBit : bitLines) {
				if ((bit & subBit) == 0) {
					sub.add(subBit);
				}
			}
			if (!sub.isEmpty()) {
				baseLine.put(bit, sub);
			}
		}
	}

	private void generateBitLines() {
		bitLines = new ArrayList<>(width);
		generate(width);
	}

	private void generateWalls() {
		for (Long line : baseLine.keySet()) {
			generateWalls(line, 0);
		}

	}

	private void generateWalls(Long line, long depth) {

		if (depth >= height - 1) {
			countWalls++;
			return;
		}

		if (baseLine.get(line) == null) {
			return;
		}

		for (long subLine : baseLine.get(line)) {
			generateWalls(subLine, depth + 1);
		}
	}
}

public class WallCrackFree {

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
