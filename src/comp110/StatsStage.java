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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StatsStage extends Base {

  private Stage _stage;
  private Scene _scene;
  private boolean started = false;
  private ComboBox<String> teamBox, playerBox;
  private TableView teamView, playerView;
  VBox container;
  HBox combosHolder;
  HBox tablesHolder;
  private ObservableList<Player> _playerObsList;
  private ObservableList<TeamStat> teamStats;
  private Team selected;

  public void makeStatsStage(Team t){
    _stage = new Stage();
    makeStatsGroup(t);
    tablesHolder.getChildren().addAll(teamView, playerView);
    container.getChildren().addAll(combosHolder, tablesHolder);
    _scene = new Scene(container);
    _stage.setScene(_scene);
    _stage.show();
  }
  
  public void makeStatsGroup(Team selected) {
    this.selected = selected;

    try {
      _teams = readJson();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    container = new VBox(10);
    combosHolder = new HBox(10);
    tablesHolder = new HBox(10);
    

    teamBox = buildTeamBox();
    teamBox.getSelectionModel().selectedItemProperty().addListener(this::teamChanged);
    int startIndex = 0;
    // sets the team to be the team chosen in box 1
    for (int i = 0; i < teamBox.getItems().size(); i++) {
      if (teamBox.getItems().get(i).equals(selected.getName()))
        startIndex = i;
    }
    

    _playerObsList = FXCollections.observableArrayList();
    for (int i = 0; i < selected.getRoster().length; i++) {
      _playerObsList.add(selected.getRoster()[i]);
    }
    
    Button showStats = new Button("Detailed Player Stats");
    showStats.setOnAction((playerStatsEvent) -> {
      Stage playerStatStage = new Stage();
      Scene chartScene = new Scene(PlayerStatsStage.makeDetailedPlayerTable(_playerObsList));
      chartScene.getStylesheets().add("file:assets/fextile.css");
      playerStatStage.setTitle(selected.getName() + " Players");
      playerStatStage.setScene(chartScene);
      playerStatStage.show();
    });
    
    teamBox.getSelectionModel().select(startIndex);
    combosHolder.getChildren().addAll(teamBox, showStats);
    playerBox = buildPlayerBox();
    ObservableList<TeamStat> y = null;
    try {
      y = setupStats();
    } catch (IllegalArgumentException | IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    teamView = makeTeamTable(y);
    playerView = makePlayerTable(_playerObsList);
  }
 
  
  private TableView<Player> makePlayerTable(ObservableList<Player> players) {
    TableView<Player> playerStats = new TableView<Player>();
    int colWidth = 100;
    
    String[][] stats = {
        {"Name", "FullName"},
        {"Year", "Year"},
        {"Played", "Played"},
        {"Avg Points", "avgPoints"},
        {"Avg Rebs", "avgRebs"}
    };
    
    TableColumn jerseyCol = new TableColumn("Jersey");
    jerseyCol.setCellValueFactory(new PropertyValueFactory<>("Jersey"));
    playerStats.getColumns().add(jerseyCol);
    
    for (String[] stat : stats) {
      TableColumn currentColumn = new TableColumn(stat[0]);
      currentColumn.setCellValueFactory(new PropertyValueFactory<>(stat[1]));
      playerStats.getColumns().add(currentColumn);
    }

    playerStats.setItems(players);
    playerStats.getSortOrder().add(jerseyCol);
    
    return playerStats;
  }

  private ObservableList<TeamStat> setupStats() throws IllegalArgumentException, IllegalAccessException {
    Class klass = null;
    try {
      klass = Class.forName("comp110.Team");
    } catch (ClassNotFoundException e) {
      System.out.println("crap"); // lol
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

  private TableView makeTeamTable(ObservableList<TeamStat> y) {
    int colWidth = 152;
//    System.out.println(teamStats.size());
//    for (TeamStat stat : teamStats){
//      System.out.println(stat.stat + " g " + stat.value + " " + stat.rank);
//    }
   
    TableView<TeamStat> teamStatsTable = new TableView<TeamStat>();
    TableColumn statCol = new TableColumn("Stat");
    statCol.setCellValueFactory(new PropertyValueFactory<>("Stat"));
    statCol.setPrefWidth(colWidth);

    TableColumn valueCol = new TableColumn("Value");
    valueCol.setCellValueFactory(new PropertyValueFactory<>("Value"));
    valueCol.setPrefWidth(100);
String hi = "";
    TableColumn rankCol = new TableColumn("Rank");
    rankCol.setCellValueFactory(new PropertyValueFactory<>("Rank" + hi));
    rankCol.setPrefWidth(100);
    // TableRow row = new TableRow();
    teamStatsTable.getColumns().addAll(statCol, valueCol, rankCol);
    teamStatsTable.getItems().removeAll();
    teamStatsTable.setItems(y);
    teamStatsTable.getSortOrder().add(statCol);
hi += "hello";
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
//    System.out.println(selected.getName());
    ObservableList<TeamStat> y = null;
    try {
      y = setupStats();
    } catch (IllegalArgumentException | IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    _playerObsList = FXCollections.observableArrayList();
    for (int i = 0; i < selected.getRoster().length; i++) {
      _playerObsList.add(selected.getRoster()[i]);
    }
//    teamView = makeTeamTable(y);
    playerView.setItems(_playerObsList);
    teamView.setItems(y);
    }

}
