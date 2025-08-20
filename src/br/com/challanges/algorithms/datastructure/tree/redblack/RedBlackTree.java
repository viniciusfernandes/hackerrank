package br.com.challanges.algorithms.datastructure.tree.redblack;

import br.com.challanges.algorithms.datastructure.utils.Assertions;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

/**
 * A generic Red-Black Tree implementation supporting add, remove, contains,
 * in-order traversal, and validation of invariants.
 * <p>
 * Based on CLRS-style algorithms, using a single shared NIL sentinel.
 * <p>
 * Usage:
 * RedBlackTree<Integer> t = new RedBlackTree<>();
 * t.add(10); t.add(20); t.add(15);
 * t.remove(20);
 * boolean has15 = t.contains(15);
 * t.forEachInOrder(System.out::println);
 * t.validate(); // throws if invariants are violated
 */
public class RedBlackTree<T extends Comparable<? super T>> {
    private enum Color {RED, BLACK}

    private final Node NIL = new Node(null, Color.BLACK); // leaf sentinel
    private Node root = NIL;
    private int size = 0;

    private class Node {
        T key;
        Color color;
        Node left, right, parent;

        Node(T key, Color color) {
            this.key = key;
            this.color = color;
            this.left = NIL;
            this.right = NIL;
            this.parent = NIL;
        }

        boolean isNil() {
            return this == NIL;
        }
    }

    // Public API
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(T key) {
        return findNode(key) != NIL;
    }

    public void add(T key) {
        if (key == null) {
            throw new NullPointerException("key");
        }
        Node z = new Node(key, Color.RED);
        Node y = NIL;
        Node x = root;
        while (x != NIL) {
            y = x;
            int cmp = key.compareTo(x.key);
            if (cmp < 0) {
                x = x.left;
            } else if (cmp > 0) {
                x = x.right;
            } else { // replace value semantics: do nothing if duplicates are not allowed
                return; // ignore duplicate keys
            }
        }
        z.parent = y;
        if (y == NIL) {
            root = z;
        } else if (key.compareTo(y.key) < 0) {
            y.left = z;
        } else {
            y.right = z;
        }
        size++;
        insertFixup(z);
    }

    public boolean remove(T key) {
        Node z = findNode(key);
        if (z == NIL) {
            return false;
        }
        deleteNode(z);
        size--;
        return true;
    }

    public T min() {
        if (root == NIL) {
            throw new NoSuchElementException();
        }
        return minimum(root).key;
    }

    public T max() {
        if (root == NIL) {
            throw new NoSuchElementException();
        }
        return maximum(root).key;
    }

    public void clear() {
        root = NIL;
        size = 0;
    }

    public List<T> toListInOrder() {
        List<T> out = new ArrayList<>();
        forEachInOrder(out::add);
        return out;
    }

    public void forEachInOrder(Consumer<T> action) {
        inOrder(root, action);
    }

    // Validation helpers: throws IllegalStateException if invalid
    public void validate() {
        if (root.color != Color.BLACK) {
            throw new IllegalStateException("Root must be black");
        }
        if (!NIL.isNil() || NIL.color != Color.BLACK) {
            throw new IllegalStateException("NIL must be black sentinel");
        }
        validateProperties(root);
    }

    public boolean isValid() {
        try {
            validate();
            return true;
        } catch (IllegalStateException e) {
            return false;
        }
    }

    // ===== Internal helpers =====

