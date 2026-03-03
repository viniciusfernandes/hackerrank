package br.com.challanges.leetcode.geometry;

import br.com.challanges.algorithms.datastructure.utils.Assertions;

import java.util.Stack;

public class CountLatticePointsInsideCircle {
    private record Lattice(int x, int y) {

        public static Lattice of(int x, int y) {
            return new Lattice(x, y);
        }

        public boolean isInside(Lattice l, int radius) {
            int dx = this.x - l.x;
            int dy = this.y - l.y;
            return dx * dx + dy * dy <= radius * radius;
        }

        public Lattice up() {
            return new Lattice(x, y + 1);
        }

        public Lattice down() {
            return new Lattice(x, y - 1);

        }

        public Lattice left() {
            return new Lattice(x - 1, y);

        }

        public Lattice right() {
            return new Lattice(x + 1, y);

        }

        @Override
        public String toString() {
            return "{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    public static int countLatticePoints(int[][] circles) {
        final int MAX_COORD_VALUE = 100;
        final int SIZE = 3 * MAX_COORD_VALUE + 10;
        boolean[][] visited = new boolean[SIZE][SIZE];
        boolean[][] points = new boolean[SIZE][SIZE];

        int count = 0;
        var stack = new Stack<Lattice>();
        for (int[] c : circles) {
            int r = c[2];
            var center = Lattice.of(c[0], c[1]);
            stack.push(center);
            while (!stack.isEmpty()) {
                var l = stack.pop();
                if (visited[MAX_COORD_VALUE + l.x][MAX_COORD_VALUE + l.y] || !center.isInside(l, r)) {
                    continue;
                }

                stack.push(l.up());
                stack.push(l.down());
                stack.push(l.left());
                stack.push(l.right());

                if (!points[MAX_COORD_VALUE + l.x][MAX_COORD_VALUE + l.y]) {
                    count++;
                }
                visited[MAX_COORD_VALUE + l.x][MAX_COORD_VALUE + l.y] = true;
                points[MAX_COORD_VALUE + l.x][MAX_COORD_VALUE + l.y] = true;
            }
            visited = new boolean[SIZE][SIZE];
        }
        return count;
    }

    public static void main(String[] args) {
        testUnitCircle();
        testUnitNegativeCenterCircle();
        testRadius2Circle();
        testOverlapUnitCircles();
        testNotOverlapUnitCircles();
        testOverlapIdenticalUnitCircles();
        testZeroRadiusCircle();
        testZeroRadiusCircles();
        testOverlapZeroRadiusCircles();
        testOverlapGeneralCircles();
        testGeneral();
    }

    private static void testUnitCircle() {
        int[][] circles = {{1, 1, 1}};
        Assertions.assertEquals(5, countLatticePoints(circles));
    }

    private static void testUnitNegativeCenterCircle() {
        int[][] circles = {{-1, -1, 1}};
        Assertions.assertEquals(5, countLatticePoints(circles));
    }

    private static void testRadius2Circle() {
        int[][] circles = {{2, 2, 2}};
        Assertions.assertEquals(13, countLatticePoints(circles));
    }

    private static void testOverlapUnitCircles() {
        int[][] circles = {{1, 1, 1}, {2, 2, 1}};
        Assertions.assertEquals(8, countLatticePoints(circles));
    }

    private static void testNotOverlapUnitCircles() {
        int[][] circles = {{1, 1, 1}, {20, 20, 1}};
        Assertions.assertEquals(10, countLatticePoints(circles));
    }

    private static void testOverlapIdenticalUnitCircles() {
        int[][] circles = {{1, 1, 1}, {1, 1, 1}};
        Assertions.assertEquals(5, countLatticePoints(circles));
    }

    private static void testZeroRadiusCircle() {
        int[][] circles = {{1, 1, 0}};
        Assertions.assertEquals(1, countLatticePoints(circles));
    }

    private static void testZeroRadiusCircles() {
        int[][] circles = {{1, 1, 0}, {1, 2, 0}};
        Assertions.assertEquals(2, countLatticePoints(circles));
    }

    private static void testOverlapZeroRadiusCircles() {
        int[][] circles = {{1, 1, 0}, {1, 1, 0}};
        Assertions.assertEquals(1, countLatticePoints(circles));
    }

    private static void testOverlapGeneralCircles() {
        int[][] circles = {{3, 4, 1}};
        Assertions.assertEquals(5, countLatticePoints(circles));

        circles = new int[][]{{2, 2, 2}, {3, 4, 1}};
        Assertions.assertEquals(16, countLatticePoints(circles));
    }

    private static void testGeneral() {
        int[][] circles = {{8, 9, 6}, {9, 8, 4}, {4, 1, 1}, {8, 5, 1}, {7, 1, 1}, {6, 7, 5}, {7, 1, 1}, {7, 1, 1}, {5, 5, 3}};
        Assertions.assertEquals(141, countLatticePoints(circles));
    }

}
