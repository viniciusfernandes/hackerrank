package br.com.challanges.aws;

import br.com.challanges.algorithms.datastructure.utils.Assertions;

import java.util.*;

public class MaxBrandwidth {
    private static class Node {
        int id;
        int port;
        Node parent;
        int broadcast;
        List<Node> children = new ArrayList<>();

        public Node(int id, int port) {
            this.id = id;
            this.port = port;
        }

        void addChild(Node child) {
            children.add(child);
            child.parent = this;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "id=" + id +
                    ", broadcast=" + broadcast +
                    '}';
        }
    }

    private static int bandwidth(Node root) {
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        int broadcast = Integer.MIN_VALUE;
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            if (node.broadcast > broadcast) {
                broadcast = node.broadcast;
            }
            if (!node.children.isEmpty()) {
                for (Node child : node.children) {
                    if (child.port == node.port) {
                        child.broadcast = node.broadcast + 1;
                    }
                    stack.push(child);
                }

            }

        }
        return broadcast;
    }

    public static void main(String[] args) {
        test1();
        test2();
        test3();
    }

    private static void test1() {
        Node n = new Node(1, 3);
        Node n2 = new Node(2, 1);
        Node n3 = new Node(3, 3);
        Node n4 = new Node(4, 3);
        Node n5 = new Node(5, 1);
        Node n6 = new Node(6, 1);
        Node n7 = new Node(7, 1);
        Node n8 = new Node(8, 1);

        n.addChild(n2);
        n.addChild(n3);

        n2.addChild(n6);
        n6.addChild(n7);
        n7.addChild(n8);

        n3.addChild(n4);
        n3.addChild(n5);

        Assertions.assertEquals(3, bandwidth(n));
    }

    private static void test2() {
        Node n = new Node(1, 3);
        Assertions.assertEquals(0, bandwidth(n));
    }

    private static void test3() {
        Node n = new Node(1, 3);
        Node n2 = new Node(2, 3);
        Node n3 = new Node(3, 3);
        Node n4 = new Node(4, 10);
        Node n5 = new Node(5, 11);
        Node n6 = new Node(6, 12);
        Node n7 = new Node(7, 13);
        Node n8 = new Node(8, 14);

        n.addChild(n2);
        n.addChild(n3);

        n2.addChild(n6);
        n6.addChild(n7);
        n7.addChild(n8);

        n3.addChild(n4);
        n3.addChild(n5);

        Assertions.assertEquals(1, bandwidth(n));
    }
}
