package br.com.challanges.algorithms.datastructure;

public class PriorityQueue {
    private BinaryHeap heap;

    public PriorityQueue(int capacity) {
        heap = new BinaryHeap(capacity);
    }

    public void add(int value) {
        heap.add(value);
    }

    public int poll() {
        if (heap.isEmpty()) {
            throw new IllegalStateException("Priority queue is empty");
        }
        return heap.remove(0);
    }

    public int peek() {
        return heap.get(0);
    }

    public int size() {
        return heap.size();
    }

    public void print() {
        heap.print();
    }

    public static void main(String[] args) {
        int[] values = new int[]{4, 9, 8, 17, 26, 50, 16, 19, 69, 32, 93, 55};
        //int[] values = new int[]{4, 9, 8, 17};
        var queue = new PriorityQueue(3);
        for (int i = 0; i < values.length; i++) {
            queue.add(values[i]);
        }

        queue.print();
        System.out.println(queue.poll());
        queue.print();
    }
}
