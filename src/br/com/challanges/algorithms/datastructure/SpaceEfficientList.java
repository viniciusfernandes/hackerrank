package br.com.challanges.algorithms.datastructure;

import java.util.Arrays;

import static br.com.challanges.algorithms.datastructure.utils.Assertions.assertEquals;

public class SpaceEfficientList<T> {
    private static final int MAX_CAPACITY = 3;
    private Node<T> dummy;
    private int n;

    public SpaceEfficientList() {
        dummy = new Node<>();
        dummy.next = new Node<>();
        dummy.prev = dummy.next;
    }


    public void add(T e) {
        if (dummy.next.size() >= MAX_CAPACITY) {
            newNode();
        }
        dummy.next.add(e);
        n++;
    }

    private void newNode() {
        var newNode = new Node<T>();
        var tail = dummy.next;
        tail.next = newNode;
        newNode.prev = tail;
        dummy.next = newNode;
    }

    public void add(int i, T value) {
        validate(i);
        if (i == 0 && n == 0) {
            add(value);
            return;
        }

        var tail = dummy.next;
        // forcing expansion when the last node is full to be shifted later
        // it guarantees the tail will never be full
        if (tail.isFull()) {
            newNode();
            tail = dummy.next;
        }
        // tail will always have a new value added
        tail.n++;
        // resizing list
        n++;
        var l = getLocation(i);
        var node = l.node;
        do {
            tail.shift(0, tail.prev != null ? tail.prev.last() : null);
            tail = tail.prev;
        } while (node != tail);
        node.shift(l.i, value);

    }


    public T set(int i, T value) {
        var l = getLocation(i);
        var old = l.getValue();
        l.node.set(i, value);
        return old;
    }

    public T remove(int i) {
        validate(i);
        var tail = dummy.next;
        var l = getLocation(i);
        final var oldValue = l.getValue();
        var node = l.node;
        while (node != tail) {
            node.backShift(i, node.next != null ? node.next.first() : null);
            node = node.next;
        }
        node.backShift(node.n - 1, null);
        tail.n--;
        n--;
        if (tail.isEmpty()) {
            var prev = tail.prev;
            prev.next = null;
            tail.prev = null;
            dummy.next = prev;
        }
        return oldValue;
    }


    public T get(int i) {
        return getLocation(i).getValue();
    }

    private Location<T> getLocation(int i) {
        validate(i);
        int qtdNodes = numberOfNodes(i);
        int count = 1;
        var curr = dummy.prev;
        int idx = 0;
        while (count < qtdNodes) {
            curr = curr.next;
            count++;
            idx += MAX_CAPACITY;
        }
        return new Location<>(curr, i - idx);
    }

    private int numberOfNodes(int i) {
        int qtdNodes = (i + 1) / MAX_CAPACITY;
        if ((i + 1) % MAX_CAPACITY != 0) {
            qtdNodes += 1;
        }
        return qtdNodes;
    }


    public int size() {
        return n;
    }

    private void clear() {
        dummy.next = new Node<>();
        dummy.prev = dummy.next;
        n = 0;
    }

    private void validate(int i) {
        if (i < 0 || i >= n) {
            throw new IndexOutOfBoundsException();
        }
    }

    private static class Location<T> {
        final Node<T> node;
        final int i;

        public Location(Node<T> node, int i) {
            this.node = node;
            this.i = i;
        }

        public T getValue() {
            return node.get(i);
        }

    }

    private static class Node<T> {
        final T[] values = (T[]) new Object[MAX_CAPACITY];
        Node<T> next;
        Node<T> prev;
        private int n;

        public boolean isFull() {
            return n == MAX_CAPACITY;
        }

        public T first() {
            return values[0];
        }

        public T last() {
            return values[n - 1];
        }

        public int size() {
            return n;
        }

        public void add(T e) {
            values[n] = e;
            n++;
        }

        public T get(int i) {
            return values[i];
        }

        public T set(int i, T e) {
            var old = values[i];
            values[i] = e;
            return old;
        }

        private void shift(int i, T e) {
            for (int j = values.length - 2; j >= i; j--) {
                values[j + 1] = values[j];
            }
            values[i] = e;
        }

        void backShift(int i, T e) {
            for (int j = i; j < n - 1; j++) {
                values[j] = values[j + 1];
            }
            values[n - 1] = e;
        }

        public boolean isEmpty() {
            return n == 0;
        }

        @Override
        public String toString() {
            return "Node{" + "values=" + Arrays.deepToString(values) + '}';
        }
    }

    public static void main(String[] args) {
        var l = new SpaceEfficientList<String>();
        l.add("a");
        l.add("b");
        l.add("c");
        l.add("d");
        l.add("e");
        assertEquals(5, l.size());
        assertEquals("a", l.get(0));
        assertEquals("b", l.get(1));
        assertEquals("c", l.get(2));
        assertEquals("d", l.get(3));
        assertEquals("e", l.get(4));


        l.add(0, "1");
        assertEquals("1", l.get(0));
        assertEquals(6, l.size());


        l.add(5, "2");
        assertEquals("1", l.get(0));
        assertEquals("a", l.get(1));
        assertEquals("b", l.get(2));
        assertEquals("c", l.get(3));
        assertEquals("d", l.get(4));
        assertEquals("2", l.get(5));
        assertEquals("e", l.get(6));

        assertEquals("1", l.remove(0));
        assertEquals("e", l.remove(5));
        assertEquals(5, l.size());

        l.clear();
        assertEquals(0, l.size());
    }
}
