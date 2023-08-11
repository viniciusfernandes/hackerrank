package br.com.challanges.algorithms.datastructure;

public class BinarySearchTree {
    private Node root;

    private static class Node {
        int value;
        int height;
        int size;
        Node parent;
        Node left;
        Node right;


        public Node(int value) {
            this.value = value;
        }


        public Node left(int value) {
            Node node = new Node(value);
            node.parent = this;
            left = node;
            return this;

        }

        public Node right(int value) {
            Node node = new Node(value);
            node.parent = this;
            right = node;
            return this;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        public boolean isLeftChild() {
            return parent != null && parent.left == this;
        }

        public boolean hasChild() {
            return left != null || right != null;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    ", height=" + height +
                    ", size=" + size +
                    '}';
        }
    }


    public int size(int value) {
        Node node = find(value);
        if (node == null) {
            throw new NullPointerException();
        }
        transverse(node);
        return node.size;
    }

    public int size() {
        transverse(root);
        return root.size;
    }

    public int height() {
        if (root == null) {
            return 0;
        }
        transverse(root);
        return root.height;
    }


    public void add(int value) {
        if (root == null) {
            root = new Node(value);
            return;
        }

        Node parent = findParent(value);
        if (value < parent.value) {
            parent.left(value);
        } else if (value > parent.value) {
            parent.right(value);
        }
    }


    public boolean isBalanced() {
        return isBalanced(root.value);
    }

    public boolean isBalanced(int value) {
        Node u = find(value);
        if (u == null) {
            return false;
        }
        transverse(u.left);
        transverse(u.right);
        return Math.abs(u.left.size - u.right.size) <= 1;
    }

    public void remove(int value) {
        Node u = find(value);
        if (u.isLeaf()) {
            if (u.isLeftChild()) {
                u.parent.left = null;
            } else {
                u.parent.right = null;
            }
        } else if (u.left == null) {
            u.parent.right = u.right;
            u.right.parent = u.parent;
            u.parent = null;
            u.right = null;
        } else if (u.right == null) {
            u.parent.left = u.left;
            u.left.parent = u.parent;
            u.parent = null;
            u.left = null;
        } else {
            Node w = u.right;
            while (w.left != null) {
                w = w.left;
            }
            u.value = w.value;
            w.parent.left = null;
            w.parent = null;
        }
    }

    private Node find(int value) {
        Node u = root;
        while (u != null) {
            if (value < u.value) {
                u = u.left;
            } else if (value > u.value) {
                u = u.right;
            } else {
                return u;
            }
        }
        return null;
    }

    private Node findParent(int value) {
        Node u = root;
        Node prev = null;

        while (u != null) {
            prev = u;
            if (value < u.value) {
                u = u.left;
            } else if (value > u.value) {
                u = u.right;
            } else {
                return u;
            }
        }
        return prev;
    }

    private void transverse(final Node node) {
        if (node == null) {
            return;
        }
        Node prev = null;
        Node next;
        final Node parent = node.parent;
        Node u = node;
        // I am nulling the node parent to emulate this node is a root node, because
        // this serch method works I have null parent
        u.parent = null;
        u.height = 1;
        node.size = 0;
        while (u != null) {
            if (prev == u.parent) {
                node.size++;
                if (u.left != null) {
                    next = u.left;
                    next.height = u.height + 1;
                } else if (u.right != null) {
                    next = u.right;
                    next.height = u.height + 1;
                } else {
                    next = u.parent;
                }

            } else if (prev == u.left) {
                if (u.right != null) {
                    next = u.right;
                    next.height = u.height + 1;
                } else {
                    next = u.parent;
                }
            } else {
                next = u.parent;
            }

            prev = u;
            u = next;
            if (next != null) {
                node.height = Math.max(node.height, next.height);
            }
        }
        node.parent = parent;

    }

    public static void main(String[] args) {
        scenario1();
        scenario2();
    }

    private static void scenario1() {
        int[] values = new int[]{7, 3, 1, 5, 4, 6, 11, 9, 8, 13, 12, 14};
        BinarySearchTree tree = new BinarySearchTree();
        for (int value : values) {
            tree.add(value);
        }
        System.out.println("size before removing: " + tree.size());
        System.out.println("size subtree: " + tree.size(11));
        tree.remove(11);
        System.out.println("size: " + tree.size());
        System.out.println("size after removing: " + tree.size(12));
        System.out.println("balanced: " + tree.isBalanced());
    }

    private static void scenario2() {
        int[] values = new int[]{7, 3, 1, 11};
        BinarySearchTree tree = new BinarySearchTree();
        for (int value : values) {
            tree.add(value);
        }
        System.out.println("height: " + tree.height());
    }
}
