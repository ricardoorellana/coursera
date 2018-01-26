import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double [] thresholds;
    private int example;
    private int size;

    public PercolationStats(int n, int trials) {
        if (n <= 0) throw new java.lang.IllegalArgumentException("N is out of bounds");
        if (trials <= 0) throw new java.lang.IllegalArgumentException("T is out of bounds");

        size = n;
        example = trials;
        thresholds = new double[example];

        for (int i=0; i < trials; i++) {
            thresholds[i] = findPercolationThreshold();
        }
    }

    public double mean() {
        return StdStats.mean(thresholds);
    }

    public double stddev() {
        if (example == 1) return Double.NaN;
        return StdStats.stddev(thresholds);
    }

    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(example);
    }

    public double confidenceHi() {
        return mean() + 1.96*stddev()/Math.sqrt(example);
    }

    private double findPercolationThreshold() {
        Percolation perc = new Percolation(size);
        int i, j;
        int count = 0;
        while (!perc.percolates()) {
            do {
                i = StdRandom.uniform(size) + 1;
                j = StdRandom.uniform(size) + 1;
            } while (perc.isOpen(i,j));
            count++;
            perc.open(i, j);
        }
        return count / (Math.pow(size,2));
    }

    // test client, described below
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);
        StdOut.printf("mean = %f\n", stats.mean());
        StdOut.printf("stddev = %f\n", stats.stddev());
        StdOut.printf("95%% confidence interval = %f, %f\n", stats.confidenceLo(), stats.confidenceHi());
    }
}
