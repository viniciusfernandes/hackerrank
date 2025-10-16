package br.com.challanges.leetcode;

import java.math.BigInteger;
import java.util.*;

public class TreeSummation {
    public static class Input {
        final BigInteger X, N;

        public Input(BigInteger n, BigInteger x) {
            N = n;
            X = x;
        }
    }

    private static class Node {
        int label;
        int weight;
        LinkedList<Node> children = new LinkedList<>();
        Node parent;

        public Node(int label) {
            this.label = label;
        }

        public void add(Node node) {
            children.push(node);
            node.parent = this;
            node.weight = this.weight + 1;
        }

        public void print() {
            Set<Node> visited = new HashSet<>();
            Deque<Node> stack = new ArrayDeque<>();
            stack.push(this);
            visited.add(this);

            while (!stack.isEmpty()) {
                Node current = stack.pop();

                for (Node child : current.children) {
                    if (!visited.contains(child)) {
                        System.out.println(current.label + " " + child.label);
                        visited.add(child);
                        stack.push(child);
                    }
                }
            }
        }

        @Override
        public String toString() {
            return "Node{" +
                    "weight=" + weight +
                    ", label=" + label +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return label == node.label;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(label);
        }
    }

    public static void main(String[] args) {
        final String NO_RESULT = "-1 -1";
        Scanner scanner = new Scanner(System.in);
        BigInteger T = new BigInteger(scanner.nextLine());
        if (T.compareTo(BigInteger.ONE) < 0 || T.compareTo(BigInteger.TEN) > 0) {
            return;
        }
        int t = T.intValue();
        List<Input> inputs = new ArrayList<>();
        final BigInteger maxNLimite = new BigInteger("100000");
        BigInteger NSum = BigInteger.ZERO;
        for (int i = 1; i <= t; i++) {
            String line = scanner.nextLine();
            String[] values = line.split(" ");
            BigInteger N = new BigInteger(values[0]);
            BigInteger x = new BigInteger(values[1]);
            final boolean isXInvalid = x.compareTo(BigInteger.ZERO) < 0 || x.compareTo(new BigInteger("1000000000000")) > 0;
            final boolean isNInvalid = N.compareTo(BigInteger.ONE) < 0 || N.compareTo(new BigInteger("10000")) > 0;
            if (isXInvalid || isNInvalid) {
                return;
            }
            NSum = NSum.add(N);
            inputs.add(new Input(N, x));
        }

        if (NSum.compareTo(maxNLimite) > 0) {
            return;
        }

        for (var input : inputs) {
            BigInteger N = input.N;
            BigInteger x = input.X;
            int n = N.intValue();
            final int min = n - 1;
            final int max = (n - 1) * (n) / 2;

            if (x.compareTo(BigInteger.valueOf(min)) < 0 || x.compareTo(BigInteger.valueOf(max)) > 0) {
                System.out.println(NO_RESULT);
                return;
            }

            final Node ROOT = new Node(1);
            Node node = ROOT;
            for (int i = 2; i <= n; i++) {
                Node child = new Node(i);
                node.add(child);
                node = child;
            }

            BigInteger sum = BigInteger.valueOf(max);
            Node parent = node.parent;
            while (true) {
                if (x.compareTo(sum) == 0) {
                    node.parent.children.remove(node);
                    parent.add(node);
                    break;
                } else if (x.compareTo(sum) < 0) {
                    sum = sum.subtract(BigInteger.ONE);
                    parent = parent.parent;
                }

                if (parent == ROOT) {
                    Node temp = node.parent;
                    node.parent.children.remove(node);
                    ROOT.add(node);
                    node = temp;
                    parent = node.parent;
                }
            }

            ROOT.print();
        }
    }
}
