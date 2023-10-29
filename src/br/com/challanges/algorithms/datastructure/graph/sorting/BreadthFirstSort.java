package br.com.challanges.algorithms.datastructure.graph.sorting;

import br.com.challanges.algorithms.datastructure.Queue;
import br.com.challanges.algorithms.datastructure.graph.Graph;

public class BreadthFirstSort {
    private BreadthFirstSort() {
    }

    public static void sort(Graph graph) {
        sort(graph, 0);
    }

    public static void sort(Graph graph, int r) {
        var visited = new boolean[graph.size()];
        var q = new Queue<Integer>(graph.size());
        q.add(r);
        visited[r] = true;
        while (!q.isEmpty()) {
            int j = q.remove();
            for (var e : graph.outEdges(j)) {
                if (!visited[e]) {
                    q.add(e);
                    visited[e] = true;
                }
            }
        }
    }
}
