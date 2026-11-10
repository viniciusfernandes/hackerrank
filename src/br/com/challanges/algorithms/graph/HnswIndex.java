package br.com.challanges.algorithms.graph;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public final class HnswIndex {

    public record SearchResult(int id, double distance) {
    }

    private static final class Node {
        final int id;
        final double[] vector;
        final int maxLevel;

        // neighbors.get(level) contains the node's neighbors at that level.
        final List<Set<Node>> neighbors = new ArrayList<>();

        Node(int id, double[] vector, int maxLevel) {
            this.id = id;
            this.vector = vector;
            this.maxLevel = maxLevel;

            for (int level = 0; level <= maxLevel; level++) {
                neighbors.add(new HashSet<>());
            }
        }
    }

    private record Candidate(Node node, double distance) {
    }

    private final int M;
    private final int efConstruction;
    private final double levelMultiplier;

    private final Random random;
    private final AtomicInteger nextId = new AtomicInteger();
    private final Map<Integer, Node> nodes = new HashMap<>();

    private Node entryPoint;
    private int maxLevel = -1;
    private int dimensions = -1;

    public HnswIndex(int M, int efConstruction) {
        this(M, efConstruction, System.nanoTime());
    }

    public HnswIndex(int M, int efConstruction, long seed) {
        if (M < 2) {
            throw new IllegalArgumentException("M must be >= 2");
        }
        if (efConstruction < 1) {
            throw new IllegalArgumentException(
                    "efConstruction must be >= 1");
        }

        this.M = M;
        this.efConstruction = efConstruction;
        this.levelMultiplier = 1.0 / Math.log(M);
        this.random = new Random(seed);
    }

    /**
     * Adds a vector and returns its generated ID.
     * This implementation assumes vectors have the same dimension
     * and uses Euclidean distance.
     */
    public int add(double[] input) {
        Objects.requireNonNull(input, "vector");
        validateVector(input);

        double[] vector = input.clone();
        int id = nextId.getAndIncrement();
        int nodeLevel = randomLevel();

        Node node = new Node(id, vector, nodeLevel);
        nodes.put(id, node);

        // First node becomes the initial entry point.
        if (entryPoint == null) {
            entryPoint = node;
            maxLevel = nodeLevel;
            return id;
        }

        Node currentEntry = entryPoint;

        // Phase 1: greedy descent through layers above this node.
        for (int level = maxLevel; level > nodeLevel; level--) {
            currentEntry = greedySearch(vector, currentEntry, level);
        }

        // Phase 2: search for neighbors and connect at each shared layer.
        for (int level = Math.min(nodeLevel, maxLevel); level >= 0; level--) {

            List<Node> candidates = searchLayer(
                    vector,
                    List.of(currentEntry),
                    efConstruction,
                    level
            );

            int maxConnections = maxConnections(level);
            List<Node> selected = selectNeighbors(
                    vector, candidates, maxConnections);

            for (Node neighbor : selected) {
                connect(node, neighbor, level);
                prune(neighbor, level);
            }

            // Use the closest candidate as the starting point
            // for insertion into the next lower layer.
            if (!candidates.isEmpty()) {
                currentEntry = candidates.get(0);
            }
        }

        // A node above the previous maximum level becomes the entry point.
        if (nodeLevel > maxLevel) {
            entryPoint = node;
            maxLevel = nodeLevel;
        }

        return id;
    }

    /**
     * Returns up to k approximate nearest neighbors.
     * <p>
     * efSearch controls the breadth of Layer 0 exploration.
     */
    public List<SearchResult> search(
            double[] query, int k, int efSearch) {

        Objects.requireNonNull(query, "query");
        validateVector(query);

        if (k < 1 || efSearch < 1) {
            throw new IllegalArgumentException(
                    "k and efSearch must be >= 1");
        }

        if (entryPoint == null) {
            return List.of();
        }

        Node current = entryPoint;

        // Greedy navigation from the top layer down to Layer 1.
        for (int level = maxLevel; level > 0; level--) {
            current = greedySearch(query, current, level);
        }

        // Layer 0 uses best-first search, not just greedy traversal.
        int ef = Math.max(k, efSearch);

        List<Node> candidates = searchLayer(
                query, List.of(current), ef, 0);

        return candidates.stream()
                .sorted(Comparator.comparingDouble(
                        node -> distance(query, node.vector)))
                .limit(k)
                .map(node -> new SearchResult(
                        node.id, distance(query, node.vector)))
                .toList();
    }

    /**
     * Greedy search: repeatedly move to a closer neighbor
     * until no closer neighbor is available at this layer.
     */
    private Node greedySearch(
            double[] query, Node entry, int level) {

        Node current = entry;
        double currentDistance = distance(query, current.vector);
        boolean improved;

        do {
            improved = false;

            for (Node neighbor : current.neighbors.get(level)) {
                double d = distance(query, neighbor.vector);

                if (d < currentDistance) {
                    current = neighbor;
                    currentDistance = d;
                    improved = true;
                }
            }
        } while (improved);

        return current;
    }

    /**
     * Bounded best-first search at one layer.
     * <p>
     * candidates: min-heap; closest unexplored candidate first.
     * best:       max-heap; worst retained result first.
     * visited:    prevents revisiting nodes.
     */
    private List<Node> searchLayer(
            double[] query,
            Collection<Node> entryPoints,
            int ef,
            int level) {

        PriorityQueue<Candidate> candidates =
                new PriorityQueue<>(
                        Comparator.comparingDouble(Candidate::distance));

        PriorityQueue<Candidate> best =
                new PriorityQueue<>(
                        Comparator.comparingDouble(
                                Candidate::distance).reversed());

        Set<Node> visited = new HashSet<>();

        for (Node entry : entryPoints) {
            double d = distance(query, entry.vector);
            Candidate candidate = new Candidate(entry, d);

            candidates.add(candidate);
            best.add(candidate);
            visited.add(entry);
        }

        while (!candidates.isEmpty()) {
            Candidate closest = candidates.poll();
            Candidate worst = best.peek();

            // Stop when the closest unexplored candidate cannot
            // improve the current ef-sized result set.
            if (best.size() >= ef
                    && closest.distance() > worst.distance()) {
                break;
            }

            for (Node neighbor : closest.node().neighbors.get(level)) {
                if (!visited.add(neighbor)) {
                    continue;
                }

                double d = distance(query, neighbor.vector);
                worst = best.peek();

                if (best.size() < ef || d < worst.distance()) {
                    Candidate candidate = new Candidate(neighbor, d);
                    candidates.add(candidate);
                    best.add(candidate);

                    if (best.size() > ef) {
                        best.poll();
                    }
                }
            }
        }

        // Return closest first.
        return best.stream()
                .sorted(Comparator.comparingDouble(
                        Candidate::distance))
                .map(Candidate::node)
                .toList();
    }

    /**
     * Diversity heuristic:
     * prefer candidates that are not too close to an already
     * selected neighbor relative to their distance from the query.
     */
    private List<Node> selectNeighbors(
            double[] query,
            Collection<Node> candidates,
            int limit) {

        List<Node> sorted = new ArrayList<>(candidates);
        sorted.sort(Comparator.comparingDouble(
                node -> distance(query, node.vector)));

        List<Node> selected = new ArrayList<>();
        Set<Node> seen = new HashSet<>();

        for (Node candidate : sorted) {
            if (!seen.add(candidate)) {
                continue;
            }

            double queryDistance = distance(query, candidate.vector);
            boolean diverse = true;

            for (Node chosen : selected) {
                if (distance(candidate.vector, chosen.vector)
                        < queryDistance) {
                    diverse = false;
                    break;
                }
            }

            if (diverse) {
                selected.add(candidate);
                if (selected.size() == limit) {
                    return selected;
                }
            }
        }

        // If the diversity heuristic selected too few neighbors,
        // fill remaining slots with the closest unselected candidates.
        for (Node candidate : sorted) {
            if (seen.add(candidate)) {
                selected.add(candidate);
                if (selected.size() == limit) {
                    break;
                }
            }
        }

        return selected;
    }

    private void connect(Node a, Node b, int level) {
        a.neighbors.get(level).add(b);
        b.neighbors.get(level).add(a);
    }

    /**
     * Pruning removes edges from this node's adjacency list.
     * It does not delete the node or its vector.
     */
    private void prune(Node node, int level) {
        Set<Node> links = node.neighbors.get(level);
        int limit = maxConnections(level);

        if (links.size() <= limit) {
            return;
        }

        List<Node> selected = selectNeighbors(
                node.vector, links, limit);

        links.clear();
        links.addAll(selected);

        // Deliberately do not remove reverse links here.
        // Reciprocal pruning is not guaranteed by every HNSW
        // implementation; edges can become asymmetric.
    }

    private int maxConnections(int level) {
        // Common convention: up to 2*M at Layer 0 and M above it.
        return level == 0 ? 2 * M : M;
    }

    private int randomLevel() {
        double u = Math.max(random.nextDouble(),
                Double.MIN_NORMAL);

        return (int) Math.floor(
                -Math.log(u) * levelMultiplier);
    }

    private static double distance(double[] a, double[] b) {
        double sum = 0.0;

        for (int i = 0; i < a.length; i++) {
            double delta = a[i] - b[i];
            sum += delta * delta;
        }

        return Math.sqrt(sum);
    }

    private void validateVector(double[] vector) {
        if (vector.length == 0) {
            throw new IllegalArgumentException(
                    "Vector must not be empty");
        }

        for (double value : vector) {
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException(
                        "Vector values must be finite");
            }
        }

        if (dimensions == -1) {
            dimensions = vector.length;
        } else if (vector.length != dimensions) {
            throw new IllegalArgumentException(
                    "Expected dimension " + dimensions
                            + ", got " + vector.length);
        }
    }

    public int size() {
        return nodes.size();
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public int getNodeMaxLevel(int id) {
        Node node = Objects.requireNonNull(
                nodes.get(id), "Unknown node ID: " + id);
        return node.maxLevel;
    }

    public List<Integer> getNeighborIds(int id, int level) {
        Node node = Objects.requireNonNull(
                nodes.get(id), "Unknown node ID: " + id);

        if (level < 0 || level > node.maxLevel) {
            throw new IllegalArgumentException(
                    "Node " + id + " does not exist at level " + level);
        }

        return node.neighbors.get(level).stream()
                .map(neighbor -> neighbor.id)
                .sorted()
                .toList();
    }

    public static void main(String[] args) {
        HnswIndex index = new HnswIndex(
                4,      // M
                100,    // efConstruction
                42L     // deterministic random seed for this demo
        );

        int a = index.add(new double[]{0.90, 0.10});
          a = index.add(new double[]{0.90, 0.10});
          a = index.add(new double[]{0.90, 0.10});
          a = index.add(new double[]{0.90, 0.10});
//        int b = index.add(new double[]{0.85, 0.15});
//        int c = index.add(new double[]{0.80, 0.20});
//        int d = index.add(new double[]{0.75, 0.25});
//        int e = index.add(new double[]{0.05, 0.95});
//        int f = index.add(new double[]{0.10, 0.90});

        double[] query = {0.82, 0.18};

        List<SearchResult> results =
                index.search(query, 3, 50);

        System.out.println("Index size: " + index.size());
        System.out.println("Graph max level: " + index.getMaxLevel());

        System.out.println("Nearest results:");
        results.forEach(System.out::println);

        System.out.println("\nNode levels and neighbors:");
        for (int id : List.of(a )) {
            System.out.printf("Node %d, maxLevel=%d%n",
                    id, index.getNodeMaxLevel(id));

            for (int level = index.getNodeMaxLevel(id);
                 level >= 0;
                 level--) {
                System.out.printf("  Layer %d: %s%n",
                        level, index.getNeighborIds(id, level));
            }
        }
    }
}
