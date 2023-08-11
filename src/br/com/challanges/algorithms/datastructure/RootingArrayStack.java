package br.com.challanges.algorithms.datastructure;

import java.util.ArrayList;
import java.util.List;

public class RootingArrayStack<T> {
  private final  List<T[]> blocks;
  private int n;

  public RootingArrayStack(int capacity) {
    int r = 1 + findBlock(capacity - 1);
    blocks = new ArrayList<>(r);
  }

  public RootingArrayStack() {
    this(10);
  }

  private int findBlock(int i) {
    double root = (-3d + Math.sqrt(9 + 8 * i)) / 2d;
    return (int) Math.ceil(root);
  }

  public T get(int i) {
    int b = findBlock(i);
    int j = i - b * (b + 1) / 2;
    return blocks.get(b)[j];
  }

  public T set(int i, T ele) {
    int b = findBlock(i);
    int j = i - b * (b + 1) / 2;
    T old = blocks.get(b)[j];
    blocks.get(b)[j] = ele;
    return old;
  }

  public void add(int i, T ele) {
    var r = blocks.size();
    if (r * (r + 1) / 2 < n + 1) {
      grow();
    }
    n++;
    for (int j = n - 1; j > i; j--) {
      set(j, get(j - 1));
    }
    set(i, ele);
  }

  public void add(T ele) {
    add(n, ele);
  }

  private void grow() {
    blocks.add((T[]) new Object[blocks.size() + 1]);
  }

  public T remove(int i) {
    T old = get(i);
    for (int k = i; k < n - 1; k++) {
      set(k, get(k + 1));
    }
    set(n - 1, null);
    n--;
    var r = blocks.size();
    if ((r - 1) * (r - 2) / 2 >= n) {
      shrink();
    }
    return old;
  }

  private void shrink() {
    blocks.remove(blocks.size() - 1);
  }

  public T pop() {
    return remove(n - 1);
  }

  public T peek() {
    return get(n - 1);
  }

  public int size() {
    return n;
  }

  public static void main(String[] args) {
    var rootStack = new RootingArrayStack<String>();

    rootStack.add("1");
    rootStack.add("2");
    rootStack.add("3");
    rootStack.add("4");
    rootStack.add("5");
    rootStack.add("6");
    rootStack.add("7");
    rootStack.add("8");
    rootStack.add("9");
    rootStack.add("10");

    rootStack.set(9, "33");

    System.out.println(rootStack.get(1));
    System.out.println(rootStack.remove(1));
    System.out.println(rootStack.get(0));
    System.out.println(rootStack.get(1));
    System.out.println(rootStack.get(2));
  }
}
