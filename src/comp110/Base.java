package comp110;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
  ComboBox<String> _box1;
  ComboBox<String> _box2;
  Label winner;
  boolean ran = false;
  Matchup _matchup;

  @Override
  public void start(Stage stage) throws Exception {
    _stage = stage;
    _pane = new TabPane();
    _teams = readJson();
    teamMap = readJSON();
    initializeStage();
    matchupTab = new Tab();
    textStats = new Tab();
    graphStats = new Tab();
    matchupTab.setText("Matchup");
    textStats.setText("Stats");
    graphStats.setText("Graphs");
    Pane mainPane = new Pane();
    buildBox(mainPane);
    setupMainPane(mainPane);
    Matchup m = new Matchup(teamMap.get(457), teamMap.get(193));
    GridPane goo = Pane2Generator.Pane2(m); //Max: me testing Pane2Gen getting for for FX nothing meaningful yet
    textStats.setContent(goo);
    //_pane.getTabs().addAll(matchupTab, textStats, graphStats);
  _pane.getTabs().add(matchupTab);
    //this.initializeStage();

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
    versus.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE,null,null)));
    Button run = new Button("Run Match");
    run.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent event) {
        boolean boxesFilled = true;
        String homeString = "", awayString = "";
        //tries to get names of chosen teams
        try {
          homeString = (String) _box1.getSelectionModel().getSelectedItem().toString();
          awayString = (String) _box2.getSelectionModel().getSelectedItem().toString();
        } catch (NullPointerException e) {
          boxesFilled = false;
        }
        //gets team object based on team name and sets up resulting label accordingly
        if (!ran && boxesFilled) {
          Team home = null, away = null;
          for (int i = 0; i < _teams.length; i++) {
            if (_teams[i].getName().equals(homeString)) home = _teams[i];
            if (_teams[i].getName().equals(awayString)) away = _teams[i];
          }         
                
          _matchup = new Matchup(home, away);
          winner = new Label("The winnner is: " + _matchup.get_winner().getName());
          winner.setLayoutX(335);
          winner.setLayoutY(700);
          winner.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, null, null)));
          mainPane.getChildren().add(winner);
          GridPane stats = Pane2Generator.Pane2(_matchup);
          textStats.setContent(stats);
          _pane.getTabs().add(textStats);
        }
      }

    });
    //this part may look a bit weird if you aren't too familiar with fx, it's a lot of formatting stuff
    run.setLayoutX(335);
    run.setLayoutY(600); //setLayoutX/Y just sets the coordinates of a node on the screen (only works well with a plain pane)
    BackgroundFill x = new BackgroundFill(Color.LIGHTGREEN, null, null);//this takes a color, insets, and something else I can't remember but I just leave them null for this
    Background y = new Background(x);//need a background object which takes any subclass of background (there are others besides backgroundfill
    run.setBackground(y);
    Image court = new Image("file:assets/courtTry2.jpg");
    ImageView view = new ImageView(court);//put an image in an imageview node so that the size and position can be changed
    view.setLayoutX(118);//this and the next line will probably need to be changed, i just moved the image manually because it was starting in a weird place    
    view.setLayoutY(243);
    view.setScaleY(_scene.getHeight() / court.getHeight());//scaling the image up/down based on its size compared to the scene
    view.setScaleX(_scene.getWidth() / court.getWidth());
    mainPane.getChildren().addAll(view, _box1, _box2, versus, run);
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

  private void buildBox(Pane pane) {
    ArrayList<String> arr = new ArrayList<String>();
    ObservableList<String> options;
    for (Team item : _teams) {
      arr.add(item.getName());
    }
    options = FXCollections.observableArrayList(arr);
    _box1 = new ComboBox(options);
    _box2 = new ComboBox(options);
    
    _box1.valueProperty().addListener(new ChangeListener<String>() {
      @Override public void changed(ObservableValue o, String oldText, String newText){
          Team home = null;
          for (int i = 0; i < _teams.length; i++) {
            if (_teams[i].getName().equals(newText)) home = _teams[i];
          }
          Image team1 = new Image("file:assets/" + home.getImagePath());
          ImageView view1 = new ImageView(team1);
          view1.setScaleY(_scene.getHeight() / team1.getHeight());//scaling the image up/down based on its size compared to the scene
          view1.setScaleX(_scene.getWidth() / team1.getWidth());
          view1.setLayoutX(50);    
          view1.setLayoutY(400);
          pane.getChildren().add(view1);
      }
    });
    
    _box2.valueProperty().addListener(new ChangeListener<String>() {
      @Override public void changed(ObservableValue o, String oldText, String newText){
          Team home = null;
          for (int i = 0; i < _teams.length; i++) {
            if (_teams[i].getName().equals(newText)) home = _teams[i];
          }
          Image team1 = new Image("file:assets/" + home.getImagePath());
          ImageView view1 = new ImageView(team1);
          view1.setScaleY(_scene.getHeight() / team1.getHeight());//scaling the image up/down based on its size compared to the scene
          view1.setScaleX(_scene.getWidth() / team1.getWidth());
          view1.setLayoutX(50);    
          view1.setLayoutY(400);
          pane.getChildren().add(view1);
      }
    });
  }

}
