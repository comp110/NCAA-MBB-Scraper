package comp110;

class TarHeelTraditionKey extends BasketballAlgo {

  /** Instance Variables **/
  // None declared

  /** Constructor **/
  TarHeelTraditionKey() {
    // TarHeelTraditionAlgo _must_ have a 0-parameter constructor.
    // Don't let this stop you from using instance variables, if you'd like.
  }

  /** Methods **/
  Scorecard score(Team away, Team home) {
    Scorecard scorecard = new Scorecard(away, home);

    // Here is an example Scoreline being setup and added to
    // the Scorecard. We recommend breaking up each Scoreline into its
    // own method, but you're free to design this code however you would
    // like to.
    scorecard.add(kButter(away, home));
    scorecard.add(makeItWayne(away, home));
    scorecard.add(hensoned(away, home));
    scorecard.add(psychoT(away, home));
    scorecard.add(ballDontLie(away, home));
    scorecard.add(reallyBigTeam(away, home));
    scorecard.add(hasABrice(away, home));
    scorecard.add(bigSean(away, home));
    scorecard.add(theOG(away, home));
    scorecard.add(realBlueSteel(away, home));
    
    return scorecard;
  }
  
  Scoreline kButter(Team away, Team home) {
	  return new Scoreline(
			  "KButter Factor",
			  away.getAssistTurnoverRatio()*10,
			  home.getAssistTurnoverRatio()*10
	  );
  }
  
  Scoreline makeItWayne(Team away, Team home) {
	  return new Scoreline(
			  "Make it Wayne",
			  away.getThreesAttempted() / away.getGamesPlayed(),
			  home.getThreesAttempted() / home.getGamesPlayed()
	  );
  }
  
  Scoreline hensoned(Team away, Team home) {
	  return new Scoreline(
			  "Hensoned",
			  (away.getAvgBlocks() - home.getAvgBlocks()) * 10,
			  (home.getAvgBlocks() - away.getAvgBlocks()) * 10
	  );
  }
  
  Scoreline psychoT(Team away, Team home) {
	  double homeValue = 0;
	  double awayValue = 0;
	  if (away.getFreeThrowsAttempted() > home.getFreeThrowsAttempted()) {
		  awayValue = 15.0;
	  } else {
		  homeValue = 15.0;
	  }
	  return new Scoreline("PsychoT", awayValue, homeValue);
  }
  
  Scoreline ballDontLie(Team away, Team home) {
	  double homeValue = 0;
	  double awayValue = 0;
	  if (away.getFtPercent() > home.getFtPercent()) {
		  awayValue = away.getFreeThrowsMade() / away.getGamesPlayed();
	  } else {
		  homeValue = home.getFreeThrowsMade() / home.getGamesPlayed();
	  }
	  
	  return new Scoreline("Ball Don't Lie", awayValue, homeValue);
  }
  
  Scoreline reallyBigTeam(Team away, Team home) {
	  return new Scoreline("Really Big Team",
			  away.getRoster().size(),
			  home.getRoster().size()
	  );
  }
  
  Scoreline hasABrice(Team away, Team home) {
	  double homeValue = -10.0;
	  double awayValue = -10.0;
	  int i = 0;
	  while (i<home.getRoster().size()) {
		  if (home.getRoster().get(i).getFullName().contains("Brice Johnson")) {
			  homeValue = 10.0;
		  }
		  i++;
	  }
	  i = 0;
	  while (i<away.getRoster().size()) {
		  if (away.getRoster().get(i).getFullName().contains("Brice Johnson")) {
			  awayValue = 10.0;
		  }
		  i++;
	  }
	  return new Scoreline("Has a Brice", awayValue, homeValue);
  }
  
  Scoreline bigSean(Team away, Team home) {
	  int homeDDs = 0;
	  int awayDDs = 0;
	  int i = 0;
	  while (i<away.getRoster().size()) {
		  if (away.getRoster().get(i).getDoubleDoubles() > 0) {
			  awayDDs++;
		  }
		  i++;
	  }
	  i = 0;
	  while (i<home.getRoster().size()) {
		  if (home.getRoster().get(i).getDoubleDoubles() > 0) {
			  homeDDs++;
		  }
		  i++;
	  }
	  return new Scoreline("Big Sean", awayDDs * 5, homeDDs * 5);
  }

  Scoreline theOG(Team away, Team home) {
	  int homeMax = 0;
	  int awayMax = 0;
	  int i = 0;
	  while (i<away.getRoster().size()) {
		  if (away.getRoster().get(i).getTotalPoints() > awayMax) {
			 awayMax = away.getRoster().get(i).getTotalPoints();
		  }
		  i++;
	  }
	  i = 0;
	  while (i<home.getRoster().size()) {
		  if (home.getRoster().get(i).getTotalPoints() > homeMax) {
				 homeMax = home.getRoster().get(i).getTotalPoints();
			  }
			  i++;
	  }
	  double homeScore = 0;
	  double awayScore = 0;
	  if (homeMax > awayMax) {
		  homeScore = 15.0;
	  } else if (awayMax > homeMax) {
		  awayScore = 15.0;
	  }
	  return new Scoreline("The OG", awayScore, homeScore);
  }

  Scoreline realBlueSteel(Team away, Team home) {
	  int homePoints = 0;
	  int awayPoints = 0;
	  int i = 0;
	  while (i<away.getRoster().size()) {
		  Player current = away.getRoster().get(i);
		  if ((double)current.getMinutes() / current.getPlayed() < 2.0) {
			 awayPoints += current.getTotalPoints();
		  }
		  i++;
	  }
	  i = 0;
	  while (i<home.getRoster().size()) {
		  Player current = home.getRoster().get(i);
		  if ((double)current.getMinutes() / current.getPlayed() < 2.0) {
			 homePoints += current.getTotalPoints();
		  }
		  i++;
	  }
	  return new Scoreline("Real Blue Steel", awayPoints, homePoints);
  }

}