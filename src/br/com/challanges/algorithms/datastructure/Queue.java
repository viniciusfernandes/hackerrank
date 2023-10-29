package br.com.challanges.algorithms.datastructure;

import java.util.Arrays;
import java.util.Iterator;

public class Queue<T> implements Iterable<T> {
    private T[] elements;
    private int pointer;

    public Queue(int capacity) {
        elements = (T[]) new Object[capacity];
    }

    public Queue() {
        this(10);
    }

    public int size() {
        return pointer;
    }

    public void add(T element) {
        if (pointer >= elements.length) {
            elements = Arrays.copyOf(elements, pointer + 10);
        }
        elements[pointer] = element;
        pointer++;
    }

    public T remove() {
        if (pointer == 0) {
            throw new IllegalStateException("This queue is empty.");
        }
        T first = elements[0];
        for (int i = 0; i < pointer; i++) {
            elements[i] = elements[i + 1];
        }
        pointer--;
        return first;
    }

    public T first() {
        if (pointer == 0) {
            throw new IllegalStateException("This queue is empty.");
        }
        return elements[0];
    }

    public boolean isEmpty() {
        return pointer <= 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new QueueIterator<>();
    }

    private class QueueIterator<V> implements Iterator<V> {
        private int cursor = 0;

        @Override
        public boolean hasNext() {
            return cursor < pointer;
        }

        @Override
        public V next() {
            return (V) elements[cursor++];
        }
    }

    public static void main(String[] args) {
        var q = new Queue<String>(0);
        q.add("a");
        q.add("b");
        q.add("c");
        q.add("d");
        q.forEach(value -> System.out.println("queue value=" + value));

        System.out.println(q.remove());
        System.out.println(q.size());
        q.forEach(value -> System.out.println("queue value=" + value));

    }

}
