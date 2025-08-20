package br.com.challanges.algorithms.datastructure.tree.scapegoat;

import br.com.challanges.algorithms.datastructure.utils.Assertions;

import java.util.*;

public class ScapegoatTree<T extends Comparable<T>> {

    private static class Node<T> {
        T key;
        Node<T> left, right, parent;
        int size;

        Node(T key, Node<T> parent) {
            this.key = key;
            this.parent = parent;
            this.size = 1;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "key=" + key +
                    ", size=" + size +
                    '}';
        }
    }

    private Node<T> root;
    private int n;         // current size
    private int maxSize;   // max size since last rebuild
    private final double alpha; // 0.5 < alpha < 1

    public ScapegoatTree(double alpha) {
        if (alpha <= 0.5 || alpha >= 1.0)
            throw new IllegalArgumentException("alpha must be between 0.5 and 1");
        this.alpha = alpha;
    }

    /**
     * Public API
     **/

    public boolean add(T key) {
        if (root == null) {
            root = new Node<>(key, null);
            n = maxSize = 1;
            return true;
        }
        // Standard BST insertion with depth tracking
        Node<T> u = root, parent = null;
        int depth = 0;
        while (u != null) {
            parent = u;
            int cmp = key.compareTo(u.key);
            if (cmp == 0) return false; // already exists
            if (cmp < 0) u = u.left;
            else u = u.right;
            depth++;
        }
        Node<T> newNode = new Node<>(key, parent);
        if (key.compareTo(parent.key) < 0) parent.left = newNode;
        else parent.right = newNode;

        // Update sizes up to root
        u = newNode;
        while (u != null) {
            updateSize(u);
            u = u.parent;
        }

        // Check balance
        if (depth > logAlpha(n + 1)) {
            Node<T> scapegoat = findScapegoat(newNode);
            rebuildSubtree(scapegoat.parent, scapegoat);
        }

        n++;
        maxSize = Math.max(maxSize, n);
        return true;
    }

    public boolean contains(T key) {
        return findNode(key) != null;
    }

    public boolean remove(T key) {
        Node<T> node = findNode(key);
        if (node == null) return false;

        // BST delete
        deleteNode(node);
        n--;

        if (n < alpha * maxSize) {
            root = rebuild(root, null);
            maxSize = n;
        }
        return true;
    }

    public void printInOrder() {
        printInOrder(root);
        System.out.println();
    }

    /**
     * Internals
     **/

    private Node<T> findNode(T key) {
        Node<T> u = root;
        while (u != null) {
            int cmp = key.compareTo(u.key);
            if (cmp == 0) return u;
            u = (cmp < 0) ? u.left : u.right;
        }
        return null;
    }

    private void deleteNode(Node<T> u) {
        if (u.left != null && u.right != null) {
            Node<T> succ = u.right;
            while (succ.left != null) succ = succ.left;
            u.key = succ.key;
            u = succ; // now delete successor
        }
        Node<T> child = (u.left != null) ? u.left : u.right;
        if (child != null) child.parent = u.parent;

        if (u.parent == null) root = child;
        else if (u == u.parent.left) u.parent.left = child;
        else u.parent.right = child;

        // Update sizes
        Node<T> p = u.parent;
        while (p != null) {
            updateSize(p);
            p = p.parent;
        }
    }

    private Node<T> findScapegoat(Node<T> u) {
        while (u != null) {
            if (!isBalanced(u)) return u;
            u = u.parent;
        }
        return null;
    }

    private boolean isBalanced(Node<T> u) {
        return size(u.left) <= alpha * u.size &&
                size(u.right) <= alpha * u.size;
    }

    private void rebuildSubtree(Node<T> parent, Node<T> scapegoat) {
        List<Node<T>> nodes = new ArrayList<>();
        packIntoArray(scapegoat, nodes);
        Node<T> rebuilt = buildBalanced(nodes, 0, nodes.size(), parent);

        if (parent == null) {
            root = rebuilt;
        } else if (parent.left == scapegoat) {
            parent.left = rebuilt;
        } else {
            parent.right = rebuilt;
        }
    }

    private Node<T> rebuild(Node<T> u, Node<T> parent) {
        List<Node<T>> nodes = new ArrayList<>();
        packIntoArray(u, nodes);
        return buildBalanced(nodes, 0, nodes.size(), parent);
    }

    private void packIntoArray(Node<T> u, List<Node<T>> nodes) {
        if (u == null) return;
        packIntoArray(u.left, nodes);
        nodes.add(u);
        packIntoArray(u.right, nodes);
    }

    private Node<T> buildBalanced(List<Node<T>> nodes, int i, int n, Node<T> parent) {
        if (n == 0) return null;
        int mid = n / 2;
        Node<T> root = nodes.get(i + mid);
        root.parent = parent;
        root.left = buildBalanced(nodes, i, mid, root);
        root.right = buildBalanced(nodes, i + mid + 1, n - mid - 1, root);
        updateSize(root);
        return root;
    }

    private void updateSize(Node<T> u) {
        if (u != null) {
            u.size = 1 + size(u.left) + size(u.right);
        }
    }

    private int size(Node<T> u) {
        return (u == null) ? 0 : u.size;
    }

    private double logAlpha(int n) {
        return Math.log(n) / Math.log(1.0 / alpha);
    }

    private void printInOrder(Node<T> u) {
        if (u == null) return;
        printInOrder(u.left);
        System.out.print(u.key + " ");
        printInOrder(u.right);
    }

    /**
     * Example
     **/
    public static void main(String[] args) {
        ScapegoatTree<Integer> tree = new ScapegoatTree<>(0.7);
//        tree.add(10);
//        tree.add(5);
//        tree.add(15);
//        tree.add(3);
//        tree.add(8);
//        tree.add(12);
//        tree.add(18);

        tree.add(1);
        tree.add(2);
        tree.add(3);
        tree.add(4);
        tree.add(5);
        tree.add(6);
        tree.add(7);

        tree.printInOrder(); // 3 5 8 10 12 15 18
        Assertions.assertTrue(tree.contains(8));
        Assertions.assertFalse(tree.contains(20));

        tree.remove(10);
        Assertions.assertFalse(tree.contains(10));
        tree.printInOrder(); // 3 5 8 12 15 18
    }
}

