package br.com.challanges.algorithms.datastructure;

import br.com.challanges.algorithms.datastructure.utils.Assertions;

class Node {
    int value;
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

public class BinaryTree extends Node {
    private int n;

    public BinaryTree(int value) {
        super(value);
    }

    public int size() {
        return n;
    }

    public void transverse() {
        Node prev = null;
        Node next;
        Node u = this;
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

    public int height() {
        return height(this);
    }

    private int height(Node u) {
        if (u == null) {
            return -1;
        }
        return 1 + Math.max(height(u.left), height(u.right));
    }

    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree(1);
        tree.left(2).right(3);
        tree.left.left(4);
        tree.transverse();
        Assertions.assertEquals(4, tree.size());
        Assertions.assertEquals(2, tree.height());

        BinaryTree tree2 = new BinaryTree(2);
        Assertions.assertEquals(0, tree2.height());

        BinaryTree tree3 = new BinaryTree(1);
        tree3.right(2);
        tree3.right.right(3);
        tree3.right.right.right(4);
        tree3.right.right.right.right(5);
        Assertions.assertEquals(4, tree3.height());

    }
}
