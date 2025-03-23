package br.com.challanges.algorithms.datastructure;

import static br.com.challanges.algorithms.datastructure.utils.Assertions.assertEquals;
public class ArrayQueue<T> {
    private T[] a;
    private int j;
    private int n;

    public ArrayQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException();
        }
        a = (T[]) new Object[capacity];
    }

    public ArrayQueue() {
        this(10);
    }

    public int size() {
        return n;
    }

    public boolean add(T e) {
        if (n >= a.length) {
            resize();
        }
        a[(j + n) % a.length] = e;
        n++;
        return true;
    }

    public T remove() {
        if (n <= 0) {
            throw new IllegalStateException();
        }
        T first = a[j];
        a[j] = null;
        n--;
        j++;
        if (a.length >= 3 * n) {
            resize();
        }

        return first;
    }

    public T get(int i) {
        return a[(j + i) % a.length];
    }

    public T set(int i, T e) {
        T x = a[(j + i) % a.length];
        a[(j + i) % a.length] = e;
        return x;
    }

    private void resize() {
        T[] copy = (T[]) new Object[2 * n];
        for (int i = 0; i < n; i++) {
            copy[i] = a[(j + i) % a.length];
        }
        j = 0;
        a = copy;
    }

    public boolean isEmpty() {
        return n <= 0;
    }


    public static void main(String[] args) {
        var q = new ArrayQueue<String>();
        q.add("a");
        q.add("b");
        q.add("c");
        q.add("d");

        assertEquals("a", q.remove());
        assertEquals("b", q.remove());
        assertEquals("c", q.remove());

        q.add("e");
        assertEquals("d", q.get(0));
        assertEquals("e", q.get(1));
        assertEquals(2, q.size());

        q.set(0, "z");
        q.set(1, "k");
        assertEquals("z", q.get(0));
        assertEquals("k", q.get(1));
        assertEquals(2, q.size());
    }



}
