package comp110;
import java.io.IOException;

/**
 * This is an early example of what a student would do
 */

public class Scorer {

	public static void main(String[] args) throws IOException {
		
		// In the future this will be handled by the gui and teams will be pre-scraped
		Team homeTeam = Scraper.scrapeTeam(457);
		Team awayTeam = Scraper.scrapeTeam(392);

		double homeScore = calculateScore(homeTeam);
		double awayScore = calculateScore(awayTeam);
		System.out.println(homeTeam.getName() + " : " + homeScore);
		System.out.println(awayTeam.getName() + " : " + awayScore);
		if (homeScore>awayScore) {
			System.out.println(homeTeam.getName() + " wins!");
		} else {
			System.out.println(awayTeam.getName() + " wins!");
		}
	}

	public static double calculateScore(Team team) throws IOException {

		double compositeScore = 0;
		Player[] roster = team.getRoster();
		double avgOver6ft = avgInchesOver6ft(roster);
		int totalDoubleDoubles = totalDoubleDoubles(roster);
		int winScore = winScore(team, 2, -1);
		double scoringMargin = scoringMargin(team);
		int totalAbove12 = totalAbove12(roster);
		int top20Stats = top20Stats(team);

		compositeScore = 0.3*avgOver6ft
				       + 0.5*totalDoubleDoubles 
				       + 0.2*winScore 
				       + 0.5*scoringMargin
				       + 1.0*totalAbove12
				       + 0.8*top20Stats;
		
		return compositeScore;

	}

	public static int top20Stats(Team team) {
		int scoringOffense = team.getScoringOffenseRank();
		int wonLost = team.getWonLostPercentRank();
		int turnoverMargin = team.getTurnoverMarginRank();
		int assistTurnoverRatio = team.getAssistTurnoverRatioRank();
		int avgTotalRebs = team.getAvgReboundsRank();
		
		int top20Count = 0;
		int[] rankings = {scoringOffense, wonLost, turnoverMargin, assistTurnoverRatio, avgTotalRebs};
		for (int i : rankings) {
			if (i <= 20) {
				top20Count++;
			}
		}
		
		return top20Count;
	}

	public static int totalAbove12(Player[] roster) {
		int countAbove12 = 0;
		for (Player p : roster) {
			if (p.getAvgPoints() >= 12.0) {
				countAbove12++;
			}
		}
		return countAbove12;
	}

	public static double scoringMargin(Team team) {
		double offense = team.getScoringOffense();
		double defense = team.getScoringDefense();
		return offense-defense;
	}

	public static int winScore(Team team, int pointsForWin, int pointsForLoss) {
		int wins = team.getWins();
		int losses = team.getLosses();
		return pointsForWin*wins + pointsForLoss*losses;
	}

	public static int totalDoubleDoubles(Player[] roster) {
		int totalDoubleDoubles = 0;
		for (Player p : roster) {
			totalDoubleDoubles += p.getDoubleDoubles();
		}
		return totalDoubleDoubles;
	}

	public static double avgInchesOver6ft(Player[] roster) {
		int totalInches = 0;
		int numPlayers = roster.length;
		for (Player p : roster) {
			totalInches += p.getHeight() - 72;
		}
		return totalInches/(double)numPlayers;
	}


}
