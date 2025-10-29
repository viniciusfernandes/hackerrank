package br.com.challanges.algorithms.graph;

import java.util.Objects;

public class Node implements Comparable<Node> {
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

    @Override
    public int compareTo(Node o) {
        return Integer.compare(this.weight, o.weight);
    }
}
