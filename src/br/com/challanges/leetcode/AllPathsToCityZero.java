package br.com.challanges.leetcode;

import br.com.challanges.algorithms.datastructure.utils.Assertions;

import java.util.*;

class AllPathsToCityZero {
    public int minReorder(int n, int[][] connections) {
        List<List<int[]>> tree = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            tree.add(new ArrayList<>());
        }
        for (int[] path : connections) {
            // element=1 in array indicates flit and will add a unit to the total
            tree.get(path[0]).add(new int[]{path[1], 1});
            tree.get(path[1]).add(new int[]{path[0], 0});
        }

        Queue<Integer> queue = new LinkedList<>();
        queue.add(0);
        boolean[] visited = new boolean[n];
        visited[0] = true;
        int count = 0;
        while (!queue.isEmpty()) {
            int c = queue.poll();
            List<int[]> paths = tree.get(c);
            for (int[] path : paths) {
                if (visited[path[0]]) {
                    continue;
                }
                count += path[1];
                queue.add(path[0]);
            }
            visited[c] = true;
        }
        return count;
    }

    public static void main(String[] args) {
        Assertions.assertEquals(0, new AllPathsToCityZero().minReorder(3, new int[][]{{1, 0}, {2, 0}}));
        Assertions.assertEquals(0, new AllPathsToCityZero().minReorder(3, new int[][]{{1, 2}, {2, 0}}));
        Assertions.assertEquals(1, new AllPathsToCityZero().minReorder(3, new int[][]{{1, 2}, {0, 2}}));
        Assertions.assertEquals(3, new AllPathsToCityZero().minReorder(6, new int[][]{
                {0, 1},
                {1, 3},
                {2, 3},
                {4, 0},
                {4, 5}
        }));

    }
}