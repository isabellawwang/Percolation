/**
 * Simulates percolation thresholds for a grid-base system using a Union Find algorithm
 * for determining if the top of a grid is connected to the bottom of a grid.
 *
 * @author Isabella Wang
 * @Version Nov. 29, 2019
 */

public class PercolationUF implements IPercolate{
    private boolean[][] myGrid;
    private int myOpenCount;
    private IUnionFind myFinder;
    private final int VTOP;
    private final int VBOTTOM;

    /**
     * Initializes a size*size grid so that a union find algorithm can be implemented
     *
     * @param finder specified implementation of IUnionFind
     * @param size number of rows/columns of the grid
     */
    public PercolationUF(IUnionFind finder, int size)
    {
        myGrid = new boolean[size][size];
        finder.initialize(size*size + 2);
        myFinder = finder;
        VTOP = size*size;
        VBOTTOM = size*size + 1;
    }

    /**
     * Open site (row, col) if it is not already open. By convention, (0, 0)
     * is the upper-left site
     *
     * The method modifies internal state so that determining if percolation
     * occurs could change after taking a step in the simulation.
     *
     * @param row
     *            row index in range [0,N-1]
     * @param col
     *            column index in range [0,N-1]
     * @throw IndexOutOfBoundsException if index is out of bounds
     */
    @Override
    public void open(int row, int col) {
        if (!inBounds(row, col)) {
            throw new IndexOutOfBoundsException(
                    String.format("(%d,%d) not in bounds", row, col));
        }
        if (isOpen(row, col)) {
            return;
        }
        myGrid[row][col] = true;
        myOpenCount++;
        int[] changeRowForNeighbors = {-1,1,0,0};
        int[] changeColForNeighbors = {0,0,-1,1};
        for (int i=0;i<changeRowForNeighbors.length;i++)
        {
            int neighRow = row + changeRowForNeighbors[i];
            int neighCol = col + changeColForNeighbors[i];
            if (inBounds(neighRow, neighCol) && myGrid[neighRow][neighCol])
            {
                myFinder.union(row*myGrid.length+col, neighRow*myGrid.length+neighCol);
            }
            else if (neighRow<0)
            {
                myFinder.union(row*myGrid.length+col, VTOP);
            }
            else if (neighRow>=myGrid.length)
            {
                myFinder.union(row*myGrid.length+col, VBOTTOM);
            }
        }
    }

    /**
     * Returns true if and only if site (row, col) is OPEN
     *
     * @param row
     *            row index in range [0,N-1]
     * @param col
     *            column index in range [0,N-1]
     * @return true if the cell is OPEN, false otherwise
     * @throw IndexOutOfBoundsException if index is out of bounds
     */
    @Override
    public boolean isOpen(int row, int col)
    {
        if (! inBounds(row,col)) {
            throw new IndexOutOfBoundsException(
                    String.format("(%d,%d) not in bounds", row,col));
        }
        return myGrid[row][col];
    }

    /**
     * Returns true if and only if site (row, col) is FULL
     *
     * @param row
     *            row index in range [0,N-1]
     * @param col
     *            column index in range [0,N-1]
     * @return true if cell is FULL, false otherwise
     * @throw IndexOutOfBoundsException if index is out of bounds
     */
    @Override
    public boolean isFull(int row, int col)
    {
        if (! inBounds(row,col)) {
            throw new IndexOutOfBoundsException(
                    String.format("(%d,%d) not in bounds", row,col));
        }
        return myFinder.connected(row*myGrid.length+col, VTOP);
    }

    /**
     * Returns true if the simulated percolation actually percolates. What it
     * means to percolate could depend on the system being simulated, but
     * returning true typically means there's a connected path from
     * top-to-bottom.
     *
     * @return true if the simulated system percolates
     */
    @Override
    public boolean percolates()
    {
        return myFinder.connected(VTOP, VBOTTOM);
    }

    /**
     * Returns the number of distinct sites that have been opened in this
     * simulation
     *
     * @return number of open sites
     */
    @Override
    public int numberOfOpenSites()
    {
        return myOpenCount;
    }

    /**
     * Determine if (row,col) is valid for given grid
     * @param row specifies row
     * @param col specifies column
     * @return true if (row,col) on grid, false otherwise
     */
    protected boolean inBounds(int row, int col) {
        if (row < 0 || row >= myGrid.length) return false;
        if (col < 0 || col >= myGrid[0].length) return false;
        return true;
    }

}
