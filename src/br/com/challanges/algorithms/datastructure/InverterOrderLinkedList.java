package br.com.challanges.algorithms.datastructure;

public class InverterOrderLinkedList {

  private static class ListNode {
    int val;
    ListNode next;

    ListNode(int val) {
      this.val = val;
    }

    private void print() {
      var node = this;
      while (node != null) {
        System.out.print(node.val + "=>");
        node = node.next;
      }
    }

    public String toString() {
      return "node=" + val;
    }
  }

  public ListNode sortList(ListNode head) {
    if (head == null || head.next == null) {
      return head;
    }

    var orderedList = new ListNode(head.val);
    var node = head.next;
    while (node != null) {
      orderedList = sorting(orderedList, node.val);
      node = node.next;
    }
    return orderedList;
  }

  private ListNode sorting(ListNode orderedNode, int value) {
    if (value <= orderedNode.val) {
      var newNode = new ListNode(value);
      newNode.next = orderedNode;
      return newNode;
    }
    var curr = orderedNode;
    ListNode prev = null;
    while (curr != null && value >= curr.val) {
      prev = curr;
      curr = curr.next;
    }
    if (prev != null) {
      var next = prev.next;
      prev.next = new ListNode(value);
      prev.next.next = next;
    }
    return orderedNode;
  }

  public static void main(String[] args) {
    var head = new ListNode(4);
    head.next = new ListNode(2);
    head.next.next = new ListNode(1);
    head.next.next.next = new ListNode(3);
    new InverterOrderLinkedList().sortList(head).print();
  }

}
