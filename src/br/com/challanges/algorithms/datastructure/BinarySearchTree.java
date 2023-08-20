package br.com.challanges.algorithms.datastructure;

public class BinarySearchTree {
    protected Node root;

    protected static class Node {
        int value;
        int depth = -1;
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
                    ", height=" + depth +
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

    public int depth() {
        return depth(root);
    }

    private int depth(Node u) {
        int d = 0;
        while (u != null) {
            d++;
            u = u.parent;
        }
        return d;
    }


    public boolean add(int value) {
        if (root == null) {
            root = new Node(value);
            root.depth = 0;
            return true;
        }

        Node parent = findParent(value);
        if (value < parent.value) {
            parent.left(value);
        } else if (value > parent.value) {
            parent.right(value);
        } else {
            return false;
        }
        return true;
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

    public boolean remove(int value) {
        Node u = find(value);
        if (u == null) {
            return false;
        }
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
        return true;
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
        u.depth = 0;
        while (u != null) {
            prev = u;
            if (value < u.value) {
                u = u.left;
                u.depth = u.parent.depth + 1;
            } else if (value > u.value) {
                u = u.right;
                u.depth = u.parent.depth + 1;
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
        u.depth = 1;
        node.size = 0;
        while (u != null) {
            if (prev == u.parent) {
                node.size++;
                if (u.left != null) {
                    next = u.left;
                    next.depth = u.depth + 1;
                } else if (u.right != null) {
                    next = u.right;
                    next.depth = u.depth + 1;
                } else {
                    next = u.parent;
                }

            } else if (prev == u.left) {
                if (u.right != null) {
                    next = u.right;
                    next.depth = u.depth + 1;
                } else {
                    next = u.parent;
                }
            } else {
                next = u.parent;
            }

            prev = u;
            u = next;
            if (next != null) {
                node.depth = Math.max(node.depth, next.depth);
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
        System.out.println("height: " + tree.depth());
    }
}
