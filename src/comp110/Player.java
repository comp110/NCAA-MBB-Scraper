package comp110;

import org.jsoup.nodes.Element;

public class Player {
	private int _jersey, _height, _played, _started, _minutes, _fieldGoals, _fieldGoalsAtt, _threes,
		_threesAtt, _freeThrows, _freeThrowsAtt, _totalPoints, _oRebs, _dRebs, _totalRebs, _assists,
		_turnovers, _steals, _blocks, _fouls;
	private String _first, _last, _year, _position;
	private double _fgPercent, _threePercent, _ftPercent, _avgPoints, _avgReb;
	
	public Player(Element row) {
		// Reads in the <td> text and stores it in the appropriate instance variable
		_jersey = Scraper.getInt(row, 0);
		String rawName = row.child(1).text();
		String[] rawNameArr = rawName.split(", ");
		_last = rawNameArr[0];
		_first = rawNameArr[1];
		_year = row.child(2).text();
		_position = row.child(3).text();
		String[] rawHeight = row.child(4).text().split("-");
		// Toby Egbuna's height is not on the NCAA page so this handles that
		if (rawHeight.length>1) {
			// Storing height in inches
			_height = Integer.parseInt(rawHeight[0]) * 12 + Integer.parseInt(rawHeight[1]);
		} else {
			_height = 0;
		}
		_played = Scraper.getInt(row, 5);
		_started = Scraper.getInt(row, 6);
		// Minutes on the page are stored like 345:00
		String rawMinutes = row.child(7).text();
		_minutes = Integer.parseInt(rawMinutes.substring(0, rawMinutes.length()-3));
		_fieldGoals = Scraper.getInt(row, 8);
		_fieldGoalsAtt = Scraper.getInt(row, 9);
		_fgPercent = Scraper.getDoub(row, 10);
		_threes = Scraper.getInt(row, 11);
		_threesAtt = Scraper.getInt(row, 12);
		_threePercent = Scraper.getDoub(row, 13);
		_freeThrows = Scraper.getInt(row, 14);
		_freeThrowsAtt = Scraper.getInt(row, 15);
		_ftPercent = Scraper.getDoub(row, 16);
		_totalPoints = Scraper.getInt(row, 17);
		_avgPoints = Scraper.getDoub(row, 18);
		_oRebs = Scraper.getInt(row, 19);
		_dRebs = Scraper.getInt(row, 20);
		_totalRebs = Scraper.getInt(row, 21);
		_avgReb = Scraper.getDoub(row, 22);
		_assists = Scraper.getInt(row, 23);
		_turnovers = Scraper.getInt(row, 24);
		_steals = Scraper.getInt(row, 25);
		_blocks = Scraper.getInt(row, 26);
		_fouls = Scraper.getInt(row, 27);
	}
	

	
	public String toString() {
		return String.format("%2d %10s %10s %5d %5d %5.2f %5.2f", _jersey, _first, _last, _played, _minutes, _avgPoints, _avgReb);
	}
	
	
}
