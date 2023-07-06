package br.com.challanges.hackerrank.tree;

public class HighBinaryTree {

  private static class Node {
    int data;
    Node left;
    Node right;

    int level = -1;

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

  public static int height(Node root) {
    if (root == null) {
      return -1;
    }
    if (!root.hasChild()) {
      return 0;
    }

    Node deepestNode = new Node(-1);
    deepestNode.level = -1;
    root.level = 0;
    height(root, deepestNode);
    return deepestNode.level;
  }

  private static void height(Node node, Node deepestNode) {
    if (node == null) {
      return;
    }
    if (!node.hasChild() && node.level > deepestNode.level) {
      deepestNode.level = node.level;
      deepestNode.data = node.data;
    }

    if (node.right != null) {
      node.right.level = node.level + 1;
    }
    if (node.left != null) {
      node.left.level = node.level + 1;
    }
    height(node.right, deepestNode);
    height(node.left, deepestNode);
  }

  public static void main(String[] args) {
    System.out.println(HighBinaryTree.height(scenario1()));
    System.out.println(HighBinaryTree.height(scenario2()));
    System.out.println(HighBinaryTree.height(scenario3()));
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

  private static Node scenario3() {
    Node root = new Node(1);
    return root;
  }

}