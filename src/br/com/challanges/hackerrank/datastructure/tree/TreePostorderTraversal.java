package br.com.challanges.hackerrank.datastructure.tree;

import br.com.challanges.algorithms.datastructure.Queue;
import br.com.challanges.algorithms.datastructure.Stack;

public class TreePostorderTraversal {
  private static class Node {
    public int data;
    public Node left;
    public Node right;

    public Node(int d) {
      data = d;
      left = null;
      right = null;
    }
  }

  private Queue<Node> queue = new Queue<>();

  public void dfs(Node node) {
    if (node == null) {
      return;
    }
    if (node.left != null) {
      dfs(node.left);
    }
    if (node.right != null) {
      dfs(node.right);
    }
    queue.add(node);
  }

  public void print() {
    var postOredered = new StringBuilder();
    for (var node : queue) {
      postOredered.append(node.data).append(" ");
    }
    System.out.println(postOredered);
  }

  public static void main(String[] args) {
    var root = new Node(1);
    root.right = new Node(2);
    root.right.right = new Node(5);
    root.right.right.left = new Node(3);
    root.right.right.left.right = new Node(4);
    root.right.right.right = new Node(6);

    var t = new TreePostorderTraversal();
    t.dfs(root);
    t.print();
  }
}
