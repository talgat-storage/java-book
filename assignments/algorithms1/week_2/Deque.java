import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        private Item item;
        private Node prev;
        private Node next;
    }

    private Node first;
    private Node last;
    private int n;

    public boolean isEmpty() { return this.n == 0; }
    public int size() { return this.n; }
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node node = new Node();
        node.item = item;

        if (this.isEmpty()) {
            this.first = node;
            this.last = node;
        }
        else {
            node.next = this.first;
            this.first.prev = node;
            this.first = node;
        }

        this.n += 1;
    }
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node node = new Node();
        node.item = item;

        if (this.isEmpty()) {
            this.first = node;
            this.last = node;
        }
        else {
            node.prev = this.last;
            this.last.next = node;
            this.last = node;
        }

        this.n += 1;
    }
    public Item removeFirst() {
        if (this.size() == 0) {
            throw new NoSuchElementException();
        }

        Node node = this.first;
        if (this.size() == 1) {
            this.first = null;
            this.last = null;
        }
        else {
            this.first = this.first.next;
            this.first.prev = null;
        }

        this.n -= 1;

        return node.item;
    }
    public Item removeLast() {
        if (this.size() == 0) {
            throw new NoSuchElementException();
        }

        Node node = this.last;
        if (this.size() == 1) {
            this.first = null;
            this.last = null;
        }
        else {
            this.last = this.last.prev;
            this.last.next = null;
        }

        this.n -= 1;

        return node.item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        Node current = Deque.this.first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }

            Node node = current;
            current = current.next;
            return node.item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();

        deque.addFirst("second");
        deque.addFirst("first");
        deque.addLast("third");

        StdOut.println(deque.size());
        for (String s : deque) {
            StdOut.println(s);
        }

        StdOut.println(deque.removeFirst());
        StdOut.println(deque.removeLast());
        StdOut.println(deque.removeLast());
        StdOut.println(deque.size());
    }
}
