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
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Base extends Application {
  // hi
  private Stage              _stage;
  private Scene              _scene;
  private TabPane            _pane;
  private Tab                matchupTab, textStats, graphStats;
  private Team[]             _teams;
  private Map<Integer, Team> teamMap;
  private ComboBox<String>   _box1;
  private ComboBox<String>   _box2;
  private Label              winner;
  private boolean            ran = false;
  private Matchup            _matchup;
  private Team               homeTeam, awayTeam;
  public static double[]     homeScores;
  public static double[]     awayScores;
  public static String[]     scoringFields;
  private Rectangle2D screenBounds;

  @Override
  public void start(Stage stage) throws Exception {
    screenBounds = Screen.getPrimary().getBounds();
    System.out.println(Screen.getPrimary().getBounds().getHeight());
    _stage = stage;
    _pane = new TabPane();
    _pane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
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
    GridPane goo = Pane2Generator.Pane2(m); // Max: me testing Pane2Gen getting
                                            // for for FX nothing meaningful yet
    textStats.setContent(goo);
    // _pane.getTabs().addAll(matchupTab, textStats, graphStats);
    _pane.getTabs().add(matchupTab);
    // this.initializeStage();

    // Matchup matchup = new Matchup(//GET TEAMS SOMEHOW) //Max: the TeamMap is
    // a good idea made method for it
  }

  private void setupMainPane(Pane mainPane) {
    _box1.setLayoutX(60);
    _box1.setLayoutY(200);
    _box2.setLayoutX(540);
    _box2.setLayoutY(200);
    Label versus = new Label("VERSUS");
    versus.setLayoutX(380);
    versus.setLayoutY(200);
    versus.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));
    Button run = new Button("Run Match");
    run.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent event) {
        boolean boxesFilled = true;
        String homeString = "", awayString = "";
        // tries to get names of chosen teams
        try {
          homeString = (String) _box1.getSelectionModel().getSelectedItem().toString();
          awayString = (String) _box2.getSelectionModel().getSelectedItem().toString();
        } catch (NullPointerException e) {
          boxesFilled = false;
        }
        // gets team object based on team name and sets up resulting label
        // accordingly
        if (!ran && boxesFilled) {
          Team home = null, away = null;
          for (int i = 0; i < _teams.length; i++) {
            if (_teams[i].getName().equals(homeString))
              home = _teams[i];
            if (_teams[i].getName().equals(awayString))
              away = _teams[i];
          }

          _matchup = new Matchup(home, away);
          homeTeam = home;
          awayTeam = away;
          winner = new Label("The winnner is: " + _matchup.get_winner().getName());
          winner.setLayoutX(300);
          winner.setLayoutY(700);
          winner.setBackground(
              new Background(new BackgroundFill(Color.LIGHTPINK, null, null)));
          mainPane.getChildren().add(winner);
          GridPane stats = Pane2Generator.Pane2(_matchup);
          textStats.setContent(stats);
          Pane pane3 = generatePane3();
          graphStats.setContent(pane3);
          _pane.getTabs().add(textStats);
          _pane.getTabs().add(graphStats);
        }
      }

    });
    // this part may look a bit weird if you aren't too familiar with fx, it's a
    // lot of formatting stuff
    run.setLayoutX(365);
    run.setLayoutY(600); // setLayoutX/Y just sets the coordinates of a node on
                         // the screen (only works well with a plain pane)
    BackgroundFill x = new BackgroundFill(Color.LIGHTGREEN, null, null);// this
                                                                        // takes
                                                                        // a
                                                                        // color,
                                                                        // insets,
                                                                        // and
                                                                        // something
                                                                        // else
                                                                        // I
                                                                        // can't
                                                                        // remember
                                                                        // but I
                                                                        // just
                                                                        // leave
                                                                        // them
                                                                        // null
                                                                        // for
                                                                        // this
    Background y = new Background(x);// need a background object which takes any
                                     // subclass of background (there are others
                                     // besides backgroundfill
    run.setBackground(y);
    Image court = new Image("file:assets/ncaa_court.jpg");
    ImageView view = new ImageView(court);// put an image in an imageview node
                                          // so that the size and position can
                                          // be changed
    view.setLayoutX(-157);// this and the next line will probably need to be
                         // changed, i just moved the image manually because it
                         // was starting in a weird place
    view.setLayoutY(100);
    view.setScaleY(_scene.getHeight() / court.getHeight());// scaling the image
                                                           // up/down based on
                                                           // its size compared
                                                           // to the scene
    view.setScaleX(_scene.getWidth() / court.getWidth());
    mainPane.getChildren().addAll(view, _box1, _box2, versus, run);
    matchupTab.setContent(mainPane);
  }

  private void initializeStage() {
    _stage.setTitle("COMP110 PS0X - NCAA Bracket");
    Group g = new Group();
    g.getChildren().add(_pane);
    _pane.setPrefHeight(800);
    _pane.setPrefWidth(800);
    g.prefHeight(800);
    _scene = new Scene(g);
    _stage.setScene(_scene);
    _stage.show();
    if (1100 >= screenBounds.getHeight() * .9) {
      double yTranslate = .001875 * screenBounds.getHeight() -.7;
      Scale scale = new Scale(1,  yTranslate);
      scale.setPivotX(0);
      scale.setPivotY(0);
      _scene.getRoot().getTransforms().setAll(scale);
      _stage.setHeight(_pane.getPrefHeight() * yTranslate + 41);
     // _stage.centerOnScreen();
    }
    
   
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
    ImageView view1 = new ImageView();
    ImageView view2 = new ImageView();
    options = FXCollections.observableArrayList(arr);
    _box1 = new ComboBox(options);
    _box2 = new ComboBox(options);

    _box1.valueProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue o, String oldText, String newText) {

        Team home = null;
        for (int i = 0; i < _teams.length; i++) {
          if (_teams[i].getName().equals(newText))
            home = _teams[i];
        }

        Image team1 = new Image("file:assets/" + home.getImagePath(), 200, 200, false,
            true);
        // new ImageView(team1);
        view1.setImage(team1);
        Group imageHolder = new Group();
        imageHolder.getChildren().add(view1);
        view1.setScaleY(_scene.getHeight() / team1.getHeight());// scaling the
                                                                // image up/down
                                                                // based on its
                                                                // size compared
                                                                // to the scene
        view1.setScaleX(_scene.getWidth() / team1.getWidth());
        // view1.setLayoutX(50);
        // view1.setLayoutY(400);
        double xScale = 200 / imageHolder.getLayoutBounds().getWidth();
        double yScale = 250 / imageHolder.getLayoutBounds().getHeight();
        /*
         * view1.setScaleX(xScale); view1.setScaleY(yScale);
         * view1.setLayoutX(0); view1.setLayoutY(220);
         */
        imageHolder.setScaleX(xScale);
        imageHolder.setScaleY(yScale);
        imageHolder.setLayoutX(65);
        imageHolder.setLayoutY(280);
        imageHolder.maxWidth(200);
        
        for (int i = 0; i < pane.getChildren().size(); i++) {
          if (pane.getChildren().get(i) == imageHolder) {
            return;
          }
        }
        pane.getChildren().add(imageHolder);
      }
    });

    _box2.valueProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue o, String oldText, String newText) {
        Team home = null;
        for (int i = 0; i < _teams.length; i++) {
          if (_teams[i].getName().equals(newText))
            home = _teams[i];
        }
        Image team1 = new Image("file:assets/" + home.getImagePath(), 200, 200, false,
            true);
        view2.setImage(team1);
        view2.setScaleY(_scene.getHeight() / team1.getHeight());// scaling the
                                                                // image up/down
                                                                // based on its
                                                                // size compared
                                                                // to the scene
        view2.setScaleX(_scene.getWidth() / team1.getWidth());
        double xScale = 200 / team1.getWidth();
        double yScale = 250 / team1.getHeight();
        view2.setScaleX(xScale);
        view2.setScaleY(yScale);
        view2.setLayoutX(540);
        view2.setLayoutY(280);
        for (int i = 0; i < pane.getChildren().size(); i++) {
          if (pane.getChildren().get(i) == view2) {
            return;
          }
        }
        pane.getChildren().add(view2);
      }
    });
  }

  private Pane generatePane3() {
    Pane pane = new Pane();
    VBox box = new VBox(5);
    // pane.getChildren().add(new Label("testing"));
    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    final BarChart<String, Number> team1 = new BarChart<String, Number>(xAxis, yAxis);
    team1.setTitle(homeTeam.getName() + " Score Summary");
    xAxis.setLabel("Scoring area");
    yAxis.setLabel("Score");
    XYChart.Series seriesHome = new XYChart.Series();
    for (int i = 0; i < homeScores.length; i++) {
      seriesHome.getData().add(new XYChart.Data(scoringFields[i], homeScores[i]));
    }
    team1.getData().add(seriesHome);
    box.getChildren().add(team1);
    final CategoryAxis xAxis2 = new CategoryAxis();
    final NumberAxis yAxis2 = new NumberAxis();
    final BarChart<String, Number> team2 = new BarChart<String, Number>(xAxis2, yAxis2);
    team2.setTitle(awayTeam.getName() + " Score Summary");
    xAxis2.setLabel("Scoring area");
    yAxis2.setLabel("Score");
    XYChart.Series seriesAway = new XYChart.Series();
    for (int i = 0; i < awayScores.length; i++) {
      seriesAway.getData().add(new XYChart.Data(scoringFields[i], awayScores[i]));
    }
    team2.getData().add(seriesAway);
    box.getChildren().add(team2);
    pane.getChildren().add(box);
    return pane;
  }

}
