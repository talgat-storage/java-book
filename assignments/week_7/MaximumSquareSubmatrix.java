public class MaximumSquareSubmatrix {
//    private static void printMatrix(int[][] a) {
//        for (int row = 0; row < a.length; row++) {
//            for (int col = 0; col < a.length; col++) {
//                StdOut.print(a[row][col] + " ");
//            }
//            StdOut.println();
//        }
//    }

    public static int size(int[][] a) {
        int[][] aCopy = a;
        int min, ans = 0;

//        for (int row = 0; row < a.length; row++) {
//            for (int col = 0; col < a.length; col++) {
//                aCopy[row][col] = a[row][col];
//            }
//        }

        for (int row = 0; row < aCopy.length; row++) {
            for (int col = 0; col < aCopy.length; col++) {
                if (aCopy[row][col] == 0) {
                    continue;
                }
                ans = 1;
                if (row - 1 < 0 || col - 1 < 0) {
                    continue;
                }

                min = aCopy[row - 1][col - 1];
                if (aCopy[row - 1][col] < min) {
                    min = aCopy[row - 1][col];
                }
                if (aCopy[row][col - 1] < min) {
                    min = aCopy[row][col - 1];
                }

                aCopy[row][col] = min + 1;

                if (aCopy[row][col] > ans) {
                    ans = aCopy[row][col];
                }
            }
        }

        return ans;
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();

        int[][] a = new int[n][n];

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                a[row][col] = StdIn.readInt();
            }
        }

        StdOut.println(size(a));
    }
}
