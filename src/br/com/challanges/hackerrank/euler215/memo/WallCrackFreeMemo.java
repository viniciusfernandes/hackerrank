package br.com.challanges.hackerrank.euler215.memo;

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
	long countWalls;
	long mod;

	public Wall(int width, int height, long mod) throws Exception {
		this.width = width;
		this.height = height;
		this.mod = mod;
		if (height < 2 || height > 100 || width < 5 || width > 45 || mod < 1 || mod > Math.pow(2, 30)) {
			// throw new Exception("Invalid parameters");
		}
	}

	private void count() {
		if (height == 1) {
			countWalls = bitLines.size() % mod;
			return;
		}
		Line line = null;
		if (height == 2) {
			while ((line = adjacentLines.nextChild()) != null) {
				countWalls += line.totChildren % mod;
			}
			return;
		}
		count(adjacentLines.mainChild, 1, height - 1);
	}

	private void count(Line line, long level, final long lastButOne) {
		if (line == null) {
			return;
		}
		if (level == lastButOne) {
			Line l = line;
			do {
				countWalls += adjacentLines.getLine(line.bits).totChildren % mod;
			} while ((l = l.next) != null);
		}
		do {
			count(line.mainChild, level + 1, lastButOne);
		} while ((line = line.next) != null);

	}

	public long countWalls() {
		Date ini = new Date();
		generateBitLines();
		generateAdjacentLines();

		System.out.println("Bit Lines=" + bitLines.size() + " seconds: " + (new Date().getTime() - ini.getTime())
				/ 1000);
		count();
		return countWalls;
	}

	private void generateAdjacentLines() {
		Line line = null;
		for (Long bitLine : bitLines) {
			line = new Line(bitLine);
			adjacentLines.addChild(line);

			for (Long subBit : bitLines) {
				if ((bitLine & subBit) == 0) {
					line.addChild(new Line(subBit));
				}
			}
		}
	}

	private void generateAdjacentLinesxxx() {
		Line line = null;
		Line adjLine = null;
		for (Long bitLine : bitLines) {
			if ((line = adjacentLines.getLine(bitLine)) == null) {
				line = new Line(bitLine);
			}
			adjacentLines.addChild(line);

			for (Long subBit : bitLines) {
				if ((bitLine & subBit) == 0) {
					if ((adjLine = adjacentLines.getLine(subBit)) == null) {
						adjLine = new Line(subBit);
					}
					line.addChild(adjLine);
				}
			}
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

public class WallCrackFreeMemo {

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
			wall.printAdjacentLines();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
}
