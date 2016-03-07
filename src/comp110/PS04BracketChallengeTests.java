package comp110;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runners.MethodSorters;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PS04BracketChallengeTests {

  private final static double EPSILON = 0.1;
  private final static int UNC_INDEX = 15;

  @Rule
  public Timeout globalTimeout = Timeout.millis(500);

  static Team[] _teams;
  Scorecard _student;
  Scorecard _key;
  Team _home;
  Team _away;

  @BeforeClass
  public static void readJson() throws FileNotFoundException {
    InputStream is = PS04TarHeelTraditionTests.class.getResourceAsStream("assets/accplustop25.json");
    JsonReader reader = new JsonReader(new InputStreamReader(is));
    Gson gson = new Gson();
    _teams = gson.fromJson(reader, Team[].class);
  }

  @Before
  public void setup() {
    boolean tarheelsHome = Math.random() > 0.5;
    if (tarheelsHome) {
      _home = _teams[UNC_INDEX];
      _away = _teams[(int) (Math.random() * _teams.length)];
    } else {
      _home = _teams[(int) (Math.random() * _teams.length)];
      _away = _teams[UNC_INDEX];
    }
    BasketballAlgo studentAlgo = new BracketChallengeAlgo();
    BasketballAlgo answerAlgo = new BracketChallengeAlgo();
    _key = answerAlgo.score(_away, _home);
    _away.resetRosterAccessCount();
    _home.resetRosterAccessCount();
    _student = studentAlgo.score(_away, _home);
  }

  public void failIfNull() {
    if (_student == null) {
      Assert.fail("You must return a Scorecard that is not null.");
    }
  }

  @Test
  public void r01FirstScoreline() {
    this.failIfNull();
    assertThat("Number of Scorelines added to Scorecard", _student.getScorelines().size(), greaterThanOrEqualTo(1));
  }

  @Test
  public void r02SecondScoreline() {
    this.failIfNull();
    assertThat("Number of Scorelines added to Scorecard", _student.getScorelines().size(), greaterThanOrEqualTo(2));
  }

  @Test
  public void r03ThirdScoreline() {
    this.failIfNull();
    assertThat("Number of Scorelines added to Scorecard", _student.getScorelines().size(), greaterThanOrEqualTo(3));
  }

  @Test
  public void r04FourthScoreline() {
    this.failIfNull();
    assertThat("Number of Scorelines added to Scorecard", _student.getScorelines().size(), greaterThanOrEqualTo(4));
  }

  @Test
  public void r05FifthScoreline() {
    this.failIfNull();
    assertThat("Number of Scorelines added to Scorecard", _student.getScorelines().size(), greaterThanOrEqualTo(5));
  }

  @Test
  public void r06AtLeastTwoScorelinesUseRoster() {
    this.failIfNull();
    assertThat("Number of Scorelines using Roster", _home.rosterAccessCount(), greaterThanOrEqualTo(1));
  }

  @Test
  public void r07UniqueScorelines() {
    this.failIfNull();
    List<String> labels = new ArrayList<String>();
    String[] labelStrings = { "KButter Factor", "Make it Wayne", "Hensoned", "PsychoT", "Ball Don't Lie",
        "Really Big Team", "Has a Brice", "Big Sean", "The OG", "Real Blue Steel" };
    for (String s : labelStrings) {
      labels.add(s);
    }

    if (_student.getScorelines().size() < 5) {
      Assert.fail("Requirement 1 must be met first. Must have 5 scorelines.");
    }

    for (Scoreline s : _student.getScorelines()) {
      if (labels.contains(s.getLabel())) {
        Assert.fail("Duplicate Scoreline found from Part 1");
      }
    }
  }

}
