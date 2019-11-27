public class Minesweeper {
    public static void main(String[] args) {
        int m = Integer.parseInt(args[0]);
        int n = Integer.parseInt(args[1]);
        int k = Integer.parseInt(args[2]);

        int[][] matrix = new int[m][n];

        for (int i = 0; i < k; i++) {
            int row = (int) (Math.random() * m);
            int col = (int) (Math.random() * n);

            if (matrix[row][col] == -1) {
                i--;
                continue;
            }

            matrix[row][col] = -1;
            if (row - 1 >= 0 && col - 1 >= 0 && matrix[row - 1][col - 1] != -1) {
                matrix[row - 1][col - 1] += 1;
            }
            if (row - 1 >= 0 && matrix[row - 1][col] != -1) {
                matrix[row - 1][col] += 1;
            }
            if (row - 1 >= 0 && col + 1 < n && matrix[row - 1][col + 1] != -1) {
                matrix[row - 1][col + 1] += 1;
            }

            if (col - 1 >= 0 && matrix[row][col - 1] != -1) {
                matrix[row][col - 1] += 1;
            }
            if (col + 1 < n && matrix[row][col + 1] != -1) {
                matrix[row][col + 1] += 1;
            }

            if (row + 1 < m && col - 1 >= 0 && matrix[row + 1][col - 1] != -1) {
                matrix[row + 1][col - 1] += 1;
            }
            if (row + 1 < m && matrix[row + 1][col] != -1) {
                matrix[row + 1][col] += 1;
            }
            if (row + 1 < m && col + 1 < n && matrix[row + 1][col + 1] != -1) {
                matrix[row + 1][col + 1] += 1;
            }
        }

        for (int row = 0; row < m; row++) {
            for (int col = 0; col < n; col++) {
                if (matrix[row][col] == -1) {
                    System.out.print("* ");
                }
                else {
                    System.out.print(matrix[row][col] + " ");
                }
            }
            System.out.println();
        }
    }
}
