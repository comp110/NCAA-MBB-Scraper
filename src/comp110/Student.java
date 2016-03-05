package comp110;

import java.util.ArrayList;

public class Student {

  private int _overallScore;
  private int _correct;
  private int _incorrect;
  private ArrayList<Game> _games;

  public Student(ArrayList<Game> games) {
    _games = games;
    _overallScore = 0;
    _correct = 0;
    _incorrect = 0;
    
    for (Game game : games) {
      // Set winner variable to the real game's winner, studentWinner to predicted winner
      Team winner = null;
      if (game.getHomeScore() > game.getAwayScore()) {
        winner = game.getHome();
      } else { 
        winner = game.getAway();
      }
      
      Team studentWinner = null;
      if (game.getScoreCard().getHomeScore() > game.getScoreCard().getAwayScore()) {
        studentWinner = game.getScoreCard().getHomeTeam();
      } else {
        studentWinner = game.getScoreCard().getAwayTeam();
      }
      
      if (winner == studentWinner) {
        _overallScore += game.getPoints();
        _correct++;
      } else {
        _incorrect++;
      }
    
    }

  }
  
  public int getOverallScore() {
    return _overallScore;
  }
  
  public int getCorrect() {
    return _correct;
  }
  
  public int getIncorrect() {
    return _incorrect;
  }
  
}
