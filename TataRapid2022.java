import java.util.*;

public class TataRapid2022 { //2022 Tata Steel Rapid/Blitz
	
	static class Player {
		String name;
		int[] elos; //rapid, blitz
		int score;
		int[] results = new int[10];
		double expectedSum;
		
		public Player(String name, int rElo, int bElo) {
			this.name = name;
			elos = new int[] {rElo, bElo};
		}
	}
	
	public static void main(String[] args) {
		Player[] players = new Player[] {
				new Player("Hikaru Nakamura", 2795, 2909),
				new Player("Shakhriyar Mamedyarov", 2753, 2734),
				new Player("Wesley So", 2800, 2763),
				new Player("Vidit Santosh Gujrathi", 2661, 2675),
				new Player("Arjun Erigaisi", 2628, 2745),
				new Player("Nodirbek Abdusattorov", 2676, 2665),
				new Player("Gukesh Dommaraju", 2632, 2632),
				new Player("Praggnanandhaa Rameshbabu", 2587, 2599),
				new Player("Nihal Sarin", 2626, 2705),
				new Player("S.P. Sethuraman", 2545, 2545)
		};
		
		HashMap<String, Integer> wins = new HashMap<String, Integer>();
		HashMap<String, Integer> pointsScored = new HashMap<String, Integer>();
		for(Player p : players) {
			wins.put(p.name, 0);
			pointsScored.put(p.name, 0);
		}
		
		Player[] result;
		for(int i = 0; i < 1000000; ++i) {
			result = tournament(players, 0);
			wins.put(result[0].name, wins.get(result[0].name) + 1);
			
			for(Player p : result) {
				pointsScored.put(p.name, pointsScored.get(p.name) + p.score);
			}
		}
		
		for(Map.Entry<String, Integer> e : wins.entrySet()) {
			System.out.println(e.getKey() + "\t" + e.getValue());
		}
		for(Map.Entry<String, Integer> e : pointsScored.entrySet()) {
			System.out.println(e.getKey() + "\t" + Math.round(e.getValue()/200000.0)/10.0);
		}
	}
	
	private static Player[] tournament(Player[] players, int form) { //0 = rapid, 1 = blitz
		for(Player p : players) {
			p.score = 0;
			p.expectedSum = 0;
		}
		
		double[] probabilities;
		double actual, matchResult;
		int elo1, elo2;
		for(int i = 0; i < 10; ++i) {
			for(int j = i + 1; j < 10; ++j) {
				elo1 = players[i].elos[form];
				elo2 = players[j].elos[form];
				probabilities = probability(elo1, elo2);
				
				for(int k = 0; k <= form; ++k) { //double round robin for blitz
					players[i].expectedSum += probabilities[0];
					players[j].expectedSum += probabilities[2];
					actual = Math.random();
					if(actual < probabilities[0]) {
						matchResult = 2;
					} else if(actual < probabilities[0] + probabilities[1]) {
						matchResult = 1;
					} else {
						matchResult = 0;
					}
					players[i].score += matchResult;
					players[j].score += (2 - matchResult);
					players[i].results[j] += (int) matchResult;
					players[j].results[i] += (int) (2 - matchResult);
				}
			}
		}
		
		Arrays.sort(players, (a, b) -> {
			if(a.score != b.score) {
				return b.score - a.score;
			} else {
				int sb1 = 0;
				for(int i = 0; i < 10; ++i) {
					sb1 += (a.results[i] * players[i].score);
				}
				
				int sb2 = 0;
				for(int i = 0; i < 10; ++i) {
					sb2 += (b.results[i] * players[i].score);
				}
				return sb2 - sb1;
			}
		});
		return players;
	}
	
	private static double[] probability(int elo1, int elo2) {
		double[] probabilities = new double[3];
		
		//draw (source: https://wismuth.com/elo/calculator.html)
		int diff = -Math.abs(elo1 - elo2);
		double avg = (elo1 + elo2)/2.0;
		double expected = 1/(1 + Math.pow(10, -diff/400.0));
		double eloPerPawn = 26.59 * Math.pow(Math.E, avg/1020.0);
		double eloShift = 0.6 * eloPerPawn;
		
		double lowerRatedProbability = 1/(1 + Math.pow(10, -(diff - eloShift)/400.0));
		probabilities[1] = (expected - lowerRatedProbability) * 2;
		
		//player1
		expected = 1/(1 + Math.pow(10, (elo2 - elo1)/400.0));
		probabilities[0] = expected - 0.5 * probabilities[1];
		
		//player2
		expected = 1/(1 + Math.pow(10, (elo1 - elo2)/400.0));
		probabilities[2] = expected - 0.5 * probabilities[1];
		
		return probabilities;
	}
}