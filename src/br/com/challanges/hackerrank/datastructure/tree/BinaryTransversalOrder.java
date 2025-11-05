package br.com.challanges.hackerrank.datastructure.tree;

import java.util.*;

public class BinaryTransversalOrder {
    private static class Node {
        int data;
        Node left;
        Node right;
    }

    public static void levelOrder(Node root) {
        if (root == null) return;
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        List<Integer> list = new ArrayList<>();
        while (!queue.isEmpty()) {
            Node n = queue.poll();
            list.add(n.data);
            if (n.left != null) {
                queue.add(n.left);
            }
            if (n.right != null) {
                queue.add(n.right);
            }
        }
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i));
            if (i < list.size() - 1) {
                System.out.print(" ");
            }
        }
    }
}
