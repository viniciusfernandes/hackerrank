package br.com.challanges.olipmpiadasinformatica;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

public class RoboEmMarteComGrafos {
	private static void teste() {
		final Point from = new Point(2, 5);
		final Point to = new Point(1, 0);
		final Area a1 = new Area(1, 3, 2, 4);
		final Area a2 = new Area(3, 1, 4, 3);

		if (from.distanceOf(from) != 0) {
			System.err.println("distance FROM to FROM must be 0");
		}

		if (to.distanceOf(from) != 6) {
			System.err.println("distance TO to FROM must be 6");
		}

		if (from.distanceOf(to) != 6) {
			System.err.println("distance FROM to TO must be 6");
		}

		if (from.distanceOf(a1) != 1) {
			System.err.println("distance from to a1 must be 1");
		}

		if (from.distanceOf(a2) != 3) {
			System.err.println("distance from to a2 must be 3");
		}

		if (to.distanceOf(a1) != 3) {
			System.err.println("distance TO to a1 must be 3");
		}

		if (to.distanceOf(a2) != 3) {
			System.err.println("distance TO to a2 must be 3");
		}

		if (a1.distanceOf(a1) != 0) {
			System.err.println("distance a1 to a1 must be 0");
		}

		if (a1.distanceOf(a2) != 1) {
			System.err.println("distance a1 to a2 must be 1");
		}

		if (a2.distanceOf(a1) != 1) {
			System.err.println("distance a2 to a1 must be 1");
		}

	}

	static String[] points;
	static int x1;
	static int y1;
	static int x2;
	static int y2;

	static int limit = 1000000;
	static Graph graph;

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

		final int n = Integer.parseInt(in.nextLine());
		if (n < 0 || n > 1000) {
			in.close();
			return;
		}
		final boolean areasOk = buildGraph(n, in, from, to);
		if (!areasOk) {
			in.close();
			return;
		}

		in.close();

		System.out.println(graph.searchLowerCost());
	}

	private static boolean buildGraph(int totalAreas, Scanner in, Point from, Point to) {

		graph = new Graph(totalAreas, from, to);

		if (totalAreas <= 0) {
			return true;
		}

		Area newArea = null;
		for (int i = 1; i <= totalAreas; i++) {
			points = in.nextLine().split(" ");

			x1 = Integer.parseInt(points[0]);
			y1 = Integer.parseInt(points[1]);
			x2 = Integer.parseInt(points[2]);
			y2 = Integer.parseInt(points[3]);

			if (isInvalidArea(x1, y1, x2, y2)) {
				return false;
			}
			newArea = new Area(x1, y1, x2, y2);
			graph.add(newArea);
		}
		return true;
	}

	private static boolean isInvalidPoint(int x, int y) {
		return x < 0 || x > limit || y < 0 || y > limit;
	}

	private static boolean isInvalidArea(int x1, int y1, int x2, int y2) {
		return isInvalidPoint(x1, y1) || isInvalidPoint(x2, y2) || x1 > x2 || y1 > y2;
	}

}

class Graph {
	private final List<Node> nodes = new ArrayList<>(10000);
	private final TreeSet<Node> estimatedCostNodes = new TreeSet<>();

	final Node origin;
	final Node destination;

	private Node node = null;
	private int cost = -1;

	private final int[][] costMatrix;
	private final int size;
	private final int firstNodeIndex;
	private final int lastNodeIndex;

	private int countAreas;

	public Graph(int totalAreas, Point from, Point to) {
		size = totalAreas + 2;
		firstNodeIndex = 0;
		lastNodeIndex = size - 1;

		costMatrix = new int[size][size];

		origin = new Node(firstNodeIndex, from);
		// SETTING NODE "TO" AS THE LAST NODE IN THE ORDER INDEXES
		destination = new Node(lastNodeIndex, to);

		// TWO NODES HAVE ADDED IN THE GRAPH
		countAreas = 1;

		cost = from.distanceOf(to);

		costMatrix[origin.id][destination.id] = cost;
		costMatrix[destination.id][origin.id] = cost;

		nodes.add(origin);
		nodes.add(destination);

		origin.linking(destination);
	}

	public Node getLowerCostNode() {
		if (estimatedCostNodes.isEmpty()) {
			return null;
		}

		Node lowerCost = null;
		while ((lowerCost = estimatedCostNodes.pollFirst()) != null) {
			if (!lowerCost.estimativeClosed) {
				lowerCost.estimativeClosed = true;
				return lowerCost;
			}
		}

		return null;
	}