    private Node findNode(T key) {
        Node x = root;
        while (x != NIL) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) {
                x = x.left;
            } else if (cmp > 0) {
                x = x.right;
            } else {
                return x;
            }
        }
        return NIL;
    }

    private void inOrder(Node n, Consumer<T> action) {
        if (n == NIL) {
            return;
        }
        inOrder(n.left, action);
        action.accept(n.key);
        inOrder(n.right, action);
    }

    private void leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != NIL) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == NIL) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    private void rightRotate(Node y) {
        Node x = y.left;
        y.left = x.right;
        if (x.right != NIL) {
            x.right.parent = y;
        }
        x.parent = y.parent;
        if (y.parent == NIL) {
            root = x;
        } else if (y == y.parent.left) {
            y.parent.left = x;
        } else {
            y.parent.right = x;
        }
        x.right = y;
        y.parent = x;
    }

    private void insertFixup(Node z) {
        while (z.parent.color == Color.RED) {
            if (z.parent == z.parent.parent.left) {
                Node y = z.parent.parent.right; // uncle
                if (y.color == Color.RED) { // Case 1: recolor
                    z.parent.color = Color.BLACK;
                    y.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.right) { // Case 2: left-rotate
                        z = z.parent;
                        leftRotate(z);
                    }
                    // Case 3: right-rotate
                    z.parent.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    rightRotate(z.parent.parent);
                }
            } else { // symmetric
                Node y = z.parent.parent.left; // uncle
                if (y.color == Color.RED) {
                    z.parent.color = Color.BLACK;
                    y.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.left) {
                        z = z.parent;
                        rightRotate(z);
                    }
                    z.parent.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    leftRotate(z.parent.parent);
                }
            }
        }
        root.color = Color.BLACK;
    }

    private void deleteNode(Node z) {
        Node y = z; // node actually removed from the tree
        Color yOriginalColor = y.color;
        Node x;
        if (z.left == NIL) {
            x = z.right;
            transplant(z, z.right);
        } else if (z.right == NIL) {
            x = z.left;
            transplant(z, z.left);
        } else {
            y = minimum(z.right); // successor
            yOriginalColor = y.color;
            x = y.right;
            if (y.parent == z) {
                x.parent = y;
            } else {
                transplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }
            transplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }
        if (yOriginalColor == Color.BLACK) {
            deleteFixup(x);
        }
    }

    private void deleteFixup(Node x) {
        while (x != root && x.color == Color.BLACK) {
            if (x == x.parent.left) {
                Node w = x.parent.right; // sibling
                if (w.color == Color.RED) { // Case 1
                    w.color = Color.BLACK;
                    x.parent.color = Color.RED;
                    leftRotate(x.parent);
                    w = x.parent.right;
                }
                if (w.left.color == Color.BLACK && w.right.color == Color.BLACK) { // Case 2
                    w.color = Color.RED;
                    x = x.parent;
                } else {
                    if (w.right.color == Color.BLACK) { // Case 3
                        w.left.color = Color.BLACK;
                        w.color = Color.RED;
                        rightRotate(w);
                        w = x.parent.right;
                    }
                    // Case 4
                    w.color = x.parent.color;
                    x.parent.color = Color.BLACK;
                    w.right.color = Color.BLACK;
                    leftRotate(x.parent);
                    x = root;
                }
            } else { // symmetric
                Node w = x.parent.left;
                if (w.color == Color.RED) {
                    w.color = Color.BLACK;
                    x.parent.color = Color.RED;
                    rightRotate(x.parent);
                    w = x.parent.left;
                }
                if (w.right.color == Color.BLACK && w.left.color == Color.BLACK) {
                    w.color = Color.RED;
                    x = x.parent;
                } else {
                    if (w.left.color == Color.BLACK) {
                        w.right.color = Color.BLACK;
                        w.color = Color.RED;
                        leftRotate(w);
                        w = x.parent.left;
                    }
                    w.color = x.parent.color;
                    x.parent.color = Color.BLACK;
                    w.left.color = Color.BLACK;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }
        x.color = Color.BLACK;
    }

    private void transplant(Node u, Node v) {
        if (u.parent == NIL) {
            root = v;
        } else if (u == u.parent.left) {
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        v.parent = u.parent;
    }

    private Node minimum(Node x) {
        while (x.left != NIL) {
            x = x.left;
        }
        return x;
    }

    private Node maximum(Node x) {
        while (x.right != NIL) {
            x = x.right;
        }
        return x;
    }

    // ===== Validation of Red-Black properties =====
    private void validateProperties(Node n) {
        if (n == NIL) {
            return;
        }
        if (n.color == Color.RED) {
            if (n.left.color != Color.BLACK || n.right.color != Color.BLACK) {
                throw new IllegalStateException("Red node with red child at key=" + n.key);
            }
        }
        int leftBH = blackHeight(n.left);
        int rightBH = blackHeight(n.right);
        if (leftBH != rightBH) {
            throw new IllegalStateException("Black-height mismatch at key=" + n.key + " (L=" + leftBH + ", R=" + rightBH + ")");
        }
        validateProperties(n.left);
        validateProperties(n.right);
    }

    private int blackHeight(Node n) {
        if (n == NIL) {
            return 1;
        } // NIL counts as black
        int left = blackHeight(n.left);
        int right = blackHeight(n.right);
        if (left != right) {
            throw new IllegalStateException("Subtree black-height mismatch under key=" + n.key);
        }
        return left + (n.color == Color.BLACK ? 1 : 0);
    }

    // ===== Debug helpers =====
    @Override
    public String toString() {
        return toListInOrder().toString();
    }

    // Simple sanity test
    public static void main(String[] args) {
        RedBlackTree<Integer> t = new RedBlackTree<>();
        int[] vals = {10, 20, 30, 40, 50, 25, 5, 1, 60, 70, 65, 55, 15};
        for (int v : vals) t.add(v);
        System.out.println("InOrder: " + t);
        Assertions.assertEquals(13, t.size());
        Assertions.assertTrue(t.isValid());
        Assertions.assertTrue(t.contains(25));
        Assertions.assertEquals(1, t.min());
        Assertions.assertEquals(70, t.max());
        t.remove(30);
        t.remove(10);
        t.remove(60);
        System.out.println("After deletes: " + t);
        Assertions.assertTrue(t.isValid());
        Assertions.assertFalse(t.contains(30));
        Assertions.assertFalse(t.contains(10));
        Assertions.assertFalse(t.contains(60));
        Assertions.assertEquals(10, t.size());
    }
}
