import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

public class MergeSortBottomUp {
    private static void merge(Comparable[] items, int lo, int mid, int hi, Comparable[] aux) {
        int p1 = lo;
        int p2 = mid + 1;
        int i = 0;
        while (p1 <= mid || p2 <= hi) {
            Comparable item;
            if (p1 > mid) {
                item = items[p2];
                p2 += 1;
            }
            else if (p2 > hi) {
                item = items[p1];
                p1 += 1;
            }
            else if (items[p1].compareTo(items[p2]) < 0) {
                item = items[p1];
                p1 += 1;
            }
            else {
                item = items[p2];
                p2 += 1;
            }

            aux[lo + i] = item;
            i += 1;
        }

        for (i = lo; i <= hi; i++) {
            items[i] = aux[i];
//            StdOut.println(i + ": " + items[i]);
        }
    }

    public static void sort(Comparable[] items) {
        int n = items.length;
        Comparable[] aux = new Comparable[n];

        for (int sz = 1; sz < n; sz *= 2) {
//            StdOut.println("sz: " + sz);
            for (int lo = 0; lo < n ; lo += 2 * sz) {
                int mid = Math.min(lo + sz - 1, n - 1);
                int hi = Math.min(lo + 2 * sz - 1, n - 1);
//                StdOut.println("merge " + lo + " - " + mid + " - " + hi);
                merge(items, lo, mid, hi, aux);
            }
        }
    }

    public static boolean isSorted(Comparable[] items) {
        int n = items.length;

        for (int i = 1; i < n; i++) {
            if (items[i].compareTo(items[i - 1]) < 0) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);

        Integer[] nums = new Integer[n];

        for (int i = 0; i < n; i++) {
            nums[i] = StdRandom.uniform(n);
        }

        Stopwatch timer = new Stopwatch();

        sort(nums);

        double elapsedTime = timer.elapsedTime();

        StdOut.println(isSorted(nums) ? "Sorted" : "Not sorted");
        StdOut.println("Elapsed time: " + elapsedTime);
    }
}
