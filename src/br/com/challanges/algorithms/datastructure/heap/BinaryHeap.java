package br.com.challanges.algorithms.datastructure.heap;

import br.com.challanges.algorithms.datastructure.utils.Assertions;

import java.util.Arrays;

public class BinaryHeap<T extends Comparable<T>> {
    private T[] heap;
    private int size = 0;

    @SuppressWarnings("unchecked")
    public BinaryHeap(int capacity) {
        // we have to create an Object[] and cast it
        heap = (T[]) new Comparable[capacity];
    }

    public BinaryHeap() {
        this(10); // default capacity
    }

    public boolean add(T x) {
        if (x == null) {
            throw new IllegalArgumentException("Cannot add null value");
        }
        if (size == heap.length) {
            resize();
        }
        heap[size] = x;
        bubbleUp(size);
        size++;
        return true;
    }

    private void bubbleUp(int i) {
        if (i == 0) {
            return;
        }
        int p = parent(i);
        if (heap[i].compareTo(heap[p]) < 0) {
            T temp = heap[p];
            heap[p] = heap[i];
            heap[i] = temp;
        }
        bubbleUp(p);
    }

    private int parent(int i) {
        return (i - 1) / 2;
    }

    private int left(int i) {
        return 2 * i + 1;
    }

    private int right(int i) {
        return 2 * i + 2;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public T max() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public T min() {
        return heap[0];
    }

    private void resize() {
        heap = Arrays.copyOf(heap, heap.length * 2);
    }

    private T[] children(int i) {
        return (T[]) new Comparable[]{heap[left(i)], heap[right(i)]};
    }

    public static void main(String[] args) {
        BinaryHeap<String> heap = new BinaryHeap<>();
        heap.add("vinicius");
        heap.add("aida");
        heap.add("marcos");
        heap.add("bianca");
        heap.add("luana");
        Assertions.assertEquals(3, heap.size());
    }
}
