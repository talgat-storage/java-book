import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] array;
    private int filled;

    public RandomizedQueue() {
        this.array = (Item[]) new Object[1];
    }

    private void resize(int max) {
        Item[] oldArray = this.array;
        this.array = (Item[]) new Object[max];

        for (int i = 0; i < this.filled; i++) {
            this.array[i] = oldArray[i];
        }
    }

    public boolean isEmpty() {
        return this.filled == 0;
    }

    public int size() {
        return this.filled;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (this.filled == this.array.length) {
            this.resize(this.array.length * 2);
        }

        this.array[this.filled] = item;
        this.filled += 1;
    }

    public Item dequeue() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }

        int index = StdRandom.uniform(this.filled);

        Item temp = this.array[this.filled - 1];
        this.array[this.filled - 1] = this.array[index];
        this.array[index] = temp;

        temp = this.array[this.filled - 1];
        this.array[this.filled - 1] = null;

        this.filled -= 1;

        if (this.filled > 0 && this.filled == this.array.length / 4) {
            this.resize(this.array.length / 2);
        }

        return temp;
    }

    public Item sample() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }

        int index = StdRandom.uniform(this.filled);

        return this.array[index];
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private final Item[] shuffledArray;
        private int current;

        public RandomizedQueueIterator() {
            RandomizedQueue<Item> queueCopy = new RandomizedQueue<Item>();
            this.shuffledArray = (Item[]) new Object[RandomizedQueue.this.filled];

            for (int i = 0; i < RandomizedQueue.this.filled; i++) {
                queueCopy.enqueue(RandomizedQueue.this.array[i]);
            }

            for (int i = 0; i < RandomizedQueue.this.filled; i++) {
                this.shuffledArray[i] = queueCopy.dequeue();
            }
        }

        public boolean hasNext() {
            return current != this.shuffledArray.length;
        }

        public Item next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }

            current += 1;
            return this.shuffledArray[current - 1];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();

        rq.enqueue("first");
        rq.enqueue("second");
        rq.enqueue("third");
        StdOut.println(rq.size());
        StdOut.println();
        StdOut.println(rq.sample());
        StdOut.println(rq.sample());
        StdOut.println(rq.sample());
        StdOut.println();
        for (String s : rq) {
            StdOut.println(s);
        }
        StdOut.println();
        for (String s : rq) {
            StdOut.println(s);
        }
        StdOut.println();
        StdOut.println(rq.dequeue());
        StdOut.println(rq.dequeue());
        StdOut.println(rq.dequeue());
        StdOut.println();
        StdOut.println(rq.size());
    }
}
