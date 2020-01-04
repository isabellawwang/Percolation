/**
 * Simulate percolation threshold using a depth-first-search to determine if the
 *  top of the a grid is connected to the bottom of a grid. PercolationDFSFast
 *  is more efficient than PercolationDFS.
 *
 * @author Isabella Wang
 * @Version Nov. 29, 2019
 */

public class PercolationDFSFast extends PercolationDFS{
    /**
     * Initialize a grid with blocked cells.
     *
     * @param n is the size of the grid
     */
    public PercolationDFSFast(int n)
    {
        super(n);
    }

    /**
     * Updates grid when a cell is opened and marks cell as 'FULL' if needed.
     * If cell is marked 'FULL', marks neighboring cells as 'FULL' as well.
     *
     * @param row row index in range [0,N-1]
     * @param col col index in range [0,N-1]
     * @throw IndexOutOfBoundsException if index is out of bounds
     */

    @Override
    public void updateOnOpen(int row, int col)
    {
        boolean shouldBeFull = false;

        if (! inBounds(row,col)) {
            throw new IndexOutOfBoundsException(
                    String.format("(%d,%d) not in bounds", row,col));
        }

        if (row == 0) {
            shouldBeFull = true;
        } else {
            if (myGrid[row-1][col] == FULL) {
                shouldBeFull = true;
            }
            if (row + 1 < myGrid.length && myGrid[row+1][col] == FULL) {
                shouldBeFull = true;
            }
            if (col != 0 && myGrid[row][col-1] == FULL) {
                shouldBeFull = true;
            }
            if (col + 1 < myGrid[row].length && myGrid[row][col+1] == FULL) {
                shouldBeFull = true;
            }
        }

        if (shouldBeFull) {
            dfs(row, col);
        }
    }
}
