package comp110;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.select.Elements;

public class Team {
	private String _name;
	private ArrayList<Player> _roster;
	private int _wins, _losses;
	private TeamStat[] _stats;
	
	
	
	public Team(String header) {
		// The team name and record are in the header in the format "$name ($wins-$losses)"
		// This uses regex to get the right info into the right field
		Pattern recordPattern = Pattern.compile("\\(\\d+-\\d+\\)");
		Matcher recordMatcher = recordPattern.matcher(header);
		recordMatcher.find();
		String rawRecord = recordMatcher.group(0);
		rawRecord = rawRecord.substring(1, rawRecord.length()-1);
		String[] rawRecArray = rawRecord.split("-");
		_wins = Integer.parseInt(rawRecArray[0]);
		_losses = Integer.parseInt(rawRecArray[1]);
		_name = recordMatcher.replaceAll("").trim();
		_roster = new ArrayList<Player>();
	}
	
	public void populateFields(Elements rows) {
		// Loops through each stat row, creates a new TeamStat object for it,
		// and adds that to the _stats array
		rows.remove(0);
		_stats = new TeamStat[rows.size()];
		for (int i=0; i<rows.size(); i++) {
			String fieldName = rows.get(i).child(0).text();
			// This regex removes the unnecessary " (346 ranked)" from the field names
			Pattern rankedPattern = Pattern.compile(" \\(\\d+ ranked\\)");
			Matcher rankedMatcher = rankedPattern.matcher(fieldName);
			rankedMatcher.find();
			fieldName = rankedMatcher.replaceAll("");
			double value = Scraper.getDoub(rows.get(i), 3);
			int confRank = Scraper.getInt(rows.get(i), 2);
			int natRank = Scraper.getInt(rows.get(i), 1);
			_stats[i] = new TeamStat(fieldName, value, confRank, natRank);
		}
		
	}
	
	public void addPlayer(Player player) {
		_roster.add(player);
	}
	
	public String toString() {
		StringBuilder out = new StringBuilder();
		out.append(_name + " " + _wins + "-" + _losses + "\n");
		out.append(String.format("%2s %10s %10s %5s %5s %5s %5s", 
								 	  "#", "First", "Last", "GP", "MIN", "PPG", "RPG") + "\n");
		for (Player p : _roster) {
			out.append(p + "\n");
		}
		return out.toString();
	}
}
