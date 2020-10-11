import java.util.Stack;

public class Board {

    private int[][] tiles;
    private final int n;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles)
    {
        this.tiles = copy(tiles);
        n = tiles.length;
    }

    // helper function that copies the user input tiles
    // to the class property tiles
    private int[][] copy(int[][] tiles)
    {
        int [][] c = new int[tiles.length][tiles.length];
        for (int i = 0; i < tiles.length; i++)
        {
            for (int j = 0; j < tiles.length; j++)
            {
                c[i][j] = tiles[i][j];
            }
        }
        return c;
    }

    // string representation of this board
    public String toString()
    {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(n).append("\n");
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                strBuilder.append(String.valueOf(this.tiles[i][j])).append(" ");
            }
            strBuilder.append("\n");
        }
        return strBuilder.toString();
    }

    // board dimension n (n=#of rows=#of columns)
    public int dimension()
    {
        return n;
    }

    // number of tiles out of place
    public int hamming()
    {
        int hammingDistance = 0;
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                if (this.tiles[i][j] != 0 && this.tiles[i][j] != j + i*n + 1) {hammingDistance++;}
            }
        }
        return hammingDistance;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan()
    {
        int manhattanDistance = 0;
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                if (this.tiles[i][j] != 0 && this.tiles[i][j] != j + i*n + 1)
                {
                    manhattanDistance += manhattanDistance(i, j, this.tiles[i][j]);
                }
            }
        }
        return manhattanDistance;
    }

    // helper function to calculate the manhattan distance
    private int manhattanDistance(int i, int j, int value)
    {
        int horizontalDistance = Math.abs(--value % n - j);
        int verticalDistance = Math.abs(value / n - i);
        return horizontalDistance + verticalDistance;
    }

    // is this board the goal board?
    public boolean isGoal()
    {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y)
    {
        if (y == this) return true;
        if (y == null) return false;
        if (this.getClass() != y.getClass()) return false;
        Board that = (Board) y;
        if (this.n != that.n) return false;
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                if (this.tiles[i][j] != that.tiles[i][j]) return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors()
    {
        Stack<Board> neighbours = new Stack<>();
        int zero = zeroPosition();
        int i = zero / n;
        int j = zero % n;
        if (i > 0) neighbours.push(new Board(swap(i, j, i-1,j, this.tiles)));
        if (i < n-1) neighbours.push(new Board(swap(i, j, i+1,j, this.tiles)));
        if (j > 0) neighbours.push(new Board(swap(i, j, i, j-1,this.tiles)));
        if (j < n-1) neighbours.push(new Board(swap(i, j, i, j+1, this.tiles)));
        return neighbours;
    }

    // helper function that returns the 0 position
    private int zeroPosition()
    {
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                if (this.tiles[i][j] ==0) return j + i*n;
            }
        }
        return -1;
    }
    // a board that is obtained by exchanging any pair of tiles
    public Board twin()
    {
        int [][] copyTiles = copy(this.tiles);
        if (copyTiles[0][0] != 0 && copyTiles[0][1] != 0)
        {
            return new Board(swap(0, 0, 0, 1, copyTiles));
        }
        else
        {
            return new Board(swap(1, 0, 1, 1, copyTiles));
        }
    }

    // helper function that swaps tiles
    private int[][] swap(int i1, int j1, int i2, int j2, int[][] copy)
    {
        int temp = copy[i1][j1];
        copy[i1][j1] = copy[i2][j2];
        copy[i2][j2] = temp;
        return copy;
    }

    // unit testing (not graded)
    public static void main(String[] args)
    {

    }

}