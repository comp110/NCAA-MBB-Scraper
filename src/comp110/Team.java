package comp110;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.select.Elements;

public class Team {
	private String _name;
	private Player[] _roster;
	
	public double _threesAttempted, _assistTurnoverRatio, _avgAssists, _avgBlocks, _avgDRebs, 
	    _fouls, _turnovers, _fgPercent, _fgPercentDef, _freeThrowsAttempted, _freeThrowsMade,
	    _ftPercent, _avgORebs, _avgPersonalFouls, _reboundMargin, _scoringDefense, _scoringMargin, 
	    _scoringOffense, _avgSteals, _threePointDef, _avgThreesMade, _threePercent, _threesMade, 
	    _totalAssists, _totalBlocks, _totalRebounds, _avgRebounds, _totalSteals, _turnoverMargin, 
	    _turnoversForced, _avgTurnovers, _wonLostPercent;
	
	private int _threesAttemptedRank, _assistTurnoverRatioRank, _avgAssistsRank, _avgBlocksRank,
	    _avgDRebsRank, _foulsRank, _turnoversRank, _fgPercentRank, _fgPercentDefRank,
	    _freeThrowsAttemptedRank, _freeThrowsMadeRank, _ftPercentRank, _avgORebsRank,
	    _avgPersonalFoulsRank, _reboundMarginRank, _scoringDefenseRank, _scoringMarginRank,
	    _scoringOffenseRank, _avgStealsRank, _threePointDefRank, _avgThreesMadeRank,
	    _threePercentRank, _threesMadeRank, _totalAssistsRank, _totalBlocksRank,
	    _totalReboundsRank, _avgReboundsRank, _totalStealsRank, _turnoverMarginRank,
	    _turnoversForcedRank, _avgTurnoversRank, _wonLostPercentRank, _wins, _losses, _id;
	
	private String _imagePath;
	
	
	public Team(String header, int numPlayers) {
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
		_roster = new Player[numPlayers];
		
	}
	
