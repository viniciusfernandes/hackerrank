package br.com.challanges.algorithms.datastructure;

import java.util.Arrays;

public class BinaryHeap {
    private int[] heap;
    private int n;

    public BinaryHeap(int capacity) {
        heap = new int[capacity];
    }

    public BinaryHeap(int[] heap) {
        this.heap = heap;
        n = heap.length;
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

    private void trickleDown(int i) {
        int r, j;
        do {
            r = right(i);
            if (r < n && heap[r] < heap[i]) {
                int l = left(i);
                if (heap[l] < heap[i]) {
                    j = l;
                } else {
                    j = r;
                }
            } else {
                int l = left(i);
                if (l < n && heap[l] < heap[i]) {
                    j = l;
                } else {
                    break;
                }
            }
            if (j >= 0) {
                swap(i, j);
            }
            i = j;
        } while (i >= 0);
    }

    private void swap(int i, int j) {
        int t = heap[i];
        heap[i] = heap[j];
        heap[j] = t;
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
        BinaryHeap heap = new BinaryHeap(values);
        heap.print();

        heap.clear();
        for (int i = 0; i < values.length; i++) {
            heap.add(values[i]);
        }
        heap.print();
        //System.out.println(heap.size());
    }
}
