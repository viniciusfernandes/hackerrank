package br.com.challanges.algorithms.graph;

import br.com.challanges.algorithms.datastructure.utils.Assertions;

import java.util.*;

public class DijkstraAlgorithm {
    private DijkstraAlgorithm() {
    }

    public static int distance(String from, String to, PositiveUndirectedGraph graph) {
        if (from.equals(to)) {
            return 0;
        }
        if (!graph.contains(from) || !graph.contains(to)) {
            return 0;
        }
        Map<String, Integer> dist = new HashMap<>();
        PriorityQueue<Node> queue = new PriorityQueue<>();
        Set<String> visited = new HashSet<>();
        queue.add(new Node(from, 0));
        dist.put(from, 0);
        int shortDist = Integer.MAX_VALUE;
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (visited.contains(current.id)) {
                continue;
            }
            for (Node edge : graph.getEdges(current.id)) {
                if (visited.contains(edge.id)) {
                    continue;
                }
                int newDist = dist.get(current.id) + edge.weight;
                if (newDist < dist.getOrDefault(edge.id, Integer.MAX_VALUE)) {
                    dist.put(edge.id, newDist);
                    queue.add(new Node(edge.id, newDist));
                    if (edge.id.equals(to) && newDist < shortDist) {
                        shortDist = newDist;
                    }
                }
            }
            visited.add(current.id);
        }
        return shortDist;
    }


    public static void main(String[] args) {
        testCase1();
        testCase2();
        testCase3();
        testCase4();
        testCase5();
    }

    private static void testCase1() {
        var graph = new PositiveUndirectedGraph();
        graph.addEdge("a", "b", 1);
        graph.addEdge("a", "c", 2);
        graph.addEdge("c", "b", 1);
        graph.addEdge("b", "d", 3);
        graph.addEdge("c", "d", 5);

        int dist = distance("a", "d", graph);
        Assertions.assertEquals(4, dist);
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

        int dist = distance("d", "f", graph);
        Assertions.assertEquals(10, dist);
    }

    private static void testCase3() {
        var graph = new PositiveUndirectedGraph();
        graph.addEdge("a", "b", 1);
        graph.addEdge("b", "a", 2);


        int dist = distance("a", "a", graph);
        Assertions.assertEquals(0, dist);
    }

    private static void testCase4() {
        var graph = new PositiveUndirectedGraph();
        graph.addEdge("a", "b", 1);
        graph.addEdge("b", "d", 10);
        graph.addEdge("c", "d", 3);


        int dist = distance("a", "d", graph);
        Assertions.assertEquals(11, dist);
    }

    private static void testCase5() {
        var graph = new PositiveUndirectedGraph();
        graph.addEdge("a", "b", 10);
        graph.addEdge("b", "d", 1);
        graph.addEdge("a", "c", 2);
        graph.addEdge("c", "b", 2);
        graph.addEdge("c", "d", 4);

        int dist = distance("a", "d", graph);
        Assertions.assertEquals(5, dist);
    }
}
