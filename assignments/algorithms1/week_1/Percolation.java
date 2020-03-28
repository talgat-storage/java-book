import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF uf;
    private int[][] grid;
    private int size;
    private int count;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        this.uf = new WeightedQuickUnionUF(n * n + 2);
        this.grid = new int[n][n];
        this.size = n;
        this.count = 0;
    }

    public void open(int row, int col) {
        row -= 1;
        col -= 1;
        if (row < 0 || row >= this.size || col < 0 || col >= this.size) {
            throw new IllegalArgumentException();
        }
        if (this.grid[row][col] == 1) {
            return;
        }
        this.grid[row][col] = 1;
        int[] adjRows = {row - 1, row + 1, row, row};
        int[] adjCols = {col, col, col - 1, col + 1};
        for (int i = 0; i < 4; i++) {
            if (adjRows[i] >= 0 && adjRows[i] < this.size && adjCols[i] >= 0 && adjCols[i] < this.size && this.grid[adjRows[i]][adjCols[i]] == 1) {
                this.uf.union(row * this.size + col, adjRows[i] * this.size + adjCols[i]);
            }
            if (adjRows[i] == 0 && adjCols[i] >= 0 && adjCols[i] < this.size) {
                this.uf.union(adjRows[i] * this.size + adjCols[i], this.size * this.size);
            }
            if (adjRows[i] == this.size - 1 && adjCols[i] >= 0 && adjCols[i] < this.size) {
                this.uf.union(adjRows[i] * this.size + adjCols[i], this.size * this.size + 1);
            }
        }
        this.count += 1;
    }

    public boolean isOpen(int row, int col) {
        row -= 1;
        col -= 1;
        if (row < 0 || row >= this.size || col < 0 || col >= this.size) {
            throw new IllegalArgumentException();
        }
        return this.grid[row][col] == 1;
    }

    public boolean isFull(int row, int col) {
        row -= 1;
        col -= 1;
        if (row < 0 || row >= this.size || col < 0 || col >= this.size) {
            throw new IllegalArgumentException();
        }
        int p = this.uf.find(row * this.size + col);
        int q = this.uf.find(this.size * this.size);
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
        Percolation p = new Percolation(3);
        StdOut.println(p.isOpen(1, 1));
        StdOut.println(p.isFull(3, 1));
        StdOut.println(p.percolates());
        p.open(1, 1);
        p.open(2, 1);
        p.open(3, 1);
        StdOut.println(p.isFull(3, 1));
        StdOut.println(p.percolates());
    }
}