	public void add(Area a) {
		node = new Node(countAreas++, a);
		for (final Node n : nodes) {
			if (n.point != null) {
				cost = n.point.distanceOf(a);
			}
			else if (n.area != null) {
				cost = n.area.distanceOf(a);
			}

			costMatrix[node.id][n.id] = cost;
			costMatrix[n.id][node.id] = cost;

			node.linking(n);
		}

		node.cost = Integer.MAX_VALUE;
		nodes.add(node);
	}

	public int searchLowerCost() {
		origin.cost = 0;
		origin.estimativeClosed = true;
		destination.cost = Integer.MAX_VALUE;

		searchLowerCost(origin);
		return destination.cost;
	}

	private void searchLowerCost(Node node) {
		if (node == null) {
			return;
		}

		int lowerCost = -1;

		for (final Node adjacent : node.adjacents) {
			if (adjacent.estimativeClosed) {
				continue;
			}
			lowerCost = node.cost + costMatrix[node.id][adjacent.id];
			if (lowerCost < adjacent.cost) {
				adjacent.cost = lowerCost;
			}
			estimatedCostNodes.add(adjacent);
		}

		searchLowerCost(getLowerCostNode());

	}

	class Node implements Comparable<Node> {
		public final int id;
		public Area area;
		public Point point;
		public List<Node> adjacents = new ArrayList<>();
		public List<Node> notVisited = new ArrayList<>();

		public int cost;
		boolean estimativeClosed;

		public Node(int id, Area area) {
			this.area = area;
			this.id = id;
		}

		public Node(int id, Point point) {
			this.point = point;
			this.id = id;
		}

		public void linking(Node adjacent) {
			adjacents.add(adjacent);
			notVisited.add(adjacent);

			adjacent.adjacents.add(this);
			adjacent.notVisited.add(this);
		}

		@Override
		public boolean equals(Object o) {
			return id == ((Node) o).id;
		}

		@Override
		public int hashCode() {
			return id;
		}

		@Override
		public String toString() {
			return "Node@" + id;
		}

		@Override
		public int compareTo(Node other) {
			if (cost < other.cost) {
				return -1;
			}
			if (cost > other.cost) {
				return 1;
			}
			return 0;
		}
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

	public boolean isAbove(Area a) {
		return y1 > a.y2;
	}

	public boolean isDown(Area a) {
		return y2 < a.y1;
	}

	public boolean isOnTheRight(Area a) {
		return x1 > a.x2;
	}

	public boolean isOnTheLeft(Area a) {
		return x2 < a.x1;
	}

	public boolean isOverlaped(Area a) {
		return x1 <= a.x2 && x2 >= a.x1 && y1 <= a.y2 && y2 >= a.y1;
	}

	public boolean contains(Point p) {
		return p.x >= x1 && p.x <= x2 && p.x >= y1 && p.x <= y2;
	}

	public int distanceOf(Area a) {
		if (isOverlaped(a)) {
			return 0;
		}

		int dx = 0;
		int dy = 0;
		if (isAbove(a)) {
			dy = y1 - a.y2;
		}
		else if (isDown(a)) {
			dy = a.y1 - y2;
		}

		if (isOnTheRight(a)) {
			dx = x1 - a.x2;
		}
		else if (isOnTheLeft(a)) {
			dx = a.x1 - x2;
		}

		return dx + dy;
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

	@Override
	public String toString() {
		return "(" + x1 + ", " + y1 + ", " + x2 + ", " + y2 + ")";
	}

}

class Point {
	public final int x;
	public final int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;

	}

	public boolean isAbove(Area area) {
		return y > area.y2;
	}

	public boolean isDown(Area area) {
		return y < area.y1;
	}

	public boolean isOnTheRight(Area area) {
		return x > area.x2;
	}

	public boolean isOnTheLeft(Area area) {
		return x < area.x1;
	}

	public boolean isInside(Area area) {
		return x >= area.x1 && x <= area.x2 && y >= area.y1 && y <= area.y2;
	}

	public int distanceOf(Point p) {
		int dx = x - p.x;
		if (dx < 0) {
			dx *= -1;
		}
		int dy = y - p.y;
		if (dy < 0) {
			dy *= -1;
		}
		return dx + dy;
	}

	public int distanceOf(Area a) {

		if (isInside(a)) {
			return 0;
		}

		int dx = 0;
		int dy = 0;
		if (isAbove(a)) {
			dy = y - a.y2;
		}
		else if (isDown(a)) {
			dy = a.y1 - y;
		}

		if (isOnTheRight(a)) {
			dx = x - a.x2;
		}
		else if (isOnTheLeft(a)) {
			dx = a.x1 - x;
		}

		return dx + dy;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

}