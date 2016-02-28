package comp110;

import java.util.ArrayList;

class Scorecard {

  Team _home;
  Team _away;
  ArrayList<Scoreline> _lines;

  Scorecard(Team away, Team home) {
    _home = home;
    _away = away;
    _lines = new ArrayList<Scoreline>();
  }

  Team getHomeTeam() {
    return _home;
  }

  Team getAwayTeam() {
    return _away;
  }

  void add(Scoreline line) {
    _lines.add(line);
  }

  ArrayList<Scoreline> getScorelines() {
    return _lines;
  }

  double getHomeScore() {
    double score = 0;
    for (Scoreline line : this.getScorelines()) {
      score = score + line.getHomeValue();
    }
    return score;
  }

  double getAwayScore() {
    double score = 0;
    for (Scoreline line : this.getScorelines()) {
      score = score + line.getAwayValue();
    }
    return score;
  }

  Team getWinner() {
    if (this.getHomeScore() >= this.getAwayScore()) {
      return _home;
    } else {
      return _away;
    }
  }

}