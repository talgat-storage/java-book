import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Board {
    private int[][] tiles;
    private int hamming = -1;
    private int manhattan = -1;
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        int n = tiles.length;
        this.tiles = new int[n][];
        for (int row = 0; row < n; row++) {
            this.tiles[row] = Arrays.copyOf(tiles[row], n);
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder ans = new StringBuilder();

        int n = tiles.length;
        ans.append(n + "\n");

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                ans.append(" " + tiles[row][col]);
            }
            ans.append("\n");
        }
        return ans.toString();
    }

    // board dimension n
    public int dimension() {
        return tiles.length;
    }

    // number of tiles out of place
    public int hamming() {
        if (hamming != -1) {
            return hamming;
        }
        int count = 0;
        int n = tiles.length;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                int goal = row * n + col + 1;
                if (tiles[row][col] == 0 || row == n - 1 && col == n - 1 && tiles[row][col] == 0) {
                    continue;
                }
                if (tiles[row][col] != goal) {
                    count += 1;
                }
            }
        }
        hamming = count;
        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        if (manhattan != -1) {
            return manhattan;
        }
        int count = 0;
        int n = tiles.length;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                int value = tiles[row][col];
                if (value == 0) {
                    continue;
                }
                int goalRow = (value - 1) / n;
                int goalCol = (value - 1) % n;

                count += Math.abs(goalRow - row);
                count += Math.abs(goalCol - col);
            }
        }
        manhattan = count;
        return count;
    }

    // is this board the goal board?
    public boolean isGoal() {
        int n = tiles.length;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (row == n - 1 && col == n - 1) {
                    continue;
                }
                int goal = row * n + col + 1;
                if (tiles[row][col] != goal) {
                    return false;
                }
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null || this.getClass() != y.getClass()) {
            return false;
        }
        Board that = (Board) y;

        if (this.tiles.length != that.tiles.length) {
            return false;
        }

        int n = this.tiles.length;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (this.tiles[row][col] != that.tiles[row][col]) {
                    return false;
                }
            }
        }

        return true;
    }

    private void swap(int row1, int col1, int row2, int col2) {
        int temp = tiles[row1][col1];
        tiles[row1][col1] = tiles[row2][col2];
        tiles[row2][col2] = temp;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> list = new LinkedList<>();
        int n = tiles.length;

        int zeroRow = 0;
        int zeroCol = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (tiles[row][col] == 0) {
                    zeroRow = row;
                    zeroCol = col;
                    break;
                }
            }
        }

        int[][] candidates = new int[][]{
                {zeroRow - 1, zeroCol},
                {zeroRow + 1, zeroCol},
                {zeroRow, zeroCol - 1},
                {zeroRow, zeroCol + 1}
        };

        for (int[] candidate : candidates) {
            if (candidate[0] >= 0 && candidate[0] < n && candidate[1] >= 0 && candidate[1] < n) {
                swap(candidate[0], candidate[1],  zeroRow, zeroCol);
                Board neighbor = new Board(tiles);
                list.add(neighbor);
                swap(candidate[0], candidate[1],  zeroRow, zeroCol);
            }
        }

        return list;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int n = tiles.length;

        int firstTile = -1;
        int secondTile = -1;

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (tiles[row][col] == 0) {
                    continue;
                }
                if (firstTile == -1) {
                    firstTile = row * n + col;
                    continue;
                }
                secondTile = row * n + col;
                break;
            }
        }

        swap(firstTile / n, firstTile % n, secondTile / n, secondTile % n);
        Board board = new Board(tiles);
        swap(firstTile / n, firstTile % n, secondTile / n, secondTile % n);

        return board;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] matrix = new int[][]{
            {8, 1, 3},
            {4, 0, 2},
            {7, 6, 5}
        };

        Board board = new Board(matrix);
        StdOut.println(board);
        StdOut.println("Dimension: " + board.dimension());
        StdOut.println("Hamming: " + board.hamming());
        StdOut.println("Manhattan: " + board.manhattan());
        StdOut.println("Is goal: " + board.isGoal());

        for (Board neighbor : board.neighbors()) {
            StdOut.println(neighbor);
            StdOut.println(board.equals(neighbor));
        }

        StdOut.println(board.twin());
    }
}
