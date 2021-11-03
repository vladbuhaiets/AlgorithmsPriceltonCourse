package Percolation;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int n;
    private WeightedQuickUnionUF sites;
    private WeightedQuickUnionUF sitesForPecolates;
    private boolean[] openedSite;
    private int numberOfOpenedSites;
    private int virtualTopSite;
    private int virtualBottomSite;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 1)
            throw new java.lang.IllegalArgumentException("N must be greater than 0");
        this.n = n;
        sites = new WeightedQuickUnionUF((n * n) + 2);
        virtualTopSite = 0;
        virtualBottomSite = (n * n) + 1;
        openedSite = new boolean[(n * n) + 2];

        openedSite[virtualBottomSite] = true;
        openedSite[virtualTopSite] = false;

        this.numberOfOpenedSites = 0;

        for (int col = 1; col <= this.n; col++) {
            int top = 1;
            int bot = this.n;
            int indexTop = rowAndColToIndex(top, col);
            int indexBot = rowAndColToIndex(bot, col);
            this.sites.union(virtualBottomSite, indexBot);
            this.sites.union(virtualTopSite, indexTop);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

        int index = rowAndColToIndex(row, col);
        if (this.openedSite[index]) {
            return;
        }

        this.openedSite[index] = true;
        this.numberOfOpenedSites++;


        if (row > 1 && isOpen(row - 1, col)) {
            int indexNeighbour = rowAndColToIndex(row - 1, col);
            this.sites.union(index, indexNeighbour);
        }
        if (row < n && isOpen(row + 1, col)) {
            int indexNeighbour = rowAndColToIndex(row + 1, col);
            this.sites.union(index, indexNeighbour);
        }

        if (col > 1 && isOpen(row, col - 1)) {
            int indexNeighbour = rowAndColToIndex(row, col - 1);
            this.sites.union(index, indexNeighbour);
        }
        if (col < n && isOpen(row, col + 1)) {
            int indexNeighbour = rowAndColToIndex(row, col + 1);
            this.sites.union(index, indexNeighbour);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {

        int index = rowAndColToIndex(row, col);
        return this.openedSite[index];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int index = rowAndColToIndex(row, col);

        return (isOpen(row, col) && (sites.find(index) == sites.find(virtualTopSite)));

    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.numberOfOpenedSites;
    }

    // does the system percolate?
    public boolean percolates() {
        if (n == 1) {
            int index = rowAndColToIndex(1, 1);
            return openedSite[index];
        }
        return (sites.find(virtualBottomSite) == sites.find(virtualTopSite));

    }

    private int rowAndColToIndex(int row, int col) {
        if ((row <= 0 || row > this.n) || (col <= 0 || col > this.n)) {
            throw new java.lang.IllegalArgumentException();
        }
        return ((row - 1) * n) + col;
    }

    public static void main(String[] args) {

        Percolation percolation = new Percolation(2);

        StdOut.println("percolates = " + percolation.percolates());

        StdOut.println("isOpen(1, 2) = " + percolation.isOpen(1, 2));
        StdOut.println("isFull(1, 2) = " + percolation.isFull(1, 2));
        StdOut.println("open(1, 2)");
        percolation.open(1, 2);
        StdOut.println("isOpen(1, 2) = " + percolation.isOpen(1, 2));
        StdOut.println("isFull(1, 2) = " + percolation.isFull(1, 2));
        StdOut.println("numberOfOpenSites() = " + percolation.numberOfOpenSites());
        StdOut.println("percolates() = " + percolation.percolates());

        StdOut.println("isOpen(2, 1) = " + percolation.isOpen(2, 1));
        StdOut.println("isFull(2, 1) = " + percolation.isFull(2, 1));
        StdOut.println("open(2, 1)");
        percolation.open(2, 1);
        StdOut.println("isOpen(2, 1) = " + percolation.isOpen(2, 1));
        StdOut.println("isFull(2, 1) = " + percolation.isFull(2, 1));
        StdOut.println("numberOfOpenSites() = " + percolation.numberOfOpenSites());
        StdOut.println("percolates() = " + percolation.percolates());

        StdOut.println("isOpen(1, 1) = " + percolation.isOpen(1, 1));
        StdOut.println("isFull(1, 1) = " + percolation.isFull(1, 1));
        StdOut.println("open(1, 1)");
        percolation.open(1, 1);
        StdOut.println("isOpen(1, 1) = " + percolation.isOpen(1, 1));
        StdOut.println("isFull(1, 1) = " + percolation.isFull(1, 1));
        StdOut.println("numberOfOpenSites() = " + percolation.numberOfOpenSites());
        StdOut.println("percolates() = " + percolation.percolates());
    }
}

