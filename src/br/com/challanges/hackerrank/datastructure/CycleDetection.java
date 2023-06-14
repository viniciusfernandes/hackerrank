package br.com.challanges.hackerrank.datastructure;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CycleDetection {
  private class Node {
    String label;
    int index;
    Node next;

    public Node(String label, int index) {
      this.label = label;
      this.index = index;
    }

    public String toString() {
      return label + ":" + index;
    }
  }

  private Node head;
  private boolean hasCycle;

  public int countCycle(List<String> nodeLabels) {
    var max = nodeLabels.size() - 1;
    if (max < 0 || max > 1000) {
      return 0;
    }
    linkedList(max, 0, null, nodeLabels);
    return hasCycle ? 1 : 0;
  }

  private void linkedList(int maxIndex, int index, Node node, List<String> nodeLabels) {
    if (index > maxIndex) {
      return;
    }
    if (index == 0) {
      head = new Node("head", 0);
      linkedList(maxIndex, index + 1, head, nodeLabels);
      return;
    }
    var next = getNext(nodeLabels.get(index));
    if (next == null) {
      next = new Node(nodeLabels.get(index), index);
    } else {
      hasCycle = !next.label.equals(node.label);
      return;
    }
    node.next = next;
    node = next;
    linkedList(maxIndex, index + 1, node, nodeLabels);
  }

  private Node getNext(String label) {
    var node = head;
    while (node != null && !node.label.equals(label)) {
      node = node.next;
    }
    return node;
  }

  public static void main(String[] args) throws FileNotFoundException {
    var scanner = new Scanner(new FileInputStream("/home/vinicius/Documents/cycle-detection-0.txt"));
    //var scanner = new Scanner(System.in);
    List<String> nodeLabels = new ArrayList<>();
    while (scanner.hasNextLine()) {
      nodeLabels.add(scanner.nextLine());
    }
    System.out.println(new CycleDetection().countCycle(nodeLabels));
  }
}