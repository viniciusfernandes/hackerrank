package br.com.tutorial.dockernetworking.networkingmicroserviceconsumer;

import java.io.*;
import java.util.stream.IntStream;

class Node {
  public int data;
  public Node next;
  public Node prev;

  public Node(int nodeData) {
    this.data = nodeData;
    this.next = null;
    this.prev = null;
  }

  public String toString() {
    return "node:" + data;
  }
}

class DoublyLinkedList {
  public Node head;
  public Node tail;

  public DoublyLinkedList() {
    this.head = null;
    this.tail = null;
  }

  public void insertNode(int nodeData) {
    Node newNode = new Node(nodeData);
    if (head == null) {
      head = newNode;
      tail = newNode;
      return;
    }

    var ref = head;
    while (ref != null && nodeData > ref.data) {
      ref = ref.next;
    }
    if (ref == null) {
      newNode.prev = tail;
      tail.next = newNode;
      tail = newNode;
    } else {
      newNode.next = ref;
      newNode.prev = ref.prev;
      ref.prev = newNode;
      if (newNode.prev != null) {
        newNode.prev.next = newNode;
      } else {
        head = newNode;
      }
    }
  }

  private Node reverse(Node node, Node ref) {
    if (ref == null) {
      return node;
    }

    if (ref.prev != null) {
      node.next = new Node(ref.prev.data);
      node.next.prev = node;
    }

    reverse(node.next, ref.prev);
    return node;
  }

  public Node reverse() {
    if (tail == head) {
      return head;
    }
    return reverse(new Node(tail.data), tail);
  }

}

class DoublyLinkedListPrintHelper {
  public static void printList(Node node, String sep, BufferedWriter bufferedWriter) throws IOException {
    while (node != null) {
      bufferedWriter.write(String.valueOf(node.data));

      node = node.next;

      if (node != null) {
        bufferedWriter.write(sep);
      }
    }
  }
}

public class DoublyOrderLinkedList {

  public static void main(String[] args) throws IOException {
    //BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    //BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

    BufferedReader bufferedReader = new BufferedReader(new FileReader("/home/vinicius/Documents/inverted-redered-linked-list.txt"));
    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("/home/vinicius/Documents/ordered-linked-list-output.txt"));
    int t = Integer.parseInt(bufferedReader.readLine().trim());

    IntStream.range(0, t).forEach(tItr -> {
      try {
        DoublyLinkedList llist = new DoublyLinkedList();

        int llistCount = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, llistCount).forEach(i -> {
          try {
            int llistItem = Integer.parseInt(bufferedReader.readLine().trim());

            llist.insertNode(llistItem);
          } catch (IOException ex) {
            throw new RuntimeException(ex);
          }
        });

        var llist1 = llist.reverse( );

        DoublyLinkedListPrintHelper.printList(llist1, " ", bufferedWriter);
        bufferedWriter.newLine();
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    });

    bufferedReader.close();
    bufferedWriter.close();
  }
}
