package br.com.challanges.algorithms.graph;

import java.util.*;

public class UndirectedGraph {
    final Map<String, List<Node>> edges = new HashMap<>();

    public void addEdge(String from, String to, int weight) {
        edges.computeIfAbsent(from, n -> new ArrayList<>()).add(new Node(to, weight));
        edges.computeIfAbsent(to, n -> new ArrayList<>()).add(new Node(from, weight));
    }

    public int size() {
        return edges.size();
    }

    public List<Node> getEdges(String from) {
        return edges.getOrDefault(from, Collections.emptyList());
    }

    public boolean contains(String id) {
        return edges.containsKey(id);
    }
}
