package br.com.challanges.algorithms.datastructure.graph.sorting;

import br.com.challanges.algorithms.datastructure.Stack;
import br.com.challanges.algorithms.datastructure.graph.Graph;

public class DepthFirstSort {
    private DepthFirstSort() {
    }

    public static void sort(Graph graph) {
        sort(graph, 0);
    }

    public static void sort(Graph graph, int r) {
        boolean[] visited = new boolean[graph.size()];
        var s = new Stack<Integer>(graph.size());
        s.push(r);
        while (!s.isEmpty()) {
            int j = s.pop();
            if (visited[j] == false) {
                visited[j] = true;
                for (var e : graph.outEdges(j)) {
                    s.push(e);
                }
            }
        }
    }
}
