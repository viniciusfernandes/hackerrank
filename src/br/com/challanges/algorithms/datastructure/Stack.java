package br.com.challanges.algorithms.datastructure;

import java.util.Arrays;
import java.util.Iterator;
import java.util.PriorityQueue;

public class Stack<T> implements Iterable<T> {
  private T[] elements;
  private int pointer;

  public Stack(int capacity) {
    elements = (T[]) new Object[capacity];
  }

  public Stack() {
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

  public T poll() {
    if (pointer == 0) {
      throw new IllegalStateException("This queue is empty.");
    }
    pointer--;
    T last = elements[pointer];
    elements[pointer] = null;
    return last;
  }

  public T peek() {
    if (pointer == 0) {
      throw new IllegalStateException("This queue is empty.");
    }
    return elements[pointer - 1];
  }

  public static void main(String[] args) {
    var q = new Stack<String>(0);
    q.add("a");
    q.add("b");
    q.add("c");
    q.add("d");
    q.forEach(v -> System.out.println("queue element=" + v));
    System.out.println(q.poll());
    System.out.println(q.size());


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
    private int cursor = pointer - 1;

    @Override
    public boolean hasNext() {
      return cursor >= 0;
    }

    @Override
    public V next() {
      var element = (V) elements[cursor];
      cursor--;
      return element;
    }
  }
}
