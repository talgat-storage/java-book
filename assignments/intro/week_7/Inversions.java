public class Inversions {
    private static void printArray(int[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.print(a[i] + " ");
        }
        StdOut.println();
    }

    // Return the number of inversions in the permutation a[].
    public static long count(int[] a) {
        long count = 0;

        for (int i = 0; i < a.length; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[i] > a[j]) {
                    count++;
                }
            }
        }

        return count;
    }

    // Return a permutation of length n with exactly k inversions.
    public static int[] generate(int n, long k) {
        int[] a = new int[n];
        int currIndex = 0;
        int sum = 0;
        int i, j;

        for (i = n - 1; i >= 0; i--) {
            if (sum + i <= k) {
                a[currIndex] = i;
                currIndex += 1;
            }
            else {
                a[(int) (n - (k - sum) - 1)] = i;

                for (j = 0; j < i; j++) {
                    if (a[currIndex] == 0) {
                        a[currIndex] = j;
                    }
                    else {
                        j--;
                    }
                    currIndex += 1;
                }

                break;
            }

            sum += i;
        }

        return a;
    }

    // Takes an integer n and a long k as command-line arguments,
    // and prints a permutation of length n with exactly k inversions.
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        long k = Long.parseLong(args[1]);

        int[] a = generate(n, k);

        printArray(a);
    }
}
