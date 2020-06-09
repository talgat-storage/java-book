import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class BST<Key extends Comparable<Key>, Value> {
    private Node root;

    private class Node {
        Key key;
        Value value;
        int count;
        Node left;
        Node right;

        public Node(Key key, Value value, int count) {
            this.key = key;
            this.value = value;
            this.count = count;
        }

        public String toString() {
            return "(" + this.key + ", " + this.value + ")";
        }
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        return x.count;
    }

    public Node get(Key key) {
        return get(key, root);
    }

    private Node get(Key key, Node x) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return get(key, x.left);
        else if (cmp > 0) return get(key, x.right);
        return x;
    }

    public void put(Key key, Value value) {
        root = put(key, value, root);
    }

    private Node put(Key key, Value value, Node x) {
        if (x == null) {
            return new Node(key, value, 1);
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = put(key, value, x.left);
        }
        else if (cmp > 0) {
            x.right = put(key, value, x.right);
        }
        else {
            x.value = value;
        }
        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }

    public Node floor(Key key) {
        return floor(key, root);
    }

    private Node floor(Key key, Node x) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) {
            return x;
        }
        else if (cmp < 0) {
            return floor(key, x.left);
        }
        Node t = floor(key, x.right);
        if (t != null) return t;
        return x;
    }

    public Node ceiling(Key key) {
        return ceiling(key, root);
    }

    private Node ceiling(Key key, Node x) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp > 0) return ceiling(key, x.right);
        Node t = ceiling(key, x.left);
        if (t != null) return t;
        return x;
    }

    public Node select(int k) {
        return select(k, root);
    }

    private Node select(int k, Node x) {
        if (x == null) return null;
        int t = size(x.left);
        if (k == t) return x;
        if (k < t) return select(k, x.left);
        return select(k - t - 1, x.right);
    }

    public int rank(Key key) {
        return rank(key, root);
    }

    private int rank(Key key, Node x) {
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return size(x.left);
        else if (cmp < 0) return rank(key, x.left);
        return 1 + size(x.left) + rank(key, x.right);
    }

    public Iterable<Key> keysOrdered() {
        Queue<Key> queue = new Queue<>();
        inorder(queue, root);
        return queue;
    }

    private void inorder(Queue<Key> queue, Node x) {
        if (x == null) return;
        inorder(queue, x.left);
        queue.enqueue(x.key);
        inorder(queue, x.right);
    }

    public Iterable<Key> keysByLevel() {
        Queue<Key> queue = new Queue<>();
        Queue<Node> nodes = new Queue<>();

        nodes.enqueue(root);
        while (!nodes.isEmpty()) {
            Node x = nodes.dequeue();
            queue.enqueue(x.key);
            if (x.left != null) {
                nodes.enqueue(x.left);
            }
            if (x.right != null) {
                nodes.enqueue(x.right);
            }
        }

        return queue;
    }

    public static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void shuffle(int[] a) {
        int n = a.length;

        for (int i = 0; i < n; i++) {
            int r = StdRandom.uniform(i, n);
            swap(a, i, r);
        }
    }

    public static void main(String[] args) {
        int[] a = new int[]{6, 1, 4, 7, 9, 2, 3, 8};

        shuffle(a);

        BST<Integer, Integer> bst = new BST<>();

        for (int i = 0; i < a.length; i++) {
            bst.put(a[i], i);
        }

        for (int i = 0; i < a.length; i++) {
            int j = bst.get(a[i]).value;
            if (i != j) {
                StdOut.println("Wrong value for " + a[i]);
            }
        }

        StdOut.println("Floor for 5 is " + bst.floor(5));
        StdOut.println("Ceiling for 5 is " + bst.ceiling(5));
        StdOut.println("The 5-th element is " + bst.select(4));
        StdOut.println("The rank of number 5 is " + bst.rank(5));
    }
}
