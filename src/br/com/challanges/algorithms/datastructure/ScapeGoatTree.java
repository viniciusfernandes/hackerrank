package br.com.challanges.algorithms.datastructure;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/* Class ScapeGoatTree */
public class ScapeGoatTree extends BinarySearchTree {
    private Node root;
    private int n, q;

    /* Constructor */
    public ScapeGoatTree() {
        root = null;
        // size = 0
        n = 0;
    }

    /* Function to check if tree is empty */
    public boolean isEmpty() {
        return root == null;
    }

    /* Function to clear  tree */
    public void makeEmpty() {
        root = null;
        n = 0;
    }

    /* Function to count number of nodes recursively */
    private int size(Node u) {
        if (u == null)
            return 0;
        else {
            int l = 1;
            l += size(u.left);
            l += size(u.right);
            return l;
        }
    }

    /* Functions to search for an element */
    public boolean search(int val) {
        return search(root, val);
    }

    /* Function to search for an element recursively */
    private boolean search(Node r, int val) {
        boolean found = false;
        while ((r != null) && !found) {
            int rval = r.value;
            if (val < rval)
                r = r.left;
            else if (val > rval)
                r = r.right;
            else {
                found = true;
                break;
            }
            found = search(r, val);
        }
        return found;
    }

    /* Function to return current size of tree */
    public int size() {
        return n;
    }

    /* Function for inorder traversal */
    public void inorder() {
        inorder(root);
    }

    private void inorder(Node r) {
        if (r != null) {
            inorder(r.left);
            System.out.print(r.value + " ");
            inorder(r.right);
        }
    }

    /* Function for preorder traversal */
    public void preorder() {
        preorder(root);
    }

    private void preorder(Node r) {
        if (r != null) {
            System.out.print(r.value + " ");
            preorder(r.left);
            preorder(r.right);
        }
    }

    /* Function for postorder traversal */
    public void postorder() {
        postorder(root);
    }

    private void postorder(Node u) {
        if (u != null) {
            postorder(u.left);
            postorder(u.right);
            System.out.print(u.value + " ");
        }
    }

    private static int log32(int q) {
        final double log23 = 2.4663034623764317;
        return (int) Math.ceil(log23 * Math.log(q));
    }

    /* Function to insert an element */
    public boolean add(int value) {
        /* first do basic insertion keeping track of depth */
        Node u = new Node(value);
        int depth = addWithDepth(u);
        if (depth > log32(q)) {
            /* depth exceeded, find scapegoat */
            Node w = u.parent;
            while (3 * size(w) <= 2 * size(w.parent)) {
                w = w.parent;
            }
            rebuild(w.parent);
        }
        return depth >= 0;
    }

    /* Function to rebuild tree from node u */
    protected void rebuild(Node u) {
        int size = size(u);
        Node p = u.parent;
        Node[] arr = new Node[size];
        packIntoArray(u, arr, 0);
        if (p == null) {
            root = buildBalanced(arr, 0, size);
            root.parent = null;
        } else if (p.right == u) {
            p.right = buildBalanced(arr, 0, size);
            p.right.parent = p;
        } else {
            p.left = buildBalanced(arr, 0, size);
            p.left.parent = p;
        }
    }

    public boolean remove(int value) {
        if (super.remove(value)) {
            if (2 * n < q) {
                rebuild(root);
                q = n;
            }
            return true;
        }
        return false;
    }

    /* Function to packIntoArray */
    protected int packIntoArray(Node u, Node[] arr, int i) {
        if (u == null) {
            return i;
        }
        i = packIntoArray(u.left, arr, i);
        arr[i++] = u;
        return packIntoArray(u.right, arr, i);
    }

    /* Function to build balanced nodes */
    protected Node buildBalanced(Node[] a, int i, int size) {
        if (size == 0)
            return null;
        int m = size / 2;
        a[i + m].left = buildBalanced(a, i, m);
        if (a[i + m].left != null) {
            a[i + m].left.parent = a[i + m];
        }
        a[i + m].right = buildBalanced(a, i + m + 1, size - m - 1);
        if (a[i + m].right != null) {
            a[i + m].right.parent = a[i + m];
        }
        return a[i + m];
    }

    /* Function add with depth */
    private int addWithDepth(Node u) {
        Node w = root;
        if (w == null) {
            root = u;
            n++;
            q++;
            return 0;
        }
        boolean done = false;
        int depth = 0;
        do {

            if (u.value < w.value) {
                if (w.left == null) {
                    w.left = u;
                    u.parent = w;
                    done = true;
                } else {
                    w = w.left;
                }
            } else if (u.value > w.value) {
                if (w.right == null) {
                    w.right = u;
                    u.parent = w;
                    done = true;
                }
                w = w.right;
            } else {
                return -1;
            }
            depth++;
        } while (!done);
        n++;
        q++;
        return depth;
    }

    public static void main(String[] args) {
        ScapeGoatTree tree = new ScapeGoatTree();
        IntStream.rangeClosed(1, 3).forEach(value -> tree.add(value));
        System.out.println(tree.size());
    }
}
