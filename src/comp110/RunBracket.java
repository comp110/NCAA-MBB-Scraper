package comp110;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RunBracket {

  private static Map<Integer, Team> _teamMap;

  private static final int GAME_ID = 0, ROUND = 1, HOME_ID = 2, AWAY_ID = 3, HOME_SCORE = 4, AWAY_SCORE = 5;

  public static void main(String[] args) {

    final String teamsJson, gamesCsv, outputJson;
    if (args.length > 2) {
      teamsJson = args[0];
      gamesCsv = args[1];
      outputJson = args[2];
    } else {
      System.err.println("Usage: comp110.RunBracket [teams json] [games csv] [output json]");
      System.exit(1);
      return;
    }

    try {
      _teamMap = Base.readJSON(teamsJson);
      BufferedReader csvReader = new BufferedReader(new FileReader(gamesCsv));
      csvReader.readLine();

      Student student = new Student();
      Game[] games = new Game[64];

      class GamesThread implements Callable<Void> {
        public Void call() {
          try {
            runGames(csvReader, games);
            saveToJSON(new Student(games), outputJson);
            System.exit(0);
          } catch (Exception e) {
            handleException(e);
          }
          return null;
        }
      }

      ExecutorService executor = Executors.newSingleThreadExecutor();
      GamesThread game = new GamesThread();
      try {
        executor.submit(game).get(2000, TimeUnit.MILLISECONDS);
      } catch (Exception e) {
        handleException(e);
      }

    } catch (Exception e) {
      handleException(e);
    }

  }

  private static void handleException(Exception e) {
    String failString = e.getClass().getSimpleName() + " - ";
    failString += e.getMessage() + "\n";
    for (StackTraceElement frame : e.getStackTrace()) {
      failString += "  at Line " + frame.getLineNumber() + " of " + frame.getFileName() + "\n";
    }
    System.out.println(failString);
    System.exit(1);
  }

  private static void runGames(BufferedReader csvReader, Game[] games) throws IOException {
    String[][] gameStrings = gamesToStrings(csvReader);
    for (int game = gameStrings.length - 1; game >= 1; game--) {
      String[] gameString = gameStrings[game];

      int homeId, awayId;
      if (gameString[HOME_ID].contains("Winner of ")) {
        int homeGameId = Integer.parseInt(gameString[HOME_ID].replace("Winner of #", ""));
        int awayGameId = Integer.parseInt(gameString[AWAY_ID].replace("Winner of #", ""));
        homeId = games[homeGameId].getWinnerId();
        awayId = games[awayGameId].getWinnerId();
      } else {
        homeId = Integer.parseInt(gameString[HOME_ID]);
        awayId = Integer.parseInt(gameString[AWAY_ID]);
      }
      int homeScore = Integer.parseInt(gameString[HOME_SCORE]);
      int awayScore = Integer.parseInt(gameString[AWAY_SCORE]);
      int round = Integer.parseInt(gameString[ROUND]);
      games[game] = new Game(game, round, homeId, awayId, homeScore, awayScore);

      boolean hasError = false;
      try {
        games[game].play();
      } catch (Exception e) {
        String error = e.getClass().getName() + ": " + e.getMessage() + "\n";
        for (StackTraceElement frame : e.getStackTrace()) {
          error += "  at Line " + frame.getLineNumber() + " of " + frame.getFileName() + "\n";
        }
        games[game].setError(error);
      }

    }
  }

  private static String[][] gamesToStrings(BufferedReader csvReader) throws IOException {
    String[][] games = new String[64][7];
    String line = "";
    int game = 1;
    while ((line = csvReader.readLine()) != null) {
      String[] splitCsv = line.replaceAll("\"", "").split(",");
      games[game++] = splitCsv;
    }
    return games;
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

  private static void saveToJSON(Student student, String filename) throws IOException {
    File outFile = new File(filename);
    FileOutputStream outStream = new FileOutputStream(outFile);
    OutputStreamWriter outWriter = new OutputStreamWriter(outStream, "utf8");

    Gson gson = (new GsonBuilder()).serializeSpecialFloatingPointValues().create();
    gson.toJson(student, outWriter);
    outWriter.close();
  }

  public static Team getTeam(int id) {
    return _teamMap.get(id);
  }
}
