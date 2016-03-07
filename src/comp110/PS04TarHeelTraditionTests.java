package comp110;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertNotNull;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
public class PS04TarHeelTraditionTests {

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
    BasketballAlgo studentAlgo = new TarHeelTraditionAlgo();
    BasketballAlgo answerAlgo = new TarHeelTraditionKey();
    _student = studentAlgo.score(_away, _home);
    _key = answerAlgo.score(_away, _home);
  }

  @Test
  public void r01KButter() {
    String label = "KButter Factor";
    Scoreline key = getLine(_key, label);
    Scoreline student = getLine(_student, label);
    assertNotNull(
        label
            + " Scoreline was not found in Scorecard. Check your spelling, punctuation, and capitalization carefully!",
        student);
    assertThat(this.assertString(label, false), student.getAwayValue(), closeTo(key.getAwayValue(), EPSILON));
    assertThat(this.assertString(label, true), student.getHomeValue(), closeTo(key.getHomeValue(), EPSILON));

  }

  @Test
  public void r02MakeItWayne() {
    String label = "Make it Wayne";
    Scoreline key = getLine(_key, label);
    Scoreline student = getLine(_student, label);
    assertNotNull(
        label
            + " Scoreline was not found in Scorecard. Check your spelling, punctuation, and capitalization carefully!",
        student);
    assertThat(this.assertString(label, false), student.getAwayValue(), closeTo(key.getAwayValue(), EPSILON));
    assertThat(this.assertString(label, true), student.getHomeValue(), closeTo(key.getHomeValue(), EPSILON));
  }

  @Test
  public void r03Hensoned() {
    String label = "Hensoned";
    Scoreline key = getLine(_key, label);
    Scoreline student = getLine(_student, label);
    assertNotNull(
        label
            + " Scoreline was not found in Scorecard. Check your spelling, punctuation, and capitalization carefully!",
        student);
    assertThat(this.assertString(label, false), student.getAwayValue(), closeTo(key.getAwayValue(), EPSILON));
    assertThat(this.assertString(label, true), student.getHomeValue(), closeTo(key.getHomeValue(), EPSILON));
  }

  @Test
  public void r04PsychoT() {
    String label = "PsychoT";
    Scoreline key = getLine(_key, label);
    Scoreline student = getLine(_student, label);
    assertNotNull(
        label
            + " Scoreline was not found in Scorecard. Check your spelling, punctuation, and capitalization carefully!",
        student);
    assertThat(this.assertString(label, false), student.getAwayValue(), closeTo(key.getAwayValue(), EPSILON));
    assertThat(this.assertString(label, true), student.getHomeValue(), closeTo(key.getHomeValue(), EPSILON));
  }

  @Test
  public void r05BallDontLie() {
    String label = "Ball Don't Lie";
    Scoreline key = getLine(_key, label);
    Scoreline student = getLine(_student, label);
    assertNotNull(
        label
            + " Scoreline was not found in Scorecard. Check your spelling, punctuation, and capitalization carefully!",
        student);
    assertThat(this.assertString(label, false), student.getAwayValue(), closeTo(key.getAwayValue(), EPSILON));
    assertThat(this.assertString(label, true), student.getHomeValue(), closeTo(key.getHomeValue(), EPSILON));
  }

  @Test
  public void r06ReallyBigTeam() {
    String label = "Really Big Team";
    Scoreline key = getLine(_key, label);
    Scoreline student = getLine(_student, label);
    assertNotNull(
        label
            + " Scoreline was not found in Scorecard. Check your spelling, punctuation, and capitalization carefully!",
        student);
    assertThat(this.assertString(label, false), student.getAwayValue(), closeTo(key.getAwayValue(), EPSILON));
    assertThat(this.assertString(label, true), student.getHomeValue(), closeTo(key.getHomeValue(), EPSILON));
  }

  @Test
  public void r07HasABrice() {
    String label = "Has a Brice";
    Scoreline key = getLine(_key, label);
    Scoreline student = getLine(_student, label);
    assertNotNull(
        label
            + " Scoreline was not found in Scorecard. Check your spelling, punctuation, and capitalization carefully!",
        student);
    assertThat(this.assertString(label, false), student.getAwayValue(), closeTo(key.getAwayValue(), EPSILON));
    assertThat(this.assertString(label, true), student.getHomeValue(), closeTo(key.getHomeValue(), EPSILON));
  }

  @Test
  public void r08BigSean() {
    String label = "Big Sean";
    Scoreline key = getLine(_key, label);
    Scoreline student = getLine(_student, label);
    assertNotNull(
        label
            + " Scoreline was not found in Scorecard. Check your spelling, punctuation, and capitalization carefully!",
        student);
    assertThat(this.assertString(label, false), student.getAwayValue(), closeTo(key.getAwayValue(), EPSILON));
    assertThat(this.assertString(label, true), student.getHomeValue(), closeTo(key.getHomeValue(), EPSILON));
  }

  @Test
  public void r09TheOG() {
    String label = "The OG";
    Scoreline key = getLine(_key, label);
    Scoreline student = getLine(_student, label);
    assertNotNull(
        label
            + " Scoreline was not found in Scorecard. Check your spelling, punctuation, and capitalization carefully!",
        student);
    assertThat(this.assertString(label, false), student.getAwayValue(), closeTo(key.getAwayValue(), EPSILON));
    assertThat(this.assertString(label, true), student.getHomeValue(), closeTo(key.getHomeValue(), EPSILON));
  }

  @Test
  public void r10RealBlueSteel() {
    String label = "Real Blue Steel";
    Scoreline key = getLine(_key, label);
    Scoreline student = getLine(_student, label);
    assertNotNull(
        label
            + " Scoreline was not found in Scorecard. Check your spelling, punctuation, and capitalization carefully!",
        student);
    assertThat(this.assertString(label, false), student.getAwayValue(), closeTo(key.getAwayValue(), EPSILON));
    assertThat(this.assertString(label, true), student.getHomeValue(), closeTo(key.getHomeValue(), EPSILON));
  }

  private Scoreline getLine(Scorecard card, String label) {
    for (Scoreline sl : card.getScorelines()) {
      // Made this non-case sensitive for now
      if (sl.getLabel().toLowerCase().contains(label.toLowerCase())) {
        return sl;
      }
    }
    return null;
  }

  private String assertString(String label, boolean isHome) {
    String side = isHome ? "home" : "away";
    Team focus = isHome ? _home : _away;
    Team other = isHome ? _away : _home;
    return "The \"" + label + "\" Scoreline of " + focus.getName() + " (" + side + ") versus " + other.getName();
  }

}
