import java.util.Locale;
import java.util.Scanner;

public class RockPaperScissors { //basic bot, low win rate
    //scorekeeping
    static double player = 0;
    static double computer = 0;
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        boolean end = false;
        while(!end) {
            game(s);
            System.out.print("Play again? (y/n) "); //ask whether to play again
            if(!s.next().equals("y")) {
                end = true;
            }
            System.out.println();
        }

        System.out.println(player + "-" + computer);
    }

    private static void game(Scanner s) {
        //decision by accounting for player bias
        int rocks = 3;
        int papers = 3;
        int scissors = 3;

        double r = Math.random() * (rocks + papers + scissors);
        int decision; //0 = rock, 1 = paper, 2 = scissors
        if(r < rocks) {
            decision = 1;
        } else if(r < (rocks + papers)) {
            decision = 2;
        } else {
            decision = 0;
        }

        //ask for user input
        String input;
        int playerDecision = -1;
        boolean valid = false;
        do {
            System.out.print("Rock, paper, scissors: ");
            input = s.next().toLowerCase();
            if(input.equals("rock")) {
                playerDecision = 0;
                valid = true;
            } else if(input.equals("paper")) {
                playerDecision = 1;
                valid = true;
            } else if(input.equals("scissors")) {
                playerDecision = 2;
                valid = true;
            } else {
                System.out.println("Invalid input, please try again.");
            }
        } while(!valid);

        //determine result
        String results = "tlwwtllwt";
        char result = results.charAt(playerDecision * 3 + decision);
        if(result == 'w') {
            System.out.println("You win");
            player++;
        } else if(result == 't') {
            System.out.println("tie");
            player += 0.5;
            computer += 0.5;
        } else {
            System.out.println("Computer wins");
            computer++;
        }
    }
}