package br.com.challanges.algorithms.graph;

public class PositiveUndirectedGraph extends UndirectedGraph {
    public void addEdge(String from, String to, int weight) {
        if (weight <= 0) {
            return;
        }
        super.addEdge(from, to, weight);
    }
}
