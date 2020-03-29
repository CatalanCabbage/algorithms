/**
 * @author Davis Jeffrey
 */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] thresholdArr;
    private int trials;


    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("args should be > 0");
        }
        this.trials = trials;
        thresholdArr = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            thresholdArr[i] = findPercolationThreshold(p, n);
        }

    }

    private double findPercolationThreshold(Percolation p, int n) {
        while (!p.percolates()) {
            int tempRow = StdRandom.uniform(n) + 1;
            int tempCol = StdRandom.uniform(n) + 1;
            p.open(tempRow, tempCol);
        }
        double totalSites = n * n;
        double openSites = p.numberOfOpenSites();
        return (openSites / totalSites);
    }

    private double mean;

    // sample mean of percolation threshold
    public double mean() {
        mean = StdStats.mean(thresholdArr);
        return mean;
    }

    private double stddev;

    // sample standard deviation of percolation threshold
    public double stddev() {
        stddev = StdStats.stddev(thresholdArr);
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return (mean - (1.96 * stddev / Math.sqrt(trials)));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (mean + (1.96 * stddev / Math.sqrt(trials)));
    }

    // test client (see below)
    public static void main(String[] args) {
        int size = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(size, trials);
        System.out.println("mean = " + ps.mean());
        System.out.println("stddev = " + ps.stddev());
        System.out.println("95% confidence interval = " + "[" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }

}