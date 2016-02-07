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
	
	public double calculateScore(Team team){ //moving weights to inside methods will solve reflection problem for now, easiest/quickest fix

		double compositeScore = 0;
		double avgOver6ft = avgInchesOver6ft(team);
		double totalDoubleDoubles = totalDoubleDoubles(team);
		double winScore = winScore(team);
		double scoringMargin = scoringMargin(team);
		double totalAbove12 = totalAbove12(team);
		double top20Stats = top20Stats(team);

		compositeScore = avgOver6ft 
				        + totalDoubleDoubles 
				        + winScore 
				        + scoringMargin 
				        + totalAbove12 
				        + top20Stats;
		
		return compositeScore;

	}

	public Team get_homeTeam() {
		return _homeTeam;
	}

	public Team get_awayTeam() {
		return _awayTeam;
	}

	public Team get_winner() {
		return _winner;
	}

	public double top20Stats(Team team) {
		double weight=0.8;
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

	public double totalAbove12(Team team) {
		Player[] roster = team.getRoster();
		double weight=1.2;
		int countAbove12 = 0;
		for (Player p : roster) {
			if (p.getAvgPoints() >= 12.0) {
				countAbove12++;
			}
		}
		return countAbove12*weight;
	}

	public double scoringMargin(Team team) {
		double weight=0.6;
		double offense = team.getScoringOffense();
		double defense = team.getScoringDefense();
		return (offense-defense)*weight;
	}

	public double winScore(Team team) {
		double weight=0.4;
		int pointsForWin=2;
		int pointsForLoss=1;
		int wins = team.getWins();
		int losses = team.getLosses();
		return (pointsForWin*wins + pointsForLoss*losses) * weight;
	}

	public double totalDoubleDoubles(Team team) {
		Player[] roster = team.getRoster(); 
		double weight=0.4;
		int totalDoubleDoubles = 0;
		for (Player p : roster) {
			totalDoubleDoubles += p.getDoubleDoubles();
		}
		return totalDoubleDoubles*weight;
	}

	public double avgInchesOver6ft(Team team) {
		Player[] roster = team.getRoster();
		double weight=0.2;
		int totalInches = 0;
		int numPlayers = roster.length;
		for (Player p : roster) {
			totalInches += p.getHeight() - 72;
		}
		return (totalInches/(double)numPlayers) * weight;
	}
}
