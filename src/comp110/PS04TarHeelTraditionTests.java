package comp110;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertNotNull;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runners.MethodSorters;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PS04TarHeelTraditionTests {

  @Rule
  public Timeout globalTimeout = Timeout.millis(1000);

  Scorecard _student;
  Scorecard _key;

  @Before
  public void setup() throws FileNotFoundException {
    JsonReader reader = new JsonReader(
        new FileReader("resources/accplustop25.json")
        );
    Gson gson = new Gson();
    Team[] teams = gson.fromJson(reader, Team[].class);
    // I just picked these two teams arbitrarily for now
    Team home = teams[5];
    Team away = teams[12];
    BasketballAlgo studentAlgo =  new TarHeelTraditionAlgo();
    BasketballAlgo answerAlgo = new TarHeelTraditionKey();
    _student = studentAlgo.score(away, home);
    _key = answerAlgo.score(away, home);
  }

  @Test
  public void r01KButter() {
    String label = "KButter Factor";
    Scoreline key = getLine(_key, label);
    Scoreline student = getLine(_student, label);
    assertNotNull(label + " Scoreline is in Scorecard", student);
    assertThat(
        label + " away score is calcluated correctly",
        key.getAwayValue(), 
        closeTo(student.getAwayValue(), 0.001)
        );
    assertThat(
        label + " home score is calculated correctly",
        key.getHomeValue(),
        closeTo(student.getHomeValue(), 0.001)
        );

  }

  @Test
  public void r02MakeItWayne() {
    String label = "Make it Wayne";
    Scoreline key = getLine(_key, label);
    Scoreline student = getLine(_student, label);
    assertNotNull(label + " Scoreline is in Scorecard", student);
    assertThat(
        label + " away score is calcluated correctly",
        key.getAwayValue(), 
        closeTo(student.getAwayValue(), 0.001)
        );
    assertThat(
        label + " home score is calculated correctly",
        key.getHomeValue(),
        closeTo(student.getHomeValue(), 0.001)
        );
  }

  @Test
  public void r03Hensoned() {
    String label = "Hensoned";
    Scoreline key = getLine(_key, label);
    Scoreline student = getLine(_student, label);
    assertNotNull(label + " Scoreline is in Scorecard", student);
    assertThat(
        label + " away score is calcluated correctly",
        key.getAwayValue(), 
        closeTo(student.getAwayValue(), 0.001)
        );
    assertThat(
        label + " home score is calculated correctly",
        key.getHomeValue(),
        closeTo(student.getHomeValue(), 0.001)
        );
  }

  @Test
  public void r04PsychoT() {
    String label = "PsychoT";
    Scoreline key = getLine(_key, label);
    Scoreline student = getLine(_student, label);
    assertNotNull(label + " Scoreline is in Scorecard", student);
    assertThat(
        label + " away score is calcluated correctly",
        key.getAwayValue(), 
        closeTo(student.getAwayValue(), 0.001)
        );
    assertThat(
        label + " home score is calculated correctly",
        key.getHomeValue(),
        closeTo(student.getHomeValue(), 0.001)
        );
  }

  @Test
  public void r05BallDontLie() {
    String label = "Ball Don't Lie";
    Scoreline key = getLine(_key, label);
    Scoreline student = getLine(_student, label);
    assertNotNull(label + " Scoreline is in Scorecard", student);
    assertThat(
        label + " away score is calcluated correctly",
        key.getAwayValue(), 
        closeTo(student.getAwayValue(), 0.001)
        );
    assertThat(
        label + " home score is calculated correctly",
        key.getHomeValue(),
        closeTo(student.getHomeValue(), 0.001)
        );
  }

  @Test
  public void r06ReallyBigTeam() {
    String label = "Really Big Team";
    Scoreline key = getLine(_key, label);
    Scoreline student = getLine(_student, label);
    assertNotNull(label + " Scoreline is in Scorecard", student);
    assertThat(
        label + " away score is calcluated correctly",
        key.getAwayValue(), 
        closeTo(student.getAwayValue(), 0.001)
        );
    assertThat(
        label + " home score is calculated correctly",
        key.getHomeValue(),
        closeTo(student.getHomeValue(), 0.001)
        );
  }

  @Test
  public void r07HasABrice() {
    String label = "Has a Brice";
    Scoreline key = getLine(_key, label);
    Scoreline student = getLine(_student, label);
    assertNotNull(label + " Scoreline is in Scorecard", student);
    assertThat(
        label + " away score is calcluated correctly",
        key.getAwayValue(), 
        closeTo(student.getAwayValue(), 0.001)
        );
    assertThat(
        label + " home score is calculated correctly",
        key.getHomeValue(),
        closeTo(student.getHomeValue(), 0.001)
        );
  }

  @Test
  public void r08BigSean() {
    String label = "Big Sean";
    Scoreline key = getLine(_key, label);
    Scoreline student = getLine(_student, label);
    assertNotNull(label + " Scoreline is in Scorecard", student);
    assertThat(
        label + " away score is calcluated correctly",
        key.getAwayValue(), 
        closeTo(student.getAwayValue(), 0.001)
        );
    assertThat(
        label + " home score is calculated correctly",
        key.getHomeValue(),
        closeTo(student.getHomeValue(), 0.001)
        );
  }

  @Test
  public void r09TheOG() {
    String label = "The OG";
    Scoreline key = getLine(_key, label);
    Scoreline student = getLine(_student, label);
    assertNotNull(label + " Scoreline is in Scorecard", student);
    assertThat(
        label + " away score is calcluated correctly",
        key.getAwayValue(), 
        closeTo(student.getAwayValue(), 0.001)
        );
    assertThat(
        label + " home score is calculated correctly",
        key.getHomeValue(),
        closeTo(student.getHomeValue(), 0.001)
        );
  }

  @Test
  public void r10RealBlueSteel() {
    String label = "Real Blue Steel";
    Scoreline key = getLine(_key, label);
    Scoreline student = getLine(_student, label);
    assertNotNull(label + " Scoreline is in Scorecard", student);
    assertThat(
        label + " away score is calcluated correctly",
        key.getAwayValue(), 
        closeTo(student.getAwayValue(), 0.001)
        );
    assertThat(
        label + " home score is calculated correctly",
        key.getHomeValue(),
        closeTo(student.getHomeValue(), 0.001)
        );
  }

  private Scoreline getLine(Scorecard card, String label) {
    for (Scoreline sl : card.getScorelines()) {
      // Made this non-case sensitive for now
      if (sl.getLabel().toLowerCase().equals(label.toLowerCase())) {
        return sl;
      }
    }
    return null;
  }

}
