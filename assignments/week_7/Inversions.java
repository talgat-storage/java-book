public class Inversions {
    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private static void printArray(int[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.print(a[i] + " ");
        }
        StdOut.println();
    }

    // Return the number of inversions in the permutation a[].
    public static long count(int[] a) {
        int[] aCopy = a;
        int number, index = -1, i;
        long count = 0;

//        for (i = 0; i < a.length; i++) {
//            aCopy[i] = a[i];
//        }

        for (number = 0; number < aCopy.length; number++) {
            for (i = 0; i < aCopy.length; i++) {
                if (aCopy[i] == number) {
                    index = i;
                    break;
                }
            }

            while (index != number) {
                if (number < index) {
                    swap(aCopy, index, index - 1);
                    index--;
                }
                else {
                    swap(aCopy, index, index + 1);
                    index++;
                }

                count++;
            }
        }

        return count;
    }

    // Return a permutation of length n with exactly k inversions.
    public static int[] generate(int n, long k) {
        int[] a = new int[n];
        int number, index = -1, i;

        for (i = 0; i < a.length; i++) {
            a[i] = i;
        }

        for (number = a.length - 1; number >= 0; number--) {
            if (k == 0) {
                break;
            }

            for (i = 0; i < a.length; i++) {
                if (a[i] == number) {
                    index = i;
                    break;
                }
            }

            while (k > 0 && index >= a.length - number) {
                swap(a, index, index - 1);
                index -= 1;
                k -= 1;
            }
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
