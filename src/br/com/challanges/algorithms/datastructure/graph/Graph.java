package br.com.challanges.algorithms.datastructure.graph;

import java.util.ArrayList;
import java.util.List;

public interface Graph {
    void addEdge(int i, int j);

    boolean hasEdge(int i, int j);

    boolean removeEdge(int i, int j);

    List<Integer> outEdges(int i);

    List<Integer> inEdges(int i);

    int size();

}
