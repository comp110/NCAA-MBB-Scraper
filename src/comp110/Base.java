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
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Base extends Application{
	//hi
	Stage _stage;
	Scene _scene;
	TabPane _pane;
	Team[] _teams;
	ComboBox _box1;
	ComboBox _box2;
	

	@Override
	public void start(Stage stage) throws Exception {
		_stage = stage;
		_pane = new TabPane();
	    _teams = readJson();
	    buildBox();
	    Tab matchupTab = new Tab();
	    Tab textStats = new Tab();
	    Tab graphStats = new Tab();
	    matchupTab.setText("Matchup");
	    textStats.setText("Stats");
	    graphStats.setText("Graphs");
	    Pane test = new Pane();
	    test.getChildren().add(_box1);
	    test.getChildren().add(_box2);
	    
	    matchupTab.setContent(test);
	    _pane.getTabs().addAll(matchupTab, textStats, graphStats);
	    this.initializeStage();

	    
	    
	   // Matchup matchup = new Matchup(//GET TEAMS SOMEHOW)
		
	}
	
	private void initializeStage() {
		    _stage.setTitle("COMP110 PS0X - NCAA Bracket");
		    _scene = new Scene(_pane, 800, 800);
		    _stage.setScene(_scene);
		    _stage.show();
	}
	
	private static Team[] readJson() throws FileNotFoundException{
		JsonReader reader = new JsonReader(new FileReader("acc.json"));
		Gson gson = new Gson();
		Team[] teams = gson.fromJson(reader, Team[].class);
		
		Map<Integer, Team> teamMap= new HashMap<Integer, Team>();
		for (Team team : teams) {
			teamMap.put(team.getId(), team);
		}
		return teams;
				
	}
	
	private void buildBox(){
		ArrayList<String> arr = new ArrayList<String>();
		ObservableList<String> options;
		for (Team item : _teams){
			arr.add(item.getName());
		}
		options = FXCollections.observableArrayList(arr);
		_box1 = new ComboBox(options);
		_box2 = new ComboBox(options);
	}
	

}
