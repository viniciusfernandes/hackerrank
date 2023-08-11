package br.com.challanges.algorithms.datastructure;

import java.util.Arrays;
import java.util.Iterator;
import java.util.PriorityQueue;

public class Stack<T> implements Iterable<T> {
    private T[] arr;
    private int n;
    private final int shift = 2;

    public Stack(int capacity) {
        arr = (T[]) new Object[capacity];
    }

    public Stack() {
        this(10);
    }

    public int size() {
        return n;
    }


    public void add(int i, T e) {
        if (i < 0 || i >= n) {
            throw new IndexOutOfBoundsException();
        }
        if (n >= arr.length) {
            arr = Arrays.copyOf(arr, n + shift);
        }
        for (int j = n - 1; j >= i; j--) {
            arr[j + 1] = arr[j];
        }
        arr[i] = e;
        n++;
    }


    public void add(int i, T[] els) {
        if (i < 0 || i >= n) {
            throw new IndexOutOfBoundsException();
        }
        if (arr.length - n < els.length) {
            int empty = arr.length - n;
            int diff = els.length - empty;
            arr = Arrays.copyOf(arr, n + diff + shift);
        }
        for (int j = n - 1; j >= i; j--) {
            arr[j + els.length] = arr[j];
        }

        for (int k = 0; k < els.length; k++) {
            arr[i + k] = els[k];

        }
        n += els.length;
    }

    public void add(T[] els) {
        if (arr.length - n < els.length) {
            int empty = arr.length - n;
            int diff = els.length - empty;
            arr = Arrays.copyOf(arr, n + diff + shift);
        }
        for (int k = 0; k < els.length; k++) {
            arr[n + k] = els[k];
        }
        n += els.length;
    }

    public void add(T e) {
        if (n >= arr.length) {
            arr = Arrays.copyOf(arr, n + shift);
        }
        arr[n] = e;
        n++;
    }

    public T pop() {
        if (n == 0) {
            throw new IllegalStateException("This queue is empty.");
        }
        T last = arr[n - 1];
        arr[n - 1] = null;
        n--;
        return last;
    }

    public T peek() {
        if (n == 0) {
            throw new IllegalStateException("This queue is empty.");
        }
        return arr[n - 1];
    }

    public static void main(String[] args) {
        var q = new Stack<String>(0);
        q.add("a");
        q.add("b");
        q.add(0, "x");
        q.add(2, new String[]{"y1", "y2"});
        q.add(new String[]{"W1", "W2"});
        while (q.size() > 0) {
            System.out.println("queue element=" + q.pop());
        }

        PriorityQueue<String> pq = new PriorityQueue<>(3, (o1, o2) -> {
            if (o1.length() > o2.length()) {
                return 1;
            } else if (o1.length() < o2.length()) {
                return -1;
            } else {
                return 0;
            }
        });
        pq.add("xxxx");
        pq.add("yyy");
        pq.add("qqqqqqqqqqqqqq");
        pq.add("aaaaa");
        pq.add("ddd");
        pq.forEach(v -> System.out.println("value=" + v));
        System.out.println("\nbigger=" + pq.poll());
    }

    @Override
    public Iterator<T> iterator() {
        return new StackIterator<>();
    }

    private class StackIterator<V> implements Iterator<V> {
        private int cursor = n - 1;

        @Override
        public boolean hasNext() {
            return cursor >= 0;
        }

        @Override
        public V next() {
            var element = (V) arr[cursor];
            cursor--;
            return element;
        }
    }
}
