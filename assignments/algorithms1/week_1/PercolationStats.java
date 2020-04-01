import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private static final double D1_96 = 1.96;
    private final double[] x;
    private double m = -1;
    private double s = -1;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        x = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                p.open(row, col);
            }
            x[i] = (double) p.numberOfOpenSites() / (n * n);
        }
    }

    public double mean() {
        if (this.m == -1) {
            this.m = StdStats.mean(this.x);
        }
        return this.m;
    }

    public double stddev() {
        if (this.s == -1) {
            this.s = StdStats.stddev(this.x);
        }
        return this.s;
    }

    public double confidenceLo() {
        return this.mean() - D1_96 * this.stddev() / Math.sqrt(this.x.length);
    }

    public double confidenceHi() {
        return this.mean() + D1_96 * this.stddev() / Math.sqrt(this.x.length);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats percolationStats = new PercolationStats(n, t);

        StdOut.printf("%-23s = %f\n", "mean", percolationStats.mean());
        StdOut.printf("%-23s = %f\n", "stddev", percolationStats.stddev());
        StdOut.printf("%-23s = [%f, %f]\n", "95% confidence interval", percolationStats.confidenceLo(), percolationStats.confidenceHi());
    }
}
