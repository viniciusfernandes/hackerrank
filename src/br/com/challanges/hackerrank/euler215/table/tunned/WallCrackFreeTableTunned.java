package br.com.challanges.hackerrank.euler215.table.tunned;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Brick {
	final List<Integer> bitLines = new ArrayList<>();
	int bits = 1;
	Brick bLeft;
	Brick bRight;
	int height = 0;
	int totWidth = 0;
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
		Brick b = new Brick();
		b.bits = 0;
		generate(b, width);
	}

	public String linesToString() {
		StringBuilder s = new StringBuilder();
		for (int l : bitLines) {
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
		int totChildren = 0;

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

		public void clear() {
			mainChild = next = previous = last = nextChild = null;
			totChildren = 0;
		}

		public Line getLine(Long bits) {
			return mapChild.get(bits);
		}

		public Line getMainChild(Long bits) {
			return mapChild.get(bits).mainChild;
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

	private long memoAfter[] = null;

	long mod;

	public Wall(int width, int height, long mod) throws Exception {
		this.width = width;
		this.height = height;
		this.mod = mod;
		if (height < 2 || height > 100 || width < 5 || width > 45 || mod < 1 || mod > Math.pow(2, 30)) {
			throw new Exception("Invalid parameters");
		}
	}

	private void copy(long[] arr1, long[] arr2) {
		for (int i = 0; i < arr2.length; i++) {
			arr2[i] = arr1[i];
			arr1[i] = 0;
		}
	}

	private long count() {
		long countWalls = 0;

		if (height == 1) {
			countWalls = memoAfter.length % mod;
		}
		Line c = null;
		Line l = null;
		long level = 2;
		long memoBefore[] = memoAfter;
		memoAfter = new long[memoAfter.length];

		while (true) {
			adjacentLines.reset();
			while ((l = adjacentLines.nextChild()) != null) {
				l.reset();
				while ((c = l.nextChild()) != null) {
					memoAfter[c.idx] += memoBefore[l.idx] % mod;
				}
			}
			if (level++ >= height) {
				break;
			}
			copy(memoAfter, memoBefore);
		}
		for (int i = 0; i < memoAfter.length; i++) {
			countWalls += memoAfter[i] % mod;
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

	private Line idxLine[] = new Line[1431655765];

	private void generateAdjacentLines() {
		Line line = null;
		Line subline = null;
		for (int bits : bitLines) {
			if ((line = idxLine[bits]) == null) {
				line = new Line(bits);
				idxLine[bits] = line;
			}
			adjacentLines.addChild(line);

			for (int subBit : bitLines) {
				if ((bits & subBit) == 0) {
					if ((subline = idxLine[subBit]) == null) {
						subline = new Line(bits);
						idxLine[subBit] = subline;
					}
					line.addChild(subline);
				}
			}
		}

		memoAfter = new long[adjacentLines.totChildren];
		for (int i = 0; i < memoAfter.length; i++) {
			memoAfter[i] = 1;
		}
	}

	private void generateBitLines() {
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
		for (int l : bitLines) {
			s.append(l).append("\n");
		}
		System.out.println(s.toString());
	}

}

public class WallCrackFreeTableTunned {

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
