package comp110;

public class Student {

  private int overallScore;
  private Game[] games;

  public Student(Game[] games) {
    this.games = games;
    overallScore = 0;
    for (Game game : games) {
      Team winner = null;
      if (game.getHomeScore() > game.getAwayScore())
        winner = game.getHome();
      else
        winner = game.getAway();
      Team studentWinner = null;
      if (game.getScoreCard().getHomeScore() > game.getScoreCard().getAwayScore())
        studentWinner = game.getScoreCard().getHomeTeam();
      else
        studentWinner = game.getScoreCard().getAwayTeam();
      if (winner == studentWinner)
        overallScore += game.getPoints();
    }

  }
}
