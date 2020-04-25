/**
 * @author Davis Jeffrey
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stack;

import java.util.Comparator;

/**
 * --assignment
 * 8-puzzle: https://en.wikipedia.org/wiki/15_puzzle
 * Solver implements A* search to solve n-by-n slider puzzles
 * Property of a board:
 * If a board is solvable, its twin is unsolvable and vice versa
 * <p>
 * Overview:
 * Search node == an obj containing a board, the number of moves made to reach the board, and the previous search node
 * Insert the initial search node (the initial board, 0 moves, and a null previous search node) into a priority queue.
 * Delete from the priority queue the search node with the minimum priority
 * Insert onto the priority queue all neighboring search nodes
 * Repeat this procedure until the search node dequeued corresponds to the goal board.
 */
public class Solver {
    private boolean isSolvable;
    private Stack<Board> solutionIterable;
    private int movesToSolve;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        solve(initial);
    }

    class SearchNode implements Comparable<SearchNode> {
        final SearchNode previousNode;
        final Board board;
        final int moves;

        public SearchNode(Board board, SearchNode previousNode, int moves) {
            this.board = board;
            this.previousNode = previousNode;
            this.moves = moves;
        }

        @Override
        public int compareTo (SearchNode that) { //Needs to be implemented to use in MinPQ
            int thisPriority = this.moves + this.board.manhattan();
            int thatPriority = that.moves + that.board.manhattan();
            if (thisPriority < thatPriority) {
                return -1;
            }
            if (thisPriority > thatPriority) {
                return 1;
            }
            return 0;
        }
    }

    private void solve(Board board) {
        Board twinBoard = board.twin();
        MinPQ<SearchNode> gameTree = new MinPQ<>();
        MinPQ<SearchNode> twinGameTree = new MinPQ<>();
        gameTree.insert(new SearchNode(board, null, 0));
        twinGameTree.insert(new SearchNode(twinBoard, null, 0));
        //Keep iterating till either initial board or twin reaches Goal board
        while (!twinGameTree.min().board.isGoal() && !gameTree.min().board.isGoal()) {
            //Iterate through neighbors and add to game trees
            Iterable<Board> neighborsItr = gameTree.min().board.neighbors();
            Iterable<Board> twinNeighborsItr = twinGameTree.min().board.neighbors();

            //For board
            SearchNode minNode = gameTree.min();
            gameTree.delMin(); //Remove min and add all possible neighbors
            for (Board b : neighborsItr) {
                //Inserting previous board is redundant; add only distinct boards
                if (minNode.previousNode == null || !b.equals(minNode.previousNode.board)) {
                    gameTree.insert(new SearchNode(b, minNode, minNode.moves + 1));
                }
            }

            //For twinBoard
            SearchNode minTwinNode = twinGameTree.min();
            twinGameTree.delMin(); //Remove min and add all possible neighbors
            for (Board b : twinNeighborsItr) {
                //Inserting previous board is redundant; add only distinct boards
                if (minTwinNode.previousNode == null || !b.equals(minTwinNode.previousNode.board)) {
                    twinGameTree.insert(new SearchNode(b, minTwinNode, minTwinNode.moves + 1));
                }
            }
        }

        //Loop is over; meaning either initial board or twin has reached Goal board
        //Initialize isSolvable, moves, solution iterable
        if (gameTree.min().board.isGoal()) {
            isSolvable = true;
            SearchNode node = gameTree.min();
            movesToSolve = node.moves;
            this.solutionIterable = new Stack<>();
            //Iterate to get all steps
            while (node != null) {
                solutionIterable.push(node.board);
                node = node.previousNode;
            }
        }
        if (twinGameTree.min().board.isGoal()) {
            isSolvable = false;
        }
    }

    //Get a twin board, try to solve both this Board and twin in parallel
    //If twin is solved, this Board is unsolvable.
    public boolean isSolvable() {
        return isSolvable;
    }

    //Min number of moves to solve initial board
    public int moves() {
        if (!isSolvable()) {
            throw new RuntimeException("Board cannot be solved");
        }
        return movesToSolve;
    }

    //Sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            throw new RuntimeException("Board cannot be solved");
        }
        return solutionIterable;
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
