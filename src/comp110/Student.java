package comp110;

public class Student {

  private int _overallScore;
  private Game[] _games;

  public Student(Game[] games) {
    _games = games;
    _overallScore = 0;
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
      
      if (winner == studentWinner)
        _overallScore += game.getPoints();
      }

  }
}