	public void populateFields(Elements rows) {
		// Getting everything in the right field
		rows.remove(0);
		_threesAttempted = Scraper.getDoub(rows.get(0), 3);
		_threesAttemptedRank = Scraper.getRank(rows.get(0), 1);
		_assistTurnoverRatio = Scraper.getDoub(rows.get(1), 3);
		_assistTurnoverRatioRank = Scraper.getRank(rows.get(1), 1);
		_avgAssists = Scraper.getDoub(rows.get(2), 3);
		_avgAssistsRank = Scraper.getRank(rows.get(2), 1);
		_avgBlocks = Scraper.getDoub(rows.get(3), 3);
		_avgBlocksRank = Scraper.getRank(rows.get(3), 1);
		_avgDRebs = Scraper.getDoub(rows.get(4), 3);
		_avgDRebsRank = Scraper.getRank(rows.get(4), 1);
		_fouls = Scraper.getDoub(rows.get(5), 3);
		_foulsRank = Scraper.getRank(rows.get(5), 1);
		_turnovers = Scraper.getDoub(rows.get(6), 3);
		_turnoversRank = Scraper.getRank(rows.get(6), 1);
		_fgPercent = Scraper.getDoub(rows.get(7), 3);
		_fgPercentRank = Scraper.getRank(rows.get(7), 1);
		_fgPercentDef = Scraper.getDoub(rows.get(8), 3);
		_fgPercentDefRank = Scraper.getRank(rows.get(8), 1);
		_freeThrowsAttempted = Scraper.getDoub(rows.get(9), 3);
		_freeThrowsAttemptedRank = Scraper.getRank(rows.get(9), 1);
		_freeThrowsMade = Scraper.getDoub(rows.get(10), 3);
		_freeThrowsMadeRank = Scraper.getRank(rows.get(10), 1);
		_ftPercent = Scraper.getDoub(rows.get(11), 3);
		_ftPercentRank = Scraper.getRank(rows.get(11), 1);
		_avgORebs = Scraper.getDoub(rows.get(12), 3);
		_avgORebsRank = Scraper.getRank(rows.get(12), 1);
		_avgPersonalFouls = Scraper.getDoub(rows.get(13), 3);
		_avgPersonalFoulsRank = Scraper.getRank(rows.get(13), 1);
		_reboundMargin = Scraper.getDoub(rows.get(14), 3);
		_reboundMarginRank = Scraper.getRank(rows.get(14), 1);
		_scoringDefense = Scraper.getDoub(rows.get(15), 3);
		_scoringDefenseRank = Scraper.getRank(rows.get(15), 1);
		_scoringMargin = Scraper.getDoub(rows.get(16), 3);
		_scoringMarginRank = Scraper.getRank(rows.get(16), 1);
		_scoringOffense = Scraper.getDoub(rows.get(17), 3);
		_scoringOffenseRank = Scraper.getRank(rows.get(17), 1);
		_avgSteals = Scraper.getDoub(rows.get(18), 3);
		_avgStealsRank = Scraper.getRank(rows.get(18), 1);
		_threePointDef = Scraper.getDoub(rows.get(19), 3);
		_threePointDefRank = Scraper.getRank(rows.get(19), 1);
		_avgThreesMade = Scraper.getDoub(rows.get(20), 3);
		_avgThreesMadeRank = Scraper.getRank(rows.get(20), 1);
		_threePercent = Scraper.getDoub(rows.get(21), 3);
		_threePercentRank = Scraper.getRank(rows.get(21), 1);
		_threesMade = Scraper.getDoub(rows.get(22), 3);
		_threesMadeRank = Scraper.getRank(rows.get(22), 1);
		_totalAssists = Scraper.getDoub(rows.get(23), 3);
		_totalAssistsRank = Scraper.getRank(rows.get(23), 1);
		_totalBlocks = Scraper.getDoub(rows.get(24), 3);
		_totalBlocksRank = Scraper.getRank(rows.get(24), 1);
		_totalRebounds = Scraper.getDoub(rows.get(25), 3);
		_totalReboundsRank = Scraper.getRank(rows.get(25), 1);
		_avgRebounds = Scraper.getDoub(rows.get(26), 3);
		_avgReboundsRank = Scraper.getRank(rows.get(26), 1);
		_totalSteals = Scraper.getDoub(rows.get(27), 3);
		_totalStealsRank = Scraper.getRank(rows.get(27), 1);
		_turnoverMargin = Scraper.getDoub(rows.get(28), 3);
		_turnoverMarginRank = Scraper.getRank(rows.get(28), 1);
		_turnoversForced = Scraper.getDoub(rows.get(29), 3);
		_turnoversForcedRank = Scraper.getRank(rows.get(29), 1);
		_avgTurnovers = Scraper.getDoub(rows.get(30), 3);
		_avgTurnoversRank = Scraper.getRank(rows.get(30), 1);
		_wonLostPercent = Scraper.getDoub(rows.get(31), 3);
		_wonLostPercentRank = Scraper.getRank(rows.get(31), 1);

		
	}
	
	public void addPlayer(Player player, int i) {
		_roster[i] = player;
	}
	
