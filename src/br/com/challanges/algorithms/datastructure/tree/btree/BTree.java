package br.com.challanges.algorithms.datastructure.tree.btree;

import br.com.challanges.algorithms.datastructure.utils.Assertions;

class BTree {
    BTreeNode root;
    int t;  // minimum degree

    BTree(int t) {
        this.t = t;
        root = null;
    }

    // Traverse the tree
    void traverse() {
        if (root != null) {
            root.traverse();
        }
        System.out.println();
    }

    public boolean contains(int k) {
        return search(k) != null;
    }

    // Search key k
    private BTreeNode search(int k) {
        if (root == null) {
            return null;
        } else {
            return root.search(k);
        }
    }

    // Insert a new key
    void insert(int k) {
        if (root == null) {
            root = new BTreeNode(t, true);
            root.keys[0] = k;
            root.n = 1;
        } else {
            if (root.n == 2 * t - 1) {
                BTreeNode s = new BTreeNode(t, false);
                s.children[0] = root;
                s.splitChild(0, root);

                int i = 0;
                if (s.keys[0] < k) {
                    i++;
                }
                s.children[i].insertNonFull(k);
                root = s;
            } else {
                root.insertNonFull(k);
            }
        }
    }


    public static void main(String[] args) {
        BTree t = new BTree(2); // minimum degree = 2 → max 3 keys per node
        for (int i = 1; i <= 15; i++) {
            t.insert(i);
        }
        System.out.println("Traversal of the B-tree:");
        t.traverse();

        for (int i = 1; i <= 15; i++) {
            Assertions.assertTrue(t.contains(i));
        }
        Assertions.assertFalse(t.contains(0));
    }
}

