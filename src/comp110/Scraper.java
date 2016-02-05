package comp110;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.File;
import java.io.IOException;

public class Scraper {
	
	public static Team scrapeTeam(int teamID) throws IOException {
		// The NCAA website has a unique id for each team that they use in URLS
		// The scraper uses that id to load two web pages about the same team
		
		String statsUrl = "http://stats.ncaa.org/team/stats?org_id=" + teamID + "&sport_year_ctl_id=12260";
		String rankingsUrl = "http://stats.ncaa.org/rankings/ranking_summary?academic_year=2016&division=1.0&org_id="
							  + teamID + "&ranking_period=90&sport_code=MBB";
		
		Document statsPage, rankingsPage;
		// UNC's pages are saved here to cut down on loading
		if (teamID == 457) {
			File localStats = new File("unc.html");
			statsPage = Jsoup.parse(localStats, "UTF-8", "http://stats.ncaa.org/team/stats?org_id=457&sport_year_ctl_id=12260");
			File localRankings = new File("uncrankings.html");
			rankingsPage = Jsoup.parse(localRankings, "UTF-8", "http://stats.ncaa.org/team/stats?org_id=457&sport_year_ctl_id=12260");
		} else {
			System.out.println("Loading pages for Team ID " + teamID + "...");
			statsPage = Jsoup.connect(statsUrl).timeout(10000).get();
			rankingsPage = Jsoup.connect(rankingsUrl).timeout(10000).get();
		}
		
		
		// The team class' constructor puts the info in the header into "name", "wins", and "losses" fields
		String header = statsPage.select("h1").first().text();

		// Processing of each player's stats is done in the Player class' constructor
		Elements statRows = statsPage.select("tr.text");
		int numPlayers = statRows.size()-1;
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
	
	public static int getRank(Element row, int i) {
		// The same as getInt but handles an empty <td> differently because it works on ranks
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
