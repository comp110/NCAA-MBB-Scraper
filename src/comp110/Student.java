package comp110;

import java.util.ArrayList;

public class Student {

  private int _overallScore;
  private int _correct;
  private int _incorrect;
  private Game[] _games;

  public Student(Game[] games) {
    _games = games;
    _overallScore = 0;
    _correct = 0;
    _incorrect = 0;
    
    for (Game game : games) {
      // Set winner variable to the real game's winner, studentWinner to predicted winner
      String winner = null;
      if (game.getHomeScore() > game.getAwayScore()) {
        winner = game.getHomeName();
      } else { 
        winner = game.getAwayName();
      }
      
      String studentWinner = null;
      if (game.getStudentHomeScore() > game.getStudentAwayScore()) {
        studentWinner = game.getHomeName();
      } else {
        studentWinner = game.getAwayName();
      }
      
      if (winner.equals(studentWinner)) {
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
