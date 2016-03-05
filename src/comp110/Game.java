package comp110;

public class Game {

  private int _gameId;
  private String _homeName, _awayName;
  private int _homeId, _awayId;
  private int _pointsValue;
  private int _homeScore;
  private int _awayScore;
  private Scorecard _card;

  public Game(int gameId, int tourneyRound, int homeId, int awayId, int homeScore, int awayScore) {
    _gameId = gameId;
    _homeScore = homeScore;
    _awayScore = awayScore;
    _homeId = homeId;
    _awayId = awayId;
    
    Team home = RunBracket.getTeam(homeId);
    Team away = RunBracket.getTeam(awayId);
    _homeName = home.getName();
    _awayName = away.getName();
    
    BasketballAlgo studentAlgo = new BracketChallengeAlgo();
    _card = studentAlgo.score(away, home);
    // Assigns 1 point for each of the 32 games in round 0, 2 for each of the 16 in round 1, etc.
    _pointsValue = (int) Math.pow(2, tourneyRound);
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

  public Scorecard getScoreCard() {
    return _card;
  }
}
