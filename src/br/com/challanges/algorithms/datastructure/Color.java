package br.com.challanges.algorithms.datastructure;

import java.util.LinkedList;
import java.util.PriorityQueue;

public final class Color implements Comparable<Color> {
  private int value;
  private String color;

  public Color(int value, String color) {
    this.value = value;
    this.color = color;
  }

  @Override
  public int compareTo(Color o) {
    if (color.equals("red") && !o.color.equals("red")) {
      return -1;
    } else if (!color.equals("red") && o.color.equals("red")) {
      return 1;
    } else if (color.equals(o.color)) {
      return value <= o.value ? -1 : 1;
    } else {
      return color.compareTo(o.color);
    }

  }

  public static void main(String[] args) {
    var q = new PriorityQueue<Color>();

    q.add(new Color(1200, "red"));
    q.add(new Color(21, "red"));
    q.add(new Color(23, "red"));
    q.add(new Color(222, "yellow"));
    q.add(new Color(22, "blue"));
    q.add(new Color(21, "blue"));

    //q.stream().sorted().forEach(c -> System.out.println(c.color + "=" + c.value));
    q.forEach(c -> System.out.println(c.color + "=" + c.value));
    System.out.println("**********");

    Color c;
    while ((c = q.peek()) != null) {
      System.out.println(c.color + "=" + c.value);
    }
  }
}