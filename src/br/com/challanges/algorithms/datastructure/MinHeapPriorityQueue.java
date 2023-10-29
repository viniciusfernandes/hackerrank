package br.com.challanges.algorithms.datastructure;

import java.util.Arrays;

public class MinHeapPriorityQueue {
    private int[] heap;
    private int size;

    public MinHeapPriorityQueue(int capacity) {
        heap = new int[capacity];
        size = 0;
    }

    public void offer(int value) {
        if (size == heap.length) {
            // Resize the heap if needed
            heap = Arrays.copyOf(heap, 2 * heap.length);
        }

        // Add the element to the end of the heap
        heap[size] = value;
        size++;

        // Heapify up to maintain the min-heap property
        int currentIndex = size - 1;
        while (currentIndex > 0) {
            int parentIndex = (currentIndex - 1) / 2;
            if (heap[currentIndex] < heap[parentIndex]) {
                // Swap elements if the current value is smaller than its parent
                int temp = heap[currentIndex];
                heap[currentIndex] = heap[parentIndex];
                heap[parentIndex] = temp;

                currentIndex = parentIndex;
            } else {
                break;
            }
        }
    }

    public int poll() {
        if (isEmpty()) {
            throw new IllegalStateException("Priority queue is empty.");
        }

        int minValue = heap[0];

        // Move the last element to the root and heapify down
        heap[0] = heap[size - 1];
        size--;
        heapifyDown(0);

        return minValue;
    }

    private void heapifyDown(int index) {
        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;
        int smallest = index;

        if (leftChild < size && heap[leftChild] < heap[smallest]) {
            smallest = leftChild;
        }

        if (rightChild < size && heap[rightChild] < heap[smallest]) {
            smallest = rightChild;
        }

        if (smallest != index) {
            // Swap elements if the current value is larger than its smallest child
            int temp = heap[index];
            heap[index] = heap[smallest];
            heap[smallest] = temp;

            heapifyDown(smallest);
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void print() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < size; i++) {
            s.append(heap[i]).append(" ");
        }
        System.out.println(s);
    }

    public static void main(String[] args) {
        int[] values = new int[]{4, 9, 8, 17, 26, 50, 16, 19, 69, 32, 93, 55};
        var queue = new MinHeapPriorityQueue(10);
        var heap = new BinaryHeap(3);
        for (int i = 0; i < values.length; i++) {
            queue.offer(values[i]);
            heap.add(values[i]);
        }
        queue.print();
        heap.print();
        System.out.println(queue.poll());
        System.out.println(heap.remove());
        queue.print();
        heap.print();
    }

}
