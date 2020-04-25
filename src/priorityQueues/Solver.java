/**
 * @author Davis Jeffrey
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * --assignment
 * 8-puzzle: https://en.wikipedia.org/wiki/15_puzzle
 * Solver implements A* search to solve n-by-n slider puzzles
 *
 * Overview:
 * Search node == (a board, the number of moves made to reach the board, and the previous search node).
 * Insert the initial search node (the initial board, 0 moves, and a null previous search node) into a priority queue.
 * Delete from the priority queue the search node with the minimum priority
 * Insert onto the priority queue all neighboring search nodes
 * Repeat this procedure until the search node dequeued corresponds to the goal board.
 */
public class Solver {
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return true;
    }

    // min number of moves to solve initial board
    public int moves() {
        return 0;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        return null;
    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
