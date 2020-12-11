
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RoboEmMarte {

	private static List<Area> areas;
	static String[] points;
	static int x1;
	static int y1;
	static int x2;
	static int y2;

	static int limit = 1000000;

	public static void main(String[] args) {
		final Scanner in = new Scanner(System.in);

		points = in.nextLine().split(" ");
		x1 = Integer.parseInt(points[0]);
		y1 = Integer.parseInt(points[1]);
		x2 = Integer.parseInt(points[2]);
		y2 = Integer.parseInt(points[3]);

		if (isInvalidPoint(x1, y1) || isInvalidPoint(x2, y2)) {
			in.close();
			return;
		}

		final Point from = new Point(x1, y1);
		final Point to = new Point(x2, y2);

		final int n = in.nextInt();
		if (n < 0 || n > 1000) {
			in.close();
			return;
		}
		final boolean areasOk = buildAreas(n, in);
		if (!areasOk) {
			in.close();
			return;
		}

		in.close();

		final int distance = calculateShortestDistance(from, to);
		System.out.println(distance);
	}

	private static boolean buildAreas(int totalAreas, Scanner in) {
		Area newArea = null;
		for (int i = 2; i <= totalAreas; i++) {
			points = in.nextLine().split(" ");
			x1 = Integer.parseInt(points[0]);
			y1 = Integer.parseInt(points[1]);
			x2 = Integer.parseInt(points[2]);
			y2 = Integer.parseInt(points[3]);

			if (isInvalidArea(x1, y1, x2, y2)) {
				return false;
			}
			newArea = new Area(x1, y1, x2, y2);

			linkAreas(newArea);
			areas.add(newArea);
		}
		return true;
	}

	private static boolean isInvalidPoint(int x, int y) {
		return x < 0 || x > limit || y < 0 || y > limit;
	}

	private static boolean isInvalidArea(int x1, int y1, int x2, int y2) {
		return isInvalidPoint(x1, y1) || isInvalidPoint(x2, y2) || x1 > x2 || y1 > y2;
	}

	private static int calculateShortestDistance(Point from, Point to) {
		final Area[] areas = searchFromToAreas(from, to);
		final Area fromArea = areas[0];
		final Area toArea = areas[1];
		if (fromArea.isOverlaped(toArea)) {
			return 0;
		}
		return -1;
	}

	private static void linkAreas(Area newArea) {
		for (final Area area : areas) {
			area.linkAreas(newArea);
		}
	}

	private static Area[] searchFromToAreas(Point from, Point to) {
		final Area[] fromToAreas = new Area[2];

		for (final Area area : areas) {
			if (area.contains(from)) {
				fromToAreas[0] = area;
				if (fromToAreas[1] != null) {
					break;
				}
			}

			if (area.contains(to)) {
				fromToAreas[1] = area;
				if (fromToAreas[0] != null) {
					break;
				}
			}
		}
		return fromToAreas;
	}
}

class Area {
	public int x1, y1, x2, y2;

	public List<Area> aboveAreas = new ArrayList<>();
	public List<Area> downAreas = new ArrayList<>();
	public List<Area> onRightAreas = new ArrayList<>();
	public List<Area> onLeftAreas = new ArrayList<>();

	public Area(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	public boolean isAbove(Area other) {
		return other.y1 >= y2;
	}

	public boolean isDown(Area other) {
		return other.y2 <= y1;
	}

	public boolean isOnTheRight(Area other) {
		return other.x1 >= x2;
	}

	public boolean isOnTheLeft(Area other) {
		return other.x2 <= x1;
	}

	public boolean isOverlaped(Area other) {
		return other.x1 <= x2 && other.y1 <= y2;
	}

	public boolean contains(Point p) {
		return p.x >= x1 && p.x <= x2 && p.x >= y1 && p.x <= y2;
	}

	public void linkAreas(Area other) {
		if (isAbove(other)) {
			aboveAreas.add(other);
			other.downAreas.add(this);
		}
		else if (isDown(other)) {
			downAreas.add(other);
			other.aboveAreas.add(this);
		}
		else if (isOnTheRight(other)) {
			onRightAreas.add(other);
			other.onLeftAreas.add(this);
		}
		else if (isOnTheLeft(other)) {
			onLeftAreas.add(other);
			other.onRightAreas.add(this);
		}
	}

}

class Point {
	public int x, y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
