package br.com.challanges.algorithms.datastructure.graph;

import java.util.ArrayList;
import java.util.List;

public class AdjacencyListGraph implements Graph {
    private List<Integer>[] adj;
    int n;

    public AdjacencyListGraph(int capacity) {
        adj = new ArrayList[capacity];
        for (int i = 0; i < capacity; i++) {
            adj[i] = new ArrayList<>();
        }
    }

    public void addEdge(int i, int j) {
        adj[i].add(j);
        n++;
    }

    public boolean hasEdge(int i, int j) {
        return adj[i].contains(j);
    }

    public boolean removeEdge(int i, int j) {
        for (int k = 0; k < adj[i].size(); k++) {
            if (adj[i].get(k).intValue() == j) {
                adj[i].remove(k);
                n--;
                return true;
            }
        }
        return false;
    }

    public List<Integer> outEdges(int i) {
        return adj[i];
    }

    public List<Integer> inEdges(int i) {
        var edges = new ArrayList<Integer>();
        for (int j = 0; j < n; j++) {
            for (int k = 0; k < adj[j].size(); k++) {
                if (adj[j].get(k).intValue() == i) {
                    edges.add(j);
                    break;
                }
            }
        }
        return edges;
    }

    @Override
    public int size() {
        return n;
    }

}
