import java.util.Iterator;

public class Stack<Item> implements Iterable<Item> {
    private class Node {
        Item item;
        Node next;

        public Node(Item item) {
            this.item = item;
            this.next = null;
        }
    }

    private Node first;

    public Stack() {
        this.first = null;
    }

    public boolean isEmpty() {
        return this.first == null;
    }

    public void push(Item item) {
        Node oldFirst = this.first;
        this.first = new Node(item);
        this.first.next = oldFirst;
    }

    public Item pop() {
        Item item = null;

        if (!this.isEmpty()) {
            item = this.first.item;
            this.first = this.first.next;
        }

        return item;
    }

    private class StackIterator implements Iterator<Item> {
        private Node current;

        public StackIterator() {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            Node oldCurrent = current;
            current = current.next;
            return oldCurrent.item;
        }

        public void remove() {}
    }

    public Iterator<Item> iterator() {
        return new StackIterator();
    }

    public static void main(String[] args) {
        Stack<String> stack = new Stack<>();

        while (!StdIn.isEmpty()) {
            String str = StdIn.readString();

            stack.push(str);
        }

        for (String str : stack) {
            StdOut.println(str);
        }

        while (!stack.isEmpty()) {
            String str = stack.pop();

            StdOut.println(str);
        }
    }
}
