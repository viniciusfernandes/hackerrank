package br.com.challanges.hackerrank.euler215.table;

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
		int idx;
		private Line last;
		Line mainChild;
		private Map<Long, Line> mapChild = new HashMap<>(1000);
		Line next;
		private Line nextChild = null;
		Line parent;

		Line previous;
		long totChildren = 0;

		Line(long bits) {
			this.bits = bits;
		}

		Line(long bits, int idx) {
			this.bits = bits;
			this.idx = idx;
		}

		public void addChild(Line line) {
			if (bits == -1) {
				mapChild.put(line.bits, line);
			}

			line.parent = this;
			if (mainChild == null) {
				mainChild = line;
				last = mainChild;
				totChildren++;
				return;
			}

			if (last == null) {
				mainChild.next = line;
				line.previous = mainChild;
				last = line;
			} else {
				last.next = line;
				line.previous = last;
				last = line;
			}
			totChildren++;
		}

		public Line nextChild() {
			if (nextChild == null) {
				nextChild = mainChild;
			} else if (nextChild != null && nextChild.next != null) {
				nextChild = nextChild.next;
			} else {
				return null;
			}
			return nextChild;
		}

		public void reset() {
			this.nextChild = null;
		}

		public String toString() {
			return "L" + " " + Long.toBinaryString(bits);
		}

		public long total() {
			long tot = 0;
			Line l = null;
			while ((l = nextChild()) != null) {
				tot += l.totChildren;
			}
			return tot;
		}
	}

	Line adjacentLines = new Line(-1);
	private long tableAfter[] = null;

	long mod;

	public Wall(int width, int height, long mod) throws Exception {
		this.width = width;
		this.height = height;
		this.mod = mod;
		if (height < 2 || height > 100 || width < 5 || width > 45 || mod < 1 || mod > Math.pow(2, 30)) {
			throw new Exception("Invalid parameters");
		}
	}

	private long count() {
		long countWalls = 0;

		if (height == 1) {
			countWalls = tableAfter.length % mod;
		}
		Line c = null;
		Line l = null;
		long level = 2;
		long tableBefore[] = tableAfter;
		tableAfter = new long[tableAfter.length];

		while (true) {
			adjacentLines.reset();
			while ((l = adjacentLines.nextChild()) != null) {
				l.reset();
				while ((c = l.nextChild()) != null) {
					tableAfter[c.idx] += tableBefore[l.idx];
					tableAfter[c.idx] %= mod;
				}
			}
			if (level++ >= height) {
				break;
			}
			tableBefore = tableAfter;
			tableAfter = new long[tableAfter.length];
			// copy(tableAfter, tableBefore);
		}

		for (int i = 0; i < tableAfter.length; i++) {
			countWalls += tableAfter[i];
			countWalls %= mod;
		}

		return countWalls;
	}

	public long countWalls() {
		Date ini = new Date();
		generateBitLines();

		System.out.println("Bit Lines=" + bitLines.size() + " seconds: " + (new Date().getTime() - ini.getTime())
				/ 1000);

		ini = new Date();
		generateAdjacentLines();
		System.out.println("Adjacent Lines=" + adjacentLines.totChildren + " seconds: "
				+ (new Date().getTime() - ini.getTime()) / 1000);

		ini = new Date();
		long l = count();
		System.out.println("Count walls seconds: " + (new Date().getTime() - ini.getTime()) / 1000);

		return l;
	}

	private void generateAdjacentLines() {
		Line line = null;
		long bit = 0;
		long adjBit = 0;
		for (int i = 0; i < bitLines.size(); i++) {
			bit = bitLines.get(i);
			line = new Line(bit, i);
			adjacentLines.addChild(line);

			for (int j = 0; j < bitLines.size(); j++) {
				adjBit = bitLines.get(j);
				if ((bit & adjBit) == 0) {
					line.addChild(new Line(adjBit, j));
				}
			}
		}

		tableAfter = new long[bitLines.size()];
		for (int i = 0; i < tableAfter.length; i++) {
			tableAfter[i] = 1;
		}
	}

	private void generateBitLines() {
		bitLines = new ArrayList<>(width);
		generate(width);
	}

	public void printAdjacentLines() {
		StringBuilder s = new StringBuilder();
		Line l = null;

		adjacentLines.reset();
		while ((l = adjacentLines.nextChild()) != null) {
			s.append(l.bits).append(":{");
			l = l.mainChild;
			while (l != null) {
				s.append(l.bits);
				if ((l = l.next) != null) {
					s.append(", ");
				} else {
					break;
				}
			}
			s.append("}\n");
		}
		System.out.println(s.toString());
	}

	public void printBitLines() {
		StringBuilder s = new StringBuilder();
		for (Long l : bitLines) {
			s.append(l).append("\n");
		}
		System.out.println(s.toString());
	}

}

public class WallCrackFreeTable {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		try {
			String[] s = in.nextLine().split("\\s+");
			in.close();

			int width = Integer.parseInt(s[0]);
			int heigth = Integer.parseInt(s[1]);
			int mod = Integer.parseInt(s[2]);

			Wall wall;
			wall = new Wall(width, heigth, mod);
			System.out.println(wall.countWalls());
		} catch (Exception e) {
			return;
		}
	}
}
