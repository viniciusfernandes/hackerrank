package br.com.challanges.algorithms.datastructure;

public class BinaryTree {
    Node root;

    public BinaryTree(Node root) {
        this.root = root;
    }

    private static class Node {
        int value;
        int height = 1;
        int size = 1;
        Node parent;
        Node left;
        Node right;


        public Node(int value) {
            this.value = value;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        public Node left(int value) {
            Node node = new Node(value);
            node.parent = this;
            left = node;
            return this;

        }

        public Node right(int value) {
            Node node = new Node(value);
            node.parent = this;
            right = node;
            return this;

        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    '}';
        }
    }

    int n;

    public int size() {
        return n;
    }

    public void transverse() {
        Node prev = null;
        Node next;
        Node u = root;
        while (u != null) {
            System.out.println(u.value);
            if (prev == u.parent) {
                n++;
                if (u.left != null) {
                    next = u.left;
                } else if (u.right != null) {
                    next = u.right;
                } else {
                    next = u.parent;
                }
            } else if (prev == u.left) {
                if (u.right != null) {
                    next = u.right;
                } else {
                    next = u.parent;
                }
            } else {
                next = u.parent;
            }
            prev = u;
            u = next;
        }
    }

    public static void main(String[] args) {
        Node root = new Node(1);
        root.left(2).right(3);
        root.left.left(4);
        root.left.right(5);
        BinaryTree tree = new BinaryTree(root);
        tree.transverse();
        System.out.println("size: " + tree.size());
    }
}
