/**
 * @author Davis Jeffrey
 */

//Does not deal with Backwash; score: 84/100
public class Percolation {
    private int n;
    private int openSites;
    private int[] a, weight;
    private boolean[] state; //True == Open, False == closed

    // Creates n-by-n a, with all sites initially blocked
    // a starts at 1; create arrays of size n+1
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n should be > 0");
        }
        this.n = n;
        a = new int[n * n + 1];
        weight = new int[n * n + 1];
        state = new boolean[n * n + 1];
        for (int i = 1; i <= n * n; i++) {
            a[i] = i;
            weight[i] = 1;
        }
        //init top and bottom virtual sites
        for (int i = 1; i <= n; i++) {
            union(1, i);
            union(n * n, ((n * n) - i + 1));
        }
    }


    private void union(int site1, int site2) {
        if (weight[site1] >= weight[site2]) { //Weighting
            a[root(site2)] = root(site1);
            weight[root(site1)] += weight[root(site2)];
        } else {
            a[root(site1)] = root(site2);
            weight[root(site2)] += weight[root(site1)];
        }
    }

    private int root(int site) {
        while (site != a[site]) {
            a[site] = a[a[site]]; //Path compression
            site = a[site];
        }
        return site;
    }

    private boolean find(int site1, int site2) {
        return root(site1) == root(site2);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        int index = gridToArrayIndex(row, col);
        if (!state[index]) {
            state[index] = true;
            openSites++;
        }
        connectToNeighbors(row, col);
        //visualizeGrid();
    }

    //check if open and connect to next, previous, top, bottom
    private void connectToNeighbors(int row, int col) {
        int index = gridToArrayIndex(row, col);
        //if not left-most column, connect to left
        if (col != 1 && isOpen(row, col - 1)) {
            union(index, gridToArrayIndex(row, col - 1));
        }
        //if not right-most column, connect to right
        if (col != n && isOpen(row, col + 1)) {
            union(index, gridToArrayIndex(row, col + 1));
        }
        //if not top-most row, connect to top
        if (row != 1 && isOpen(row - 1, col)) {
            union(index, gridToArrayIndex(row - 1, col));
        }
        //if not bottom-most row, connect to bottom
        if (row != n && isOpen(row + 1, col)) {
            union(index, gridToArrayIndex(row + 1, col));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        int index = gridToArrayIndex(row, col);
        return state[index];
    }

    // is the site (row, col) full?
    // full iff open and connected to the top
    public boolean isFull(int row, int col) {
        validate(row, col);
        int index = gridToArrayIndex(row, col);
        return (state[index] && find(1, index));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return find(n * n, 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation p = new Percolation(10);
        p.visualizeGrid();
        System.out.println(""); //Uncomment and place debugger breakpoint here to test
    }

    //Utility function
    private int gridToArrayIndex(int row, int col) {
        return ((row - 1) * n) + col;
    }

    private void validate(int row, int col) {
        if (!(row > 0 && row <= n) || !(col > 0 && col <= n)) {
            throw new IllegalArgumentException("Row/Column out of bounds");
        }
    }

    //place debugger breakpoint and test functions using Evaluate option in the IDE
    //pretty prints the grid
    private void visualizeGrid() {
        System.out.print("     ");
        for (int col = 1; col <= n; col++) {
            System.out.print(col);
            if (col < 10) {
                System.out.print("     ");
            } else if (col < 100) {
                System.out.print("   ");
            }
        }
        System.out.println("");
        for (int row = 1; row <= n; row++) {
            System.out.print(row);
            if (row < 10) {
                System.out.print("    ");
            } else if (row < 100) {
                System.out.print("   ");
            }
            for (int col = 1; col <= n; col++) {
                int index = gridToArrayIndex(row, col);
                System.out.print(a[index]);
                if (state[index]) {
                    System.out.print("O");
                } else {
                    System.out.print("X");
                }
                if (a[index] < 10) {
                    System.out.print("    ");
                } else if (a[index] < 100) {
                    System.out.print("   ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println("");
        }
        System.out.println("");
    }
}