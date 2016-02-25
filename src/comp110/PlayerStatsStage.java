package comp110;

import java.lang.reflect.Method;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class PlayerStatsStage {

  
  public static TableView<Player> makeDetailedPlayerTable(ObservableList<Player> players) {
    TableView detailedStats = new TableView<Player>();
    ArrayList<String> getters = getGetters();
    
    for (Player p : players) {
      System.out.println(p);
    }
    
    for (String getter : getters) {
      System.out.println(getter);
      System.out.println(getter.substring(3));
      TableColumn currentColumn = new TableColumn(getter);
      currentColumn.setCellValueFactory(new PropertyValueFactory<>(getter.substring(3)));
      detailedStats.getColumns().add(currentColumn);
    }

    detailedStats.setItems(players);
    
    return detailedStats;
  }
  
  public static ArrayList<String> getGetters() {
    Class playerClass = null;
    try {
      playerClass = Class.forName("comp110.Player");
    } catch (ClassNotFoundException e) {
      System.out.println("crap"); 
    }
    Method[] allMethods = playerClass.getDeclaredMethods();
    ArrayList<String> getters = new ArrayList<>();
    for (Method m : allMethods) {
      if (m.getName().substring(0, 3).equals("get")) {
        getters.add(m.getName());
      }
    }
    return getters;
  }
  
}
