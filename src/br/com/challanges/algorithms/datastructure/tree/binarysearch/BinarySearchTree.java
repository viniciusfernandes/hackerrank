package br.com.challanges.algorithms.datastructure.tree.binarysearch;

import br.com.challanges.algorithms.datastructure.utils.Assertions;

public class BinarySearchTree<T extends Comparable<T>> {
    private int n;

    class Node<K extends T> {
        K key;
        Node<K> left, right, parent;

        Node(K key) {
            this.key = key;
            left = right = null;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "key=" + key +
                    '}';
        }
    }

    private Node<T> root;

    // Insert a key
    public boolean insert(T key) {
        if (root == null) {
            root = new Node<T>(key);
            return true;
        }

        Node<T> u = root, y = root;
        int comp = 0;
        while (u != null) {
            y = u;
            comp = key.compareTo(u.key);
            if (comp < 0) {
                u = u.left;
            } else if (comp > 0) {
                u = u.right;
            } else {
                return false;
            }
        }
        comp = key.compareTo(y.key);
        Node<T> z = new Node<>(key);
        z.parent = y;
        if (comp < 0) {
            y.left = z;
        } else if (comp > 0) {
            y.right = z;

        }
        n++;
        return true;
    }

    public int size() {
        return size(root);
    }

    private int size(Node<T> node) {
        if (node == null) return 0;
        return size(node.left) + size(node.right) + 1;
    }

    // Search for a key
    public boolean contains(T key) {
        return search(key) != null;
    }

    private Node<T> search(T key) {
        if (key == null) {
            return null;
        }
        Node<T> u = root;
        int comp = 0;
        while (u != null) {
            comp = key.compareTo(u.key);
            if (comp < 0) {
                u = u.left;
            } else if (comp > 0) {
                u = u.right;
            } else {
                return u;
            }
        }
        return null;
    }


    // Delete a key
    public boolean delete(T key) {
        Node<T> u = search(key);
        if (u == null) {
            return false;
        }
        if (u.left == null || u.right == null) {
            splice(u);
        } else {
            Node<T> min = minimal(u.right);
            u.key = min.key;
            splice(min);
        }
        n--;
        return true;
    }

    // Delete a key
    private void splice(Node<T> u) {
        Node<T> s = null, p = null;
        if (u.left != null) {
            s = u.left;
        } else if (u.right != null) {
            s = u.right;
        }

        if (u == root) {
            root = s;
            p = null;
        } else {// leaf condition
            p = u.parent;
            if (p.left == u) {
                p.left = s;
            } else if (p.right == u) {
                p.right = s;
            }
        }
        if (s != null) {
            s.parent = p;
        }
    }

    private Node<T> minimal(Node<T> u) {
        while (u.left != null) {
            u = u.left;
        }
        return u;
    }

    // In-order traversal (sorted order)
    public String inOrderTraversal() {
        StringBuilder string = new StringBuilder();
        inOrder(root, string);
        return string.toString().trim();
    }

    private void inOrder(Node<T> u, StringBuilder string) {
        if (u != null) {
            inOrder(u.left, string);
            string.append(u.key).append(" ");
            inOrder(u.right, string);
        }
    }

    // Main test
    public static void main(String[] args) {
        int[] arr = {50, 30, 70, 20, 40, 60, 80};
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        for (int j : arr) {
            bst.insert(j);
        }

        Assertions.assertEquals("20 30 40 50 60 70 80", bst.inOrderTraversal());
        Assertions.assertEquals(7, bst.size());
        Assertions.assertTrue(bst.contains(40)); // true
        Assertions.assertFalse(bst.contains(90)); // false

        bst.delete(20);
        bst.delete(30);
        bst.delete(50);

        Assertions.assertEquals(4, bst.size());
        Assertions.assertEquals("40 60 70 80", bst.inOrderTraversal());
    }
}
