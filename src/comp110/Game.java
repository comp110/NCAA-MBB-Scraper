package comp110;

import java.util.ArrayList;

public class Game {

  private int _gameId;
  private String _homeName, _awayName;
  private int _homeId, _awayId;
  private int _pointsValue;
  private int _homeScore;
  private int _awayScore;
  private ArrayList<Scoreline> _scoreLines;
  private int _studentHScore, _studentAScore;
  private String _error = "";

  public Game(int gameId, int tourneyRound, int homeId, int awayId, int homeScore, int awayScore) {
    _gameId = gameId;
    _homeScore = homeScore;
    _awayScore = awayScore;
    _homeId = homeId;
    _awayId = awayId;
    _studentHScore = 0;
    _studentAScore = 0;
    _pointsValue = (int) Math.pow(2, tourneyRound);
  }

  public void play() {
    Team home = RunBracket.getTeam(_homeId);
    Team away = RunBracket.getTeam(_awayId);
    _homeName = home.getName();
    _awayName = away.getName();

    BasketballAlgo studentAlgo = new BracketChallengeAlgo();
    Scorecard card = studentAlgo.score(away, home);
    // Assigns 1 point for each of the 32 games in round 0, 2 for each of the 16
    // in round 1, etc.

    if (card != null) {
      _scoreLines = card.getScorelines();
      _studentHScore = (int) Math.round(card.getHomeScore());
      _studentAScore = (int) Math.round(card.getAwayScore());
    } else {
      _scoreLines = new ArrayList<Scoreline>();
    }
  }

  public int getWinnerId() {
    // If we have actual scores... use that
    if (_homeScore + _awayScore > 0) {
      if (_homeScore > _awayScore) {
        return _homeId;
      } else {
        return _awayId;
      }
    } else {
      // Otherwise, use predictions
      if (_studentHScore >= _studentAScore) {
        return _homeId;
      } else {
        return _awayId;
      }
    }
  }

  public String getError() {
    return _error;
  }

  public void setError(String error) {
    _error = error;
  }

  public String getHomeName() {
    return _homeName;
  }

  public String getAwayName() {
    return _awayName;
  }

  public int getPoints() {
    return _pointsValue;
  }

  public int getAwayScore() {
    return _awayScore;
  }

  public int getHomeScore() {
    return _homeScore;
  }

  public ArrayList<Scoreline> getScoreCard() {
    return _scoreLines;
  }

  public int getStudentHomeScore() {
    return _studentHScore;
  }

  public int getStudentAwayScore() {
    return _studentAScore;
  }
}
