package comp110;

public class Game {

  private int _gameId;
  private Team _home;
  private Team _away;
  private int _pointsValue;
  private int _homeScore;
  private int _awayScore;
  private Scorecard _card;

  public Game(int gameId, int tourneyRound, int homeId, int awayId, int homeScore, int awayScore) {
    _gameId = gameId;
    _homeScore = homeScore;
    _awayScore = awayScore;
    
    _home = RunBracket.getTeam(homeId);
    _away = RunBracket.getTeam(awayId);
    
    BasketballAlgo studentAlgo = new BracketChallengeAlgo();
    _card = studentAlgo.score(_away, _home);
    // Assigns 1 point for each of the 32 games in round 0, 2 for each of the 16 in round 1, etc.
    _pointsValue = (int) Math.pow(2, tourneyRound);
  }

  public Team getHome() {
    return _home;
  }

  public Team getAway() {
    return _away;
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

  public Scorecard getScoreCard() {
    return _card;
  }
}
