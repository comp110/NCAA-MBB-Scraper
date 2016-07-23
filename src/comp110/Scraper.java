package comp110;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.SocketTimeoutException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

public class Scraper {

  public static void main(String[] args) throws IOException {
    int[] teamIds = { 346, 340, 342, 810, 811, 812, 813, 817, 2915, 719, 718, 716, 711, 1068, 19651,
        299, 294, 295, 290, 590, 198, 596, 599, 194, 196, 191, 193, 270, 272, 275, 277, 527, 521,
        522, 523, 1014, 528, 529, 441, 440, 446, 444, 108, 109, 102, 101, 107, 104, 32, 31, 30, 37,
        647, 439, 649, 648, 434, 432, 433, 430, 10411, 334, 331, 6, 99, 90, 94, 97, 96, 740, 741, 742,
        559, 746, 748, 554, 556, 551, 550, 553, 234, 235, 236, 231, 147, 140, 141, 30024, 610, 617,
        148, 149, 688, 683, 2711, 498, 136, 494, 497, 490, 493, 27, 23, 28, 29, 406, 404, 402, 400,
        371, 370, 709, 704, 706, 700, 702, 703, 393, 392, 391, 83, 80, 81, 86, 87, 797, 796, 794, 
        792, 14927, 7, 587, 244, 249, 248, 519, 518, 513, 1004, 514, 458, 459, 627, 626, 625, 624,
        450, 629, 454, 456, 457, 178, 176, 175, 173, 172, 657, 654, 183, 180, 2, 650, 651, 189, 202,
        659, 184, 14, 17, 2699, 327, 328, 201, 774, 563, 771, 204, 207, 772, 77, 72, 71, 655, 669,
        667, 665, 664, 663, 660, 692, 693, 690, 691, 697, 694, 695, 698, 699, 540, 541, 545, 8, 26172,
        2707, 127, 128, 129, 414, 415, 416, 418, 419, 312, 311, 310, 317, 316, 315, 314, 368, 369, 366,
        367, 365, 363, 361, 380, 381, 386, 387, 388, 786, 782, 579, 572, 576, 575, 574, 61, 62, 66, 67,
        68, 253, 251, 257, 254, 255, 157, 731, 732, 735, 508, 736, 739, 738, 504, 505, 502, 503, 500,
        501, 630, 631, 632, 469, 635, 465, 639, 466, 460, 463, 169, 164, 165, 167, 1104, 2743, 9,
        28600, 676, 646, 355, 352, 768, 769, 219, 288, 283, 285, 287, 678, 1092, 674, 1157, 670,
        671, 261, 260, 51, 536, 534, 539, 1320, 115, 116, 111, 110, 428, 308, 301, 302, 305, 306,
        2678, 756, 754, 562, 758, 229, 228, 222, 220, 726, 725, 721, 153, 606, 600, 603, 156, 158,
        609, 749, 48, 47, 43, 5, 464, 474, 1356, 489, 488, 485, 483, 482, 509, 472, 473, 471, 28755
    };
    

    saveToJSON("NCAA_MBB_2015-16.json", teamIds, 155);
  }

  public static void saveToJSON(String filename, int[] teamIds, int rankingPeriod) throws IOException {
    File outFile = new File(filename);
    FileOutputStream outStream = new FileOutputStream(outFile);
    OutputStreamWriter outWriter = new OutputStreamWriter(outStream, "UTF-8");
    Team[] teams = bulkScrape(teamIds, rankingPeriod);
    Gson gson = new Gson();
    gson.toJson(teams, outWriter);
    outWriter.close();
    System.out.println("\nAll teams successfully scraped and serialized!\n");
  }

