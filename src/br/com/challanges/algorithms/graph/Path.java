package br.com.challanges.algorithms.graph;

import java.util.Collections;
import java.util.List;

public class Path {
    final List<String> path;
    final int weight;

    public Path(List<String> path, int weight) {
        this.path = path;
        this.weight = weight;
    }

    public Path() {
        this(Collections.emptyList(), 0);
    }

    @Override
    public String toString() {
        return "Path{" +
                "path=" + pathAsString() +
                ", weight=" + weight +
                '}';
    }

    private String pathAsString() {
        String p = "";
        int l = path.size();
        for (int i = 0; i < l; i++) {
            p += path.get(i);
            if (i < l - 1) {
                p += " -> ";
            }
        }
        return p;
    }
}
