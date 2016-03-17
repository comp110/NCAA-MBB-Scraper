package comp110;

public class Student {

  private int _overallScore;
  private int _correct;
  private int _incorrect;
  private Game[] _games;
  private String _error;

  public Student() {
    _games = new Game[64];
    _overallScore = 0;
    _correct = 0;
    _incorrect = 0;
    _error = "";
  }

  public Student(Game[] games) {
    this();
    _games = games;

    for (Game game : games) {
      // We skip index 0 for simplicity's sake in binheap numbering
      if (game == null) {
        continue;
      }

      String studentWinner = null;
      if (game.getStudentHomeScore() > game.getStudentAwayScore()) {
        studentWinner = game.getHomeName();
      } else {
        studentWinner = game.getAwayName();
      }

      // Set winner variable to the real game's winner, studentWinner to
      // predicted winner.
      String winner = "";
      if (game.getHomeScore() + game.getAwayScore() > 0) {

        // Past Game

        if (game.getHomeScore() > game.getAwayScore()) {
          winner = game.getHomeName();
        } else {
          winner = game.getAwayName();
        }

        if (game.getError().equals("")) {
          if (winner.equals(studentWinner)) {
            _overallScore += game.getPoints();
            _correct++;
          } else {
            _error += game.getError() + "\n\n";
            _incorrect++;
          }
        }

      } else {

        // Future Game
        winner = studentWinner;

      }

    }

  }

  public void setGame(int index, Game game) {
    _games[index] = game;
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

  public String getError() {
    return _error;
  }

  public void setError(String error) {
    _error = error;
  }

}
