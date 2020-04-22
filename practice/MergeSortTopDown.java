import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class MergeSortTopDown {
    private static int count = 0;

    public static void sort(Comparable[] items) {
        int n = items.length;
        Comparable[] aux = new Comparable[n];

        helper(items, 0, n - 1, aux);
    }

    private static void helper(Comparable[] items, int lo, int hi, Comparable[] aux) {
        if (hi <= lo) return;

        int mid = lo + (hi - lo) / 2;
        helper(items, lo, mid, aux);
        helper(items, mid + 1, hi, aux);

        count += 1;
        StdOut.println(count + ": Merge {lo: " + lo + ", hi: " + hi + "}");

        int p1 = lo;
        int p2 = mid + 1;
        int offset = 0;
        while (p1 <= mid || p2 <= hi) {
            if (p1 > mid) {
                aux[lo + offset] = items[p2];
                p2 += 1;
            }
            else if (p2 > hi) {
                aux[lo + offset] = items[p1];
                p1 += 1;
            }
            else if (items[p1].compareTo(items[p2]) < 0) {
                aux[lo + offset] = items[p1];
                p1 += 1;
            }
            else {
                aux[lo + offset] = items[p2];
                p2 += 1;
            }
            offset += 1;
        }

        for (int i = lo; i <= hi; i++) {
            items[i] = aux[i];
        }
    }

    public static boolean isSorted(Comparable[] items) {
        Comparable prevItem = null;
        for (Comparable item : items) {
            if (prevItem != null && item.compareTo(prevItem) < 0) {
                return false;
            }
            prevItem = item;
        }
        return true;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);

        Integer[] items = new Integer[n];

        for (int i = 0; i < n; i++) {
            items[i] = StdRandom.uniform(n);
        }

        MergeSortTopDown.sort(items);

        StdOut.println(isSorted(items) ? "Sorted" : "Not sorted");
    }
}
