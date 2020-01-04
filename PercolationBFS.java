/**
 * Simulate percolation thresholds using a breadth-first-search technique
 * to determine if the top of a grid is connected to the bottom of a grid.
 *
 * @author Isabella Wang
 * @Version Nov. 29, 2019
 */

import java.util.*;
public class PercolationBFS extends PercolationDFSFast{
    /**
     * Initialize a grid with blocked cells.
     *
     * @param n is the size of the grid
     */
    public PercolationBFS (int n)
    {
        super(n);
    }

    /**
     * Private helper method to mark all cells that are open and reachable from
     * (row,col).
     *
     * @param row
     *            is the row coordinate of the cell being checked/marked
     * @param col
     *            is the col coordinate of the cell being checked/marked
     * @throw IndexOutOfBoundsException if index is out of bounds
     */
    @Override
    protected void dfs(int row, int col) {
        if (! inBounds(row,col)) {
            throw new IndexOutOfBoundsException(
                    String.format("(%d,%d) not in bounds", row,col));
        }

        if (isFull(row, col) || !isOpen(row, col)) {
            return;
        }

        Queue<Integer> myQueue = new LinkedList<>();

        myGrid[row][col] = FULL;
        myQueue.add(row*myGrid.length + col);
        while (myQueue.size() != 0){
            int cell = myQueue.remove();
            int rowD = cell/myGrid.length;
            int colD = cell%myGrid.length;
            int[] changeRowForNeighbors = {-1,1,0,0};
            int[] changeColForNeighbors = {0,0,-1,1};
            for(int k=0; k < changeRowForNeighbors.length; k++){
                row = rowD + changeRowForNeighbors[k];
                col = colD + changeColForNeighbors[k];

                if (row >= 0 && row < myGrid.length && col >= 0 && col < myGrid[row].length){
                    if (myGrid[row][col] == OPEN && myGrid[row][col] != FULL) {
                        myGrid[row][col] = FULL;
                        myQueue.add(row*myGrid.length + col);
                    }
                }
            }
        }
    }
}
