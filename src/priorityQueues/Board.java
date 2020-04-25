/**
 * @author Davis Jeffrey
 */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

/**
 * --assignment
 * 8-puzzle: https://en.wikipedia.org/wiki/15_puzzle
 * Board is an immutable data type that models an n-by-n board with sliding tiles
 * Constraints: All Board methods in O(n*n) or better in the worst case.
 */
public class Board {
    private int[][] tiles;
    private int n;
    private Deque<Board> neighborsDeueue;
    private int manhattanNum;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        validateTiles(tiles);
        this.tiles = deepClone(tiles);
        n = this.tiles.length;
        manhattanNum = findManhattanNum();
    }

    private void validateTiles(int[][] tiles) {
        if (tiles == null) {
            throw new IllegalArgumentException("Input is null");
        }
        if (tiles.length < 2) {
            throw new IllegalArgumentException("Input is less than 2");
        }
        if (tiles.length != tiles[0].length) {
            throw new IllegalArgumentException("Tiles are jagged, lengths "+  tiles.length + " and " + tiles[0].length + " are present");
        }
        //Ignoring tile value validation
    }

    /**
     * Returns a string composed of n + 1 lines. The first line contains the board size n;
     * Remaining n lines contain the n-by-n grid of tiles in row-major order, using 0 to designate the blank square.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(n);
        for (int i = 0; i < n; i++) {
            sb.append("\n");
            for (int j = 0; j < n; j++) {
                sb.append(tiles[i][j] + " ");
            }
        }
        return sb.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int hammingNum = 0;
        int expectedValue = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != expectedValue) {
                    hammingNum++;
                }
                expectedValue++;
            }
        }
        //Value correction: Last tile should be 0, but will be covered as incorrect in the previous loop
        if (tiles[n - 1][n - 1] == 0) {
            hammingNum--;
        }
        return hammingNum;
    }

    //Returns num of Manhattan distances between tiles and goal. ie., vertical + horizontal displacement
    public int manhattan() {
        return manhattanNum;
    }

    //Finds num of Manhattan distances between tiles and goal. ie., vertical + horizontal displacement
    //Called during Class initialization
    private int findManhattanNum() {
        int manhattanNum = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                //Eg. for a 3*3 board, let
                //Current position of num (4) == [0, 2] == [row, col]
                //Correct position of num (4) == [(num - 1) / n, (num - 1) % n] == [1, 0] == [row2, col2]
                //ManhattanNum = Math.abs(row - row2) + Math.abs(col - col2) == 1 + 2 == 3
                int row2 = (tiles[row][col] - 1) / n;
                int col2 = (tiles[row][col] - 1) % n;
                if (tiles[row][col] == 0) { //Special case for 0
                    row2 = n - 1;
                    col2 = n - 1;
                }
                manhattanNum += Math.abs(row - row2) + Math.abs(col - col2);
            }
        }
        return manhattanNum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0; //If hamming == 0, no tiles are out of place, it's the goal board
    }

    // does this board equal y?
    public boolean equals(Object obj) {
        if (!(obj instanceof Board)) {
            return false;
        }
        if (((Board) obj).dimension() != n) {
            return false;
        }
        int[][] tiles2 = ((Board) obj).tiles;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (tiles2[row][col] != tiles[row][col]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        initNeighboringBoards();
        return neighborsDeueue;
    }

    private void initNeighboringBoards() {
        if (neighborsDeueue != null) { //Cache the value, so it's not recalculated every time
            return;
        }
        neighborsDeueue = new Deque<>();
        int row = 0, col = 0; //Will have position of blank tile
        int[][] tempTiles = deepClone(tiles);
        //Find blank tile
        outerloop:
        for (row = 0; row < n; row++) {
            for (col = 0; col < n; col++) {
                if (tiles[row][col] == 0) {
                    break outerloop;
                }
            }
        }
        assert (tiles[row][col] == 0);
        //Try to move blank tile left
        if (col != 0) {
            swapTiles(tempTiles, row, col, row, col - 1);
            neighborsDeueue.addFirst(new Board(tempTiles));
            //Swap them back to the initial position after creating the board so that tiles arr can be reused
            swapTiles(tempTiles, row, col, row, col - 1);
        }
        //Try to move blank tile right
        if (col != n - 1) {
            swapTiles(tempTiles, row, col, row, col + 1);
            neighborsDeueue.addFirst(new Board(tempTiles));
            swapTiles(tempTiles, row, col, row, col + 1);
        }
        //Try to move blank tile top
        if (row != 0) {
            swapTiles(tempTiles, row, col, row -1, col);
            neighborsDeueue.addFirst(new Board(tempTiles));
            swapTiles(tempTiles, row, col, row - 1, col);
        }
        //Try to move blank tile bottom
        if (row != n - 1) {
            swapTiles(tempTiles, row, col, row + 1, col);
            neighborsDeueue.addFirst(new Board(tempTiles));
            swapTiles(tempTiles, row, col, row + 1, col);
        }
    }

    private void swapTiles(int[][] tiles, int row1, int col1, int row2, int col2) {
        int temp = tiles[row1][col1];
        tiles[row1][col1] = tiles[row2][col2];
        tiles[row2][col2] = temp;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int row1 = 0;
        int row2 = 0;
        int col1 = 0;
        int col2 = 0;
        //Both should not point to the same tile, and neither tiles should be the blank tile
        while ((row1 == row2 && col1 == col2) || (tiles[row1][col1] == 0) || (tiles[row2][col2] == 0)) {
            row1 = StdRandom.uniform(n);
            row2 = StdRandom.uniform(n);
            col1 = StdRandom.uniform(n);
            col2 = StdRandom.uniform(n);
        }
        int[][] tempTiles = deepClone(tiles);
        swapTiles(tempTiles, row1, col1, row2, col2);
        return new Board(tempTiles);
    }

    private int[][] deepClone(int[][] tiles) {
        int[][] clone = new int[tiles.length][tiles.length];
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles.length; col++) {
                clone[row][col] = tiles[row][col];
            }
        }
        return clone;
    }
    // unit testing
    public static void main(String[] args) {
        int[][] tiles2 = {{1, 2, 3},
                          {0, 4, 6},
                          {7, 5, 8}};
        int[][] tiles = {{1, 2, 3},
                         {4, 0, 6},
                         {7, 5, 8}};
        Board board = new Board(tiles);
        Board board2 = new Board(tiles2);
        assert (board.hamming() == 3);
        assert (board.manhattan() == 4); //(2 for 0) + (1 for 5) + (1 for 8) == 4
        assert (!board.isGoal());
        assert (board.equals(new Board(tiles)));
        System.out.println("Board:");
        System.out.println(board);
        System.out.println("Twin:");
        System.out.println(board.twin());
        System.out.println("Neighbors");
        Iterable<Board> itr = board.neighbors();
        for (Board b : itr) {
            System.out.println(b);
        }
    }
}