  public static Team[] bulkScrape(int[] teamIds, int rankingPeriod) throws IOException {
    // Scrapes all of the teams whose ids are in the int[] and returns an id,
    // Team map
    Team[] teams = new Team[teamIds.length];

    int index = 0;
    for (int id : teamIds) {
      System.out.println("Scraping team " + id + "...");
      Team currentTeam = scrapeTeam(id, rankingPeriod);
      currentTeam.setId(id);
      teams[index] = currentTeam;
      System.out.println(" " + currentTeam.getName() + " succssfully scraped!");
      if (index != teams.length - 1) {
        // Sleep for a while to be polite to the servers
        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }

      index++;
    }
    return teams;
  }

  public static Team scrapeTeam(int teamID, int rankingPeriod) throws IOException {
    // The NCAA website has a unique id for each team that they use in URLS
    // The scraper uses that id to load two web pages about the same team

    // The "ranking_period" updates periodically so it's important to check what
    // the current one is
    // before scraping
    String statsUrl = "http://stats.ncaa.org/team/" + teamID + "/stats/12260";
    String rankingsUrl = "http://stats.ncaa.org/rankings/ranking_summary?academic_year=2016&division=1&org_id=" + teamID
        + "&ranking_period=" + rankingPeriod + "&sport_code=MBB";
    System.out.println(rankingsUrl);
    System.out.println(statsUrl);
    Document statsPage, rankingsPage;
    // These servers can be really slow at times so I've set timeout really high
    // and
    // set the thread to sleep between scraping the two pages
    // And if the connection does time out there are two extra tries for it to
    // work again
    int maxTries = 10;
    int count = 1;
    while (true) {
      try {
        statsPage = Jsoup.connect(statsUrl).timeout(30000).get();
        System.out.println(" First of two team pages scraped!");
        try {
          Thread.sleep(100);
        } catch (InterruptedException ie) {
          ie.printStackTrace();
        }
        rankingsPage = Jsoup.connect(rankingsUrl).timeout(300000).get();
        break;
      } catch (SocketTimeoutException ste) {
        count++;
        if (count >= maxTries) {
          throw ste;
        }
        System.out.println(" Having trouble connecting to the NCAA server. Trying again after a few seconds...");
        try {
          Thread.sleep(100);
        } catch (InterruptedException ie) {
          ie.printStackTrace();
        }
      }
    }

    // The team class' constructor puts the info in the header into "name",
    // "wins", and "losses" fields
    String header = statsPage.select("span.org_heading").first().text();

    // Processing of each player's stats is done in the Player class'
    // constructor
    Elements statRows = statsPage.select("tr.text");
    int numPlayers = statRows.size() - 1;
    Team team = new Team(header, numPlayers);
    int playerIndex = 0;
    for (Element row : statRows) {
      // Checking for the "TEAM" table row and skipping if found
      if (row.child(1).text().equals("TEAM")) {
        continue;
      }
      Player current = new Player(row);
      team.addPlayer(current, playerIndex);
      playerIndex++;
    }

    Elements rankingsRows = rankingsPage.select("table").get(2).select("tr");
    team.populateFields(rankingsRows);
    return team;
  }

  public static int getInt(Element row, int i) {
    // Helper method that takes in an html element and int i
    // Tries to return the int inside the ith child of the element
    // Returns 0 if it encounters a NumberFormatException (if the <td> element
    // is empty)
    try {
      return Integer.parseInt(row.child(i).text());
    } catch (NumberFormatException ex) {
      // Some fields in the NCAA table are empty if the player has 0 so this
      // catches that case
      return 0;
    }
  }

  public static double getDoub(Element row, int i) {
    // Same as getInt but with doubles
    try {
      return Double.parseDouble(row.child(i).text());
    } catch (NumberFormatException ex) {
      // Some fields in the NCAA table are empty if the player has 0 so this
      // catches that case
      return 0.0;
    }
  }

  public static int getRank(Element row, int i) {
    // The same as getInt but handles an empty <td> differently because it works
    // on ranks
    // rather than statistic values
    try {
      return Integer.parseInt(row.child(i).text());
    } catch (NumberFormatException ex) {
      // If a team has no rank for a stat on the rankings page, this gives them
      // one lower rank than the lowest rank
      return 347;
    }
  }

}
