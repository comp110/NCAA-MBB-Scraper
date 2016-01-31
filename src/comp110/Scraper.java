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
		// Hardcoding URL for now
		String url = "http://stats.ncaa.org/team/stats?org_id=457&sport_year_ctl_id=12260";
		Document page = Jsoup.connect(url).get();
		
//		// Testing code to avoid loading the page over and over
//		File localHTML = new File("unc.html");
//		Document page = Jsoup.parse(localHTML, "UTF-8", "http://stats.ncaa.org/team/stats?org_id=457&sport_year_ctl_id=12260");
		
		// The team class' constructor puts the info in the header into "name", "wins", and "losses" fields
		String header = page.select("h1").first().text();
		Team team = new Team(header);

		// Processing of each player's stats is done in the Player class' constructor
		Elements rows = page.select("tr.text");
		for (Element row : rows) {
			// Checking for the "TEAM" table row and skipping if found
			if (row.child(1).text().equals("TEAM")) {
				continue;
			}
			Player current = new Player(row);
			team.addPlayer(current);
		}
		
		Gson gson = new Gson();
		String teamJson = gson.toJson(team);
		System.out.println(teamJson);
		
	}
	
	
	
	
}
