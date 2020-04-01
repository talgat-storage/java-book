import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF uf_full;
    private boolean[][] grid;
    private final int size;
    private int count;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        this.uf = new WeightedQuickUnionUF(n * n + 2);
        this.uf_full = new WeightedQuickUnionUF(n * n + 1);
        this.grid = new boolean[n][n];
        this.size = n;
        this.count = 0;
    }

    public void open(int row, int col) {
        row -= 1;
        col -= 1;
        if (!isCellValid(row, col)) {
            throw new IllegalArgumentException();
        }
        if (this.grid[row][col]) {
            return;
        }
        this.grid[row][col] = true;
        if (row == 0) {
            this.uf.union(row * this.size + col, this.size * this.size);
            this.uf_full.union(row * this.size + col, this.size * this.size);
        }
        if (row == this.size - 1) {
            this.uf.union(row * this.size + col, this.size * this.size + 1);
        }

        int[] adjRows = {row - 1, row + 1, row, row};
        int[] adjCols = {col, col, col - 1, col + 1};
        for (int i = 0; i < 4; i++) {
            if (!isCellValid(adjRows[i], adjCols[i])) {
                continue;
            }
            if (this.grid[adjRows[i]][adjCols[i]]) {
                this.uf.union(row * this.size + col, adjRows[i] * this.size + adjCols[i]);
                this.uf_full.union(row * this.size + col, adjRows[i] * this.size + adjCols[i]);
            }
        }
        this.count += 1;
    }

    public boolean isOpen(int row, int col) {
        row -= 1;
        col -= 1;
        if (!isCellValid(row, col)) {
            throw new IllegalArgumentException();
        }
        return this.grid[row][col];
    }

    public boolean isFull(int row, int col) {
        row -= 1;
        col -= 1;
        if (!isCellValid(row, col)) {
            throw new IllegalArgumentException();
        }
        int p = this.uf_full.find(row * this.size + col);
        int q = this.uf_full.find(this.size * this.size);
        return p == q;
    }

    public int numberOfOpenSites() {
        return this.count;
    }

    public boolean percolates() {
        int p = this.uf.find(this.size * this.size);
        int q = this.uf.find(this.size * this.size + 1);
        return p == q;
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(5);
        p.open(1, 4);
        StdOut.println(p.isOpen(1, 5));
        StdOut.println(p.percolates());
        StdOut.println(p.numberOfOpenSites());
        StdOut.println(p.isFull(1, 5));
    }

    private boolean isCellValid(int row, int col) {
        return row >= 0 && row < this.size && col >= 0 && col < this.size;
    }
}
