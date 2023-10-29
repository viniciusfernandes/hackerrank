package br.com.challanges.algorithms.datastructure;

import java.util.Arrays;
import java.util.Collections;

public class BinaryHeap {
    private int[] heap;
    private int n;

    public BinaryHeap() {
        this(0);
    }

    public BinaryHeap(int capacity) {
        heap = new int[capacity];
    }

    public void sort(int[] a) {
        if (a == null) {
            return;
        }
        if (a.length <= 1) {
            return;
        }
        for (int i = a.length - 1; i >= 0; i--) {
            int p = parent(i);
            int l = left(p);
            if (a[i] < a[l]) {
                if (a[i] < a[p]) {
                    swap(a, i, p);
                }
            } else if (a[l] < a[p]) {
                swap(a, l, p);
            }
        }
    }

    private void reverse() {
        for (int i = 0; i < n / 2; i++) {
            int t = heap[i];
            heap[i] = heap[n - 1 - i];
            heap[n - 1 - i] = t;
        }
    }

    public boolean check() {
        if (n <= 1) {
            return true;
        }
        int p, i = n;
        do {
            p = parent(--i);
            if (heap[p] > heap[i]) {
                return false;
            }
        } while (i > 0);
        return true;
    }

    public int size() {
        return n;
    }

    public void clear() {
        n = 0;
        heap = new int[heap.length / 2];
    }

    public boolean add(int value) {
        if (n + 1 > heap.length) {
            resize();
        }
        heap[n++] = value;
        bubbleUp(n - 1);
        return true;
    }

    public boolean isEmpty() {
        return n <= 0;
    }

    public void print() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < n; i++) {
            s.append(heap[i]).append(" ");
        }
        System.out.println(s);
    }

    public int remove() {
        return remove(0);
    }

    public int remove(int i) {
        if (i < 0 || i >= n) {
            throw new IndexOutOfBoundsException("Failure on removing the index " + i
                    + " when the last heap index is " + (n - 1));
        }
        int val = heap[i];
        heap[i] = heap[n - 1];
        n--;
        trickleDown(i);
        return val;
    }

    public int get(int i) {
        if (i < 0 || i >= n) {
            throw new IndexOutOfBoundsException("Failure on getting the index " + i
                    + " when the last heap index is " + (n - 1));
        }
        return heap[i];
    }

    public void trickleDown(int p) {
        int r, l, j = 0;
        do {
            r = right(p);
            l = left(p);
            if (l < n && heap[l] < heap[p]) {
                j = l;
            }
            if (r < n && heap[r] < heap[p]) {
                j = r;
            }
            if (r >= n) {
                break;
            }
            swap(p, j);
            p = j;
        } while (p >= 0);
    }

    public void swap(int i, int j) {
        swap(heap, i, j);
    }

    private void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private void bubbleUp(int i) {
        int p = parent(i);
        while (p >= 0 && heap[p] > heap[i]) {
            swap(p, i);
            i = p;
            p = parent(i);
        }
    }

    private int left(int i) {
        return 2 * i + 1;
    }

    private int right(int i) {
        return 2 * i + 2;
    }

    private int parent(int i) {
        return (i - 1) / 2;
    }


    private void resize() {
        heap = Arrays.copyOf(heap, heap.length + 10);
    }

    public static void main(String[] args) {
        int[] values = new int[]{4, 9, 8, 17, 26, 50, 16, 19, 69, 32, 93, 55};
        //int[] values = new int[]{4, 9, 8, 17};
        BinaryHeap heap = new BinaryHeap(3);
        heap.print();
        System.out.println(heap.check());

        heap.clear();
        for (int i = 0; i < values.length; i++) {
            heap.add(values[i]);
        }
        heap.print();
        //System.out.println(heap.size());
    }
}
