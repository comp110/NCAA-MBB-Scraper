package comp110;

import org.jsoup.nodes.Element;

public class Player {
	public int _jersey, _height, _played, _started, _minutes, _fieldGoals, _fieldGoalsAtt, _threes,
		_threesAtt, _freeThrows, _freeThrowsAtt, _totalPoints, _oRebs, _dRebs, _totalRebs, _assists,
		_turnovers, _steals, _blocks, _fouls;
	public String _first, _last, _year, _position;
	public double _fgPercent, _threePercent, _ftPercent, _avgPoints, _avgReb;
	
	public Player(Element row) {
		// Reads in the <td> text and stores it in the appropriate instance variable
		_jersey = getInt(row, 0);
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
		_played = getInt(row, 5);
		_started = getInt(row, 6);
		// Minutes on the page are stored like 345:00
		String rawMinutes = row.child(7).text();
		_minutes = Integer.parseInt(rawMinutes.substring(0, rawMinutes.length()-3));
		_fieldGoals = getInt(row, 8);
		_fieldGoalsAtt = getInt(row, 9);
		_fgPercent = getDoub(row, 10);
		_threes = getInt(row, 11);
		_threesAtt = getInt(row, 12);
		_threePercent = getDoub(row, 13);
		_freeThrows = getInt(row, 14);
		_freeThrowsAtt = getInt(row, 15);
		_ftPercent = getDoub(row, 16);
		_totalPoints = getInt(row, 17);
		_avgPoints = getDoub(row, 18);
		_oRebs = getInt(row, 19);
		_dRebs = getInt(row, 20);
		_totalRebs = getInt(row, 21);
		_avgReb = getDoub(row, 22);
		_assists = getInt(row, 23);
		_turnovers = getInt(row, 24);
		_steals = getInt(row, 25);
		_blocks = getInt(row, 26);
		_fouls = getInt(row, 27);
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
	
	public String toString() {
		return String.format("%2d %10s %10s %5d %5d %5.2f %5.2f", _jersey, _first, _last, _played, _minutes, _avgPoints, _avgReb);
	}
	
	
}
