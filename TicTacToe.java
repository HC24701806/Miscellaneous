import java.util.Scanner;

public class TicTacToe {

    public static void main(String[] args) {
        int[][] board = new int[3][3];

        Scanner s = new Scanner(System.in);
        System.out.println("Player 1 or 2? (Note: X is always player, O is always computer");
        int player;
        if(s.nextInt() == 1) {
            player = 0;
        } else {
            player = 1;
        }

        while(true) {
            if(player == 0) {
                System.out.println("Enter coordinates of your choice (x, y).");
                int x = s.nextInt();
                int y = s.nextInt();
                board[y][x] = 1;
            } else {
                int[] bestMove = solver(board);
                board[bestMove[1]][bestMove[0]] = -1;
            }
            print(board);

            int state = eval(board);
            if(state == 10) {
                System.out.println("You win!");
                break;
            } else if(state == -10) {
                System.out.println("Computer wins");
                break;
            } else if(isFull(board)) {
                System.out.println("Draw");
                break;
            }

            player = 1 - player;
        }
    }

    private static int[] solver(int[][] board) {
        int best = Integer.MAX_VALUE;
        int bestX = -1;
        int bestY = -1;

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 3; ++j) {
                if(board[i][j] == 0) {
                    board[i][j] = -1;
                    int eval = minimax(board, 0, true);
                    board[i][j] = 0;
                    if(eval < best) {
                        best = eval;
                        bestX = j;
                        bestY = i;
                    }
                }
            }
        }

        return new int[] {bestX, bestY, best};
    }

    private static int minimax(int[][] board, int depth, boolean player) {
        int score = 0;
        if(eval(board) != 0) {
            return score;
        }

        if(isFull(board)) {
            return 0;
        }

        if(player) { //player
            int best = 1000;
            for(int i = 0; i < 3; ++i) {
                for(int j = 0; j < 3; ++j) {
                    if(board[i][j] == 0) {
                        board[i][j] = 1;
                        best = Math.min(best, minimax(board, depth + 1, false));
                        board[i][j] = 0;
                    }
                }
            }
            return best + depth;
        } else { //ai
            int best = -1000;
            for(int i = 0; i < 3; ++i) {
                for(int j = 0; j < 3; ++j) {
                    if(board[i][j] == 0) {
                        board[i][j] = -1;
                        best = Math.max(best, minimax(board, depth + 1, true));
                        board[i][j] = 0;
                    }
                }
            }
            return best - depth;
        }
    }

    private static int eval(int[][] board) {
        int sum = 0;
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 3; ++j) {
                sum += board[i][j];
            }
            if(Math.abs(sum) == 3) {
                return sum/3 * 10;
            }
            sum = 0;
        }

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 3; ++j) {
                sum += board[j][i];
            }
            if(Math.abs(sum) == 3) {
                return sum/3 * 10;
            }
            sum = 0;
        }

        for(int i = 0; i < 3; ++i) {
            sum += board[i][i];
        }
        if(Math.abs(sum) == 3) {
            return sum/3 * 10;
        }
        sum = 0;

        for(int i = 0; i < 3; ++i) {
            sum += board[i][2 - i];
        }
        if(Math.abs(sum) == 3) {
            return sum/3 * 10;
        }
        sum = 0;

        return 0;
    }

    private static boolean isFull(int[][] board) {
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 3; ++j) {
                if(board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void print(int[][] board) {
        char[] symbols = new char[] {'O', ' ', 'X'};
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 2; ++j) {
                System.out.print(symbols[board[i][j] + 1] + " | ");
            }

            if(i < 2) {
                System.out.println(symbols[board[i][2] + 1] + "\n----------");
            } else {
                System.out.println(symbols[board[i][2] + 1]);
            }
        }
        System.out.println("\n");
    }
}
