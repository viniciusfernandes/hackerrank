package br.com.challanges.algorithms.graph;

import br.com.challanges.algorithms.datastructure.utils.Assertions;

import java.util.*;

public class DijkstraAlgorithm {
    private DijkstraAlgorithm() {
    }

    public static Path calculatePath(String from, String to, PositiveUndirectedGraph graph) {
        if (from.equals(to)) {
            return new Path(Collections.singletonList(from), 0);
        }
        if (!graph.contains(from) || !graph.contains(to)) {
            return new Path();
        }
        List<Node> edges = graph.getEdges(from);
        Map<String, Integer> dist = new HashMap<>();
        List<String> paths = new ArrayList<>();

        int totalWeight = 0;
        int weight = Integer.MAX_VALUE;
        Node curr = null;
        dist.put(from, 0);
        do {
            for (Node node : edges) {
                if (totalWeight + node.weight < dist.getOrDefault(node.id, Integer.MAX_VALUE)) {
                    dist.put(node.id, totalWeight + node.weight);
                    if (node.weight < weight) {
                        weight = node.weight;
                        curr = node;
                    }
                }
            }
            paths.add(from);
            totalWeight += weight;
            from = curr.id;
            edges = graph.getEdges(from);
            weight = Integer.MAX_VALUE;
        } while (!from.equals(to));
        paths.add(to);
        return new Path(paths, totalWeight);
    }

    public static void main(String[] args) {
        testCase1();
        testCase2();
        testCase3();
    }

    private static void testCase1() {
        var graph = new PositiveUndirectedGraph();
        graph.addEdge("a", "b", 1);
        graph.addEdge("a", "c", 2);
        graph.addEdge("c", "b", 1);
        graph.addEdge("b", "d", 3);
        graph.addEdge("c", "d", 5);

        Path path = calculatePath("a", "d", graph);
        Assertions.assertEquals(4, path.weight);
        Assertions.assertEquals(List.of("a", "b", "d"), path.path);
    }

    private static void testCase2() {
        var graph = new PositiveUndirectedGraph();
        graph.addEdge("a", "d", 4);
        graph.addEdge("a", "c", 3);
        graph.addEdge("a", "e", 4);
        graph.addEdge("d", "e", 2);
        graph.addEdge("e", "c", 4);
        graph.addEdge("c", "b", 2);
        graph.addEdge("c", "g", 5);
        graph.addEdge("c", "f", 5);
        graph.addEdge("b", "f", 2);
        graph.addEdge("f", "g", 5);

        Path path = calculatePath("d", "f", graph);
        Assertions.assertEquals(10, path.weight);
        Assertions.assertEquals(List.of("d", "e", "c", "b", "f"), path.path);
    }

    private static void testCase3() {
        var graph = new PositiveUndirectedGraph();
        graph.addEdge("a", "b", 1);
        graph.addEdge("b", "a", 2);


        Path path = calculatePath("a", "a", graph);
        Assertions.assertEquals(0, path.weight);
        Assertions.assertEquals(List.of("a"), path.path);
    }
}
