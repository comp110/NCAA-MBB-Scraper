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
  

  public Game(int gameId, int tourneyRound, int homeId, int awayId, int homeScore, int awayScore) {
    _gameId = gameId;
    _homeScore = homeScore;
    _awayScore = awayScore;
    _homeId = homeId;
    _awayId = awayId;
    _studentHScore = 0;
    _studentAScore = 0;
    
    Team home = RunBracket.getTeam(homeId);
    Team away = RunBracket.getTeam(awayId);
    _homeName = home.getName();
    _awayName = away.getName();
    
    BasketballAlgo studentAlgo = new BracketChallengeAlgo();
    Scorecard card = studentAlgo.score(away, home);
    // Assigns 1 point for each of the 32 games in round 0, 2 for each of the 16 in round 1, etc.
    _pointsValue = (int) Math.pow(2, tourneyRound);
    
    _scoreLines = card.getScorelines();
    for (Scoreline line : _scoreLines){
      _studentHScore += line.getHomeValue();
      _studentAScore += line.getAwayValue();
    }
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
  
  public int getStudentHomeScore(){
    return _studentHScore;
  }
  
  public int getStudentAwayScore(){
    return _studentAScore;
  }
}
