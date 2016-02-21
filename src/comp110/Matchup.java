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
		if (homeScore > awayScore) {
			_winner = _homeTeam;
		} else {
			_winner = _awayTeam;
		}
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
	
	public double test8(Team team){
	  return 0.0;
	}
	public double test7(Team team){
    return 0.0;
  }
	public double test5(Team team){
    return 0.0;
  }
	public double test4(Team team){
    return 0.0;
  }
	public double test3(Team team){
    return 0.0;
  }
	public double test2(Team team){
    return 0.0;
  }
	
	public double experienceAdvantage(Team team) {
		int total = 0;
		double weight = 0.6;
		Player[] roster = team.getRoster();
		for (int i=0; i<roster.length; i++) {
			if (roster[i].getYear().equals("Senior")) {
				total += 2;
			} else if (roster[i].getYear().equals("Junior")) {
				total += 1;
			} else if (roster[i].getYear().equals("Sophomore")) {
				
			} else {
				total -= 1;
			}
		}
		return total*weight;
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
	
	public double totalPointsScored(Team team) {
		int total = 0;
		double weight = 0.01;
		for (int i=0; i<team.getRoster().length; i++) {
			Player current = team.getRoster()[i];
			total += current.getTotalPoints();
		}
		return total*weight;
	}
	
	public double totalRebounds(Team team) {
		int total = 0;
		double weight = 0.01;
		for (int i=0; i<team.getRoster().length; i++) {
			Player current = team.getRoster()[i];
			total += current.getORebounds() + current.getDRebounds();
		}
		return total*weight;
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
	
	public double hasBriceJohnson(Team team) {
		double weight = 1.0;
		for (Player p : team.getRoster()) {
			if (p.getFirst().equals("Brice") && p.getLast().equals("Johnson")) {
				return 10.0*weight;
			}
		}
		return 0.0;
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

}
