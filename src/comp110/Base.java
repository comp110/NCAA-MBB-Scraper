package comp110;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Base extends Application {
  boolean x = false;
  private double width, height;
  private Stage _stage;
  private Scene _scene;
  private TabPane _pane;
  private Tab matchupTab, textStats, graphStats;
  private Team[] _teams;
  private Map<Integer, Team> teamMap;
  private ComboBox<String> _box1;
  private ComboBox<String> _box2;
  private Label winner, homePointsLabel, awayPointsLabel;
  private boolean ran = false;
  private Matchup _matchup;
  private Team homeTeam, awayTeam;
  public static double[] homeScores;
  public static double[] awayScores;
  private static double[] getHomeAwayScore;
  public static String[] scoringFields;
  private static double _homeTotal, _awayTotal;
  private static ObservableList<MethodOutput> _methodOutputs;
  private String _homePointsString, _awayPointsString;
  
  private Rectangle2D screenBounds;
  private ImageView view2;
  private ImageView view1;
  private Pane mainPane;
  private ScrollPane _statsScroll;
  private TableView _outputTable;
  private Group cboxGroup1, cboxGroup2, logoGroup1, logoGroup2, courtGroup, labelGroup, scrollGroup, scoreLabels;
  private Group nodeGroup, showChartGroup;
  private ImageView view;
  private String _awayScoreString;
  private String _homeScoreString;

  @Override
  public void start(Stage stage) throws Exception {
    initializeGroups();
    screenBounds = Screen.getPrimary().getBounds();
    //System.out.println(Screen.getPrimary().getBounds().getHeight());
    _stage = stage;
    _pane = new TabPane();
    _pane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
    _teams = readJson();
    teamMap = readJSON();
    mainPane = new Pane();
    initializeStage();
    matchupTab = new Tab();
    textStats = new Tab();
    graphStats = new Tab();
    matchupTab.setText("Matchup");
    textStats.setText("Stats");
    graphStats.setText("Graphs");
    buildBox(mainPane);
    setupMainPane(mainPane);
    Matchup m = new Matchup(teamMap.get(457), teamMap.get(193));// Max: me testing Pane2Gen getting
    // for for FX nothing meaningful yet
    //    textStats.setContent(goo);
    // _pane.getTabs().addAll(matchupTab, textStats, graphStats);
    // _pane.getTabs().add(matchupTab);
    // this.initializeStage();

    // Matchup matchup = new Matchup(//GET TEAMS SOMEHOW) //Max: the TeamMap is
    // a good idea made method for it
  }

  private void initializeGroups() {
    cboxGroup1 = new Group();
    cboxGroup2 = new Group();
    logoGroup1 = new Group();
    logoGroup2 = new Group();
    courtGroup = new Group();
    labelGroup = new Group();
    scrollGroup = new Group();
    nodeGroup = new Group();
    scoreLabels = new Group();
    showChartGroup = new Group();
  }

  private void setupMainPane(Pane mainPane) {
    _box1.setLayoutX(width * .065);
    _box1.setLayoutY(height * .41);
    _box2.setLayoutX(width * .725);
    _box2.setLayoutY(height * .41);
    cboxGroup1.getChildren().addAll(_box1);
    cboxGroup2.getChildren().addAll(_box2);
    Button run = new Button("Run Match");
    // All of the following happens when the "Run match" button is pressed
    run.setOnAction((event) -> {
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
          if (_teams[i].getName().equals(homeString)) home = _teams[i];
          if (_teams[i].getName().equals(awayString)) away = _teams[i];
        }

        _matchup = new Matchup(home, away);
        homeTeam = home;
        awayTeam = away;

        // Adding various nodes to the stage
        _outputTable = TableViewGenerator.makeTable(_matchup);
        _outputTable.setScaleX(.8);
        _outputTable.setScaleY(.8);

        scrollGroup.getChildren().add(_outputTable);
        scrollGroup.setLayoutX(width * .3);
        scrollGroup.setLayoutY(height * .36);
        
        DecimalFormat df1 = new DecimalFormat("###.#");
        double getHomeScoreOut = getHomeAwayScore[0];
        double getAwayScoreOut = getHomeAwayScore[1];
        _homeScoreString = Double.toString(Double.valueOf(df1.format(getHomeScoreOut)));
        _awayScoreString = Double.toString(Double.valueOf(df1.format(getAwayScoreOut)));
        
        scoreLabels.getChildren().removeAll(awayPointsLabel, homePointsLabel);
        
        homePointsLabel = new Label(_homeScoreString);
        homePointsLabel.getStyleClass().add("scorelabel");
        homePointsLabel.setLayoutX(width * .018);
        homePointsLabel.setLayoutY(height * .46);

        awayPointsLabel = new Label(_awayScoreString);
        awayPointsLabel.getStyleClass().add("scorelabel");
        awayPointsLabel.setLayoutX(width * .853);
        awayPointsLabel.setLayoutY(height * .46);

        // Turns the scoreboard label blue if the team won and leaves it red otherwise
        if (_matchup.getWinner() != null) {
        	if (_matchup.getWinner().getName().equals(_matchup.getHomeTeam().getName())) {
        		homePointsLabel.getStyleClass().add("winner_score_label");
        	}
        	else {
        		awayPointsLabel.getStyleClass().add("winner_score_label");
        	}
        }
        //        mainPane.getChildren().add(awayPointsLabel);
        //        mainPane.getChildren().add(homePointsLabel);
        scoreLabels.getChildren().addAll(awayPointsLabel, homePointsLabel);
        
        Button showChart = new Button("Show Chart");
        showChart.setOnAction((showGraphEvent) -> {
          Stage chartStage = new Stage();
          Scene chartScene = new Scene(generateChart());
          chartScene.getStylesheets().add("file:assets/fextile.css");
          chartStage.setTitle(_matchup.getHomeTeam().getName()
              + " vs. " +_matchup.getAwayTeam().getName());
          chartStage.setScene(chartScene);
          chartStage.show();
        });
        showChart.setLayoutX(width * .59);
        showChart.setLayoutY(height * .35);
        showChartGroup.getChildren().add(showChart);
        
      }
      
    });
    // this part may look a bit weird if you aren't too familiar with fx, it's a
    // lot of formatting stuff
    run.setLayoutX(width * .46);
    run.setLayoutY(height * .35); // setLayoutX/Y just sets the coordinates of a node on
    // the screen (only works well with a plain pane)
    BackgroundFill x = new BackgroundFill(Color.LIGHTGREEN, null, null);
    Background y = new Background(x);// need a background object which takes any
                                     // subclass of background (there are others
                                     // besides backgroundfill
    run.setBackground(y);

    //    view.setLayoutX(-157);
    //    view.setLayoutY(100);
    //    view.setScaleY(_scene.getHeight() / court.getHeight());
    //    view.setScaleX(_scene.getWidth() / court.getWidth());
    //mainPane.getChildren().addAll(_box1, _box2, run);
    nodeGroup.getChildren().addAll(run);
    matchupTab.setContent(mainPane);
  }

  private void initializeStage() {
    _stage.setTitle("COMP110 PS0X - NCAA Bracket");
    Image court = new Image("file:assets/ncaa_court.jpg");
    view = new ImageView(court);

//    if (800 <= screenBounds.getHeight() * .9) {
      view.setFitWidth(1100);
      cboxGroup1.setScaleX(.9);
      cboxGroup1.setScaleY(.9);
      cboxGroup2.setScaleX(.9);
      cboxGroup2.setScaleY(.9);
      nodeGroup.setScaleX(.9);
      nodeGroup.setScaleY(.9);
      logoGroup1.setScaleX(.9);
      logoGroup1.setScaleY(.7);
      logoGroup2.setScaleX(.9);
      logoGroup2.setScaleY(.7);
      scoreLabels.setScaleX(.8);
      scoreLabels.setScaleY(.8);
      showChartGroup.setScaleX(.9);
      showChartGroup.setScaleY(.9);
//    }
//    else if (800 >= screenBounds.getHeight() * .9) {
//      double translate = .001875 * screenBounds.getHeight() - .7;
//      view.setFitWidth(1100 * translate);
//      nodeGroup.setScaleX(translate);
//      nodeGroup.setScaleY(translate);
//      showChartGroup.setScaleX(translate);
//      showChartGroup.setScaleY(translate);
//    }
    width = view.getLayoutBounds().getWidth();
    height = view.getLayoutBounds().getHeight();
    view.setPreserveRatio(true);
    _pane.setPrefSize(view.getFitWidth(), view.getFitHeight());
    view.setPreserveRatio(true);
    _pane.setPrefSize(view.getFitWidth(), view.getFitHeight());

    // mainPane.setPrefWidth(view.getFitWidth());
    //mainPane.setPrefHeight(view.getFitHeight());
    //matchupTab.setContent(view);
    //courtGroup.getChildren().add(matchupTab);
    courtGroup.getChildren().add(view);
    //nodeGroup.getChildren().add(view);

    mainPane.getChildren().addAll(courtGroup, nodeGroup, logoGroup1, logoGroup2,
        scoreLabels, cboxGroup1, cboxGroup2, showChartGroup, scrollGroup);

    // _pane.getTabs().add(matchupTab);
    Group g = new Group();
    g.getChildren().add(mainPane);
    // _pane.setPrefHeight(800);
    // _pane.setPrefWidth(800);
    // g.prefHeight(800);
    _scene = new Scene(g);
    _scene.getStylesheets().add("file:assets/fextile.css");
    _stage.setScene(_scene);
    _stage.show();
    //scales based on screen size

    //_stage.setResizable(false);

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
    ArrayList<String> arr2 = new ArrayList<String>();
    ObservableList<String> options, options2;
    for (Team item : _teams) {
      arr.add(item.getName());
      arr2.add(item.getName());
    }
    view1 = new ImageView();
    view2 = new ImageView();
    view1.scaleXProperty().addListener((r, x, y) -> {
      view1.setLayoutX(width * .065);
      view1.setLayoutY(height * .05);
    });
    options = FXCollections.observableArrayList(arr);
    options2 = FXCollections.observableArrayList(arr2);
    _box1 = new ComboBox(options);
    _box2 = new ComboBox(options2);
    _box1.getSelectionModel().selectedItemProperty().addListener(this::changed);
    _box2.getSelectionModel().selectedItemProperty().addListener(this::changed);

  }

  private Pane generateChart() {
	  Pane pane = new Pane();
	  VBox box = new VBox(5);
	  NumberAxis xAxis = new NumberAxis();
	  CategoryAxis yAxis = new CategoryAxis();
	  BarChart<Number,String> barChart = 
			  new BarChart<Number,String>(xAxis,yAxis);
	  barChart.setTitle("Method Outputs");
	  xAxis.setLabel("Value");
	  yAxis.setLabel("");    

	  XYChart.Series homeSeries = new XYChart.Series();
	  homeSeries.setName(
			  _matchup.getHomeTeam().getName() + " (" + _homeScoreString + ")");

	  XYChart.Series awaySeries = new XYChart.Series();
	  awaySeries.setName(_matchup.getAwayTeam().getName() + " (" + _awayScoreString + ")");

	  for (MethodOutput out : _methodOutputs) {
		  homeSeries.getData().add(new XYChart.Data(out.getHomeValue(), out.getName()));
		  awaySeries.getData().add(new XYChart.Data(out.getAwayValue(), out.getName()));
	  }

	  if (_matchup.getWinner().getName().equals(_matchup.getHomeTeam().getName())) {
		  barChart.getData().addAll(homeSeries, awaySeries);
	  } else {
		  barChart.getData().addAll(awaySeries, homeSeries);
	  }
	  barChart.setMinHeight(600);
	  barChart.setMinWidth(790);
	  box.getChildren().add(barChart);
	  pane.getChildren().add(box);
	  return pane;
  }

  public void changed(ObservableValue o, String old, String newText) {
    Team home = null;
    ImageView inFocus = null;
    if (o == _box1.getSelectionModel().selectedItemProperty()) {
      inFocus = view1;
      //      System.out.println("test");
    }
    else if (o == _box2.getSelectionModel().selectedItemProperty()) inFocus = view2;
    else {
      System.out.println("error");
      return;
    }
    for (int i = 0; i < _teams.length; i++) {
      if (_teams[i].getName().equals(newText)) home = _teams[i];
    }
    if (x) {
      System.out.println();
    }
    x = true;
    Image team1 = new Image("file:assets/" + home.getImagePath(), 200, 200, false, true);
    inFocus.setImage(team1);
    //inFocus.setScaleY(_scene.getHeight() / team1.getHeight());// scaling the
    //inFocus.setScaleX(_scene.getWidth() / team1.getWidth());
    // double xScale = 200 / team1.getWidth();
    // double yScale = 250 / team1.getHeight();
    //inFocus.setScaleX(xScale);
    //inFocus.setScaleY(yScale);
    if (inFocus == view2) {
      inFocus.setLayoutX(width * .74);
      inFocus.setLayoutY(height * .05);
    }
    else if (inFocus == view1) {
      inFocus.setLayoutX(width * .08);
      inFocus.setLayoutY(height * .05);
    }
    ComboBox focusBox = null;
    if (o == _box1.getSelectionModel().selectedItemProperty()) focusBox = _box2;
    else if (o == _box2.getSelectionModel().selectedItemProperty()) focusBox = _box1;
    for (int i = 0; i < _box1.getItems().size(); i++) {
      if (focusBox.getItems().get(i).equals(home.getName())) {
        focusBox.getItems().remove(i);
        break;
      }
    }
    if (old != null) focusBox.getItems().add(old);
    if (o == _box1.getSelectionModel().selectedItemProperty()) {
      for (int i = 0; i < logoGroup1.getChildren().size(); i++) {
        if (logoGroup1.getChildren().get(i) == inFocus) {
          return;
        }
      }
    }
    else {
      for (int i = 0; i < logoGroup2.getChildren().size(); i++) {
        if (logoGroup2.getChildren().get(i) == inFocus) {
          return;
        }
      }
    }
    //logoGroup.getChildren().add(inFocus);
    if (o == _box1.getSelectionModel().selectedItemProperty()) {
      logoGroup1.getChildren().add(inFocus);
    }
    else {
      logoGroup2.getChildren().add(inFocus);
    }

  }

  public static void setHomeScore(double score) {
    _homeTotal = score;
  }

  public static void setAwayScore(double score) {
    _awayTotal = score;
  }
  
  public static void setMethodOutputs(ObservableList<MethodOutput> methodOutputs) {
    _methodOutputs = methodOutputs;
  }

	public static void setHomeAwayScoreOutputs(double[] homeAwayScoreOutputs) {
		getHomeAwayScore = homeAwayScoreOutputs;
	}
  
  

}
