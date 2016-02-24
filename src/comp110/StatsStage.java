package comp110;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StatsStage extends Base {

  private Stage _stage;
  private boolean started = false;
  private ComboBox<String> teamBox, playerBox;
  private TableView teamView, playerView;
  
  private ObservableList<TeamStat> teamStats;
  private Team selected;

  public VBox makeStatsStage(Team selected) {
    this.selected = selected;
    

 

    try {
      _teams = readJson();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    VBox container = new VBox(10);
    HBox combosHolder = new HBox(10);
    HBox tablesHolder = new HBox(10);
    container.getChildren().addAll(combosHolder, tablesHolder);

    teamBox = buildTeamBox();
    teamBox.getSelectionModel().selectedItemProperty().addListener(this::teamChanged);
    int startIndex = 0;
    // sets the team to be the team chosen in box 1
    for (int i = 0; i < teamBox.getItems().size(); i++) {
      if (teamBox.getItems().get(i).equals(selected.getName()))
        startIndex = i;
    }
    teamBox.getSelectionModel().select(startIndex);
    combosHolder.getChildren().add(teamBox);
    playerBox = buildPlayerBox();
    combosHolder.getChildren().add(playerBox);
    teamView = makeTeamTable();
    tablesHolder.getChildren().add(teamView);
    
    

    return container;
  }

  private ObservableList<TeamStat> setupStats() throws IllegalArgumentException, IllegalAccessException {
    Class klass = null;
    try {
      klass = Class.forName("comp110.Team");
    } catch (ClassNotFoundException e) {
      System.out.println("crap");
    }
    Method[] methods = klass.getDeclaredMethods();
    Method[] getterMethodsDoubles = getGetters(methods);
    Method[] getterMethodsInts = getGetterI(methods);
    ArrayList<TeamStat> stats = new ArrayList<TeamStat>();
    for (int i = 0; i < getterMethodsDoubles.length; i++){
      double value = 0;
      int rank = 0;
      try {
        value = (double) getterMethodsDoubles[i].invoke(selected);
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      }
      for (int j = 0; j < getterMethodsInts.length; j++){
       // System.out.println(getterMethodsInts[j].getName());
        if ((getterMethodsDoubles[i].getName() + "Rank").equals(getterMethodsInts[j].getName())){
          try {
            rank = (int) getterMethodsInts[j].invoke(selected);
          } catch (InvocationTargetException e) {
            e.printStackTrace();
          }
        }
      }
      stats.add(new TeamStat(getterMethodsDoubles[i].getName(), value, rank));
    }
    ObservableList<TeamStat> x = FXCollections.observableArrayList(stats);
    return x;
  }

  private Method[] getGetterI(Method[] methods) {
    Method[] filtered = new Method[methods.length];
    int filteredCount = 0;

    for (Method method : methods) {
      if (method.getReturnType() == Integer.TYPE && method.getName().substring(0, 3).equals("get")) {
        filtered[filteredCount] = method;
        filteredCount++;
      }
    }
    
    return Arrays.copyOf(filtered, filteredCount);
  }

  private static Method[] getGetters(Method[] methods) {
    Method[] filtered = new Method[methods.length];
    int filteredCount = 0;

    for (Method method : methods) {
      if (method.getReturnType() == Double.TYPE && method.getName().substring(0, 3).equals("get")) {
        filtered[filteredCount] = method;
        filteredCount++;
      }
    }
    
    return Arrays.copyOf(filtered, filteredCount);
  }

  private TableView makeTeamTable() {
    try {
      teamStats = setupStats();
    } catch (IllegalArgumentException | IllegalAccessException e1) {
      e1.printStackTrace();
    }
    int colWidth = 152;
    System.out.println(teamStats.size());
    for (TeamStat stat : teamStats){
      System.out.println(stat.stat + " g " + stat.value + " " + stat.rank);
    }
   
    TableView<TeamStat> teamStatsTable = new TableView<TeamStat>();
    TableColumn statCol = new TableColumn("Stat");
    statCol.setCellValueFactory(new PropertyValueFactory<>("Stat"));
    statCol.setPrefWidth(colWidth);

    TableColumn valueCol = new TableColumn("Value");
    valueCol.setCellValueFactory(new PropertyValueFactory<>("Value"));
    valueCol.setPrefWidth(colWidth);

    TableColumn rankCol = new TableColumn("Rank");
    rankCol.setCellValueFactory(new PropertyValueFactory<>("Rank"));
    rankCol.setPrefWidth(colWidth);
    // TableRow row = new TableRow();
    teamStatsTable.getColumns().addAll(statCol, valueCol, rankCol);
    teamStatsTable.setItems(teamStats);

    return teamStatsTable;
  }

  private ComboBox buildTeamBox() {
    ComboBox t;
    ArrayList<String> arr = new ArrayList<String>();
    ObservableList<String> options;
    for (Team item : _teams) {
      arr.add(item.getName());
    }
    options = FXCollections.observableArrayList(arr);

    t = new ComboBox<String>(options);
    return t;
  }

  private ComboBox buildPlayerBox() {
    ComboBox<String> p;
    if (teamBox.getSelectionModel().getSelectedItem() == null)
      throw new RuntimeException("No team chosen");
    Team selected = getSelectedTeam(teamBox.getSelectionModel().getSelectedItem());
    ObservableList<String> options;
    ArrayList<String> players = new ArrayList<String>();
    for (int i = 0; i < selected.getRoster().length; i++) {
      players.add(selected.getRoster()[i].getFirst() + " " + selected.getRoster()[i].getLast());
    }
    options = FXCollections.observableArrayList(players);
    p = new ComboBox<String>(options);
    return p;
  }

  private Team getSelectedTeam(String teamName) {
    for (int i = 0; i < _teams.length; i++) {
      if (_teams[i].getName() == teamName)
        return _teams[i];
    }
    throw new IllegalArgumentException("Team not found");
  }

  private void teamChanged(ObservableValue o, String oldText, String newText) {
    if (!started) {
      started = true;
      return;
    }
    
    for (int i = 0; i < _teams.length; i++){
      if (newText.equals(_teams[i].getName())) selected = _teams[i];
    }
    teamView = makeTeamTable();
    
    }

}
