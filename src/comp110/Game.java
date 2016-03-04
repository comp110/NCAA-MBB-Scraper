package comp110;

public class Game {

  private int id;
  private Team home;
  private Team away;
  private int pointsValue;
  private int homeScore;
  private int awayScore;
  private Scorecard card;

  public Game(int id, int homeid, int awayid, int homeScore, int awayScore) {
    this.id = id;
    this.homeScore = homeScore;
    this.awayScore = awayScore;
    this.home = Base.getTeamById(homeid);
    this.away = Base.getTeamById(awayid);
    BracketChallengeAlgo a = new BracketChallengeAlgo();
    card = a.score(away, home);
  }

  public Team getHome() {
    return home;
  }

  public Team getAway() {
    return away;
  }

  public int getPoints() {
    return pointsValue;
  }

  public int getAwayScore() {
    return awayScore;
  }

  public int getHomeScore() {
    return homeScore;
  }

  public Scorecard getScoreCard() {
    return card;
  }
}
