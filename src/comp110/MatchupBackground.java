package comp110;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

/**
 * This is where I'm putting the bulk scraping and instantiation of new Matchups for now
 * I think a lot of this will be replaced by the gui and/or runner
 **/

public class MatchupBackground {

	public static void main(String[] args) throws IOException {
//	    // The scraping here only needs to be done once and takes about a minute per team
//		// These are currently all of the ACC teams for testing
//		int[] teamIds = {67, 147, 193, 234, 255, 367, 415, 457, 490, 513, 545, 688, 746, 742, 749};
//		// The rankingPeriod is there to be put into the ncaa website URLs
//		// It changes periodically so make sure you have the current one before scraping
//		// or else you will get outdated stats
//		int rankingPeriod = 95;
//		Scraper.saveToJSON("acc.json", teamIds, rankingPeriod);
		
		JsonReader reader = new JsonReader(new FileReader("acc.json"));
		Gson gson = new Gson();
		Team[] teams = gson.fromJson(reader, Team[].class);
		
		Map<Integer, Team> teamMap= new HashMap<Integer, Team>();
		for (Team team : teams) {
			teamMap.put(team.getId(), team);
		}
		
		new Matchup(teamMap.get(457), teamMap.get(193));
	} 


}
