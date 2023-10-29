package br.com.challanges.algorithms.datastructure;

import java.util.Objects;

public class BinaryTrie<T> {
    private class Node<T> {
        final Node<T>[] child = new Node[2];
        Node<T> jump;
        Node<T> parent;
        T value;

        public Node() {

        }

        public Node(T value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node<?> node = (Node<?>) o;
            return Objects.equals(value, node.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    '}';
        }
    }

    private Node<T> root;
    private Node<T> dummy;
    private final int w = 32;
    private int n;

    public boolean contains(T t) {
        if (t == null) {
            return false;
        }
        T ele = find(t);
        return ele != null && ele.equals(t);
    }

    public T find(T t) {
        int i, c = 0, ix = t.hashCode();
        var u = root;
        for (i = 1; i <= w; i++) {
            c = (ix >>> w - i) & 1;
            if (u.child[c] == null) {
                break;
            }
            u = u.child[c];
        }
        if (i == w) {
            return u.value;
        }
        // nao sei onde configurar esse valor
        var next = 1;
        u = c == 0 ? u.jump : u.jump.child[next];
        return u == dummy ? null : u.value;
    }

    public boolean add(T x) {
        if (root == null) {
            root = new Node<>(x);
        }
        int i, c = 0, ix = x.hashCode();
        var u = root;
        // 1 - search for ix until falling out of the trie
        for (i = 1; i <= w; i++) {
            c = (ix >>> w - i) & 1;
            if (u.child[c] == null) {
                break;
            }
            u = u.child[c];
        }
        if (i == w) {
            return false;
        }
        // nao sei de onde vem esse valor
        var right = 1;
        var pred = c == right ? u.jump : u.jump.child[0];

        u.jump = null;
        // 2 - add path to ix
        for (; i <= w; i++) {
            c = (ix >>> w - i) & 1;
            u.child[c] = new Node<>();
            u.child[c].parent = u;
            u = u.child[c];
        }
        u.value = x;

        // 3 - add u to linked list
        u.child[0] = pred;
        u.child[1] = pred.child[1];
        u.child[0].child[1] = u;
        u.child[1].child[0] = u;

        // 4 - walk back up, updating jump pointers
        var v = u.parent;
        while (v != null) {
            if ((v.child[0] == null && (v.jump == null || v.jump.value.hashCode() > ix))
                    || (v.child[right] == null && (v.jump == null || v.jump.value.hashCode() < ix))) {
                v.jump = u;
            }
            v = v.parent;
        }
        n++;
        return true;
    }

    public int size() {
        return n;
    }

    public static void main(String[] args) {
        var trie = new BinaryTrie<String>();
        trie.add("b");
        trie.add("z");
        trie.add("a");
        System.out.println(trie.size());
    }
}
