import java.io.*;
import java.util.Arrays;
import java.util.Map.Entry;
import java.util.TreeMap;

public class WorldCup {
	static Country[] countries;
	static TreeMap<String, Integer> placements = new TreeMap<String, Integer>();
	private static class Country {
		String country;
		int elo;
		int[] results = new int[7];
		
		public Country(String country, int elo) {
			this.country = country;
			this.elo = elo;
		}
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		BufferedReader in = new BufferedReader(new FileReader("worldcup.txt"));
		countries = new Country[32];
		for(int i = 0; i < 32; ++i) {
			String[] input;
			input = in.readLine().split("  ");
			countries[i] = new Country(input[0], Integer.parseInt(input[1]));
		}
		in.close();
		
		TreeMap<String, Integer> winners = new TreeMap<String, Integer>();
		for(Country c : countries) {
			winners.put(c.country, 0);
		}
		String winner;
		for(int i = 0; i < 1000000; ++i) {
			winner = simulate();
			winners.put(winner, winners.get(winner) + 1);
		}
		
		for(Entry<String, Integer> e : winners.entrySet()) {
			System.out.println(e.getKey() + "\t" + e.getValue());
		}
		
		for(Country c : countries) {
			System.out.print(c.country + "\t");
			for(int data : c.results) {
				System.out.print(data + "\t");
			}
			System.out.println();
		}
		
		for(Entry<String, Integer> e: placements.entrySet()) {
			System.out.println(e.getKey() + "\t" + e.getValue());
		}
	}
	
	private static String simulate() {
		Country[] group = new Country[4];
		Country[] g16 = new Country[16];
		for(int i = 0; i < 8; ++i) {
			int[] groupResults = new int[] {0, 1, 2, 3};
			for(int j = 0; j < 4; ++j) {
				group[j] = countries[i * 4 + j];
			}
			
			for(int j = 0; j < 4; ++j) {
				for(int k = j + 1; k < 4; ++k) {
					int result = resultWithDraw(group[j], group[k]);
					if(result == 1) {
						groupResults[j] += 30;
					} else if(result == -1) {
						groupResults[k] += 30;
					} else {
						groupResults[j] += 10;
						groupResults[k] += 10;
					}
				}
			}
			Arrays.sort(groupResults);
			g16[2 * i] = group[groupResults[3] % 10];
			g16[2 * i + 1] = group[groupResults[2] % 10];
			group[groupResults[0] % 10].results[6]++;
			group[groupResults[1] % 10].results[6]++;
		}
		
		//g16
		Country[] quarterfinals = new Country[8];
		for(int i = 0; i < 4; ++i) {
			int result = result(g16[i * 4], g16[i * 4 + 3]);
			if(result == 1) {
				quarterfinals[2 * i] = g16[i * 4];
				g16[i * 4 + 3].results[5]++;
			} else {
				quarterfinals[2 * i] = g16[i * 4 + 3];
				g16[i * 4].results[5]++;
			}
			
			result = result(g16[i * 4 + 1], g16[i * 4 + 2]);
			if(result == 1) {
				quarterfinals[2 * i + 1] = g16[i * 4 + 1];
				g16[i * 4 + 2].results[5]++;
			} else {
				quarterfinals[2 * i + 1] = g16[i * 4 + 2];
				g16[i * 4 + 1].results[5]++;
			}
		}
		
		//quarterfinals
		Country[] semifinals = new Country[4];
		for(int i = 0; i < 8; i += 4) {
			int result = result(quarterfinals[i], quarterfinals[i + 2]);
			if(result == 1) {
				semifinals[i/4] = quarterfinals[i];
				quarterfinals[i + 2].results[4]++;
			} else {
				semifinals[i/4] = quarterfinals[i + 2];
				quarterfinals[i].results[4]++;
			}
		}
		for(int i = 1; i < 8; i += 4) {
			int result = result(quarterfinals[i], quarterfinals[i + 2]);
			if(result == 1) {
				semifinals[i/4 + 2] = quarterfinals[i];
				quarterfinals[i + 2].results[4]++;
			} else {
				semifinals[i/4 + 2] = quarterfinals[i + 2];
				quarterfinals[i].results[4]++;
			}
		}
		
		//semifinals
		Country[] finals = new Country[2];
		Country[] consolation = new Country[2];
		int result = result(semifinals[0], semifinals[2]);
		if(result == 1) {
			finals[0] = semifinals[0];
			consolation[0] = semifinals[2];
		} else {
			finals[0] = semifinals[2];
			consolation[0] = semifinals[0];
		}
		
		result = result(semifinals[1], semifinals[3]);
		if(result == 1) {
			finals[1] = semifinals[1];
			consolation[1] = semifinals[3];
		} else {
			finals[1] = semifinals[3];
			consolation[1] = semifinals[1];
		}
		
		//third place match
		String[] nextTwo = new String[2];
		result = result(consolation[0], consolation[1]);
		if(result == 1) {
			nextTwo[0] = consolation[0].country;
			consolation[0].results[2]++;
			nextTwo[1] = consolation[1].country;
			consolation[1].results[3]++;
		} else {
			nextTwo[0] = consolation[1].country;
			consolation[1].results[2]++;
			nextTwo[1] = consolation[0].country;
			consolation[0].results[3]++;
		}
		
		//finals
		String[] topTwo = new String[2];
		result = result(finals[0], finals[1]);
		if(result == 1) {
			topTwo[0] = finals[0].country;
			finals[0].results[0]++;
			topTwo[1] = finals[1].country;
			finals[1].results[1]++;
		} else {
			topTwo[0] = finals[1].country;
			finals[1].results[0]++;
			topTwo[1] = finals[0].country;
			finals[0].results[1]++;
		}
		
		String finalPlacement = topTwo[0] + "\t" + topTwo[1] + "\t" + nextTwo[0] + "\t" + nextTwo[1];
		if(!placements.containsKey(finalPlacement)) {
			placements.put(finalPlacement, 1);
		} else {
			placements.put(finalPlacement, placements.get(finalPlacement) + 1);
		}
		return topTwo[0];
	}
	
	private static int result(Country c1, Country c2) {
		double expected = 1/(Math.pow(10, (c2.elo - c1.elo)/400.0) + 1);
		double actual = Math.random();
		if(actual < expected) {
			return 1;
		}
		return -1;
	}
	
	private static int resultWithDraw(Country c1, Country c2) {
		double expected = 1/(Math.pow(10, (c2.elo - c1.elo)/400.0) + 1);
		double actual = Math.random();
		double lower = Math.min(expected, 1 - expected);
		if(actual < expected - lower/3.0) {
			return 1;
		} else if(actual > expected + lower/3.0) {
			return -1;
		}
		return 0;
	}
}