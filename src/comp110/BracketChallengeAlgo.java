package comp110;

public class BracketChallengeAlgo extends BasketballAlgo {
  Scorecard score(Team away, Team home) {
    return new Scorecard(home, away);
  }
}
