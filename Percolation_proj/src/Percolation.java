import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF weightedQuickUnionUF;
    private int gridSize;
    private int width;
    private int [] openSites;
    private int openSitesCount;
    private int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Percolation(int n) {
        int nBynGrid;
        gridSize = n * n;
        width = n;
        nBynGrid = gridSize + 2;
        openSites = new int[gridSize];
        weightedQuickUnionUF = new WeightedQuickUnionUF(nBynGrid);
    }

    public void open(int row, int col) {
        int openPoint = xyTo1D(row, col);
        openSitesCount ++;

        openSites[openPoint] = 1;
        connectToOpenPoints(row, col);
    }

    public boolean isOpen(int row, int col) {
        validateIndices(row, col);
        int num = xyTo1D(row, col);
        return (openSites[num] == 1);
    }

    public boolean isFull(int row, int col) {
        return weightedQuickUnionUF.connected(gridSize, xyTo1D(row, col));
    }

    public int numberOfOpenSites() {
        return openSitesCount;
    }

    public boolean percolates() {
        return weightedQuickUnionUF.connected(gridSize, gridSize + 1);
    }

    private int xyTo1D(int row, int col) {
        validateIndices(row, col);
        return ((row * width - width) + col) - 1;
    }

    private void connectToOpenPoints(int row, int col) {
        validateIndices(row, col);
        int index = xyTo1D(row, col);
        this.setIndex(index);

        if (col < width) attemptUnion(row, col + 1);

        if (col > 1) attemptUnion(row, col - 1);

        if (row < width) {
            attemptUnion(row + 1, col);
        } else {
            gridUnion(gridSize + 1);
        }

        if (row > 1) {
            attemptUnion(row - 1, col);
        } else {
            gridUnion(gridSize);
        }
    }

    private void gridUnion(int gridSize) {
        int index = this.getIndex();
        weightedQuickUnionUF.union(index, gridSize);
    }

    private void attemptUnion(int row, int col) {
        validateIndices(row, col);
        int index = this.getIndex();

        if (isOpen(row, col)) {
            weightedQuickUnionUF.union(xyTo1D(row, col), index);
        }
    }

    private void validateIndices(int row, int col) {
        if (row <= 0 || row > width) {
            throw new java.lang.IllegalArgumentException("row index i out of bounds");
        }
        if (col <= 0 || col > width) {
            throw new java.lang.IllegalArgumentException("col index i out of bounds");
        }
    }
}
