package comp110;

/**
 * This is a draft of what the students will do
 * In the future we can maybe have them instantiate matchups in their own main methods but
 * this seemed simpler for now
 **/

public class Matchup {
	
	private Team _homeTeam, _awayTeam, _winner;
	
	public Matchup(Team homeTeam, Team awayTeam) {
		_homeTeam = homeTeam;
		_awayTeam = awayTeam;
		double homeScore = calculateScore(_homeTeam);
		double awayScore = calculateScore(_awayTeam);
		System.out.println(_homeTeam.getName() + ": " + homeScore);
		System.out.println(_awayTeam.getName() + ": " + awayScore);
		if (homeScore > awayScore) {
			_winner = _homeTeam;
		} else {
			_winner = _awayTeam;
		}
		System.out.println(_winner.getName() + " wins!");
		
	}
	
	public double calculateScore(Team team){

		double compositeScore = 0;
		Player[] roster = team.getRoster();
		double avgOver6ft = avgInchesOver6ft(roster, 0.2);
		double totalDoubleDoubles = totalDoubleDoubles(roster, 0.4);
		double winScore = winScore(team, 2, -1, 0.4);
		double scoringMargin = scoringMargin(team, 0.6);
		double totalAbove12 = totalAbove12(roster, 1.2);
		double top20Stats = top20Stats(team, 0.8);

		compositeScore = avgOver6ft 
				        + totalDoubleDoubles 
				        + winScore 
				        + scoringMargin 
				        + totalAbove12 
				        + top20Stats;
		
		return compositeScore;

	}

	public double top20Stats(Team team, double weight) {
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
		
		return top20Count*weight;
	}

	public double totalAbove12(Player[] roster, double weight) {
		int countAbove12 = 0;
		for (Player p : roster) {
			if (p.getAvgPoints() >= 12.0) {
				countAbove12++;
			}
		}
		return countAbove12*weight;
	}

	public double scoringMargin(Team team, double weight) {
		double offense = team.getScoringOffense();
		double defense = team.getScoringDefense();
		return offense-defense;
	}

	public double winScore(Team team, int pointsForWin, int pointsForLoss, double weight) {
		int wins = team.getWins();
		int losses = team.getLosses();
		return (pointsForWin*wins + pointsForLoss*losses) * weight;
	}

	public double totalDoubleDoubles(Player[] roster, double weight) {
		int totalDoubleDoubles = 0;
		for (Player p : roster) {
			totalDoubleDoubles += p.getDoubleDoubles();
		}
		return totalDoubleDoubles*weight;
	}

	public double avgInchesOver6ft(Player[] roster, double weight) {
		int totalInches = 0;
		int numPlayers = roster.length;
		for (Player p : roster) {
			totalInches += p.getHeight() - 72;
		}
		return (totalInches/(double)numPlayers) * weight;
	}
}
