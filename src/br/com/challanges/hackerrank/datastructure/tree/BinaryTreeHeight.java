package br.com.challanges.hackerrank.datastructure.tree;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class BinaryTreeHeight {
    private static class Node {
        int data;
        Node left;
        Node right;
    }

    public static int height(Node root) {
        if (root == null) return 0;
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        int height = 0;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(root.data, 0);
        while (!stack.empty()) {
            Node n = stack.pop();
            if (n.data > 20) {
                return 0;
            }
            if (map.get(n.data) > height) {
                height = map.get(n.data);
            }
            if (n.left != null) {
                stack.push(n.left);
                map.put(n.left.data, map.get(n.data) + 1);
            }
            if (n.right != null) {
                stack.push(n.right);
                map.put(n.right.data, map.get(n.data) + 1);
            }
        }
        return height;
    }
}
