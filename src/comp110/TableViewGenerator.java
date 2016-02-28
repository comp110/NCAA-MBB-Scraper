package comp110;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableViewGenerator {

  public static TableView makeTable(Scorecard s) {

    ObservableList<MethodOutput> outputs = FXCollections.observableArrayList();
    DecimalFormat df2 = new DecimalFormat("###.##");
    for (Scoreline l : s.getScorelines()) {
      outputs.add(new MethodOutput(l.getLabel(), l.getHomeValue(), l.getAwayValue()));
    }

    return fillTable(s, outputs);
  }

  public static TableView fillTable(Scorecard s, ObservableList<MethodOutput> outputs) {
    // This is really confusing but basically in JavaFX TableView you
    // create columns based on fields from a list of objects of a certain type
    // So you create a PropertyValueFactory and feed it a string that is the
    // name
    // of the field you want to be in that column
    TableView<MethodOutput> methodTable = new TableView();
    int colWidth = 152;

    TableColumn methodCol = new TableColumn("ScoreLine");
    methodCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    methodCol.setPrefWidth(colWidth);

    TableColumn awayCol = new TableColumn(s.getAwayTeam().getName());
    awayCol.setCellValueFactory(new PropertyValueFactory<>("awayValue"));
    awayCol.setPrefWidth(colWidth);

    TableColumn homeCol = new TableColumn(s.getHomeTeam().getName());
    homeCol.setCellValueFactory(new PropertyValueFactory<>("homeValue"));
    homeCol.setPrefWidth(colWidth);

    methodTable.getColumns().addAll(methodCol, awayCol, homeCol);
    // At the end you just setItems to your ObservableList and JavaFX does all
    // the
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
      if (method.getReturnType() == Double.TYPE) {
        filtered[filteredCount] = method;
        filteredCount++;
      }
    }

    return Arrays.copyOf(filtered, filteredCount);
  }

}
