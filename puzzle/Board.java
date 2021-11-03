import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;


public class Board {
    private final int n;
    private int[][] tiles;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.n = tiles.length;
        this.tiles = new int[this.n][this.n];

        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }

    }

    // string representation of this board
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.n);
        for (int i = 0; i < this.n; i++) {
            stringBuilder.append("\n");
            for (int j = 0; j < this.n; j++) {
                stringBuilder.append(" " + this.tiles[i][j]);
            }
        }
        return stringBuilder.toString();
    }

    // board dimension n
    public int dimension() {
        return this.n;
    }

    // number of tiles out of place
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if (this.tiles[i][j] != 0 && this.tiles[i][j] != (i * this.n + j + 1))
                    hamming++;
            }
        }
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattan = 0;
        int xV, yV;
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if (this.tiles[i][j] != 0) {
                    xV = j - (this.tiles[i][j] - 1) % this.n;
                    yV = i - (this.tiles[i][j] - 1) / this.n;
                    manhattan += (yV < 0) ? -yV : yV;
                    manhattan += (xV < 0) ? -xV : xV;
                }
            }
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return (manhattan() == 0 && hamming() == 0);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (this.getClass() != y.getClass()) return false;
        Board temp = (Board) y;
        if (this.dimension() != temp.dimension()) return false;

        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if (this.tiles[i][j] != temp.tiles[i][j])
                    return false;
            }
        }
        return true;

    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> list = new ArrayList<>();
        int[][] temp = new int[this.n][this.n];
        int indexX = 0, indexY = 0;
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                temp[i][j] = this.tiles[i][j];
                if (temp[i][j] == 0) {
                    indexX = i;
                    indexY = j;
                }
            }
        }
        int newTemp = 0;
        if (indexX > 0) {
            newTemp = temp[indexX][indexY];
            temp[indexX][indexY] = temp[indexX - 1][indexY];
            temp[indexX - 1][indexY] = newTemp;
            Board board = new Board(temp);
            list.add(board);
            temp = this.tiles;
        }
        if (indexX < this.n - 1) {
            newTemp = temp[indexX][indexY];
            temp[indexX][indexY] = temp[indexX + 1][indexY];
            temp[indexX + 1][indexY] = newTemp;
            Board board = new Board(temp);
            list.add(board);
            temp = this.tiles;
        }
        if (indexY > 0) {
            newTemp = temp[indexX][indexY];
            temp[indexX][indexY] = temp[indexX][indexY - 1];
            temp[indexX][indexY - 1] = newTemp;
            Board board = new Board(temp);
            list.add(board);
            temp = this.tiles;
        }
        if (indexY < this.n - 1) {
            newTemp = temp[indexX][indexY];
            temp[indexX][indexY] = temp[indexX][indexY + 1];
            temp[indexX][indexY + 1] = newTemp;
            Board board = new Board(temp);
            list.add(board);
            temp = this.tiles;
        }
        return list;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] twinTiles = new int[this.n][this.n];
        for (int i = 0; i < this.n; i++) {
            twinTiles[i] = this.tiles[i].clone();
        }
        int row = 0;
        if (twinTiles[0][0] == 0 || twinTiles[0][1] == 0)
            row = 1;
        int tmp = twinTiles[row][0];
        twinTiles[row][0] = twinTiles[row][1];
        twinTiles[row][1] = tmp;

        return new Board(twinTiles);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] arr = {{8, 2, 3}, {4, 5, 6}, {7, 1, 0}};
        Board b = new Board(arr);
        StdOut.println("=== SOURCE ===");
        StdOut.println(b.toString());

        Iterable<Board> neighbours = b.neighbors();
        for (Board n : neighbours) {
            StdOut.println(n.toString());
        }
    }
}