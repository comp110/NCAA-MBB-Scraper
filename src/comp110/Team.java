package comp110;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Team {
	public String _name;
	public ArrayList<Player> _players;
	public int _wins, _losses;
	
	
	
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
		_players = new ArrayList<Player>();
	}
	
	public void addPlayer(Player player) {
		_players.add(player);
	}
	
	public String toString() {
		StringBuilder out = new StringBuilder();
		out.append(_name + " " + _wins + "-" + _losses + "\n");
		out.append(String.format("%2s %10s %10s %5s %5s %5s %5s", 
								 	  "#", "First", "Last", "GP", "MIN", "PPG", "RPG") + "\n");
		for (Player p : _players) {
			out.append(p + "\n");
		}
		return out.toString();
	}
}
