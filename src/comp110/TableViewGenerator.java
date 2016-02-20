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
		ObservableList<MethodOutput> outputs = FXCollections.observableArrayList();

		Method[] methods = m.getClass().getDeclaredMethods();
		methods = filterMethods(methods);
		double[] awayScoreList = new double[methods.length];
		double[] homeScoreList = new double[methods.length];
		double awayScore = 0;
		double homeScore = 0;
		Base.scoringFields = new String[methods.length];

		DecimalFormat df2 = new DecimalFormat("###.##");
		DecimalFormat df1 = new DecimalFormat("###.#");

		for (Method method : methods) {
			try {
				String name = method.getName();
				double awayValue = (double) method.invoke(m, m.get_awayTeam());
				awayValue = Double.valueOf(df2.format(awayValue));
				double homeValue =(double) method.invoke(m, (Team) m.get_homeTeam());
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
//			Base.homeScores = homeScoreList;
//			Base.awayScores = awayScoreList;


		}

		// Setting these total scores to static fields in Base so they can be put in
//		// the scoreboard labels
//		double roundedHome = Double.valueOf(df1.format(homeScore));
//		double roundedAway = Double.valueOf(df1.format(awayScore));
//		Base.setHomeScore(roundedHome);
//		Base.setAwayScore(roundedAway);
		return fillTable(m, outputs); // have all scores....

	}

	public static TableView fillTable(Matchup m, ObservableList<MethodOutput> outputs) {
		TableView<MethodOutput> methodTable = new TableView();
		TableColumn methodCol = new TableColumn("Method");
		int colWidth = 152;
		
		methodCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		methodCol.setMaxWidth(colWidth);
		
		TableColumn homeCol = new TableColumn(m.get_homeTeam().getName());
		homeCol.setCellValueFactory(new PropertyValueFactory<>("homeValue"));
		homeCol.setMaxWidth(colWidth);
		homeCol.setMinWidth(colWidth);
		
		TableColumn awayCol = new TableColumn(m.get_awayTeam().getName());
		awayCol.setCellValueFactory(new PropertyValueFactory<>("awayValue"));
		awayCol.setMaxWidth(colWidth);
		awayCol.setMinWidth(colWidth);
		
		methodTable.getColumns().addAll(methodCol, homeCol, awayCol);
		methodTable.setItems(outputs);
		
		
		return methodTable;
	}

	private static GridPane drawMethodGrid(GridPane _grid, Method method, double away,
			double home, int row) {
		Label awayLabel = new Label(method.getName() + "(Team t): " + away);
		Label homeLabel = new Label(method.getName() + "(Team t): " + home);
		_grid.add(awayLabel, 1, row);
		_grid.add(homeLabel, 0, row);

		return _grid;
	}

	// good reference below for making labels, just keeping it here for now for
	// my eyes...
	/*
	 * private static GridPane drawMethodGUI(Method method, int rowIndex, GridPane
	 * _grid,Matchup m) { Label methodName = new Label(method.getName() + "()");
	 * //methodName.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 15));
	 * _grid.add(methodName, 0, rowIndex); boolean isFirstParam = true;
	 * TextField[] paramFields = new TextField[method.getParameters().length]; int
	 * i = 0; for (Parameter param : method.getParameters()) { TextField
	 * paramField = new TextField(); paramFields[i++] = paramField; Label
	 * paramType = new Label(param.getType().getSimpleName());
	 * _grid.add(paramField, i, rowIndex); _grid.add(paramType, (i + 1),
	 * rowIndex); if (isFirstParam) { isFirstParam = false; } else { Label comma =
	 * new Label(","); //not sure where this was supposed to go but it's not
	 * working now _grid.add(comma, (i + 1), rowIndex); } } // Label
	 * methodParamClose = new Label(")"); // _grid.add(methodParamClose, 0,
	 * rowIndex); Button evaluate = new Button("Evaluate \u2192");
	 * _grid.add(evaluate, (i + 1), rowIndex); Label result = new
	 * Label("<result>"); //result.setFont(Font.font("Arial",
	 * FontWeight.SEMI_BOLD, 15)); _grid.add(result, (i + 2), rowIndex);
	 * Parameter[] params = method.getParameters(); Object[] args = new
	 * Object[params.length]; evaluate.setOnAction((event) -> { for (int j = 0; j
	 * < params.length; j++) { if (params[j].getType() == Integer.TYPE) { args[j]
	 * = Integer.parseInt(paramFields[j].getText()); } else if
	 * (params[j].getType() == Double.TYPE) { args[j] =
	 * Double.parseDouble(paramFields[j].getText()); } else if
	 * (params[j].getType() == String.class) { args[j] = paramFields[j].getText();
	 * } } try { result.setText(method.invoke(m, args).toString()); } catch
	 * (Exception e) { result.setText(e.getMessage()); } }); //centers each
	 * element within their respective cell for(Node node : _grid.getChildren()){
	 * _grid.setHalignment(node, HPos.CENTER); } return _grid; }
	 */
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
