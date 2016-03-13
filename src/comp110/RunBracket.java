package comp110;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import com.google.gson.Gson;

public class RunBracket {

  private static Map<Integer, Team> _teamMap;

  public static void main(String[] args) throws IOException {
    _teamMap = Base.readJSON();

    String file = "";
    if (args.length > 0) {
      file = args[0];
    } else {
      System.err.println("Usage: comp110.RunBracket [csv]");
      System.exit(1);
    }
    String games = args[0];
    BufferedReader csvReader = new BufferedReader(new FileReader(file));
    csvReader.readLine();

    ArrayList<Game> gamesList = getGames(csvReader);
    Game[] gamesArray = new Game[gamesList.size()];
    gamesArray = gamesList.toArray(gamesArray);

    printJSON(new Student(gamesArray));
  }

  private static ArrayList<Game> getGames(BufferedReader csvReader) throws IOException {
    // Reading in each line of the csv, instantiating a game for the line, and
    // adding it to games
    String line = "";
    ArrayList<Game> games = new ArrayList<>();
    while ((line = csvReader.readLine()) != null) {
      String[] splitCsv = line.split(",");

      // Accounting for text csv lines we can use for comments or to indicate a
      // new round
      try {
        Integer.parseInt(splitCsv[0]);
      } catch (NumberFormatException nfe) {
        continue;
      }

      int[] csvInt = new int[splitCsv.length];
      // Converting to int[]
      for (int i = 0; i < splitCsv.length; i++) {
        csvInt[i] = Integer.parseInt(splitCsv[i]);
      }
      games.add(new Game(csvInt[0], csvInt[1], csvInt[2], csvInt[3], csvInt[4], csvInt[5]));
    }

    return games;
  }

  private static void printJSON(Student student) {
    System.out.println("\n\n");
    System.out.println(new Gson().toJson(student));
  }

  public static Team getTeam(int id) {
    return _teamMap.get(id);
  }
}
