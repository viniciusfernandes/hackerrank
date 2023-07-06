package br.com.challanges.hackerrank.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class PostOrderNodes {

  private static class Node {
    int data;
    Node left;
    Node right;

    public Node(int data) {
      this.data = data;
    }

    public boolean hasChild() {
      return left != null || right != null;
    }

    public Node add(Node left, Node right) {
      this.left = left;
      this.right = right;
      return this;
    }
  }

  public static void postOrder(Node root) {
    if (root == null || !root.hasChild()) {
      return;
    }
    Stack<Integer> stack = new Stack<>();
    postOrder(root, stack);
    StringBuilder s = new StringBuilder();
    while (!stack.isEmpty()) {
      s.append(stack.pop()).append(" ");
    }
    System.out.println(s.toString());
  }

  private static void postOrder(Node node, Stack<Integer> stack) {
    if (node == null) {
      return;
    }
    stack.push(node.data);
    postOrder(node.right, stack);
    postOrder(node.left, stack);
  }

  public static void main(String[] args) {
    PostOrderNodes.postOrder(scenario1());
    PostOrderNodes.postOrder(scenario2());
  }

  private static Node scenario1() {
    Node root = new Node(1).add(null,
        new Node(2));
    root.right.add(null, new Node(5));
    root.right.right.add(new Node(3), new Node(6));
    root.right.right.left.add(null, new Node(4));
    return root;
  }

  private static Node scenario2() {
    Node root = new Node(1).add(new Node(2), new Node(3));
    return root;
  }
}