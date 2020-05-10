import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.Iterator;

public class Heap<Item extends Comparable<Item>> implements Iterable {
    private Item[] buf;
    private int count;

    public Heap() {
        buf = (Item[]) new Comparable[1];
        count = 0;
    }

    public Heap(Item[] items) {
        buf = items.clone();
        count = items.length;

        checkBuf();
        heapify();
    }

    public Heap(Heap<Item> that) {
        this.buf = that.buf.clone();
        this.count = that.count;
    }

    private class HeapIterator implements Iterator<Item> {
        Heap<Item> heap;

        public HeapIterator(Heap<Item> heap) {
            this.heap = new Heap<Item>(heap);
        }

        public boolean hasNext() {
            return !this.heap.isEmpty();
        }

        public Item next() {
            return this.heap.delMax();
        }

        public void remove() {}
    }

    public Iterator<Item> iterator() {
        return new HeapIterator(this);
    }

    private void checkBuf() {
        if (count != 0 && count < buf.length / 4) {
            buf = Arrays.copyOf(buf, buf.length / 4);
        }
        else if (count != 0 && count == buf.length) {
            buf = Arrays.copyOf(buf, buf.length * 2);
        }
    }

    private boolean less(int i, int j) {
        return buf[i].compareTo(buf[j]) < 0;
    }

    private void swap(int i, int j) {
        Item temp = buf[i];
        buf[i] = buf[j];
        buf[j] = temp;
    }

    private int getParentIndex(int i) {
        return (i - 1) / 2;
    }

    private int getLeftChildIndex(int i) {
        return i * 2 + 1;
    }

    private int getRightChildIndex(int i) {
        return i * 2 + 2;
    }

    private void swim(int k) {
        while (k > 0 && less(getParentIndex(k), k)) {
            swap(getParentIndex(k), k);
            k = getParentIndex(k);
        }
    }

    private void sink(int k) {
        while (getLeftChildIndex(k) < count) {
            int childIndex = getLeftChildIndex(k);
            if (getRightChildIndex(k) < count && less(getLeftChildIndex(k), getRightChildIndex(k))) {
                childIndex = getRightChildIndex(k);
            }
            if (!less(k, childIndex)) {
                break;
            }
            swap(k, childIndex);
            k = childIndex;
        }
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public void add(Item item) {
        checkBuf();
        buf[count] = item;
        swim(count);
        count++;
    }

    public Item delMax() {
        swap(0, count - 1);
        Item item = buf[count - 1];
        buf[count - 1] = null;
        count--;
        sink(0);
        checkBuf();
        return item;
    }

    public void heapify() {
        for (int i = count / 2; i >= 0; i--) {
            sink(i);
        }
    }

    private static void printNums(Integer[] nums) {
        for (int i = 0; i < nums.length; i++) {
            if (i != 0) {
                StdOut.print(", ");
            }
            StdOut.print(nums[i]);
        }
        StdOut.println();
    }

    private static boolean isSorted(Integer[] items) {
        for (int i = 1; i < items.length; i++) {
            if (items[i].compareTo(items[i - 1]) > 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);

        Integer[] nums = new Integer[n];
        for (int i = 0; i < n; i++) {
            nums[i] = StdRandom.uniform(2 * n);
        }

        Heap<Integer> heap = new Heap<Integer>();

        for (Integer num : nums) {
            heap.add(num);
        }

        Integer[] sorted = new Integer[n];
        int i = 0;
        for (Object num : heap) {
            sorted[i] = (Integer) num;
            i++;
        }

        printNums(sorted);

        StdOut.println(isSorted(sorted) ? "Sorted" : "Not sorted");
    }
}
