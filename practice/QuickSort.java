import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class QuickSort {
    public static void sort(Comparable[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
//        StdOut.println("Sort " + lo + " - " + hi);
//        printArray(a);

        if (lo >= hi) { return; }

        int j = partition(a, lo, hi);

        sort(a, lo, j - 1);
        sort(a, j + 1, hi);
    }

    private static int partition(Comparable[] a, int lo, int hi) {
        Comparable pivot = a[lo];
        int i = lo + 1;
        int j = hi;

        while (true) {
            while (i <= hi && pivot.compareTo(a[i]) > 0) { i++; }
            while (j >= lo && pivot.compareTo(a[j]) < 0) { j--; }
            if (i >= j) { break; }

            Comparable temp = a[i];
            a[i] = a[j];
            a[j] = temp;

            i++;
            j--;
        }

        a[lo] = a[j];
        a[j] = pivot;

        return j;
    }

    public static boolean isSorted(Comparable[] a) {
        int n = a.length;

        for (int i = 1; i < n; i++) {
            if (a[i].compareTo(a[i - 1]) < 0) {
                return false;
            }
        }

        return true;
    }

    private static void printArray(Comparable[] a) {
        int n = a.length;

        for (int i = 0; i < n; i++) {
            if (i != 0) {
                StdOut.print(", ");
            }
            StdOut.print(a[i] + " (" + i + ")");
        }
        StdOut.println();
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);

        Integer[] a = new Integer[n];
        for (int i = 0; i < n; i++) {
            a[i] = StdRandom.uniform(n);
        }

        sort(a);

        StdOut.println(isSorted(a) ? "Sorted" : "Not sorted");
    }
}
