package br.com.challanges.algorithms.graph;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MyHnswGraph {
    private final int M;
    private final double levelMultiplier;
    private final int efConstruction;
    private final int dimension;
    private int maxLevel = -1;
    private final Random random;
    private final AtomicInteger nextId = new AtomicInteger();
    private Node entryPoint;
    private final Map<Integer, Node> nodes = new HashMap<>();

    private static final class Node {
        final int id;
        final double[] vector;
        final int maxLevel;
        final List<Set<Node>> neighbors;

        public Node(int id, double[] vector, int maxLevel) {
            this.id = id;
            this.vector = vector;
            this.maxLevel = maxLevel;
            neighbors = new ArrayList<>();
            for (int i = 0; i <= maxLevel; i++) {
                neighbors.add(new HashSet<>());
            }
        }

        Set<Node> getNeighbors(int level) {
            return neighbors.get(level);
        }

        void connect(Node node, int level) {
            getNeighbors(level).add(node);
            node.getNeighbors(level).add(this);
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return Objects.deepEquals(vector, node.vector);
        }

        @Override
        public String toString() {
            return "Node{" +
                    "id=" + id +
                    ", vector=" + Arrays.toString(vector) +
                    ", maxLevel=" + maxLevel +
                    ", neighbors=" + neighbors +
                    '}';
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(vector);
        }

    }

    private record Candidate(Node node,
                             double distance) {
    }

    private record Result(int id, double distance) {
    }


    public MyHnswGraph(int M, int efConstruction, int seed, int dimension) {
        this.M = M;
        this.levelMultiplier = 1 / Math.log(M);
        this.efConstruction = efConstruction;
        this.dimension = dimension;
        random = new Random(seed);
    }

    public static void main(String[] args) {
        MyHnswGraph index = new MyHnswGraph(3, 10, 47, 2);
        index.add(new double[]{1d, 1d});
        index.add(new double[]{2d, 2d});
        index.add(new double[]{3d, 3d});
        index.add(new double[]{1.1d, 1.1d});
        index.add(new double[]{1.2d, 1.1d});
        index.add(new double[]{1.1d, 1.2d});
        index.add(new double[]{1.2d, 1.2d});

        double[] query = {0.82, 0.18};
        List<Result> results = index.find(query, 2, 3);
        System.out.println("Index size: " + index.size());
        System.out.println("Graph max level: " + index.maxLevel());

        System.out.println("Nearest results:");
        results.forEach(System.out::println);
    }

    public int size() {
        return nodes.size();
    }

    public int maxLevel() {
        return maxLevel;
    }

    public List<Result> find(double[] query, int k, int efSearch) {
        if (entryPoint == null || k < 1 || efSearch < 1) {
            return List.of();
        }
        if (k > efSearch) {
            efSearch = k;
        }
        Node currNode = entryPoint;
        for (int level = maxLevel; level >= 0; level--) {
            currNode = findCloserEntry(query, currNode, level);
        }

        List<Node> closest = findClosestNeighbors(query, currNode, 0, efSearch);
        return closest.stream()
                .limit(k)
                .map(n -> new Result(n.id, distance(query, n.vector)))
                .toList();
    }

    public int add(double[] vector) {
        validateVector(vector);
        int id = nextId.getAndIncrement();
        int nodeLevel = randomLevel();
        Node node = new Node(id, vector, nodeLevel);
        nodes.put(id, node);
        if (entryPoint == null) {
            entryPoint = node;
            maxLevel = nodeLevel;
            return id;
        }
        Node currEntry = entryPoint;
        for (int level = maxLevel; level > nodeLevel; level--) {
            currEntry = findCloserEntry(vector, currEntry, level);
        }

        for (int level = Math.min(nodeLevel, maxLevel); level >= 0; level--) {
            List<Node> closestNeighbors = findClosestNeighbors(
                    vector,
                    currEntry,
                    level,
                    efConstruction);
            int maxConnections = maxConnections(level);
            List<Node> selectedNeighbors = heuristicNeighbors(vector, closestNeighbors, maxConnections);

            for (Node neighbor : selectedNeighbors) {
                node.connect(neighbor, level);
                pruneNeighbors(neighbor, level);
            }
            // NAO ENTENDI ESSE PONTO
            if (!closestNeighbors.isEmpty()) {
                currEntry = closestNeighbors.get(0);
            }
        }

        if (nodeLevel > maxLevel) {
            entryPoint = node;
            maxLevel = nodeLevel;
        }
        return id;
    }

    private void pruneNeighbors(Node node, int level) {
        Set<Node> neighbors = node.getNeighbors(level);
        int maxConnections = maxConnections(level);
        if (neighbors.size() <= maxConnections) {
            return;
        }
        List<Node> selected = heuristicNeighbors(node.vector, neighbors, maxConnections);
        neighbors.clear();
        neighbors.addAll(selected);
    }

    private List<Node> heuristicNeighbors(double[] vector, Collection<Node> candidates, int maxConnections) {
        List<Node> sortedCandidates = candidates.stream()
                .sorted(Comparator.comparingDouble(node -> distance(vector, node.vector)))
                .toList();
        final List<Node> selected = new ArrayList<>();
        Set<Node> visited = new HashSet<>();
        for (Node candidate : sortedCandidates) {
            if (!visited.add(candidate)) {
                continue;
            }
            double d = distance(vector, candidate.vector);
            boolean diverse = true;
            for (Node s : selected) {
                if (distance(s.vector, candidate.vector) < d) {
                    diverse = false;
                    break;
                }
            }
            if (diverse) {
                selected.add(candidate);
                if (selected.size() == maxConnections) {
                    return selected;
                }
            }
        }
        return selected;
    }

    private int maxConnections(int level) {
        // Common convention: up to 2*M at Layer 0 and M above it.
        return level == 0 ? 2 * M : M;
    }

    private Node findCloserEntry(double[] vector, Node node, int level) {
        Node closer = node;
        double shorterDist = distance(vector, closer.vector);
        boolean foundCloser;
        do {
            foundCloser = false;
            for (Node neighbor : closer.getNeighbors(level)) {
                double neighborDist = distance(vector, neighbor.vector);
                if (neighborDist < shorterDist) {
                    closer = neighbor;
                    shorterDist = neighborDist;
                    foundCloser = true;
                }
            }
        } while (foundCloser);

        return closer;
    }

    private List<Node> findClosestNeighbors(double[] vector, Node node, int level, int ef) {
        Set<Node> visited = new HashSet<>();
        Queue<Candidate> candidates =
                new PriorityQueue<>(
                        Comparator.comparingDouble(Candidate::distance));

        Queue<Candidate> best = new PriorityQueue<>(
                Comparator.comparingDouble(
                        Candidate::distance).reversed());
        Candidate candidate = new Candidate(node, distance(vector, node.vector));

        visited.add(node);
        candidates.add(candidate);
        best.add(candidate);

        while (!candidates.isEmpty()) {
            Candidate closest = candidates.poll();
            Candidate worst = best.peek();

            if (best.size() >= ef && closest.distance > worst.distance) {
                break;
            }

            for (Node neighbor : closest.node.getNeighbors(level)) {
                if (!visited.add(neighbor)) {
                    continue;
                }
                double d = distance(vector, neighbor.vector);
                worst = best.peek();
                if (best.size() < ef || d < worst.distance) {
                    Candidate c = new Candidate(neighbor, d);
                    candidates.add(c);
                    best.add(c);
                    if (best.size() > ef) {
                        best.poll();
                    }
                }
            }
        }
        return best.stream()
                .sorted(Comparator.comparing(Candidate::distance))
                .map(Candidate::node)
                .toList();

    }

    private void validateVector(double[] vector) {
        if (vector.length != dimension) {
            throw new IllegalArgumentException(
                    "Expected dimension " + dimension
                            + ", got " + vector.length);
        }
    }

    private double distance(double[] a, double[] b) {
        double d = 0d;
        double diff;
        for (int i = 0; i < dimension; i++) {
            diff = a[i] - b[i];
            d += diff * diff;
        }
        return Math.sqrt(d);
    }

    private int randomLevel() {
        final double u = random.nextDouble();
        return (int) Math.floor(-Math.log(u) * levelMultiplier);
    }

}
