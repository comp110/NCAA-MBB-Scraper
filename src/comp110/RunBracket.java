package comp110;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RunBracket {
  
  private static Map<Integer, Team> _teamMap;

  public static void main(String[] args) throws IOException {
    
       _teamMap = Base.readJSON();
       BufferedReader csvReader = new BufferedReader(new FileReader("test.csv"));
       // Skipping the first line to avoid parsing the headings
       csvReader.readLine();       
       
       ArrayList<Game> gamesList = getGames(csvReader);
       Game[] gamesArray = new Game[gamesList.size()];
       gamesArray = gamesList.toArray(gamesArray);
       
       Student stu = new Student(gamesArray);
       System.out.println("Total points: " + stu.getOverallScore());
       System.out.println("Correct predictions: " + stu.getCorrect());
       System.out.println("Incorrect preditions: " + stu.getIncorrect());
       
       saveToJSON(stu, "student_test.json");
       
  }
  
  private static ArrayList<Game> getGames(BufferedReader csvReader) throws IOException {
    // Reading in each line of the csv, instantiating a game for the line, and adding it to games
    String line = "";
    ArrayList<Game> games = new ArrayList<>();
    while ((line = csvReader.readLine()) != null) {
      String[] splitCsv = line.split(",");
      
      // Accounting for text csv lines we can use for comments or to indicate a new round
      try {
        Integer.parseInt(splitCsv[0]);
      } catch (NumberFormatException nfe) {
        continue;
      }
      
      int[] csvInt = new int[splitCsv.length];
      // Converting to int[]
      for (int i=0; i<splitCsv.length; i++) {
        csvInt[i] = Integer.parseInt(splitCsv[i]);
      }
      games.add(new Game(csvInt[0], csvInt[1], csvInt[2], csvInt[3], csvInt[4], csvInt[5]));
    }
    
    return games;
  }
  
  private static void saveToJSON(Student student, String filename) throws IOException {
    File outFile = new File(filename);
    FileOutputStream outStream = new FileOutputStream(outFile);
    OutputStreamWriter outWriter = new OutputStreamWriter(outStream, "UTF-8");
    // Pretty printing for when you need to look at the json
    // Gson gson = new GsonBuilder().setPrettyPrinting().create();
    Gson gson = new Gson();
    gson.toJson(student, outWriter);
    outWriter.close();
  }
  
  public static Team getTeam(int id) {
    return _teamMap.get(id);
  }
}
