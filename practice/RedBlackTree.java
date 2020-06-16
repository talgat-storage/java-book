import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class RedBlackTree<Key extends Comparable<Key>> {
    private Node root = null;

    private enum Color {
        RED, BLACK
    }

    public class Node {
        private Key key;
        private Color color;

        private Node left;
        private Node right;

        public Node(Key key, Color color) {
            this.key = key;
            this.color = color;
        }
    }

    private boolean isRed(Node node) {
        if (node == null) return false;
        return node.color == Color.RED;
    }

    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = Color.RED;
        return x;
    }

    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = Color.RED;
        return x;
    }

    private void flipColors(Node h) {
        h.left.color = Color.BLACK;
        h.right.color = Color.BLACK;
        h.color = Color.RED;
    }

    public void put(Key key) {
        root = put(key, root);
    }

    private Node put(Key key, Node node) {
        if (node == null) return new Node(key, Color.RED);

        int cmp = key.compareTo(node.key);
        if (cmp < 0) node.left = put(key, node.left);
        else if (cmp > 0) node.right = put(key, node.right);

        if (!isRed(node.left) && isRed(node.right)) node = rotateLeft(node);
        if (isRed(node.left) && isRed(node.left.left)) node = rotateRight(node);
        if (isRed(node.left) && isRed(node.right)) flipColors(node);

        return node;
    }

    public Node get(Key key) {
        return get(key, root);
    }

    private Node get(Key key, Node node) {
        if (node == null) return null;

        int cmp = key.compareTo(node.key);
        if (cmp < 0) return get(key, node.left);
        else if (cmp > 0) return get(key, node.right);
        else return node;
    }

    public List<Key> inorder() {
        List<Key> list = new LinkedList<>();
        inorder(root, list);
        return list;
    }

    private void inorder(Node node, List<Key> list) {
        if (node == null) return;

        inorder(node.left, list);
        list.add(node.key);
        inorder(node.right, list);
    }

    public String toString() {
        if (root == null) return "";

        StringBuilder sb = new StringBuilder();
        Queue<Node> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            int sz = q.size();
            for (int i = 0; i < sz; i++) {
                Node node = q.poll();
                sb.append(" ");
                if (node == null) {
                    sb.append("null");
                }
                else {
                    sb.append(node.key + "(" + (node.color == Color.BLACK ? "B" : "R") + ")");
                    q.offer(node.left);
                    q.offer(node.right);
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    private boolean is23(Node node) {
        if (node == null) return true;

        if (isRed(node.right)) return false;

        return !(node.color == Color.RED && isRed(node.left));
    }

    public boolean isBalanced() {
        return dfs(root) != -1;
    }

    private int dfs(Node node) {
        if (node == null) return 0;

        int left = dfs(node.left);
        int right = dfs(node.right);

        if (!is23(node) || left == -1 || right == -1 || left != right) return -1;
        if (node.color == Color.RED) return left;

        return left + 1;
    }

    public static void main(String[] args) {
        RedBlackTree<Integer> tree1 = new RedBlackTree<>();

        int[] nums1 = new int[]{3, 7, 11, 1, 9, 5, 13, 15, 19, 21, 17};
        int[] nums2 = new int[]{2, 4, 3 ,7, 1, 5, 6, 8, 13, 9, 15, 20};
        int[] nums3 = new int[50];

        for (int num : nums1) {
            tree1.put(num);
        }

        for (Integer num : tree1.inorder()) {
            StdOut.print(num + ", ");
        }

        for (int num : nums2) {
            if (tree1.get(num) == null) {
                StdOut.println(num + " is not found");
            }
        }

        StdOut.println(tree1);

        for (int i = 0; i < nums3.length; i++) {
            nums3[i] = StdRandom.uniform(1000);
        }

        RedBlackTree<Integer> tree2 = new RedBlackTree<>();

        for (int num : nums3) {
            tree2.put(num);
        }

        StdOut.println(tree2);

        StdOut.println(tree1.isBalanced() ? "Balanced" : "Not balanced");
        StdOut.println(tree2.isBalanced() ? "Balanced" : "Not balanced");
    }
}
