package comp110;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Base extends Application {
  //hi
  Stage _stage;
  Scene _scene;
  TabPane _pane;
  Tab matchupTab, textStats, graphStats;
  Team[] _teams;
  Map<Integer, Team> teamMap;
  ComboBox _box1;
  ComboBox _box2;
  Label winner;
  boolean ran = false;
  Matchup _matchup;

  @Override
  public void start(Stage stage) throws Exception {
    _stage = stage;
    _pane = new TabPane();
    _teams = readJson();
    teamMap = readJSON();
    buildBox();
    matchupTab = new Tab();
    textStats = new Tab();
    graphStats = new Tab();
    matchupTab.setText("Matchup");
    textStats.setText("Stats");
    graphStats.setText("Graphs");
    Pane mainPane = new Pane();
    setupMainPane(mainPane);
    _pane.getTabs().addAll(matchupTab, textStats, graphStats);
    Matchup m = new Matchup(teamMap.get(457), teamMap.get(193));
    GridPane goo = Pane2Generator.Pane2(m); //Max: me testing Pane2Gen getting for for FX nothing meaningful yet
    textStats.setContent(goo);
    this.initializeStage();

    // Matchup matchup = new Matchup(//GET TEAMS SOMEHOW) //Max: the TeamMap is a good idea made method for it

  }

  private void setupMainPane(Pane mainPane) {
    _box1.setLayoutX(20);
    _box1.setLayoutY(200);
    _box2.setLayoutX(520);
    _box2.setLayoutY(200);
    Label versus = new Label("VERSUS");
    versus.setLayoutX(350);
    versus.setLayoutY(200);
    Button run = new Button("Run Match");
    run.setOnAction(new EventHandler<ActionEvent>(){
      public void handle(ActionEvent event) {
        if (ran == false){
          String homeString = (String) _box1.getSelectionModel().getSelectedItem().toString(); 
          String awayString = (String) _box2.getSelectionModel().getSelectedItem().toString(); 
          Team home = null, away = null;
          for (int i = 0; i < _teams.length; i++){
            if (_teams[i].getName().equals(homeString)) home = _teams[i];
            if (_teams[i].getName().equals(awayString)) away = _teams[i];
          }
          _matchup = new Matchup(home, away);
          winner = new Label("The winnner is: " + _matchup.get_winner().getName());
          winner.setLayoutX(335);
          winner.setLayoutY(700);
          winner.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, null, null)));
          mainPane.getChildren().add(winner);
        }
      }
    });
    run.setLayoutX(335);
    run.setLayoutY(600);
    BackgroundFill x = new BackgroundFill(Color.LIGHTGREEN, null, null);
    Background y = new Background(x);
    run.setBackground(y);
    Image court = new Image("file:assets/court.jpg");
    ImageView view = new ImageView(court);
    view.setLayoutX(200);
    view.setLayoutY(280);
    view.setScaleY(800/court.getHeight());
    view.setScaleX(800/court.getWidth());
    
    BackgroundImage background = new BackgroundImage(court, null, null, null, null);
    Background back = new Background(background);
   // mainPane.setBackground(back);
    mainPane.getChildren().addAll(view,_box1, _box2, versus, run);
    matchupTab.setContent(mainPane);
  }

  private void initializeStage() {
    _stage.setTitle("COMP110 PS0X - NCAA Bracket");
    _scene = new Scene(_pane, 800, 800);
    _stage.setScene(_scene);
    _stage.show();
  }

  private static Team[] readJson() throws FileNotFoundException {
    JsonReader reader = new JsonReader(new FileReader("acc.json"));
    Gson gson = new Gson();
    Team[] teams = gson.fromJson(reader, Team[].class);

    Map<Integer, Team> teamMap = new HashMap<Integer, Team>();
    for (Team team : teams) {
      teamMap.put(team.getId(), team);
    }
    return teams;

  }

  private static Map<Integer, Team> readJSON() throws FileNotFoundException {
    JsonReader reader = new JsonReader(new FileReader("acc.json"));
    Gson gson = new Gson();
    Team[] teams = gson.fromJson(reader, Team[].class);

    Map<Integer, Team> teamMap = new HashMap<Integer, Team>();
    for (Team team : teams) {
      teamMap.put(team.getId(), team);
    }
    return teamMap;

  }

  private void buildBox() {
    ArrayList<String> arr = new ArrayList<String>();
    ObservableList<String> options;
    for (Team item : _teams) {
      arr.add(item.getName());
    }
    options = FXCollections.observableArrayList(arr);
    _box1 = new ComboBox(options);
    _box2 = new ComboBox(options);
  }

}
