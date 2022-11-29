import java.util.Arrays;
import java.util.Locale;

public class Chess {
    static Piece[][] board = new Piece[8][8];
    private static class Piece {
        String name;
        int points;
        int x, y;
        char side;

        public Piece(String name, int points, int x, int y, char side) {
            this.name = name + ((char) (97 + x));
            this.points = points;
            this.x = x;
            this.y = y;
            this.side = side;
        }
    }

    private static class Pawn extends Piece {
        int start;
        public Pawn(int x, int y, char side) {
            super("p", 1, x, y, side);
            this.start = y;
        }

        boolean move(int x, int y) {
            if(Math.abs(x - this.x) == 1 && Math.abs(y - this.y) && )
            if(this.y == start) {

            }
        }
    }

    private static class Knight extends Piece {
        public Knight(int x, int y, char side) {
            super("n", 3, x, y, side);
        }
    }

    private static class Bishop extends Piece {
        public Bishop(int x, int y, char side) {
            super("b", 3, x, y, side);
        }
    }

    private static class Rook extends Piece {
        public Rook(int x, int y, char side) {
            super("r", 5, x, y, side);
        }
    }

    private static class Queen extends Piece {
        public Queen(int x, int y, char side) {
            super("q", 9, x, y, side);
        }
    }

    private static class King extends Piece {
        public King(int x, int y, char side) {
            super("k", 1000, x, y, side);
        }
    }

    public static void main(String[] args) {
        setup(7, -1, 'w');
        setup(0, 1, 'b');
        print();
    }

    private static void setup(int n, int d, char side) {
        for(int i = 0; i < board.length; ++i) {
            board[n + d][i] = new Pawn(i, n + d, side);
        }
        board[n][0] = new Rook(0, n, side);
        board[n][7] = new Rook(7, n, side);
        board[n][1] = new Knight(1, n, side);
        board[n][6] = new Knight(6, n, side);
        board[n][2] = new Bishop(2, n, side);
        board[n][5] = new Bishop(5, n, side);
        board[n][3] = new Queen(3, n, side);
        board[n][4] = new King(4, n, side);
    }

    private static void print() {
        Piece p;
        for(int i = 0; i < board.length; ++i) {
            for(int j = 0; j < board.length; ++j) {
                p = board[i][j];
                if(p == null) {
                    System.out.print("__");
                } else {
                    if(p.side == 'w') {
                        System.out.print(p.name.toUpperCase());
                    } else {
                        System.out.print(p.name);
                    }
                }
            }
            System.out.println();
        }
    }
}
