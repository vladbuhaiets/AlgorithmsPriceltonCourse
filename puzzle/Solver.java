
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;

public class Solver {
    private class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final int manhattan;
        private int moves;
        private final int priority;
        private SearchNode previousNode;

        public SearchNode(Board board, SearchNode previousNode) {
            this.board = board;
            this.previousNode = previousNode;
            this.manhattan = board.manhattan();
            if (previousNode == null)
                this.moves = 0;
            else
                this.moves = previousNode.moves + 1;
            this.priority = this.moves + this.manhattan;
        }

        public void insertNewNeighbours(MinPQ<SearchNode> queue) {
            for (Board board : board.neighbors()) {
                if (previousNode != null && board.equals(this.previousNode.board))
                    continue;

                SearchNode newNode = new SearchNode(board, this);
                queue.insert(newNode);
            }
        }

        public int compareTo(SearchNode compared) {
            if (this.priority > compared.priority)
                return 1;
            if (this.priority < compared.priority)
                return -1;
            else
                return 0;
        }
    }

    private SearchNode solutionNode;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException();

        MinPQ<SearchNode> nodes = new MinPQ<>();
        SearchNode initialNode = new SearchNode(initial, null);
        nodes.insert(initialNode);

        MinPQ<SearchNode> twinNodes = new MinPQ<>();
        SearchNode twinInitialNode = new SearchNode(initial.twin(), null);
        twinNodes.insert(twinInitialNode);

        while (true) {
            SearchNode node = nodes.delMin();
            SearchNode twinNode = twinNodes.delMin();

            if (node.board.isGoal()) {
                this.solutionNode = node;
                break;
            }
            if (twinNode.board.isGoal()) {
                this.solutionNode = null;
                break;
            }

            node.insertNewNeighbours(nodes);
            twinNode.insertNewNeighbours(twinNodes);
        }

    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solutionNode != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isSolvable())
            return this.solutionNode.moves;
        else
            return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (isSolvable()) {
            LinkedList<Board> solution = new LinkedList<>();
            SearchNode node = this.solutionNode;

            while (node != null) {
                solution.addFirst(node.board);
                node = node.previousNode;
            }
            return solution;
        } else
            return null;
    }

    // test client (see below)
    public static void main(String[] args) {
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