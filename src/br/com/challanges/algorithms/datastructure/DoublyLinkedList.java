package br.com.challanges.algorithms.datastructure;

import java.util.ArrayList;
import java.util.List;

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

    Node<T> head;
    Node<T> tail;
    int n;

    public void add(T value) {
        var node = new Node<>(value);
        if (head == null) {
            head = node;
            tail = head;
        } else if (head.next == null) {
            tail = node;
            tail.prev = head;
            head.next = tail;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
        n++;
    }

    public void add(int i, T value) {
        if (i < 0 || i >= n) {
            throw new IndexOutOfBoundsException();
        }
        var node = new Node<>(value);
        if (i == 0) {
            node.next = head;
            head.prev = node;
            head = node;
        } else {
            var curr = head;
            int count = 1;
            while (count <= i) {
                curr = curr.next;
                count++;
            }
            node.next = curr;
            node.prev = curr.prev;
            node.prev.next = node;
            node.next.prev = node;
        }
        n++;
    }

    public T set(int i, T value) {
        if (i < 0 || i >= n) {
            throw new IndexOutOfBoundsException();
        }
        var curr = head;
        int count = 0;
        while (count >= i) {
            curr = curr.next;
            count++;
        }
        var old = curr.value;
        curr.value = value;
        return old;
    }

    public T remove() {
        if (n == 0) {
            throw new IndexOutOfBoundsException();
        }
        var old = head.value;
        head = head.next;
        n--;
        return old;
    }

    public List<T> toList() {
        var list = new ArrayList<>(n);
        var node = head;
        while (node != null) {
            list.add(node.value);
            node = node.next;
        }
        return (List<T>) list;
    }

    public int size() {
        return n;
    }

    public boolean checkSize() {
        var count = 0;
        var node = head;
        while (node != null) {
            count++;
            node = node.next;
        }
        return count == n;
    }


    public void reverse() {
        if (head == null || head == tail) {
            return;
        }
        tail = head;
        Node node = head.next;
        Node next;
        while (node != null) {
            next = node.next;
            if (node.next != null) {
                node.next.prev = node.prev;
            }
            if (node.prev != null) {
                node.prev.next = node.next;
            }

            node.prev = null;
            node.next = head;
            node.next.prev = node;
            head = node;
            node = next;
        }
    }

    public static void main(String[] args) {
        var dlist = new DoublyLinkedList<String>();
        dlist.add("a");
        dlist.add("b");
        dlist.add("c");
        dlist.add("d");
        //dlist.add("x");
        dlist.reverse();
        // dlist.reverse2();

        dlist.toList().forEach( System.out::println );
        System.out.println(dlist.checkSize());
        System.out.println("XXXXXXXXXXXXX");
        var x = 17612864;
        while (x > 0) {
            System.out.print(x % 2);
            x = x / 2;
        }
        var bits = "11000010000000".toCharArray();
        int num = 0;
        for (int i = 0; i < bits.length; i++) {
            num += bits[i] == '0' ? 0 : pow(i);
        }
        System.out.println("\n"+num);
    }

    static int pow(int i) {
        int pow = 1;
        while (i-- > 0) {
            pow *= 2;
        }
        return pow;
    }
}