	public Player[] getRoster() {
		return Arrays.copyOf(_roster, _roster.length);
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

	public String getName() {
		return _name;
	}

	public int getWins() {
		return _wins;
	}

	public int getLosses() {
		return _losses;
	}

	public double getThreesAttempted() {
		return _threesAttempted;
	}

	public double getAssistTurnoverRatio() {
		return _assistTurnoverRatio;
	}

	public double getAvgAssists() {
		return _avgAssists;
	}

	public double getAvgBlocks() {
		return _avgBlocks;
	}

	public double getAvgDRebs() {
		return _avgDRebs;
	}

	public double getFouls() {
		return _fouls;
	}

	public double getTurnovers() {
		return _turnovers;
	}

	public double getFgPercent() {
		return _fgPercent;
	}

	public double getFgPercentDef() {
		return _fgPercentDef;
	}

	public double getFreeThrowsAttempted() {
		return _freeThrowsAttempted;
	}

	public double getFreeThrowsMade() {
		return _freeThrowsMade;
	}

	public double getFtPercent() {
		return _ftPercent;
	}

	public double getAvgORebs() {
		return _avgORebs;
	}

	public double getAvgPersonalFouls() {
		return _avgPersonalFouls;
	}

	public double getReboundMargin() {
		return _reboundMargin;
	}

	public double getScoringDefense() {
		return _scoringDefense;
	}

	public double getScoringMargin() {
		return _scoringMargin;
	}

	public double getScoringOffense() {
		return _scoringOffense;
	}

	public double getAvgSteals() {
		return _avgSteals;
	}

	public double getThreePointDef() {
		return _threePointDef;
	}

	public double getAvgThreesMade() {
		return _avgThreesMade;
	}

	public double getThreePercent() {
		return _threePercent;
	}

	public double getThreesMade() {
		return _threesMade;
	}

	public double getTotalAssists() {
		return _totalAssists;
	}

	public double getTotalBlocks() {
		return _totalBlocks;
	}

	public double getTotalRebounds() {
		return _totalRebounds;
	}

	public double getAvgRebounds() {
		return _avgRebounds;
	}

	public double getTotalSteals() {
		return _totalSteals;
	}

	public double getTurnoverMargin() {
		return _turnoverMargin;
	}

	public double getTurnoversForced() {
		return _turnoversForced;
	}

	public double getAvgTurnovers() {
		return _avgTurnovers;
	}

	public double getWonLostPercent() {
		return _wonLostPercent;
	}

	public int getThreesAttemptedRank() {
		return _threesAttemptedRank;
	}

	public int getAssistTurnoverRatioRank() {
		return _assistTurnoverRatioRank;
	}

	public int getAvgAssistsRank() {
		return _avgAssistsRank;
	}

	public int getAvgBlocksRank() {
		return _avgBlocksRank;
	}

	public int getAvgDRebsRank() {
		return _avgDRebsRank;
	}

	public int getFoulsRank() {
		return _foulsRank;
	}

	public int getTurnoversRank() {
		return _turnoversRank;
	}

	public int getFgPercentRank() {
		return _fgPercentRank;
	}

	public int getFgPercentDefRank() {
		return _fgPercentDefRank;
	}

	public int getFreeThrowsAttemptedRank() {
		return _freeThrowsAttemptedRank;
	}

	public int getFreeThrowsMadeRank() {
		return _freeThrowsMadeRank;
	}

	public int getFtPercentRank() {
		return _ftPercentRank;
	}

	public int getAvgORebsRank() {
		return _avgORebsRank;
	}

	public int getAvgPersonalFoulsRank() {
		return _avgPersonalFoulsRank;
	}

	public int getReboundMarginRank() {
		return _reboundMarginRank;
	}

	public int getScoringDefenseRank() {
		return _scoringDefenseRank;
	}

	public int getScoringMarginRank() {
		return _scoringMarginRank;
	}

	public int getScoringOffenseRank() {
		return _scoringOffenseRank;
	}

	public int getAvgStealsRank() {
		return _avgStealsRank;
	}

	public int getThreePointDefRank() {
		return _threePointDefRank;
	}

	public int getAvgThreesMadeRank() {
		return _avgThreesMadeRank;
	}

	public int getThreePercentRank() {
		return _threePercentRank;
	}

	public int getThreesMadeRank() {
		return _threesMadeRank;
	}

	public int getTotalAssistsRank() {
		return _totalAssistsRank;
	}

	public int getTotalBlocksRank() {
		return _totalBlocksRank;
	}

	public int getTotalReboundsRank() {
		return _totalReboundsRank;
	}

	public int getAvgReboundsRank() {
		return _avgReboundsRank;
	}

	public int getTotalStealsRank() {
		return _totalStealsRank;
	}

	public int getTurnoverMarginRank() {
		return _turnoverMarginRank;
	}

	public int getTurnoversForcedRank() {
		return _turnoversForcedRank;
	}

	public int getAvgTurnoversRank() {
		return _avgTurnoversRank;
	}

	public int getWonLostPercentRank() {
		return _wonLostPercentRank;
	}

	public int getId() {
		return _id;
	}
	
	public String getImagePath(){
	  return _id + ".png";
	}

	public void setId(int id) {
		_id = id;
	}
	
	
	
	
}
