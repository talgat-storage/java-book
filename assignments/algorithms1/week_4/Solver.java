import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;
import java.util.List;

public class Solver {
    private Node lastNode = null;

    private class Node implements Comparable<Node> {
        public Board board;
        public int moves;
        public Node prev;
        public int manhattan;

        public Node(Board board) {
            this.board = board;
            this.moves = 0;
            this.prev = null;
            this.manhattan = -1;
        }

        public int compareTo(Node that) {
            if (this.manhattan == -1) {
                this.manhattan = this.board.manhattan();
            }
            if (that.manhattan == -1) {
                that.manhattan = that.board.manhattan();
            }
            return this.moves + this.manhattan - that.moves - that.manhattan;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        MinPQ<Node> pq1 = new MinPQ<>();
        MinPQ<Node> pq2 = new MinPQ<>();
        pq1.insert(new Node(initial));
        pq2.insert(new Node(initial.twin()));

        while (!pq1.min().board.isGoal() && !pq2.min().board.isGoal()) {
            replaceWithNeighbors(pq1);
            replaceWithNeighbors(pq2);
        }

        if (pq1.min().board.isGoal()) {
            lastNode = pq1.delMin();
        }
    }

    private void replaceWithNeighbors(MinPQ<Node> pq) {
        Node minNode = pq.delMin();
        for (Board neighbor : minNode.board.neighbors()) {
            if (minNode.prev != null && neighbor.equals(minNode.prev.board)) {
                continue;
            }
            Node nextNode = new Node(neighbor);
            nextNode.moves = minNode.moves + 1;
            nextNode.prev = minNode;
            pq.insert(nextNode);
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return lastNode != null;
    }

    // min number of moves to solve initial board
    public int moves() {
        if (lastNode == null) {
            return -1;
        }
        return lastNode.moves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        if (lastNode == null) {
            return null;
        }

        List<Board> seq = new LinkedList<>();

        Node node = lastNode;
        while (node != null) {
            seq.add(0, node.board);
            node = node.prev;
        }

        return seq;
    }

    // test client (see below)
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
