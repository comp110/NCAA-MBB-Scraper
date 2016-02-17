package comp110;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.SocketTimeoutException;

public class Scraper {
	
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
		// Scrapes all of the teams whose ids are in the int[] and returns an id, Team map
		Team[] teams = new Team[teamIds.length];
		
		int index = 0;
		for (int id : teamIds) {
			System.out.println("Scraping team " + id + "...");
			Team currentTeam = scrapeTeam(id, rankingPeriod);
			currentTeam.setId(id);
			teams[index] = currentTeam;
			System.out.println(" " + currentTeam.getName() + " succssfully scraped!");
			if (index != teams.length-1) {
				// Sleep for a while to be polite to the servers
				try { 
					Thread.sleep(30000);
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
		
		// The "ranking_period" updates periodically so it's important to check what the current one is
		// before scraping
		String statsUrl = "http://stats.ncaa.org/team/" + teamID + "/stats/12260";
		String rankingsUrl = "http://stats.ncaa.org/rankings/ranking_summary?academic_year=2016&division=1&org_id="
							  + teamID + "&ranking_period=" + rankingPeriod + "&sport_code=MBB";
		
		Document statsPage, rankingsPage;
		// These servers can be really slow at times so I've set timeout really high and
		// set the thread to sleep between scraping the two pages
		// And if the connection does time out there are two extra tries for it to work again
		int maxTries = 3;
		int count = 1;
		while (true) {
			try {
				statsPage = Jsoup.connect(statsUrl).timeout(30000).get();
				System.out.println(" First of two team pages scraped!");
				try {
					Thread.sleep(15000);
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}
				rankingsPage = Jsoup.connect(rankingsUrl).timeout(30000).get();
				break;
			} catch (SocketTimeoutException ste) {
				count++;
				if (count >= maxTries) {
					throw ste;
				}
				System.out.println(" Having trouble connecting to the NCAA server. Trying again after a few seconds...");
				try {
					Thread.sleep(15000);
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}
			}
		}
		
		
		// The team class' constructor puts the info in the header into "name", "wins", and "losses" fields
		String header = statsPage.select("span.org_heading").first().text();

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
