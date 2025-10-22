package br.com.challanges.algorithms.graph;

public class Node {
    final String id;
    final int weight;

    public Node(String id, int weight) {
        this.id = id;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id='" + id + '\'' +
                ", weight=" + weight +
                '}';
    }
}
