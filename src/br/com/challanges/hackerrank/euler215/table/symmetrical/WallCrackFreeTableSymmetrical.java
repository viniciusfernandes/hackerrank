package br.com.challanges.hackerrank.euler215.table.symmetrical;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Brick {
	long BIT_WIDTH = 1;
	List<Brick> bitLines = null;

	List<Brick> bitLinesSym = null;

	long bits = 1;

	Brick bLeft;
	Brick bRight;
	Brick bSym;
	long height = 0;
	long totWidth = 0;
	int width = 0;

	@Override
	public boolean equals(Object o) {
		return o instanceof Brick && ((Brick) o).bits == this.bits;

	}

	private void generate(Brick bParent, long width) {
		Brick bLeft = new Brick();
		Brick bRight = new Brick();

		bLeft.bSym = new Brick();
		bRight.bSym = new Brick();
		bLeft.bSym.bits = BIT_WIDTH;
		bRight.bSym.bits = BIT_WIDTH;

		bLeft.width = 2;
		bRight.width = 3;
		bLeft.bSym.width = 2;
		bRight.bSym.width = 3;

		bParent.bLeft = bLeft;
		bParent.bRight = bRight;
		bParent.bLeft.bSym = bLeft.bSym;
		bParent.bRight.bSym = bRight.bSym;

		bLeft.totWidth = bLeft.width + bParent.totWidth;
		bRight.totWidth = bRight.width + bParent.totWidth;
		bLeft.bSym.totWidth = bLeft.bSym.width + bParent.bSym.totWidth;
		bRight.bSym.totWidth = bRight.bSym.width + bParent.bSym.totWidth;

		bLeft.bits = bLeft.bits << bLeft.totWidth;
		bLeft.bits = bLeft.bits | bParent.bits;
		bLeft.bSym.bits = bLeft.bSym.bits >> bLeft.bSym.totWidth;
		bLeft.bSym.bits = bLeft.bSym.bits | bParent.bSym.bits;

		bRight.bits = bRight.bits << bRight.totWidth;
		bRight.bits = bRight.bits | bParent.bits;
		bRight.bSym.bits = bRight.bSym.bits >> bRight.bSym.totWidth;
		bRight.bSym.bits = bRight.bSym.bits | bParent.bSym.bits;

		if (bLeft.totWidth < width) {
			generate(bLeft, width);
		} else if (bLeft.totWidth == width) {
			bLeft.bits = bParent.bits;
			// Nao sei o porque, mas subtraindo uma unidade tudo esta de acordo.
			bLeft.bSym.bits = bParent.bSym.bits - 1;
			bitLinesSym.add(bLeft);
		}

		if (bRight.totWidth < width) {
			generate(bRight, width);
		} else if (bRight.totWidth == width) {
			bRight.bits = bParent.bits;
			// Nao sei o porque, mas subtraindo uma unidade tudo esta de
			// acordo.
			bRight.bSym.bits = bParent.bSym.bits - 1;
			bitLinesSym.add(bRight);
		}
	}

	protected void generate(int width) {
		bitLines = new LinkedList<>();
		Brick b = new Brick();
		b.bits = 0;
		b.bSym = new Brick();

		BIT_WIDTH = (long) Math.pow(2, width);
		generate(b, width);

		for (Brick bc : bitLinesSym) {
			if (bc.bSym != null && !bitLines.contains(bc.bSym)) {
				bitLines.add(bc);
			}
		}
	}

	@Override
	public int hashCode() {
		return (int) bits;
	}

	public boolean hasSymmetric() {
		return bSym != null && bSym.bits != this.bits;
	}

	public String linesToString() {
		StringBuilder s = new StringBuilder();
		for (Brick b : bitLines) {
			s.append(Long.toBinaryString(b.bits)).append("\n");
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
		boolean hasSymmetric = false;
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

		Line(long bits, int idx, boolean hasSymmetric) {
			this.bits = bits;
			this.idx = idx;
			this.hasSymmetric = hasSymmetric;
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
	long mod;

	private long tableAfter[] = null;

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
					tableAfter[c.idx] += l.hasSymmetric ? 2 * tableBefore[l.idx] : tableBefore[l.idx];
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
		Brick brick = null;
		Brick adjBit = null;
		for (int i = 0; i < bitLines.size(); i++) {
			brick = bitLines.get(i);
			line = new Line(brick.bits, i, brick.hasSymmetric());
			adjacentLines.addChild(line);

			for (int j = 0; j < bitLinesSym.size(); j++) {
				adjBit = bitLinesSym.get(j);
				if ((brick.bits & adjBit.bits) == 0) {
					// Nao sei o que fazer nesse ponto
					line.addChild(new Line(brick.bits, i, brick.hasSymmetric()));
				}
			}
		}

		tableAfter = new long[bitLines.size()];
		for (int i = 0; i < tableAfter.length; i++) {
			tableAfter[i] = 1;
		}
	}

	private void generateBitLines() {
		bitLinesSym = new LinkedList<>();
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
		for (Brick l : bitLines) {
			s.append(l.bits).append("\n");
		}
		System.out.println(s.toString());
	}

}

public class WallCrackFreeTableSymmetrical {

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
			e.printStackTrace();
			return;
		}
	}
}
