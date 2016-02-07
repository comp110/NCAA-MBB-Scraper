package comp110;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import javafx.geometry.Insets;

import javafx.scene.layout.GridPane;

public class Pane2Generator {

	public static GridPane Pane2(Matchup m) {
		GridPane _grid = new GridPane();
		_grid.setHgap(10);
		_grid.setVgap(10);
		_grid.setPadding(new Insets(5, 10, 5, 10));

		Method[] methods = m.getClass().getDeclaredMethods();
		methods = filterMethods(methods);
		double[] awayScoreList = new double[methods.length];
		double[] homeScoreList = new double[methods.length];
		int index = 0;
		for (Method method : methods) {
			try {
				awayScoreList[index] = (double) method.invoke(m, m.get_awayTeam());
				homeScoreList[index] = (double) method.invoke(m, (Team) m.get_homeTeam());
				index++;
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
		return _grid; // have all scores....

	}

	// good reference below for making labels, just keeping it here for now for
	// my eyes...
	/*
	 * private static GridPane drawMethodGUI(Method method, int rowIndex,
	 * GridPane _grid,Matchup m) {
	 * 
	 * Label methodName = new Label(method.getName() + "()");
	 * //methodName.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 15));
	 * _grid.add(methodName, 0, rowIndex);
	 * 
	 * boolean isFirstParam = true; TextField[] paramFields = new
	 * TextField[method.getParameters().length]; int i = 0; for (Parameter param
	 * : method.getParameters()) { TextField paramField = new TextField();
	 * paramFields[i++] = paramField; Label paramType = new
	 * Label(param.getType().getSimpleName());
	 * 
	 * _grid.add(paramField, i, rowIndex); _grid.add(paramType, (i + 1),
	 * rowIndex); if (isFirstParam) { isFirstParam = false; } else { Label comma
	 * = new Label(","); //not sure where this was supposed to go but it's not
	 * working now _grid.add(comma, (i + 1), rowIndex); } }
	 * 
	 * // Label methodParamClose = new Label(")"); //
	 * _grid.add(methodParamClose, 0, rowIndex);
	 * 
	 * Button evaluate = new Button("Evaluate \u2192"); _grid.add(evaluate, (i +
	 * 1), rowIndex);
	 * 
	 * Label result = new Label("<result>"); //result.setFont(Font.font("Arial",
	 * FontWeight.SEMI_BOLD, 15)); _grid.add(result, (i + 2), rowIndex);
	 * 
	 * Parameter[] params = method.getParameters(); Object[] args = new
	 * Object[params.length]; evaluate.setOnAction((event) -> { for (int j = 0;
	 * j < params.length; j++) { if (params[j].getType() == Integer.TYPE) {
	 * args[j] = Integer.parseInt(paramFields[j].getText()); } else if
	 * (params[j].getType() == Double.TYPE) { args[j] =
	 * Double.parseDouble(paramFields[j].getText()); } else if
	 * (params[j].getType() == String.class) { args[j] =
	 * paramFields[j].getText(); } } try { result.setText(method.invoke(m,
	 * args).toString()); } catch (Exception e) {
	 * result.setText(e.getMessage()); } }); //centers each element within their
	 * respective cell for(Node node : _grid.getChildren()){
	 * _grid.setHalignment(node, HPos.CENTER); } return _grid; }
	 */
	private static Method[] filterMethods(Method[] methods) {
		// only want doubles that are not calculateScore
		Method[] filtered = new Method[methods.length];
		int filteredCount = 0;

		for (Method method : methods) {
			if (method.getReturnType() == Double.TYPE && !method.getName().equals("calculateScore")) {
				filtered[filteredCount] = method;
				filteredCount++;
			}
		}

		return Arrays.copyOf(filtered, filteredCount);
	}

}