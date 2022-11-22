import java.util.*;

public class RockPaperScissors2 {
    public static void main(String[] args) { //updated bot, higher win rate
        int[][] frequencies = new int[3][4];
        for(int[] a : frequencies) {
            Arrays.fill(a, 2);
            a[3] = 6;
        }

        Scanner s = new Scanner(System.in);
        int input;
        int last = -1;
        double player = 0;
        double computer = 0;
        double r; int row;
        int decision;
        double[] results = new double[] {0.5, 0, 1, 1, 0.5, 0, 0, 1, 0.5};
        for(int i = 0; i < 20; ++i) {
            if(last == -1) {
                row = 0;
            } else {
                row = last;
            }
            r = Math.random() * frequencies[row][3];
            if(r < frequencies[row][0]) {
                decision = 1;
            } else if(r < frequencies[row][0] + frequencies[row][1]) {
                decision = 2;
            } else {
                decision = 0;
            }

            while(true) {
                System.out.print("Rock (0), paper (1), scissors (2): ");
                input = Integer.parseInt(s.next());
                if(input >= 0 && input <= 2) {
                    break;
                }
                System.out.println("Invalid output, please try again.");
            }

            if(last != -1) {
                frequencies[last][input]++;
                frequencies[last][3]++;
            }
            last = input;

            player += results[input * 3 + decision];
            computer += (1 - results[input * 3 + decision]);
        }
        System.out.println(player + " " + computer);
        System.out.println(Arrays.deepToString(frequencies));
    }
}
