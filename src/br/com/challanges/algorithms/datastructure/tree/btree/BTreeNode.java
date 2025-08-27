package br.com.challanges.algorithms.datastructure.tree.btree;

class BTreeNode {
    int[] keys;       // keys in this node
    int t;            // minimum degree
    BTreeNode[] children; // child pointers
    int n;            // current number of keys
    boolean leaf;     // true if leaf node

    BTreeNode(int t, boolean leaf) {
        this.t = t;
        this.leaf = leaf;
        this.keys = new int[2 * t - 1];
        this.children = new BTreeNode[2 * t];
        this.n = 0;
    }

    // Traverse all nodes (in-order)
    void traverse() {
        int i;
        for (i = 0; i < n; i++) {
            if (!leaf) {
                children[i].traverse();
            }
            System.out.print(keys[i] + " ");
        }
        if (!leaf) {
            children[i].traverse();
        }
    }

    // Search key k in subtree rooted with this node
    BTreeNode search(int k) {
        int i = 0;
        while (i < n && k > keys[i]) {
            i++;
        }
        if (i < n && keys[i] == k) {
            return this;
        }
        if (leaf) {
            return null;
        } else {
            return children[i].search(k);
        }
    }

    // Insert a new key in non-full node
    void insertNonFull(int k) {
        int i = n - 1;

        if (leaf) {
            // shift keys to make space
            while (i >= 0 && keys[i] > k) {
                keys[i + 1] = keys[i];
                i--;
            }
            keys[i + 1] = k;
            n++;
        } else {
            // find child to insert into
            while (i >= 0 && keys[i] > k) {
                i--;
            }
            i++;
            if (children[i].n == 2 * t - 1) {
                splitChild(i, children[i]);
                if (keys[i] < k) {
                    i++;
                }
            }
            children[i].insertNonFull(k);
        }
    }

    // Split child y at index i
    void splitChild(int i, BTreeNode y) {
        BTreeNode z = new BTreeNode(y.t, y.leaf);
        z.n = t - 1;

        // copy last t-1 keys from y to z
        for (int j = 0; j < t - 1; j++) {
            z.keys[j] = y.keys[j + t];
        }

        // copy last t children from y to z
        if (!y.leaf) {
            for (int j = 0; j < t; j++) {
                z.children[j] = y.children[j + t];
            }
        }

        y.n = t - 1;

        // shift children of this node to make space for z
        for (int j = n; j >= i + 1; j--) {
            children[j + 1] = children[j];
        }
        children[i + 1] = z;

        // shift keys to make space for y's middle key
        for (int j = n - 1; j >= i; j--) {
            keys[j + 1] = keys[j];
        }
        keys[i] = y.keys[t - 1];
        n++;
    }
}