package br.com.challanges.hackerrank.datastructure.tree;

import java.util.*;

public class BinaryTreeSwapped {
    private static class Node {
        int data;
        Node left;
        Node right;
    }

    public static void levelOrder(Node root) {
        if (root == null) return;
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node n = queue.poll();
            if (n.left != null) {
                queue.add(n.left);
            }
            if (n.right != null) {
                queue.add(n.right);
            }
            Node l =n.left;
            n.left=n.right;;
            n.right=l;
        }
    }


    public static List<List<Integer>> swapNodes(List<List<Integer>> indexes, List<Integer> queries) {
        Set<Integer> levels= new HashSet<>();
        
        return null;

    }
}
