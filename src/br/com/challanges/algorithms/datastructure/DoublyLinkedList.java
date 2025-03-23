package br.com.challanges.algorithms.datastructure;

import static br.com.challanges.algorithms.datastructure.utils.Assertions.assertEquals;

public class DoublyLinkedList<T> {
    private class Node<V> {
        V value;

        Node<T> prev;
        Node<T> next;

        public Node(V value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node{" + "value=" + value + '}';
        }
    }

    private Node<T> dummy;
    private int n;

    public DoublyLinkedList() {
        dummy = new Node<>(null);
        dummy.prev = dummy;
        dummy.next = dummy;
    }

    public void add(T value) {
        if (n == 0) {
            add(0, value);
            return;
        }
        var newNode = new Node<>(value);
        var last = dummy.next;
        last.next = newNode;
        newNode.prev = last;
        dummy.next = newNode;
        n++;
    }

    public void add(int i, T value) {
        if (i < 0 || i > n) {
            throw new IndexOutOfBoundsException();
        }
        if (n == 0) {
            var newNode = new Node<>(value);
            dummy.prev = newNode;
            dummy.next = newNode;
        } else {
            var newNode = new Node<>(value);
            var currNode = getNode(i);
            newNode.next = currNode;
            newNode.prev = currNode.prev;
            if (currNode.prev != null) {
                currNode.prev.next = newNode;
            }
            currNode.prev = newNode;
            dummy.prev = newNode;
        }
        n++;
    }

    public T set(int i, T value) {
        validateIndex(i);
        var curr = dummy.prev;
        int count = 0;
        while (count >= i) {
            curr = curr.next;
            count++;
        }
        var old = curr.value;
        curr.value = value;
        return old;
    }

    public T remove(int i) {
        validateIndex(i);
        Node<T> curr = null;
        if (i < n / 2) {
            var count = 0;
            do {
                if (curr == null) {
                    curr = dummy.prev;
                } else {
                    curr = curr.next;
                }
            }
            while (++count <= i);
        } else {
            int count = n - 1;
            do {
                if (curr == null) {
                    curr = dummy.next;
                } else {
                    curr = curr.prev;
                }
            }
            while (--count >= i);
        }
        var old = curr.value;
        if (i == 0) {
            dummy.prev = curr.next;
        } else if (i == n - 1) {
            dummy.next = curr.prev;
        }
        var prev = curr.prev;
        if (prev != null) {
            prev.next = curr.next;
        }
        if (curr.next != null) {
            curr.next.prev = prev;
        }
        n--;
        return old;
    }

    private Node<T> getNode(int i) {
        if (n == 0 || i < 0 || i > n) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> node = dummy.prev;
        if (i < n / 2) {
            for (int j = 0; j <= i; j++) {
                if (j == i) {
                    break;
                }
                node = node.next;
            }
        } else {
            node = dummy.next;
            for (int j = n - 1; j >= i; j--) {
                if (j == i) {
                    break;
                }
                node = node.prev;
            }
        }
        return node;
    }

    public T get(int i) {
        Node<T> node = getNode(i);
        return node != null ? node.value : null;
    }

    public int size() {
        return n;
    }

    private void validateIndex(int i) {
        if (i < 0 || i >= n) {
            throw new IndexOutOfBoundsException();
        }
    }

    public static void main(String[] args) {
        var l = new DoublyLinkedList<String>();
        l.add("a");
        l.add("b");
        l.add("c");
        l.add("d");

        assertEquals("a", l.get(0));
        assertEquals("b", l.get(1));
        assertEquals("c", l.get(2));
        assertEquals("d", l.get(3));
        assertEquals(4, l.size());

        assertEquals("c", l.remove(2));
        assertEquals("d", l.remove(2));
        assertEquals("a", l.remove(0));
        assertEquals("b", l.remove(0));
        assertEquals(0, l.size());

        l.add("z");
        assertEquals(1, l.size());
    }

    static int pow(int i) {
        int pow = 1;
        while (i-- > 0) {
            pow *= 2;
        }
        return pow;
    }
}
