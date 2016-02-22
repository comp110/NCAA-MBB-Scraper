package comp110;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

public class TableViewGenerator {

	public static TableView makeTable(Matchup m) {
		// For TableView JavaFX wants an ObservableList of a user created class and 
		// as we all know JavaFX gets what JavaFX wants
		ObservableList<MethodOutput> outputs = FXCollections.observableArrayList();

		Method[] methods = m.getClass().getDeclaredMethods();
		methods = filterMethods(methods);
		double awayTotal = 0;
		double homeTotal = 0;
		Base.scoringFields = new String[methods.length];
		DecimalFormat df2 = new DecimalFormat("###.##");

		// For each method in the student's matchup class create an instance
		// of MethodOutput and add that instance to the outputs ObservableList
		for (Method method : methods) {
			try {
				String name = method.getName();
				double awayValue = (double) method.invoke(m, m.getAwayTeam());
				awayTotal += awayValue;
				awayValue = Double.valueOf(df2.format(awayValue));

				double homeValue =(double) method.invoke(m, (Team) m.getHomeTeam());
				homeTotal += homeValue;
				homeValue = Double.valueOf(df2.format(homeValue));

				MethodOutput output = new MethodOutput(name, homeValue, awayValue);
				outputs.add(output);
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalArgumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InvocationTargetException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}


		}

		// Setting these total scores to static fields in Base so they can be put in
		// the scoreboard labels
		Base.setMethodOutputs(outputs);
		Base.setHomeScore(homeTotal);
		Base.setAwayScore(awayTotal);
		return fillTable(m, outputs); // have all scores....

	}

	public static TableView fillTable(Matchup m, ObservableList<MethodOutput> outputs) {
		// This is really confusing but basically in JavaFX TableView you
		// create columns based on fields from a list of objects of a certain type
		// So you create a PropertyValueFactory and feed it a string that is the name
		// of the field you want to be in that column
		TableView<MethodOutput> methodTable = new TableView();
		int colWidth = 152;

		TableColumn methodCol = new TableColumn("Method");
		methodCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		methodCol.setPrefWidth(colWidth);

		TableColumn homeCol = new TableColumn(m.getHomeTeam().getName());
		homeCol.setCellValueFactory(new PropertyValueFactory<>("homeValue"));
		homeCol.setPrefWidth(colWidth);

		TableColumn awayCol = new TableColumn(m.getAwayTeam().getName());
		awayCol.setCellValueFactory(new PropertyValueFactory<>("awayValue"));
		awayCol.setPrefWidth(colWidth);

		methodTable.getColumns().addAll(methodCol, homeCol, awayCol);
		// At the end you just setItems to your ObservableList and JavaFX does all the
		// work of getting the fields from each individual list element
		methodTable.setItems(outputs);
		methodTable.getSortOrder().add(methodCol);

		return methodTable;
	}

	private static Method[] filterMethods(Method[] methods) {
		// only want doubles that are not calculateScore
		Method[] filtered = new Method[methods.length];
		int filteredCount = 0;

		for (Method method : methods) {
			if (method.getReturnType() == Double.TYPE
					&& !method.getName().equals("calculateScore")) {
				filtered[filteredCount] = method;
				filteredCount++;
			}
		}

		return Arrays.copyOf(filtered, filteredCount);
	}

}
