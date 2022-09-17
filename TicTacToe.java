import java.util.Scanner;

public class TicTacToe2 {
    static int x;
    public static void main(String[] args) {
        int[][] board = new int[3][3];

        Scanner s = new Scanner(System.in);
        System.out.println("X or O?");
        boolean player;
        String input = s.next();
        if(input.equalsIgnoreCase("X")) {
            player = true;
            x = 0;
        } else if(input.equalsIgnoreCase("O")) {
            player = false;
            x = 1;
        } else {
            System.out.println("Invalid input");
            s.close();
            return;
        }

        while(true) {
            if(player) {
                int x, y;
                while(true) {
                    System.out.println("Enter coordinates of your choice (x, y).");
                	x = s.nextInt();
                    y = s.nextInt();
                    if(board[y][x] == 0) {
                    	board[y][x] = -1;
                    	break;
                    } else {
                    	System.out.println("This coordinate is already taken.");
                    }
                }
                
            } else {
                int[] bestMove = findBestMove(board);
                board[bestMove[1]][bestMove[0]] = 1;
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

            player = !player;
        }
        s.close();
    }

    private static int[] findBestMove(int[][] board) {
        int best = Integer.MIN_VALUE;
        int bestX = -1;
        int bestY = -1;

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 3; ++j) {
                if(board[i][j] == 0) {
                    board[i][j] = 1;
                    int eval = minimax(board, 0, false);
                    board[i][j] = 0;
                    if(eval > best) {
                        best = eval;
                        bestX = j;
                        bestY = i;
                    }
                }
            }
        }

        System.out.println(bestX + " " + bestY + " " + best);
        return new int[] {bestX, bestY, best};
    }

    private static int minimax(int[][] board, int depth, boolean isAI) {
        int score = eval(board);
        if(score != 0) {
            return score;
        }

        if(isFull(board)) {
            return 0;
        }

        if(isAI) {
			int best = Integer.MIN_VALUE;
			for(int i = 0; i < 3; ++i) {
				for(int j = 0; j < 3; ++j) {
					if(board[i][j] == 0) {
						board[i][j] = 1;
						best = Math.max(minimax(board, depth + 1, false), best);
						board[i][j] = 0;
					}
				}
			}
			return best - depth;
		} else {
			int best = Integer.MAX_VALUE;
			for(int i = 0; i < 3; ++i) {
				for(int j = 0; j < 3; ++j) {
					if(board[i][j] == 0) {
						board[i][j] = -1;
						best = Math.min(minimax(board, depth + 1, true), best);
						board[i][j] = 0;
					}
				}
			}
			return best + depth;
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
        char[] symbols;
        if(x == 0) {
            symbols = new char[] {'X', ' ', 'O'};
        } else {
            symbols = new char[] {'O', ' ', 'X'};
        }

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
