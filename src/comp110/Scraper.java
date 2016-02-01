package comp110;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.*;

import java.io.File;
import java.io.IOException;

public class Scraper {
	
	public static void main(String[] args) throws IOException {
		// The NCAA website has a unique id for each team that they use in URLS
		// The scraper uses that id to load two web pages about the same team
		// The id is passed to the program as an argument. If no argument exists it uses UNC's id (457)
		int teamID = 457;
		if (args.length>0) {
			teamID = Integer.parseInt(args[0]);
		}
		
		String statsUrl = "http://stats.ncaa.org/team/stats?org_id=" + teamID + "&sport_year_ctl_id=12260";
		String rankingsUrl = "http://stats.ncaa.org/rankings/ranking_summary?academic_year=2016&division=1.0&org_id="
							  + teamID + "&ranking_period=90&sport_code=MBB";
		Document statsPage = Jsoup.connect(statsUrl).get();
		
//		// Testing code to avoid loading the page over and over
//		File localStats = new File("unc.html");
//		Document statsPage = Jsoup.parse(localStats, "UTF-8", "http://stats.ncaa.org/team/stats?org_id=457&sport_year_ctl_id=12260");
//		File localRankings = new File("uncrankings.html");
//		Document rankingsPage = Jsoup.parse(localRankings, "UTF-8", "http://stats.ncaa.org/team/stats?org_id=457&sport_year_ctl_id=12260");
		
		// The team class' constructor puts the info in the header into "name", "wins", and "losses" fields
		String header = statsPage.select("h1").first().text();
		Team team = new Team(header);

		// Processing of each player's stats is done in the Player class' constructor
		Elements statRows = statsPage.select("tr.text");
		for (Element row : statRows) {
			// Checking for the "TEAM" table row and skipping if found
			if (row.child(1).text().equals("TEAM")) {
				continue;
			}
			Player current = new Player(row);
			team.addPlayer(current);
		}
		

		Document rankingsPage = Jsoup.connect(rankingsUrl).get();
		Elements rankingsRows = rankingsPage.select("table").get(2).select("tr");
		team.populateFields(rankingsRows);
		
		Gson gson = new Gson();
		String teamJson = gson.toJson(team);
		System.out.println(teamJson);
		
	}
	
	public static int getInt(Element row, int i) {
		// Helper method that takes in an html element and int i
		// Tries to return the int inside the ith chile of the element
		// Returns 0 if it encounters a NumberFormatException (if the <td> element is empty)
		try {
			return Integer.parseInt(row.child(i).text());
		} catch (NumberFormatException ex) {
			// Some fields in the NCAA table are empty if the player has 0 so this catches that case
			return 0;
		}
	}
	
	public static double getDoub(Element row, int i) {
		// Same as getInt but with doubles
		try {
			return Double.parseDouble(row.child(i).text());
		} catch (NumberFormatException ex) {
			// Some fields in the NCAA table are empty if the player has 0 so this catches that case
			return 0.0;
		}
	}
	
	
	
	
}
