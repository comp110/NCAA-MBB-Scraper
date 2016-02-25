package comp110;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;

public class PlayerStatsStage {

  public static TableView<Player> makeDetailedPlayerTable(ObservableList<Player> players) {
    TableView detailedStats = new TableView<Player>();
    ArrayList<String> getters = getGetters();
    HashMap<String, String> abbreviation = createAbbreviationMap();

    for (Player p : players) {
      System.out.println(p);
    }

    for (String getter : getters) {
      Label insert = new Label(abbreviation.get(getter));
      insert.setTooltip(new Tooltip(getter));
      TableColumn currentColumn = new TableColumn<>();
      currentColumn.setCellValueFactory(new PropertyValueFactory<>(getter.substring(3)));
      currentColumn.setGraphic(insert);
      detailedStats.getColumns().add(currentColumn);
    }

    detailedStats.setItems(players);

    return detailedStats;
  }

  public static ArrayList<String> getGetters() {
    Class playerClass = null;
    try {
      playerClass = Class.forName("comp110.Player");
    } catch (ClassNotFoundException e) {
      System.out.println("crap");
    }
    Method[] allMethods = playerClass.getDeclaredMethods();
    ArrayList<String> getters = new ArrayList<>();
    for (int i = 1; i < 8; i++) {
      getters.add(null);
    }
    for (Method m : allMethods) {
      String name = m.getName();
      if (m.getName().substring(0, 3).equals("get")) {
        if (m.getName().equals("getFirst") || m.getName().equals("getLast")) {
          continue;
        }
        switch (name) {
        case "getJersey":
          getters.remove(0);
          getters.add(0, name);
          break;
        case "getFullName":
          getters.remove(1);
          getters.add(1, name);
          break;
        case "getYear":
          getters.remove(2);
          getters.add(2, name);
          break;
        case "getPosition":
          getters.remove(3);
          getters.add(3, name);
          break;
        case "getHeight":
          getters.remove(4);
          getters.add(4, name);
          break;
        case "getPlayed":
          getters.remove(5);
          getters.add(5, name);
          break;
        case "getAvgPoints":
          getters.remove(6);
          getters.add(6, name);
          break;
        case "getAvgRebs":
          getters.remove(7);
          getters.add(7, name);
          break;
        default:
          getters.add(m.getName());
          break;
        }
      }
    }
    return getters;
  }

  public static HashMap<String, String> createAbbreviationMap() {
    HashMap<String, String> abbreviations = new HashMap<String, String>() {
      {
        put("getJersey", "Jersey");
        put("getFullName", "Player");
        put("getYear", "Yr");
        put("getPosition", "Pos");
        put("getHeight", "Ht");
        put("getPlayed", "GP");
        put("getStarted", "GS");
        put("getMinutes", "MP");
        put("getFieldGoals", "FGM");
        put("getFieldGoalsAtt", "FGA");
        put("getFgPercent", "FG%");
        put("getThrees", "3FG");
        put("getThreesAtt", "3FGA");
        put("getThreePercent", "3FG%");
        put("getFreeThrows", "FT");
        put("getFreeThrowsAtt", "FTA");
        put("getFtPercent", "FT%");
        put("getTotalPoints", "PTS");
        put("getAvgPoints", "AvgP");
        put("getORebounds", "ORebs");
        put("getDRebounds", "DRebs");
        put("getTotalRebs", "Tot Reb");
        put("getAvgRebs", "AvgR");
        put("getAssists", "AST");
        put("getTurnovers", "TO");
        put("getSteals", "STL");
        put("getBlocks", "BLK");
        put("getFouls", "Fouls");
        put("getDoubleDoubles", "Dbl Dbl");
        put("getTripleDoubles", "Trpl Dbl");
      }
    };

    return abbreviations;
  }

}
