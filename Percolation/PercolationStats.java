package Percolation;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int n;
    private int trials;
    private double mean;
    private double stddev;
    private double confidenceLo;
    private double confidenceHi;
    private double[] result;
    private final double constant = 1.96;


    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n < 1 && trials < 1)
            throw new java.lang.IllegalArgumentException("N and Trials must be greater than 0");
        this.n = n;
        this.trials = trials;
        this.result = new double[trials];
        for (int i = 0; i < this.trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                percolation.open(row, col);
            }

            double res = (double) percolation.numberOfOpenSites() / (n * n);
            result[i] = res;
        }
        this.mean = StdStats.mean(result);
        this.stddev = StdStats.stddev(result);
        this.confidenceHi = this.mean + (constant * this.stddev / Math.sqrt(trials));
        this.confidenceLo = this.mean - (constant * this.stddev / Math.sqrt(trials));
    }

    // sample mean of percolation threshold
    public double mean() {
        return this.mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return this.stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.confidenceHi;
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, t);
        StdOut.println("mean = "+ stats.mean());
        StdOut.println("stddev = "+ stats.stddev());
        StdOut.println("95% confidence interval = "+ stats.confidenceLo() + ", " + stats.confidenceHi());

    }

}