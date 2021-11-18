import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final boolean[] grid;
    private final WeightedQuickUnionUF qu;
    private final WeightedQuickUnionUF lightQu;
    private int number = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        grid = new boolean[n * n + 2];
        qu = new WeightedQuickUnionUF(n * n + 2);
        lightQu = new WeightedQuickUnionUF(n * n + 1);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int rowIndex = row - 1;
        int colIndex = col - 1;
        int n = (int) Math.sqrt(grid.length - 2);
        if (rowIndex < 0 || colIndex < 0 || rowIndex >= n || colIndex >= n) throw new IllegalArgumentException();
        if (isOpen(row, col)) return;
        int openSiteIndex = rowIndex * n + colIndex;
        int upSiteIndex = (rowIndex - 1) * n + colIndex;
        int downSiteIndex = (rowIndex + 1) * n + colIndex;
        int leftSiteIndex =  rowIndex * n + colIndex - 1;
        int rightSiteIndex = rowIndex * n + colIndex + 1;

        grid[openSiteIndex] = true;

        if (rowIndex == 0) {
            qu.union(openSiteIndex, grid.length - 2);
            lightQu.union(openSiteIndex, grid.length - 2);
        }
        if (rowIndex == n - 1) qu.union(openSiteIndex, grid.length - 1);

        if (rowIndex > 0 && isOpen(rowIndex - 1 + 1, colIndex + 1)) {
            qu.union(openSiteIndex, upSiteIndex);
            lightQu.union(openSiteIndex, upSiteIndex);
        }
        if (rowIndex < n - 1 && isOpen(rowIndex + 1 + 1, colIndex + 1)) {
            qu.union(openSiteIndex, downSiteIndex);
            lightQu.union(openSiteIndex, downSiteIndex);
        }
        if (colIndex > 0 && isOpen(rowIndex + 1, colIndex - 1 + 1)) {
            qu.union(openSiteIndex, leftSiteIndex);
            lightQu.union(openSiteIndex, leftSiteIndex);
        }
        if (colIndex < n - 1 && isOpen(rowIndex + 1, colIndex + 1 + 1)) {
            qu.union(openSiteIndex, rightSiteIndex);
            lightQu.union(openSiteIndex, rightSiteIndex);
        }
        number++;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        int n = (int) Math.sqrt(grid.length - 2);
        if (row - 1 < 0 || col - 1 < 0 || row - 1 >= n || col - 1 >= n) throw new IllegalArgumentException();
        return grid[(row - 1) * n + col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!isOpen(row, col)) return false;
        int n = (int) Math.sqrt(grid.length - 2);
        if (row - 1 < 0 || col - 1 < 0 || row - 1 >= n || col - 1 >= n) throw new IllegalArgumentException();
        int currentRoot = lightQu.find((row - 1) * n + col - 1);
        return currentRoot == lightQu.find(grid.length - 2);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return number;
    }

    // does the system percolate?
    public boolean percolates() {
        int initialRoot = qu.find(grid.length - 2);
        int finalRoot = qu.find(grid.length - 1);
        return initialRoot == finalRoot;
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation x = new Percolation(3);
        while (!x.percolates()) {
            int row = StdRandom.uniform(3) + 1;
            int col = StdRandom.uniform(3) + 1;
            x.open(row, col);
        }

        double mean = x.numberOfOpenSites() / (double) (3 * 3);
        StdOut.print(mean);
    }
}
