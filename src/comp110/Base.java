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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.geometry.Side;
import javafx.scene.Group;
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
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
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
import javafx.scene.transform.Scale;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Base extends Application {

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
  public static String[] scoringFields;
  private static double _homeTotal, _awayTotal;
  private static ObservableList<MethodOutput> _methodOutputs;
  private static String _homeScoreString, _awayScoreString;
  private Rectangle2D screenBounds;
  private ImageView view2;
  private ImageView view1;
  private Pane mainPane;
  private ScrollPane _statsScroll;
  private TableView _outputTable;
  private BarChart _horizontalChart;
 private Group cboxGroup1, cboxGroup2, logoGroup1, logoGroup2, courtGroup, labelGroup, scrollGroup, scoreLabels;
  private Group nodeGroup;
  private ImageView view;
  

  @Override
  public void start(Stage stage) throws Exception {
    screenBounds = Screen.getPrimary().getBounds();
    //System.out.println(Screen.getPrimary().getBounds().getHeight());
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
    mainPane = new Pane();
    buildBox(mainPane);
    setupMainPane(mainPane);
    Matchup m = new Matchup(teamMap.get(457), teamMap.get(193));// Max: me testing Pane2Gen getting
                                            // for for FX nothing meaningful yet
//    textStats.setContent(goo);
    // _pane.getTabs().addAll(matchupTab, textStats, graphStats);
    _pane.getTabs().add(matchupTab);
    // this.initializeStage();

    // Matchup matchup = new Matchup(//GET TEAMS SOMEHOW) //Max: the TeamMap is
    // a good idea made method for it
  }

  private void setupMainPane(Pane mainPane) {
    _box1.setLayoutX(60);
    _box1.setLayoutY(310);
    _box2.setLayoutX(507);
    _box2.setLayoutY(310);
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
        
        mainPane.getChildren().removeAll(_outputTable, homePointsLabel, awayPointsLabel);
        
        // All of the following is just adding various nodes to the stage
        GridPane stats = Pane2Generator.Pane2(_matchup);
        textStats.setContent(stats);
        _pane.getTabs().add(textStats);
        
        
        _outputTable = TableViewGenerator.makeTable(_matchup);
        _outputTable.setLayoutX(177);
        _outputTable.setLayoutY(430);
        _outputTable.setMinWidth(450);
        _outputTable.setMaxHeight(300);
//        _outputTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        mainPane.getChildren().add(_outputTable);
        

		DecimalFormat df1 = new DecimalFormat("###.#");
		_homeScoreString = Double.toString(Double.valueOf(df1.format(_homeTotal)));
		_awayScoreString = Double.toString(Double.valueOf(df1.format(_awayTotal)));
		
        homePointsLabel = new Label(_homeScoreString);
        // Positions score label in center even if it is 5 chars or 3 chars
        if (_homeScoreString.length() == 5) {
        homePointsLabel.setLayoutX(width * .09);
        homePointsLabel.setLayoutY(height * .46);
        } else if (_homeScoreString.length() == 3) {
        homePointsLabel.setLayoutX(width * .09);
        homePointsLabel.setLayoutY(height * .46);
        } else {
        homePointsLabel.setLayoutX(width * .09);
        homePointsLabel.setLayoutY(height * .46);
        }
        homePointsLabel.getStyleClass().add("scorelabel");
        
        awayPointsLabel = new Label(_awayScoreString);
        if (_awayScoreString.length() == 5) {
        awayPointsLabel.setLayoutX(width * .85);
        awayPointsLabel.setLayoutY(height * .46);
        } else if (_awayScoreString.length() == 3) {
        awayPointsLabel.setLayoutX(width * .85);
        awayPointsLabel.setLayoutY(height * .46);
        } else {
        awayPointsLabel.setLayoutX(width * .85);
        awayPointsLabel.setLayoutY(height * .46);
        }
        awayPointsLabel.getStyleClass().add("scorelabel");
        
        // Turns the scoreboard label blue if the team won and leaves it red otherwise
        if (_homeTotal > _awayTotal) {
        	homePointsLabel.getStyleClass().add("winner_score_label");
        } else {
        	awayPointsLabel.getStyleClass().add("winner_score_label");
        }
        scoreLabels.getChildren().addAll(awayPointsLabel, homePointsLabel);
        
        Pane pane3 = generatePane3();
        graphStats.setContent(pane3);
        _pane.getTabs().add(graphStats);
      }
    });
    // this part may look a bit weird if you aren't too familiar with fx, it's a
    // lot of formatting stuff
    run.setLayoutX(358);
    run.setLayoutY(310); // setLayoutX/Y just sets the coordinates of a node on
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
    mainPane.getChildren().addAll(view, _box1, _box2, run);
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
    _scene.getStylesheets().add("file:assets/fextile.css");
    _stage.setScene(_scene);
    _stage.show();
    if (800 >= screenBounds.getHeight() * .9) {
      double translate = .001875 * screenBounds.getHeight() - .7;
//      Scale scale = new Scale(translate, translate);
      Scale scale = new Scale(translate, translate);
      scale.setPivotX(0);
      scale.setPivotY(0);
      _scene.getRoot().getTransforms().setAll(scale);
      _stage.setHeight(_pane.getPrefHeight() * translate + 41);
      _stage.setWidth(_pane.getPrefWidth() * translate + 18);
      // _stage.centerOnScreen();
    }
    _stage.setY(0);

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
    options = FXCollections.observableArrayList(arr);
    options2 = FXCollections.observableArrayList(arr2);
    _box1 = new ComboBox(options);
    _box2 = new ComboBox(options2);
    _box1.getSelectionModel().selectedItemProperty().addListener(this::changed);
    _box2.getSelectionModel().selectedItemProperty().addListener(this::changed);

  }
  
  

  private Pane generatePane3() {
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
	barChart.setLegendSide(Side.BOTTOM);
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
    Image team1 = new Image("file:assets/" + home.getImagePath(), 200, 200, false, true);
    inFocus.setImage(team1);
    inFocus.setScaleY(_scene.getHeight() / team1.getHeight());// scaling the
    inFocus.setScaleX(_scene.getWidth() / team1.getWidth());
    double xScale = 200 / team1.getWidth();
    double yScale = 250 / team1.getHeight();
    inFocus.setScaleX(xScale);
    inFocus.setScaleY(yScale);
    if (inFocus == view2) {
      inFocus.setLayoutX(528);
      inFocus.setLayoutY(75);
    }
    else if (inFocus == view1) {
      inFocus.setLayoutX(74);
      inFocus.setLayoutY(75);
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
    for (int i = 0; i < mainPane.getChildren().size(); i++) {
      if (mainPane.getChildren().get(i) == inFocus) {
        return;
      }
    }
    mainPane.getChildren().add(inFocus);
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
  
  

}
