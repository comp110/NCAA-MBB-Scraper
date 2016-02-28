package comp110;

import java.util.ArrayList;

class CarolinaTraditionAlgoSoln extends BasketballAlgo {

  /** Instance Variables **/
  // None declared

  /** Constructor **/
  CarolinaTraditionAlgoSoln() {
    // BizarroAlgo _must_ have a 0-parameter constructor to grade.
    // Don't let this stop you from using instance variables of your
    // own design, if you'd like.
  }

  /** Methods **/
  Scorecard score(Team away, Team home) {
    Scorecard scorecard = new Scorecard(away, home);

    scorecard.add(this.score("Make it Wayne", this.chuckFactor(away),
        this.chuckFactor(home)));

    scorecard.add(this.hackAttack(away, home));

    scorecard.add(this.ballDontLie(away, home));

    scorecard.add(this.score("Brice Factor", this.briceFactor(away),
        this.briceFactor(home)));

    scorecard.add(this.score("Big Sean", this.playersWithDoubleDoubles(away),
        this.playersWithDoubleDoubles(home)));

    scorecard.add(this.getAssistTurnoverRatio(away, home));
    scorecard.add(this.blocked(away, home));
    scorecard.add(this.tallestPlayer(away, home));

    scorecard.add(this.teamSize(away, home));
    scorecard.add(this.bluesteel(away, home));
    return scorecard;
  }

  Scoreline score(String label, double awayScore, double homeScore) {
    return new Scoreline(label, awayScore, homeScore);
  }

  // 1. Assist / Turnover Ratio
  Scoreline getAssistTurnoverRatio(Team away, Team home) {
    return new Scoreline("KButter Factor", away.getAssistTurnoverRatio() * 10.0,
        home.getAssistTurnoverRatio() * 10.0);
  }

  // 2. Chuck Factor - Give A Point Per Three Attempted Per Game
  double chuckFactor(Team team) {
    return team.getThreesAttempted() / team.getGamesPlayed();
  }

  // 3. Blocks
  Scoreline blocked(Team away, Team home) {
    return new Scoreline("Hensoned",
        (away.getAvgBlocks() - home.getAvgBlocks()) * 10.0,
        (home.getAvgBlocks() - away.getAvgBlocks()) * 10.0);
  }

  // 4. Hack Attack - The Team Which Commits More Fouls Gets +15.0 Points
  Scoreline hackAttack(Team away, Team home) {
    double homeScore = 0.0;
    double awayScore = 0.0;

    if (away.getFreeThrowsAttempted() > home.getFreeThrowsAttempted()) {
      awayScore = 15.0;
    } else {
      homeScore = 15.0;
    }

    return new Scoreline("PsychoT", awayScore, homeScore);
  }

  // 5. "Ball Don't Lie"
  Scoreline ballDontLie(Team away, Team home) {
    double homeScore = 0;
    double awayScore = 0;

    if (away.getFtPercent() != home.getFgPercent()) {
      if (away.getFtPercent() > home.getFtPercent()) {
        awayScore = away.getFreeThrowsMade() / away.getGamesPlayed();
      } else {
        homeScore = home.getFreeThrowsMade() / home.getGamesPlayed();
      }
    }

    return new Scoreline("Ball Don't Lie", awayScore, homeScore);
  }

  // 6. Which team has more players?
  Scoreline teamSize(Team away, Team home) {
    return new Scoreline("Really Big Team", away.getRoster().size(),
        home.getRoster().size());
  }

  // 7. Does The Team Have Brice Johnson? (there exists)
  double briceFactor(Team team) {
    double result = 0;
    for (Player p : team.getRoster()) {
      if (p.getFullName().contains("Brice Johnson")) {
        result = 10.0;
      }
    }
    if (result != 10.0) {
      return -10.0;
    } else {
      return result;
    }
  }

  // 8. How Many Players With Odd Jerseys (count)
  double playersWithDoubleDoubles(Team team) {
    int i = 0;
    double count = 0;

    ArrayList<Player> players = team.getRoster();
    while (i < players.size()) {
      if (players.get(i).getDoubleDoubles() > 0) {
        count += 1.0;
      }
      i++;
    }
    return count * 5.0;
  }

  // 9. Which team has the tallest player? (max)
  Scoreline tallestPlayer(Team away, Team home) {
    double tallestHome = this.mostPoints(home);
    double tallestAway = this.mostPoints(away);
    if (tallestHome != tallestAway) {
      if (tallestAway > tallestHome) {
        return new Scoreline("The OG", 15.0, 0.0);
      } else {
        return new Scoreline("The OG", 0.0, 15.0);
      }
    } else {
      return new Scoreline("The OG", 0.0, 0.0);
    }
  }

  int mostPoints(Team team) {
    int mostPoints = 0;
    ArrayList<Player> players = team.getRoster();
    int i = 0;
    while (i < players.size()) {
      Player p = players.get(i);
      if (p.getTotalPoints() > mostPoints) {
        mostPoints = p.getTotalPoints();
      }
      i++;
    }
    return mostPoints;
  }

  // 10. Which team has best blue steel
  Scoreline bluesteel(Team away, Team home) {
    return new Scoreline("Real Blue Steel", this.reserveSize(away),
        this.reserveSize(home));
  }

  double reserveSize(Team team) {
    int reserveCount = 0;
    double points = 0.0;
    ArrayList<Player> players = team.getRoster();
    int i = 0;
    while (i < players.size()) {
      Player p = players.get(i);
      if (p.getMinutes() / p.getPlayed() < 2.0) {
        points = points + p.getTotalPoints();
      }
      i++;
    }
    return points;
  }

  // Sum
  // Filtered Sum - Blue Steel
  // Scoring average higher than Jordan's 83-84

}